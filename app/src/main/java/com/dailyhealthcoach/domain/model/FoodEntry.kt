package com.dailyhealthcoach.domain.model

data class FoodEntry(
    val id: Long,
    val date: String,
    val mealName: String,
    val foodName: String,
    val brandName: String?,
    val servingDescription: String?,
    val calories: Int?,
    val proteinGrams: Double?,
    val carbGrams: Double?,
    val fatGrams: Double?,
    val fiberGrams: Double?,
    val mealTime: String?,
    val isWholeFoodBased: Boolean,
    val isProcessed: Boolean,
    val isFermented: Boolean
)
