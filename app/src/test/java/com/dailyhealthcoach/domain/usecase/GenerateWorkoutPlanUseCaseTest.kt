package com.dailyhealthcoach.domain.usecase

import com.dailyhealthcoach.domain.model.Exercise
import com.dailyhealthcoach.domain.model.Workout
import com.dailyhealthcoach.domain.model.WorkoutExercise
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GenerateWorkoutPlanUseCaseTest {

    private lateinit var useCase: GenerateWorkoutPlanUseCase

    private val exercises = listOf(
        Exercise(1L, "Bench Press", "Chest", "Barbell", "push", null, false),
        Exercise(2L, "Pull-Up", "Back", "Bodyweight", "pull", null, false),
        Exercise(3L, "Squat", "Legs", "Barbell", "squat", null, false),
        Exercise(4L, "Overhead Press", "Shoulders", "Barbell", "push", null, false),
        Exercise(5L, "Bicep Curl", "Arms", "Dumbbell", "curl", null, false),
        Exercise(6L, "Plank", "Core", "Bodyweight", "isometric", null, false),
        Exercise(7L, "Deadlift", "Back", "Barbell", "hinge", null, false),
        Exercise(8L, "Leg Press", "Legs", "Machine", "push", null, false),
        Exercise(9L, "Incline Press", "Chest", "Dumbbell", "push", null, false),
        Exercise(10L, "Lat Pulldown", "Back", "Cable", "pull", null, false)
    )

    private val today = "2025-06-09"

    @Before
    fun setUp() {
        useCase = GenerateWorkoutPlanUseCase()
    }

    @Test
    fun `STRENGTH with no recent training selects 5 exercises and is a strength day`() {
        val plan = useCase.generate(
            recommendationType = "STRENGTH",
            exercises = exercises,
            recentWorkouts = emptyList(),
            recentSets = emptyList(),
            today = today
        )

        assertTrue(plan.isStrengthDay)
        assertEquals(5, plan.suggestedExercises.size)
        assertEquals(3, plan.setsPerExercise)
        assertEquals("6-12", plan.repsRange)
        assertEquals("7-8", plan.rpeTarget)
        assertTrue(plan.reasons.isNotEmpty())
        assertTrue(plan.nonStrengthActivities.isEmpty())
    }

    @Test
    fun `LOWER_INTENSITY_STRENGTH selects 4 exercises with lighter guidance`() {
        val plan = useCase.generate(
            recommendationType = "LOWER_INTENSITY_STRENGTH",
            exercises = exercises,
            recentWorkouts = emptyList(),
            recentSets = emptyList(),
            today = today
        )

        assertTrue(plan.isStrengthDay)
        assertEquals(4, plan.suggestedExercises.size)
        assertEquals(2, plan.setsPerExercise)
        assertEquals("8-12", plan.repsRange)
        assertEquals("6-7", plan.rpeTarget)
    }

    @Test
    fun `STRENGTH avoids upper body when upper was trained in last 48h`() {
        val recentWorkout = Workout(id = 1L, date = today, name = "Upper Day",
            durationMinutes = 45, status = "COMPLETED", overallRpe = 7, notes = null)
        val recentSets = listOf(
            WorkoutExercise(1L, 1L, 1L, 1, 10, 60.0, 7, null, null),  // Bench Press = Chest
            WorkoutExercise(2L, 1L, 2L, 1, 8, 0.0, 7, null, null)      // Pull-Up = Back
        )

        val plan = useCase.generate(
            recommendationType = "STRENGTH",
            exercises = exercises,
            recentWorkouts = listOf(recentWorkout),
            recentSets = recentSets,
            today = today
        )

        assertTrue(plan.isStrengthDay)
        assertEquals("Lower Body", plan.focus)
        assertTrue(plan.suggestedExercises.none { it.muscleGroup in setOf("Chest", "Back", "Shoulders", "Arms") })
        assertTrue(plan.reasons.any { it.contains("Chest") || it.contains("Back") || it.contains("48h") })
    }

    @Test
    fun `STRENGTH avoids lower body when legs were trained in last 48h`() {
        val recentWorkout = Workout(id = 2L, date = today, name = "Leg Day",
            durationMinutes = 50, status = "COMPLETED", overallRpe = 8, notes = null)
        val recentSets = listOf(
            WorkoutExercise(3L, 2L, 3L, 1, 8, 100.0, 8, null, null),  // Squat = Legs
            WorkoutExercise(4L, 2L, 8L, 1, 10, 80.0, 7, null, null)   // Leg Press = Legs
        )

        val plan = useCase.generate(
            recommendationType = "STRENGTH",
            exercises = exercises,
            recentWorkouts = listOf(recentWorkout),
            recentSets = recentSets,
            today = today
        )

        assertTrue(plan.isStrengthDay)
        assertEquals("Upper Body", plan.focus)
        assertTrue(plan.suggestedExercises.none { it.muscleGroup in setOf("Legs", "Core") })
    }
}
