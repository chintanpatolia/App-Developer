package com.dailyhealthcoach.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dailyhealthcoach.domain.model.DashboardSummary
import com.dailyhealthcoach.domain.usecase.GetDashboardSummaryUseCase
import java.time.LocalDate
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DashboardViewModel(
    getDashboardSummaryUseCase: GetDashboardSummaryUseCase
) : ViewModel() {
    val uiState: StateFlow<DashboardUiState> = getDashboardSummaryUseCase(
        LocalDate.now().toString()
    ).map { it.toUiState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DashboardUiState()
        )
}

private fun DashboardSummary.toUiState(): DashboardUiState {
    return DashboardUiState(
        isLoading = false,
        totalHabits = totalHabits,
        completedHabits = completedHabits,
        proteinConsumedGrams = proteinConsumedGrams,
        proteinMinGoalGrams = proteinMinGoalGrams,
        proteinMaxGoalGrams = proteinMaxGoalGrams,
        steps = steps,
        stepGoal = stepGoal,
        sleepHours = sleepHours,
        recoveryScore = recoveryScore,
        nextDayRecommendation = nextDayRecommendation
    )
}

class DashboardViewModelFactory(
    private val getDashboardSummaryUseCase: GetDashboardSummaryUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(getDashboardSummaryUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
