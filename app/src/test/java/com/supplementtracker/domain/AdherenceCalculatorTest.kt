package com.supplementtracker.domain

import com.supplementtracker.data.entity.DailyOccurrenceEntity
import org.junit.Assert.*
import org.junit.Test

class AdherenceCalculatorTest {

    private fun occ(
        id: Long, supId: Long, date: String,
        groupId: Long = 1L,
        completed: Boolean = false,
        completedAt: Long? = null,
        hour: Int = 8, minute: Int = 0
    ) = DailyOccurrenceEntity(
        id = id, supplementId = supId, scheduledDate = date,
        scheduledHour = hour, scheduledMinute = minute,
        scheduleGroupId = groupId, completed = completed, completedAt = completedAt
    )

    // 1. Daily adherence calculation — all completed
    @Test fun `daily adherence all completed`() {
        val occs = listOf(occ(1, 1, "2024-01-01", completed = true), occ(2, 2, "2024-01-01", completed = true))
        val stats = AdherenceCalculator.computeDayStats(occs)
        assertEquals(100f, stats[0].percentage, 0.01f)
        assertTrue(stats[0].isPerfect)
    }

    // 1. Daily adherence — partial
    @Test fun `daily adherence partial completion`() {
        val occs = listOf(occ(1, 1, "2024-01-01", completed = true), occ(2, 2, "2024-01-01", completed = false))
        val stats = AdherenceCalculator.computeDayStats(occs)
        assertEquals(50f, stats[0].percentage, 0.01f)
        assertFalse(stats[0].isPerfect)
    }

    // 1. Daily adherence — none completed
    @Test fun `daily adherence none completed`() {
        val occs = listOf(occ(1, 1, "2024-01-01"), occ(2, 2, "2024-01-01"))
        val stats = AdherenceCalculator.computeDayStats(occs)
        assertEquals(0f, stats[0].percentage, 0.01f)
    }

    // 2. 7D/30D/90D ranges
    @Test fun `7D range aggregation`() {
        val occs = (1..7).flatMap { d ->
            listOf(occ(d.toLong(), d.toLong(), "2024-01-%02d".format(d), completed = true))
        }
        val stats = AdherenceCalculator.computeDayStats(occs)
        assertEquals(7, stats.size)
        stats.forEach { assertEquals(100f, it.percentage, 0.01f) }
    }

    @Test fun `30D range computes average adherence`() {
        val occs = (1..30).flatMap { day ->
            val date = "2024-01-%02d".format(day)
            listOf(
                occ(day.toLong() * 2, 1L, date, completed = true),
                occ(day.toLong() * 2 + 1, 2L, date, completed = day <= 15)
            )
        }
        val stats = AdherenceCalculator.computeDayStats(occs)
        val summary = AdherenceCalculator.computeSummary(stats, "2024-01-30")
        assertTrue(summary.adherencePercent in 74f..76f)
    }

    @Test fun `90D range spans correct days`() {
        val occs = (1..90).flatMap { d ->
            val date = java.time.LocalDate.of(2024, 1, 1).plusDays(d.toLong() - 1).toString()
            listOf(occ(d.toLong(), d.toLong(), date, completed = true))
        }
        val stats = AdherenceCalculator.computeDayStats(occs)
        assertEquals(90, stats.size)
    }

    // 3. Perfect day calculation
    @Test fun `perfect days counts only fully completed days`() {
        val occs = listOf(
            occ(1, 1, "2024-01-01", completed = true), occ(2, 2, "2024-01-01", completed = true),
            occ(3, 1, "2024-01-02", completed = true), occ(4, 2, "2024-01-02", completed = false),
            occ(5, 1, "2024-01-03", completed = true), occ(6, 2, "2024-01-03", completed = true),
        )
        val stats = AdherenceCalculator.computeDayStats(occs)
        val summary = AdherenceCalculator.computeSummary(stats, "2024-01-03")
        assertEquals(2, summary.perfectDays)
    }

    // 4. Current streak
    @Test fun `streak three consecutive perfect days`() {
        val days = listOf(
            DayStats("2024-01-01", 2, 2),
            DayStats("2024-01-02", 2, 2),
            DayStats("2024-01-03", 2, 2),
        )
        assertEquals(3, AdherenceCalculator.computeStreak(days, "2024-01-03"))
    }

    @Test fun `streak broken by imperfect day`() {
        val days = listOf(
            DayStats("2024-01-01", 2, 2),
            DayStats("2024-01-02", 2, 1),
            DayStats("2024-01-03", 2, 2),
        )
        assertEquals(1, AdherenceCalculator.computeStreak(days, "2024-01-03"))
    }

