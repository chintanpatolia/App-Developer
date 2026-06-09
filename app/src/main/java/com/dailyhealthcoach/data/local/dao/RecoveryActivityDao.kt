package com.dailyhealthcoach.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dailyhealthcoach.data.local.entity.RecoveryActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecoveryActivityDao {
    @Insert
    suspend fun insertAll(activities: List<RecoveryActivityEntity>)

    @Query("SELECT * FROM recovery_activity_logs ORDER BY id ASC")
    fun observeAll(): Flow<List<RecoveryActivityEntity>>
}
