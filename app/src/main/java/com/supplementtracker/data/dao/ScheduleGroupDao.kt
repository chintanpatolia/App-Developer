package com.supplementtracker.data.dao

import androidx.room.*
import com.supplementtracker.data.entity.ScheduleGroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleGroupDao {
    @Query("SELECT * FROM schedule_groups ORDER BY sortOrder ASC")
    fun observeAll(): Flow<List<ScheduleGroupEntity>>

    @Query("SELECT * FROM schedule_groups ORDER BY sortOrder ASC")
    suspend fun getAll(): List<ScheduleGroupEntity>

    @Query("SELECT * FROM schedule_groups WHERE id = :id")
    suspend fun getById(id: Long): ScheduleGroupEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(group: ScheduleGroupEntity): Long

    @Update
    suspend fun update(group: ScheduleGroupEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(groups: List<ScheduleGroupEntity>)
}
