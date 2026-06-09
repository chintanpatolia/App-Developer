package com.dailyhealthcoach.domain.recovery

import com.dailyhealthcoach.domain.model.BodyMetricLog
import com.dailyhealthcoach.domain.model.DailyHabitLog
import com.dailyhealthcoach.domain.model.FoodEntry
import com.dailyhealthcoach.domain.model.HabitDefinition
import com.dailyhealthcoach.domain.model.Workout
import com.dailyhealthcoach.domain.model.WorkoutExercise

class RecoveryScoreCalculator {
    fun calculate(input: RecoveryScoreInput): RecoveryScoreResult? {
        if (!input.hasEnoughData()) return null

        var score = 50
        val reasons = mutableListOf<String>()

        val sleep = input.bodyMetricLog?.sleepHours
        when {
            sleep == null -> Unit
            sleep >= 7.5 -> {
                score += 20
                reasons += "Sleep was ${sleep.oneDecimal()} hours"
            }
            sleep >= 7.0 -> {
                score += 15
                reasons += "Sleep was solid at ${sleep.oneDecimal()} hours"
            }
            sleep >= 6.0 -> {
                score += 5
                reasons += "Sleep was moderate at ${sleep.oneDecimal()} hours"
            }
            sleep >= 5.0 -> {
                score -= 10
                reasons += "Sleep was low at ${sleep.oneDecimal()} hours"
            }
            else -> {
                score -= 20
                reasons += "Sleep was very low at ${sleep.oneDecimal()} hours"
            }
        }

        val protein = input.foodEntries.sumOf { it.proteinGrams ?: 0.0 }
        when {
            protein >= 170.0 -> {
                score += 15
                reasons += "Protein goal is on track at ${protein.toInt()}g"
            }
            protein >= 140.0 -> {
                score += 5
                reasons += "Protein is close to target at ${protein.toInt()}g"
            }
            input.foodEntries.isNotEmpty() && protein >= 100.0 -> {
                score -= 5
                reasons += "Protein is below target at ${protein.toInt()}g"
            }
            input.foodEntries.isNotEmpty() -> {
                score -= 15
                reasons += "Protein is low at ${protein.toInt()}g"
            }
        }

        input.bodyMetricLog?.energyLevel?.let { energy ->
            when (energy) {
                in 8..10 -> {
                    score += 10
                    reasons += "Energy is strong at $energy/10"
                }
                in 6..7 -> {
                    score += 5
                    reasons += "Energy is steady at $energy/10"
                }
                in 4..5 -> {
                    score -= 5
                    reasons += "Energy is low at $energy/10"
                }
                in 1..3 -> {
                    score -= 10
                    reasons += "Energy is very low at $energy/10"
                }
            }
        }

        input.bodyMetricLog?.stressLevel?.let { stress ->
            when (stress) {
                in 1..3 -> {
                    score += 10
                    reasons += "Stress is well managed at $stress/10"
                }
                in 7..8 -> {
                    score -= 10
                    reasons += "Stress is elevated at $stress/10"
                }
                in 9..10 -> {
                    score -= 20
                    reasons += "Stress is very high at $stress/10"
                }
            }
        }

        input.bodyMetricLog?.sorenessLevel?.let { soreness ->
            when (soreness) {
                in 1..3 -> {
                    score += 10
                    reasons += "Soreness is manageable at $soreness/10"
                }
                in 7..8 -> {
                    score -= 10
                    reasons += "Soreness is high at $soreness/10"
                }
                in 9..10 -> {
                    score -= 20
                    reasons += "Soreness is very high at $soreness/10"
                }
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

        val recentSetCount = input.recentWorkouts.sumOf { workout -> input.setsFor(workout.id).size }
        val recentAverageRpe = input.recentWorkouts
            .mapNotNull { workout ->
                val avgSetRpe = input.setsFor(workout.id).mapNotNull { it.rpe }.averageOrNull()
                listOfNotNull(workout.overallRpe?.toDouble(), avgSetRpe).maxOrNull()
            }
            .maxOrNull()
        when {
            recentAverageRpe != null && recentAverageRpe >= 9.0 && recentSetCount >= 20 -> {
                score -= 15
                reasons += "Workout load was very hard recently"
            }
            recentAverageRpe != null && recentAverageRpe >= 8.0 -> {
                score -= 10
                reasons += "A hard workout was logged recently"
            }
            recentAverageRpe != null || input.recentWorkouts.isNotEmpty() -> {
                score -= 5
                reasons += "Moderate workout load was logged recently"
            }
        }

        if (input.isHabitNotDone("late eating")) {
            score -= 5
            reasons += "Late eating habit was not done"
        }

        if (input.bodyMetricLog?.stressLevel != null && input.bodyMetricLog.stressLevel >= 7 && input.isHabitComplete("breath")) {
            score += 5
            reasons += "Breathwork was completed despite high stress"
        }

        val clampedScore = score.recoveryFloor(input).coerceIn(0, 100)
        return RecoveryScoreResult(
            score = clampedScore,
            label = recoveryLabel(clampedScore),
            reasons = reasons.take(4).ifEmpty { listOf("Recovery score is based on today's logged health data") }
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

data class RecoveryScoreInput(
    val bodyMetricLog: BodyMetricLog?,
    val foodEntries: List<FoodEntry>,
    val habitDefinitions: List<HabitDefinition>,
    val habitLogs: List<DailyHabitLog>,
    val todayWorkouts: List<Workout>,
    val recentWorkouts: List<Workout>,
    val workoutSets: List<WorkoutExercise>
)

data class RecoveryScoreResult(
    val score: Int,
    val label: String,
    val reasons: List<String>
)

private fun Iterable<Int>.averageOrNull(): Double? {
    val values = toList()
    return if (values.isEmpty()) null else values.average()
}

private fun Double.oneDecimal(): String = String.format("%.1f", this)
