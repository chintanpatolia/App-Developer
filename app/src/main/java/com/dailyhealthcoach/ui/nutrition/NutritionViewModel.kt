package com.dailyhealthcoach.ui.nutrition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dailyhealthcoach.ai.AiFoodLoggingService
import com.dailyhealthcoach.barcode.FoodLookupService
import com.dailyhealthcoach.barcode.NutritionLabelOcrParser
import com.dailyhealthcoach.domain.model.FoodEntry
import com.dailyhealthcoach.domain.model.FoodEntryInput
import com.dailyhealthcoach.domain.model.MacroTarget
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NutritionViewModel(
    private val nutritionRepository: NutritionRepository,
    macroTargetRepository: MacroTargetRepository,
    private val userProfileRepository: UserProfileRepository,
    private val foodLookupService: FoodLookupService,
    private val aiFoodLoggingService: AiFoodLoggingService,
    private val habitAutoUpdateUseCase: HabitAutoUpdateUseCase,
    private val plannedMealRepository: PlannedMealRepository
) : ViewModel() {
    private val today = LocalDate.now().toString()
    private val formState = MutableStateFlow(FormVisibilityState())

    private val userProfileState = userProfileRepository.observeUserProfile()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val uiState: StateFlow<NutritionUiState> = combine(
        nutritionRepository.observeFoodEntriesForDate(today),
        macroTargetRepository.observeActiveTarget(),
        formState,
        nutritionRepository.observeAll(),
        plannedMealRepository.observeForDate(today)
    ) { entries, macroTarget, form, allEntries, plannedMeals ->
        entries.toUiState(
            proteinGoalMin = macroTarget?.proteinMinGrams ?: 170,
            proteinGoalMax = macroTarget?.proteinMaxGrams ?: 200,
            macroTarget = macroTarget,
            goal = userProfileState.value?.nutritionGoal,
            dietPreference = userProfileState.value?.dietPreferences?.firstOrNull(),
            form = form,
            allEntries = allEntries,
            hour = LocalTime.now().hour,
            plannedMeals = plannedMeals
        )
    }
    .flowOn(Dispatchers.Default)
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = NutritionUiState()
    )

    fun showAddForm() {
        formState.value = FormVisibilityState(
            isVisible = true,
            form = FoodEntryFormUiState(
                mealTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
            )
        )
    }

    fun hideForm() {
        formState.value = FormVisibilityState()
    }

    fun updateForm(transform: (FoodEntryFormUiState) -> FoodEntryFormUiState) {
        formState.update { it.copy(form = transform(it.form)) }
    }

    fun editEntry(entry: FoodEntryUiState) {
        val q = entry.quantity.coerceAtLeast(0.01)
        formState.value = FormVisibilityState(
            isVisible = true,
            form = FoodEntryFormUiState(
                editingId = entry.id,
                mealName = entry.mealName,
                foodName = entry.foodName,
                brandName = entry.brandName.orEmpty(),
                servingDescription = entry.servingDescription.orEmpty(),
                quantity = q.let { if (it == 1.0) "1" else it.toString() },
                calories = entry.calories.takeIf { it > 0 }?.let { (it / q).toInt().takeIf { v -> v > 0 }?.toString() }.orEmpty(),
                proteinGrams = entry.proteinGrams.takeIf { it > 0.0 }?.let { formatMacro(it / q) }.orEmpty(),
                carbGrams = entry.carbGrams.takeIf { it > 0.0 }?.let { formatMacro(it / q) }.orEmpty(),
                fatGrams = entry.fatGrams.takeIf { it > 0.0 }?.let { formatMacro(it / q) }.orEmpty(),
                fiberGrams = entry.fiberGrams.takeIf { it > 0.0 }?.let { formatMacro(it / q) }.orEmpty(),
                mealTime = entry.mealTime.orEmpty(),
                isWholeFoodBased = entry.isWholeFoodBased,
                isProcessed = entry.isProcessed,
                isFermented = entry.isFermented,
                barcode = entry.barcode.orEmpty(),
                source = entry.source,
                vitaminA = entry.vitaminA?.let { formatMacro(it) }.orEmpty(),
                vitaminC = entry.vitaminC?.let { formatMacro(it) }.orEmpty(),
                vitaminD = entry.vitaminD?.let { formatMacro(it) }.orEmpty(),
                vitaminB12 = entry.vitaminB12?.let { formatMacro(it) }.orEmpty(),
                calcium = entry.calcium?.let { formatMacro(it) }.orEmpty(),
                iron = entry.iron?.let { formatMacro(it) }.orEmpty(),
                potassium = entry.potassium?.let { formatMacro(it) }.orEmpty(),
                magnesium = entry.magnesium?.let { formatMacro(it) }.orEmpty(),
                sodium = entry.sodium?.let { formatMacro(it) }.orEmpty(),
                zinc = entry.zinc?.let { formatMacro(it) }.orEmpty(),
                vitaminE = entry.vitaminE?.let { formatMacro(it) }.orEmpty(),
                vitaminK = entry.vitaminK?.let { formatMacro(it) }.orEmpty(),
                vitaminB1 = entry.vitaminB1?.let { formatMacro(it) }.orEmpty(),
                vitaminB2 = entry.vitaminB2?.let { formatMacro(it) }.orEmpty(),
                vitaminB3 = entry.vitaminB3?.let { formatMacro(it) }.orEmpty(),
                vitaminB6 = entry.vitaminB6?.let { formatMacro(it) }.orEmpty(),
                folate = entry.folate?.let { formatMacro(it) }.orEmpty(),
                biotin = entry.biotin?.let { formatMacro(it) }.orEmpty(),
                pantothenicAcid = entry.pantothenicAcid?.let { formatMacro(it) }.orEmpty(),
                phosphorus = entry.phosphorus?.let { formatMacro(it) }.orEmpty(),
                iodine = entry.iodine?.let { formatMacro(it) }.orEmpty(),
                selenium = entry.selenium?.let { formatMacro(it) }.orEmpty(),
                copper = entry.copper?.let { formatMacro(it) }.orEmpty(),
                manganese = entry.manganese?.let { formatMacro(it) }.orEmpty(),
                chromium = entry.chromium?.let { formatMacro(it) }.orEmpty(),
                molybdenum = entry.molybdenum?.let { formatMacro(it) }.orEmpty()
            )
        )
    }

    private fun formatMacro(v: Double): String {
        return if (v == v.toLong().toDouble()) v.toLong().toString() else "%.1f".format(v)
    }

    fun saveForm() {
        val form = formState.value.form
        if (form.foodName.isBlank()) return

        viewModelScope.launch {
            val qty = form.quantity.toDoubleOrNull()?.coerceAtLeast(0.01) ?: 1.0
            nutritionRepository.saveFoodEntry(
                FoodEntryInput(
                    id = form.editingId,
                    date = today,
                    mealName = form.mealName,
                    foodName = form.foodName.trim(),
                    brandName = form.brandName.ifBlank { null },
                    servingDescription = form.servingDescription.ifBlank { null },
                    quantity = qty,
                    calories = form.calories.toIntOrNull()?.let { (it * qty).toInt() },
                    proteinGrams = form.proteinGrams.toDoubleOrNull()?.let { it * qty },
                    carbGrams = form.carbGrams.toDoubleOrNull()?.let { it * qty },
                    fatGrams = form.fatGrams.toDoubleOrNull()?.let { it * qty },
                    fiberGrams = form.fiberGrams.toDoubleOrNull()?.let { it * qty },
                    mealTime = form.mealTime.ifBlank { null },
                    isWholeFoodBased = form.isWholeFoodBased,
                    isProcessed = form.isProcessed,
                    isFermented = form.isFermented,
                    barcode = form.barcode.ifBlank { null },
                    source = form.source,
                    vitaminA = form.vitaminA.toDoubleOrNull(),
                    vitaminC = form.vitaminC.toDoubleOrNull(),
                    vitaminD = form.vitaminD.toDoubleOrNull(),
                    vitaminB12 = form.vitaminB12.toDoubleOrNull(),
                    calcium = form.calcium.toDoubleOrNull(),
                    iron = form.iron.toDoubleOrNull(),
                    potassium = form.potassium.toDoubleOrNull(),
                    magnesium = form.magnesium.toDoubleOrNull(),
                    sodium = form.sodium.toDoubleOrNull(),
                    zinc = form.zinc.toDoubleOrNull(),
                    vitaminE = form.vitaminE.toDoubleOrNull(),
                    vitaminK = form.vitaminK.toDoubleOrNull(),
                    vitaminB1 = form.vitaminB1.toDoubleOrNull(),
                    vitaminB2 = form.vitaminB2.toDoubleOrNull(),
                    vitaminB3 = form.vitaminB3.toDoubleOrNull(),
                    vitaminB6 = form.vitaminB6.toDoubleOrNull(),
                    folate = form.folate.toDoubleOrNull(),
                    biotin = form.biotin.toDoubleOrNull(),
                    pantothenicAcid = form.pantothenicAcid.toDoubleOrNull(),
                    phosphorus = form.phosphorus.toDoubleOrNull(),
                    iodine = form.iodine.toDoubleOrNull(),
                    selenium = form.selenium.toDoubleOrNull(),
                    copper = form.copper.toDoubleOrNull(),
                    manganese = form.manganese.toDoubleOrNull(),
                    chromium = form.chromium.toDoubleOrNull(),
                    molybdenum = form.molybdenum.toDoubleOrNull()
                )
            )
            habitAutoUpdateUseCase(today)
            formState.value = FormVisibilityState()
        }
    }

    fun deleteEntry(id: Long) {
        viewModelScope.launch {
            nutritionRepository.deleteFoodEntry(id)
        }
    }

    fun toggleSaved(id: Long, saved: Boolean) {
        viewModelScope.launch {
            nutritionRepository.setFoodEntrySaved(id, saved)
        }
    }

    fun showQuickAddDialog(food: QuickAddFoodUiState) {
        formState.update {
            it.copy(
                quickAddFood = food,
                quickAddMealName = food.defaultMealName,
                quickAddQuantity = "1",
                quickAddTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
            )
        }
    }

    fun hideQuickAddDialog() {
        formState.update { it.copy(quickAddFood = null) }
    }

    fun updateQuickAddMealName(name: String) {
        formState.update { it.copy(quickAddMealName = name) }
    }

    fun updateQuickAddQuantity(q: String) {
        formState.update { it.copy(quickAddQuantity = q) }
    }

    fun updateQuickAddTime(t: String) {
        formState.update { it.copy(quickAddTime = t) }
    }

    fun confirmQuickAdd() {
        val state = formState.value
        val food = state.quickAddFood ?: return
        val qty = state.quickAddQuantity.toDoubleOrNull()?.coerceAtLeast(0.01) ?: 1.0
        viewModelScope.launch {
            nutritionRepository.saveFoodEntry(
                FoodEntryInput(
                    id = 0,
                    date = today,
                    mealName = state.quickAddMealName.ifBlank { food.defaultMealName },
                    foodName = food.foodName,
                    brandName = food.brandName,
                    servingDescription = food.servingDescription,
                    calories = food.calories.takeIf { it > 0 }?.let { (it * qty).toInt() },
                    proteinGrams = food.proteinGrams.takeIf { it > 0.0 }?.let { it * qty },
                    carbGrams = food.carbGrams.takeIf { it > 0.0 }?.let { it * qty },
                    fatGrams = food.fatGrams.takeIf { it > 0.0 }?.let { it * qty },
                    fiberGrams = food.fiberGrams.takeIf { it > 0.0 }?.let { it * qty },
                    mealTime = state.quickAddTime.ifBlank { null },
                    isWholeFoodBased = food.isWholeFoodBased,
                    isProcessed = food.isProcessed,
                    isFermented = food.isFermented,
                    barcode = food.barcode,
                    source = food.source,
                    vitaminA = food.vitaminA,
                    vitaminC = food.vitaminC,
                    vitaminD = food.vitaminD,
                    vitaminB12 = food.vitaminB12,
                    calcium = food.calcium,
                    iron = food.iron,
                    potassium = food.potassium,
                    magnesium = food.magnesium,
                    sodium = food.sodium,
                    zinc = food.zinc,
                    vitaminE = food.vitaminE,
                    vitaminK = food.vitaminK,
                    vitaminB1 = food.vitaminB1,
                    vitaminB2 = food.vitaminB2,
                    vitaminB3 = food.vitaminB3,
                    vitaminB6 = food.vitaminB6,
                    folate = food.folate,
                    biotin = food.biotin,
                    pantothenicAcid = food.pantothenicAcid,
                    phosphorus = food.phosphorus,
                    iodine = food.iodine,
                    selenium = food.selenium,
                    copper = food.copper,
                    manganese = food.manganese,
                    chromium = food.chromium,
                    molybdenum = food.molybdenum
                )
            )
            habitAutoUpdateUseCase(today)
            formState.update { it.copy(quickAddFood = null) }
        }
    }

    fun copyYesterday() {
        viewModelScope.launch {
            val yesterday = LocalDate.now().minusDays(1).toString()
            val nowTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
            val entries = nutritionRepository.getFoodEntriesForDate(yesterday)
            if (entries.isEmpty()) return@launch
            entries.forEach { entry ->
                nutritionRepository.saveFoodEntry(
                    FoodEntryInput(
                        id = 0,
                        date = today,
                        mealName = entry.mealName,
                        foodName = entry.foodName,
                        brandName = entry.brandName,
                        servingDescription = entry.servingDescription,
                        calories = entry.calories,
                        proteinGrams = entry.proteinGrams,
                        carbGrams = entry.carbGrams,
                        fatGrams = entry.fatGrams,
                        fiberGrams = entry.fiberGrams,
                        mealTime = nowTime,
                        isWholeFoodBased = entry.isWholeFoodBased,
                        isProcessed = entry.isProcessed,
                        isFermented = entry.isFermented
                    )
                )
            }
            habitAutoUpdateUseCase(today)
        }
    }

    // ── Barcode Scanner ──────────────────────────────────────────────────────

    fun showScanner() {
        formState.update { it.copy(isScannerVisible = true, barcodeMessage = null) }
    }

    fun hideScanner() {
        formState.update { it.copy(isScannerVisible = false) }
    }

    // ── Nutrition Label OCR ──────────────────────────────────────────────────

    fun showLabelScanner() {
        formState.update { it.copy(isLabelScannerVisible = true) }
    }

    fun hideLabelScanner() {
        formState.update { it.copy(isLabelScannerVisible = false) }
    }

    fun onLabelOcrText(text: String) {
        formState.update { state ->
            state.copy(
                isLabelScannerVisible = false,
                labelScanMessage = null,
                ocrConfirmMessage = "Label scan added nutrition details. Please review before saving.",
                form = NutritionLabelOcrParser.parse(text, state.form)
            )
        }
    }

    fun logUsualMeal(meal: UsualMealUiState, mealName: String) {
        viewModelScope.launch {
            nutritionRepository.saveFoodEntry(
                FoodEntryInput(
                    id = 0,
                    date = today,
                    mealName = mealName,
                    foodName = meal.foodName,
                    brandName = meal.brandName,
                    servingDescription = meal.servingDescription,
                    calories = meal.calories.takeIf { it > 0 },
                    proteinGrams = meal.proteinGrams.takeIf { it > 0.0 },
                    carbGrams = meal.carbGrams.takeIf { it > 0.0 },
                    fatGrams = meal.fatGrams.takeIf { it > 0.0 },
                    fiberGrams = meal.fiberGrams.takeIf { it > 0.0 },
                    mealTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
                    isWholeFoodBased = meal.isWholeFoodBased,
                    isProcessed = meal.isProcessed,
                    isFermented = meal.isFermented
                )
            )
            habitAutoUpdateUseCase(today)
        }
    }

    // ── AI Quick Log ─────────────────────────────────────────────────────────

    fun showAiLog() {
        formState.update { it.copy(isAiLogVisible = true, isVisible = false, aiInput = "", aiConfidenceMessage = null) }
    }

    fun hideAiLog() {
        formState.update { it.copy(isAiLogVisible = false, aiInput = "", isAiParsing = false) }
    }

    fun onAiInputChange(text: String) {
        formState.update { it.copy(aiInput = text) }
    }

    fun submitAiLog() {
        val input = formState.value.aiInput.trim()
        if (input.isBlank()) return
        viewModelScope.launch {
            formState.update { it.copy(isAiParsing = true) }
            val estimate = aiFoodLoggingService.parse(input)
            val mealTimeNow = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
            val confidenceMsg = "AI estimate — confidence: ${estimate.confidence}. ${estimate.notes}"
            formState.update {
                it.copy(
                    isAiLogVisible = false,
                    isAiParsing = false,
                    aiInput = "",
                    isVisible = true,
                    aiConfidenceMessage = confidenceMsg,
                    form = FoodEntryFormUiState(
                        mealTime = mealTimeNow,
                        foodName = estimate.mealName,
                        calories = estimate.calories.takeIf { c -> c > 0 }?.toString().orEmpty(),
                        proteinGrams = estimate.proteinGrams.takeIf { p -> p > 0.0 }?.toString().orEmpty(),
                        carbGrams = estimate.carbGrams.takeIf { c -> c > 0.0 }?.toString().orEmpty(),
                        fatGrams = estimate.fatGrams.takeIf { f -> f > 0.0 }?.toString().orEmpty(),
                        fiberGrams = estimate.fiberGrams.takeIf { f -> f > 0.0 }?.toString().orEmpty(),
                        isWholeFoodBased = estimate.isWholeFoodBased,
                        source = "AI_QUICK_LOG"
                    )
                )
            }
        }
    }

    fun onBarcodeDetected(barcode: String) {
        formState.update { it.copy(isScannerVisible = false) }
        val mealTimeNow = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        viewModelScope.launch {
            try {
                val result = foodLookupService.lookup(barcode)
                if (result != null) {
                    formState.update {
                        it.copy(
                            isVisible = true,
                            barcodeMessage = null,
                            form = FoodEntryFormUiState(
                                mealTime = mealTimeNow,
                                foodName = result.foodName,
                                brandName = result.brandName.orEmpty(),
                                servingDescription = result.servingDescription.orEmpty(),
                                calories = result.calories?.toString().orEmpty(),
                                proteinGrams = result.proteinGrams?.toString().orEmpty(),
                                carbGrams = result.carbGrams?.toString().orEmpty(),
                                fatGrams = result.fatGrams?.toString().orEmpty(),
                                fiberGrams = result.fiberGrams?.toString().orEmpty(),
                                barcode = barcode,
                                source = result.source,
                                vitaminA = result.vitaminA?.let { formatMacro(it) }.orEmpty(),
                                vitaminC = result.vitaminC?.let { formatMacro(it) }.orEmpty(),
                                vitaminD = result.vitaminD?.let { formatMacro(it) }.orEmpty(),
                                vitaminB12 = result.vitaminB12?.let { formatMacro(it) }.orEmpty(),
                                calcium = result.calcium?.let { formatMacro(it) }.orEmpty(),
                                iron = result.iron?.let { formatMacro(it) }.orEmpty(),
                                potassium = result.potassium?.let { formatMacro(it) }.orEmpty(),
                                magnesium = result.magnesium?.let { formatMacro(it) }.orEmpty(),
                                sodium = result.sodium?.let { formatMacro(it) }.orEmpty(),
                                zinc = result.zinc?.let { formatMacro(it) }.orEmpty(),
                                vitaminE = result.vitaminE?.let { formatMacro(it) }.orEmpty(),
                                vitaminK = result.vitaminK?.let { formatMacro(it) }.orEmpty(),
                                vitaminB1 = result.vitaminB1?.let { formatMacro(it) }.orEmpty(),
                                vitaminB2 = result.vitaminB2?.let { formatMacro(it) }.orEmpty(),
                                vitaminB3 = result.vitaminB3?.let { formatMacro(it) }.orEmpty(),
                                vitaminB6 = result.vitaminB6?.let { formatMacro(it) }.orEmpty(),
                                folate = result.folate?.let { formatMacro(it) }.orEmpty(),
                                biotin = result.biotin?.let { formatMacro(it) }.orEmpty(),
                                pantothenicAcid = result.pantothenicAcid?.let { formatMacro(it) }.orEmpty(),
                                phosphorus = result.phosphorus?.let { formatMacro(it) }.orEmpty(),
                                iodine = result.iodine?.let { formatMacro(it) }.orEmpty(),
                                selenium = result.selenium?.let { formatMacro(it) }.orEmpty(),
                                copper = result.copper?.let { formatMacro(it) }.orEmpty(),
                                manganese = result.manganese?.let { formatMacro(it) }.orEmpty(),
                                chromium = result.chromium?.let { formatMacro(it) }.orEmpty(),
                                molybdenum = result.molybdenum?.let { formatMacro(it) }.orEmpty()
                            ),
                            labelScanMessage = "Nutrition facts incomplete. Scan label?"
                        )
                    }
                } else {
                    formState.update {
                        it.copy(
                            isVisible = true,
                            barcodeMessage = "Food not found. Enter details manually.",
                            form = FoodEntryFormUiState(
                                mealTime = mealTimeNow,
                                barcode = barcode,
                                source = "BARCODE_MANUAL"
                            )
                        )
                    }
                }
            } catch (_: java.io.IOException) {
                formState.update {
                    it.copy(
                        isVisible = true,
                        barcodeMessage = "Network error. Check your connection and try again.",
                        form = FoodEntryFormUiState(
                            mealTime = mealTimeNow,
                            barcode = barcode,
                            source = "BARCODE_MANUAL"
                        )
                    )
                }
            }
        }
    }
}

