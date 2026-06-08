package com.dailyhealthcoach.ui.nutrition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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
    macroTargetRepository: MacroTargetRepository
) : ViewModel() {
    private val today = LocalDate.now().toString()
    private val formState = MutableStateFlow(FormVisibilityState())

    val uiState: StateFlow<NutritionUiState> = combine(
        nutritionRepository.observeFoodEntriesForDate(today),
        macroTargetRepository.observeActiveTarget(),
        formState
    ) { entries, macroTarget, form ->
        entries.toUiState(
            proteinGoalMin = macroTarget?.proteinMinGrams ?: 170,
            proteinGoalMax = macroTarget?.proteinMaxGrams ?: 200,
            form = form
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
                    isFermented = form.isFermented
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
}

private data class FormVisibilityState(
    val isVisible: Boolean = false,
    val form: FoodEntryFormUiState = FoodEntryFormUiState()
)

private fun List<FoodEntry>.toUiState(
    proteinGoalMin: Int,
    proteinGoalMax: Int,
    form: FormVisibilityState
): NutritionUiState {
    val uiEntries = map { it.toUiState() }
    val meals = listOf("Breakfast", "Lunch", "Dinner", "Snack")
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
        form = form.form
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
        isFermented = isFermented
    )
}

class NutritionViewModelFactory(
    private val nutritionRepository: NutritionRepository,
    private val macroTargetRepository: MacroTargetRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NutritionViewModel::class.java)) {
            return NutritionViewModel(
                nutritionRepository = nutritionRepository,
                macroTargetRepository = macroTargetRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
