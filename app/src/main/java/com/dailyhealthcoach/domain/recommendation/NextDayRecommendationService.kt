package com.dailyhealthcoach.domain.recommendation

import com.dailyhealthcoach.domain.model.BodyMetricLog
import com.dailyhealthcoach.domain.model.DailyHabitLog
import com.dailyhealthcoach.domain.model.DailyRecommendation
import com.dailyhealthcoach.domain.model.Exercise
import com.dailyhealthcoach.domain.model.FoodEntry
import com.dailyhealthcoach.domain.model.HabitDefinition
import com.dailyhealthcoach.domain.model.Workout
import com.dailyhealthcoach.domain.model.WorkoutExercise
import java.time.LocalDate

class NextDayRecommendationService {
    fun calculate(input: RecommendationInput): DailyRecommendation? {
        if (!input.hasEnoughData()) return null

        val recoveryScore = input.recoveryScore ?: 50
        val sleep = input.bodyMetricLog?.sleepHours
        val stress = input.bodyMetricLog?.stressLevel
        val soreness = input.bodyMetricLog?.sorenessLevel
        val protein = input.foodEntries.sumOf { it.proteinGrams ?: 0.0 }
        val weeklyStrengthCount = input.weeklyWorkouts.count { it.status == "COMPLETED" || it.status == "PARTIAL" }
        val recentAverageRpe = input.recentAverageRpe()
        val hardWorkoutTodayOrYesterday = input.hardWorkoutTodayOrYesterday()
        val hardWorkoutToday = input.todayWorkouts.any { workout ->
            val workoutRpe = listOfNotNull(workout.overallRpe?.toDouble(), input.averageSetRpe(workout.id)).maxOrNull()
            workout.status == "COMPLETED" && workoutRpe != null && workoutRpe >= 8.0
        }
        val hardMuscleGroups = input.hardMuscleGroupsInLast48Hours()
        val restDayFatigueSignalCount = listOf(
            sleep != null && sleep < 6.0,
            soreness != null && soreness >= 8,
            input.bodyMetricLog?.energyLevel != null && input.bodyMetricLog.energyLevel <= 3,
            stress != null && stress >= 8,
            hardWorkoutTodayOrYesterday,
            recentAverageRpe != null && recentAverageRpe >= 8.0
        ).count { it }
        val hasStrongRestSignal = soreness != null && soreness >= 8 ||
            input.bodyMetricLog?.energyLevel != null && input.bodyMetricLog.energyLevel <= 3 ||
            hardWorkoutTodayOrYesterday ||
            recentAverageRpe != null && recentAverageRpe >= 8.0
        val shouldRest = recoveryScore < 25 ||
            (recoveryScore < 35 && restDayFatigueSignalCount >= 2 && hasStrongRestSignal)

        val recommendationType = when {
            shouldRest -> RecommendationType.REST
            recoveryScore < 40 -> RecommendationType.WALKING_MOBILITY
            recoveryScore < 50 -> {
                if (weeklyStrengthCount < 3 && (soreness == null || soreness <= 6)) {
                    RecommendationType.LOWER_INTENSITY_STRENGTH
                } else {
                    RecommendationType.ACTIVE_RECOVERY
                }
            }
            recoveryScore < 70 -> {
                if ((protein > 0.0 && protein < 140.0) || (sleep != null && sleep < 6.5) || hardWorkoutToday) {
                    RecommendationType.LOWER_INTENSITY_STRENGTH
                } else {
                    RecommendationType.STRENGTH
                }
            }
            weeklyStrengthCount < 4 && !hardWorkoutToday -> RecommendationType.STRENGTH
            else -> RecommendationType.ACTIVE_RECOVERY
        }

        val finalType = if (weeklyStrengthCount >= 4 && recommendationType == RecommendationType.STRENGTH) {
            RecommendationType.ACTIVE_RECOVERY
        } else {
            recommendationType
        }
        val focus = suggestedFocus(finalType, hardMuscleGroups, stress)
        val reasons = buildReasons(
            recoveryScore = recoveryScore,
            sleep = sleep,
            protein = protein,
            stress = stress,
            soreness = soreness,
            weeklyStrengthCount = weeklyStrengthCount,
            recentAverageRpe = recentAverageRpe,
            input = input
        )

        return DailyRecommendation(
            id = 0,
            date = input.date,
            recommendationType = finalType.storageValue,
            title = finalType.title,
            explanation = explanation(finalType, recoveryScore),
            suggestedFocus = focus,
            reasonBullets = reasons.take(4),
            targetMuscleGroups = focus,
            intensity = finalType.intensity,
            loadGuidance = focus,
            reasonSummary = reasons.take(4).joinToString("\n")
        )
    }