private data class FormVisibilityState(
    val isVisible: Boolean = false,
    val form: FoodEntryFormUiState = FoodEntryFormUiState(),
    val isScannerVisible: Boolean = false,
    val isLabelScannerVisible: Boolean = false,
    val barcodeMessage: String? = null,
    val labelScanMessage: String? = null,
    val ocrConfirmMessage: String? = null,
    val isAiLogVisible: Boolean = false,
    val aiInput: String = "",
    val isAiParsing: Boolean = false,
    val aiConfidenceMessage: String? = null,
    val quickAddFood: QuickAddFoodUiState? = null,
    val quickAddMealName: String = "",
    val quickAddQuantity: String = "1",
    val quickAddTime: String = ""
)

private fun List<FoodEntry>.toUiState(
    proteinGoalMin: Int,
    proteinGoalMax: Int,
    macroTarget: MacroTarget?,
    goal: String?,
    dietPreference: String?,
    form: FormVisibilityState,
    allEntries: List<FoodEntry>,
    hour: Int,
    plannedMeals: List<PlannedMeal> = emptyList()
): NutritionUiState {
    val uiEntries = map { it.toUiState() }
    val meals = listOf("Breakfast", "Lunch", "Dinner", "Snack")
    val usualMeals = detectUsualMeals(allEntries, todayEntries = this, hour)
    val suggestedFoodNames = usualMeals.map { it.foodName.lowercase().trim() }.toSet()
    val recentFoods = allEntries
        .sortedByDescending { it.id }
        .distinctBy { it.foodName.lowercase() + "|" + (it.brandName?.lowercase() ?: "") }
        .filter { it.foodName.lowercase().trim() !in suggestedFoodNames }
        .take(8)
        .map { it.toQuickAddUiState() }
    val savedFoods = allEntries
        .filter { it.isSaved && it.foodName.lowercase().trim() !in suggestedFoodNames }
        .sortedByDescending { it.id }
        .distinctBy { it.foodName.lowercase() + "|" + (it.brandName?.lowercase() ?: "") }
        .map { it.toQuickAddUiState() }
    val suggestions = if (plannedMeals.isNotEmpty()) {
        plannedMeals.map { meal ->
            RecipeSuggestionUiState(
                mealName = PlannedMealSlotKey.toDisplayLabel(meal.slotKey),
                food = QuickAddFoodUiState(
                    sourceEntryId = 0L,
                    foodName = meal.recipeName,
                    brandName = null,
                    servingDescription = "1 serving",
                    calories = meal.calories,
                    proteinGrams = meal.proteinGrams,
                    carbGrams = meal.carbGrams,
                    fatGrams = meal.fatGrams,
                    fiberGrams = meal.fiberGrams,
                    defaultMealName = PlannedMealSlotKey.toDisplayLabel(meal.slotKey),
                    isSaved = false,
                    isWholeFoodBased = false,
                    isProcessed = false,
                    isFermented = false
                )
            )
        }
    } else {
        RecipeSuggestionEngine.suggest(
            savedFoods = savedFoods,
            recentFoods = recentFoods,
            consumedCalories = uiEntries.sumOf { it.calories },
            consumedProtein = uiEntries.sumOf { it.proteinGrams },
            macroTarget = macroTarget,
            goal = goal,
            dietPreference = dietPreference
        )
    }
    return NutritionUiState(
        calories = uiEntries.sumOf { it.calories },
        proteinGrams = uiEntries.sumOf { it.proteinGrams },
        carbGrams = uiEntries.sumOf { it.carbGrams },
        fatGrams = uiEntries.sumOf { it.fatGrams },
        fiberGrams = uiEntries.sumOf { it.fiberGrams },
        proteinGoalMin = proteinGoalMin,
        proteinGoalMax = proteinGoalMax,
        calorieTarget = macroTarget?.calorieTarget ?: 2000,
        carbGoal = macroTarget?.carbTargetGrams ?: 0,
        fatGoal = macroTarget?.fatTargetGrams ?: 0,
        fiberGoal = macroTarget?.fiberTargetGrams ?: 0,
        latestMealTime = uiEntries.mapNotNull { it.mealTime }.maxOrNull() ?: "Not logged",
        mealSections = meals.map { meal ->
            MealSectionUiState(
                mealName = meal,
                entries = uiEntries.filter { it.mealName == meal }
            )
        },
        isFormVisible = form.isVisible,
        form = form.form,
        recentFoods = recentFoods,
        savedFoods = savedFoods,
        usualMeals = usualMeals,
        isScannerVisible = form.isScannerVisible,
        isLabelScannerVisible = form.isLabelScannerVisible,
        barcodeMessage = form.barcodeMessage,
        labelScanMessage = form.labelScanMessage,
        ocrConfirmMessage = form.ocrConfirmMessage,
        isAiLogVisible = form.isAiLogVisible,
        aiInput = form.aiInput,
        isAiParsing = form.isAiParsing,
        aiConfidenceMessage = form.aiConfidenceMessage,
        quickAddDialogFood = form.quickAddFood,
        quickAddMealName = form.quickAddMealName,
        quickAddQuantity = form.quickAddQuantity,
        quickAddTime = form.quickAddTime,
        suggestions = suggestions
    )
}

