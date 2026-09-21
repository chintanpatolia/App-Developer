package com.supplementtracker.data.dao

import androidx.room.*
import com.supplementtracker.data.entity.SupplementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SupplementDao {
    @Query("SELECT * FROM supplements WHERE active = 1 ORDER BY scheduleGroupId ASC, sortOrder ASC, id ASC")
    fun observeActive(): Flow<List<SupplementEntity>>

    @Query("SELECT * FROM supplements ORDER BY scheduleGroupId ASC, sortOrder ASC, id ASC")
    fun observeAll(): Flow<List<SupplementEntity>>

    @Query("SELECT * FROM supplements WHERE active = 1 ORDER BY scheduleGroupId ASC, sortOrder ASC, id ASC")
    suspend fun getActive(): List<SupplementEntity>

    @Query("SELECT * FROM supplements WHERE scheduleGroupId = :groupId AND active = 1")
    suspend fun getActiveByGroup(groupId: Long): List<SupplementEntity>

    @Query("SELECT * FROM supplements WHERE id = :id")
    suspend fun getById(id: Long): SupplementEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(supplement: SupplementEntity): Long

    @Update
    suspend fun update(supplement: SupplementEntity)

    @Delete
    suspend fun delete(supplement: SupplementEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(supplements: List<SupplementEntity>)

    @Query("SELECT COUNT(*) FROM supplements")
    suspend fun count(): Int
}
