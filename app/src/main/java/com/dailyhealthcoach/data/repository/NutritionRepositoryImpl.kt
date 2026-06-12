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
                barcode = input.barcode,
                servingDescription = input.servingDescription,
                quantity = input.quantity,
                calories = input.calories,
                proteinGrams = input.proteinGrams,
                carbGrams = input.carbGrams,
                fatGrams = input.fatGrams,
                fiberGrams = input.fiberGrams,
                mealTime = input.mealTime,
                isWholeFoodBased = input.isWholeFoodBased,
                isProcessed = input.isProcessed,
                isFermented = input.isFermented,
                source = input.source,
                createdAt = now,
                updatedAt = now,
                vitaminA = input.vitaminA,
                vitaminC = input.vitaminC,
                vitaminD = input.vitaminD,
                vitaminB12 = input.vitaminB12,
                calcium = input.calcium,
                iron = input.iron,
                potassium = input.potassium,
                magnesium = input.magnesium,
                sodium = input.sodium,
                zinc = input.zinc,
                vitaminE = input.vitaminE,
                vitaminK = input.vitaminK,
                vitaminB1 = input.vitaminB1,
                vitaminB2 = input.vitaminB2,
                vitaminB3 = input.vitaminB3,
                vitaminB6 = input.vitaminB6,
                folate = input.folate,
                biotin = input.biotin,
                pantothenicAcid = input.pantothenicAcid,
                phosphorus = input.phosphorus,
                iodine = input.iodine,
                selenium = input.selenium,
                copper = input.copper,
                manganese = input.manganese,
                chromium = input.chromium,
                molybdenum = input.molybdenum
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
        quantity = quantity,
        calories = calories,
        proteinGrams = proteinGrams,
        carbGrams = carbGrams,
        fatGrams = fatGrams,
        fiberGrams = fiberGrams,
        mealTime = mealTime,
        isWholeFoodBased = isWholeFoodBased,
        isProcessed = isProcessed,
        isFermented = isFermented,
        isSaved = isSaved,
        barcode = barcode,
        source = source,
        vitaminA = vitaminA,
        vitaminC = vitaminC,
        vitaminD = vitaminD,
        vitaminB12 = vitaminB12,
        calcium = calcium,
        iron = iron,
        potassium = potassium,
        magnesium = magnesium,
        sodium = sodium,
        zinc = zinc,
        vitaminE = vitaminE,
        vitaminK = vitaminK,
        vitaminB1 = vitaminB1,
        vitaminB2 = vitaminB2,
        vitaminB3 = vitaminB3,
        vitaminB6 = vitaminB6,
        folate = folate,
        biotin = biotin,
        pantothenicAcid = pantothenicAcid,
        phosphorus = phosphorus,
        iodine = iodine,
        selenium = selenium,
        copper = copper,
        manganese = manganese,
        chromium = chromium,
        molybdenum = molybdenum
    )
}
