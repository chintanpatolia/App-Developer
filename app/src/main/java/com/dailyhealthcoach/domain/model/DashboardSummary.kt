package com.dailyhealthcoach.domain.model

import com.dailyhealthcoach.domain.recovery.RecoveryContributor

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
    val recoveryContributors: List<RecoveryContributor> = emptyList(),
    val nextDayRecommendation: DailyRecommendation?,
    val workoutCompletedToday: Boolean = false
)
