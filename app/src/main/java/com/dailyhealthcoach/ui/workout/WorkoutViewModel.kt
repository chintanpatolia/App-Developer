package com.dailyhealthcoach.ui.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dailyhealthcoach.domain.model.DailyRecommendation
import com.dailyhealthcoach.domain.model.Exercise
import com.dailyhealthcoach.domain.model.RecoveryActivity
import com.dailyhealthcoach.domain.model.Workout
import com.dailyhealthcoach.domain.model.WorkoutExercise
import com.dailyhealthcoach.domain.model.WorkoutPlan
import com.dailyhealthcoach.domain.model.WorkoutSetInput
import com.dailyhealthcoach.domain.model.WorkoutStatus
import com.dailyhealthcoach.domain.repository.DailyRecommendationRepository
import com.dailyhealthcoach.domain.repository.ExerciseRepository
import com.dailyhealthcoach.domain.repository.RecoveryActivityRepository
import com.dailyhealthcoach.domain.repository.UserProfileRepository
import com.dailyhealthcoach.domain.repository.WorkoutRepository
import com.dailyhealthcoach.domain.usecase.GenerateWorkoutPlanUseCase
import com.dailyhealthcoach.domain.usecase.HabitAutoUpdateUseCase
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkoutViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val workoutRepository: WorkoutRepository,
    private val dailyRecommendationRepository: DailyRecommendationRepository,
    private val recoveryActivityRepository: RecoveryActivityRepository,
    private val generateWorkoutPlanUseCase: GenerateWorkoutPlanUseCase,
    private val habitAutoUpdateUseCase: HabitAutoUpdateUseCase,
    private val userProfileRepository: UserProfileRepository,
    private val today: String
) : ViewModel() {
    private val draftState = MutableStateFlow(WorkoutDraftState())
    private val _pendingPrAchievements = MutableStateFlow<List<String>>(emptyList())

    private val rawWorkoutSets = workoutRepository.observeWorkoutSets()
        .stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = emptyList())

    private val rawExercises = exerciseRepository.observeExercises()
        .stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = emptyList())

    val uiState: StateFlow<WorkoutUiState> = combine(
        exerciseRepository.observeExercises(),
        workoutRepository.observeWorkouts(),
        workoutRepository.observeWorkoutSets(),
        draftState,
        dailyRecommendationRepository.observeForDate(today)
            .combine(userProfileRepository.observeUserProfile()) { rec, profile ->
                rec to (profile?.workoutGoals?.takeIf { it.isNotEmpty() } ?: listOf("General Fitness"))
            }
    ) { exercises, workouts, workoutSets, draft, recAndGoal ->
        buildUiState(exercises, workouts, workoutSets, draft, recAndGoal.first, recAndGoal.second, generateWorkoutPlanUseCase, today)
    }.combine(recoveryActivityRepository.observeAll()) { state, allLogs ->
        mergeRecoveryLogs(state, allLogs)
    }.combine(_pendingPrAchievements) { state, prs ->
        if (prs.isEmpty()) state else state.copy(newPrAchievements = prs)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = WorkoutUiState()
    )

    fun startWorkout() {
        draftState.update { it.copy(isWorkoutStarted = true) }
    }

    fun closeActiveWorkout() {
        draftState.value = WorkoutDraftState()
    }

    fun updateWorkoutName(value: String) {
        draftState.update { it.copy(workoutName = value) }
    }

    fun updateDuration(value: String) {
        draftState.update { it.copy(durationMinutes = value.filter { char -> char.isDigit() }) }
    }

    fun updateOverallRpe(value: String) {
        val filtered = value.filter { it.isDigit() }.take(2)
        val rpe = filtered.toIntOrNull()
        val error = when {
            filtered.isEmpty() -> null
            rpe == null || rpe < 1 || rpe > 10 -> "RPE must be between 1 and 10."
            else -> null
        }
        draftState.update { it.copy(overallRpe = filtered, overallRpeError = error) }
    }

    fun updateWorkoutNotes(value: String) {
        draftState.update { it.copy(workoutNotes = value) }
    }

    fun selectStatus(status: WorkoutStatus) {
        draftState.update { it.copy(selectedStatus = status) }
    }

    fun addExerciseToWorkout(exerciseId: Long) {
        draftState.update { draft ->
            if (draft.selectedExercises.any { it.exerciseId == exerciseId }) {
                draft.copy(
                    selectedExercises = draft.selectedExercises.map {
                        it.copy(isExpanded = it.exerciseId == exerciseId)
                    }
                )
            } else {
                draft.copy(
                    selectedExercises = draft.selectedExercises.map { it.copy(isExpanded = false) } +
                        DraftExerciseState(exerciseId = exerciseId, isExpanded = true)
                )
            }
        }
    }

    fun toggleExerciseExpanded(exerciseId: Long) {
        draftState.update { draft ->
            val shouldExpand = draft.selectedExercises
                .firstOrNull { it.exerciseId == exerciseId }
                ?.isExpanded == false

            draft.copy(
                selectedExercises = draft.selectedExercises.map {
                    when {
                        it.exerciseId == exerciseId -> it.copy(isExpanded = shouldExpand)
                        shouldExpand -> it.copy(isExpanded = false)
                        else -> it
                    }
                }
            )
        }
    }

    fun updateSetReps(exerciseId: Long, value: String) {
        updateExercise(exerciseId) { it.copy(setReps = value.filter { char -> char.isDigit() }) }
    }

    fun updateSetWeight(exerciseId: Long, value: String) {
        updateExercise(exerciseId) { it.copy(setWeight = value.filterWeightInput()) }
    }

    fun updateSetRpe(exerciseId: Long, value: String) {
        updateExercise(exerciseId) { it.copy(setRpe = value.filter { char -> char.isDigit() }.take(2)) }
    }

    fun updateSetNotes(exerciseId: Long, value: String) {
        updateExercise(exerciseId) { it.copy(setNotes = value) }
    }

    fun updateExerciseNotes(exerciseId: Long, value: String) {
        updateExercise(exerciseId) { it.copy(exerciseNotes = value) }
    }

    fun addSet(exerciseId: Long) {
        updateExercise(exerciseId) { exercise ->
            val setNumber = exercise.sets.size + 1
            exercise.copy(
                sets = exercise.sets + DraftSetState(
                    exerciseId = exercise.exerciseId,
                    setNumber = setNumber,
                    repsText = exercise.setReps,
                    weightText = exercise.setWeight,
                    rpeText = exercise.setRpe,
                    notes = exercise.setNotes.ifBlank { null }
                ),
                setReps = "",
                setWeight = "",
                setRpe = "",
                setNotes = ""
            )
        }
    }

    fun removeSet(exerciseId: Long, setNumber: Int) {
        updateExercise(exerciseId) { exercise ->
            exercise.copy(
                sets = exercise.sets
                    .filterNot { it.setNumber == setNumber }
                    .mapIndexed { index, set -> set.copy(setNumber = index + 1) }
            )
        }
    }

    fun selectWorkout(id: Long) {
        draftState.update { it.copy(selectedWorkoutId = id) }
    }

    fun clearSelectedWorkout() {
        draftState.update { it.copy(selectedWorkoutId = null) }
    }

    fun dismissPrCelebration() {
        _pendingPrAchievements.value = emptyList()
    }

    fun navigateCalendarWeek(delta: Int) {
        draftState.update { it.copy(calendarWeekOffset = (it.calendarWeekOffset + delta).coerceIn(-4, 4)) }
    }

    fun startWorkoutWithPlan(suggestedExercises: List<SuggestedExerciseUiState>) {
        draftState.update { draft ->
            draft.copy(
                isWorkoutStarted = true,
                selectedExercises = suggestedExercises.map { suggestion ->
                    val defaultRepsText = parseRepsFromRange(suggestion.prescribedRepsRange)?.toString() ?: ""
                    val defaultWeightText = parseWeightFromText(suggestion.suggestedWeightText)?.let { w ->
                        if (w % 1.0 == 0.0) w.toInt().toString() else "%.1f".format(w)
                    } ?: ""
                    val defaultRpeText = parseRpeFromRange(suggestion.prescribedRpe)?.toString() ?: ""
                    val preSets = if (suggestion.prescribedSets > 0) {
                        (1..suggestion.prescribedSets).map { setNum ->
                            DraftSetState(
                                exerciseId = suggestion.exerciseId,
                                setNumber = setNum,
                                repsText = defaultRepsText,
                                weightText = defaultWeightText,
                                rpeText = defaultRpeText,
                                notes = null
                            )
                        }
                    } else emptyList()
                    DraftExerciseState(
                        exerciseId = suggestion.exerciseId,
                        isExpanded = false,
                        sets = preSets,
                        setReps = defaultRepsText,
                        setWeight = defaultWeightText,
                        setRpe = defaultRpeText,
                        prescribedSets = suggestion.prescribedSets,
                        prescribedRepsRange = suggestion.prescribedRepsRange,
                        prescribedRpe = suggestion.prescribedRpe,
                        suggestedWeightText = suggestion.suggestedWeightText,
                        progressionNote = suggestion.progressionNote,
                        alternatives = suggestion.alternatives
                    )
                }
            )
        }
    }

    private fun parseRepsFromRange(range: String): Int? =
        range.split("-").firstOrNull()?.trim()?.toIntOrNull()

    private fun parseRpeFromRange(range: String): Int? =
        range.split("-").firstOrNull()?.trim()?.toIntOrNull()

    private fun parseWeightFromText(text: String): Double? {
        if (text.isBlank() || text == "Bodyweight" || text.startsWith("Choose")) return null
        return text.split(" ").firstOrNull()?.toDoubleOrNull()
    }

    fun updateSetFieldReps(exerciseId: Long, setNumber: Int, value: String) {
        updateSet(exerciseId, setNumber) { it.copy(repsText = value.filter { c -> c.isDigit() }) }
    }

    fun updateSetFieldWeight(exerciseId: Long, setNumber: Int, value: String) {
        updateSet(exerciseId, setNumber) { it.copy(weightText = value.filterWeightInput()) }
    }

    fun updateSetFieldRpe(exerciseId: Long, setNumber: Int, value: String) {
        updateSet(exerciseId, setNumber) { it.copy(rpeText = value.filter { c -> c.isDigit() }.take(2)) }
    }

    private fun updateSet(exerciseId: Long, setNumber: Int, transform: (DraftSetState) -> DraftSetState) {
        draftState.update { draft ->
            draft.copy(
                selectedExercises = draft.selectedExercises.map { exercise ->
                    if (exercise.exerciseId != exerciseId) exercise
                    else exercise.copy(
                        sets = exercise.sets.map { set ->
                            if (set.setNumber == setNumber) transform(set) else set
                        }
                    )
                }
            )
        }
    }

    fun saveWorkout(
        warmUpActivities: List<ActivityDraft> = emptyList(),
        coolDownActivities: List<ActivityDraft> = emptyList()
    ) {
        val draft = draftState.value
        val existingSets = rawWorkoutSets.value
        val exerciseMap = rawExercises.value.associateBy { it.id }
        val draftSetsForPr = draft.selectedExercises.flatMap { ex ->
            ex.sets.mapNotNull { set ->
                val w = set.weightText.toDoubleOrNull() ?: return@mapNotNull null
                val r = set.repsText.toIntOrNull() ?: return@mapNotNull null
                if (w <= 0 || r <= 0) return@mapNotNull null
                WorkoutExercise(
                    id = 0, workoutId = 0, exerciseId = ex.exerciseId,
                    setNumber = set.setNumber, reps = r, weight = w,
                    rpe = set.rpeText.toIntOrNull(), restSeconds = null, notes = null
                )
            }
        }
        viewModelScope.launch {
            val workoutId = workoutRepository.saveWorkout(
                date = LocalDate.now().toString(),
                name = draft.workoutName.ifBlank { "Strength Session" },
                status = draft.selectedStatus,
                durationMinutes = draft.durationMinutes.toIntOrNull(),
                overallRpe = draft.overallRpe.toIntOrNull(),
                notes = draft.combinedWorkoutNotes(),
                sets = draft.selectedExercises.flatMap { exercise ->
                    exercise.sets.map {
                        WorkoutSetInput(
                            exerciseId = it.exerciseId,
                            setNumber = it.setNumber,
                            reps = it.repsText.toIntOrNull(),
                            weight = it.weightText.toDoubleOrNull(),
                            rpe = it.rpeText.toIntOrNull(),
                            notes = it.notes
                        )
                    }
                }
            )
            val allActivities = warmUpActivities + coolDownActivities
            if (allActivities.isNotEmpty()) {
                recoveryActivityRepository.saveAll(
                    workoutId = workoutId,
                    activities = allActivities.map { d ->
                        RecoveryActivity(
                            workoutId = workoutId,
                            name = d.name,
                            status = d.status.storageValue,
                            durationMinutes = d.durationInput.toIntOrNull(),
                            rpe = d.rpeInput.toIntOrNull(),
                            notes = d.notesInput.ifBlank { null }
                        )
                    }
                )
            }
            val achievements = detectNewPrs(existingSets, draftSetsForPr, exerciseMap)
            if (achievements.isNotEmpty()) _pendingPrAchievements.value = achievements
            habitAutoUpdateUseCase(today)
            draftState.value = WorkoutDraftState()
        }
    }

    fun saveRecoverySession(activityDrafts: List<ActivityDraft>, overallNotes: String) {
        val draft = draftState.value
        val requiredDrafts = activityDrafts.filter { !it.name.startsWith("Optional") }
        val overallStatus = when {
            activityDrafts.all { it.status == WorkoutStatus.SKIPPED } -> WorkoutStatus.SKIPPED
            requiredDrafts.isNotEmpty() && requiredDrafts.all { it.status == WorkoutStatus.COMPLETED } -> WorkoutStatus.COMPLETED
            else -> WorkoutStatus.PARTIAL
        }
        val totalDuration = activityDrafts.sumOf { it.durationInput.toIntOrNull() ?: 0 }.takeIf { it > 0 }
        viewModelScope.launch {
            val workoutId = workoutRepository.saveWorkout(
                date = LocalDate.now().toString(),
                name = draft.workoutName.ifBlank { "Active Recovery" },
                status = overallStatus,
                durationMinutes = totalDuration,
                overallRpe = null,
                notes = overallNotes.ifBlank { null },
                sets = emptyList()
            )
            recoveryActivityRepository.saveAll(
                workoutId = workoutId,
                activities = activityDrafts.map { d ->
                    RecoveryActivity(
                        workoutId = workoutId,
                        name = d.name,
                        status = d.status.storageValue,
                        durationMinutes = d.durationInput.toIntOrNull(),
                        rpe = d.rpeInput.toIntOrNull(),
                        notes = d.notesInput.ifBlank { null }
                    )
                }
            )
            draftState.value = WorkoutDraftState()
        }
    }

    private fun updateExercise(
        exerciseId: Long,
        transform: (DraftExerciseState) -> DraftExerciseState
    ) {
        draftState.update { draft ->
            draft.copy(
                selectedExercises = draft.selectedExercises.map {
                    if (it.exerciseId == exerciseId) transform(it) else it
                }
            )
        }
    }
}

