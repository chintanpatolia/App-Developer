package com.dailyhealthcoach.domain.recommendation

import com.dailyhealthcoach.domain.model.BodyMetricLog
import com.dailyhealthcoach.domain.model.FoodEntry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class NextDayRecommendationServiceTest {
    private val service = NextDayRecommendationService()

    @Test
    fun lowRecoveryWithPoorSleepAndHighSorenessRecommendsRest() {
        val recommendation = service.calculate(
            input(
                recoveryScore = 25,
                bodyMetricLog = bodyMetricLog(
                    sleepHours = 5.5,
                    energyLevel = 4,
                    stressLevel = 8,
                    sorenessLevel = 8
                ),
                proteinGrams = 115.0
            )
        )

        assertNotNull(recommendation)
        assertEquals("Rest day", recommendation?.title)
        assertTrue(recommendation?.reasonBullets.orEmpty().any { it.contains("Sleep was low") })
        assertTrue(recommendation?.reasonBullets.orEmpty().any { it.contains("Protein is below target") })
    }

    @Test
    fun lowRecoveryWithoutStrongFatigueMarkerRecommendsWalkingMobility() {
        val recommendation = service.calculate(
            input(
                recoveryScore = 25,
                bodyMetricLog = bodyMetricLog(
                    sleepHours = 5.5,
                    energyLevel = 4,
                    stressLevel = 8,
                    sorenessLevel = 6
                ),
                proteinGrams = 115.0
            )
        )

        assertNotNull(recommendation)
        assertEquals("Walking / mobility day", recommendation?.title)
        assertEquals("20-40 minute easy walk, light mobility, hydration, breathwork", recommendation?.suggestedFocus)
        assertTrue(recommendation?.reasonBullets.orEmpty().any { it.contains("Recovery score is 25/100") })
        assertTrue(recommendation?.reasonBullets.orEmpty().any { it.contains("Sleep was low at 5.5 hours") })
        assertTrue(recommendation?.reasonBullets.orEmpty().any { it.contains("Protein is below target at 115g") })
        assertTrue(recommendation?.reasonBullets.orEmpty().any { it.contains("Stress is elevated at 8/10") })
    }

    @Test
    fun highRecoveryBelowWeeklyStrengthTargetRecommendsStrength() {
        val recommendation = service.calculate(
            input(
                recoveryScore = 82,
                bodyMetricLog = bodyMetricLog(
                    sleepHours = 7.5,
                    energyLevel = 8,
                    stressLevel = 3,
                    sorenessLevel = 3
                ),
                proteinGrams = 180.0
            )
        )

        assertNotNull(recommendation)
        assertEquals("Strength training day", recommendation?.title)
        assertTrue(recommendation?.reasonBullets.orEmpty().any { it.contains("Recovery score is 82/100") })
    }

    private fun input(
        recoveryScore: Int,
        bodyMetricLog: BodyMetricLog,
        proteinGrams: Double
    ): RecommendationInput {
        return RecommendationInput(
            date = "2026-06-08",
            recoveryScore = recoveryScore,
            bodyMetricLog = bodyMetricLog,
            foodEntries = listOf(foodEntry(proteinGrams)),
            habitDefinitions = emptyList(),
            habitLogs = emptyList(),
            todayWorkouts = emptyList(),
            recentWorkouts = emptyList(),
            weeklyWorkouts = emptyList(),
            workoutSets = emptyList(),
            exercises = emptyList()
        )
    }

    private fun bodyMetricLog(
        sleepHours: Double,
        energyLevel: Int,
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
