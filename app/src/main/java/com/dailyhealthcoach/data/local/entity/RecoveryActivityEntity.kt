package com.dailyhealthcoach.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recovery_activity_logs")
data class RecoveryActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "workout_id") val workoutId: Long,
    @ColumnInfo(name = "activity_name") val activityName: String,
    val status: String = "COMPLETED",
    @ColumnInfo(name = "duration_minutes") val durationMinutes: Int? = null,
    val rpe: Int? = null,
    val notes: String? = null
)
