package com.supplementtracker.data.dao

import androidx.room.*
import com.supplementtracker.data.entity.DailyOccurrenceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyOccurrenceDao {
    @Query("SELECT * FROM daily_occurrences WHERE scheduledDate = :date ORDER BY scheduledHour ASC, scheduledMinute ASC")
    fun observeByDate(date: String): Flow<List<DailyOccurrenceEntity>>

    @Query("SELECT * FROM daily_occurrences WHERE scheduledDate = :date ORDER BY scheduledHour ASC, scheduledMinute ASC")
    suspend fun getByDate(date: String): List<DailyOccurrenceEntity>

    @Query("SELECT * FROM daily_occurrences WHERE supplementId = :supplementId AND scheduledDate = :date LIMIT 1")
    suspend fun getBySupplementAndDate(supplementId: Long, date: String): DailyOccurrenceEntity?

    @Query("SELECT * FROM daily_occurrences WHERE scheduleGroupId = :groupId AND scheduledDate = :date")
    suspend fun getByGroupAndDate(groupId: Long, date: String): List<DailyOccurrenceEntity>

    @Query("SELECT * FROM daily_occurrences WHERE scheduledDate BETWEEN :startDate AND :endDate ORDER BY scheduledDate ASC")
    suspend fun getInRange(startDate: String, endDate: String): List<DailyOccurrenceEntity>

    @Query("SELECT * FROM daily_occurrences ORDER BY scheduledDate ASC LIMIT 1")
    suspend fun getEarliest(): DailyOccurrenceEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(occurrence: DailyOccurrenceEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(occurrences: List<DailyOccurrenceEntity>): List<Long>

    @Update
    suspend fun update(occurrence: DailyOccurrenceEntity)

    @Query("UPDATE daily_occurrences SET completed = :completed, completedAt = :completedAt WHERE id = :id")
    suspend fun setCompleted(id: Long, completed: Boolean, completedAt: Long?)

    @Query("UPDATE daily_occurrences SET completed = :completed, completedAt = :completedAt WHERE supplementId = :supplementId AND scheduledDate = :date")
    suspend fun setCompletedBySupplementAndDate(supplementId: Long, date: String, completed: Boolean, completedAt: Long?)

    @Query("DELETE FROM daily_occurrences WHERE supplementId = :supplementId AND scheduledDate = :date")
    suspend fun deleteBySupplementAndDate(supplementId: Long, date: String)

    @Query("SELECT DISTINCT scheduledDate FROM daily_occurrences ORDER BY scheduledDate ASC")
    suspend fun getAllDates(): List<String>
}
