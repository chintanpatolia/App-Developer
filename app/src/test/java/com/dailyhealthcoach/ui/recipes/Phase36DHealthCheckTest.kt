package com.dailyhealthcoach.ui.recipes

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

/**
 * Phase 36D catalog health check smoke tests.
 * Tests diet preference coverage, restriction filtering, and plan completeness.
 * READ-ONLY audit — no catalog or engine changes should be needed to pass these.
 */
class Phase36DHealthCheckTest {

    private val monday = LocalDate.of(2026, 6, 22)
    private val all = RecipeCatalog.ALL

    // ── Helpers ──────────────────────────────────────────────────────────────

    private fun generateVariety(dietPref: String, restrictions: List<String> = emptyList()) =
        MealPlanEngine.generateWeek(
            allRecipes = all,
            weekStart = monday,
            nutritionGoal = null,
            dietPreferences = listOf(dietPref),
            foodRestrictions = restrictions
        )

    private fun generateRepeat(dietPref: String) =
        MealPlanEngine.generateWeekRepeat(
            allRecipes = all,
            weekStart = monday,
            nutritionGoal = null,
            dietPreferences = listOf(dietPref)
        )

    private fun allRecipes(days: List<DayMealPlanUiState>): List<Recipe> =
        days.flatMap { day -> day.meals.values.filterNotNull() }

    private fun proteinSources(days: List<DayMealPlanUiState>): Set<String> =
        allRecipes(days).map { it.proteinSource }.filter { it.isNotBlank() }.toSet()

    private fun tags(days: List<DayMealPlanUiState>): Set<String> =
        allRecipes(days).flatMap { it.tags }.toSet()

    private fun mealCount(days: List<DayMealPlanUiState>): Int =
        days.sumOf { it.meals.values.count { r -> r != null } }

    // ── 1. Total catalog sanity ───────────────────────────────────────────

    @Test
    fun `catalog has 234 recipes`() {
        assertTrue("Expected 234, got ${all.size}", all.size == 234)
    }

    @Test
    fun `no duplicate recipe IDs`() {
        val ids = all.map { it.id }
        val dupes = ids.groupBy { it }.filter { it.value.size > 1 }.keys
        assertTrue("Duplicate IDs found: $dupes", dupes.isEmpty())
    }

    @Test
    fun `no duplicate recipe names`() {
        val names = all.map { it.name }
        val dupes = names.groupBy { it }.filter { it.value.size > 1 }.keys
        assertTrue("Duplicate names found: $dupes", dupes.isEmpty())
    }

    @Test
    fun `all recipes have non-blank IDs and names`() {
        val bad = all.filter { it.id.isBlank() || it.name.isBlank() }
        assertTrue("Recipes with blank ID or name: ${bad.map { it.id }}", bad.isEmpty())
    }

    @Test
    fun `all recipes have valid proteinSource`() {
        val valid = setOf(
            "Soy", "Lentils", "Paneer", "Tofu", "Cottage Cheese", "Greek Yogurt",
            "Eggs", "Fish", "Chicken", "Turkey", "Beef", "Seeds", "Nuts",
            "Pea Protein", "Protein Powder", "Tempeh", "Legumes", "Dairy", ""
        )
        val invalid = all.filter { it.proteinSource !in valid }
        assertTrue("Recipes with unexpected proteinSource: ${invalid.map { it.id to it.proteinSource }}",
            invalid.isEmpty())
    }

    // ── 2. mealType distribution sanity ─────────────────────────────────

    @Test
    fun `each mealType has at least 10 recipes`() {
        val types = listOf("Breakfast", "Lunch", "Dinner", "Snack", "Protein Booster")
        types.forEach { type ->
            val count = all.count { it.mealType == type }
            assertTrue("mealType '$type' has only $count recipes (expected >= 10)", count >= 10)
        }
    }

    // ── 3. Macro sanity ──────────────────────────────────────────────────

    @Test
    fun `no recipe has suspiciously low calories`() {
        val bad = all.filter { it.calories < 100 }
        assertTrue("Recipes with calories < 100: ${bad.map { it.id to it.calories }}", bad.isEmpty())
    }

    @Test
    fun `no recipe has impossibly high calories`() {
        val bad = all.filter { it.calories > 800 }
        assertTrue("Recipes with calories > 800: ${bad.map { it.id to it.calories }}", bad.isEmpty())
    }

    @Test
    fun `no recipe has impossibly high protein`() {
        val bad = all.filter { it.proteinGrams > 80.0 }
        assertTrue("Recipes with protein > 80g: ${bad.map { it.id to it.proteinGrams }}", bad.isEmpty())
    }

    @Test
    fun `all scores are within 1-10 range`() {
        val bad = all.filter { r ->
            r.metabolicResetScore !in 1..10 ||
            r.antiInflammatoryScore !in 1..10 ||
            r.insulinResistanceScore !in 1..10 ||
            r.womensHealthScore !in 1..10 ||
            r.glucoseImpactScore !in 1..10
        }
        assertTrue("Recipes with out-of-range scores: ${bad.map { it.id }}", bad.isEmpty())
    }

    // ── 4. Diet preference coverage smoke tests ─────────────────────────

