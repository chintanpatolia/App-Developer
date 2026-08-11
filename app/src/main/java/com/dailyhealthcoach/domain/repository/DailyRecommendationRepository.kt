package com.dailyhealthcoach.domain.repository

import com.dailyhealthcoach.domain.model.DailyRecommendation
import kotlinx.coroutines.flow.Flow

interface DailyRecommendationRepository {
    fun observeForDate(date: String): Flow<DailyRecommendation?>
    suspend fun saveForDate(recommendation: DailyRecommendation)
}
