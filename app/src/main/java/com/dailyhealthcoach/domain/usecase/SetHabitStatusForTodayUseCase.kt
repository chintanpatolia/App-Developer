package com.dailyhealthcoach.domain.usecase

import com.dailyhealthcoach.domain.model.HabitStatus
import com.dailyhealthcoach.domain.repository.HabitRepository

class SetHabitStatusForTodayUseCase(
    private val habitRepository: HabitRepository
) {
    suspend operator fun invoke(
        habitDefinitionId: Long,
        date: String,
        status: HabitStatus
    ) {
        habitRepository.setHabitStatusForDate(
            habitDefinitionId = habitDefinitionId,
            date = date,
            status = status.storageValue
        )
    }
}
