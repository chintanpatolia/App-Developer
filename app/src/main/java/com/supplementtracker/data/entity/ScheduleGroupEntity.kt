package com.supplementtracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule_groups")
data class ScheduleGroupEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,         // "MORNING", "LUNCH", "EVENING", "BEDTIME", "CUSTOM"
    val label: String,        // "Morning", "Lunch", "Evening", "Bedtime", "Custom"
    val reminderHour: Int,
    val reminderMinute: Int,
    val groupNote: String = "",
    val sortOrder: Int = 0
)
