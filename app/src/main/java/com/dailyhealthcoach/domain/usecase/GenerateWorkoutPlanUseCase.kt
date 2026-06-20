package com.dailyhealthcoach.domain.usecase

import com.dailyhealthcoach.domain.model.Exercise
import com.dailyhealthcoach.domain.model.SuggestedExercise
import com.dailyhealthcoach.domain.model.Workout
import com.dailyhealthcoach.domain.model.WorkoutExercise
import com.dailyhealthcoach.domain.model.WorkoutPlan
import java.time.LocalDate
import kotlin.math.roundToInt

class GenerateWorkoutPlanUseCase {

    fun generate(
        recommendationType: String,
        exercises: List<Exercise>,
        recentWorkouts: List<Workout>,
        recentSets: List<WorkoutExercise>,
        today: String,
        workoutGoals: List<String> = listOf("General Fitness"),
        focusOverride: String? = null
    ): WorkoutPlan {
        val goals = workoutGoals.ifEmpty { listOf("General Fitness") }

        // Safety-first: conservative goals override everything else
        val isConservative = goals.any { it == "Physical Therapy" || it == "Postpartum" }
        if (isConservative) {
            val label = goals.first { it == "Physical Therapy" || it == "Postpartum" }
            return conservativePlan(label)
        }

        val hasMobility = goals.contains("Mobility & Flex")
        val hasRecoveryFocus = goals.contains("Recovery Focus")
        val hasBeginner = goals.contains("Low Impact")
        val hasStrength = goals.any { it == "Strength Training" || it == "Muscle Gain" }

        // Recovery Focus overrides any strength recommendation
        if (hasRecoveryFocus) {
            return when (recommendationType) {
                "STRENGTH", "LOWER_INTENSITY_STRENGTH" -> if (hasMobility) mobilityFocusPlan() else activeRecoveryPlan()
                "ACTIVE_RECOVERY" -> activeRecoveryPlan()
                "WALKING_MOBILITY" -> walkingMobilityPlan()
                else -> restDayPlan()
            }
        }

        // Mobility only (no strength goals selected alongside)
        if (hasMobility && !hasStrength) {
            return when (recommendationType) {
                "REST" -> restDayPlan()
                else -> mobilityFocusPlan()
            }
        }

        // Beginner — always light; integrate mobility warm-up when selected together
        if (hasBeginner) {
            return when (recommendationType) {
                "STRENGTH", "LOWER_INTENSITY_STRENGTH" -> strengthPlan(exercises, recentWorkouts, recentSets, today, isLight = true, workoutGoals = goals, focusOverride = focusOverride)
                "ACTIVE_RECOVERY" -> if (hasMobility) mobilityFocusPlan() else activeRecoveryPlan()
                "WALKING_MOBILITY" -> walkingMobilityPlan()
                else -> restDayPlan()
            }
        }

        // Standard routing with goal-aware tuning
        return when (recommendationType) {
            "STRENGTH" -> strengthPlan(exercises, recentWorkouts, recentSets, today, isLight = false, workoutGoals = goals, focusOverride = focusOverride)
            "LOWER_INTENSITY_STRENGTH" -> strengthPlan(exercises, recentWorkouts, recentSets, today, isLight = true, workoutGoals = goals, focusOverride = focusOverride)
            "ACTIVE_RECOVERY" -> if (hasMobility) mobilityFocusPlan() else activeRecoveryPlan()
            "WALKING_MOBILITY" -> walkingMobilityPlan()
            else -> restDayPlan()
        }
    }

