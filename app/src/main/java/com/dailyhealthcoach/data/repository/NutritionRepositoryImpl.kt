package com.dailyhealthcoach.data.repository

import com.dailyhealthcoach.data.local.dao.FoodEntryDao
import com.dailyhealthcoach.data.local.entity.FoodEntryEntity
import com.dailyhealthcoach.domain.model.FoodEntry
import com.dailyhealthcoach.domain.model.FoodEntryInput
import com.dailyhealthcoach.domain.repository.NutritionRepository
import java.time.Instant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NutritionRepositoryImpl(
    private val foodEntryDao: FoodEntryDao
) : NutritionRepository {
    override fun observeFoodEntriesForDate(date: String): Flow<List<FoodEntry>> {
        return foodEntryDao.observeForDate(date).map { entries -> entries.map { it.toDomain() } }
    }

    override fun observeAll(): Flow<List<FoodEntry>> {
        return foodEntryDao.observeAll().map { entries -> entries.map { it.toDomain() } }
    }

    override suspend fun saveFoodEntry(input: FoodEntryInput) {
        val now = Instant.now().toString()
        foodEntryDao.upsert(
            FoodEntryEntity(
                id = input.id,
                date = input.date,
                mealName = input.mealName,
                foodName = input.foodName,
                brandName = input.brandName,
                servingDescription = input.servingDescription,
                calories = input.calories,
                proteinGrams = input.proteinGrams,
                carbGrams = input.carbGrams,
                fatGrams = input.fatGrams,
                fiberGrams = input.fiberGrams,
                mealTime = input.mealTime,
                isWholeFoodBased = input.isWholeFoodBased,
                isProcessed = input.isProcessed,
                isFermented = input.isFermented,
                source = "MANUAL",
                createdAt = now,
                updatedAt = now
            )
        )
    }

    override suspend fun deleteFoodEntry(id: Long) {
        foodEntryDao.deleteById(id)
    }

    override suspend fun setFoodEntrySaved(id: Long, saved: Boolean) {
        foodEntryDao.setSaved(id, if (saved) 1 else 0)
    }

    override suspend fun getFoodEntriesForDate(date: String): List<FoodEntry> {
        return foodEntryDao.getForDate(date).map { it.toDomain() }
    }
}

private fun FoodEntryEntity.toDomain(): FoodEntry {
    return FoodEntry(
        id = id,
        date = date,
        mealName = mealName,
        foodName = foodName,
        brandName = brandName,
        servingDescription = servingDescription,
        calories = calories,
        proteinGrams = proteinGrams,
        carbGrams = carbGrams,
        fatGrams = fatGrams,
        fiberGrams = fiberGrams,
        mealTime = mealTime,
        isWholeFoodBased = isWholeFoodBased,
        isProcessed = isProcessed,
        isFermented = isFermented,
        isSaved = isSaved
    )
}
