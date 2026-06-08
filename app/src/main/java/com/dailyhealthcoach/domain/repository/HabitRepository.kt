package com.dailyhealthcoach.domain.repository

import com.dailyhealthcoach.domain.model.DailyHabitLog
import com.dailyhealthcoach.domain.model.HabitDefinition
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    fun observeActiveHabits(): Flow<List<HabitDefinition>>
    fun observeLogsForDate(date: String): Flow<List<DailyHabitLog>>
    suspend fun setHabitStatusForDate(
        habitDefinitionId: Long,
        date: String,
        status: String,
        notes: String? = null
    )
}