private data class WorkoutDraftState(
    val isWorkoutStarted: Boolean = false,
    val workoutName: String = "Strength Session",
    val durationMinutes: String = "",
    val overallRpe: String = "",
    val overallRpeError: String? = null,
    val workoutNotes: String = "",
    val selectedStatus: WorkoutStatus = WorkoutStatus.COMPLETED,
    val selectedExercises: List<DraftExerciseState> = emptyList(),
    val selectedWorkoutId: Long? = null,
    val calendarWeekOffset: Int = 0
)

private data class DraftExerciseState(
    val exerciseId: Long,
    val sets: List<DraftSetState> = emptyList(),
    val setReps: String = "",
    val setWeight: String = "",
    val setRpe: String = "",
    val setNotes: String = "",
    val exerciseNotes: String = "",
    val isExpanded: Boolean = true,
    val prescribedSets: Int = 0,
    val prescribedRepsRange: String = "",
    val prescribedRpe: String = "",
    val suggestedWeightText: String = "",
    val progressionNote: String = "",
    val alternatives: List<String> = emptyList()
)

private data class DraftSetState(
    val exerciseId: Long,
    val setNumber: Int,
    val repsText: String = "",
    val weightText: String = "",
    val rpeText: String = "",
    val notes: String? = null
)

private fun buildUiState(
    exercises: List<Exercise>,
    workouts: List<Workout>,
    workoutSets: List<WorkoutExercise>,
    draft: WorkoutDraftState,
    recommendation: DailyRecommendation?,
    workoutGoals: List<String>,
    generateWorkoutPlanUseCase: GenerateWorkoutPlanUseCase,
    today: String
): WorkoutUiState {
    val exerciseById = exercises.associateBy { it.id }
    val setsByWorkoutId = workoutSets.groupBy { it.workoutId }
    val sortedWorkouts = workouts.sortedByDescending { it.date }

    val historyList = sortedWorkouts.map { workout ->
        val sets = setsByWorkoutId[workout.id].orEmpty()
        val muscleGroups = sets.mapNotNull { exerciseById[it.exerciseId]?.muscleGroup }.distinct()
        val exerciseCount = sets.map { it.exerciseId }.distinct().size
        val rpeValues = sets.mapNotNull { it.rpe }
        val avgRpe = if (rpeValues.isNotEmpty()) "RPE ${rpeValues.average().let { "%.1f".format(it) }}" else ""
        val totalVolume = sets.sumOf { (it.weight ?: 0.0) * (it.reps ?: 0) }
        WorkoutHistoryUiState(
            id = workout.id,
            date = workout.date,
            name = workout.name,
            statusLabel = WorkoutStatus.fromStorageValue(workout.status).label,
            durationText = workout.durationMinutes?.let { "$it min" } ?: "",
            exerciseCount = exerciseCount,
            setCount = sets.size,
            avgRpe = avgRpe,
            muscleGroups = muscleGroups.joinToString(),
            totalVolumeText = formatVolume(totalVolume)
        )
    }

    val selectedDetail = draft.selectedWorkoutId?.let { id ->
        val workout = sortedWorkouts.firstOrNull { it.id == id } ?: return@let null
        val sets = setsByWorkoutId[id].orEmpty()
        val detailRpe = sets.mapNotNull { it.rpe }
        val detailVolume = sets.sumOf { (it.weight ?: 0.0) * (it.reps ?: 0) }
        WorkoutDetailUiState(
            id = workout.id,
            date = workout.date,
            name = workout.name,
            statusLabel = WorkoutStatus.fromStorageValue(workout.status).label,
            durationText = workout.durationMinutes?.let { "$it min" } ?: "",
            notes = workout.notes,
            exercises = sets.groupBy { it.exerciseId }.map { (exerciseId, exerciseSets) ->
                val exercise = exerciseById[exerciseId]
                ExerciseDetailUiState(
                    exerciseName = exercise?.name ?: "Unknown exercise",
                    muscleGroup = exercise?.muscleGroup ?: "",
                    sets = exerciseSets.sortedBy { it.setNumber }.map { set ->
                        SetDetailUiState(
                            setNumber = set.setNumber,
                            reps = set.reps,
                            weight = set.weight,
                            rpe = set.rpe,
                            notes = set.notes
                        )
                    }
                )
            },
            totalVolumeText = formatVolume(detailVolume),
            totalSetsText = if (sets.isNotEmpty()) "${sets.size} sets" else "",
            avgRpeText = if (detailRpe.isNotEmpty()) "Avg RPE ${"%.1f".format(detailRpe.average())}" else ""
        )
    }

    val workoutPlan = if (recommendation != null) {
        generateWorkoutPlanUseCase.generate(
            recommendationType = recommendation.recommendationType,
            exercises = exercises,
            recentWorkouts = workouts,
            recentSets = workoutSets,
            today = today,
            workoutGoals = workoutGoals
        ).toWorkoutPlanUiState()
    } else null

    val todayWorkouts = workouts.filter { it.date == today }
    val hasWorkoutTodayCompleted = todayWorkouts.any { WorkoutStatus.fromStorageValue(it.status) != WorkoutStatus.SKIPPED }
    val personalRecords = computePersonalRecords(workoutSets, exerciseById)
    val weeklyLoads = computeWeeklyLoads(workouts, workoutSets, today)
    val calendarWeekOffset = draft.calendarWeekOffset
    val calendarDays = buildCalendarWeek(workouts, workoutSets, exerciseById, today, workoutPlan, generateWorkoutPlanUseCase, calendarWeekOffset, workoutGoals)
    val calendarWeekLabel = calendarWeekLabel(calendarWeekOffset)

    return WorkoutUiState(
        isWorkoutStarted = draft.isWorkoutStarted,
        workoutName = draft.workoutName,
        durationMinutes = draft.durationMinutes,
        overallRpe = draft.overallRpe,
        overallRpeError = draft.overallRpeError,
        workoutNotes = draft.workoutNotes,
        selectedStatus = draft.selectedStatus,
        exercises = exercises.map {
            ExerciseOptionUiState(
                id = it.id,
                name = it.name,
                muscleGroup = it.muscleGroup,
                equipmentType = it.equipmentType,
                movementPattern = it.movementPattern
            )
        },
        selectedExercises = draft.selectedExercises.mapNotNull { draftExercise ->
            val exercise = exerciseById[draftExercise.exerciseId] ?: return@mapNotNull null
            SelectedExerciseUiState(
                exerciseId = exercise.id,
                name = exercise.name,
                muscleGroup = exercise.muscleGroup,
                equipmentType = exercise.equipmentType,
                movementPattern = exercise.movementPattern,
                sets = draftExercise.sets.map {
                    DraftWorkoutSetUiState(
                        exerciseId = it.exerciseId,
                        setNumber = it.setNumber,
                        repsText = it.repsText,
                        weightText = it.weightText,
                        rpeText = it.rpeText,
                        notes = it.notes
                    )
                },
                repsInput = draftExercise.setReps,
                weightInput = draftExercise.setWeight,
                rpeInput = draftExercise.setRpe,
                setNotesInput = draftExercise.setNotes,
                exerciseNotes = draftExercise.exerciseNotes,
                isExpanded = draftExercise.isExpanded,
                prescribedSets = draftExercise.prescribedSets,
                prescribedRepsRange = draftExercise.prescribedRepsRange,
                prescribedRpe = draftExercise.prescribedRpe,
                suggestedWeightText = draftExercise.suggestedWeightText,
                progressionNote = draftExercise.progressionNote
            )
        },
        recentWorkouts = historyList,
        selectedWorkoutDetail = selectedDetail,
        workoutPlan = workoutPlan,
        hasWorkoutTodayCompleted = hasWorkoutTodayCompleted,
        personalRecords = personalRecords,
        weeklyLoads = weeklyLoads,
        calendarDays = calendarDays,
        selectedWeekOffset = calendarWeekOffset,
        calendarWeekLabel = calendarWeekLabel
    )
}

