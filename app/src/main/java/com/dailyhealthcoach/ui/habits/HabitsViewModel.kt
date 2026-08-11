package com.dailyhealthcoach.ui.habits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dailyhealthcoach.domain.model.HabitStatus
import com.dailyhealthcoach.domain.model.HabitToday
import com.dailyhealthcoach.domain.usecase.GetTodayHabitsUseCase
import com.dailyhealthcoach.domain.usecase.SetHabitStatusForTodayUseCase
import java.time.LocalDate
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HabitsViewModel(
    getTodayHabitsUseCase: GetTodayHabitsUseCase,
    private val setHabitStatusForTodayUseCase: SetHabitStatusForTodayUseCase
) : ViewModel() {
    private val today = LocalDate.now().toString()

    val uiState: StateFlow<HabitsUiState> = getTodayHabitsUseCase(today)
        .map { habits -> habits.toUiState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HabitsUiState()
        )

    fun setStatus(habitId: Long, status: HabitStatus) {
        viewModelScope.launch {
            setHabitStatusForTodayUseCase(
                habitDefinitionId = habitId,
                date = today,
                status = status
            )
        }
    }
}

private fun List<HabitToday>.toUiState(): HabitsUiState {
    val trackableHabits = filter { it.isTodayTrackable }
    val completedCount = trackableHabits.count { it.status == HabitStatus.COMPLETE }
    val completionPercentage = if (trackableHabits.isEmpty()) {
        0
    } else {
        ((completedCount.toFloat() / trackableHabits.size.toFloat()) * 100).toInt()
    }

    return HabitsUiState(
        isLoading = false,
        completionPercentage = completionPercentage,
        completedCount = completedCount,
        trackableCount = trackableHabits.size,
        habits = map { habit ->
            HabitRowUiState(
                id = habit.id,
                name = habit.name,
                description = habit.description,
                frequencyLabel = habit.frequencyLabel,
                targetText = habit.targetPerWeek?.let { "$it/week" },
                status = habit.status,
                isTodayTrackable = habit.isTodayTrackable,
                autoCompleteSource = habit.notes?.takeIf { it.startsWith("Auto-completed from") }
            )
        }
    )
}

class HabitsViewModelFactory(
    private val getTodayHabitsUseCase: GetTodayHabitsUseCase,
    private val setHabitStatusForTodayUseCase: SetHabitStatusForTodayUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitsViewModel::class.java)) {
            return HabitsViewModel(
                getTodayHabitsUseCase = getTodayHabitsUseCase,
                setHabitStatusForTodayUseCase = setHabitStatusForTodayUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
