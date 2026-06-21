package com.dailyhealthcoach.ui.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dailyhealthcoach.domain.model.FoodEntryInput
import com.dailyhealthcoach.domain.model.PlannedMeal
import com.dailyhealthcoach.domain.model.PlannedMealSlotKey
import com.dailyhealthcoach.domain.repository.MacroTargetRepository
import com.dailyhealthcoach.domain.repository.NutritionRepository
import com.dailyhealthcoach.domain.repository.PlannedMealRepository
import com.dailyhealthcoach.domain.repository.UserProfileRepository
import com.dailyhealthcoach.domain.usecase.HabitAutoUpdateUseCase
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.DayOfWeek
import java.time.temporal.TemporalAdjusters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.format.TextStyle
import java.util.Locale
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private data class ParsedIngredient(
    val qty: Double?,
    val unit: String,
    val normalizedName: String,
    val recipeName: String
)

private data class MealSlotContext(val dayLabel: String, val mealType: String, val recipe: Recipe)

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
    private val macroTargetRepository: MacroTargetRepository,
    userProfileRepository: UserProfileRepository,
    private val habitAutoUpdateUseCase: HabitAutoUpdateUseCase,
    private val plannedMealRepository: PlannedMealRepository
) : ViewModel() {

    private val todayDate: LocalDate = LocalDate.now()
    private val today = todayDate.toString()
    private val selectedDialogState = MutableStateFlow<Recipe?>(null)
    private val logMessageState = MutableStateFlow<String?>(null)
    private val selectionState = MutableStateFlow(RecipeSelectionState())

    private val userProfileState = userProfileRepository.observeUserProfile()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val macroTargetState = macroTargetRepository.observeActiveTarget()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    // ── Meal Calendar ──────────────────────────────────────────────────────

    private data class MealCalendarInternal(
        val weekOffset: Int = 0,
        val plans: Map<Int, List<DayMealPlanUiState>> = emptyMap(),
        val selectedSlot: PlannedMealSlot? = null,
        val weekGroceryListOpen: Boolean = false,
        val weekGroceryCheckedKeys: Set<String> = emptySet(),
        val mealPlanMode: String = "Variety",
        val replacementCandidates: List<Recipe> = emptyList(),
        val inlineReplaceSlot: PlannedMealSlot? = null,
        val selectedGrocerySlots: Set<String> = emptySet()
    )

    private data class DraftPlanInternal(
        val weekOffset: Int,
        val days: List<DayMealPlanUiState>,
        val mealPlanMode: String
    )

    private val calendarInternal = MutableStateFlow(MealCalendarInternal())
    private val draftPlanInternal = MutableStateFlow<DraftPlanInternal?>(null)

    val draftPlanUiState: StateFlow<DraftPlanUiState?> = draftPlanInternal.map { draft ->
        if (draft == null) return@map null
        val weekLabel = when (draft.weekOffset) {
            0 -> "This Week"; 1 -> "Next Week"; -1 -> "Last Week"
            else -> if (draft.weekOffset > 0) "+${draft.weekOffset} Weeks" else "${-draft.weekOffset} Weeks Ago"
        }
        DraftPlanUiState(weekLabel = weekLabel, days = draft.days, mealPlanMode = draft.mealPlanMode)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val mealCalendarUiState: StateFlow<MealCalendarUiState> = calendarInternal
        .flatMapLatest { cal ->
            val weekDates = computeWeekDates(cal.weekOffset)
            combine(
                plannedMealRepository.observeForDates(weekDates),
                plannedMealRepository.observeForDate(today)
            ) { dbWeekMeals, dbTodayMeals ->
                val currentPlan = cal.plans[cal.weekOffset] ?: emptyList()
                val effectiveDays = currentPlan.ifEmpty { rebuildDaysFromDb(weekDates, dbWeekMeals) }
                val weekLabel = when (cal.weekOffset) {
                    0 -> "This Week"; 1 -> "Next Week"; -1 -> "Last Week"
                    else -> if (cal.weekOffset > 0) "+${cal.weekOffset} Weeks" else "${-cal.weekOffset} Weeks Ago"
                }
                MealCalendarUiState(
                    weekOffset = cal.weekOffset,
                    weekLabel = weekLabel,
                    days = effectiveDays,
                    isGenerated = effectiveDays.isNotEmpty(),
                    selectedSlot = cal.selectedSlot,
                    weekGroceryListOpen = cal.weekGroceryListOpen,
                    weekGroceryItems = if (cal.weekGroceryListOpen) {
                        val slots = effectiveDays.flatMap { day ->
                            listOf("Breakfast", "Lunch", "Dinner", "Snack", "Protein Booster 1", "Protein Booster 2").mapNotNull { mealType ->
                                if ("${day.date}::$mealType" in cal.selectedGrocerySlots) {
                                    val recipe = day.meals[mealType] ?: return@mapNotNull null
                                    MealSlotContext(day.dayLabel, mealType, recipe)
                                } else null
                            }
                        }
                        buildGroceryItemsFromSlots(slots)
                    } else emptyList(),
                    weekGroceryCheckedKeys = cal.weekGroceryCheckedKeys,
                    mealPlanMode = cal.mealPlanMode,
                    replacementCandidates = cal.replacementCandidates,
                    inlineReplaceSlot = cal.inlineReplaceSlot,
                    selectedGrocerySlots = cal.selectedGrocerySlots,
                    todayHasPlan = dbTodayMeals.isNotEmpty(),
                    todayPlannedCalories = dbTodayMeals.sumOf { it.calories },
                    todayPlannedProtein = dbTodayMeals.sumOf { it.proteinGrams },
                    todayPlannedCarbs = dbTodayMeals.sumOf { it.carbGrams },
                    todayPlannedFat = dbTodayMeals.sumOf { it.fatGrams },
                    todayPlannedFiber = dbTodayMeals.sumOf { it.fiberGrams }
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MealCalendarUiState()
        )

    private fun computeWeekDates(weekOffset: Int): List<String> {
        val weekStart = todayDate.plusWeeks(weekOffset.toLong())
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        return (0..6).map { weekStart.plusDays(it.toLong()).toString() }
    }

    private fun rebuildDaysFromDb(weekDates: List<String>, dbMeals: List<PlannedMeal>): List<DayMealPlanUiState> {
        val byDate = dbMeals.groupBy { it.date }
        return weekDates.mapNotNull { dateStr ->
            val mealsForDay = byDate[dateStr] ?: return@mapNotNull null
            val date = LocalDate.parse(dateStr)
            val mealMap: Map<String, Recipe?> = mealsForDay.associate { pm ->
                PlannedMealSlotKey.toDisplayLabel(pm.slotKey) to RecipeCatalog.ALL.find { it.id == pm.recipeId }
            }
            DayMealPlanUiState(
                date = dateStr,
                dayLabel = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                dateNumber = date.dayOfMonth,
                meals = mealMap
            )
        }
    }

    fun navigateCalendarWeek(delta: Int) {
        calendarInternal.update { it.copy(weekOffset = it.weekOffset + delta) }
    }

    fun setMealPlanMode(mode: String) {
        calendarInternal.update { it.copy(mealPlanMode = mode) }
    }

    fun generateWeekPlan() {
        val profile = userProfileState.value
        val offset = calendarInternal.value.weekOffset
        val mode = calendarInternal.value.mealPlanMode
        val weekStart = todayDate.plusWeeks(offset.toLong())
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val proteinTarget = macroTargetState.value?.proteinMaxGrams ?: 0
        val calTarget = macroTargetState.value?.calorieTarget ?: 0
        val days = if (mode == "Repeat Weekly") {
            MealPlanEngine.generateWeekRepeat(
                allRecipes = RecipeCatalog.ALL,
                weekStart = weekStart,
                nutritionGoal = profile?.nutritionGoal,
                dietPreferences = profile?.dietPreferences ?: emptyList(),
                foodRestrictions = profile?.foodRestrictions ?: emptyList(),
                proteinTargetGrams = proteinTarget,
                calorieTarget = calTarget
            )
        } else {
            MealPlanEngine.generateWeek(
                allRecipes = RecipeCatalog.ALL,
                weekStart = weekStart,
                nutritionGoal = profile?.nutritionGoal,
                dietPreferences = profile?.dietPreferences ?: emptyList(),
                foodRestrictions = profile?.foodRestrictions ?: emptyList(),
                proteinTargetGrams = proteinTarget,
                calorieTarget = calTarget
            )
        }
        draftPlanInternal.value = DraftPlanInternal(weekOffset = offset, days = days, mealPlanMode = mode)
    }

    fun regenerateWeekPlan() = generateWeekPlan()

    fun acceptDraftPlan() {
        val draft = draftPlanInternal.value ?: return
        calendarInternal.update { state ->
            state.copy(plans = state.plans + (draft.weekOffset to draft.days))
        }
        val dates = draft.days.map { it.date }
        val meals = draft.days.flatMap { day ->
            day.meals.entries.mapNotNull { (label, recipe) ->
                recipe?.let {
                    PlannedMeal(
                        date = day.date,
                        slotKey = PlannedMealSlotKey.fromDisplayLabel(label),
                        recipeId = it.id,
                        recipeName = it.name,
                        calories = it.calories,
                        proteinGrams = it.proteinGrams,
                        carbGrams = it.carbGrams,
                        fatGrams = it.fatGrams,
                        fiberGrams = it.fiberGrams ?: 0.0
                    )
                }
            }
        }
        viewModelScope.launch {
            plannedMealRepository.replaceForDates(dates, meals)
        }
        draftPlanInternal.value = null
    }

    fun cancelDraftPlan() {
        draftPlanInternal.value = null
    }

    fun replaceDraftMeal(date: String, mealType: String) {
        val draft = draftPlanInternal.value ?: return
        val profile = userProfileState.value
        val usedIds = draft.days.flatMap { it.meals.values }.mapNotNull { it?.id }.toSet()
        val replacement = MealPlanEngine.pickReplacement(
            allRecipes = RecipeCatalog.ALL,
            mealType = mealType,
            usedIds = usedIds,
            nutritionGoal = profile?.nutritionGoal,
            dietPreferences = profile?.dietPreferences ?: emptyList(),
            foodRestrictions = profile?.foodRestrictions ?: emptyList(),
            proteinTargetGrams = macroTargetState.value?.proteinMaxGrams ?: 0,
            calorieTarget = macroTargetState.value?.calorieTarget ?: 0
        ) ?: return
        val updatedDays = draft.days.map { day ->
            if (day.date == date) day.copy(meals = day.meals + (mealType to replacement)) else day
        }
        draftPlanInternal.value = draft.copy(days = updatedDays)
    }

    fun selectCalendarMeal(date: String, mealType: String) {
        val offset = calendarInternal.value.weekOffset
        val day = calendarInternal.value.plans[offset]?.find { it.date == date } ?: return
        calendarInternal.update { it.copy(
            selectedSlot = PlannedMealSlot(date, mealType, day.meals[mealType]),
            inlineReplaceSlot = null,
            replacementCandidates = emptyList()
        ) }
    }

    fun dismissCalendarMeal() {
        calendarInternal.update { it.copy(selectedSlot = null, replacementCandidates = emptyList()) }
    }

    fun loadCalendarMealAlternatives(date: String, mealType: String) {
        val offset = calendarInternal.value.weekOffset
        val currentPlan = calendarInternal.value.plans[offset] ?: return
        val profile = userProfileState.value
        val usedInPlan = currentPlan.flatMap { it.meals.values }.mapNotNull { it?.id }.toSet()
        val usedInCandidates = mutableSetOf<String>()
        val candidates = mutableListOf<Recipe>()
        repeat(3) {
            MealPlanEngine.pickReplacement(
                allRecipes = RecipeCatalog.ALL,
                mealType = mealType,
                usedIds = usedInPlan + usedInCandidates,
                nutritionGoal = profile?.nutritionGoal,
                dietPreferences = profile?.dietPreferences ?: emptyList(),
                foodRestrictions = profile?.foodRestrictions ?: emptyList(),
                proteinTargetGrams = macroTargetState.value?.proteinMaxGrams ?: 0,
                calorieTarget = macroTargetState.value?.calorieTarget ?: 0
            )?.let {
                candidates.add(it)
                usedInCandidates.add(it.id)
            }
        }
        calendarInternal.update { it.copy(replacementCandidates = candidates) }
    }

    fun clearCalendarAlternatives() {
        calendarInternal.update { it.copy(replacementCandidates = emptyList()) }
    }

    fun startInlineReplace(date: String, mealType: String) {
        val offset = calendarInternal.value.weekOffset
        val currentPlan = calendarInternal.value.plans[offset] ?: return
        val recipe = currentPlan.find { it.date == date }?.meals?.get(mealType)
        val slot = PlannedMealSlot(date, mealType, recipe)
        val profile = userProfileState.value
        val usedInPlan = currentPlan.flatMap { it.meals.values }.mapNotNull { it?.id }.toSet()
        val usedInCandidates = mutableSetOf<String>()
        val candidates = mutableListOf<Recipe>()
        repeat(3) {
            MealPlanEngine.pickReplacement(
                allRecipes = RecipeCatalog.ALL,
                mealType = mealType,
                usedIds = usedInPlan + usedInCandidates,
                nutritionGoal = profile?.nutritionGoal,
                dietPreferences = profile?.dietPreferences ?: emptyList(),
                foodRestrictions = profile?.foodRestrictions ?: emptyList(),
                proteinTargetGrams = macroTargetState.value?.proteinMaxGrams ?: 0,
                calorieTarget = macroTargetState.value?.calorieTarget ?: 0
            )?.let {
                candidates.add(it)
                usedInCandidates.add(it.id)
            }
        }
        calendarInternal.update { it.copy(
            inlineReplaceSlot = slot,
            replacementCandidates = candidates,
            selectedSlot = null
        ) }
    }

    fun clearInlineReplace() {
        calendarInternal.update { it.copy(inlineReplaceSlot = null, replacementCandidates = emptyList()) }
    }

    fun replaceInlineCalendarMeal(date: String, mealType: String, recipe: Recipe) {
        val offset = calendarInternal.value.weekOffset
        val currentPlan = calendarInternal.value.plans[offset] ?: return
        val updated = currentPlan.map { day ->
            if (day.date == date) day.copy(meals = day.meals + (mealType to recipe)) else day
        }
        calendarInternal.update { state ->
            state.copy(
                plans = state.plans + (offset to updated),
                inlineReplaceSlot = null,
                replacementCandidates = emptyList()
            )
        }
    }

    fun quickLogCalendarMeal(date: String, mealType: String) {
        val offset = calendarInternal.value.weekOffset
        val recipe = calendarInternal.value.plans[offset]
            ?.find { it.date == date }?.meals?.get(mealType) ?: return
        logCalendarMeal(recipe, mealType)
    }

    fun replaceCalendarMealWith(date: String, mealType: String, recipe: Recipe) {
        val offset = calendarInternal.value.weekOffset
        val currentPlan = calendarInternal.value.plans[offset] ?: return
        val updated = currentPlan.map { day ->
            if (day.date == date) day.copy(meals = day.meals + (mealType to recipe)) else day
        }
        calendarInternal.update { state ->
            state.copy(
                plans = state.plans + (offset to updated),
                selectedSlot = PlannedMealSlot(date, mealType, recipe),
                replacementCandidates = emptyList()
            )
        }
    }

    fun replaceCalendarMeal(date: String, mealType: String) {
        val offset = calendarInternal.value.weekOffset
        val currentPlan = calendarInternal.value.plans[offset] ?: return
        val profile = userProfileState.value
        val usedIds = currentPlan.flatMap { it.meals.values }.mapNotNull { it?.id }.toSet()
        val replacement = MealPlanEngine.pickReplacement(
            allRecipes = RecipeCatalog.ALL,
            mealType = mealType,
            usedIds = usedIds,
            nutritionGoal = profile?.nutritionGoal,
            dietPreferences = profile?.dietPreferences ?: emptyList(),
            foodRestrictions = profile?.foodRestrictions ?: emptyList(),
            proteinTargetGrams = macroTargetState.value?.proteinMaxGrams ?: 0,
            calorieTarget = macroTargetState.value?.calorieTarget ?: 0
        ) ?: return
        val updated = currentPlan.map { day ->
            if (day.date == date) day.copy(meals = day.meals + (mealType to replacement)) else day
        }
        calendarInternal.update { state ->
            state.copy(
                plans = state.plans + (offset to updated),
                selectedSlot = PlannedMealSlot(date, mealType, replacement)
            )
        }
    }

    fun logCalendarMeal(recipe: Recipe, mealName: String) {
        viewModelScope.launch {
            nutritionRepository.saveFoodEntry(
                FoodEntryInput(
                    id = 0, date = today, mealName = mealName, foodName = recipe.name,
                    brandName = null, servingDescription = "1 serving",
                    calories = recipe.calories, proteinGrams = recipe.proteinGrams,
                    carbGrams = recipe.carbGrams, fatGrams = recipe.fatGrams,
                    fiberGrams = recipe.fiberGrams,
                    mealTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
                    isWholeFoodBased = recipe.tags.any {
                        it.equals("Vegan", ignoreCase = true) ||
                        it.equals("Whole Foods", ignoreCase = true) ||
                        it.equals("Vegetarian", ignoreCase = true)
                    },
                    isProcessed = false, isFermented = false, source = "RECIPE"
                )
            )
            habitAutoUpdateUseCase(today)
            logMessageState.value = "${recipe.name} added to your log."
            calendarInternal.update { it.copy(selectedSlot = null) }
        }
    }

    fun toggleGrocerySlot(date: String, mealType: String) {
        val key = "$date::$mealType"
        calendarInternal.update { s ->
            val updated = if (key in s.selectedGrocerySlots) s.selectedGrocerySlots - key
                          else s.selectedGrocerySlots + key
            s.copy(selectedGrocerySlots = updated)
        }
    }

    fun selectAllGrocerySlots() {
        val offset = calendarInternal.value.weekOffset
        val currentPlan = calendarInternal.value.plans[offset] ?: return
        val allKeys = currentPlan.flatMap { day ->
            listOf("Breakfast", "Lunch", "Dinner", "Snack", "Protein Booster 1", "Protein Booster 2")
                .filter { day.meals[it] != null }
                .map { mealType -> "${day.date}::$mealType" }
        }.toSet()
        calendarInternal.update { it.copy(selectedGrocerySlots = allKeys) }
    }

    fun clearGrocerySlots() {
        calendarInternal.update { it.copy(selectedGrocerySlots = emptySet()) }
    }

    fun openWeekGroceryList() {
        calendarInternal.update { it.copy(weekGroceryListOpen = true) }
    }

    fun closeWeekGroceryList() {
        calendarInternal.update { it.copy(weekGroceryListOpen = false) }
    }

    fun toggleWeekGroceryItem(key: String) {
        calendarInternal.update { s ->
            val updated = if (key in s.weekGroceryCheckedKeys) s.weekGroceryCheckedKeys - key
                          else s.weekGroceryCheckedKeys + key
            s.copy(weekGroceryCheckedKeys = updated)
        }
    }

    fun getWeekGroceryShareText(): String {
        val items = mealCalendarUiState.value.weekGroceryItems
        if (items.isEmpty()) return "No items in grocery list."
        val grouped = items.groupBy { it.category }
        return buildString {
            GroceryCategory.values().forEach { cat ->
                val catItems = grouped[cat] ?: return@forEach
                appendLine(cat.name.replace("_", "/").lowercase().replaceFirstChar { it.uppercase() })
                catItems.forEach { appendLine("[ ] ${it.displayLine}") }
                appendLine()
            }
        }.trim()
    }

    val uiState: StateFlow<RecipesUiState> = combine(
        nutritionRepository.observeFoodEntriesForDate(today),
        macroTargetRepository.observeActiveTarget(),
        selectedDialogState,
        logMessageState,
        selectionState
    ) { entries, macroTarget, selected, logMsg, sel ->
        val consumedCal = entries.sumOf { it.calories ?: 0 }
        val consumedProtein = entries.sumOf { it.proteinGrams ?: 0.0 }
        val consumedCarbs = entries.sumOf { it.carbGrams ?: 0.0 }
        val consumedFat = entries.sumOf { it.fatGrams ?: 0.0 }
        val consumedFiber = entries.sumOf { it.fiberGrams ?: 0.0 }
        val calTarget = macroTarget?.calorieTarget ?: 2000
        val protTarget = macroTarget?.proteinMaxGrams?.toDouble() ?: 170.0
        val carbTarget = macroTarget?.carbTargetGrams ?: 0
        val fatTarget = macroTarget?.fatTargetGrams ?: 0
        val fiberTarget = macroTarget?.fiberTargetGrams ?: 0
        val remainCal = calTarget - consumedCal
        val remainProtein = protTarget - consumedProtein
        val goal = userProfileState.value?.nutritionGoal
        val dietPref = userProfileState.value?.dietPreferences?.firstOrNull()

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
            consumedCalories = consumedCal,
            consumedProtein = consumedProtein,
            consumedCarbs = consumedCarbs,
            consumedFat = consumedFat,
            consumedFiber = consumedFiber,
            calorieTarget = calTarget,
            proteinTarget = protTarget,
            carbTarget = carbTarget,
            fatTarget = fatTarget,
            fiberTarget = fiberTarget,
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
        val dietPref = userProfileState.value?.dietPreferences?.firstOrNull()
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
                appendLine(cat.name.replace("_", "/").lowercase().replaceFirstChar { it.uppercase() })
                catItems.forEach { item ->
                    appendLine("[ ] ${item.displayLine}")
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

    private fun buildGroceryItems(selectedIds: Set<String>): List<GroceryItem> {
        if (selectedIds.isEmpty()) return emptyList()
        val allParsed = selectedIds.flatMap { id ->
            val recipe = RecipeCatalog.ALL.find { it.id == id } ?: return@flatMap emptyList()
            recipe.ingredients.map { raw -> parseIngredientLine(raw, recipe.name) }
        }
        return allParsed
            .groupBy { "${it.normalizedName}::${it.unit}" }
            .map { (key, items) ->
                val first = items.first()
                val totalQty = if (items.all { it.qty != null }) items.sumOf { it.qty!! } else null
                val displayName = first.normalizedName.replaceFirstChar { it.uppercase() }
                GroceryItem(
                    key = key,
                    displayLine = buildDisplayLine(totalQty, first.unit, displayName),
                    recipeSources = items.map { it.recipeName }.distinct(),
                    category = categorizeIngredient(first.normalizedName)
                )
            }
            .sortedBy { it.category.ordinal }
    }

    private fun buildGroceryItemsFromSlots(slots: List<MealSlotContext>): List<GroceryItem> {
        if (slots.isEmpty()) return emptyList()
        val allParsed = slots.flatMap { ctx ->
            ctx.recipe.ingredients.map { raw ->
                val parsed = parseIngredientLine(raw, ctx.recipe.name)
                parsed.copy(recipeName = "${ctx.dayLabel} ${ctx.mealType}: ${ctx.recipe.name}")
            }
        }
        return allParsed
            .groupBy { "${it.normalizedName}::${it.unit}" }
            .map { (key, items) ->
                val first = items.first()
                val totalQty = if (items.all { it.qty != null }) items.sumOf { it.qty!! } else null
                val displayName = first.normalizedName.replaceFirstChar { it.uppercase() }
                GroceryItem(
                    key = key,
                    displayLine = buildDisplayLine(totalQty, first.unit, displayName),
                    recipeSources = items.map { it.recipeName }.distinct(),
                    category = categorizeIngredient(first.normalizedName)
                )
            }
            .sortedBy { it.category.ordinal }
    }

    // Multiply per-day ingredient quantities by 7 for a full week's bulk shopping list.
    private fun buildWeeklyRepeatGroceryItems(selectedIds: Set<String>): List<GroceryItem> {
        if (selectedIds.isEmpty()) return emptyList()
        val allParsed = selectedIds.flatMap { id ->
            val recipe = RecipeCatalog.ALL.find { it.id == id } ?: return@flatMap emptyList()
            recipe.ingredients.map { raw -> parseIngredientLine(raw, recipe.name) }
        }
        return allParsed
            .groupBy { "${it.normalizedName}::${it.unit}" }
            .map { (key, items) ->
                val first = items.first()
                val totalQty = if (items.all { it.qty != null }) items.sumOf { it.qty!! } * 7 else null
                val displayName = first.normalizedName.replaceFirstChar { it.uppercase() }
                GroceryItem(
                    key = key,
                    displayLine = buildDisplayLine(totalQty, first.unit, displayName),
                    recipeSources = items.map { it.recipeName }.distinct(),
                    category = categorizeIngredient(first.normalizedName)
                )
            }
            .sortedBy { it.category.ordinal }
    }

    private fun parseIngredientLine(raw: String, recipeName: String): ParsedIngredient {
        // Normalize mixed-number fractions first ("1½" → "1.5"), then standalone fractions
        var text = Regex("""(\d)([½¼¾⅓⅔])""").replace(raw.trim()) { m ->
            (m.groupValues[1].toDouble() + fractionValue(m.groupValues[2])).toString()
        }
        text = text.replace("½", "0.5").replace("¼", "0.25").replace("¾", "0.75")
                   .replace("⅓", "0.333").replace("⅔", "0.667")

        val unitRegex = Regex(
            """^(\d+(?:\.\d+)?)\s*(cups?|tbsp|tsp|oz|scoops?|slices?|g)\b\s*(.+)""",
            RegexOption.IGNORE_CASE
        )
        val qtyOnlyRegex = Regex("""^(\d+(?:\.\d+)?)\s+(.+)""")

        val m1 = unitRegex.find(text)
        if (m1 != null) {
            return ParsedIngredient(
                qty = m1.groupValues[1].toDoubleOrNull(),
                unit = normalizeUnit(m1.groupValues[2]),
                normalizedName = normalizeName(m1.groupValues[3]),
                recipeName = recipeName
            )
        }
        val m2 = qtyOnlyRegex.find(text)
        if (m2 != null) {
            return ParsedIngredient(
                qty = m2.groupValues[1].toDoubleOrNull(),
                unit = "",
                normalizedName = normalizeName(m2.groupValues[2]),
                recipeName = recipeName
            )
        }
        return ParsedIngredient(qty = null, unit = "", normalizedName = normalizeName(text), recipeName = recipeName)
    }

    private fun fractionValue(char: String) = when (char) {
        "½" -> 0.5; "¼" -> 0.25; "¾" -> 0.75; "⅓" -> 1.0 / 3; "⅔" -> 2.0 / 3; else -> 0.0
    }

    private fun normalizeUnit(unit: String) = when (unit.lowercase()) {
        "cups" -> "cup"; "scoops" -> "scoop"; "slices" -> "slice"; else -> unit.lowercase()
    }

    private fun normalizeName(raw: String): String {
        var name = raw
            .replace(Regex("""\(.*?\)"""), "")
            .replace(Regex("""\s+to\s+taste\b.*""", RegexOption.IGNORE_CASE), "")
            .replace(Regex("""\s+or\s+\S+.*""", RegexOption.IGNORE_CASE), "")
        val prepWords = listOf("cooked", "frozen", "fresh", "low-fat", "non-fat", "firm",
            "unsweetened", "minced", "diced", "halved", "sliced", "rinsed", "pressed", "cubed")
        for (word in prepWords) {
            name = name.replace(Regex("""\b${Regex.escape(word)}\b""", RegexOption.IGNORE_CASE), "")
        }
        return name.replace(Regex("""\s+"""), " ").trim().lowercase()
    }

    private fun buildDisplayLine(qty: Double?, unit: String, name: String): String {
        val qtyStr = qty?.let { formatQty(it) } ?: return name
        return if (unit.isNotBlank()) "$name — $qtyStr $unit" else "$name — $qtyStr"
    }

    private fun formatQty(d: Double): String = when {
        d == 0.25 -> "¼"; d == 0.5 -> "½"; d == 0.75 -> "¾"
        d == 1.5 -> "1½"; d == 2.5 -> "2½"
        d % 1.0 == 0.0 -> d.toLong().toString()
        else -> "%.2f".format(d).trimEnd('0').trimEnd('.')
    }

    private fun categorizeIngredient(name: String): GroceryCategory {
        val n = name.lowercase()
        return when {
            // Check almond/plant milk before generic "milk" to avoid DAIRY mismatch
            n.containsAny("almond milk", "oat milk", "plant milk", "soy milk") -> GroceryCategory.PANTRY
            n.containsAny("spinach", "tomato", "cucumber", "berr", "banana",
                "bell pepper", "mushroom", "onion", "cherry", "broccoli",
                "snap peas", "carrot", "lemon", "vegetable", "salsa",
                "garlic") -> GroceryCategory.PRODUCE
            n.containsAny("yogurt", "milk", "cottage cheese", "paneer") -> GroceryCategory.DAIRY
            n.containsAny("egg", "tofu", "protein powder", "premier protein") -> GroceryCategory.PROTEIN
            n.containsAny("oat", "rice", "bread", "granola", "chickpea", "lentil",
                "black bean", "bean", "chia", "broth", "olive oil", "sesame oil",
                "soy sauce", "honey", "almond butter", "vanilla") -> GroceryCategory.PANTRY
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
    private val habitAutoUpdateUseCase: HabitAutoUpdateUseCase,
    private val plannedMealRepository: PlannedMealRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipesViewModel::class.java)) {
            return RecipesViewModel(
                nutritionRepository = nutritionRepository,
                macroTargetRepository = macroTargetRepository,
                userProfileRepository = userProfileRepository,
                habitAutoUpdateUseCase = habitAutoUpdateUseCase,
                plannedMealRepository = plannedMealRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
