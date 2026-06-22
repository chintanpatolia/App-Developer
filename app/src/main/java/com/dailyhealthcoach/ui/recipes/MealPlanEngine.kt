package com.dailyhealthcoach.ui.recipes

import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

object MealPlanEngine {

    fun generateWeek(
        allRecipes: List<Recipe>,
        weekStart: LocalDate,
        nutritionGoal: String?,
        dietPreferences: List<String> = emptyList(),
        foodRestrictions: List<String> = emptyList(),
        proteinTargetGrams: Int = 0,
        calorieTarget: Int = 0
    ): List<DayMealPlanUiState> {
        val filtered = applyFilters(allRecipes, dietPreferences, foodRestrictions)
        val preferredSources = preferredSourcesFor(dietPreferences)
        val nonSnackTypes = listOf("Breakfast", "Lunch", "Dinner")
        val pools: Map<String, ArrayDeque<Recipe>> = nonSnackTypes.associateWith { mealType ->
            buildPool(filtered.filter { it.mealType == mealType }, nutritionGoal, proteinTargetGrams, calorieTarget, preferredSources)
        }
        val rankedSnacks = rankRecipes(filtered.filter { it.mealType == "Snack" }, nutritionGoal, proteinTargetGrams, calorieTarget)
        val snackPool = buildPool(filtered.filter { it.mealType == "Snack" }, nutritionGoal, proteinTargetGrams, calorieTarget, preferredSources)

        return (0..6).map { offset ->
            val day = weekStart.plusDays(offset.toLong())
            val breakfast = pools["Breakfast"]?.removeFirstOrNull()
            val lunch = pools["Lunch"]?.removeFirstOrNull()
            val dinner = pools["Dinner"]?.removeFirstOrNull()
            val snack = if (proteinTargetGrams > 0 && rankedSnacks.isNotEmpty()) {
                val proteinSoFar = (breakfast?.proteinGrams ?: 0.0) +
                        (lunch?.proteinGrams ?: 0.0) + (dinner?.proteinGrams ?: 0.0)
                val gap = (proteinTargetGrams - proteinSoFar).coerceAtLeast(0.0)
                pickSnackForGap(rankedSnacks, gap)
            } else {
                snackPool.removeFirstOrNull()
            }
            val totalProtein = (breakfast?.proteinGrams ?: 0.0) + (lunch?.proteinGrams ?: 0.0) +
                               (dinner?.proteinGrams ?: 0.0) + (snack?.proteinGrams ?: 0.0)
            val initialGap = if (proteinTargetGrams > 0) (proteinTargetGrams - totalProtein).coerceAtLeast(0.0) else 0.0
            val usedIds = mutableSetOf(*setOfNotNull(breakfast?.id, lunch?.id, dinner?.id, snack?.id).toTypedArray())
            val boosters = addBoosters(filtered, usedIds, initialGap)
            DayMealPlanUiState(
                date = day.toString(),
                dayLabel = day.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                dateNumber = day.dayOfMonth,
                meals = buildMap {
                    put("Breakfast", breakfast); put("Lunch", lunch)
                    put("Dinner", dinner); put("Snack", snack)
                    boosters.forEachIndexed { i, b -> put("Protein Booster ${i + 1}", b) }
                }
            )
        }
    }

