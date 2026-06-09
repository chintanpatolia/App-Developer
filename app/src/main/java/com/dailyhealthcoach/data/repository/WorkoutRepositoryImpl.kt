package com.dailyhealthcoach.data.repository

import com.dailyhealthcoach.data.local.dao.WorkoutDao
import com.dailyhealthcoach.data.local.dao.WorkoutExerciseDao
import com.dailyhealthcoach.data.local.entity.WorkoutEntity
import com.dailyhealthcoach.data.local.entity.WorkoutExerciseEntity
import com.dailyhealthcoach.domain.model.Workout
import com.dailyhealthcoach.domain.model.WorkoutExercise
import com.dailyhealthcoach.domain.model.WorkoutSetInput
import com.dailyhealthcoach.domain.model.WorkoutStatus
import com.dailyhealthcoach.domain.repository.WorkoutRepository
import java.time.Instant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WorkoutRepositoryImpl(
    private val workoutDao: WorkoutDao,
    private val workoutExerciseDao: WorkoutExerciseDao
) : WorkoutRepository {
    override fun observeWorkouts(): Flow<List<Workout>> {
        return workoutDao.observeWorkouts().map { workouts -> workouts.map { it.toDomain() } }
    }

    override fun observeWorkoutsForDate(date: String): Flow<List<Workout>> {
        return workoutDao.observeWorkoutsForDate(date).map { workouts -> workouts.map { it.toDomain() } }
    }

    override fun observeExercisesForWorkout(workoutId: Long): Flow<List<WorkoutExercise>> {
        return workoutExerciseDao.observeForWorkout(workoutId)
            .map { exercises -> exercises.map { it.toDomain() } }
    }

    override fun observeWorkoutSets(): Flow<List<WorkoutExercise>> {
        return workoutExerciseDao.observeAll()
            .map { exercises -> exercises.map { it.toDomain() } }
    }

    override suspend fun saveWorkout(
        date: String,
        name: String,
        status: WorkoutStatus,
        durationMinutes: Int?,
        overallRpe: Int?,
        notes: String?,
        sets: List<WorkoutSetInput>
    ): Long {
        val now = Instant.now().toString()
        val workoutId = workoutDao.insert(
            WorkoutEntity(
                date = date,
                name = name,
                durationMinutes = durationMinutes,
                status = status.storageValue,
                overallRpe = overallRpe,
                notes = notes,
                createdAt = now,
                updatedAt = now
            )
        )

        workoutExerciseDao.insertAll(
            sets.map { set ->
                WorkoutExerciseEntity(
                    workoutId = workoutId,
                    exerciseId = set.exerciseId,
                    setNumber = set.setNumber,
                    reps = set.reps,
                    weight = set.weight,
                    rpe = set.rpe,
                    notes = set.notes
                )
            }
        )
        return workoutId
    }
}

private fun WorkoutEntity.toDomain(): Workout {
    return Workout(
        id = id,
        date = date,
        name = name,
        durationMinutes = durationMinutes,
        status = status,
        overallRpe = overallRpe,
        notes = notes
    )
}

private fun WorkoutExerciseEntity.toDomain(): WorkoutExercise {
    return WorkoutExercise(
        id = id,
        workoutId = workoutId,
        exerciseId = exerciseId,
        setNumber = setNumber,
        reps = reps,
        weight = weight,
        rpe = rpe,
        restSeconds = restSeconds,
        notes = notes
    )
}
