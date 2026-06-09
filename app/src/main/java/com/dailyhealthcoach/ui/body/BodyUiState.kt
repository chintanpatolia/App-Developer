package com.dailyhealthcoach.ui.body

data class BodyUiState(
    val today: BodyMetricLogUiState? = null,
    val form: BodyMetricFormUiState = BodyMetricFormUiState(),
    val recentLogs: List<BodyMetricLogUiState> = emptyList()
)

data class BodyMetricLogUiState(
    val date: String,
    val heightInches: Double?,
    val bodyWeight: Double?,
    val bodyFatPercentage: Double?,
    val calculatedBodyFatPercent: Double?,
    val manualBodyFatPercent: Double?,
    val isBodyFatOverridden: Boolean,
    val waistMeasurement: Double?,
    val neckMeasurement: Double?,
    val chestMeasurement: Double?,
    val armMeasurement: Double?,
    val sleepHours: Double?,
    val energyLevel: Int?,
    val stressLevel: Int?,
    val sorenessLevel: Int?,
    val restingHeartRate: Int?,
    val stepCount: Int?,
    val notes: String?
)

data class BodyMetricFormUiState(
    val heightFeet: String = "",
    val heightInches: String = "",
    val bodyWeight: String = "",
    val bodyFatPercentage: String = "",
    val calculatedBodyFatPercent: Double? = null,
    val isBodyFatOverridden: Boolean = false,
    val bodyFatHelperText: String = "Enter height, waist, and neck to estimate body fat.",
    val waistMeasurement: String = "",
    val neckMeasurement: String = "",
    val chestMeasurement: String = "",
    val armMeasurement: String = "",
    val sleepHours: String = "",
    val energyLevel: String = "",
    val stressLevel: String = "",
    val sorenessLevel: String = "",
    val restingHeartRate: String = "",
    val stepCount: String = "",
    val notes: String = "",
    val isDirty: Boolean = false
)
