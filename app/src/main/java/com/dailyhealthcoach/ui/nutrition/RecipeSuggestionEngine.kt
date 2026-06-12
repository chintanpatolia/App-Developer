package com.dailyhealthcoach.ui.nutrition

import com.dailyhealthcoach.domain.model.MacroTarget

object RecipeSuggestionEngine {

    private val MEALS = listOf("Breakfast", "Lunch", "Dinner", "Snack")

    fun suggest(
        savedFoods: List<QuickAddFoodUiState>,
        recentFoods: List<QuickAddFoodUiState>,
        consumedCalories: Int,
        consumedProtein: Double,
        macroTarget: MacroTarget?,
        goal: String?,
        dietPreference: String?
    ): List<RecipeSuggestionUiState> {
        val calorieTarget = macroTarget?.calorieTarget ?: 2000
        val proteinTarget = macroTarget?.proteinMaxGrams?.toDouble() ?: 170.0
        val remainCalories = calorieTarget - consumedCalories
        val remainProtein = proteinTarget - consumedProtein

        // Favorites first, then recents; deduplicated by name
        val pool = (savedFoods + recentFoods)
            .distinctBy { it.foodName.lowercase().trim() }

        if (pool.isEmpty()) return emptyList()

        // Apply diet filter; fall back to full pool if nothing passes
        val filtered = applyDietFilter(pool, dietPreference).ifEmpty { pool }

        fun score(f: QuickAddFoodUiState): Double {
            val cal = f.calories.coerceAtLeast(1).toDouble()
            val protein = f.proteinGrams
            return when {
                remainProtein > 30.0 -> protein / cal * 100.0
                remainCalories < 200 -> -cal
                goal.equals("Lose Fat", ignoreCase = true) ->
                    protein / cal * 80.0 - cal * 0.01
                goal.equals("Gain Muscle", ignoreCase = true) ->
                    protein * 2.0 - cal * 0.005
                else -> protein - cal * 0.01
            }
        }

        return MEALS.map { mealName ->
            val mealMatch = filtered.filter {
                it.defaultMealName.equals(mealName, ignoreCase = true)
            }
            val candidates = if (mealMatch.isNotEmpty()) mealMatch else filtered
            val best = candidates.maxByOrNull { score(it) } ?: filtered.first()
            RecipeSuggestionUiState(
                mealName = mealName,
                food = best.copy(defaultMealName = mealName)
            )
        }
    }

    private fun applyDietFilter(
        pool: List<QuickAddFoodUiState>,
        dietPreference: String?
    ): List<QuickAddFoodUiState> = when (dietPreference?.lowercase()?.trim()) {
        "whole foods" -> pool.filter { it.isWholeFoodBased }
        "no processed" -> pool.filter { !it.isProcessed }
        else -> pool
    }
}
