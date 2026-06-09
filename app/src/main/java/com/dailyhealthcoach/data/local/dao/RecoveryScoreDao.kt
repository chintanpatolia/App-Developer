package com.dailyhealthcoach.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dailyhealthcoach.data.local.entity.RecoveryScoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecoveryScoreDao {
    @Query("SELECT * FROM recovery_scores WHERE date = :date LIMIT 1")
    fun observeForDate(date: String): Flow<RecoveryScoreEntity?>

    @Query("SELECT * FROM recovery_scores WHERE date = :date LIMIT 1")
    suspend fun getForDate(date: String): RecoveryScoreEntity?

    @Upsert
    suspend fun upsert(score: RecoveryScoreEntity)
}
