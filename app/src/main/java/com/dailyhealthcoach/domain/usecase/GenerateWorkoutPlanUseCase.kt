package com.dailyhealthcoach.domain.usecase

import com.dailyhealthcoach.domain.model.Exercise
import com.dailyhealthcoach.domain.model.SuggestedExercise
import com.dailyhealthcoach.domain.model.Workout
import com.dailyhealthcoach.domain.model.WorkoutExercise
import com.dailyhealthcoach.domain.model.WorkoutPlan
import java.time.LocalDate

class GenerateWorkoutPlanUseCase {

    fun generate(
        recommendationType: String,
        exercises: List<Exercise>,
        recentWorkouts: List<Workout>,
        recentSets: List<WorkoutExercise>,
        today: String
    ): WorkoutPlan = when (recommendationType) {
        "STRENGTH" -> strengthPlan(exercises, recentWorkouts, recentSets, today, isLight = false)
        "LOWER_INTENSITY_STRENGTH" -> strengthPlan(exercises, recentWorkouts, recentSets, today, isLight = true)
        "ACTIVE_RECOVERY" -> activeRecoveryPlan()
        "WALKING_MOBILITY" -> walkingMobilityPlan()
        else -> restDayPlan()
    }

    private fun strengthPlan(
        exercises: List<Exercise>,
        recentWorkouts: List<Workout>,
        recentSets: List<WorkoutExercise>,
        today: String,
        isLight: Boolean
    ): WorkoutPlan {
        val cutoff48h = LocalDate.parse(today).minusDays(2).toString()
        val exerciseById = exercises.associateBy { it.id }

        val recentWorkoutIds = recentWorkouts.filter { it.date > cutoff48h }.map { it.id }.toSet()
        val musclesLast48h = recentSets
            .filter { it.workoutId in recentWorkoutIds }
            .mapNotNull { exerciseById[it.exerciseId]?.muscleGroup }
            .toSet()

        val upperMuscles = setOf("Chest", "Back", "Shoulders", "Arms")
        val lowerMuscles = setOf("Legs", "Core")
        val upperTrained = musclesLast48h.any { it in upperMuscles }
        val lowerTrained = musclesLast48h.any { it in lowerMuscles }

        val focus = when {
            upperTrained && lowerTrained -> "Full Body"
            upperTrained -> "Lower Body"
            lowerTrained -> "Upper Body"
            else -> defaultFocus(recentWorkouts, recentSets, exerciseById)
        }

        val maxExercises = if (isLight) 4 else 5
        val suggested = selectExercises(focus, exercises, maxExercises)
        val reasons = buildReasons(focus, musclesLast48h, isLight)

        return WorkoutPlan(
            focus = focus,
            setsPerExercise = if (isLight) 2 else 3,
            repsRange = if (isLight) "8-12" else "6-12",
            rpeTarget = if (isLight) "6-7" else "7-8",
            durationMinutes = if (isLight) "30-45" else "45-60",
            suggestedExercises = suggested,
            isStrengthDay = true,
            reasons = reasons,
            nonStrengthActivities = emptyList()
        )
    }

    private fun defaultFocus(
        recentWorkouts: List<Workout>,
        recentSets: List<WorkoutExercise>,
        exerciseById: Map<Long, Exercise>
    ): String {
        val lastWorkout = recentWorkouts.maxByOrNull { it.date } ?: return "Upper Body"
        val lastMuscles = recentSets
            .filter { it.workoutId == lastWorkout.id }
            .mapNotNull { exerciseById[it.exerciseId]?.muscleGroup }
            .toSet()
        val upperMuscles = setOf("Chest", "Back", "Shoulders", "Arms")
        val lowerMuscles = setOf("Legs", "Core")
        return when {
            lastMuscles.any { it in upperMuscles } && lastMuscles.none { it in lowerMuscles } -> "Lower Body"
            lastMuscles.any { it in lowerMuscles } && lastMuscles.none { it in upperMuscles } -> "Upper Body"
            else -> "Upper Body"
        }
    }

    private fun selectExercises(
        focus: String,
        exercises: List<Exercise>,
        maxCount: Int
    ): List<SuggestedExercise> {
        val byMuscle = exercises.filter { it.muscleGroup != "Cardio" }.groupBy { it.muscleGroup }
        val targets = when (focus) {
            "Upper Body" -> listOf("Chest", "Back", "Shoulders", "Arms", "Back")
            "Lower Body" -> listOf("Legs", "Legs", "Core", "Legs")
            "Push Day"   -> listOf("Chest", "Shoulders", "Chest", "Shoulders", "Arms")
            "Pull Day"   -> listOf("Back", "Back", "Arms")
            else         -> listOf("Chest", "Back", "Legs", "Shoulders", "Core")  // Full Body
        }.take(maxCount)

        val usedByMuscle = mutableMapOf<String, Int>()
        return targets.mapNotNull { muscle ->
            val idx = usedByMuscle.getOrDefault(muscle, 0)
            val exercise = byMuscle[muscle]?.getOrNull(idx) ?: byMuscle[muscle]?.firstOrNull() ?: return@mapNotNull null
            usedByMuscle[muscle] = idx + 1
            SuggestedExercise(exercise.id, exercise.name, exercise.muscleGroup)
        }.distinctBy { it.exerciseId }
    }

    private fun buildReasons(
        focus: String,
        musclesLast48h: Set<String>,
        isLight: Boolean
    ): List<String> {
        val reasons = mutableListOf<String>()
        reasons += if (isLight) "Recovery score calls for lighter load today" else "Recovery score supports a full strength session"
        if (musclesLast48h.isNotEmpty()) {
            reasons += "Avoiding ${musclesLast48h.joinToString(", ")} — trained in last 48h"
        } else {
            reasons += "No recent strength training — good time for a fresh session"
        }
        if (focus == "Full Body") reasons += "Most major groups trained recently — keeping it balanced"
        return reasons.take(3)
    }

    private fun activeRecoveryPlan() = WorkoutPlan(
        focus = "Active Recovery",
        setsPerExercise = 0, repsRange = "", rpeTarget = "", durationMinutes = "20-30",
        suggestedExercises = emptyList(), isStrengthDay = false,
        reasons = listOf("Recovery score calls for low-intensity activity", "Light movement aids recovery without adding fatigue"),
        nonStrengthActivities = listOf("20-30 min easy walk", "10 min mobility work", "Optional light stretching")
    )

    private fun walkingMobilityPlan() = WorkoutPlan(
        focus = "Walking & Mobility",
        setsPerExercise = 0, repsRange = "", rpeTarget = "", durationMinutes = "20-40",
        suggestedExercises = emptyList(), isStrengthDay = false,
        reasons = listOf("Rest and gentle movement today", "Walking maintains blood flow without taxing recovery"),
        nonStrengthActivities = listOf("20-40 min easy walk", "5-10 min mobility", "Breathwork if stress is high")
    )

    private fun restDayPlan() = WorkoutPlan(
        focus = "Rest Day",
        setsPerExercise = 0, repsRange = "", rpeTarget = "", durationMinutes = "",
        suggestedExercises = emptyList(), isStrengthDay = false,
        reasons = listOf("Body needs full rest today", "Prioritise sleep and hydration"),
        nonStrengthActivities = listOf("Full rest", "Focus on hydration and sleep", "Light walk only if desired")
    )
}
