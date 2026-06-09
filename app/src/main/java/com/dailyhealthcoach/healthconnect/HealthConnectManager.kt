package com.dailyhealthcoach.healthconnect

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build

enum class HcStatus { AVAILABLE, UNAVAILABLE }

// Foundation bridge. Data-reading methods require androidx.health.connect:connect-client
// to be added in Phase 14b (once the library can be downloaded).
object HealthConnectManager {

    private const val HC_PACKAGE = "com.google.android.apps.healthdata"

    fun getSdkStatus(context: Context): HcStatus {
        // Android 14+ has Health Connect built into the platform.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) return HcStatus.AVAILABLE
        // Older Android: check whether the standalone HC app is installed.
        return try {
            context.packageManager.getPackageInfo(HC_PACKAGE, 0)
            HcStatus.AVAILABLE
        } catch (e: PackageManager.NameNotFoundException) {
            HcStatus.UNAVAILABLE
        }
    }

    fun openSettings(context: Context) {
        try {
            context.startActivity(Intent("androidx.health.ACTION_HEALTH_CONNECT_SETTINGS"))
        } catch (_: ActivityNotFoundException) {
            // HC not available to open; status card already shows UNAVAILABLE.
        }
    }

    // ── Phase 14b stubs ──────────────────────────────────────────────────────
    // Replace with real implementations once connect-client library is added.

    suspend fun readTodaySteps(context: Context): Long? = null
    suspend fun readLastSleepHours(context: Context): Double? = null
    suspend fun readLastWeightPounds(context: Context): Double? = null
    suspend fun readLastRestingHeartRate(context: Context): Int? = null
}
