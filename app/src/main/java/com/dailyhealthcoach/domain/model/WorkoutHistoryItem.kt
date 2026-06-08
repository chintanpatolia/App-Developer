package com.dailyhealthcoach.domain.model

data class WorkoutHistoryItem(
    val id: Long,
    val date: String,
    val name: String,
    val status: WorkoutStatus,
    val durationMinutes: Int?,
    val setCount: Int,
    val muscleGroups: List<String>
)
