package com.dailyhealthcoach.domain.model

data class BodyMetricLog(
    val id: Long,
    val date: String,
    val bodyWeight: Double?,
    val sleepHours: Double?,
    val energyLevel: Int?,
    val stressLevel: Int?,
    val sorenessLevel: Int?,
    val restingHeartRate: Int?,
    val stepCount: Int?,
    val notes: String?
)