private fun FoodEntry.toUiState(): FoodEntryUiState {
    return FoodEntryUiState(
        id = id,
        mealName = mealName,
        foodName = foodName,
        brandName = brandName,
        servingDescription = servingDescription,
        quantity = quantity,
        calories = calories ?: 0,
        proteinGrams = proteinGrams ?: 0.0,
        carbGrams = carbGrams ?: 0.0,
        fatGrams = fatGrams ?: 0.0,
        fiberGrams = fiberGrams ?: 0.0,
        mealTime = mealTime,
        isWholeFoodBased = isWholeFoodBased,
        isProcessed = isProcessed,
        isFermented = isFermented,
        isSaved = isSaved,
        barcode = barcode,
        source = source,
        vitaminA = vitaminA,
        vitaminC = vitaminC,
        vitaminD = vitaminD,
        vitaminB12 = vitaminB12,
        calcium = calcium,
        iron = iron,
        potassium = potassium,
        magnesium = magnesium,
        sodium = sodium,
        zinc = zinc,
        vitaminE = vitaminE,
        vitaminK = vitaminK,
        vitaminB1 = vitaminB1,
        vitaminB2 = vitaminB2,
        vitaminB3 = vitaminB3,
        vitaminB6 = vitaminB6,
        folate = folate,
        biotin = biotin,
        pantothenicAcid = pantothenicAcid,
        phosphorus = phosphorus,
        iodine = iodine,
        selenium = selenium,
        copper = copper,
        manganese = manganese,
        chromium = chromium,
        molybdenum = molybdenum
    )
}

