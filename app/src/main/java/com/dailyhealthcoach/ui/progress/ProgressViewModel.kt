package com.dailyhealthcoach.ui.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailyhealthcoach.domain.model.BodyMetricLog
import com.dailyhealthcoach.domain.model.DailyHabitLog
import com.dailyhealthcoach.domain.model.Exercise
import com.dailyhealthcoach.domain.model.FoodEntry
import com.dailyhealthcoach.domain.model.RecoveryScore
import com.dailyhealthcoach.domain.model.Workout
import com.dailyhealthcoach.domain.model.WorkoutExercise
import com.dailyhealthcoach.domain.model.WorkoutStatus
import com.dailyhealthcoach.domain.repository.BodyMetricRepository
import com.dailyhealthcoach.domain.repository.ExerciseRepository
import com.dailyhealthcoach.domain.repository.HabitRepository
import com.dailyhealthcoach.domain.repository.NutritionRepository
import com.dailyhealthcoach.domain.repository.RecoveryRepository
import com.dailyhealthcoach.domain.repository.WorkoutRepository
import java.time.format.TextStyle
import java.util.Locale
import java.time.LocalDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProgressViewModel(
    private val bodyMetricRepository: BodyMetricRepository,
    private val nutritionRepository: NutritionRepository,
    private val habitRepository: HabitRepository,
    private val workoutRepository: WorkoutRepository,
    private val recoveryRepository: RecoveryRepository,
    private val exerciseRepository: ExerciseRepository,
    private val today: String
) : ViewModel() {

    private val _selectedPeriod = MutableStateFlow(ProgressPeriod.WEEK)
    val selectedPeriod: StateFlow<ProgressPeriod> = _selectedPeriod.asStateFlow()

    fun setSelectedPeriod(period: ProgressPeriod) { _selectedPeriod.value = period }

    private val strengthFlow = combine(
        workoutRepository.observeWorkoutSets(),
        workoutRepository.observeWorkouts(),
        exerciseRepository.observeExercises()
    ) { sets, workouts, exercises -> buildStrengthTrends(sets, workouts, exercises) }
        .catch { emit(emptyList()) }

    private val weeklyLoadFlow = combine(
        workoutRepository.observeWorkouts(),
        workoutRepository.observeWorkoutSets()
    ) { workouts, sets -> buildLatestWeeklyLoad(workouts, sets) }
        .catch { emit(null) }

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<ProgressUiState> = _selectedPeriod.flatMapLatest { period ->
        val startDate = LocalDate.parse(today).minusDays(period.days.toLong()).toString()

        val workoutFlow = workoutRepository.observeWorkouts()
            .map { workouts -> buildWorkoutFrequencyTrend(workouts, startDate, period) }
            .catch { emit(null) }

        val coreFlow = combine(
            bodyMetricRepository.observeAll(),
            nutritionRepository.observeAll(),
            combine(
                habitRepository.observeActiveHabits(),
                habitRepository.observeLogsBetween(startDate = startDate, endDate = today)
            ) { habits, logs -> Pair(habits.size, logs) },
            recoveryRepository.observeAll()
        ) { bodyLogs, foodEntries, (totalHabits, habitLogs), recoveryScores ->
            ProgressUiState(
                weight = buildBodyTrend("Weight", "lb", bodyLogs, startDate, period) { it.bodyWeight },
                bodyFat = buildBodyTrend("Body Fat", "%", bodyLogs, startDate, period) { it.bodyFatPercentage },
                sleep = buildBodyTrend("Sleep", "h", bodyLogs, startDate, period) { it.sleepHours },
                protein = buildProteinTrend(foodEntries, startDate, period),
                steps = buildBodyTrend("Steps", "steps", bodyLogs, startDate, period) { it.stepCount?.toDouble() },
                recovery = buildRecoveryTrend(recoveryScores, startDate, period),
                workoutFrequency = null,
                habitCompletion = buildHabitCompletionTrend(habitLogs, totalHabits, today, period),
                selectedPeriod = period
            )
        }.catch { emit(ProgressUiState(null, null, null, null, null, null, null, null, selectedPeriod = period)) }

        combine(coreFlow, workoutFlow, strengthFlow, weeklyLoadFlow) { mainState, workout, strength, weekLoad ->
            mainState.copy(workoutFrequency = workout, strengthTrends = strength, weeklyLoad = weekLoad)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ProgressUiState(null, null, null, null, null, null, null, null)
    )
}

private fun buildBodyTrend(
    label: String,
    unit: String,
    logs: List<BodyMetricLog>,
    startDate: String,
    period: ProgressPeriod,
    selector: (BodyMetricLog) -> Double?
): TrendData? {
    val entries = logs.mapNotNull { log ->
        val v = selector(log) ?: return@mapNotNull null
        Pair(log.date, v)
    }
    .filter { it.first >= startDate }
    .sortedByDescending { it.first }
    .take(period.displayPoints)
    if (entries.isEmpty()) return null
    val current = entries.first().second
    val avg = entries.map { it.second }.average()
    val prev = entries.getOrNull(1)?.second
    return TrendData(
        label = label,
        unit = unit,
        currentValue = "${"%.1f".format(current)} $unit",
        sevenDayAvg = "avg ${"%.1f".format(avg)} $unit",
        changeLabel = changeLabel(current, prev),
        changePositive = changePositive(label, current, prev),
        last7 = entries.map { TrendEntry(it.first, "%.1f".format(it.second)) },
        points = entries.reversed().map { it.second.toFloat() }
    )
}

private fun buildProteinTrend(
    foodEntries: List<FoodEntry>,
    startDate: String,
    period: ProgressPeriod
): TrendData? {
    val byDate = foodEntries
        .filter { it.date >= startDate }
        .groupBy { it.date }
        .mapValues { (_, entries) -> entries.sumOf { it.proteinGrams ?: 0.0 } }
        .entries
        .sortedByDescending { it.key }
        .take(period.displayPoints)
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
        last7 = byDate.map { TrendEntry(it.key, "${it.value.toInt()} g") },
        points = byDate.reversed().map { it.value.toFloat() }
    )
}

