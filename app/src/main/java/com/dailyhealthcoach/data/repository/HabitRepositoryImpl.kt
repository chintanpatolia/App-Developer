package com.dailyhealthcoach.data.repository

import com.dailyhealthcoach.data.local.dao.DailyHabitLogDao
import com.dailyhealthcoach.data.local.dao.HabitDefinitionDao
import com.dailyhealthcoach.data.local.entity.DailyHabitLogEntity
import com.dailyhealthcoach.data.local.entity.HabitDefinitionEntity
import com.dailyhealthcoach.domain.model.DailyHabitLog
import com.dailyhealthcoach.domain.model.HabitDefinition
import com.dailyhealthcoach.domain.repository.HabitRepository
import java.time.Instant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HabitRepositoryImpl(
    private val habitDefinitionDao: HabitDefinitionDao,
    private val dailyHabitLogDao: DailyHabitLogDao
) : HabitRepository {
    override fun observeActiveHabits(): Flow<List<HabitDefinition>> {
        return habitDefinitionDao.observeActiveHabits()
            .map { habits -> habits.map { it.toDomain() } }
    }

    override fun observeLogsForDate(date: String): Flow<List<DailyHabitLog>> {
        return dailyHabitLogDao.observeLogsForDate(date)
            .map { logs -> logs.map { it.toDomain() } }
    }

    override suspend fun setHabitStatusForDate(
        habitDefinitionId: Long,
        date: String,
        status: String,
        notes: String?
    ) {
        val now = Instant.now().toString()
        val insertedId = dailyHabitLogDao.insertIgnore(
            DailyHabitLogEntity(
                habitDefinitionId = habitDefinitionId,
                date = date,
                status = status,
                notes = notes,
                updatedAt = now
            )
        )

        if (insertedId == -1L) {
            dailyHabitLogDao.updateStatus(
                habitDefinitionId = habitDefinitionId,
                date = date,
                status = status,
                notes = notes,
                updatedAt = now
            )
        }
    }
}

private fun HabitDefinitionEntity.toDomain(): HabitDefinition {
    return HabitDefinition(
        id = id,
        name = name,
        description = description,
        frequencyType = frequencyType,
        targetPerWeek = targetPerWeek,
        sortOrder = sortOrder
    )
}

private fun DailyHabitLogEntity.toDomain(): DailyHabitLog {
    return DailyHabitLog(
        id = id,
        habitDefinitionId = habitDefinitionId,
        date = date,
        status = status,
        notes = notes
    )
}