private fun FoodEntry.toQuickAddUiState(): QuickAddFoodUiState {
    return QuickAddFoodUiState(
        sourceEntryId = id,
        foodName = foodName,
        brandName = brandName,
        servingDescription = servingDescription,
        calories = calories ?: 0,
        proteinGrams = proteinGrams ?: 0.0,
        carbGrams = carbGrams ?: 0.0,
        fatGrams = fatGrams ?: 0.0,
        fiberGrams = fiberGrams ?: 0.0,
        defaultMealName = mealName,
        isSaved = isSaved,
        isWholeFoodBased = isWholeFoodBased,
        isProcessed = isProcessed,
        isFermented = isFermented,
        barcode = barcode,
        source = source,
        vitaminA = vitaminA,
        vitaminC = vitaminC,
        vitaminD = vitaminD,
        vitaminB12 = vitaminB12,
        calcium = calcium,
        iron = iron,
        potassium = potassium,
        magnesium = magnesium,
        sodium = sodium,
        zinc = zinc,
        vitaminE = vitaminE,
        vitaminK = vitaminK,
        vitaminB1 = vitaminB1,
        vitaminB2 = vitaminB2,
        vitaminB3 = vitaminB3,
        vitaminB6 = vitaminB6,
        folate = folate,
        biotin = biotin,
        pantothenicAcid = pantothenicAcid,
        phosphorus = phosphorus,
        iodine = iodine,
        selenium = selenium,
        copper = copper,
        manganese = manganese,
        chromium = chromium,
        molybdenum = molybdenum
    )
}