private fun buildRecoveryTrend(
    scores: List<RecoveryScore>,
    startDate: String,
    period: ProgressPeriod
): TrendData? {
    val entries = scores
        .filter { it.date >= startDate }
        .sortedByDescending { it.date }
        .take(period.displayPoints)
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
        last7 = entries.map { TrendEntry(it.date, "${it.score}") },
        points = entries.reversed().map { it.score.toFloat() }
    )
}

private fun buildWorkoutFrequencyTrend(
    workouts: List<Workout>,
    startDate: String,
    period: ProgressPeriod
): TrendData? {
    val completed = workouts.filter {
        it.date >= startDate &&
        (it.status == WorkoutStatus.COMPLETED.storageValue || it.status == WorkoutStatus.PARTIAL.storageValue)
    }
    if (completed.isEmpty()) return null

    val byDate = completed
        .groupBy { it.date }
        .mapValues { (_, sessions) -> sessions.size }
        .entries
        .sortedByDescending { it.key }
        .take(period.displayPoints)

    val totalSessions = byDate.sumOf { it.value }
    val activeDays = byDate.size
    val current = byDate.first().value.toDouble()
    val prev = byDate.getOrNull(1)?.value?.toDouble()
    return TrendData(
        label = "Workouts",
        unit = "sessions",
        currentValue = "$totalSessions session${if (totalSessions != 1) "s" else ""}",
        sevenDayAvg = "$activeDays day${if (activeDays != 1) "s" else ""} logged",
        changeLabel = changeLabel(current, prev),
        changePositive = if (prev == null) null else current > prev,
        last7 = byDate.map { (date, count) -> TrendEntry(date, "$count") },
        points = byDate.reversed().map { it.value.toFloat() }
    )
}

