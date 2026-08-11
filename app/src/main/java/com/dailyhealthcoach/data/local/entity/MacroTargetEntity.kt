package com.dailyhealthcoach.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "macro_targets")
data class MacroTargetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val proteinMinGrams: Int,
    val proteinMaxGrams: Int,
    val calorieTarget: Int? = null,
    val carbTargetGrams: Int? = null,
    val fatTargetGrams: Int? = null,
    val fiberTargetGrams: Int? = null,
    val isActive: Boolean = true,
    val createdAt: String,
    val updatedAt: String
)
