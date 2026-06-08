package com.dailyhealthcoach.data.repository

import com.dailyhealthcoach.data.local.dao.DailyRecommendationDao
import com.dailyhealthcoach.data.local.entity.DailyRecommendationEntity
import com.dailyhealthcoach.domain.model.DailyRecommendation
import com.dailyhealthcoach.domain.repository.DailyRecommendationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DailyRecommendationRepositoryImpl(
    private val dailyRecommendationDao: DailyRecommendationDao
) : DailyRecommendationRepository {
    override fun observeForDate(date: String): Flow<DailyRecommendation?> {
        return dailyRecommendationDao.observeForDate(date).map { it?.toDomain() }
    }
}

private fun DailyRecommendationEntity.toDomain(): DailyRecommendation {
    return DailyRecommendation(
        id = id,
        date = date,
        recommendationType = recommendationType,
        targetMuscleGroups = targetMuscleGroups,
        intensity = intensity,
        loadGuidance = loadGuidance,
        reasonSummary = reasonSummary
    )
}
