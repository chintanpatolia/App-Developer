package com.dailyhealthcoach.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.dailyhealthcoach.data.local.entity.RecoveryActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecoveryActivityDao {
    @Insert
    suspend fun insertAll(activities: List<RecoveryActivityEntity>)

    @Upsert
    suspend fun upsertAll(activities: List<RecoveryActivityEntity>)

    @Query("SELECT * FROM recovery_activity_logs ORDER BY id ASC")
    fun observeAll(): Flow<List<RecoveryActivityEntity>>

    @Query("DELETE FROM recovery_activity_logs WHERE workout_id = :workoutId")
    suspend fun deleteForWorkout(workoutId: Long)
}
