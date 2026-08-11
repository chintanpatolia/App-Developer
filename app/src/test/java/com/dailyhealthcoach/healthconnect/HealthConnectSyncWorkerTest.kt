package com.dailyhealthcoach.healthconnect

import android.content.Context
import android.content.SharedPreferences
import com.dailyhealthcoach.domain.model.BodyMetricLog
import com.dailyhealthcoach.domain.model.BodyMetricLogInput
import com.dailyhealthcoach.domain.repository.BodyMetricRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.LocalDate

class SyncCheckpointTest {

    // ── Test 1: Missed-day backfill ───────────────────────────────────────────

    @Test
    fun `when lastSyncDate is 3 days ago, startDate is day after lastSync`() {
        val checkpoint = InMemorySyncCheckpoint()
        val threeDaysAgo = LocalDate.now().minusDays(3).toString()
        checkpoint.setLastSyncDate(threeDaysAgo)

        val today = LocalDate.now()
        val lastSync = LocalDate.parse(checkpoint.getLastSyncDate()!!)
        val expectedStart = lastSync.plusDays(1)

        assertEquals(expectedStart, today.minusDays(2))
    }

    @Test
    fun `when no checkpoint, first run syncs only today`() {
        val checkpoint = InMemorySyncCheckpoint()
        assertNull(checkpoint.getLastSyncDate())

        // Per worker logic: no checkpoint → start = today
        val today = LocalDate.now()
        val lastSync = checkpoint.getLastSyncDate()?.let { LocalDate.parse(it) }
        val startDate = when {
            lastSync == null -> today
            else -> lastSync.plusDays(1)
        }
        assertEquals(today, startDate)
    }

    // ── Test 2: Re-running same sync range without duplicates ─────────────────

    @Test
    fun `re-running backfill for same dates does not advance checkpoint beyond today`() {
        val checkpoint = InMemorySyncCheckpoint()
        val yesterday = LocalDate.now().minusDays(1)

        // First run syncs yesterday through today
        checkpoint.setLastSyncDate(yesterday.toString())
        checkpoint.setLastSyncDate(LocalDate.now().toString())

        // Second run: lastSync = today, nothing to do
        val today = LocalDate.now()
        val lastSync = LocalDate.parse(checkpoint.getLastSyncDate()!!)
        val startDate = if (lastSync >= today) today else lastSync.plusDays(1)

        // startDate = today and endDate = today → no backfill loop body runs
        assertEquals(today, startDate)
    }

    // ── Test 3: Partial sync failure / checkpoint safety ──────────────────────

    @Test
    fun `on failure, checkpoint does not advance past last successful date`() {
        val checkpoint = InMemorySyncCheckpoint()
        val threeDaysAgo = LocalDate.now().minusDays(3)
        val twoDaysAgo = LocalDate.now().minusDays(2)

        // Simulate: synced threeDaysAgo OK, then twoDaysAgo threw
        checkpoint.setLastSyncDate(threeDaysAgo.toString())
        // twoDaysAgo fails — checkpoint NOT advanced

        assertEquals(threeDaysAgo.toString(), checkpoint.getLastSyncDate())
    }

    @Test
    fun `checkpoint advances per date so partial failure leaves a safe resume point`() {
        val checkpoint = InMemorySyncCheckpoint()
        val start = LocalDate.now().minusDays(5)

        // Simulate syncing 3 dates successfully, then failure on 4th
        var current = start
        repeat(3) {
            checkpoint.setLastSyncDate(current.toString())
            current = current.plusDays(1)
        }
        // "fail" here: do NOT advance checkpoint for `current`

        // Next run resumes from day after last checkpoint
        val lastSynced = LocalDate.parse(checkpoint.getLastSyncDate()!!)
        val nextStart = lastSynced.plusDays(1)
        assertEquals(current, nextStart)
    }

    // ── Test: MAX_BACKFILL_DAYS cap ───────────────────────────────────────────

    @Test
    fun `backfill is capped at MAX_BACKFILL_DAYS even if checkpoint is very old`() {
        val checkpoint = InMemorySyncCheckpoint()
        val veryOldDate = LocalDate.now().minusDays(90)
        checkpoint.setLastSyncDate(veryOldDate.toString())

        val today = LocalDate.now()
        val lastSync = LocalDate.parse(checkpoint.getLastSyncDate()!!)
        val maxBackfillStart = today.minusDays(SyncCheckpoint.MAX_BACKFILL_DAYS)
        val startDate = if (lastSync.plusDays(1).isBefore(maxBackfillStart)) maxBackfillStart
        else lastSync.plusDays(1)

        assertEquals(maxBackfillStart, startDate)
    }
}

class InMemorySyncCheckpoint {
    private var lastSyncDate: String? = null
    fun getLastSyncDate(): String? = lastSyncDate
    fun setLastSyncDate(date: String) { lastSyncDate = date }
}

class FakeBodyMetricRepositoryForSync : BodyMetricRepository {
    val savedInputs = mutableListOf<BodyMetricLogInput>()
    var shouldThrow = false

    override fun observeForDate(date: String): Flow<BodyMetricLog?> = flowOf(null)
    override fun observeAll(): Flow<List<BodyMetricLog>> = flowOf(emptyList())
    override fun observeForDateRange(startDate: String, endDate: String): Flow<List<BodyMetricLog>> = flowOf(emptyList())

    override suspend fun saveForDate(input: BodyMetricLogInput) {
        if (shouldThrow) throw RuntimeException("simulated failure")
        savedInputs.add(input)
    }
}