    private fun RecommendationInput.hasEnoughData(): Boolean {
        return recoveryScore != null ||
            bodyMetricLog?.sleepHours != null ||
            bodyMetricLog?.energyLevel != null ||
            bodyMetricLog?.stressLevel != null ||
            bodyMetricLog?.sorenessLevel != null ||
            foodEntries.isNotEmpty() ||
            todayWorkouts.isNotEmpty() ||
            recentWorkouts.isNotEmpty() ||
            habitLogs.isNotEmpty()
    }

    private fun explanation(type: RecommendationType, recoveryScore: Int): String {
        return when (type) {
            RecommendationType.STRENGTH -> "Recovery is good enough for productive strength training tomorrow."
            RecommendationType.LOWER_INTENSITY_STRENGTH -> "Recovery is mixed, so keep strength work controlled tomorrow."
            RecommendationType.ACTIVE_RECOVERY -> "Recovery calls for light movement and low training stress tomorrow."
            RecommendationType.WALKING_MOBILITY -> "Recovery is low, so keep intensity low tomorrow."
            RecommendationType.REST -> "Recovery is very low, so prioritize rest tomorrow."
        } + " Current recovery score is $recoveryScore/100."
    }

    private fun suggestedFocus(type: RecommendationType, hardMuscleGroups: Set<String>, stress: Int?): String {
        val baseFocus = when (type) {
            RecommendationType.REST -> "Rest, hydration, easy breathing"
            RecommendationType.WALKING_MOBILITY -> "20-40 minute easy walk, light mobility, hydration"
            RecommendationType.ACTIVE_RECOVERY -> "Easy walk, mobility, light core"
            RecommendationType.LOWER_INTENSITY_STRENGTH -> "Full body light"
            RecommendationType.STRENGTH -> null
        }
        if (baseFocus != null) {
            return if (stress != null && stress >= 8 && !baseFocus.contains("breathwork", ignoreCase = true)) {
                "$baseFocus, breathwork"
            } else {
                baseFocus
            }
        }

        val trainedUpper = hardMuscleGroups.any { it in upperBodyGroups }
        val trainedLower = hardMuscleGroups.any { it in lowerBodyGroups }
        val strengthFocus = when {
            trainedUpper && !trainedLower -> "Lower body strength"
            trainedLower && !trainedUpper -> "Upper body strength"
            hardMuscleGroups.any { it == "Chest" || it == "Shoulders" || it == "Arms" } -> "Pull strength"
            hardMuscleGroups.any { it == "Back" } -> "Push strength"
            else -> "Full body strength"
        }
        return if (stress != null && stress >= 8) "$strengthFocus, breathwork" else strengthFocus
    }

    private fun buildReasons(
        recoveryScore: Int,
        sleep: Double?,
        protein: Double,
        stress: Int?,
        soreness: Int?,
        weeklyStrengthCount: Int,
        recentAverageRpe: Double?,
        input: RecommendationInput
    ): List<String> {
        val reasons = mutableListOf("Recovery score is $recoveryScore/100")
        sleep?.let {
            if (it < 6.0) reasons += "Sleep was low at ${it.oneDecimal()} hours"
            else if (it >= 7.0) reasons += "Sleep supports training at ${it.oneDecimal()} hours"
        }
        if (input.foodEntries.isNotEmpty()) {
            if (protein < 140.0) reasons += "Protein is below target at ${protein.toInt()}g"
            else if (protein >= 170.0) reasons += "Protein goal is on track at ${protein.toInt()}g"
        }
        stress?.let {
            if (it >= 8) reasons += "Stress is elevated at $it/10"
            else if (it <= 3) reasons += "Stress is low at $it/10"
        }
        soreness?.let {
            if (it >= 8) reasons += "Soreness is high at $it/10"
            else if (it <= 3) reasons += "Soreness is low at $it/10"
        }
        recentAverageRpe?.let {
            if (it >= 8.0) reasons += "Recent workout intensity was high"
        }
        if (weeklyStrengthCount >= 4) reasons += "Weekly strength target is already met"
        if (stress != null && stress >= 8 && input.isHabitComplete("breath")) {
            reasons += "Breathwork is complete despite high stress"
        }
        if (input.isHabitNotDone("late eating")) reasons += "Late eating habit was not done"
        if (input.isHabitComplete("whole")) reasons += "Whole foods habit is complete"
        return reasons.distinct()
    }

