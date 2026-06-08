package com.dailyhealthcoach.ui.nutrition

data class NutritionUiState(
    val calories: Int = 0,
    val proteinGrams: Double = 0.0,
    val carbGrams: Double = 0.0,
    val fatGrams: Double = 0.0,
    val fiberGrams: Double = 0.0,
    val proteinGoalMin: Int = 170,
    val proteinGoalMax: Int = 200,
    val latestMealTime: String = "Not logged",
    val mealSections: List<MealSectionUiState> = emptyList(),
    val isFormVisible: Boolean = false,
    val form: FoodEntryFormUiState = FoodEntryFormUiState()
)

data class MealSectionUiState(
    val mealName: String,
    val entries: List<FoodEntryUiState>
)

data class FoodEntryUiState(
    val id: Long,
    val mealName: String,
    val foodName: String,
    val brandName: String?,
    val servingDescription: String?,
    val calories: Int,
    val proteinGrams: Double,
    val carbGrams: Double,
    val fatGrams: Double,
    val fiberGrams: Double,
    val mealTime: String?,
    val isWholeFoodBased: Boolean,
    val isProcessed: Boolean,
    val isFermented: Boolean
)

data class FoodEntryFormUiState(
    val editingId: Long = 0,
    val mealName: String = "Breakfast",
    val foodName: String = "",
    val brandName: String = "",
    val servingDescription: String = "",
    val calories: String = "",
    val proteinGrams: String = "",
    val carbGrams: String = "",
    val fatGrams: String = "",
    val fiberGrams: String = "",
    val mealTime: String = "",
    val isWholeFoodBased: Boolean = true,
    val isProcessed: Boolean = false,
    val isFermented: Boolean = false
)