    @Test
    fun `High Protein Vegetarian plan contains only vegetarian recipes`() {
        val days = generateVariety("High Protein Vegetarian")
        val nonVeg = allRecipes(days).filter { r ->
            r.proteinSource in setOf("Chicken", "Turkey", "Beef", "Fish", "Pork", "Lamb")
        }
        assertTrue("High Protein Vegetarian plan contains non-veg recipes: ${nonVeg.map { it.id }}",
            nonVeg.isEmpty())
        assertTrue("High Protein Vegetarian plan is empty", mealCount(days) > 0)
    }

    @Test
    fun `Pescatarian plan contains only vegetarian or fish recipes`() {
        val days = generateVariety("Pescatarian")
        val meatRecipes = allRecipes(days).filter { r ->
            r.proteinSource in setOf("Chicken", "Turkey", "Beef", "Pork", "Lamb")
        }
        assertTrue("Pescatarian plan contains land-meat recipes: ${meatRecipes.map { it.id }}",
            meatRecipes.isEmpty())
        assertTrue("Pescatarian plan produced no meals", mealCount(days) > 0)
    }

    @Test
    fun `Pescatarian plan includes fish at least once`() {
        // Run several times to account for randomness
        val fishFound = (1..5).any { seed ->
            val days = MealPlanEngine.generateWeek(
                allRecipes = all,
                weekStart = monday.plusWeeks(seed.toLong()),
                nutritionGoal = null,
                dietPreferences = listOf("Pescatarian")
            )
            "Fish" in proteinSources(days)
        }
        assertTrue("Pescatarian plan never included fish across 5 runs", fishFound)
    }

    @Test
    fun `Mediterranean plan produces meals`() {
        val days = generateVariety("Mediterranean")
        assertTrue("Mediterranean plan produced no meals", mealCount(days) > 0)
    }

    @Test
    fun `Omnivore plan produces meals`() {
        val days = generateVariety("Omnivore")
        assertTrue("Omnivore plan produced no meals", mealCount(days) > 0)
    }

    @Test
    fun `Flexitarian plan produces meals`() {
        val days = generateVariety("Flexitarian")
        assertTrue("Flexitarian plan produced no meals", mealCount(days) > 0)
    }

    @Test
    fun `Chicken-Fish plan includes fish or chicken`() {
        val fishOrChicken = (1..5).any { seed ->
            val days = MealPlanEngine.generateWeek(
                allRecipes = all,
                weekStart = monday.plusWeeks(seed.toLong()),
                nutritionGoal = null,
                dietPreferences = listOf("Chicken/Fish")
            )
            proteinSources(days).any { it in setOf("Fish", "Chicken") }
        }
        assertTrue("Chicken/Fish plan never included fish or chicken across 5 runs", fishOrChicken)
    }

    @Test
    fun `Poultry plan includes chicken or turkey`() {
        val found = (1..5).any { seed ->
            val days = MealPlanEngine.generateWeek(
                allRecipes = all,
                weekStart = monday.plusWeeks(seed.toLong()),
                nutritionGoal = null,
                dietPreferences = listOf("Poultry")
            )
            proteinSources(days).any { it in setOf("Chicken", "Turkey") }
        }
        assertTrue("Poultry plan never included chicken or turkey across 5 runs", found)
    }

    @Test
    fun `Meat-Poultry plan includes meat source`() {
        val found = (1..5).any { seed ->
            val days = MealPlanEngine.generateWeek(
                allRecipes = all,
                weekStart = monday.plusWeeks(seed.toLong()),
                nutritionGoal = null,
                dietPreferences = listOf("Meat/Poultry")
            )
            proteinSources(days).any { it in setOf("Chicken", "Turkey", "Beef", "Pork", "Lamb") }
        }
        assertTrue("Meat/Poultry plan never included meat across 5 runs", found)
    }

    // ── 5. Food restriction coverage ─────────────────────────────────────

    @Test
    fun `Dairy Free restriction produces a full plan`() {
        val days = generateVariety("High Protein Vegetarian", restrictions = listOf("Dairy Free"))
        assertTrue("Dairy Free plan produced no meals", mealCount(days) > 0)
    }

    @Test
    fun `Gluten Free restriction produces a full plan`() {
        val days = generateVariety("High Protein Vegetarian", restrictions = listOf("Gluten Free"))
        assertTrue("Gluten Free plan produced no meals", mealCount(days) > 0)
    }

    @Test
    fun `Dairy Free + Gluten Free combined restriction produces a plan`() {
        val days = generateVariety("High Protein Vegetarian", restrictions = listOf("Dairy Free", "Gluten Free"))
        assertTrue("Dairy Free + Gluten Free plan produced no meals", mealCount(days) > 0)
    }

    @Test
    fun `Dairy Free + Gluten Free + Nut Free combined restriction produces a plan`() {
        val days = generateVariety("Pescatarian", restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free"))
        assertTrue("Triple restriction plan produced no meals", mealCount(days) > 0)
    }

    // ── 6. Repeat Weekly mode smoke tests ────────────────────────────────

    @Test
    fun `Poultry Repeat Weekly produces meals`() {
        val days = generateRepeat("Poultry")
        assertTrue("Poultry Repeat Weekly produced no meals", mealCount(days) > 0)
        assertTrue("All 7 days should repeat the same meals (Repeat Weekly)",
            days.map { it.meals }.distinct().size == 1)
    }

    @Test
    fun `Pescatarian Repeat Weekly produces meals`() {
        val days = generateRepeat("Pescatarian")
        assertTrue("Pescatarian Repeat Weekly produced no meals", mealCount(days) > 0)
    }
}
