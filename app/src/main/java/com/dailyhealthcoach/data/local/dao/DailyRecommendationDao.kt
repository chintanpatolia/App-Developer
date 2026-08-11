package com.dailyhealthcoach.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dailyhealthcoach.data.local.entity.DailyRecommendationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyRecommendationDao {
    @Query("SELECT * FROM daily_recommendations WHERE date = :date LIMIT 1")
    fun observeForDate(date: String): Flow<DailyRecommendationEntity?>

    @Query("SELECT * FROM daily_recommendations WHERE date = :date LIMIT 1")
    suspend fun getForDate(date: String): DailyRecommendationEntity?

    @Query("SELECT * FROM daily_recommendations ORDER BY date DESC")
    suspend fun getAll(): List<DailyRecommendationEntity>

    @Upsert
    suspend fun upsert(recommendation: DailyRecommendationEntity)
}