private fun buildHabitCompletionTrend(
    habitLogs: List<DailyHabitLog>,
    totalHabits: Int,
    today: String,
    period: ProgressPeriod
): TrendData? {
    if (totalHabits == 0) return null
    val todayDate = LocalDate.parse(today)
    val logsByDate = habitLogs.groupBy { it.date }
    val daysWithData = logsByDate.keys
        .sortedByDescending { it }
        .take(period.displayPoints)
    if (daysWithData.isEmpty()) return null
    val currentPct = run {
        val logs = logsByDate[today] ?: logsByDate[daysWithData.first()] ?: return@run 0
        logs.count { it.status == "COMPLETE" } * 100 / totalHabits
    }
    val avgPct = daysWithData.map { date ->
        (logsByDate[date]?.count { it.status == "COMPLETE" } ?: 0) * 100 / totalHabits
    }.average().toInt()
    val prevDate = daysWithData.getOrNull(1)
    val prevPct = prevDate?.let { d ->
        (logsByDate[d]?.count { it.status == "COMPLETE" } ?: 0) * 100 / totalHabits
    }
    val trendEntries = daysWithData.map { date ->
        val pct = (logsByDate[date]?.count { it.status == "COMPLETE" } ?: 0) * 100 / totalHabits
        TrendEntry(date, "$pct%")
    }
    return TrendData(
        label = "Habits",
        unit = "%",
        currentValue = "$currentPct% today",
        sevenDayAvg = "avg $avgPct%",
        changeLabel = if (prevPct != null) changeLabel(currentPct.toDouble(), prevPct.toDouble()) else "–",
        changePositive = if (prevPct != null) changePositive("Habits", currentPct.toDouble(), prevPct.toDouble()) else null,
        last7 = trendEntries,
        points = trendEntries.reversed().map { it.displayValue.replace("%", "").toFloatOrNull() ?: 0f }
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

private fun progressEpley1Rm(weight: Double, reps: Int): Double =
    if (reps <= 1) weight else weight * (1 + reps / 30.0)

private val KEY_EXERCISES = listOf("Bench Press", "Squat", "Deadlift", "Overhead Press", "Barbell Row")

private fun buildStrengthTrends(
    sets: List<WorkoutExercise>,
    workouts: List<Workout>,
    exercises: List<Exercise>
): List<TrendData> {
    val exerciseByName = exercises.associateBy { it.name }
    val workoutById = workouts.associateBy { it.id }
    return KEY_EXERCISES.mapNotNull { name ->
        val exercise = exerciseByName[name] ?: return@mapNotNull null
        val setsForEx = sets.filter {
            it.exerciseId == exercise.id && (it.weight ?: 0.0) > 0 && (it.reps ?: 0) > 0
        }
        if (setsForEx.isEmpty()) return@mapNotNull null
        val byWorkout = setsForEx.groupBy { it.workoutId }
        val sessionData = byWorkout.mapNotNull { (workoutId, wSets) ->
            val date = workoutById[workoutId]?.date ?: return@mapNotNull null
            val maxEst1Rm = wSets.maxOf { progressEpley1Rm(it.weight!!, it.reps!!) }
            Pair(date, maxEst1Rm)
        }.sortedBy { it.first }.takeLast(10)
        if (sessionData.isEmpty()) return@mapNotNull null
        val current = sessionData.last().second
        val prev = sessionData.getOrNull(sessionData.size - 2)?.second
        val avg = sessionData.map { it.second }.average()
        TrendData(
            label = "$name · Est. 1RM",
            unit = "lb",
            currentValue = "~${current.toInt()} lb",
            sevenDayAvg = "avg ~${avg.toInt()} lb",
            changeLabel = changeLabel(current, prev),
            changePositive = if (prev == null) null else current > prev,
            last7 = sessionData.map { TrendEntry(it.first, "~${it.second.toInt()} lb") },
            points = sessionData.map { it.second.toFloat() }
        )
    }
}

private fun buildLatestWeeklyLoad(
    workouts: List<Workout>,
    sets: List<WorkoutExercise>
): WeeklyTrainingLoadUiState? {
    val setsByWorkoutId = sets.groupBy { it.workoutId }
    val completed = workouts.filter {
        it.status == WorkoutStatus.COMPLETED.storageValue ||
        it.status == WorkoutStatus.PARTIAL.storageValue
    }
    if (completed.isEmpty()) return null
    val today = LocalDate.now()
    val weekStart = today.with(java.time.DayOfWeek.MONDAY)
    val weekEnd = weekStart.plusDays(6)
    val weekWorkouts = completed.filter {
        try { val d = LocalDate.parse(it.date); d >= weekStart && d <= weekEnd }
        catch (e: Exception) { false }
    }
    if (weekWorkouts.isEmpty()) return null
    val weekSets = weekWorkouts.flatMap { setsByWorkoutId[it.id].orEmpty() }
    val totalVolume = weekSets.sumOf { (it.weight ?: 0.0) * (it.reps ?: 0) }
    val rpeValues = weekSets.mapNotNull { it.rpe }
    val month = weekStart.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    return WeeklyTrainingLoadUiState(
        weekLabel = "Week of $month ${weekStart.dayOfMonth}",
        workoutCount = weekWorkouts.size,
        totalSets = weekSets.size,
        totalVolumeText = if (totalVolume > 0) "%,d lb".format(totalVolume.toLong()) else "–",
        avgRpe = if (rpeValues.isNotEmpty()) "%.1f".format(rpeValues.average()) else "–"
    )
}
