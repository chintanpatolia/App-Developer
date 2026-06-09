package com.dailyhealthcoach.ui.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailyhealthcoach.domain.model.BodyMetricLog
import com.dailyhealthcoach.domain.model.DailyHabitLog
import com.dailyhealthcoach.domain.model.FoodEntry
import com.dailyhealthcoach.domain.model.RecoveryScore
import com.dailyhealthcoach.domain.model.Workout
import com.dailyhealthcoach.domain.repository.BodyMetricRepository
import com.dailyhealthcoach.domain.repository.HabitRepository
import com.dailyhealthcoach.domain.repository.NutritionRepository
import com.dailyhealthcoach.domain.repository.RecoveryRepository
import com.dailyhealthcoach.domain.repository.WorkoutRepository
import java.time.LocalDate
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ProgressViewModel(
    private val bodyMetricRepository: BodyMetricRepository,
    private val nutritionRepository: NutritionRepository,
    private val habitRepository: HabitRepository,
    private val workoutRepository: WorkoutRepository,
    private val recoveryRepository: RecoveryRepository,
    private val today: String
) : ViewModel() {

    val uiState: StateFlow<ProgressUiState> = combine(
        bodyMetricRepository.observeAll(),
        nutritionRepository.observeAll(),
        combine(
            habitRepository.observeActiveHabits(),
            habitRepository.observeLogsBetween(
                startDate = LocalDate.parse(today).minusDays(6).toString(),
                endDate = today
            )
        ) { habits, logs -> Pair(habits.size, logs) },
        workoutRepository.observeWorkouts(),
        recoveryRepository.observeAll()
    ) { bodyLogs, foodEntries, (totalHabits, habitLogs), workouts, recoveryScores ->
        ProgressUiState(
            weight = buildBodyTrend("Weight", "lb", bodyLogs) { it.bodyWeight },
            bodyFat = buildBodyTrend("Body Fat", "%", bodyLogs) { it.bodyFatPercentage },
            sleep = buildBodyTrend("Sleep", "h", bodyLogs) { it.sleepHours },
            protein = buildProteinTrend(foodEntries),
            recovery = buildRecoveryTrend(recoveryScores),
            workoutFrequency = buildWorkoutFrequencyTrend(workouts, today),
            habitCompletion = buildHabitCompletionTrend(habitLogs, totalHabits, today)
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ProgressUiState(null, null, null, null, null, null, null)
    )
}

private fun buildBodyTrend(
    label: String,
    unit: String,
    logs: List<BodyMetricLog>,
    selector: (BodyMetricLog) -> Double?
): TrendData? {
    val entries = logs.mapNotNull { log ->
        val v = selector(log) ?: return@mapNotNull null
        Pair(log.date, v)
    }.sortedByDescending { it.first }.take(7)
    if (entries.isEmpty()) return null
    val current = entries.first().second
    val avg = entries.map { it.second }.average()
    val prev = entries.getOrNull(1)?.second
    return TrendData(
        label = label,
        unit = unit,
        currentValue = "%.1f $unit".format(current),
        sevenDayAvg = "avg %.1f $unit".format(avg),
        changeLabel = changeLabel(current, prev),
        changePositive = changePositive(label, current, prev),
        last7 = entries.map { TrendEntry(it.first, "%.1f".format(it.second)) }
    )
}

private fun buildProteinTrend(foodEntries: List<FoodEntry>): TrendData? {
    val byDate = foodEntries.groupBy { it.date }
        .mapValues { (_, entries) -> entries.sumOf { it.proteinGrams ?: 0.0 } }
        .entries
        .sortedByDescending { it.key }
        .take(7)
    if (byDate.isEmpty()) return null
    val current = byDate.first().value
    val avg = byDate.map { it.value }.average()
    val prev = byDate.getOrNull(1)?.value
    return TrendData(
        label = "Protein",
        unit = "g",
        currentValue = "${current.toInt()} g",
        sevenDayAvg = "avg ${avg.toInt()} g",
        changeLabel = changeLabel(current, prev),
        changePositive = changePositive("Protein", current, prev),
        last7 = byDate.map { TrendEntry(it.key, "${it.value.toInt()} g") }
    )
}

private fun buildRecoveryTrend(scores: List<RecoveryScore>): TrendData? {
    val entries = scores.sortedByDescending { it.date }.take(7)
    if (entries.isEmpty()) return null
    val current = entries.first().score.toDouble()
    val avg = entries.map { it.score.toDouble() }.average()
    val prev = entries.getOrNull(1)?.score?.toDouble()
    return TrendData(
        label = "Recovery",
        unit = "/100",
        currentValue = "${entries.first().score}/100",
        sevenDayAvg = "avg ${avg.toInt()}/100",
        changeLabel = changeLabel(current, prev),
        changePositive = changePositive("Recovery", current, prev),
        last7 = entries.map { TrendEntry(it.date, "${it.score}") }
    )
}

private fun buildWorkoutFrequencyTrend(workouts: List<Workout>, today: String): TrendData? {
    if (workouts.isEmpty()) return null
    val todayDate = LocalDate.parse(today)
    val last7Start = todayDate.minusDays(6).toString()
    val prev7Start = todayDate.minusDays(13).toString()
    val prev7End = todayDate.minusDays(7).toString()
    val thisWeekCount = workouts.count { it.date in last7Start..today }
    val prevWeekCount = workouts.count { it.date in prev7Start..prev7End }
    val diff = thisWeekCount - prevWeekCount
    val changeLabel = when {
        diff > 0 -> "+$diff vs prior 7d"
        diff < 0 -> "$diff vs prior 7d"
        else -> "same as prior 7d"
    }
    val last7Dates = (0..6).map { todayDate.minusDays(it.toLong()).toString() }
    val countByDate = workouts.groupBy { it.date }.mapValues { it.value.size }
    return TrendData(
        label = "Workouts",
        unit = "sessions",
        currentValue = "$thisWeekCount in 7d",
        sevenDayAvg = "prev 7d: $prevWeekCount",
        changeLabel = changeLabel,
        changePositive = if (diff == 0) null else diff > 0,
        last7 = last7Dates.map { date -> TrendEntry(date, "${countByDate.getOrDefault(date, 0)}") }
    )
}

private fun buildHabitCompletionTrend(
    habitLogs: List<DailyHabitLog>,
    totalHabits: Int,
    today: String
): TrendData? {
    if (totalHabits == 0) return null
    val todayDate = LocalDate.parse(today)
    val last7Dates = (0..6).map { todayDate.minusDays(it.toLong()).toString() }
    val logsByDate = habitLogs.groupBy { it.date }
    val completionByDate = last7Dates.map { date ->
        val completed = logsByDate[date]?.count { it.status == "COMPLETE" } ?: 0
        Pair(date, if (totalHabits > 0) (completed * 100) / totalHabits else 0)
    }
    val daysWithData = completionByDate.filter { (date, _) -> logsByDate.containsKey(date) }
    if (daysWithData.isEmpty()) return null
    val currentPct = daysWithData.first().second
    val avgPct = daysWithData.map { it.second }.average().toInt()
    val prevPct = daysWithData.getOrNull(1)?.second
    return TrendData(
        label = "Habits",
        unit = "%",
        currentValue = "$currentPct% today",
        sevenDayAvg = "avg $avgPct%",
        changeLabel = if (prevPct != null) changeLabel(currentPct.toDouble(), prevPct.toDouble()) else "–",
        changePositive = if (prevPct != null) changePositive("Habits", currentPct.toDouble(), prevPct.toDouble()) else null,
        last7 = completionByDate.map { TrendEntry(it.first, "${it.second}%") }
    )
}

private fun changeLabel(current: Double, prev: Double?): String {
    if (prev == null) return "–"
    val diff = current - prev
    return when {
        diff > 0 -> "+%.1f".format(diff)
        diff < 0 -> "%.1f".format(diff)
        else -> "no change"
    }
}

private fun changePositive(label: String, current: Double, prev: Double?): Boolean? {
    if (prev == null) return null
    val higherIsBetter = label !in listOf("Body Fat")
    val increased = current > prev
    return if (higherIsBetter) increased else !increased
}
