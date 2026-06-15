package com.dailyhealthcoach.domain.model

data class SuggestedExercise(
    val exerciseId: Long,
    val name: String,
    val muscleGroup: String,
    val prescribedSets: Int = 0,
    val prescribedRepsRange: String = "",
    val prescribedRpe: String = "",
    val suggestedWeightText: String = ""
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
    val nonStrengthActivities: List<String>,
    val warmUp: List<String> = emptyList(),
    val coolDown: List<String> = emptyList(),
    val postWorkoutRecommendations: List<String> = emptyList()
)
