package com.dailyhealthcoach.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.dailyhealthcoach.data.local.entity.HabitDefinitionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDefinitionDao {
    @Query("SELECT * FROM habit_definitions WHERE isActive = 1 ORDER BY sortOrder ASC")
    fun observeActiveHabits(): Flow<List<HabitDefinitionEntity>>

    @Query("SELECT * FROM habit_definitions ORDER BY sortOrder ASC")
    suspend fun getAll(): List<HabitDefinitionEntity>

    @Query("SELECT COUNT(*) FROM habit_definitions")
    suspend fun countHabits(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(habits: List<HabitDefinitionEntity>)

    @Upsert
    suspend fun upsertAll(habits: List<HabitDefinitionEntity>)
}
