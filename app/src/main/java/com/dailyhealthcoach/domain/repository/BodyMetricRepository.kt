package com.dailyhealthcoach.domain.repository

import com.dailyhealthcoach.domain.model.BodyMetricLog
import kotlinx.coroutines.flow.Flow

interface BodyMetricRepository {
    fun observeForDate(date: String): Flow<BodyMetricLog?>
    fun observeAll(): Flow<List<BodyMetricLog>>
}