private fun detectUsualMeals(
    allEntries: List<FoodEntry>,
    todayEntries: List<FoodEntry>,
    hour: Int
): List<UsualMealUiState> {
    // Group by food name + meal type only — brand/serving are optional and vary between logs
    fun FoodEntry.groupKey() =
        "${foodName.lowercase().trim()}|${mealName.lowercase().trim()}"

    val todayKeys = todayEntries.map { it.groupKey() }.toSet()

    return allEntries
        .groupBy { it.groupKey() }
        .filter { (_, entries) -> entries.size >= 3 }
        .map { (key, entries) ->
            val latest = entries.maxBy { it.id }
            UsualMealUiState(
                foodName = latest.foodName,
                brandName = latest.brandName,
                servingDescription = latest.servingDescription,
                mealName = latest.mealName,
                calories = latest.calories ?: 0,
                proteinGrams = latest.proteinGrams ?: 0.0,
                carbGrams = latest.carbGrams ?: 0.0,
                fatGrams = latest.fatGrams ?: 0.0,
                fiberGrams = latest.fiberGrams ?: 0.0,
                timesLogged = entries.size,
                loggedToday = key in todayKeys,
                isWholeFoodBased = latest.isWholeFoodBased,
                isProcessed = latest.isProcessed,
                isFermented = latest.isFermented
            )
        }
        .sortedWith(
            compareByDescending<UsualMealUiState> { it.timesLogged }
                .thenBy { mealTimeScore(it.mealName, hour) }
        )
        .take(8)
}

