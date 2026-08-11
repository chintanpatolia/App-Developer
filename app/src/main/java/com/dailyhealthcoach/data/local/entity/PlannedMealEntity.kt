package com.dailyhealthcoach.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "planned_meals",
    indices = [Index(value = ["date", "slot_key"], unique = true)]
)
data class PlannedMealEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    @ColumnInfo(name = "slot_key") val slotKey: String,
    @ColumnInfo(name = "recipe_id") val recipeId: String,
    @ColumnInfo(name = "recipe_name") val recipeName: String,
    val calories: Int,
    @ColumnInfo(name = "protein_grams") val proteinGrams: Double,
    @ColumnInfo(name = "carb_grams") val carbGrams: Double,
    @ColumnInfo(name = "fat_grams") val fatGrams: Double,
    @ColumnInfo(name = "fiber_grams") val fiberGrams: Double
)