private fun WorkoutDraftState.combinedWorkoutNotes(): String? {
    val exerciseNotes = selectedExercises
        .filter { it.exerciseNotes.isNotBlank() }
        .joinToString(separator = "\n") { "Exercise ${it.exerciseId}: ${it.exerciseNotes}" }

    return listOf(workoutNotes, exerciseNotes)
        .filter { it.isNotBlank() }
        .joinToString(separator = "\n\n")
        .ifBlank { null }
}

private fun String.filterWeightInput(): String {
    val builder = StringBuilder()
    var hasDecimal = false
    forEach { char ->
        when {
            char.isDigit() -> builder.append(char)
            char == '.' && !hasDecimal -> {
                builder.append(char)
                hasDecimal = true
            }
        }
    }
    return builder.toString()
}

private fun epley1Rm(weight: Double, reps: Int): Double =
    if (reps <= 1) weight else weight * (1 + reps / 30.0)

private fun formatWeight(w: Double): String =
    if (w % 1.0 == 0.0) "${w.toInt()} lb" else "${"%.1f".format(w)} lb"

private fun formatWeightNum(w: Double): String =
    if (w % 1.0 == 0.0) "${w.toInt()}" else "%.1f".format(w)

private fun formatVolume(lbs: Double): String =
    if (lbs <= 0.0) "" else "%,d lb".format(lbs.toLong())

