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
    val last7: List<TrendEntry>
)

data class ProgressUiState(
    val weight: TrendData?,
    val bodyFat: TrendData?,
    val sleep: TrendData?,
    val protein: TrendData?,
    val recovery: TrendData?,
    val workoutFrequency: TrendData?,
    val habitCompletion: TrendData?
)
