package com.dailyhealthcoach.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "body_metric_logs",
    indices = [Index(value = ["date"], unique = true)]
)
data class BodyMetricLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val heightInches: Double? = null,
    val bodyWeight: Double? = null,
    val bodyFatPercentage: Double? = null,
    val calculatedBodyFatPercent: Double? = null,
    val manualBodyFatPercent: Double? = null,
    val isBodyFatOverridden: Boolean = false,
    val waistMeasurement: Double? = null,
    val neckMeasurement: Double? = null,
    val chestMeasurement: Double? = null,
    val armMeasurement: Double? = null,
    val sleepHours: Double? = null,
    val energyLevel: Int? = null,
    val stressLevel: Int? = null,
    val sorenessLevel: Int? = null,
    val restingHeartRate: Int? = null,
    val stepCount: Int? = null,
    val notes: String? = null,
    val createdAt: String,
    val updatedAt: String
)
