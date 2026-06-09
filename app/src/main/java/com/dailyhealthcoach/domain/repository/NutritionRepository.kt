package com.dailyhealthcoach.domain.repository

import com.dailyhealthcoach.domain.model.FoodEntry
import com.dailyhealthcoach.domain.model.FoodEntryInput
import kotlinx.coroutines.flow.Flow

interface NutritionRepository {
    fun observeFoodEntriesForDate(date: String): Flow<List<FoodEntry>>
    fun observeAll(): Flow<List<FoodEntry>>
    suspend fun saveFoodEntry(input: FoodEntryInput)
    suspend fun deleteFoodEntry(id: Long)
}
