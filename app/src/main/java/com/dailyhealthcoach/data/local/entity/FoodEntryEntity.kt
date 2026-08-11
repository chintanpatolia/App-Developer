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
    val quantity: Double = 1.0,
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
    val updatedAt: String,
    // vitamins in mcg: vitaminA, vitaminD, vitaminK, vitaminB12, folate, biotin, iodine, selenium, chromium, molybdenum
    // vitamins in mg:  vitaminC, vitaminE, vitaminB1-B3, B5, B6, and all minerals below
    val vitaminA: Double? = null,
    val vitaminC: Double? = null,
    val vitaminD: Double? = null,
    val vitaminB12: Double? = null,
    val calcium: Double? = null,
    val iron: Double? = null,
    val potassium: Double? = null,
    val magnesium: Double? = null,
    val sodium: Double? = null,
    val zinc: Double? = null,
    val vitaminE: Double? = null,
    val vitaminK: Double? = null,
    val vitaminB1: Double? = null,
    val vitaminB2: Double? = null,
    val vitaminB3: Double? = null,
    val vitaminB6: Double? = null,
    val folate: Double? = null,
    val biotin: Double? = null,
    val pantothenicAcid: Double? = null,
    val phosphorus: Double? = null,
    val iodine: Double? = null,
    val selenium: Double? = null,
    val copper: Double? = null,
    val manganese: Double? = null,
    val chromium: Double? = null,
    val molybdenum: Double? = null
)
