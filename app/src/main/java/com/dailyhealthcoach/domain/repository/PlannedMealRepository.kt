package com.dailyhealthcoach.domain.repository

import com.dailyhealthcoach.domain.model.PlannedMeal
import kotlinx.coroutines.flow.Flow

interface PlannedMealRepository {
    fun observeForDate(date: String): Flow<List<PlannedMeal>>
    suspend fun replaceForDates(dates: List<String>, meals: List<PlannedMeal>)
}
