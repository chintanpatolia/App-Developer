package com.dailyhealthcoach.ui.nutrition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dailyhealthcoach.ai.AiFoodLoggingService
import com.dailyhealthcoach.barcode.FoodLookupService
import com.dailyhealthcoach.domain.model.FoodEntry
import com.dailyhealthcoach.domain.model.FoodEntryInput
import com.dailyhealthcoach.domain.repository.MacroTargetRepository
import com.dailyhealthcoach.domain.repository.NutritionRepository
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NutritionViewModel(
    private val nutritionRepository: NutritionRepository,
    macroTargetRepository: MacroTargetRepository,
    private val foodLookupService: FoodLookupService,
    private val aiFoodLoggingService: AiFoodLoggingService
) : ViewModel() {
    private val today = LocalDate.now().toString()
    private val formState = MutableStateFlow(FormVisibilityState())

    val uiState: StateFlow<NutritionUiState> = combine(
        nutritionRepository.observeFoodEntriesForDate(today),
        macroTargetRepository.observeActiveTarget(),
        formState,
        nutritionRepository.observeAll()
    ) { entries, macroTarget, form, allEntries ->
        entries.toUiState(
            proteinGoalMin = macroTarget?.proteinMinGrams ?: 170,
            proteinGoalMax = macroTarget?.proteinMaxGrams ?: 200,
            form = form,
            allEntries = allEntries
        )
    }.stateIn(
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
        formState.value = FormVisibilityState(
            isVisible = true,
            form = FoodEntryFormUiState(
                editingId = entry.id,
                mealName = entry.mealName,
                foodName = entry.foodName,
                brandName = entry.brandName.orEmpty(),
                servingDescription = entry.servingDescription.orEmpty(),
                calories = entry.calories.takeIf { it > 0 }?.toString().orEmpty(),
                proteinGrams = entry.proteinGrams.takeIf { it > 0.0 }?.toString().orEmpty(),
                carbGrams = entry.carbGrams.takeIf { it > 0.0 }?.toString().orEmpty(),
                fatGrams = entry.fatGrams.takeIf { it > 0.0 }?.toString().orEmpty(),
                fiberGrams = entry.fiberGrams.takeIf { it > 0.0 }?.toString().orEmpty(),
                mealTime = entry.mealTime.orEmpty(),
                isWholeFoodBased = entry.isWholeFoodBased,
                isProcessed = entry.isProcessed,
                isFermented = entry.isFermented
            )
        )
    }

    fun saveForm() {
        val form = formState.value.form
        if (form.foodName.isBlank()) return

        viewModelScope.launch {
            nutritionRepository.saveFoodEntry(
                FoodEntryInput(
                    id = form.editingId,
                    date = today,
                    mealName = form.mealName,
                    foodName = form.foodName.trim(),
                    brandName = form.brandName.ifBlank { null },
                    servingDescription = form.servingDescription.ifBlank { null },
                    calories = form.calories.toIntOrNull(),
                    proteinGrams = form.proteinGrams.toDoubleOrNull(),
                    carbGrams = form.carbGrams.toDoubleOrNull(),
                    fatGrams = form.fatGrams.toDoubleOrNull(),
                    fiberGrams = form.fiberGrams.toDoubleOrNull(),
                    mealTime = form.mealTime.ifBlank { null },
                    isWholeFoodBased = form.isWholeFoodBased,
                    isProcessed = form.isProcessed,
                    isFermented = form.isFermented,
                    barcode = form.barcode.ifBlank { null },
                    source = form.source
                )
            )
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

    fun quickAddFood(food: QuickAddFoodUiState) {
        viewModelScope.launch {
            nutritionRepository.saveFoodEntry(
                FoodEntryInput(
                    id = 0,
                    date = today,
                    mealName = food.defaultMealName,
                    foodName = food.foodName,
                    brandName = food.brandName,
                    servingDescription = food.servingDescription,
                    calories = food.calories.takeIf { it > 0 },
                    proteinGrams = food.proteinGrams.takeIf { it > 0.0 },
                    carbGrams = food.carbGrams.takeIf { it > 0.0 },
                    fatGrams = food.fatGrams.takeIf { it > 0.0 },
                    fiberGrams = food.fiberGrams.takeIf { it > 0.0 },
                    mealTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
                    isWholeFoodBased = food.isWholeFoodBased,
                    isProcessed = food.isProcessed,
                    isFermented = food.isFermented
                )
            )
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
        }
    }

    // ── Barcode Scanner ──────────────────────────────────────────────────────

    fun showScanner() {
        formState.update { it.copy(isScannerVisible = true, barcodeMessage = null) }
    }

    fun hideScanner() {
        formState.update { it.copy(isScannerVisible = false) }
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
                            source = result.source
                        )
                    )
                }
            } else {
                formState.update {
                    it.copy(
                        isVisible = true,
                        barcodeMessage = "Barcode $barcode not found. Enter food details manually.",
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
    val barcodeMessage: String? = null,
    val isAiLogVisible: Boolean = false,
    val aiInput: String = "",
    val isAiParsing: Boolean = false,
    val aiConfidenceMessage: String? = null
)

private fun List<FoodEntry>.toUiState(
    proteinGoalMin: Int,
    proteinGoalMax: Int,
    form: FormVisibilityState,
    allEntries: List<FoodEntry>
): NutritionUiState {
    val uiEntries = map { it.toUiState() }
    val meals = listOf("Breakfast", "Lunch", "Dinner", "Snack")
    val recentFoods = allEntries
        .sortedByDescending { it.id }
        .distinctBy { it.foodName.lowercase() + "|" + (it.brandName?.lowercase() ?: "") }
        .take(8)
        .map { it.toQuickAddUiState() }
    val savedFoods = allEntries
        .filter { it.isSaved }
        .sortedByDescending { it.id }
        .distinctBy { it.foodName.lowercase() + "|" + (it.brandName?.lowercase() ?: "") }
        .map { it.toQuickAddUiState() }
    return NutritionUiState(
        calories = uiEntries.sumOf { it.calories },
        proteinGrams = uiEntries.sumOf { it.proteinGrams },
        carbGrams = uiEntries.sumOf { it.carbGrams },
        fatGrams = uiEntries.sumOf { it.fatGrams },
        fiberGrams = uiEntries.sumOf { it.fiberGrams },
        proteinGoalMin = proteinGoalMin,
        proteinGoalMax = proteinGoalMax,
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
        isScannerVisible = form.isScannerVisible,
        barcodeMessage = form.barcodeMessage,
        isAiLogVisible = form.isAiLogVisible,
        aiInput = form.aiInput,
        isAiParsing = form.isAiParsing,
        aiConfidenceMessage = form.aiConfidenceMessage
    )
}

private fun FoodEntry.toUiState(): FoodEntryUiState {
    return FoodEntryUiState(
        id = id,
        mealName = mealName,
        foodName = foodName,
        brandName = brandName,
        servingDescription = servingDescription,
        calories = calories ?: 0,
        proteinGrams = proteinGrams ?: 0.0,
        carbGrams = carbGrams ?: 0.0,
        fatGrams = fatGrams ?: 0.0,
        fiberGrams = fiberGrams ?: 0.0,
        mealTime = mealTime,
        isWholeFoodBased = isWholeFoodBased,
        isProcessed = isProcessed,
        isFermented = isFermented,
        isSaved = isSaved
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
        isFermented = isFermented
    )
}

class NutritionViewModelFactory(
    private val nutritionRepository: NutritionRepository,
    private val macroTargetRepository: MacroTargetRepository,
    private val foodLookupService: FoodLookupService,
    private val aiFoodLoggingService: AiFoodLoggingService
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NutritionViewModel::class.java)) {
            return NutritionViewModel(
                nutritionRepository = nutritionRepository,
                macroTargetRepository = macroTargetRepository,
                foodLookupService = foodLookupService,
                aiFoodLoggingService = aiFoodLoggingService
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
