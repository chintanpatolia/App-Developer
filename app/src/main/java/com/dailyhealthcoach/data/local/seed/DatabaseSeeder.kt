package com.dailyhealthcoach.data.local.seed

import com.dailyhealthcoach.data.local.AppDatabase
import com.dailyhealthcoach.data.local.entity.ExerciseEntity
import com.dailyhealthcoach.data.local.entity.HabitDefinitionEntity
import com.dailyhealthcoach.data.local.entity.MacroTargetEntity
import com.dailyhealthcoach.data.local.entity.UserProfileEntity
import java.time.Instant

class DatabaseSeeder(
    private val database: AppDatabase
) {
    suspend fun seedIfNeeded() {
        seedUserProfile()
        seedDefaultHabits()
        seedDefaultExercises()
        seedDefaultMacroTarget()
    }

    private suspend fun seedUserProfile() {
        val now = Instant.now().toString()
        database.userProfileDao().upsert(
            UserProfileEntity(
                id = 1,
                createdAt = now,
                updatedAt = now
            )
        )
    }

    private suspend fun seedDefaultHabits() {
        if (database.habitDefinitionDao().countHabits() > 0) return

        database.habitDefinitionDao().insertAll(
            listOf(
                HabitDefinitionEntity(
                    name = "Morning sunlight",
                    description = "Get outdoor light early in the day.",
                    frequencyType = "DAILY",
                    sortOrder = 1
                ),
                HabitDefinitionEntity(
                    name = "Protein goal",
                    description = "Reach the daily protein target.",
                    frequencyType = "DAILY",
                    sortOrder = 2
                ),
                HabitDefinitionEntity(
                    name = "Strength training",
                    description = "Complete planned strength sessions 3-4 times per week.",
                    frequencyType = "WEEKLY_TARGET",
                    targetPerWeek = 4,
                    sortOrder = 3
                ),
                HabitDefinitionEntity(
                    name = "Steps",
                    description = "Walk 8,000-10,000 steps.",
                    frequencyType = "DAILY",
                    sortOrder = 4
                ),
                HabitDefinitionEntity(
                    name = "Avoid late eating",
                    description = "Avoid eating 2-3 hours before bed.",
                    frequencyType = "DAILY",
                    sortOrder = 5
                ),
                HabitDefinitionEntity(
                    name = "Sleep",
                    description = "Sleep 7+ hours.",
                    frequencyType = "DAILY",
                    sortOrder = 6
                ),
                HabitDefinitionEntity(
                    name = "Bloodwork reminder",
                    description = "Calendar-style reminder every 6-12 months. No medical interpretation.",
                    frequencyType = "INTERVAL",
                    targetPerWeek = null,
                    sortOrder = 7
                ),
                HabitDefinitionEntity(
                    name = "Breathwork",
                    description = "Use breathwork during high-stress periods.",
                    frequencyType = "AS_NEEDED",
                    sortOrder = 8
                ),
                HabitDefinitionEntity(
                    name = "Whole foods",
                    description = "Eat mostly whole-food based meals.",
                    frequencyType = "DAILY",
                    sortOrder = 9
                ),
                HabitDefinitionEntity(
                    name = "Fermented foods",
                    description = "Include fermented foods several times per week.",
                    frequencyType = "WEEKLY_TARGET",
                    targetPerWeek = 3,
                    sortOrder = 10
                )
            )
        )
    }

    private suspend fun seedDefaultExercises() {
        if (database.exerciseDao().countExercises() > 0) return

        database.exerciseDao().insertAll(
            listOf(
                ExerciseEntity(name = "Bench Press", muscleGroup = "Chest", equipmentType = "Barbell", movementPattern = "Horizontal push"),
                ExerciseEntity(name = "Push-Up", muscleGroup = "Chest", equipmentType = "Bodyweight", movementPattern = "Horizontal push"),
                ExerciseEntity(name = "Incline Dumbbell Press", muscleGroup = "Chest", equipmentType = "Dumbbell", movementPattern = "Incline push"),
                ExerciseEntity(name = "Pull-Up", muscleGroup = "Back", equipmentType = "Bodyweight", movementPattern = "Vertical pull"),
                ExerciseEntity(name = "Barbell Row", muscleGroup = "Back", equipmentType = "Barbell", movementPattern = "Horizontal pull"),
                ExerciseEntity(name = "Lat Pulldown", muscleGroup = "Back", equipmentType = "Machine", movementPattern = "Vertical pull"),
                ExerciseEntity(name = "Back Squat", muscleGroup = "Legs", equipmentType = "Barbell", movementPattern = "Squat"),
                ExerciseEntity(name = "Romanian Deadlift", muscleGroup = "Legs", equipmentType = "Barbell", movementPattern = "Hinge"),
                ExerciseEntity(name = "Walking Lunge", muscleGroup = "Legs", equipmentType = "Dumbbell", movementPattern = "Lunge"),
                ExerciseEntity(name = "Overhead Press", muscleGroup = "Shoulders", equipmentType = "Barbell", movementPattern = "Vertical push"),
                ExerciseEntity(name = "Lateral Raise", muscleGroup = "Shoulders", equipmentType = "Dumbbell", movementPattern = "Isolation"),
                ExerciseEntity(name = "Biceps Curl", muscleGroup = "Arms", equipmentType = "Dumbbell", movementPattern = "Isolation"),
                ExerciseEntity(name = "Triceps Pressdown", muscleGroup = "Arms", equipmentType = "Cable", movementPattern = "Isolation"),
                ExerciseEntity(name = "Plank", muscleGroup = "Core", equipmentType = "Bodyweight", movementPattern = "Anti-extension"),
                ExerciseEntity(name = "Hanging Knee Raise", muscleGroup = "Core", equipmentType = "Bodyweight", movementPattern = "Core flexion"),
                ExerciseEntity(name = "Treadmill Walk", muscleGroup = "Cardio", equipmentType = "Machine", movementPattern = "Steady state")
            )
        )
    }

    private suspend fun seedDefaultMacroTarget() {
        if (database.macroTargetDao().countTargets() > 0) return

        val now = Instant.now().toString()
        database.macroTargetDao().insert(
            MacroTargetEntity(
                proteinMinGrams = 170,
                proteinMaxGrams = 200,
                calorieTarget = null,
                carbTargetGrams = null,
                fatTargetGrams = null,
                fiberTargetGrams = null,
                createdAt = now,
                updatedAt = now
            )
        )
    }
}
