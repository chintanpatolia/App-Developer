package com.dailyhealthcoach.domain.model

data class FoodEntryInput(
    val id: Long = 0,
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
    val isFermented: Boolean,
    val barcode: String? = null,
    val source: String = "MANUAL"
)
