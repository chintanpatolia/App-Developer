package com.supplementtracker.domain

import com.supplementtracker.data.entity.SupplementEntity

/** Narrow interface for the three operations OccurrenceReconciler needs from persistence. */
interface ReconcileDataSource {
    suspend fun getActiveSupplementsList(): List<SupplementEntity>
    suspend fun getOccurrenceSupplementIdsForDate(date: String): Set<Long>
    suspend fun createOccurrencesForDate(supplements: List<SupplementEntity>, date: String)
}
