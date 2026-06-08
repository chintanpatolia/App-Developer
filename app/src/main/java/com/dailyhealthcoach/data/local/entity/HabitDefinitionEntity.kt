package com.dailyhealthcoach.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_definitions")
data class HabitDefinitionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String,
    val frequencyType: String,
    val targetPerWeek: Int? = null,
    val reminderWindow: String? = null,
    val sortOrder: Int,
    val isActive: Boolean = true
)
