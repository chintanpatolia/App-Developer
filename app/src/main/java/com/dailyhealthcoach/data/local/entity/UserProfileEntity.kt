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
    val createdAt: String,
    val updatedAt: String
)
