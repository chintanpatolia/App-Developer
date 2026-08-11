package com.dailyhealthcoach.domain.model

data class BodyMetricLogInput(
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
