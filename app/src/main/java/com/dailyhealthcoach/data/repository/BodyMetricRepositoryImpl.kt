package com.dailyhealthcoach.data.repository

import com.dailyhealthcoach.data.local.dao.BodyMetricLogDao
import com.dailyhealthcoach.data.local.entity.BodyMetricLogEntity
import com.dailyhealthcoach.domain.model.BodyMetricLog
import com.dailyhealthcoach.domain.repository.BodyMetricRepository
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
}

private fun BodyMetricLogEntity.toDomain(): BodyMetricLog {
    return BodyMetricLog(
        id = id,
        date = date,
        bodyWeight = bodyWeight,
        sleepHours = sleepHours,
        energyLevel = energyLevel,
        stressLevel = stressLevel,
        sorenessLevel = sorenessLevel,
        restingHeartRate = restingHeartRate,
        stepCount = stepCount,
        notes = notes
    )
}
