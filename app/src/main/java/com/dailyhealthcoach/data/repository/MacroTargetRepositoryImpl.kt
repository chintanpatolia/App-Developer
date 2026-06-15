package com.dailyhealthcoach.data.repository

import com.dailyhealthcoach.data.local.dao.MacroTargetDao
import com.dailyhealthcoach.data.local.entity.MacroTargetEntity
import com.dailyhealthcoach.domain.model.MacroTarget
import com.dailyhealthcoach.domain.repository.MacroTargetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MacroTargetRepositoryImpl(
    private val macroTargetDao: MacroTargetDao
) : MacroTargetRepository {
    override fun observeActiveTarget(): Flow<MacroTarget?> {
        return macroTargetDao.observeActiveTarget()
            .map { it?.toDomain() }
    }

    override suspend fun saveTarget(proteinMin: Int, proteinMax: Int) {
        val now = java.time.Instant.now().toString()
        val existing = macroTargetDao.getActiveTarget()
        macroTargetDao.upsert(
            MacroTargetEntity(
                id = existing?.id ?: 0,
                proteinMinGrams = proteinMin,
                proteinMaxGrams = proteinMax,
                calorieTarget = existing?.calorieTarget,
                carbTargetGrams = existing?.carbTargetGrams,
                fatTargetGrams = existing?.fatTargetGrams,
                fiberTargetGrams = existing?.fiberTargetGrams,
                isActive = true,
                createdAt = existing?.createdAt ?: now,
                updatedAt = now
            )
        )
    }

    override suspend fun saveFullTarget(
        calories: Int?,
        proteinMin: Int,
        proteinMax: Int,
        carbs: Int?,
        fat: Int?,
        fiber: Int?
    ) {
        val now = java.time.Instant.now().toString()
        val existing = macroTargetDao.getActiveTarget()
        macroTargetDao.upsert(
            MacroTargetEntity(
                id = existing?.id ?: 0,
                proteinMinGrams = proteinMin,
                proteinMaxGrams = proteinMax,
                calorieTarget = calories,
                carbTargetGrams = carbs,
                fatTargetGrams = fat,
                fiberTargetGrams = fiber,
                isActive = true,
                createdAt = existing?.createdAt ?: now,
                updatedAt = now
            )
        )
    }
}

private fun MacroTargetEntity.toDomain(): MacroTarget {
    return MacroTarget(
        id = id,
        proteinMinGrams = proteinMinGrams,
        proteinMaxGrams = proteinMaxGrams,
        calorieTarget = calorieTarget,
        carbTargetGrams = carbTargetGrams,
        fatTargetGrams = fatTargetGrams,
        fiberTargetGrams = fiberTargetGrams
    )
}