private fun mealTimeScore(mealName: String, hour: Int): Int = when {
    hour < 11 && mealName.equals("Breakfast", ignoreCase = true) -> 0
    hour in 11..14 && mealName.equals("Lunch", ignoreCase = true) -> 0
    hour >= 17 && mealName.equals("Dinner", ignoreCase = true) -> 0
    hour in 14..23 && mealName.equals("Snack", ignoreCase = true) -> 0
    else -> 1
}

class NutritionViewModelFactory(
    private val nutritionRepository: NutritionRepository,
    private val macroTargetRepository: MacroTargetRepository,
    private val userProfileRepository: UserProfileRepository,
    private val foodLookupService: FoodLookupService,
    private val aiFoodLoggingService: AiFoodLoggingService,
    private val habitAutoUpdateUseCase: HabitAutoUpdateUseCase,
    private val plannedMealRepository: PlannedMealRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NutritionViewModel::class.java)) {
            return NutritionViewModel(
                nutritionRepository = nutritionRepository,
                macroTargetRepository = macroTargetRepository,
                userProfileRepository = userProfileRepository,
                foodLookupService = foodLookupService,
                aiFoodLoggingService = aiFoodLoggingService,
                habitAutoUpdateUseCase = habitAutoUpdateUseCase,
                plannedMealRepository = plannedMealRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
