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
import kotlinx.coroutines.launch

class RecipesViewModel(
    private val nutritionRepository: NutritionRepository,
    macroTargetRepository: MacroTargetRepository,
    userProfileRepository: UserProfileRepository,
    private val habitAutoUpdateUseCase: HabitAutoUpdateUseCase
) : ViewModel() {

    private val today = LocalDate.now().toString()
    private val selectedState = MutableStateFlow<Recipe?>(null)
    private val logMessageState = MutableStateFlow<String?>(null)

    private val userProfileState = userProfileRepository.observeUserProfile()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val uiState: StateFlow<RecipesUiState> = combine(
        nutritionRepository.observeFoodEntriesForDate(today),
        macroTargetRepository.observeActiveTarget(),
        selectedState,
        logMessageState
    ) { entries, macroTarget, selected, logMsg ->
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

        RecipesUiState(
            remainingCalories = remainCal,
            remainingProtein = remainProtein,
            calorieTarget = calTarget,
            proteinTarget = protTarget,
            recommendedRecipes = ranked.take(3),
            breakfastRecipes = ranked.filter { it.mealType == "Breakfast" },
            lunchRecipes = ranked.filter { it.mealType == "Lunch" },
            dinnerRecipes = ranked.filter { it.mealType == "Dinner" },
            snackRecipes = ranked.filter { it.mealType == "Snack" },
            selectedRecipe = selected,
            logSuccessMessage = logMsg
        )
    }
    .flowOn(Dispatchers.Default)
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = RecipesUiState()
    )

    fun selectRecipe(recipe: Recipe) {
        selectedState.value = recipe
    }

    fun deselectRecipe() {
        selectedState.value = null
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
            selectedState.value = null
            logMessageState.value = "${recipe.name} added to your log."
        }
    }

    fun dismissLogMessage() {
        logMessageState.value = null
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
