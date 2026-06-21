package com.dailyhealthcoach.data.repository

import com.dailyhealthcoach.data.local.dao.PlannedMealDao
import com.dailyhealthcoach.data.local.entity.PlannedMealEntity
import com.dailyhealthcoach.domain.model.PlannedMeal
import com.dailyhealthcoach.domain.repository.PlannedMealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlannedMealRepositoryImpl(
    private val dao: PlannedMealDao
) : PlannedMealRepository {

    override fun observeForDate(date: String): Flow<List<PlannedMeal>> =
        dao.observeForDate(date).map { list -> list.map { it.toDomain() } }

    override suspend fun replaceForDates(dates: List<String>, meals: List<PlannedMeal>) {
        dao.deleteForDates(dates)
        dao.insertAll(meals.map { it.toEntity() })
    }
}

private fun PlannedMealEntity.toDomain() = PlannedMeal(
    id = id,
    date = date,
    slotKey = slotKey,
    recipeId = recipeId,
    recipeName = recipeName,
    calories = calories,
    proteinGrams = proteinGrams,
    carbGrams = carbGrams,
    fatGrams = fatGrams,
    fiberGrams = fiberGrams
)

private fun PlannedMeal.toEntity() = PlannedMealEntity(
    id = id,
    date = date,
    slotKey = slotKey,
    recipeId = recipeId,
    recipeName = recipeName,
    calories = calories,
    proteinGrams = proteinGrams,
    carbGrams = carbGrams,
    fatGrams = fatGrams,
    fiberGrams = fiberGrams
)
