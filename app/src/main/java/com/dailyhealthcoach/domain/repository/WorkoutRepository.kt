package com.dailyhealthcoach.domain.repository

import com.dailyhealthcoach.domain.model.Workout
import com.dailyhealthcoach.domain.model.WorkoutExercise
import com.dailyhealthcoach.domain.model.WorkoutSetInput
import com.dailyhealthcoach.domain.model.WorkoutStatus
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun observeWorkouts(): Flow<List<Workout>>
    fun observeWorkoutsForDate(date: String): Flow<List<Workout>>
    fun observeExercisesForWorkout(workoutId: Long): Flow<List<WorkoutExercise>>
    fun observeWorkoutSets(): Flow<List<WorkoutExercise>>
    suspend fun saveWorkout(
        date: String,
        name: String,
        status: WorkoutStatus,
        durationMinutes: Int?,
        overallRpe: Int?,
        notes: String?,
        sets: List<WorkoutSetInput>
    )
}
