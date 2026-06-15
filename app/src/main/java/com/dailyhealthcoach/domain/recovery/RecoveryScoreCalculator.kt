package com.dailyhealthcoach.domain.recovery

import com.dailyhealthcoach.domain.model.BodyMetricLog
import com.dailyhealthcoach.domain.model.DailyHabitLog
import com.dailyhealthcoach.domain.model.FoodEntry
import com.dailyhealthcoach.domain.model.HabitDefinition
import com.dailyhealthcoach.domain.model.RecoveryActivity
import com.dailyhealthcoach.domain.model.Workout
import com.dailyhealthcoach.domain.model.WorkoutExercise

class RecoveryScoreCalculator {
    fun calculate(input: RecoveryScoreInput): RecoveryScoreResult? {
        if (!input.hasEnoughData()) return null

        var score = 50
        val reasons = mutableListOf<String>()
        val contributors = mutableListOf<RecoveryContributor>()

        fun track(label: String, delta: Int, detail: String, reason: String? = null) {
            score += delta
            if (delta != 0) contributors += RecoveryContributor(label, delta, detail)
            if (reason != null) reasons += reason
        }

        // 1. Sleep — HIGH weight
        val sleep = input.bodyMetricLog?.sleepHours
        when {
            sleep == null -> Unit
            sleep >= 7.5 -> track("Sleep", +20, "${sleep.oneDecimal()} h", "Sleep was ${sleep.oneDecimal()} hours")
            sleep >= 7.0 -> track("Sleep", +15, "${sleep.oneDecimal()} h", "Sleep was solid at ${sleep.oneDecimal()} hours")
            sleep >= 6.0 -> track("Sleep", +5, "${sleep.oneDecimal()} h", "Sleep was moderate at ${sleep.oneDecimal()} hours")
            sleep >= 5.0 -> track("Sleep", -10, "${sleep.oneDecimal()} h", "Sleep was low at ${sleep.oneDecimal()} hours")
            else -> track("Sleep", -20, "${sleep.oneDecimal()} h", "Sleep was very low at ${sleep.oneDecimal()} hours")
        }

        // 2. Steps / Movement — MODERATE weight
        val steps = input.bodyMetricLog?.stepCount
        when {
            steps == null || steps == 0 -> Unit
            steps >= 12000 -> track("Steps", +10, "$steps steps", "High movement at $steps steps")
            steps >= 7000 -> track("Steps", +8, "$steps steps", "Good movement at $steps steps")
            steps in 3000..6999 -> Unit // neutral — no contributor shown
            else -> track("Steps", -8, "$steps steps", "Low movement at $steps steps")
        }

        // 3. Protein / Nutrition — MODERATE weight
        val protein = input.foodEntries.sumOf { it.proteinGrams ?: 0.0 }
        when {
            protein >= 170.0 -> track("Nutrition", +15, "${protein.toInt()}g protein", "Protein goal is on track at ${protein.toInt()}g")
            protein >= 140.0 -> track("Nutrition", +5, "${protein.toInt()}g protein", "Protein is close to target at ${protein.toInt()}g")
            input.foodEntries.isNotEmpty() && protein >= 100.0 -> track("Nutrition", -5, "${protein.toInt()}g protein", "Protein is below target at ${protein.toInt()}g")
            input.foodEntries.isNotEmpty() -> track("Nutrition", -15, "${protein.toInt()}g protein", "Protein is low at ${protein.toInt()}g")
        }

        // 4. Energy level
        input.bodyMetricLog?.energyLevel?.let { energy ->
            when (energy) {
                in 8..10 -> track("Energy", +10, "$energy/10", "Energy is strong at $energy/10")
                in 6..7 -> track("Energy", +5, "$energy/10", "Energy is steady at $energy/10")
                in 4..5 -> track("Energy", -5, "$energy/10", "Energy is low at $energy/10")
                in 1..3 -> track("Energy", -10, "$energy/10", "Energy is very low at $energy/10")
            }
        }

        // 5. Stress level
        input.bodyMetricLog?.stressLevel?.let { stress ->
            when (stress) {
                in 1..3 -> track("Stress", +10, "$stress/10", "Stress is well managed at $stress/10")
                in 4..6 -> Unit // neutral
                in 7..8 -> track("Stress", -10, "$stress/10", "Stress is elevated at $stress/10")
                in 9..10 -> track("Stress", -20, "$stress/10", "Stress is very high at $stress/10")
            }
        }

        // 6. Soreness level
        input.bodyMetricLog?.sorenessLevel?.let { soreness ->
            when (soreness) {
                in 1..3 -> track("Soreness", +10, "$soreness/10", "Soreness is manageable at $soreness/10")
                in 4..6 -> Unit // neutral
                in 7..8 -> track("Soreness", -10, "$soreness/10", "Soreness is high at $soreness/10")
                in 9..10 -> track("Soreness", -20, "$soreness/10", "Soreness is very high at $soreness/10")
            }
        }

        // 7. Habit completion rate — MODERATE weight
        val totalHabits = input.habitDefinitions.size
        val completedHabits = input.habitLogs.count { it.status == "COMPLETE" }
        if (totalHabits > 0) {
            val rate = completedHabits.toDouble() / totalHabits
            val pct = (rate * 100).toInt()
            when {
                rate >= 0.8 -> track("Habits", +8, "$pct% done", null)
                rate >= 0.5 -> track("Habits", +3, "$pct% done", null)
                rate < 0.25 -> track("Habits", -5, "$pct% done", "Low habit completion at $pct%")
            }
        }
        if (input.isHabitComplete("whole")) {
            score += 5
            reasons += "Whole foods habit is complete"
        }
        if (input.isHabitComplete("sunlight")) {
            score += 5
            reasons += "Morning sunlight habit is complete"
        }

        // 8. Recent training load — HIGH weight
        val recentSetCount = input.recentWorkouts.sumOf { workout -> input.setsFor(workout.id).size }
        val recentAverageRpe = input.recentWorkouts
            .mapNotNull { workout ->
                val avgSetRpe = input.setsFor(workout.id).mapNotNull { it.rpe }.averageOrNull()
                listOfNotNull(workout.overallRpe?.toDouble(), avgSetRpe).maxOrNull()
            }
            .maxOrNull()
        when {
            recentAverageRpe != null && recentAverageRpe >= 9.0 && recentSetCount >= 20 ->
                track("Training Load", -15, "RPE ${"%.1f".format(recentAverageRpe)}", "Workout load was very hard recently")
            recentAverageRpe != null && recentAverageRpe >= 8.0 ->
                track("Training Load", -10, "RPE ${"%.1f".format(recentAverageRpe)}", "A hard workout was logged recently")
            recentAverageRpe != null || input.recentWorkouts.isNotEmpty() ->
                track("Training Load", -5, "${input.recentWorkouts.size} session(s)", "Moderate workout load was logged recently")
        }

        // 9. Weekly training volume — MODERATE weight (new)
        if (input.weeklyWorkouts.isNotEmpty()) {
            val weeklyCompleted = input.weeklyWorkouts.count { it.status == "COMPLETED" || it.status == "PARTIAL" }
            val weeklySetCount = input.weeklyWorkouts.sumOf { w -> input.setsFor(w.id).size }
            when {
                weeklyCompleted >= 5 || weeklySetCount >= 80 ->
                    track("Weekly Volume", -8, "$weeklyCompleted sessions", "High weekly training volume")
                weeklyCompleted >= 4 || weeklySetCount >= 60 ->
                    track("Weekly Volume", -4, "$weeklyCompleted sessions", null)
            }
        }

        // 10. Stretching / Mobility from recovery activities — MODERATE weight (new)
        val completedMobility = input.recoveryActivities.count { a ->
            a.status == "COMPLETED" && (
                a.name.contains("stretch", ignoreCase = true) ||
                a.name.contains("mobility", ignoreCase = true) ||
                a.name.contains("warm", ignoreCase = true) ||
                a.name.contains("cool", ignoreCase = true) ||
                a.name.contains("breath", ignoreCase = true) ||
                a.name.contains("walk", ignoreCase = true)
            )
        }
        when {
            completedMobility >= 4 -> track("Mobility", +10, "$completedMobility activities", "Mobility and recovery work completed")
            completedMobility >= 2 -> track("Mobility", +6, "$completedMobility activities", "Mobility and recovery work completed")
            completedMobility >= 1 -> track("Mobility", +3, "$completedMobility activities", null)
        }

        // Late eating penalty
        if (input.isHabitNotDone("late eating")) {
            score -= 5
            reasons += "Late eating habit was not done"
        }

        // Breathwork bonus despite stress
        if (input.bodyMetricLog?.stressLevel != null && input.bodyMetricLog.stressLevel >= 7 && input.isHabitComplete("breath")) {
            score += 5
            reasons += "Breathwork was completed despite high stress"
        }

        // Future extension points (not yet implemented):
        // slot HRV delta, glucose variability, menstrual phase adjustment, wearable stress here

        val clampedScore = score.recoveryFloor(input).coerceIn(0, 100)
        return RecoveryScoreResult(
            score = clampedScore,
            label = recoveryLabel(clampedScore),
            reasons = reasons.take(4).ifEmpty { listOf("Recovery score is based on today's logged health data") },
            contributors = contributors
        )
    }

