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

    @Query("SELECT * FROM food_entries ORDER BY date DESC")
    fun observeAll(): Flow<List<FoodEntryEntity>>

    @Upsert
    suspend fun upsert(entry: FoodEntryEntity)

    @Query("DELETE FROM food_entries WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("UPDATE food_entries SET isSaved = :saved WHERE id = :id")
    suspend fun setSaved(id: Long, saved: Int)

    @Query("SELECT * FROM food_entries WHERE date = :date ORDER BY mealTime ASC, id ASC")
    suspend fun getForDate(date: String): List<FoodEntryEntity>
}