private fun computePersonalRecords(
    sets: List<WorkoutExercise>,
    exerciseById: Map<Long, Exercise>
): List<PersonalRecordUiState> {
    return sets
        .filter { (it.weight ?: 0.0) > 0 && (it.reps ?: 0) > 0 }
        .groupBy { it.exerciseId }
        .mapNotNull { (exerciseId, exSets) ->
            val exercise = exerciseById[exerciseId] ?: return@mapNotNull null
            val bestWeightSet = exSets.maxByOrNull { it.weight!! } ?: return@mapNotNull null
            val bestVolSet = exSets.maxByOrNull { it.weight!! * it.reps!! } ?: return@mapNotNull null
            val best1RmSet = exSets.maxByOrNull { epley1Rm(it.weight!!, it.reps!!) } ?: return@mapNotNull null
            PersonalRecordUiState(
                exerciseName = exercise.name,
                bestWeightText = formatWeight(bestWeightSet.weight!!),
                bestVolumeSetText = "${formatWeightNum(bestVolSet.weight!!)} × ${bestVolSet.reps}",
                estimated1RmText = "~${epley1Rm(best1RmSet.weight!!, best1RmSet.reps!!).toInt()} lb"
            )
        }
        .sortedBy { it.exerciseName }
}

private fun computeWeeklyLoads(
    workouts: List<Workout>,
    sets: List<WorkoutExercise>,
    today: String
): List<WeeklyLoadUiState> {
    val setsByWorkoutId = sets.groupBy { it.workoutId }
    val completed = workouts.filter { WorkoutStatus.fromStorageValue(it.status) != WorkoutStatus.SKIPPED }
    if (completed.isEmpty()) return emptyList()
    val byWeek = completed.groupBy { getIsoWeekKey(it.date) }
    return byWeek.entries.sortedByDescending { it.key }.take(8).map { (weekKey, weekWorkouts) ->
        val weekSets = weekWorkouts.flatMap { setsByWorkoutId[it.id].orEmpty() }
        val totalVolume = weekSets.sumOf { (it.weight ?: 0.0) * (it.reps ?: 0) }
        val rpeValues = weekSets.mapNotNull { it.rpe }
        WeeklyLoadUiState(
            weekLabel = formatWeekLabel(weekKey),
            workoutCount = weekWorkouts.size,
            totalSets = weekSets.size,
            totalVolumeText = formatVolume(totalVolume).ifEmpty { "–" },
            avgRpe = if (rpeValues.isNotEmpty()) "%.1f".format(rpeValues.average()) else "–"
        )
    }
}

