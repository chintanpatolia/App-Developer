package com.dailyhealthcoach.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.dailyhealthcoach.data.local.entity.DailyHabitLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyHabitLogDao {
    @Query("SELECT * FROM daily_habit_logs WHERE date = :date")
    fun observeLogsForDate(date: String): Flow<List<DailyHabitLogEntity>>

    @Query("SELECT * FROM daily_habit_logs WHERE date BETWEEN :startDate AND :endDate")
    fun observeLogsBetween(startDate: String, endDate: String): Flow<List<DailyHabitLogEntity>>

    @Query("SELECT * FROM daily_habit_logs ORDER BY date DESC")
    suspend fun getAll(): List<DailyHabitLogEntity>

    @Upsert
    suspend fun upsert(log: DailyHabitLogEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIgnore(log: DailyHabitLogEntity): Long

    @Query(
        """
        UPDATE daily_habit_logs
        SET status = :status, notes = :notes, updatedAt = :updatedAt
        WHERE habitDefinitionId = :habitDefinitionId AND date = :date
        """
    )
    suspend fun updateStatus(
        habitDefinitionId: Long,
        date: String,
        status: String,
        notes: String?,
        updatedAt: String
    )
}
