package com.dailyhealthcoach.domain.repository

import com.dailyhealthcoach.domain.model.RecoveryScore
import kotlinx.coroutines.flow.Flow

interface RecoveryRepository {
    fun observeForDate(date: String): Flow<RecoveryScore?>
    fun observeAll(): Flow<List<RecoveryScore>>
    suspend fun saveForDate(date: String, score: Int, label: String, reasons: List<String>)
}