    fun generateWeekRepeat(
        allRecipes: List<Recipe>,
        weekStart: LocalDate,
        nutritionGoal: String?,
        dietPreferences: List<String> = emptyList(),
        foodRestrictions: List<String> = emptyList(),
        proteinTargetGrams: Int = 0,
        calorieTarget: Int = 0
    ): List<DayMealPlanUiState> {
        val filtered = applyFilters(allRecipes, dietPreferences, foodRestrictions)
        val preferredSources = preferredSourcesFor(dietPreferences)
        val nonSnackTypes = listOf("Breakfast", "Lunch", "Dinner")
        val chosenNonSnacks: Map<String, Recipe?> = nonSnackTypes.associateWith { mealType ->
            val ranked = rankRecipes(filtered.filter { it.mealType == mealType }, nutritionGoal, proteinTargetGrams, calorieTarget)
            ranked.firstOrNull { it.proteinSource in preferredSources } ?: ranked.firstOrNull()
        }
        val rankedSnacks = rankRecipes(filtered.filter { it.mealType == "Snack" }, nutritionGoal, proteinTargetGrams, calorieTarget)
        val chosenSnack: Recipe? = if (proteinTargetGrams > 0 && rankedSnacks.isNotEmpty()) {
            val proteinSoFar = nonSnackTypes.sumOf { chosenNonSnacks[it]?.proteinGrams ?: 0.0 }
            val gap = (proteinTargetGrams - proteinSoFar).coerceAtLeast(0.0)
            pickSnackForGap(rankedSnacks, gap)
        } else {
            rankedSnacks.firstOrNull { it.proteinSource in preferredSources } ?: rankedSnacks.firstOrNull()
        }
        val totalProtein = nonSnackTypes.sumOf { chosenNonSnacks[it]?.proteinGrams ?: 0.0 } +
                           (chosenSnack?.proteinGrams ?: 0.0)
        val initialGap = if (proteinTargetGrams > 0) (proteinTargetGrams - totalProtein).coerceAtLeast(0.0) else 0.0
        val usedIds = (chosenNonSnacks.values.filterNotNull() + listOfNotNull(chosenSnack)).map { it.id }.toMutableSet()
        val boosters = addBoosters(filtered, usedIds, initialGap)
        val chosenMeals = buildMap {
            putAll(chosenNonSnacks)
            put("Snack", chosenSnack)
            boosters.forEachIndexed { i, b -> put("Protein Booster ${i + 1}", b) }
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

    fun pickReplacement(
        allRecipes: List<Recipe>,
        mealType: String,
        usedIds: Set<String>,
        nutritionGoal: String?,
        dietPreferences: List<String> = emptyList(),
        foodRestrictions: List<String> = emptyList(),
        proteinTargetGrams: Int = 0,
        remainingProteinGap: Double = 0.0,
        calorieTarget: Int = 0
    ): Recipe? {
        val pool = applyFilters(allRecipes, dietPreferences, foodRestrictions)
            .filter { it.mealType == mealType && it.id !in usedIds }
            .ifEmpty { applyFilters(allRecipes, dietPreferences, foodRestrictions).filter { it.mealType == mealType } }
        if (pool.isEmpty()) return null
        // For snack replacements, prefer the recipe closest to the remaining protein gap
        if (mealType == "Snack" && remainingProteinGap > 0.0) {
            return pool.minByOrNull { kotlin.math.abs(it.proteinGrams - remainingProteinGap) }
        }
        val preferredSources = preferredSourcesFor(dietPreferences)
        val ranked = rankRecipes(pool, nutritionGoal, proteinTargetGrams, calorieTarget)
        return ranked.firstOrNull { it.proteinSource in preferredSources } ?: ranked.firstOrNull()
    }

    fun applyFoodRestrictions(recipes: List<Recipe>, foodRestrictions: List<String>): List<Recipe> =
        filterByRestrictions(recipes, foodRestrictions)

    // ── Private helpers ──────────────────────────────────────────────────────

    private fun applyFilters(
        recipes: List<Recipe>,
        dietPreferences: List<String>,
        foodRestrictions: List<String>
    ): List<Recipe> = filterByRestrictions(filterByDiet(recipes, dietPreferences), foodRestrictions)

    private fun buildPool(
        candidates: List<Recipe>,
        nutritionGoal: String?,
        proteinTargetGrams: Int,
        calorieTarget: Int = 0,
        preferredSources: Set<String> = emptySet()
    ): ArrayDeque<Recipe> {
        val pool = ArrayDeque<Recipe>()
        if (candidates.isEmpty()) return pool
        val ranked = rankRecipes(candidates, nutritionGoal, proteinTargetGrams, calorieTarget)
        if (preferredSources.isEmpty()) {
            // ponytail: random shift 0–2 preserves variety on Regenerate; all 7 slots from top-ranked slice
            val shift = (0 until minOf(3, ranked.size)).random()
            repeat(7) { i -> pool.add(ranked[(i + shift) % ranked.size]) }
            return pool
        }
        val preferred = ranked.filter { it.proteinSource in preferredSources }
        val others = ranked.filter { it.proteinSource !in preferredSources }
        val quota = minOf(preferred.size, 3)
        if (quota == 0) {
            val shift = (0 until minOf(3, ranked.size)).random()
            repeat(7) { i -> pool.add(ranked[(i + shift) % ranked.size]) }
            return pool
        }
        // Build preferred slice (top quota items) and others slice (remaining 7 - quota slots)
        val prefSlice = (0 until quota).map { i -> preferred[i % preferred.size] }
        val othersSource = if (others.isEmpty()) ranked else others
        val shift = (0 until minOf(3, othersSource.size)).random()
        val otherSlice = (0 until (7 - quota)).map { i -> othersSource[(i + shift) % othersSource.size] }
        // Interleave: space preferred recipes evenly across the 7-day week
        val result = ArrayList<Recipe>(7)
        val prefDeque = ArrayDeque(prefSlice)
        val othDeque = ArrayDeque(otherSlice)
        val interval = (7.0 / quota).toInt().coerceAtLeast(1)
        var prefInserted = 0
        for (i in 0 until 7) {
            if (prefDeque.isNotEmpty() && prefInserted < quota && i % interval == 0) {
                result.add(prefDeque.removeFirst())
                prefInserted++
            } else if (othDeque.isNotEmpty()) {
                result.add(othDeque.removeFirst())
            } else if (prefDeque.isNotEmpty()) {
                result.add(prefDeque.removeFirst())
                prefInserted++
            }
        }
        while (result.size < 7) result.add(ranked[result.size % ranked.size])
        pool.addAll(result)
        return pool
    }

    private fun pickSnackForGap(rankedSnacks: List<Recipe>, proteinGap: Double): Recipe? {
        if (proteinGap <= 0.0) return rankedSnacks.firstOrNull()
        return rankedSnacks.minByOrNull { kotlin.math.abs(it.proteinGrams - proteinGap) }
    }

    private fun addBoosters(
        filtered: List<Recipe>,
        usedIds: MutableSet<String>,
        initialGap: Double,
        limit: Int = 2
    ): List<Recipe> {
        val result = mutableListOf<Recipe>()
        var gap = initialGap
        while (gap > 15.0 && result.size < limit) {
            val booster = pickBooster(filtered.filter { it.id !in usedIds }, gap) ?: break
            result.add(booster)
            usedIds.add(booster.id)
            gap -= booster.proteinGrams
        }
        return result
    }

    private fun pickBooster(candidates: List<Recipe>, targetGrams: Double): Recipe? {
        val boosters = candidates.filter { it.mealType == "Protein Booster" }
        return boosters.minByOrNull { kotlin.math.abs(it.proteinGrams - targetGrams) }
    }

    private fun rankRecipes(
        recipes: List<Recipe>,
        nutritionGoal: String?,
        proteinTargetGrams: Int = 0,
        calorieTarget: Int = 0
    ): List<Recipe> {
        val mealProteinTarget = if (proteinTargetGrams > 0) proteinTargetGrams / 4.0 else 0.0
        val calPerMeal = if (calorieTarget > 0) calorieTarget / 4.0 else 0.0
        val goal = nutritionGoal?.lowercase().orEmpty()
        val scored = recipes.map { r ->
            var score = if (mealProteinTarget > 0)
                r.proteinGrams * 1.5 - kotlin.math.abs(r.proteinGrams - mealProteinTarget) * 0.3
            else
                r.proteinGrams / r.calories.coerceAtLeast(1).toDouble() * 100.0
            // ponytail: soft calorie penalty only when >20% over per-meal share
            if (calPerMeal > 0) {
                val overBy = (r.calories - calPerMeal * 1.2).coerceAtLeast(0.0)
                score -= overBy * 0.005
            }
            when {
                goal.contains("metabolic") || goal.contains("blood sugar") || goal.contains("glucose") -> {
                    if ("Metabolic Reset" in r.collection) score += 20.0
                    if (r.metabolicResetScore >= 7) score += 5.0
                    if ((r.fiberGrams ?: 0.0) >= 8.0) score += 4.0
                    score += (10 - r.glucoseImpactScore) * 1.5
                }
                goal.contains("insulin") || goal.contains("prediabetes") -> {
                    if ("Metabolic Reset" in r.collection) score += 15.0
                    if (r.insulinResistanceScore >= 7) score += 8.0
                    if ((r.fiberGrams ?: 0.0) >= 8.0) score += 4.0
                    score += (10 - r.glucoseImpactScore) * 2.0
                }
                goal.contains("inflam") -> {
                    if ("Anti-Inflammatory" in r.collection) score += 20.0
                    if (r.antiInflammatoryScore >= 7) score += 8.0
                }
                goal.contains("women") || goal.contains("hormonal") || goal.contains("pcos") -> {
                    if (r.womensHealthScore >= 7) score += 15.0
                    if ("Anti-Inflammatory" in r.collection) score += 8.0
                    if ((r.fiberGrams ?: 0.0) >= 8.0) score += 3.0
                }
                goal.contains("muscle") || goal.contains("gain") -> score += r.proteinGrams * 0.8
                goal.contains("lose") || goal.contains("fat") || goal.contains("weight") -> {
                    score += r.proteinGrams / r.calories.coerceAtLeast(1).toDouble() * 60.0
                    if (r.calories < 400) score += 5.0
                }
                else -> if (r.collection.isNotEmpty()) score += 5.0
            }
            r to score
        }
        // ponytail: deterministic sort; buildPool adds variety via random start-index shift
        return scored.sortedByDescending { it.second }.map { it.first }
    }

    private fun filterByDiet(recipes: List<Recipe>, dietPreferences: List<String>): List<Recipe> {
        if (dietPreferences.isEmpty()) return recipes
        // Balanced / Custom mean no diet restriction
        if (dietPreferences.any { it.equals("Balanced", ignoreCase = true) || it.equals("Custom", ignoreCase = true) }) return recipes
        val filtered = recipes.filter { r -> dietPreferences.any { pref -> matchesDiet(r, pref) } }
        return filtered.ifEmpty { recipes } // never return empty — fall back gracefully
    }

    private fun matchesDiet(recipe: Recipe, pref: String): Boolean {
        val tags = recipe.tags.map { it.lowercase() }
        val isVegan = "vegan" in tags
        val isVegetarian = "vegetarian" in tags || isVegan
        return when (pref.lowercase().trim()) {
            "vegan" -> isVegan
            "vegetarian", "lacto vegetarian", "high protein vegetarian" -> isVegetarian
            // Ovo vegetarian: eggs OK, dairy excluded — accept vegan or dairy-free vegetarian
            "ovo vegetarian" -> isVegan || (isVegetarian && "Dairy Free" in recipe.restrictions)
            // Pescatarian: fish OK — all our plant-based recipes qualify
            "pescatarian" -> isVegetarian || "pescatarian" in tags
            "mediterranean" -> isVegetarian || "whole foods" in tags || "mediterranean" in tags
            // Meat-inclusive diets: our catalog is plant-based only; all recipes suit these
            "omnivore",
            "meat & poultry", "meat/poultry",
            "chicken & fish", "chicken/fish",
            "poultry", "flexitarian" -> true
            else -> true // Custom, unknown → no filter
        }
    }

    private fun filterByRestrictions(recipes: List<Recipe>, restrictions: List<String>): List<Recipe> {
        if (restrictions.isEmpty()) return recipes
        return recipes.filter { r -> restrictions.none { violatesRestriction(r, it) } }
    }

    private fun violatesRestriction(recipe: Recipe, restriction: String): Boolean {
        val key = restriction.trim().lowercase()
        // Only filter on restriction types explicitly tagged in recipe.restrictions.
        // Unsupported types (e.g. "Low Sodium") pass through rather than blocking all recipes.
        if (key !in SUPPORTED_RESTRICTIONS) return false
        return recipe.restrictions.none { it.trim().lowercase() == key }
    }

    private val SUPPORTED_RESTRICTIONS = setOf(
        "dairy free", "gluten free", "nut free", "soy free", "egg free"
    )

    private fun preferredSourcesFor(dietPreferences: List<String>): Set<String> {
        val prefs = dietPreferences.map { it.lowercase().trim() }
        return when {
            "pescatarian" in prefs -> setOf("Fish")
            prefs.any { it == "chicken & fish" || it == "chicken/fish" } -> setOf("Fish", "Chicken")
            prefs.any { it == "meat & poultry" || it == "meat/poultry" } -> setOf("Chicken", "Turkey", "Beef", "Lamb", "Pork")
            "poultry" in prefs -> setOf("Chicken", "Turkey", "Duck")
            prefs.any { it == "omnivore" || it == "flexitarian" } -> setOf("Fish", "Chicken", "Turkey", "Beef", "Eggs")
            else -> emptySet()
        }
    }
}