private fun getIsoWeekKey(dateStr: String): String {
    return try {
        val date = LocalDate.parse(dateStr)
        date.with(DayOfWeek.MONDAY).toString()
    } catch (e: Exception) { dateStr }
}

private fun formatWeekLabel(weekStartStr: String): String {
    return try {
        val date = LocalDate.parse(weekStartStr)
        val month = date.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())
        "Week of $month ${date.dayOfMonth}"
    } catch (e: Exception) { weekStartStr }
}

private fun detectNewPrs(
    existing: List<WorkoutExercise>,
    incoming: List<WorkoutExercise>,
    exerciseById: Map<Long, Exercise>
): List<String> {
    val achievements = mutableListOf<String>()
    for (id in incoming.map { it.exerciseId }.distinct()) {
        val exName = exerciseById[id]?.name ?: continue
        val existingForEx = existing.filter { it.exerciseId == id && (it.weight ?: 0.0) > 0 && (it.reps ?: 0) > 0 }
        val incomingForEx = incoming.filter { it.exerciseId == id }
        if (incomingForEx.isEmpty()) continue

        val oldBestWeight = existingForEx.maxOfOrNull { it.weight!! } ?: 0.0
        val newBestWeight = incomingForEx.maxOfOrNull { it.weight ?: 0.0 } ?: 0.0
        if (newBestWeight > oldBestWeight) {
            achievements.add("$exName — Heaviest: ${formatWeight(newBestWeight)}")
            continue
        }

        val old1Rm = existingForEx.maxOfOrNull { epley1Rm(it.weight!!, it.reps!!) } ?: 0.0
        val new1Rm = incomingForEx.mapNotNull { s ->
            val w = s.weight ?: return@mapNotNull null
            val r = s.reps ?: return@mapNotNull null
            epley1Rm(w, r)
        }.maxOrNull() ?: 0.0
        if (new1Rm > old1Rm) {
            achievements.add("$exName — New est. 1RM: ~${new1Rm.toInt()} lb")
            continue
        }

        val oldBestVol = existingForEx.maxOfOrNull { it.weight!! * it.reps!! } ?: 0.0
        val newBestVolSet = incomingForEx.maxByOrNull { (it.weight ?: 0.0) * (it.reps ?: 0) }
        val newBestVol = (newBestVolSet?.weight ?: 0.0) * (newBestVolSet?.reps ?: 0)
        if (newBestVol > oldBestVol && newBestVolSet != null) {
            achievements.add("$exName — Best set: ${formatWeightNum(newBestVolSet.weight ?: 0.0)} × ${newBestVolSet.reps}")
        }
    }
    return achievements.take(3)
}

