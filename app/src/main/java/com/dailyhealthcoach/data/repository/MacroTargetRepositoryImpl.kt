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
