package com.dailyhealthcoach.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "food_entries",
    indices = [Index("date")]
)
data class FoodEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val mealName: String,
    val foodName: String,
    val brandName: String? = null,
    val barcode: String? = null,
    val servingDescription: String? = null,
    val calories: Int? = null,
    val proteinGrams: Double? = null,
    val carbGrams: Double? = null,
    val fatGrams: Double? = null,
    val fiberGrams: Double? = null,
    val mealTime: String? = null,
    val isWholeFoodBased: Boolean = false,
    val isProcessed: Boolean = false,
    val isFermented: Boolean = false,
    val source: String,
    val notes: String? = null,
    val isSaved: Boolean = false,
    val createdAt: String,
    val updatedAt: String
)
