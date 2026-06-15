package com.dailyhealthcoach.domain.repository

import com.dailyhealthcoach.domain.model.MacroTarget
import kotlinx.coroutines.flow.Flow

interface MacroTargetRepository {
    fun observeActiveTarget(): Flow<MacroTarget?>
    suspend fun saveTarget(proteinMin: Int, proteinMax: Int)
    suspend fun saveFullTarget(
        calories: Int?,
        proteinMin: Int,
        proteinMax: Int,
        carbs: Int?,
        fat: Int?,
        fiber: Int?
    )
}
