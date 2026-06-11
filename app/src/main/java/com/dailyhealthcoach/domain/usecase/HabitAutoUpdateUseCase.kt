package com.dailyhealthcoach.domain.usecase

import com.dailyhealthcoach.domain.model.BodyMetricLog
import com.dailyhealthcoach.domain.model.FoodEntry
import com.dailyhealthcoach.domain.model.HabitStatus
import com.dailyhealthcoach.domain.model.MacroTarget
import com.dailyhealthcoach.domain.model.UserProfile
import com.dailyhealthcoach.domain.model.Workout
import com.dailyhealthcoach.domain.model.WorkoutStatus
import com.dailyhealthcoach.domain.repository.BodyMetricRepository
import com.dailyhealthcoach.domain.repository.HabitRepository
import com.dailyhealthcoach.domain.repository.MacroTargetRepository
import com.dailyhealthcoach.domain.repository.NutritionRepository
import com.dailyhealthcoach.domain.repository.UserProfileRepository
import com.dailyhealthcoach.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.first

class HabitAutoUpdateUseCase(
    private val habitRepository: HabitRepository,
    private val nutritionRepository: NutritionRepository,
    private val workoutRepository: WorkoutRepository,
    private val bodyMetricRepository: BodyMetricRepository,
    private val macroTargetRepository: MacroTargetRepository,
    private val userProfileRepository: UserProfileRepository
) {
    suspend operator fun invoke(date: String) {
        val habits = habitRepository.observeActiveHabits().first()
        val logs = habitRepository.observeLogsForDate(date).first()
        val logsByHabitId = logs.associateBy { it.habitDefinitionId }

        val foodEntries = nutritionRepository.getFoodEntriesForDate(date)
        val workouts = workoutRepository.observeWorkoutsForDate(date).first()
        val bodyMetrics = bodyMetricRepository.observeForDate(date).first()
        val macroTarget = macroTargetRepository.observeActiveTarget().first()
        val userProfile = userProfileRepository.observeUserProfile().first()

        for (habit in habits) {
            val existingLog = logsByHabitId[habit.id]
            val isAutoCompleted = existingLog?.notes?.startsWith("Auto-completed from") == true

            val result = resolveAutoComplete(
                habitName = habit.name.lowercase().trim(),
                foodEntries = foodEntries,
                workouts = workouts,
                bodyMetrics = bodyMetrics,
                macroTarget = macroTarget,
                userProfile = userProfile
            ) ?: continue

            val (shouldComplete, source) = result
            when {
                shouldComplete -> habitRepository.setHabitStatusForDate(
                    habitDefinitionId = habit.id,
                    date = date,
                    status = HabitStatus.COMPLETE.storageValue,
                    notes = source
                )
                isAutoCompleted -> habitRepository.setHabitStatusForDate(
                    habitDefinitionId = habit.id,
                    date = date,
                    status = HabitStatus.NOT_DONE.storageValue,
                    notes = null
                )
            }
        }
    }

    private fun resolveAutoComplete(
        habitName: String,
        foodEntries: List<FoodEntry>,
        workouts: List<Workout>,
        bodyMetrics: BodyMetricLog?,
        macroTarget: MacroTarget?,
        userProfile: UserProfile?
    ): Pair<Boolean, String>? {
        return when (habitName) {
            "protein goal" -> {
                val min = macroTarget?.proteinMinGrams ?: return null
                val met = foodEntries.sumOf { it.proteinGrams ?: 0.0 } >= min
                met to "Auto-completed from Nutrition"
            }
            "strength training" -> {
                val met = workouts.any {
                    it.status == WorkoutStatus.COMPLETED.storageValue ||
                    it.status == WorkoutStatus.PARTIAL.storageValue
                }
                met to "Auto-completed from Workout"
            }
            "steps" -> {
                val steps = bodyMetrics?.stepCount ?: return null
                val target = userProfile?.stepMinTarget ?: 8_000
                (steps >= target) to "Auto-completed from Body"
            }
            "sleep" -> {
                val sleep = bodyMetrics?.sleepHours ?: return null
                val target = userProfile?.sleepTargetHours ?: return null
                (sleep >= target) to "Auto-completed from Body"
            }
            "whole foods" -> foodEntries.any { it.isWholeFoodBased } to "Auto-completed from Nutrition"
            "fermented foods" -> foodEntries.any { it.isFermented } to "Auto-completed from Nutrition"
            else -> null
        }
    }
}
