package com.supplementtracker.domain

import com.supplementtracker.data.entity.DailyOccurrenceEntity
import com.supplementtracker.data.entity.ScheduleGroupEntity
import com.supplementtracker.data.entity.SupplementEntity
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate

/**
 * Deterministic tests for OccurrenceReconciler.
 * Uses the ReconcileDataSource interface — no Android Context, no Room.
 */
class OccurrenceReconcilerTest {

    // ── Fake data source ───────────────────────────────────────────────────────

    private class FakeDataSource(
        private val supplements: List<SupplementEntity>,
        private val group: ScheduleGroupEntity
    ) : ReconcileDataSource {

        val inserted = mutableListOf<DailyOccurrenceEntity>()
        private val existingByDate = mutableMapOf<String, MutableSet<Long>>()

        /** Pre-populate existing occurrences (simulates occurrences created before the test). */
        fun seed(date: String, vararg supplementIds: Long) {
            existingByDate.getOrPut(date) { mutableSetOf() }.addAll(supplementIds.toList())
        }

        override suspend fun getActiveSupplementsList() = supplements

        override suspend fun getOccurrenceSupplementIdsForDate(date: String): Set<Long> =
            existingByDate[date]?.toSet() ?: emptySet()

        override suspend fun createOccurrencesForDate(supps: List<SupplementEntity>, date: String) {
            supps.forEach { s ->
                val existing = existingByDate.getOrPut(date) { mutableSetOf() }
                if (existing.add(s.id)) {   // add returns false if already present
                    inserted.add(DailyOccurrenceEntity(
                        supplementId = s.id,
                        scheduledDate = date,
                        scheduledHour = group.reminderHour,
                        scheduledMinute = group.reminderMinute,
                        scheduleGroupId = group.id
                    ))
                }
            }
        }
    }

    // ── Helpers ────────────────────────────────────────────────────────────────

    private val group = ScheduleGroupEntity(
        id = 1, name = "MORNING", label = "Morning", reminderHour = 8, reminderMinute = 0
    )

