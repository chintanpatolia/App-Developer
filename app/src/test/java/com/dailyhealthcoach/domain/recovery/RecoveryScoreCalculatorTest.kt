package com.dailyhealthcoach.domain.recovery

import com.dailyhealthcoach.domain.model.BodyMetricLog
import com.dailyhealthcoach.domain.model.DailyHabitLog
import com.dailyhealthcoach.domain.model.FoodEntry
import com.dailyhealthcoach.domain.model.HabitDefinition
import com.dailyhealthcoach.domain.model.Workout
import com.dailyhealthcoach.domain.model.WorkoutExercise
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RecoveryScoreCalculatorTest {
    private val calculator = RecoveryScoreCalculator()

    @Test
    fun highRecoveryDataProducesHighRecoveryLabel() {
        val result = calculator.calculate(
            baseInput(
                bodyMetricLog = bodyMetricLog(
                    sleepHours = 7.5,
                    energyLevel = 8,
                    stressLevel = 3,
                    sorenessLevel = 3
                ),
                foodEntries = listOf(foodEntry(proteinGrams = 180.0)),
                habitLogs = listOf(
                    DailyHabitLog(1, 1, "2026-06-08", "COMPLETE", null),
                    DailyHabitLog(2, 2, "2026-06-08", "COMPLETE", null)
                )
            )
        )

        assertNotNull(result)
        assertEquals("High recovery", result?.label)
        assertEquals(100, result?.score)
    }

    @Test
    fun lowRecoveryDataProducesVeryLowRecoveryLabel() {
        val workout = Workout(1, "2026-06-08", "Hard session", null, "COMPLETED", 9, null)
        val result = calculator.calculate(
            baseInput(
                bodyMetricLog = bodyMetricLog(
                    sleepHours = 5.0,
                    energyLevel = 4,
                    stressLevel = 9,
                    sorenessLevel = 9
                ),
                foodEntries = listOf(foodEntry(proteinGrams = 90.0)),
                todayWorkouts = listOf(workout),
                recentWorkouts = listOf(workout),
                workoutSets = List(25) { index ->
                    WorkoutExercise(
                        id = index.toLong(),
                        workoutId = 1,
                        exerciseId = 1,
                        setNumber = index + 1,
                        reps = 8,
                        weight = 100.0,
                        rpe = 9,
                        restSeconds = null,
                        notes = null
                    )
                }
            )
        )

        assertNotNull(result)
        assertEquals("Very low recovery", result?.label)
        assertTrue((result?.score ?: 100) < 40)
    }

    @Test
    fun difficultButNotExtremeDayDoesNotDropToZero() {
        val result = calculator.calculate(
            baseInput(
                bodyMetricLog = bodyMetricLog(
                    sleepHours = 5.5,
                    energyLevel = null,
                    stressLevel = 8,
                    sorenessLevel = 8
                ),
                foodEntries = listOf(foodEntry(proteinGrams = 115.0))
            )
        )

        assertNotNull(result)
        assertEquals("Very low recovery", result?.label)
        assertTrue((result?.score ?: 0) in 25..40)
    }

    private fun baseInput(
        bodyMetricLog: BodyMetricLog? = null,
        foodEntries: List<FoodEntry> = emptyList(),
        habitLogs: List<DailyHabitLog> = emptyList(),
        todayWorkouts: List<Workout> = emptyList(),
        recentWorkouts: List<Workout> = emptyList(),
        workoutSets: List<WorkoutExercise> = emptyList()
    ): RecoveryScoreInput {
        return RecoveryScoreInput(
            bodyMetricLog = bodyMetricLog,
            foodEntries = foodEntries,
            habitDefinitions = listOf(
                HabitDefinition(1, "Whole foods", "", "DAILY", null, 1),
                HabitDefinition(2, "Morning sunlight", "", "DAILY", null, 2)
            ),
            habitLogs = habitLogs,
            todayWorkouts = todayWorkouts,
            recentWorkouts = recentWorkouts,
            workoutSets = workoutSets
        )
    }

    private fun bodyMetricLog(
        sleepHours: Double,
        energyLevel: Int?,
        stressLevel: Int,
        sorenessLevel: Int
    ): BodyMetricLog {
        return BodyMetricLog(
            id = 1,
            date = "2026-06-08",
            heightInches = null,
            bodyWeight = null,
            bodyFatPercentage = null,
            calculatedBodyFatPercent = null,
            manualBodyFatPercent = null,
            isBodyFatOverridden = false,
            waistMeasurement = null,
            neckMeasurement = null,
            chestMeasurement = null,
            armMeasurement = null,
            sleepHours = sleepHours,
            energyLevel = energyLevel,
            stressLevel = stressLevel,
            sorenessLevel = sorenessLevel,
            restingHeartRate = null,
            stepCount = null,
            notes = null
        )
    }

    private fun foodEntry(proteinGrams: Double): FoodEntry {
        return FoodEntry(
            id = 1,
            date = "2026-06-08",
            mealName = "Lunch",
            foodName = "Chicken",
            brandName = null,
            servingDescription = null,
            calories = 400,
            proteinGrams = proteinGrams,
            carbGrams = null,
            fatGrams = null,
            fiberGrams = null,
            mealTime = null,
            isWholeFoodBased = true,
            isProcessed = false,
            isFermented = false
        )
    }
}