    private fun Int.recoveryFloor(input: RecoveryScoreInput): Int {
        if (this >= 25 || input.isExtremeLowRecoveryDay()) return this
        return 25
    }

    private fun RecoveryScoreInput.isExtremeLowRecoveryDay(): Boolean {
        val sleep = bodyMetricLog?.sleepHours
        val stress = bodyMetricLog?.stressLevel
        val soreness = bodyMetricLog?.sorenessLevel
        val protein = foodEntries.sumOf { it.proteinGrams ?: 0.0 }
        val hasProteinData = foodEntries.isNotEmpty()
        return sleep != null && sleep < 5.0 &&
            hasProteinData && protein < 100.0 &&
            stress != null && stress >= 9 &&
            soreness != null && soreness >= 9
    }

    private fun RecoveryScoreInput.hasEnoughData(): Boolean {
        return bodyMetricLog?.sleepHours != null ||
            bodyMetricLog?.energyLevel != null ||
            bodyMetricLog?.stressLevel != null ||
            bodyMetricLog?.sorenessLevel != null ||
            bodyMetricLog?.restingHeartRate != null ||
            foodEntries.isNotEmpty() ||
            todayWorkouts.isNotEmpty() ||
            habitLogs.isNotEmpty()
    }

    private fun RecoveryScoreInput.isHabitComplete(nameFragment: String): Boolean {
        val matchingIds = habitDefinitions
            .filter { it.name.contains(nameFragment, ignoreCase = true) }
            .map { it.id }
            .toSet()
        return habitLogs.any { it.habitDefinitionId in matchingIds && it.status == "COMPLETE" }
    }

