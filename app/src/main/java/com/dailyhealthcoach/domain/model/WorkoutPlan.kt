package com.dailyhealthcoach.domain.model

data class SuggestedExercise(
    val exerciseId: Long,
    val name: String,
    val muscleGroup: String
)

data class WorkoutPlan(
    val focus: String,
    val setsPerExercise: Int,
    val repsRange: String,
    val rpeTarget: String,
    val durationMinutes: String,
    val suggestedExercises: List<SuggestedExercise>,
    val isStrengthDay: Boolean,
    val reasons: List<String>,
    val nonStrengthActivities: List<String>
)
