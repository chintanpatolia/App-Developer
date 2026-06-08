package com.dailyhealthcoach.domain.usecase

import com.dailyhealthcoach.domain.model.DashboardSummary
import com.dailyhealthcoach.domain.recommendation.NextDayRecommendationService
import com.dailyhealthcoach.domain.recovery.RecoveryScoreCalculator
import com.dailyhealthcoach.domain.repository.HabitRepository
import com.dailyhealthcoach.domain.repository.MacroTargetRepository
import com.dailyhealthcoach.domain.repository.NutritionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetDashboardSummaryUseCase(
    private val habitRepository: HabitRepository,
    private val macroTargetRepository: MacroTargetRepository,
    private val nutritionRepository: NutritionRepository,
    private val recoveryScoreCalculator: RecoveryScoreCalculator,
    private val recommendationService: NextDayRecommendationService
) {
    operator fun invoke(date: String): Flow<DashboardSummary> {
        return combine(
            habitRepository.observeActiveHabits(),
            habitRepository.observeLogsForDate(date),
            macroTargetRepository.observeActiveTarget(),
            nutritionRepository.observeFoodEntriesForDate(date)
        ) { habits, logs, macroTarget, foodEntries ->
            val todayHabits = habits.filter { it.frequencyType != "INTERVAL" }
            val completedHabitIds = logs
                .filter { it.status == "COMPLETE" }
                .map { it.habitDefinitionId }
                .toSet()
            val proteinConsumed = foodEntries.sumOf { it.proteinGrams ?: 0.0 }.toInt()

            DashboardSummary(
                totalHabits = todayHabits.size,
                completedHabits = todayHabits.count { it.id in completedHabitIds },
                proteinConsumedGrams = proteinConsumed,
                proteinMinGoalGrams = macroTarget?.proteinMinGrams ?: 170,
                proteinMaxGoalGrams = macroTarget?.proteinMaxGrams ?: 200,
                steps = 6_400,
                stepGoal = 8_000,
                sleepHours = 6.8,
                recoveryScore = recoveryScoreCalculator.placeholderScore(),
                nextDayRecommendation = recommendationService.placeholderRecommendation()
            )
        }
    }
}
