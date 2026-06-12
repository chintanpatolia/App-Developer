package com.dailyhealthcoach.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
data class UserProfileEntity(
    @PrimaryKey val id: Long = 1,
    val name: String = "",
    val heightInches: Double? = null,
    val birthDate: String? = null,
    val bedtime: String? = null,
    val age: Int? = null,
    val sex: String? = null,
    val weightGoalPounds: Double? = null,
    val bodyFatGoalPercent: Double? = null,
    val stepMinTarget: Int? = null,
    val stepMaxTarget: Int? = null,
    val sleepTargetHours: Double? = null,
    val strengthTrainingDaysPerWeek: Int? = null,
    val nutritionGoal: String? = null,
    val dietPreference: String? = null,
    val createdAt: String,
    val updatedAt: String
)