    private fun strengthPlan(
        exercises: List<Exercise>,
        recentWorkouts: List<Workout>,
        recentSets: List<WorkoutExercise>,
        today: String,
        isLight: Boolean,
        workoutGoals: List<String> = listOf("General Fitness"),
        focusOverride: String? = null
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

        val focus = focusOverride ?: when {
            upperTrained && lowerTrained -> "Full Body"
            upperTrained -> "Lower Body"
            lowerTrained -> "Upper Body"
            else -> defaultFocus(recentWorkouts, recentSets, exerciseById)
        }

        val isHighVolume = workoutGoals.any { it == "Strength Training" || it == "Muscle Gain" }
        val isMuscleGain = workoutGoals.contains("Muscle Gain")
        val maxExercises = if (isLight) 4 else if (isHighVolume) 6 else 5
        val prescribedSets = if (isLight) 2 else if (isHighVolume) 4 else 3
        val prescribedReps = if (isLight) "8-12" else if (isMuscleGain) "8-15" else "6-12"
        val prescribedRpe = if (isLight) "6-7" else if (isHighVolume) "7-9" else "7-8"
        val isDeload = detectDeload(recentWorkouts, recentSets, today)
        val effectiveSets = if (isDeload) (prescribedSets - 1).coerceAtLeast(1) else prescribedSets
        val effectiveRpe = if (isDeload) deloadRpe(prescribedRpe) else prescribedRpe

        val suggested = selectExercises(focus, exercises, maxExercises).map { suggestion ->
            val ex = exerciseById[suggestion.exerciseId]
            val (weightText, progressionNote) = if (ex != null)
                computeWeightSuggestion(ex, recentSets, isLight, isDeload, workoutGoals, prescribedReps)
            else
                "Choose a comfortable starting weight" to ""
            suggestion.copy(
                prescribedSets = effectiveSets,
                prescribedRepsRange = prescribedReps,
                prescribedRpe = effectiveRpe,
                suggestedWeightText = weightText,
                progressionNote = progressionNote,
                alternatives = alternativesFor(suggestion.name)
            )
        }
        val reasons = buildReasons(focus, musclesLast48h, isLight, workoutGoals)
        val allReasons = if (isDeload)
            (listOf("Deload recommended — high recent volume or RPE; reducing sets and intensity") + reasons).take(3)
        else reasons

        return WorkoutPlan(
            focus = focus,
            setsPerExercise = effectiveSets,
            repsRange = prescribedReps,
            rpeTarget = effectiveRpe,
            durationMinutes = if (isLight) "30-45" else if (isHighVolume) "50-70" else "45-60",
            suggestedExercises = suggested,
            isStrengthDay = true,
            reasons = allReasons,
            nonStrengthActivities = emptyList(),
            warmUp = warmUpFor(focus),
            coolDown = coolDownFor(focus),
            postWorkoutRecommendations = postWorkoutRecsFor(workoutGoals)
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
        isLight: Boolean,
        workoutGoals: List<String> = listOf("General Fitness")
    ): List<String> {
        val reasons = mutableListOf<String>()
        reasons += if (isLight) "Recovery score calls for lighter load today" else "Recovery score supports a full strength session"
        if (musclesLast48h.isNotEmpty()) {
            reasons += "Avoiding ${musclesLast48h.joinToString(", ")} — trained in last 48h"
        } else {
            reasons += "No recent strength training — good time for a fresh session"
        }
        if (focus == "Full Body") reasons += "Most major groups trained recently — keeping it balanced"
        val hasStrengthAndMuscle = workoutGoals.contains("Strength Training") && workoutGoals.contains("Muscle Gain")
        val hasMetabolic = workoutGoals.any { it == "Metabolic Reset" || it == "Insulin Resistance" || it == "Fat Loss" }
        when {
            hasStrengthAndMuscle -> reasons += "Goals: strength + hypertrophy — compound lifts with progressive overload"
            workoutGoals.contains("Strength Training") -> reasons += "Goal: build maximal strength — prioritising heavier compound lifts"
            workoutGoals.contains("Muscle Gain") -> reasons += "Goal: hypertrophy — higher volume with controlled tempo"
            hasMetabolic -> reasons += "Goals include metabolic health — compound movements maximise insulin sensitivity"
            workoutGoals.contains("Low Impact") -> reasons += "Goal: build base fitness — lighter load, focus on form"
        }
        return reasons.take(3)
    }

    private fun detectDeload(
        recentWorkouts: List<Workout>,
        recentSets: List<WorkoutExercise>,
        today: String
    ): Boolean {
        val cutoff = LocalDate.parse(today).minusDays(7).toString()
        val lastWeekIds = recentWorkouts.filter { it.date >= cutoff }.map { it.id }.toSet()
        val highRpeCount = recentSets.count { it.workoutId in lastWeekIds && (it.rpe ?: 0) >= 9 }
        return highRpeCount >= 8 || lastWeekIds.size >= 5
    }

    private fun deloadRpe(rpeTarget: String): String {
        val parts = rpeTarget.replace("–", "-").split("-")
        if (parts.size == 2) {
            val lo = parts[0].trim().toIntOrNull() ?: return rpeTarget
            val hi = parts[1].trim().toIntOrNull() ?: return rpeTarget
            return "${(lo - 1).coerceAtLeast(4)}-${(hi - 2).coerceAtLeast(5)}"
        }
        return rpeTarget
    }

    private fun computeWeightSuggestion(
        exercise: Exercise,
        recentSets: List<WorkoutExercise>,
        isLight: Boolean,
        isDeload: Boolean,
        workoutGoals: List<String>,
        prescribedRepsRange: String
    ): Pair<String, String> {
        val eq = exercise.equipmentType.lowercase()
        val pattern = exercise.movementPattern.lowercase()
        if (eq.contains("bodyweight")) return "Bodyweight" to ""
        if (eq.contains("cardio") || pattern.contains("cardio")) return "" to ""

        val isConservative = workoutGoals.any { it == "Physical Therapy" || it == "Postpartum" }
        val isMuscleGain = workoutGoals.contains("Muscle Gain")
        val isStrengthFocus = workoutGoals.contains("Strength Training")
        val isDumbbell = eq.contains("dumbbell") || eq.contains("kettlebell")
        val step = if (isDumbbell) 2.5 else 5.0
        val unit = if (isDumbbell) "lb/side" else "lb"

        val exerciseSets = recentSets
            .filter { it.exerciseId == exercise.id && (it.weight ?: 0.0) > 0 }
            .sortedByDescending { it.id }
            .take(5)

        if (exerciseSets.isEmpty()) return "Choose a comfortable starting weight" to ""

        val avgWeight = exerciseSets.mapNotNull { it.weight }.average()
        val avgRpe = exerciseSets.mapNotNull { it.rpe }.let { r -> if (r.isEmpty()) null else r.average() }
        val avgReps = exerciseSets.mapNotNull { it.reps }.let { r -> if (r.isEmpty()) null else r.average() }
        val repTop = parseRepsRangeTop(prescribedRepsRange)

        val (multiplier, note) = when {
            isConservative ->
                0.60 to "Conservative load — follow clinician guidance"
            isDeload ->
                0.80 to "Deload — reduced load to support recovery"
            isLight && avgRpe != null && avgRpe >= 9.0 ->
                0.75 to "Reduced load — high RPE and low recovery"
            isLight ->
                0.80 to "Lower load — recovery score"
            avgRpe != null && avgRpe >= 9.0 ->
                1.00 to "Maintain weight — high RPE last session"
            avgRpe != null && avgRpe <= 6.5 && repTop != null && avgReps != null && avgReps >= repTop ->
                when {
                    isMuscleGain -> 1.00 to "Add reps first, then increase weight (hypertrophy focus)"
                    isStrengthFocus -> 1.05 to "Suggested progression — last session was strong"
                    else -> 1.025 to "Suggested progression from last session"
                }
            isMuscleGain -> 0.95 to "Based on recent performance"
            isStrengthFocus -> 0.95 to "Based on recent performance"
            else -> 0.85 to "Based on recent performance"
        }

        val adjusted = avgWeight * multiplier
        val rounded = ((adjusted / step).roundToInt() * step).coerceAtLeast(step)
        val display = if (rounded % 1.0 == 0.0) rounded.toInt().toString() else "%.1f".format(rounded)
        return "$display $unit" to note
    }

    private fun parseRepsRangeTop(range: String): Int? =
        range.replace("–", "-").split("-").lastOrNull()?.trim()?.toIntOrNull()

    private val exerciseSubstitutions = mapOf(
        "Bench Press" to listOf("Dumbbell Press", "Push-Up"),
        "Barbell Row" to listOf("Dumbbell Row", "Band Row"),
        "Squat" to listOf("Goblet Squat", "Bodyweight Squat"),
        "Deadlift" to listOf("Romanian Deadlift", "Hip Hinge"),
        "Overhead Press" to listOf("Dumbbell Shoulder Press", "Pike Push-Up"),
        "Pull-Up" to listOf("Lat Pulldown", "Band-Assisted Pull-Up"),
        "Dip" to listOf("Tricep Pushdown", "Bench Dip")
    )

    private fun alternativesFor(exerciseName: String): List<String> {
        val key = exerciseSubstitutions.keys.firstOrNull { exerciseName.contains(it, ignoreCase = true) }
        return exerciseSubstitutions[key] ?: emptyList()
    }

    private fun conservativePlan(workoutGoal: String) = WorkoutPlan(
        focus = "Gentle Movement",
        setsPerExercise = 0, repsRange = "", rpeTarget = "3-5", durationMinutes = "15-30",
        suggestedExercises = emptyList(), isStrengthDay = false,
        reasons = listOf(
            "Use this as general guidance only. Follow clinician guidance where applicable.",
            "Conservative session tailored for $workoutGoal — prioritising safety and comfort"
        ),
        nonStrengthActivities = listOf(
            "15-30 min gentle walk at comfortable pace",
            "Light stretching — no pain, no strain",
            "Breathing exercises or relaxation"
        ),
        warmUp = listOf("Gentle joint circles as comfortable"),
        coolDown = listOf("5 min gentle breathing"),
        postWorkoutRecommendations = listOf("Hydrate", "Rest as needed", "Follow clinician guidance")
    )

    private fun mobilityFocusPlan() = WorkoutPlan(
        focus = "Mobility & Flexibility",
        setsPerExercise = 0, repsRange = "", rpeTarget = "3-5", durationMinutes = "20-40",
        suggestedExercises = emptyList(), isStrengthDay = false,
        reasons = listOf(
            "Goal: Mobility & Flexibility — joint health and range of motion",
            "Low-intensity movement supports recovery while building flexibility"
        ),
        nonStrengthActivities = listOf(
            "10-15 min dynamic warm-up",
            "20-30 min mobility flow — hips, thoracic, shoulders",
            "5-10 min static stretching cool-down"
        ),
        warmUp = listOf("5 min easy walking", "Joint circles — shoulders, hips, ankles"),
        coolDown = listOf("Child's pose — 60 sec", "Supine spinal twist — 30 sec each"),
        postWorkoutRecommendations = listOf("Hydrate", "Gentle breathing exercises", "Rest")
    )

    private fun activeRecoveryPlan() = WorkoutPlan(
        focus = "Active Recovery",
        setsPerExercise = 0, repsRange = "", rpeTarget = "", durationMinutes = "20-30",
        suggestedExercises = emptyList(), isStrengthDay = false,
        reasons = listOf("Recovery score calls for low-intensity activity", "Light movement aids recovery without adding fatigue"),
        nonStrengthActivities = listOf("20-30 min easy walk", "10 min mobility work", "Optional light stretching"),
        postWorkoutRecommendations = listOf("Hydrate", "Light stretching if comfortable", "Rest")
    )

    private fun walkingMobilityPlan() = WorkoutPlan(
        focus = "Walking & Mobility",
        setsPerExercise = 0, repsRange = "", rpeTarget = "", durationMinutes = "20-40",
        suggestedExercises = emptyList(), isStrengthDay = false,
        reasons = listOf("Rest and gentle movement today", "Walking maintains blood flow without taxing recovery"),
        nonStrengthActivities = listOf("20-40 min easy walk", "5-10 min mobility", "Breathwork if stress is high"),
        postWorkoutRecommendations = listOf("Hydrate", "Light stretching after walk", "Rest")
    )

    private fun restDayPlan() = WorkoutPlan(
        focus = "Rest Day",
        setsPerExercise = 0, repsRange = "", rpeTarget = "", durationMinutes = "",
        suggestedExercises = emptyList(), isStrengthDay = false,
        reasons = listOf("Body needs full rest today", "Prioritise sleep and hydration"),
        nonStrengthActivities = listOf("Full rest", "Focus on hydration and sleep", "Light walk only if desired"),
        postWorkoutRecommendations = listOf("Hydrate", "Rest", "Light walk only if desired")
    )

    private fun warmUpFor(focus: String): List<String> = when (focus) {
        "Upper Body", "Push Day" -> listOf(
            "Arm circles — 30 sec",
            "Band pull-aparts — 10 reps",
            "Shoulder rotations — 30 sec",
            "Cat-cow — 10 reps"
        )
        "Pull Day" -> listOf(
            "Arm circles — 30 sec",
            "Scapular pulls — 10 reps",
            "Band pull-aparts — 10 reps",
            "Lat activation — 30 sec"
        )
        "Lower Body" -> listOf(
            "Hip circles — 30 sec",
            "Leg swings — 10 each side",
            "Glute bridges — 10 reps",
            "Hip flexor stretch — 30 sec each"
        )
        else -> listOf(
            "Arm swings — 30 sec",
            "Hip circles — 30 sec",
            "Squat to stand — 10 reps",
            "Thoracic rotations — 10 reps"
        )
    }

    private fun coolDownFor(focus: String): List<String> = when (focus) {
        "Upper Body", "Push Day" -> listOf(
            "Chest stretch — 30 sec each side",
            "Lat stretch — 30 sec each",
            "Shoulder cross-body stretch — 30 sec each",
            "Neck rolls — 30 sec"
        )
        "Pull Day" -> listOf(
            "Chest stretch — 30 sec each side",
            "Lat stretch — 30 sec each",
            "Bicep stretch — 30 sec each",
            "Neck rolls — 30 sec"
        )
        "Lower Body" -> listOf(
            "Hamstring stretch — 30 sec each",
            "Quad stretch — 30 sec each",
            "Hip flexor stretch — 30 sec each",
            "Calf stretch — 30 sec each"
        )
        else -> listOf(
            "Standing forward fold — 30 sec",
            "Hip flexor stretch — 30 sec each",
            "Chest opener — 30 sec",
            "Seated spinal twist — 30 sec each"
        )
    }

    private fun postWorkoutRecsFor(workoutGoals: List<String>): List<String> {
        val isPT = workoutGoals.contains("Physical Therapy")
        val isPostpartum = workoutGoals.contains("Postpartum")
        val hasMetabolic = workoutGoals.any { it == "Metabolic Reset" || it == "Insulin Resistance" || it == "Fat Loss" }
        val isMuscleGain = workoutGoals.contains("Muscle Gain")
        val isStrength = workoutGoals.contains("Strength Training")
        val hasRecovery = workoutGoals.contains("Recovery Focus")
        val isBeginner = workoutGoals.contains("Low Impact")
        return when {
            isPT -> listOf("Gentle stretching as clinically prescribed", "Ice or heat if directed by clinician", "Rest and elevate if needed")
            isPostpartum -> listOf("10 min gentle walk", "Pelvic floor recovery exercises", "Hydrate and rest")
            hasMetabolic -> listOf("15–20 min brisk walk", "Hydrate — 500 ml minimum", "Avoid prolonged sitting for 2 h")
            isMuscleGain -> listOf("10 min stretching", "Protein within 30–60 min", "Prioritise 7–9 h sleep")
            isStrength -> listOf("10 min post-lift stretching", "Protein within 30–60 min", "Prioritise 7–9 h sleep tonight")
            hasRecovery -> listOf("10 min mobility flow", "5 min breathwork", "Hydrate and rest")
            isBeginner -> listOf("10 min easy walk", "Hydrate", "5 min light stretching")
            else -> listOf("10–15 min walk", "Hydrate — 500 ml minimum", "5 min light stretching")
        }
    }
}