private fun buildCalendarWeek(
    workouts: List<Workout>,
    workoutSets: List<WorkoutExercise>,
    exerciseById: Map<Long, Exercise>,
    today: String,
    todayPlan: WorkoutPlanUiState?,
    useCase: GenerateWorkoutPlanUseCase,
    weekOffset: Int = 0,
    workoutGoals: List<String> = listOf("General Fitness")
): List<CalendarDayUiState> {
    val todayDate = try { LocalDate.parse(today) } catch (e: Exception) { return emptyList() }
    val weekStart = todayDate.with(DayOfWeek.MONDAY).plusWeeks(weekOffset.toLong())
    val workoutsByDate = workouts.groupBy { it.date }
    val setsByWorkoutId = workoutSets.groupBy { it.workoutId }
    val exerciseList = exerciseById.values.toList()

    // All days in a future week are projected — use chained planning
    if (weekOffset > 0) {
        return buildProjectedWeek(weekStart, workouts, workoutSets, exerciseById, todayDate, workoutGoals, useCase)
    }

    // Seed upper/lower alternation from last real strength session (mirrors buildProjectedWeek logic)
    val calUpper = setOf("Chest", "Back", "Shoulders", "Arms")
    val calLower = setOf("Legs", "Core")
    val calLastStrength = workouts
        .filter { w -> isStrengthWorkoutName(w.name) && WorkoutStatus.fromStorageValue(w.status) != WorkoutStatus.SKIPPED }
        .maxByOrNull { it.date }
    val calLastMuscles = if (calLastStrength != null) {
        workoutSets.filter { it.workoutId == calLastStrength.id }
            .mapNotNull { exerciseById[it.exerciseId]?.muscleGroup }.toSet()
    } else emptySet()
    var calNextIsUpper = !(calLastMuscles.any { it in calUpper } && calLastMuscles.none { it in calLower })

    // Augment with projected strength sessions so planFutureDay sees prior within-week projections
    val augmentedWorkouts = workouts.toMutableList()

    return (0..6).map { offset ->
        val day = weekStart.plusDays(offset.toLong())
        val dateStr = day.toString()
        val isToday = dateStr == today
        val isPast = day.isBefore(todayDate)
        val dayLabel = day.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
        val dayWorkouts = workoutsByDate[dateStr] ?: emptyList()
        val completedWorkout = dayWorkouts.firstOrNull {
            WorkoutStatus.fromStorageValue(it.status) != WorkoutStatus.SKIPPED
        }
        when {
            completedWorkout != null -> {
                val sets = setsByWorkoutId[completedWorkout.id].orEmpty()
                val muscleGroups = sets.mapNotNull { exerciseById[it.exerciseId]?.muscleGroup }.distinct()
                CalendarDayUiState(
                    date = dateStr, dayLabel = dayLabel, dateNumber = day.dayOfMonth, isToday = isToday,
                    workoutTypeLabel = completedWorkout.name,
                    workoutTag = calendarTagFromName(completedWorkout.name, muscleGroups),
                    isCompleted = true, completedWorkoutId = completedWorkout.id
                )
            }
            isToday -> {
                val tag = when {
                    todayPlan == null -> "REST"
                    todayPlan.isStrengthDay -> "STRENGTH"
                    todayPlan.focus.contains("Mobil", ignoreCase = true) -> "MOBILITY"
                    todayPlan.focus.contains("Walk", ignoreCase = true) -> "WALK"
                    todayPlan.focus.contains("Rest", ignoreCase = true) -> "REST"
                    else -> "RECOVERY"
                }
                CalendarDayUiState(
                    date = dateStr, dayLabel = dayLabel, dateNumber = day.dayOfMonth, isToday = true,
                    workoutTypeLabel = todayPlan?.focus ?: "Rest", workoutTag = tag, isCompleted = false,
                    projectedPlan = todayPlan
                )
            }
            isPast -> {
                val skipped = dayWorkouts.any { WorkoutStatus.fromStorageValue(it.status) == WorkoutStatus.SKIPPED }
                CalendarDayUiState(
                    date = dateStr, dayLabel = dayLabel, dateNumber = day.dayOfMonth, isToday = false,
                    workoutTypeLabel = if (skipped) "Skipped" else "–",
                    workoutTag = if (skipped) "SKIPPED" else "REST", isCompleted = false
                )
            }
            else -> {
                val (rawLabel, tag) = planFutureDay(day, augmentedWorkouts, workoutSets, exerciseById, todayDate, workoutGoals)
                // For STRENGTH days, bypass planFutureDay's alternation (it can't read synthetic workout sets)
                // and use our own tracked nextIsUpper toggle instead.
                val label = if (tag == "STRENGTH") {
                    val l = if (calNextIsUpper) "Upper Body" else "Lower Body"
                    calNextIsUpper = !calNextIsUpper
                    l
                } else rawLabel
                if (tag == "STRENGTH") {
                    augmentedWorkouts.add(Workout(
                        id = -(2000L + offset),
                        date = dateStr,
                        name = label,
                        durationMinutes = 60,
                        status = WorkoutStatus.COMPLETED.storageValue,
                        overallRpe = 7,
                        notes = null
                    ))
                }
                val plan = useCase.generate(
                    recommendationType = workoutTagToRecType(tag),
                    exercises = exerciseList,
                    recentWorkouts = augmentedWorkouts.toList(),
                    recentSets = workoutSets,
                    today = dateStr,
                    workoutGoals = workoutGoals,
                    focusOverride = if (tag == "STRENGTH") label else null
                ).toWorkoutPlanUiState()
                CalendarDayUiState(
                    date = dateStr, dayLabel = dayLabel, dateNumber = day.dayOfMonth, isToday = false,
                    workoutTypeLabel = plan.focus, workoutTag = tag, isCompleted = false, isFuture = true,
                    projectedPlan = plan
                )
            }
        }
    }
}

