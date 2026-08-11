package com.dailyhealthcoach.domain.model

data class WorkoutExercise(
    val id: Long,
    val workoutId: Long,
    val exerciseId: Long,
    val setNumber: Int,
    val reps: Int?,
    val weight: Double?,
    val rpe: Int?,
    val restSeconds: Int?,
    val notes: String?
)
