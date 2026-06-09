package com.dailyhealthcoach.domain.model

data class DashboardSummary(
    val totalHabits: Int,
    val completedHabits: Int,
    val proteinConsumedGrams: Int,
    val proteinMinGoalGrams: Int,
    val proteinMaxGoalGrams: Int,
    val steps: Int,
    val stepGoal: Int,
    val sleepHours: Double,
    val recoveryScore: Int?,
    val recoveryLabel: String,
    val recoveryReasons: List<String>,
    val nextDayRecommendation: DailyRecommendation?
)
