package com.dailyhealthcoach.domain.model

data class DailyRecommendation(
    val id: Long,
    val date: String,
    val recommendationType: String,
    val title: String,
    val explanation: String,
    val suggestedFocus: String,
    val reasonBullets: List<String>,
    val targetMuscleGroups: String?,
    val intensity: String?,
    val loadGuidance: String?,
    val reasonSummary: String
)