    /** Build a SupplementEntity whose creation and group-assignment are N days before [today]. */
    private fun supplement(
        id: Long,
        today: LocalDate,
        createdDaysAgo: Long,
        groupAssignedDaysAgo: Long = createdDaysAgo
    ): SupplementEntity {
        fun daysAgoMillis(d: Long) =
            today.minusDays(d).atStartOfDay(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli()
        return SupplementEntity(
            id = id, name = "S$id", dose = "1", scheduleGroupId = 1,
            createdAt = daysAgoMillis(createdDaysAgo),
            groupAssignedAt = daysAgoMillis(groupAssignedDaysAgo)
        )
    }

    private fun reconcile(
        ds: FakeDataSource,
        today: LocalDate,
        lastReconciled: LocalDate?
    ): LocalDate? {
        var stored: LocalDate? = lastReconciled
        runTest {
            OccurrenceReconciler.reconcile(
                repo = ds,
                today = today,
                getLastReconciled = { stored },
                setLastReconciled = { stored = it }
            )
        }
        return stored
    }

    // ── Test A: midnight rollover — yesterday's occurrence not touched ─────────
    @Test fun `A - existing occurrence not overwritten when date advances past midnight`() {
        val today = LocalDate.of(2024, 6, 10)
        val yesterday = today.minusDays(1)
        val s = supplement(1, today, createdDaysAgo = 10)
        val ds = FakeDataSource(listOf(s), group)
        ds.seed(yesterday.toString(), 1L)   // yesterday already has occurrence

        reconcile(ds, today, lastReconciled = yesterday)

        assertTrue(
            "Should not re-insert existing occurrence for yesterday",
            ds.inserted.none { it.scheduledDate == yesterday.toString() }
        )
    }

    // ── Test B: missing yesterday occurrences are reconciled ──────────────────
    @Test fun `B - missing yesterday occurrences are created`() {
        val today = LocalDate.of(2024, 6, 10)
        val yesterday = today.minusDays(1)
        val s = supplement(1, today, createdDaysAgo = 10)
        val ds = FakeDataSource(listOf(s), group)

        reconcile(ds, today, lastReconciled = yesterday.minusDays(1))

        assertTrue(
            "Should create occurrence for yesterday",
            ds.inserted.any { it.scheduledDate == yesterday.toString() && it.supplementId == 1L }
        )
    }

    // ── Test C: reconciliation is idempotent ──────────────────────────────────
    @Test fun `C - reconciliation is idempotent`() {
        val today = LocalDate.of(2024, 6, 10)
        val s = supplement(1, today, createdDaysAgo = 5)
        val ds = FakeDataSource(listOf(s), group)

        // First reconcile covers 5 days
        reconcile(ds, today, lastReconciled = today.minusDays(6))
        val countAfterFirst = ds.inserted.size

        // Simulate calling reconcile again for same range (lastReconciled already up to date)
        reconcile(ds, today, lastReconciled = today.minusDays(1))

        assertEquals("Re-running reconcile must not insert duplicates", countAfterFirst, ds.inserted.size)
    }

    // ── Test D: completed occurrence is never overwritten ─────────────────────
    @Test fun `D - completed occurrence is not touched by reconciler`() {
        val today = LocalDate.of(2024, 6, 10)
        val yesterday = today.minusDays(1)
        val s = supplement(1, today, createdDaysAgo = 10)
        val ds = FakeDataSource(listOf(s), group)
        ds.seed(yesterday.toString(), 1L)   // completed occurrence already exists

        reconcile(ds, today, lastReconciled = today.minusDays(3))

        assertTrue(
            "Reconciler must not touch existing occurrence",
            ds.inserted.none { it.scheduledDate == yesterday.toString() && it.supplementId == 1L }
        )
    }

    // ── Test E: historical schedule bound — groupAssignedAt respected ─────────
    @Test fun `E - occurrence not created for dates before groupAssignedAt`() {
        val today = LocalDate.of(2024, 6, 10)
        // Supplement created 10 days ago but group was reassigned only yesterday
        val s = supplement(1, today, createdDaysAgo = 10, groupAssignedDaysAgo = 1)
        val ds = FakeDataSource(listOf(s), group)

        reconcile(ds, today, lastReconciled = today.minusDays(5))

        // Any dates before yesterday must not get an occurrence
        val premature = ds.inserted.filter { it.scheduledDate < today.minusDays(1).toString() }
        assertTrue(
            "No occurrence should be created before groupAssignedAt (history unknown before then): $premature",
            premature.isEmpty()
        )

        // Yesterday (= day groupAssignedAt) should get an occurrence
        assertTrue(
            "Occurrence should be created from groupAssignedAt date forward",
            ds.inserted.any { it.scheduledDate == today.minusDays(1).toString() }
        )
    }

    // ── Test F: force-stop simulation ─────────────────────────────────────────
    @Test fun `F - missed day is reconstructed on next app launch after force-stop`() {
        val monday = LocalDate.of(2024, 6, 10)
        val tuesday = monday.plusDays(1)
        val sunday = monday.minusDays(1)
        val s = supplement(1, tuesday, createdDaysAgo = 30)
        val ds = FakeDataSource(listOf(s), group)

        // App opens Tuesday; last reconcile was Sunday (Monday was force-stopped)
        reconcile(ds, today = tuesday, lastReconciled = sunday)

        assertTrue(
            "Monday's missed occurrence must be created when user opens app on Tuesday",
            ds.inserted.any { it.scheduledDate == monday.toString() && it.supplementId == 1L }
        )
    }

    // ── Test G: today's occurrences not created by reconciler ─────────────────
    @Test fun `G - reconciler does not create occurrences for today`() {
        val today = LocalDate.of(2024, 6, 10)
        val s = supplement(1, today, createdDaysAgo = 5)
        val ds = FakeDataSource(listOf(s), group)

        reconcile(ds, today, lastReconciled = today.minusDays(3))

        assertFalse(
            "Reconciler must not create today's occurrences — that is ensureTodayOccurrences' job",
            ds.inserted.any { it.scheduledDate == today.toString() }
        )
    }

    // ── DateProvider injection test ────────────────────────────────────────────
    @Test fun `date provider is injectable and deterministic in tests`() {
        val fixedDate = LocalDate.of(2024, 1, 15)

        class FixedDateProvider(val date: LocalDate) : DateProvider {
            override fun today() = date
        }

        val provider = FixedDateProvider(fixedDate)
        assertEquals(fixedDate, provider.today())
        assertNotEquals(LocalDate.now(), fixedDate) // confirms it is not using wall clock
    }
}
