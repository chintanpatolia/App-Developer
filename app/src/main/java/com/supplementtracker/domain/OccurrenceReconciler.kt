package com.supplementtracker.domain

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/**
 * Reconciles missing DailyOccurrence records for past dates.
 *
 * Runs at app startup, foreground resume, boot, and before alarm processing.
 * Does NOT overwrite existing occurrences (OnConflictStrategy.IGNORE in the DAO).
 * Does NOT create occurrences for supplement/group assignments that weren't valid
 * on the target date (uses groupAssignedAt to bound historical accuracy).
 *
 * Callers supply last-reconciled-date persistence via lambdas so this object
 * is testable without Android Context.
 */
object OccurrenceReconciler {

    suspend fun reconcile(
        repo: ReconcileDataSource,
        today: LocalDate,
        getLastReconciled: () -> LocalDate?,
        setLastReconciled: (LocalDate) -> Unit
    ) {
        val yesterday = today.minusDays(1)

        val supplements = repo.getActiveSupplementsList()
        if (supplements.isEmpty()) {
            setLastReconciled(yesterday)
            return
        }

        val lastReconciled = getLastReconciled()
        val fromDate: LocalDate = if (lastReconciled != null) {
            lastReconciled.plusDays(1)
        } else {
            // First run: start from the earliest supplement creation date
            supplements.minOf { millisToLocalDate(it.createdAt) }
        }

        // Nothing to reconcile — today is handled by ensureTodayOccurrences separately
        if (!fromDate.isBefore(today)) return

        var date = fromDate
        while (!date.isAfter(yesterday)) {
            val dateStr = date.toString()
            val existing = repo.getOccurrenceSupplementIdsForDate(dateStr)

            val toCreate = supplements.filter { s ->
                if (s.id in existing) return@filter false
                // Only create for dates where current config was known to be valid
                val configValidFrom = maxOf(
                    millisToLocalDate(s.createdAt),
                    millisToLocalDate(s.groupAssignedAt)
                )
                !date.isBefore(configValidFrom)
            }

            if (toCreate.isNotEmpty()) {
                repo.createOccurrencesForDate(toCreate, dateStr)
            }

            date = date.plusDays(1)
        }

        setLastReconciled(yesterday)
    }

    private fun millisToLocalDate(millis: Long): LocalDate =
        Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
}
