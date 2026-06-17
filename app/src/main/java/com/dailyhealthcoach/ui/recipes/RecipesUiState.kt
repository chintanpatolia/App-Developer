package com.dailyhealthcoach.ui.recipes

enum class GroceryCategory { PRODUCE, DAIRY, PROTEIN, PANTRY, SPICES_OTHER }

data class GroceryItem(
    val key: String,
    val displayLine: String,
    val recipeSources: List<String>,
    val category: GroceryCategory
)

data class PlannedMealSlot(
    val date: String,
    val mealType: String,
    val recipe: Recipe?
)

data class DayMealPlanUiState(
    val date: String,
    val dayLabel: String,
    val dateNumber: Int,
    val meals: Map<String, Recipe?> = mapOf(
        "Breakfast" to null, "Lunch" to null, "Dinner" to null, "Snack" to null
    )
)

data class DraftPlanUiState(
    val weekLabel: String = "",
    val days: List<DayMealPlanUiState> = emptyList(),
    val mealPlanMode: String = "Variety"
)

data class MealCalendarUiState(
    val weekOffset: Int = 0,
    val weekLabel: String = "This Week",
    val days: List<DayMealPlanUiState> = emptyList(),
    val isGenerated: Boolean = false,
    val selectedSlot: PlannedMealSlot? = null,
    val weekGroceryListOpen: Boolean = false,
    val weekGroceryItems: List<GroceryItem> = emptyList(),
    val weekGroceryCheckedKeys: Set<String> = emptySet(),
    val mealPlanMode: String = "Variety",
    val replacementCandidates: List<Recipe> = emptyList(),
    val inlineReplaceSlot: PlannedMealSlot? = null,
    val selectedGrocerySlots: Set<String> = emptySet(),
    // Today's accepted meal plan macro totals (offset=0, today's date)
    val todayHasPlan: Boolean = false,
    val todayPlannedCalories: Int = 0,
    val todayPlannedProtein: Double = 0.0,
    val todayPlannedCarbs: Double = 0.0,
    val todayPlannedFat: Double = 0.0,
    val todayPlannedFiber: Double = 0.0
)

data class RecipesUiState(
    val remainingCalories: Int = 0,
    val remainingProtein: Double = 0.0,
    val calorieTarget: Int = 2000,
    val proteinTarget: Double = 170.0,
    val carbTarget: Int = 0,
    val fatTarget: Int = 0,
    val fiberTarget: Int = 0,
    val recommendedRecipes: List<Recipe> = emptyList(),
    val breakfastRecipes: List<Recipe> = emptyList(),
    val lunchRecipes: List<Recipe> = emptyList(),
    val dinnerRecipes: List<Recipe> = emptyList(),
    val snackRecipes: List<Recipe> = emptyList(),
    val selectedRecipe: Recipe? = null,
    val logSuccessMessage: String? = null,
    val selectedRecipeIds: Set<String> = emptySet(),
    val groceryListOpen: Boolean = false,
    val groceryItems: List<GroceryItem> = emptyList(),
    val checkedGroceryKeys: Set<String> = emptySet(),
    val noAlternateMessage: String? = null
)
