package com.dailyhealthcoach.ui.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dailyhealthcoach.domain.model.FoodEntryInput
import com.dailyhealthcoach.domain.repository.MacroTargetRepository
import com.dailyhealthcoach.domain.repository.NutritionRepository
import com.dailyhealthcoach.domain.repository.UserProfileRepository
import com.dailyhealthcoach.domain.usecase.HabitAutoUpdateUseCase
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private data class RecipeSelectionState(
    val selectedRecipeIds: Set<String> = emptySet(),
    val recommendedOverrides: List<String> = emptyList(),
    val groceryListOpen: Boolean = false,
    val checkedGroceryKeys: Set<String> = emptySet(),
    val noAlternateMessage: String? = null
)

private fun String.containsAny(vararg keywords: String) =
    keywords.any { this.contains(it, ignoreCase = true) }

class RecipesViewModel(
    private val nutritionRepository: NutritionRepository,
    macroTargetRepository: MacroTargetRepository,
    userProfileRepository: UserProfileRepository,
    private val habitAutoUpdateUseCase: HabitAutoUpdateUseCase
) : ViewModel() {

    private val today = LocalDate.now().toString()
    private val selectedDialogState = MutableStateFlow<Recipe?>(null)
    private val logMessageState = MutableStateFlow<String?>(null)
    private val selectionState = MutableStateFlow(RecipeSelectionState())

    private val userProfileState = userProfileRepository.observeUserProfile()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val uiState: StateFlow<RecipesUiState> = combine(
        nutritionRepository.observeFoodEntriesForDate(today),
        macroTargetRepository.observeActiveTarget(),
        selectedDialogState,
        logMessageState,
        selectionState
    ) { entries, macroTarget, selected, logMsg, sel ->
        val consumedCal = entries.sumOf { it.calories ?: 0 }
        val consumedProtein = entries.sumOf { it.proteinGrams ?: 0.0 }
        val calTarget = macroTarget?.calorieTarget ?: 2000
        val protTarget = macroTarget?.proteinMaxGrams?.toDouble() ?: 170.0
        val remainCal = calTarget - consumedCal
        val remainProtein = protTarget - consumedProtein
        val goal = userProfileState.value?.nutritionGoal
        val dietPref = userProfileState.value?.dietPreference

        val filtered = filterByDiet(RecipeCatalog.ALL, dietPref)
        val ranked = rankRecipes(filtered, remainCal, remainProtein, goal)

        val recommendedRecipes = if (sel.recommendedOverrides.size == 3) {
            sel.recommendedOverrides.mapNotNull { id -> filtered.find { it.id == id } }
                .ifEmpty { ranked.take(3) }
        } else {
            ranked.take(3)
        }

        RecipesUiState(
            remainingCalories = remainCal,
            remainingProtein = remainProtein,
            calorieTarget = calTarget,
            proteinTarget = protTarget,
            recommendedRecipes = recommendedRecipes,
            breakfastRecipes = ranked.filter { it.mealType == "Breakfast" },
            lunchRecipes = ranked.filter { it.mealType == "Lunch" },
            dinnerRecipes = ranked.filter { it.mealType == "Dinner" },
            snackRecipes = ranked.filter { it.mealType == "Snack" },
            selectedRecipe = selected,
            logSuccessMessage = logMsg,
            selectedRecipeIds = sel.selectedRecipeIds,
            groceryListOpen = sel.groceryListOpen,
            groceryItems = buildGroceryItems(sel.selectedRecipeIds),
            checkedGroceryKeys = sel.checkedGroceryKeys,
            noAlternateMessage = sel.noAlternateMessage
        )
    }
    .flowOn(Dispatchers.Default)
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = RecipesUiState()
    )

    fun selectRecipe(recipe: Recipe) { selectedDialogState.value = recipe }
    fun deselectRecipe() { selectedDialogState.value = null }
    fun dismissLogMessage() { logMessageState.value = null }

    fun toggleSelection(recipeId: String) {
        selectionState.update { s ->
            val updated = if (recipeId in s.selectedRecipeIds)
                s.selectedRecipeIds - recipeId else s.selectedRecipeIds + recipeId
            s.copy(selectedRecipeIds = updated)
        }
    }

    fun openGroceryList() { selectionState.update { it.copy(groceryListOpen = true) } }
    fun closeGroceryList() { selectionState.update { it.copy(groceryListOpen = false) } }

    fun toggleGroceryItem(key: String) {
        selectionState.update { s ->
            val updated = if (key in s.checkedGroceryKeys)
                s.checkedGroceryKeys - key else s.checkedGroceryKeys + key
            s.copy(checkedGroceryKeys = updated)
        }
    }

    fun dismissNoAlternate() {
        selectionState.update { it.copy(noAlternateMessage = null) }
    }

    fun tryAnother(recipeId: String) {
        val sel = selectionState.value
        val currentIds = if (sel.recommendedOverrides.size == 3)
            sel.recommendedOverrides
        else
            uiState.value.recommendedRecipes.map { it.id }

        val currentRecipe = RecipeCatalog.ALL.find { it.id == recipeId } ?: return
        val dietPref = userProfileState.value?.dietPreference
        val goal = userProfileState.value?.nutritionGoal
        val curr = uiState.value
        val filtered = filterByDiet(RecipeCatalog.ALL, dietPref)

        val alternatives = rankRecipes(
            filtered.filter { it.mealType == currentRecipe.mealType && it.id !in currentIds },
            curr.remainingCalories, curr.remainingProtein, goal
        )

        val replacement = alternatives.firstOrNull()
        if (replacement == null) {
            selectionState.update { it.copy(noAlternateMessage = "No alternate available yet.") }
            return
        }

        val newOverrides = currentIds.toMutableList()
        val idx = newOverrides.indexOf(recipeId)
        if (idx < 0) return
        newOverrides[idx] = replacement.id
        selectionState.update { it.copy(recommendedOverrides = newOverrides, noAlternateMessage = null) }
    }

    fun getGroceryShareText(): String {
        val items = uiState.value.groceryItems
        if (items.isEmpty()) return "No items in grocery list."
        val grouped = items.groupBy { it.category }
        return buildString {
            GroceryCategory.values().forEach { cat ->
                val catItems = grouped[cat] ?: return@forEach
                appendLine(cat.name.replace("_", "/"))
                catItems.forEach { item ->
                    appendLine("• ${item.ingredient} (${item.recipeSource})")
                }
                appendLine()
            }
        }.trim()
    }

    fun logRecipe(recipe: Recipe, mealName: String) {
        viewModelScope.launch {
            nutritionRepository.saveFoodEntry(
                FoodEntryInput(
                    id = 0,
                    date = today,
                    mealName = mealName,
                    foodName = recipe.name,
                    brandName = null,
                    servingDescription = "1 serving",
                    calories = recipe.calories,
                    proteinGrams = recipe.proteinGrams,
                    carbGrams = recipe.carbGrams,
                    fatGrams = recipe.fatGrams,
                    fiberGrams = recipe.fiberGrams,
                    mealTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
                    isWholeFoodBased = recipe.tags.any {
                        it.equals("Vegan", ignoreCase = true) ||
                        it.equals("Whole Foods", ignoreCase = true) ||
                        it.equals("Vegetarian", ignoreCase = true)
                    },
                    isProcessed = false,
                    isFermented = false,
                    source = "RECIPE"
                )
            )
            habitAutoUpdateUseCase(today)
            selectedDialogState.value = null
            logMessageState.value = "${recipe.name} added to your log."
        }
    }

    private fun buildGroceryItems(selectedIds: Set<String>): List<GroceryItem> =
        selectedIds.flatMap { id ->
            val recipe = RecipeCatalog.ALL.find { it.id == id } ?: return@flatMap emptyList()
            recipe.ingredients.map { ingredient ->
                GroceryItem(
                    key = "$id:$ingredient",
                    ingredient = ingredient,
                    recipeSource = recipe.name,
                    category = categorizeIngredient(ingredient)
                )
            }
        }

    private fun categorizeIngredient(ingredient: String): GroceryCategory {
        val lower = ingredient.lowercase()
        return when {
            lower.containsAny("spinach", "tomato", "cucumber", "berr", "banana",
                "bell pepper", "mushroom", "onion", "cherry", "broccoli",
                "snap peas", "carrot", "lemon") -> GroceryCategory.PRODUCE
            lower.containsAny("yogurt", "milk", "cottage cheese", "paneer") -> GroceryCategory.DAIRY
            lower.containsAny("egg", "tofu", "protein powder", "premier protein",
                "protein shake") -> GroceryCategory.PROTEIN
            lower.containsAny("oat", "rice", "bread", "granola", "chickpea", "lentil",
                "black bean", "chia", "almond milk", "broth", "olive oil",
                "sesame oil", "soy sauce", "honey", "almond butter", "vanilla") -> GroceryCategory.PANTRY
            else -> GroceryCategory.SPICES_OTHER
        }
    }

    private fun rankRecipes(
        recipes: List<Recipe>,
        remainCal: Int,
        remainProtein: Double,
        goal: String?
    ): List<Recipe> = recipes.sortedByDescending { r ->
        val protein = r.proteinGrams
        val cal = r.calories.toDouble().coerceAtLeast(1.0)
        when {
            remainProtein > 30.0 -> protein / cal * 100.0
            remainCal < 200 -> -cal
            goal.equals("Gain Muscle", ignoreCase = true) -> protein * 2.0 - cal * 0.005
            goal.equals("Lose Fat", ignoreCase = true) -> protein / cal * 80.0 - cal * 0.01
            else -> protein - cal * 0.01
        }
    }

    private fun filterByDiet(recipes: List<Recipe>, dietPref: String?): List<Recipe> {
        if (dietPref.isNullOrBlank() || dietPref.equals("No Restriction", ignoreCase = true)) return recipes
        val pref = dietPref.lowercase().trim()
        val filtered = recipes.filter { r ->
            r.tags.any { tag ->
                when (pref) {
                    "vegetarian" -> tag.equals("Vegetarian", ignoreCase = true) ||
                                    tag.equals("Vegan", ignoreCase = true)
                    "vegan" -> tag.equals("Vegan", ignoreCase = true)
                    "whole foods" -> tag.equals("Whole Foods", ignoreCase = true)
                    else -> tag.lowercase().contains(pref)
                }
            }
        }
        return filtered.ifEmpty { recipes }
    }
}

class RecipesViewModelFactory(
    private val nutritionRepository: NutritionRepository,
    private val macroTargetRepository: MacroTargetRepository,
    private val userProfileRepository: UserProfileRepository,
    private val habitAutoUpdateUseCase: HabitAutoUpdateUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipesViewModel::class.java)) {
            return RecipesViewModel(
                nutritionRepository = nutritionRepository,
                macroTargetRepository = macroTargetRepository,
                userProfileRepository = userProfileRepository,
                habitAutoUpdateUseCase = habitAutoUpdateUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
