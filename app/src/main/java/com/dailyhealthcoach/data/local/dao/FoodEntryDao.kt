package com.dailyhealthcoach.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dailyhealthcoach.data.local.entity.FoodEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodEntryDao {
    @Query("SELECT * FROM food_entries WHERE date = :date ORDER BY mealTime ASC, id ASC")
    fun observeForDate(date: String): Flow<List<FoodEntryEntity>>

    @Upsert
    suspend fun upsert(entry: FoodEntryEntity)

    @Query("DELETE FROM food_entries WHERE id = :id")
    suspend fun deleteById(id: Long)
}