private fun buildProjectedWeek(
    weekStart: LocalDate,
    allWorkouts: List<Workout>,
    workoutSets: List<WorkoutExercise>,
    exerciseById: Map<Long, Exercise>,
    today: LocalDate,
    workoutGoals: List<String> = listOf("General Fitness"),
    useCase: GenerateWorkoutPlanUseCase
): List<CalendarDayUiState> {
    val upper = setOf("Chest", "Back", "Shoulders", "Arms")
    val lower = setOf("Legs", "Core")
    val exerciseList = exerciseById.values.toList()

    // Determine alternation starting point from last DB strength session
    val lastStrengthFromDb = allWorkouts
        .filter { w -> isStrengthWorkoutName(w.name) && WorkoutStatus.fromStorageValue(w.status) != WorkoutStatus.SKIPPED }
        .maxByOrNull { it.date }
    val lastMuscles = if (lastStrengthFromDb != null) {
        workoutSets.filter { it.workoutId == lastStrengthFromDb.id }
            .mapNotNull { exerciseById[it.exerciseId]?.muscleGroup }.toSet()
    } else emptySet()
    var nextIsUpper = !(lastMuscles.any { it in upper } && lastMuscles.none { it in lower })

    // Augmented list allows planFutureDay to see simulated completed strength days
    val augmented = allWorkouts.toMutableList()

    return (0..6).map { offset ->
        val day = weekStart.plusDays(offset.toLong())
        val dateStr = day.toString()
        val dayLabel = day.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
        val (rawLabel, tag) = planFutureDay(day, augmented, workoutSets, exerciseById, today, workoutGoals)

        // For STRENGTH days, apply tracked alternation and advance the toggle
        val finalLabel = if (tag == "STRENGTH") {
            val label = if (nextIsUpper) "Upper Body" else "Lower Body"
            nextIsUpper = !nextIsUpper
            // Add synthetic entry so subsequent days see a completed strength session
            augmented.add(
                Workout(
                    id = -(1000L + offset),
                    date = dateStr,
                    name = label,
                    durationMinutes = 60,
                    status = WorkoutStatus.COMPLETED.storageValue,
                    overallRpe = 7,
                    notes = null
                )
            )
            label
        } else rawLabel

        val plan = useCase.generate(
            recommendationType = workoutTagToRecType(tag),
            exercises = exerciseList,
            recentWorkouts = augmented.toList(),
            recentSets = workoutSets,
            today = dateStr,
            workoutGoals = workoutGoals,
            focusOverride = if (tag == "STRENGTH") finalLabel else null
        ).toWorkoutPlanUiState()

        CalendarDayUiState(
            date = dateStr, dayLabel = dayLabel, dateNumber = day.dayOfMonth, isToday = false,
            workoutTypeLabel = plan.focus, workoutTag = tag,
            isCompleted = false, isFuture = true, isProjected = true,
            projectedPlan = plan
        )
    }
}

private fun isStrengthWorkoutName(name: String): Boolean {
    val n = name.lowercase()
    return !n.contains("rest") && !n.contains("mobil") && !n.contains("recovery")
        && !n.contains("walk") && !n.contains("gentle")
}

private fun calendarWeekLabel(offset: Int): String = when (offset) {
    0 -> "This Week"
    1 -> "Next Week"
    -1 -> "Last Week"
    else -> if (offset > 0) "$offset Weeks Ahead" else "${-offset} Weeks Ago"
}

private fun calendarTagFromName(workoutName: String, muscleGroups: List<String>): String {
    val name = workoutName.lowercase()
    return when {
        name.contains("mobil") || name.contains("stretch") || name.contains("flexibility") -> "MOBILITY"
        name.contains("walk") -> "WALK"
        name.contains("recovery") -> "RECOVERY"
        name.contains("rest") -> "REST"
        muscleGroups.isNotEmpty() -> "STRENGTH"
        else -> "RECOVERY"
    }
}

