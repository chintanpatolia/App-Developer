package com.dailyhealthcoach.ui.progress

data class TrendEntry(
    val date: String,
    val displayValue: String
)

data class TrendData(
    val label: String,
    val unit: String,
    val currentValue: String,
    val sevenDayAvg: String,
    val changeLabel: String,
    val changePositive: Boolean?,
    val last7: List<TrendEntry>,
    val points: List<Float> = emptyList()
)

data class WeeklyTrainingLoadUiState(
    val weekLabel: String,
    val workoutCount: Int,
    val totalSets: Int,
    val totalVolumeText: String,
    val avgRpe: String
)

enum class ProgressPeriod(val label: String, val days: Int, val displayPoints: Int) {
    WEEK("7D", 7, 7),
    MONTH("1M", 30, 30),
    THREE_MONTHS("3M", 90, 90),
    SIX_MONTHS("6M", 180, 90),
    YEAR("1Y", 365, 90)
}

data class ProgressUiState(
    val weight: TrendData?,
    val bodyFat: TrendData?,
    val sleep: TrendData?,
    val protein: TrendData?,
    val steps: TrendData?,
    val recovery: TrendData?,
    val workoutFrequency: TrendData?,
    val habitCompletion: TrendData?,
    val strengthTrends: List<TrendData> = emptyList(),
    val weeklyLoad: WeeklyTrainingLoadUiState? = null,
    val selectedPeriod: ProgressPeriod = ProgressPeriod.WEEK
)
