package com.dailyhealthcoach.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "daily_habit_logs",
    foreignKeys = [
        ForeignKey(
            entity = HabitDefinitionEntity::class,
            parentColumns = ["id"],
            childColumns = ["habitDefinitionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("habitDefinitionId"),
        Index(value = ["habitDefinitionId", "date"], unique = true)
    ]
)
data class DailyHabitLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val habitDefinitionId: Long,
    val date: String,
    val status: String,
    val notes: String? = null,
    val updatedAt: String
)
