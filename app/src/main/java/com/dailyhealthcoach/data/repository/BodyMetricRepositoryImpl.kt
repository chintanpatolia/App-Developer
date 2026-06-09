package com.dailyhealthcoach.data.repository

import com.dailyhealthcoach.data.local.dao.BodyMetricLogDao
import com.dailyhealthcoach.data.local.entity.BodyMetricLogEntity
import com.dailyhealthcoach.domain.model.BodyMetricLog
import com.dailyhealthcoach.domain.model.BodyMetricLogInput
import com.dailyhealthcoach.domain.repository.BodyMetricRepository
import java.time.Instant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BodyMetricRepositoryImpl(
    private val bodyMetricLogDao: BodyMetricLogDao
) : BodyMetricRepository {
    override fun observeForDate(date: String): Flow<BodyMetricLog?> {
        return bodyMetricLogDao.observeForDate(date).map { it?.toDomain() }
    }

    override fun observeAll(): Flow<List<BodyMetricLog>> {
        return bodyMetricLogDao.observeAll().map { logs -> logs.map { it.toDomain() } }
    }

    override suspend fun saveForDate(input: BodyMetricLogInput) {
        val now = Instant.now().toString()
        val existing = bodyMetricLogDao.getForDate(input.date)
        bodyMetricLogDao.upsert(
            BodyMetricLogEntity(
                id = existing?.id ?: 0,
                date = input.date,
                heightInches = input.heightInches,
                bodyWeight = input.bodyWeight,
                bodyFatPercentage = input.bodyFatPercentage,
                calculatedBodyFatPercent = input.calculatedBodyFatPercent,
                manualBodyFatPercent = input.manualBodyFatPercent,
                isBodyFatOverridden = input.isBodyFatOverridden,
                waistMeasurement = input.waistMeasurement,
                neckMeasurement = input.neckMeasurement,
                chestMeasurement = input.chestMeasurement,
                armMeasurement = input.armMeasurement,
                sleepHours = input.sleepHours,
                energyLevel = input.energyLevel,
                stressLevel = input.stressLevel,
                sorenessLevel = input.sorenessLevel,
                restingHeartRate = input.restingHeartRate,
                stepCount = existing?.stepCount,
                notes = input.notes,
                createdAt = existing?.createdAt ?: now,
                updatedAt = now
            )
        )
    }
}

private fun BodyMetricLogEntity.toDomain(): BodyMetricLog {
    return BodyMetricLog(
        id = id,
        date = date,
        heightInches = heightInches,
        bodyWeight = bodyWeight,
        bodyFatPercentage = bodyFatPercentage,
        calculatedBodyFatPercent = calculatedBodyFatPercent,
        manualBodyFatPercent = manualBodyFatPercent,
        isBodyFatOverridden = isBodyFatOverridden,
        waistMeasurement = waistMeasurement,
        neckMeasurement = neckMeasurement,
        chestMeasurement = chestMeasurement,
        armMeasurement = armMeasurement,
        sleepHours = sleepHours,
        energyLevel = energyLevel,
        stressLevel = stressLevel,
        sorenessLevel = sorenessLevel,
        restingHeartRate = restingHeartRate,
        stepCount = stepCount,
        notes = notes
    )
}
