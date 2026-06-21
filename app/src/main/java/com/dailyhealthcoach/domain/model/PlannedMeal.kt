package com.dailyhealthcoach.domain.model

data class PlannedMeal(
    val id: Long = 0,
    val date: String,
    val slotKey: String,
    val recipeId: String,
    val recipeName: String,
    val calories: Int,
    val proteinGrams: Double,
    val carbGrams: Double,
    val fatGrams: Double,
    val fiberGrams: Double
)