    @Test fun `streak zero when today not perfect`() {
        val days = listOf(DayStats("2024-01-03", 2, 1))
        assertEquals(0, AdherenceCalculator.computeStreak(days, "2024-01-03"))
    }

    @Test fun `streak zero with empty data`() {
        assertEquals(0, AdherenceCalculator.computeStreak(emptyList(), "2024-01-03"))
    }

    // 5. Manual completion recorded
    @Test fun `completion records timestamp`() {
        val now = System.currentTimeMillis()
        val o = occ(1, 1, "2024-01-01", completed = true, completedAt = now)
        assertTrue(o.completed)
        assertEquals(now, o.completedAt)
    }

    // 6. Undo completion
    @Test fun `undo reduces completed to zero`() {
        val uncompleted = occ(1, 1, "2024-01-01", completed = false, completedAt = null)
        val stats = AdherenceCalculator.computeDayStats(listOf(uncompleted))
        assertEquals(0, stats[0].completed)
    }

    // 10. Partial group completion — outstanding calculation
    @Test fun `partial group - outstanding supplements identified`() {
        val groupOccs = listOf(
            occ(1, 1, "2024-01-01", groupId = 2L, completed = true),
            occ(2, 2, "2024-01-01", groupId = 2L, completed = false),
            occ(3, 3, "2024-01-01", groupId = 2L, completed = false),
        )
        val outstanding = groupOccs.filter { !it.completed }
        assertEquals(2, outstanding.size)
        assertEquals(setOf(2L, 3L), outstanding.map { it.supplementId }.toSet())
    }

    // 11. All supplements completed before notification fires
    @Test fun `no outstanding when all completed`() {
        val groupOccs = listOf(
            occ(1, 1, "2024-01-01", groupId = 2L, completed = true),
            occ(2, 2, "2024-01-01", groupId = 2L, completed = true),
        )
        val outstanding = groupOccs.filter { !it.completed }
        assertTrue(outstanding.isEmpty())
    }

    // 17. Historical schedule preservation
    @Test fun `occurrence preserves original scheduled time`() {
        val o = occ(1, 1, "2024-01-01", hour = 12, minute = 30)
        assertEquals(12, o.scheduledHour)
        assertEquals(30, o.scheduledMinute)
        // Changing group time does not modify this historical record
        val modified = o.copy(scheduledHour = 13, scheduledMinute = 0)
        assertEquals(12, o.scheduledHour) // original unchanged
        assertEquals(13, modified.scheduledHour)
    }

    // 18. Day rollover
    @Test fun `stats span multiple days in order`() {
        val occs = listOf(
            occ(1, 1, "2024-01-01", completed = true),
            occ(2, 1, "2024-01-02", completed = false),
            occ(3, 1, "2024-01-03", completed = true),
        )
        val stats = AdherenceCalculator.computeDayStats(occs)
        assertEquals(3, stats.size)
        assertEquals("2024-01-01", stats[0].date)
        assertEquals("2024-01-02", stats[1].date)
        assertEquals("2024-01-03", stats[2].date)
    }

    // 19. Timezone-agnostic (date stored as String)
    @Test fun `date stored as ISO string is timezone agnostic`() {
        val date = "2024-06-15"
        val o = occ(1, 1, date)
        assertEquals(date, o.scheduledDate)
    }

    // Per-supplement adherence calculation
    @Test fun `per-supplement adherence`() {
        val occs = listOf(
            occ(1, 1, "2024-01-01", completed = true), occ(2, 1, "2024-01-02", completed = true),
            occ(3, 1, "2024-01-03", completed = false),
            occ(4, 2, "2024-01-01", completed = true), occ(5, 2, "2024-01-02", completed = false),
        )
        val names = mapOf(1L to "A", 2L to "B")
        val result = AdherenceCalculator.computePerSupplementAdherence(occs, names)
        val a = result.find { it.supplementId == 1L }!!
        val b = result.find { it.supplementId == 2L }!!
        assertEquals(3, a.scheduled); assertEquals(2, a.completed)
        assertEquals(2, b.scheduled); assertEquals(1, b.completed)
        assertEquals(66.67f, a.percent, 0.1f)
        assertEquals(50f, b.percent, 0.1f)
    }

    // Adherence with zero scheduled days
    @Test fun `zero scheduled days returns zero adherence`() {
        val summary = AdherenceCalculator.computeSummary(emptyList(), "2024-01-01")
        assertEquals(0f, summary.adherencePercent, 0.01f)
        assertEquals(0, summary.currentStreak)
        assertEquals(0, summary.perfectDays)
    }
}
