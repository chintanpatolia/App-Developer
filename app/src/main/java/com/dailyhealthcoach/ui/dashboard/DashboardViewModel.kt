package com.dailyhealthcoach.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dailyhealthcoach.domain.model.DashboardSummary
import com.dailyhealthcoach.domain.usecase.GetDashboardSummaryUseCase
import java.time.LocalDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class DashboardViewModel(
    getDashboardSummaryUseCase: GetDashboardSummaryUseCase,
    private val prefsRepository: DashboardPreferencesRepository
) : ViewModel() {
    val uiState: StateFlow<DashboardUiState> = getDashboardSummaryUseCase(
        LocalDate.now().toString()
    ).map { it.toUiState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DashboardUiState()
        )

    private val _layoutState = MutableStateFlow(
        DashboardLayoutState(cardConfigs = prefsRepository.loadLayout())
    )
    val layoutState: StateFlow<DashboardLayoutState> = _layoutState.asStateFlow()

    fun enterEditMode() { _layoutState.update { it.copy(isEditMode = true) } }
    fun exitEditMode() { _layoutState.update { it.copy(isEditMode = false) } }

    fun toggleWidget(key: DashboardCardKey) {
        if (key == DashboardCardKey.HEALTH_SCORE) return
        val updated = _layoutState.value.cardConfigs.map {
            if (it.key == key) it.copy(visible = !it.visible) else it
        }
        _layoutState.update { it.copy(cardConfigs = updated) }
        prefsRepository.saveLayout(updated)
    }

    fun moveWidget(fromIndex: Int, toIndex: Int) {
        val healthScore = _layoutState.value.cardConfigs
            .firstOrNull { it.key == DashboardCardKey.HEALTH_SCORE }
        val optional = _layoutState.value.cardConfigs
            .filter { it.key != DashboardCardKey.HEALTH_SCORE }
            .sortedBy { it.order }
            .toMutableList()
        if (fromIndex !in optional.indices || toIndex !in optional.indices) return
        val item = optional.removeAt(fromIndex)
        optional.add(toIndex, item)
        val reindexed = buildList {
            if (healthScore != null) add(healthScore.copy(order = 0))
            optional.forEachIndexed { i, c -> add(c.copy(order = i + 1)) }
        }
        _layoutState.update { it.copy(cardConfigs = reindexed) }
        prefsRepository.saveLayout(reindexed)
    }

    fun showResetConfirm() { _layoutState.update { it.copy(showResetConfirm = true) } }
    fun dismissResetConfirm() { _layoutState.update { it.copy(showResetConfirm = false) } }
    fun resetLayout() {
        prefsRepository.resetLayout()
        _layoutState.update { it.copy(
            cardConfigs = DashboardPreferencesRepository.DEFAULT_LAYOUT,
            showResetConfirm = false
        ) }
    }
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
        recoveryLabel = recoveryLabel,
        recoveryReasons = recoveryReasons,
        recoveryContributors = recoveryContributors.map { c ->
            RecoveryContributorUiState(
                label = c.label,
                deltaText = if (c.delta >= 0) "+${c.delta}" else "${c.delta}",
                detail = c.detail,
                isPositive = c.delta >= 0
            )
        },
        nextDayRecommendation = nextDayRecommendation?.let {
            DailyRecommendationUiState(
                title = it.title,
                explanation = it.explanation,
                suggestedFocus = it.suggestedFocus,
                reasons = it.reasonBullets
            )
        },
        workoutCompletedToday = workoutCompletedToday,
        caloriesToday = caloriesToday,
        calorieGoal = calorieGoal,
        weightKg = weightKg,
        bodyFatPercent = bodyFatPercent,
        todayWorkoutName = todayWorkoutName,
        todayWorkoutStatusLabel = todayWorkoutStatusLabel,
        healthScore = healthScore,
        coachLine = coachLine
    )
}

class DashboardViewModelFactory(
    private val getDashboardSummaryUseCase: GetDashboardSummaryUseCase,
    private val prefsRepository: DashboardPreferencesRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(getDashboardSummaryUseCase, prefsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
