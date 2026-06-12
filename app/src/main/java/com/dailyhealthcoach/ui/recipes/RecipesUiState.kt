package com.dailyhealthcoach.ui.recipes

data class RecipesUiState(
    val remainingCalories: Int = 0,
    val remainingProtein: Double = 0.0,
    val calorieTarget: Int = 2000,
    val proteinTarget: Double = 170.0,
    val recommendedRecipes: List<Recipe> = emptyList(),
    val breakfastRecipes: List<Recipe> = emptyList(),
    val lunchRecipes: List<Recipe> = emptyList(),
    val dinnerRecipes: List<Recipe> = emptyList(),
    val snackRecipes: List<Recipe> = emptyList(),
    val selectedRecipe: Recipe? = null,
    val logSuccessMessage: String? = null
)
