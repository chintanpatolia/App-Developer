package com.dailyhealthcoach.domain.usecase

import com.dailyhealthcoach.domain.model.HabitFrequency
import com.dailyhealthcoach.domain.model.HabitStatus
import com.dailyhealthcoach.domain.model.HabitToday
import com.dailyhealthcoach.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetTodayHabitsUseCase(
    private val habitRepository: HabitRepository
) {
    operator fun invoke(date: String): Flow<List<HabitToday>> {
        return combine(
            habitRepository.observeActiveHabits(),
            habitRepository.observeLogsForDate(date)
        ) { habits, logs ->
            val logsByHabitId = logs.associateBy { it.habitDefinitionId }
            habits.sortedWith(
                compareBy(
                    { if (HabitFrequency.isTodayTrackable(it.frequencyType)) 0 else 1 },
                    { it.sortOrder }
                )
            ).map { habit ->
                val isTodayTrackable = HabitFrequency.isTodayTrackable(habit.frequencyType)
                HabitToday(
                    id = habit.id,
                    name = habit.name,
                    description = habit.description,
                    frequencyType = habit.frequencyType,
                    frequencyLabel = HabitFrequency.labelFor(habit.frequencyType),
                    targetPerWeek = habit.targetPerWeek,
                    status = HabitStatus.fromStorageValue(logsByHabitId[habit.id]?.status),
                    isTodayTrackable = isTodayTrackable
                )
            }
        }
    }
}
