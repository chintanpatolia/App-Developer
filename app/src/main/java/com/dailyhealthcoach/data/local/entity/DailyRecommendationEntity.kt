package com.dailyhealthcoach.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "daily_recommendations",
    indices = [Index(value = ["date"], unique = true)]
)
data class DailyRecommendationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val recommendationType: String,
    val targetMuscleGroups: String? = null,
    val intensity: String? = null,
    val loadGuidance: String? = null,
    val reasonSummary: String,
    val createdAt: String
)
