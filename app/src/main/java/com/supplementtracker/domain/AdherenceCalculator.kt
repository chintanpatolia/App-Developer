package com.supplementtracker.domain

import com.supplementtracker.data.entity.DailyOccurrenceEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class DayStats(
    val date: String,
    val scheduled: Int,
    val completed: Int
) {
    val percentage: Float get() = if (scheduled == 0) 0f else completed.toFloat() / scheduled * 100f
    val isPerfect: Boolean get() = scheduled > 0 && completed == scheduled
}

data class AdherenceSummary(
    val adherencePercent: Float,
    val currentStreak: Int,
    val perfectDays: Int,
    val totalDays: Int,
    val dayStats: List<DayStats>
)

data class SupplementAdherence(
    val supplementId: Long,
    val supplementName: String,
    val scheduled: Int,
    val completed: Int
) {
    val percent: Float get() = if (scheduled == 0) 0f else completed.toFloat() / scheduled * 100f
}

object AdherenceCalculator {

    fun computeDayStats(occurrences: List<DailyOccurrenceEntity>): List<DayStats> {
        return occurrences
            .groupBy { it.scheduledDate }
            .map { (date, occs) ->
                DayStats(
                    date = date,
                    scheduled = occs.size,
                    completed = occs.count { it.completed }
                )
            }
            .sortedBy { it.date }
    }

    fun computeSummary(dayStats: List<DayStats>, today: String): AdherenceSummary {
        val days = dayStats.filter { it.scheduled > 0 }
        val totalDays = days.size
        val adherence = if (totalDays == 0) 0f
        else days.sumOf { it.percentage.toDouble() }.toFloat() / totalDays
        val perfectDays = days.count { it.isPerfect }
        val streak = computeStreak(days, today)
        return AdherenceSummary(adherence, streak, perfectDays, totalDays, days)
    }

    fun computeStreak(dayStats: List<DayStats>, today: String): Int {
        val perfect = dayStats.filter { it.isPerfect }.map { it.date }.toSet()
        var count = 0
        var current = LocalDate.parse(today)
        while (perfect.contains(current.toString())) {
            count++
            current = current.minusDays(1)
        }
        return count
    }

    fun computePerSupplementAdherence(
        occurrences: List<DailyOccurrenceEntity>,
        supplementNames: Map<Long, String>
    ): List<SupplementAdherence> {
        return occurrences
            .groupBy { it.supplementId }
            .map { (supId, occs) ->
                SupplementAdherence(
                    supplementId = supId,
                    supplementName = supplementNames[supId] ?: "Unknown",
                    scheduled = occs.size,
                    completed = occs.count { it.completed }
                )
            }
            .sortedByDescending { it.percent }
    }
}
