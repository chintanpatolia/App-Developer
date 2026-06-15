package com.dailyhealthcoach.ui.recipes

import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

object MealPlanEngine {

    private val MEAL_TYPES = listOf("Breakfast", "Lunch", "Dinner", "Snack")

    fun generateWeek(
        allRecipes: List<Recipe>,
        weekStart: LocalDate,
        nutritionGoal: String?,
        dietPreference: String?
    ): List<DayMealPlanUiState> {
        val filtered = filterByDiet(allRecipes, dietPreference)
        val pools: Map<String, ArrayDeque<Recipe>> = MEAL_TYPES.associateWith { mealType ->
            buildPool(filtered.filter { it.mealType == mealType }, nutritionGoal)
        }

        return (0..6).map { offset ->
            val day = weekStart.plusDays(offset.toLong())
            val dateStr = day.toString()
            val dayLabel = day.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
            val meals: Map<String, Recipe?> = MEAL_TYPES.associateWith { mealType ->
                pools[mealType]?.removeFirstOrNull()
            }
            DayMealPlanUiState(
                date = dateStr,
                dayLabel = dayLabel,
                dateNumber = day.dayOfMonth,
                meals = meals
            )
        }
    }

    // Pick one recipe per meal type and apply it to every day — ideal for meal prep.
    fun generateWeekRepeat(
        allRecipes: List<Recipe>,
        weekStart: LocalDate,
        nutritionGoal: String?,
        dietPreference: String?
    ): List<DayMealPlanUiState> {
        val filtered = filterByDiet(allRecipes, dietPreference)
        val chosenMeals: Map<String, Recipe?> = MEAL_TYPES.associateWith { mealType ->
            rankRecipes(filtered.filter { it.mealType == mealType }, nutritionGoal).firstOrNull()
        }
        return (0..6).map { offset ->
            val day = weekStart.plusDays(offset.toLong())
            DayMealPlanUiState(
                date = day.toString(),
                dayLabel = day.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                dateNumber = day.dayOfMonth,
                meals = chosenMeals
            )
        }
    }

    // Regenerate a single meal slot, avoiding already-used recipes in the week
    fun pickReplacement(
        allRecipes: List<Recipe>,
        mealType: String,
        usedIds: Set<String>,
        nutritionGoal: String?,
        dietPreference: String?
    ): Recipe? {
        val filtered = filterByDiet(allRecipes, dietPreference)
            .filter { it.mealType == mealType && it.id !in usedIds }
            .ifEmpty { filterByDiet(allRecipes, dietPreference).filter { it.mealType == mealType } }
        if (filtered.isEmpty()) return null
        return rankRecipes(filtered, nutritionGoal).firstOrNull()
    }

    private fun buildPool(candidates: List<Recipe>, nutritionGoal: String?): ArrayDeque<Recipe> {
        val pool = ArrayDeque<Recipe>()
        if (candidates.isEmpty()) return pool
        val ranked = rankRecipes(candidates, nutritionGoal)
        // Fill 7 slots, cycling through the ranked list so we get variety
        repeat(7) { i -> pool.add(ranked[i % ranked.size]) }
        return pool
    }

    private fun rankRecipes(recipes: List<Recipe>, nutritionGoal: String?): List<Recipe> {
        val goal = nutritionGoal?.lowercase().orEmpty()
        val scored = recipes.map { r ->
            var score = r.proteinGrams / r.calories.coerceAtLeast(1).toDouble() * 100.0
            when {
                goal.contains("metabolic") || goal.contains("prediabetes") ||
                goal.contains("insulin") || goal.contains("blood sugar") || goal.contains("glucose") -> {
                    if ("Metabolic Reset" in r.collection) score += 20.0
                    if (r.isGlucoseConscious) score += 8.0
                    if ((r.fiberGrams ?: 0.0) >= 8.0) score += 4.0
                    if (r.metabolicResetScore >= 7) score += 5.0
                }
                goal.contains("inflam") -> {
                    if ("Anti-Inflammatory" in r.collection) score += 20.0
                    if (r.antiInflammatoryScore >= 7) score += 8.0
                }
                goal.contains("muscle") || goal.contains("gain") -> {
                    score += r.proteinGrams * 0.8
                }
                goal.contains("lose") || goal.contains("fat") || goal.contains("weight") -> {
                    score += r.proteinGrams / r.calories.coerceAtLeast(1).toDouble() * 60.0
                    if (r.calories < 400) score += 5.0
                }
                else -> {
                    // Balanced: reward both collections equally
                    if (r.collection.isNotEmpty()) score += 5.0
                }
            }
            r to score
        }
        // Sort descending, then within each tier shuffle to prevent the same top recipe every time
        val sorted = scored.sortedByDescending { it.second }
        val tierSize = (sorted.size / 3).coerceAtLeast(1)
        return sorted.take(tierSize).map { it.first }.shuffled() +
               sorted.drop(tierSize).take(tierSize).map { it.first }.shuffled() +
               sorted.drop(tierSize * 2).map { it.first }.shuffled()
    }

    private fun filterByDiet(recipes: List<Recipe>, dietPreference: String?): List<Recipe> {
        if (dietPreference.isNullOrBlank() || dietPreference.equals("No Restriction", ignoreCase = true)) return recipes
        val pref = dietPreference.lowercase().trim()
        val filtered = recipes.filter { r ->
            r.tags.any { tag ->
                when (pref) {
                    "vegetarian" -> tag.equals("Vegetarian", ignoreCase = true) || tag.equals("Vegan", ignoreCase = true)
                    "vegan" -> tag.equals("Vegan", ignoreCase = true)
                    "whole foods" -> tag.equals("Whole Foods", ignoreCase = true)
                    else -> tag.lowercase().contains(pref)
                }
            }
        }
        return filtered.ifEmpty { recipes }
    }
}
