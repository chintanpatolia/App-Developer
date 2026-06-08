package com.dailyhealthcoach.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val name: String,
    val durationMinutes: Int? = null,
    val status: String,
    val overallRpe: Int? = null,
    val notes: String? = null,
    val createdAt: String,
    val updatedAt: String
)
