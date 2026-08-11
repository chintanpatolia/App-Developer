package com.dailyhealthcoach.domain.model

data class WorkoutSetInput(
    val exerciseId: Long,
    val setNumber: Int,
    val reps: Int?,
    val weight: Double?,
    val rpe: Int?,
    val notes: String?
)
