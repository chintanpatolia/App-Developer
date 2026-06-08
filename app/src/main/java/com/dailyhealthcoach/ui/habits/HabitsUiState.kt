package com.dailyhealthcoach.ui.habits

import com.dailyhealthcoach.domain.model.HabitStatus

data class HabitsUiState(
    val isLoading: Boolean = true,
    val completionPercentage: Int = 0,
    val completedCount: Int = 0,
    val trackableCount: Int = 0,
    val habits: List<HabitRowUiState> = emptyList()
)

data class HabitRowUiState(
    val id: Long,
    val name: String,
    val description: String,
    val frequencyLabel: String,
    val targetText: String?,
    val status: HabitStatus,
    val isTodayTrackable: Boolean
)
