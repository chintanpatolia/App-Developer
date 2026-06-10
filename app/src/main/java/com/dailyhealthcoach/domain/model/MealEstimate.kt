package com.dailyhealthcoach.domain.model

data class MealEstimate(
    val mealName: String,
    val calories: Int,
    val proteinGrams: Double,
    val carbGrams: Double,
    val fatGrams: Double,
    val fiberGrams: Double,
    val confidence: String,
    val notes: String,
    val isWholeFoodBased: Boolean = true
)
