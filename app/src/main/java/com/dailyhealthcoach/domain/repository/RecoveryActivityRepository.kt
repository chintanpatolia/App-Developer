package com.dailyhealthcoach.domain.repository

import com.dailyhealthcoach.domain.model.RecoveryActivity
import kotlinx.coroutines.flow.Flow

interface RecoveryActivityRepository {
    fun observeAll(): Flow<List<RecoveryActivity>>
    suspend fun saveAll(workoutId: Long, activities: List<RecoveryActivity>)
}
