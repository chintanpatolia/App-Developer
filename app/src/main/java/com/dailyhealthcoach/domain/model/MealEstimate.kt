package com.dailyhealthcoach.domain.model

data class ParsedFoodItem(
    val foodName: String,
    val quantity: Double,
    val unit: String?,
    val confidence: String
)

data class MealEstimate(
    val mealName: String,
    val calories: Int,
    val proteinGrams: Double,
    val carbGrams: Double,
    val fatGrams: Double,
    val fiberGrams: Double,
    val confidence: String,
    val notes: String,
    val isWholeFoodBased: Boolean = true,
    val parsedItems: List<ParsedFoodItem> = emptyList(),
    val estimatedGlucoseResponse: String = "Unknown"
)
