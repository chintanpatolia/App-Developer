package com.dailyhealthcoach.ui.dashboard

data class DashboardUiState(
    val isLoading: Boolean = true,
    val totalHabits: Int = 0,
    val completedHabits: Int = 0,
    val proteinConsumedGrams: Int = 0,
    val proteinMinGoalGrams: Int = 170,
    val proteinMaxGoalGrams: Int = 200,
    val steps: Int = 0,
    val stepGoal: Int = 8_000,
    val sleepHours: Double = 0.0,
    val recoveryScore: Int = 0,
    val nextDayRecommendation: String = ""
)
