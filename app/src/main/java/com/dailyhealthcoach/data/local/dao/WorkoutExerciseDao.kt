package com.dailyhealthcoach.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.dailyhealthcoach.data.local.entity.WorkoutExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutExerciseDao {
    @Query("SELECT * FROM workout_exercises WHERE workoutId = :workoutId ORDER BY setNumber ASC")
    fun observeForWorkout(workoutId: Long): Flow<List<WorkoutExerciseEntity>>

    @Query("SELECT * FROM workout_exercises ORDER BY workoutId DESC, setNumber ASC")
    fun observeAll(): Flow<List<WorkoutExerciseEntity>>

    @Insert
    suspend fun insertAll(workoutExercises: List<WorkoutExerciseEntity>)

    @Upsert
    suspend fun upsert(workoutExercise: WorkoutExerciseEntity)
}
