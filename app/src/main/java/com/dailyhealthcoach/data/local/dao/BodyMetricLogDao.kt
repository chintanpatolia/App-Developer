package com.dailyhealthcoach.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dailyhealthcoach.data.local.entity.BodyMetricLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BodyMetricLogDao {
    @Query("SELECT * FROM body_metric_logs WHERE date = :date LIMIT 1")
    fun observeForDate(date: String): Flow<BodyMetricLogEntity?>

    @Query("SELECT * FROM body_metric_logs ORDER BY date DESC")
    fun observeAll(): Flow<List<BodyMetricLogEntity>>

    @Upsert
    suspend fun upsert(log: BodyMetricLogEntity)
}