    private fun RecommendationInput.recentAverageRpe(): Double? {
        return recentWorkouts
            .mapNotNull { workout ->
                listOfNotNull(workout.overallRpe?.toDouble(), averageSetRpe(workout.id)).maxOrNull()
            }
            .maxOrNull()
    }

    private fun RecommendationInput.hardWorkoutTodayOrYesterday(): Boolean {
        val currentDate = LocalDate.parse(date)
        return recentWorkouts.any { workout ->
            val workoutDate = LocalDate.parse(workout.date)
            val daysAgo = java.time.temporal.ChronoUnit.DAYS.between(workoutDate, currentDate)
            val workoutRpe = listOfNotNull(workout.overallRpe?.toDouble(), averageSetRpe(workout.id)).maxOrNull()
            daysAgo in 0..1 && workout.status == "COMPLETED" && workoutRpe != null && workoutRpe >= 8.0
        }
    }

    private fun RecommendationInput.averageSetRpe(workoutId: Long): Double? {
        val rpes = workoutSets.filter { it.workoutId == workoutId }.mapNotNull { it.rpe }
        return if (rpes.isEmpty()) null else rpes.average()
    }

    private fun RecommendationInput.hardMuscleGroupsInLast48Hours(): Set<String> {
        val exerciseById = exercises.associateBy { it.id }
        return recentWorkouts
            .filter { workout ->
                val workoutRpe = listOfNotNull(workout.overallRpe?.toDouble(), averageSetRpe(workout.id)).maxOrNull()
                workoutRpe != null && workoutRpe >= 8.0
            }
            .flatMap { workout -> workoutSets.filter { it.workoutId == workout.id } }
            .mapNotNull { set -> exerciseById[set.exerciseId]?.muscleGroup }
            .toSet()
    }

    private fun RecommendationInput.isHabitComplete(nameFragment: String): Boolean {
        val matchingIds = habitDefinitions
            .filter { it.name.contains(nameFragment, ignoreCase = true) }
            .map { it.id }
            .toSet()
        return habitLogs.any { it.habitDefinitionId in matchingIds && it.status == "COMPLETE" }
    }

    private fun RecommendationInput.isHabitNotDone(nameFragment: String): Boolean {
        val matchingIds = habitDefinitions
            .filter { it.name.contains(nameFragment, ignoreCase = true) }
            .map { it.id }
            .toSet()
        return habitLogs.any { it.habitDefinitionId in matchingIds && it.status == "NOT_DONE" }
    }

    private enum class RecommendationType(val storageValue: String, val title: String, val intensity: String) {
        STRENGTH("STRENGTH", "Strength training day", "Moderate to hard"),
        LOWER_INTENSITY_STRENGTH("LOWER_INTENSITY_STRENGTH", "Lower intensity strength day", "Controlled"),
        ACTIVE_RECOVERY("ACTIVE_RECOVERY", "Active recovery day", "Light"),
        WALKING_MOBILITY("WALKING_MOBILITY", "Walking / mobility day", "Very light"),
        REST("REST", "Rest day", "Rest")
    }

    private companion object {
        val upperBodyGroups = setOf("Chest", "Back", "Shoulders", "Arms")
        val lowerBodyGroups = setOf("Legs")
    }
}

data class RecommendationInput(
    val date: String,
    val recoveryScore: Int?,
    val bodyMetricLog: BodyMetricLog?,
    val foodEntries: List<FoodEntry>,
    val habitDefinitions: List<HabitDefinition>,
    val habitLogs: List<DailyHabitLog>,
    val todayWorkouts: List<Workout>,
    val recentWorkouts: List<Workout>,
    val weeklyWorkouts: List<Workout>,
    val workoutSets: List<WorkoutExercise>,
    val exercises: List<Exercise>
)

private fun Double.oneDecimal(): String = String.format("%.1f", this)
