package com.dailyhealthcoach.ui.recipes

import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class MealPlanEngineDietTest {

    private val monday = LocalDate.of(2026, 6, 22)
    private val nonVegetarianSources = setOf("Chicken", "Turkey", "Beef", "Fish", "Pork", "Lamb")

    private fun countNonVegetarianMeals(days: List<DayMealPlanUiState>): Int =
        days.sumOf { day ->
            day.meals.values.count { recipe ->
                recipe != null && recipe.proteinSource in nonVegetarianSources
            }
        }

    private fun proteinSourcesIn(days: List<DayMealPlanUiState>): Set<String> =
        days.flatMap { day ->
            day.meals.values.mapNotNull { it?.proteinSource }
        }.filter { it.isNotBlank() }.toSet()

    @Test
    fun `Chicken-Fish diet produces fish and chicken meals`() {
        val days = MealPlanEngine.generateWeek(
            allRecipes = RecipeCatalog.ALL,
            weekStart = monday,
            nutritionGoal = null,
            dietPreferences = listOf("Chicken/Fish")
        )
        val sources = proteinSourcesIn(days)
        assertTrue("Expected Fish or Chicken in Chicken/Fish plan, got: $sources",
            "Fish" in sources || "Chicken" in sources)
        assertTrue("Chicken/Fish plan should have >0 non-vegetarian meals",
            countNonVegetarianMeals(days) > 0)
    }

    @Test
    fun `Poultry diet produces chicken or turkey meals`() {
        val days = MealPlanEngine.generateWeek(
            allRecipes = RecipeCatalog.ALL,
            weekStart = monday,
            nutritionGoal = null,
            dietPreferences = listOf("Poultry")
        )
        val sources = proteinSourcesIn(days)
        assertTrue("Expected Chicken or Turkey in Poultry plan, got: $sources",
            "Chicken" in sources || "Turkey" in sources)
        assertTrue("Poultry plan should have >0 non-vegetarian meals",
            countNonVegetarianMeals(days) > 0)
    }

    @Test
    fun `Meat-Poultry diet produces chicken turkey or beef meals`() {
        val days = MealPlanEngine.generateWeek(
            allRecipes = RecipeCatalog.ALL,
            weekStart = monday,
            nutritionGoal = null,
            dietPreferences = listOf("Meat/Poultry")
        )
        val sources = proteinSourcesIn(days)
        val meatSources = setOf("Chicken", "Turkey", "Beef", "Pork", "Lamb")
        assertTrue("Expected meat source in Meat/Poultry plan, got: $sources",
            sources.any { it in meatSources })
        assertTrue("Meat/Poultry plan should have >0 non-vegetarian meals",
            countNonVegetarianMeals(days) > 0)
    }

    @Test
    fun `Poultry Repeat Weekly mode produces chicken or turkey meals`() {
        val days = MealPlanEngine.generateWeekRepeat(
            allRecipes = RecipeCatalog.ALL,
            weekStart = monday,
            nutritionGoal = null,
            dietPreferences = listOf("Poultry")
        )
        val sources = proteinSourcesIn(days)
        assertTrue("Expected Chicken or Turkey in Poultry Repeat Weekly plan, got: $sources",
            "Chicken" in sources || "Turkey" in sources)
    }

    @Test
    fun `Poultry plan has at least 3 non-vegetarian meals across 7 days`() {
        val days = MealPlanEngine.generateWeek(
            allRecipes = RecipeCatalog.ALL,
            weekStart = monday,
            nutritionGoal = null,
            dietPreferences = listOf("Poultry")
        )
        val count = countNonVegetarianMeals(days)
        assertTrue("Expected >=3 non-veg meals in Poultry plan (quota=3 per mealType * 3 types = 9), got: $count",
            count >= 3)
    }
}
