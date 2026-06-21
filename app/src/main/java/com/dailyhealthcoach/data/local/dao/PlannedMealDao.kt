package com.dailyhealthcoach.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dailyhealthcoach.data.local.entity.PlannedMealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlannedMealDao {
    @Query("SELECT * FROM planned_meals WHERE date = :date ORDER BY id ASC")
    fun observeForDate(date: String): Flow<List<PlannedMealEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(meals: List<PlannedMealEntity>)

    @Query("DELETE FROM planned_meals WHERE date IN (:dates)")
    suspend fun deleteForDates(dates: List<String>)
}