    private fun RecoveryScoreInput.isHabitNotDone(nameFragment: String): Boolean {
        val matchingIds = habitDefinitions
            .filter { it.name.contains(nameFragment, ignoreCase = true) }
            .map { it.id }
            .toSet()
        return habitLogs.any { it.habitDefinitionId in matchingIds && it.status == "NOT_DONE" }
    }

    private fun RecoveryScoreInput.setsFor(workoutId: Long): List<WorkoutExercise> {
        return workoutSets.filter { it.workoutId == workoutId }
    }

    private fun recoveryLabel(score: Int): String {
        return when (score) {
            in 80..100 -> "High recovery"
            in 60..79 -> "Moderate recovery"
            in 40..59 -> "Low recovery"
            else -> "Very low recovery"
        }
    }
}

data class RecoveryContributor(
    val label: String,
    val delta: Int,
    val detail: String
)

data class RecoveryScoreInput(
    val bodyMetricLog: BodyMetricLog?,
    val foodEntries: List<FoodEntry>,
    val habitDefinitions: List<HabitDefinition>,
    val habitLogs: List<DailyHabitLog>,
    val todayWorkouts: List<Workout>,
    val recentWorkouts: List<Workout>,
    val workoutSets: List<WorkoutExercise>,
    val recoveryActivities: List<RecoveryActivity> = emptyList(),
    val weeklyWorkouts: List<Workout> = emptyList()
)

data class RecoveryScoreResult(
    val score: Int,
    val label: String,
    val reasons: List<String>,
    val contributors: List<RecoveryContributor> = emptyList()
)

private fun Iterable<Int>.averageOrNull(): Double? {
    val values = toList()
    return if (values.isEmpty()) null else values.average()
}

private fun Double.oneDecimal(): String = String.format("%.1f", this)
