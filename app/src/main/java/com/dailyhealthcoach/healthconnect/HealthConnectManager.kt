package com.dailyhealthcoach.healthconnect

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.HeightRecord
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.RestingHeartRateRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.dailyhealthcoach.domain.model.BodyMetricLogInput
import com.dailyhealthcoach.domain.repository.BodyMetricRepository
import com.dailyhealthcoach.domain.usecase.HabitAutoUpdateUseCase
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.flow.first

enum class HcStatus { AVAILABLE, UNAVAILABLE }

object HealthConnectManager {

    private const val HC_PACKAGE = "com.google.android.apps.healthdata"

    fun getSdkStatus(context: Context): HcStatus {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) return HcStatus.AVAILABLE
        return try {
            context.packageManager.getPackageInfo(HC_PACKAGE, 0)
            HcStatus.AVAILABLE
        } catch (e: PackageManager.NameNotFoundException) {
            HcStatus.UNAVAILABLE
        }
    }

    fun openSettings(context: Context): Boolean {
        val intents = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            listOf(
                Intent("android.health.connect.action.HEALTH_HOME_SETTINGS"),
                Intent("androidx.health.ACTION_HEALTH_CONNECT_SETTINGS")
            )
        } else {
            listOf(
                Intent("androidx.health.ACTION_HEALTH_CONNECT_SETTINGS"),
                Intent("android.health.connect.action.HEALTH_HOME_SETTINGS")
            )
        }
        for (intent in intents) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            try {
                context.startActivity(intent)
                return true
            } catch (_: ActivityNotFoundException) { }
        }
        return false
    }

    suspend fun readTodaySteps(context: Context): Long? = runCatching {
        val client = HealthConnectClient.getOrCreate(context)
        val today = LocalDate.now()
        val start = today.atStartOfDay(ZoneId.systemDefault()).toInstant()
        val end = Instant.now()
        val response = client.readRecords(
            ReadRecordsRequest(
                recordType = StepsRecord::class,
                timeRangeFilter = TimeRangeFilter.between(start, end)
            )
        )
        response.records.sumOf { it.count }.takeIf { it > 0 }
    }.getOrNull()

    suspend fun readLastSleepHours(context: Context): Double? = runCatching {
        val client = HealthConnectClient.getOrCreate(context)
        val start = LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()
        val end = Instant.now()
        val response = client.readRecords(
            ReadRecordsRequest(
                recordType = SleepSessionRecord::class,
                timeRangeFilter = TimeRangeFilter.between(start, end)
            )
        )
        response.records.maxByOrNull { it.endTime }?.let { session ->
            val millis = session.endTime.toEpochMilli() - session.startTime.toEpochMilli()
            val hours = millis / 3_600_000.0
            hours.takeIf { it > 0 }
        }
    }.getOrNull()

    suspend fun readLastWeightPounds(context: Context): Double? = runCatching {
        val client = HealthConnectClient.getOrCreate(context)
        val start = LocalDate.now().minusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant()
        val end = Instant.now()
        val response = client.readRecords(
            ReadRecordsRequest(
                recordType = WeightRecord::class,
                timeRangeFilter = TimeRangeFilter.between(start, end)
            )
        )
        response.records.maxByOrNull { it.time }?.weight?.inPounds?.takeIf { it > 0 }
    }.getOrNull()

    suspend fun readLastRestingHeartRate(context: Context): Int? = runCatching {
        val client = HealthConnectClient.getOrCreate(context)
        val start = LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()
        val end = Instant.now()
        val response = client.readRecords(
            ReadRecordsRequest(
                recordType = RestingHeartRateRecord::class,
                timeRangeFilter = TimeRangeFilter.between(start, end)
            )
        )
        response.records.maxByOrNull { it.time }?.beatsPerMinute?.toInt()?.takeIf { it > 0 }
    }.getOrNull()

    suspend fun readLastHeightInches(context: Context): Double? = runCatching {
        val client = HealthConnectClient.getOrCreate(context)
        val start = LocalDate.now().minusDays(365).atStartOfDay(ZoneId.systemDefault()).toInstant()
        val end = Instant.now()
        val response = client.readRecords(
            ReadRecordsRequest(
                recordType = HeightRecord::class,
                timeRangeFilter = TimeRangeFilter.between(start, end)
            )
        )
        response.records.maxByOrNull { it.time }?.height?.inInches?.takeIf { it > 0 }
    }.getOrNull()

    // When HC toggle is ON, HC is authoritative for steps/sleep/weight/RHR/height — always
    // overwrites those fields. Manual-only fields (stress, energy, soreness, notes, waist,
    // neck, body fat override) are never touched.
    suspend fun syncToday(
        context: Context,
        bodyMetricRepository: BodyMetricRepository,
        habitAutoUpdateUseCase: HabitAutoUpdateUseCase
    ) {
        val prefs = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        if (!prefs.getBoolean("use_health_connect", false)) return
        val today = LocalDate.now().toString()
        val existing = bodyMetricRepository.observeForDate(today).first()

        val steps = readTodaySteps(context)?.toInt()
        val sleep = readLastSleepHours(context)
        val weight = readLastWeightPounds(context)
        val hr = readLastRestingHeartRate(context)
        val height = readLastHeightInches(context)

        Log.d("HCSync", "steps=$steps sleep=$sleep weight=$weight hr=$hr height=$height existing_weight=${existing?.bodyWeight}")

        if (steps == null && sleep == null && weight == null && hr == null && height == null) {
            Log.d("HCSync", "No HC data found — nothing to write")
            return
        }
        bodyMetricRepository.saveForDate(
            BodyMetricLogInput(
                date = today,
                heightInches = height ?: existing?.heightInches,
                bodyWeight = weight ?: existing?.bodyWeight,
                bodyFatPercentage = existing?.bodyFatPercentage,
                calculatedBodyFatPercent = existing?.calculatedBodyFatPercent,
                manualBodyFatPercent = existing?.manualBodyFatPercent,
                isBodyFatOverridden = existing?.isBodyFatOverridden ?: false,
                waistMeasurement = existing?.waistMeasurement,
                neckMeasurement = existing?.neckMeasurement,
                chestMeasurement = existing?.chestMeasurement,
                armMeasurement = existing?.armMeasurement,
                sleepHours = sleep ?: existing?.sleepHours,
                energyLevel = existing?.energyLevel,
                stressLevel = existing?.stressLevel,
                sorenessLevel = existing?.sorenessLevel,
                restingHeartRate = hr ?: existing?.restingHeartRate,
                stepCount = steps ?: existing?.stepCount,
                notes = existing?.notes
            )
        )
        Log.d("HCSync", "Saved: weight=$weight steps=$steps sleep=$sleep hr=$hr height=$height")
        habitAutoUpdateUseCase(today)
    }

    private const val HC_WORK_NAME = "hc_periodic_sync"

    fun schedulePeriodicSync(context: Context) {
        val request = PeriodicWorkRequestBuilder<HealthConnectSyncWorker>(15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(context.applicationContext).enqueueUniquePeriodicWork(
            HC_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    fun cancelPeriodicSync(context: Context) {
        WorkManager.getInstance(context.applicationContext).cancelUniqueWork(HC_WORK_NAME)
    }

    fun scheduleIfEnabled(context: Context) {
        val prefs = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        if (prefs.getBoolean("use_health_connect", false)) {
            schedulePeriodicSync(context)
        }
    }
}
