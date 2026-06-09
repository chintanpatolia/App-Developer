package com.dailyhealthcoach.data.repository

import com.dailyhealthcoach.data.local.dao.DailyRecommendationDao
import com.dailyhealthcoach.data.local.entity.DailyRecommendationEntity
import com.dailyhealthcoach.domain.model.DailyRecommendation
import com.dailyhealthcoach.domain.repository.DailyRecommendationRepository
import java.time.Instant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DailyRecommendationRepositoryImpl(
    private val dailyRecommendationDao: DailyRecommendationDao
) : DailyRecommendationRepository {
    override fun observeForDate(date: String): Flow<DailyRecommendation?> {
        return dailyRecommendationDao.observeForDate(date).map { it?.toDomain() }
    }

    override suspend fun saveForDate(recommendation: DailyRecommendation) {
        val now = Instant.now().toString()
        val existing = dailyRecommendationDao.getForDate(recommendation.date)
        dailyRecommendationDao.upsert(
            DailyRecommendationEntity(
                id = existing?.id ?: 0,
                date = recommendation.date,
                recommendationType = recommendation.recommendationType,
                title = recommendation.title,
                explanation = recommendation.explanation,
                suggestedFocus = recommendation.suggestedFocus,
                reasonBullets = recommendation.reasonBullets.joinToString("\n"),
                targetMuscleGroups = recommendation.targetMuscleGroups,
                intensity = recommendation.intensity,
                loadGuidance = recommendation.loadGuidance,
                reasonSummary = recommendation.reasonSummary,
                createdAt = existing?.createdAt ?: now,
                updatedAt = now
            )
        )
    }
}

private fun DailyRecommendationEntity.toDomain(): DailyRecommendation {
    return DailyRecommendation(
        id = id,
        date = date,
        recommendationType = recommendationType,
        title = title.ifBlank { recommendationType },
        explanation = explanation,
        suggestedFocus = suggestedFocus,
        reasonBullets = reasonBullets?.lines()?.filter { it.isNotBlank() }.orEmpty(),
        targetMuscleGroups = targetMuscleGroups,
        intensity = intensity,
        loadGuidance = loadGuidance,
        reasonSummary = reasonSummary
    )
}