private fun planFutureDay(
    day: LocalDate,
    allWorkouts: List<Workout>,
    workoutSets: List<WorkoutExercise>,
    exerciseById: Map<Long, Exercise>,
    today: LocalDate,
    workoutGoals: List<String> = listOf("General Fitness")
): Pair<String, String> {
    // Use day's own week boundary so projected weeks don't bleed current-week completions
    val weekStart = day.with(DayOfWeek.MONDAY)
    val completedThisWeek = allWorkouts.count { w ->
        val d = try { LocalDate.parse(w.date) } catch (e: Exception) { return@count false }
        d >= weekStart && d < day && WorkoutStatus.fromStorageValue(w.status) != WorkoutStatus.SKIPPED
    }
    val isConservative = workoutGoals.any { it in listOf("Physical Therapy", "Postpartum", "Low Impact", "Recovery Focus") }
    val isAggressive = workoutGoals.any { it in listOf("Strength Training", "Muscle Gain", "Fat Loss", "Metabolic Reset", "Insulin Resistance") }
    val targetSessions = when {
        isConservative -> 2
        isAggressive -> 4
        else -> 3
    }
    if (completedThisWeek >= targetSessions) return "Rest" to "REST"

    val workoutsByDate = allWorkouts.groupBy { it.date }
    fun completedOn(d: LocalDate) = workoutsByDate[d.toString()]
        ?.firstOrNull { WorkoutStatus.fromStorageValue(it.status) != WorkoutStatus.SKIPPED }

    val prevWorkout = completedOn(day.minusDays(1))
    val prev2Workout = completedOn(day.minusDays(2))

    fun isStrength(w: Workout?): Boolean {
        if (w == null) return false
        val n = w.name.lowercase()
        return !n.contains("rest") && !n.contains("mobil") && !n.contains("recovery")
            && !n.contains("walk") && !n.contains("gentle")
    }

    if (isStrength(prevWorkout) && isStrength(prev2Workout)) return "Mobility" to "MOBILITY"
    if (isStrength(prevWorkout)) return "Active Recovery" to "RECOVERY"

    // Alternate upper/lower based on last strength session muscle groups
    val lastStrength = allWorkouts
        .filter { isStrength(it) && WorkoutStatus.fromStorageValue(it.status) != WorkoutStatus.SKIPPED }
        .filter { w ->
            val d = try { LocalDate.parse(w.date) } catch (e: Exception) { return@filter false }
            d < day
        }
        .maxByOrNull { it.date }

    if (lastStrength != null) {
        val lastSets = workoutSets.filter { it.workoutId == lastStrength.id }
        val lastMuscles = lastSets.mapNotNull { exerciseById[it.exerciseId]?.muscleGroup }.toSet()
        val upper = setOf("Chest", "Back", "Shoulders", "Arms")
        val lower = setOf("Legs", "Core")
        return when {
            lastMuscles.any { it in upper } && lastMuscles.none { it in lower } -> "Lower Body" to "STRENGTH"
            lastMuscles.any { it in lower } && lastMuscles.none { it in upper } -> "Upper Body" to "STRENGTH"
            else -> "Upper Body" to "STRENGTH"
        }
    }
    return "Strength" to "STRENGTH"
}

private fun workoutTagToRecType(tag: String): String = when (tag) {
    "STRENGTH" -> "STRENGTH"
    "MOBILITY" -> "WALKING_MOBILITY"
    "RECOVERY" -> "ACTIVE_RECOVERY"
    "WALK" -> "WALKING_MOBILITY"
    else -> "REST"
}

private fun WorkoutPlan.toWorkoutPlanUiState(): WorkoutPlanUiState = WorkoutPlanUiState(
    focus = focus,
    setsPerExercise = setsPerExercise,
    repsRange = repsRange,
    rpeTarget = rpeTarget,
    durationMinutes = durationMinutes,
    suggestedExercises = suggestedExercises.map {
        SuggestedExerciseUiState(
            exerciseId = it.exerciseId,
            name = it.name,
            muscleGroup = it.muscleGroup,
            prescribedSets = it.prescribedSets,
            prescribedRepsRange = it.prescribedRepsRange,
            prescribedRpe = it.prescribedRpe,
            suggestedWeightText = it.suggestedWeightText,
            progressionNote = it.progressionNote,
            alternatives = it.alternatives
        )
    },
    isStrengthDay = isStrengthDay,
    reasons = reasons,
    nonStrengthActivities = nonStrengthActivities,
    warmUp = warmUp,
    coolDown = coolDown,
    postWorkoutRecommendations = postWorkoutRecommendations
)

private fun mergeRecoveryLogs(
    state: WorkoutUiState,
    allLogs: List<RecoveryActivity>
): WorkoutUiState {
    val detail = state.selectedWorkoutDetail ?: return state
    val logsForWorkout = allLogs.filter { it.workoutId == detail.id }
    if (logsForWorkout.isEmpty()) return state
    return state.copy(
        selectedWorkoutDetail = detail.copy(
            recoveryActivities = logsForWorkout.map { log ->
                RecoveryActivityDetailUiState(
                    name = log.name,
                    statusLabel = WorkoutStatus.fromStorageValue(log.status).label,
                    durationText = log.durationMinutes?.let { "$it min" } ?: "",
                    rpe = log.rpe?.let { "RPE $it" } ?: "",
                    notes = log.notes
                )
            }
        )
    )
}

class WorkoutViewModelFactory(
    private val exerciseRepository: ExerciseRepository,
    private val workoutRepository: WorkoutRepository,
    private val dailyRecommendationRepository: DailyRecommendationRepository,
    private val recoveryActivityRepository: RecoveryActivityRepository,
    private val generateWorkoutPlanUseCase: GenerateWorkoutPlanUseCase,
    private val habitAutoUpdateUseCase: HabitAutoUpdateUseCase,
    private val userProfileRepository: UserProfileRepository,
    private val today: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutViewModel::class.java)) {
            return WorkoutViewModel(
                exerciseRepository = exerciseRepository,
                workoutRepository = workoutRepository,
                dailyRecommendationRepository = dailyRecommendationRepository,
                recoveryActivityRepository = recoveryActivityRepository,
                generateWorkoutPlanUseCase = generateWorkoutPlanUseCase,
                habitAutoUpdateUseCase = habitAutoUpdateUseCase,
                userProfileRepository = userProfileRepository,
                today = today
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
