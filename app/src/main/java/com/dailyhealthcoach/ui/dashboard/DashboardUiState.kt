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
    val recoveryScore: Int? = null,
    val recoveryLabel: String = "Recovery not calculated",
    val recoveryReasons: List<String> = emptyList(),
    val recoveryContributors: List<RecoveryContributorUiState> = emptyList(),
    val nextDayRecommendation: DailyRecommendationUiState? = null,
    val workoutCompletedToday: Boolean = false,
    val caloriesToday: Int = 0,
    val calorieGoal: Int = 2000,
    val weightKg: Double? = null,
    val bodyFatPercent: Double? = null,
    val todayWorkoutName: String? = null,
    val todayWorkoutStatusLabel: String? = null,
    val healthScore: Int = 0,
    val coachLine: String? = null
)

data class RecoveryContributorUiState(
    val label: String,
    val deltaText: String,
    val detail: String,
    val isPositive: Boolean
)

data class DailyRecommendationUiState(
    val title: String,
    val explanation: String,
    val suggestedFocus: String,
    val reasons: List<String>
)

data class DashboardLayoutState(
    val cardConfigs: List<DashboardCardConfig> = DashboardPreferencesRepository.DEFAULT_LAYOUT,
    val isEditMode: Boolean = false,
    val showResetConfirm: Boolean = false
)
