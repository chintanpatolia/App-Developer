package com.dailyhealthcoach.ui.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dailyhealthcoach.domain.model.DailyRecommendation
import com.dailyhealthcoach.domain.model.Exercise
import com.dailyhealthcoach.domain.model.Workout
import com.dailyhealthcoach.domain.model.WorkoutExercise
import com.dailyhealthcoach.domain.model.WorkoutSetInput
import com.dailyhealthcoach.domain.model.WorkoutStatus
import com.dailyhealthcoach.domain.repository.DailyRecommendationRepository
import com.dailyhealthcoach.domain.repository.ExerciseRepository
import com.dailyhealthcoach.domain.repository.WorkoutRepository
import com.dailyhealthcoach.domain.usecase.GenerateWorkoutPlanUseCase
import java.time.LocalDate
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
    private val generateWorkoutPlanUseCase: GenerateWorkoutPlanUseCase,
    private val today: String
) : ViewModel() {
    private val draftState = MutableStateFlow(WorkoutDraftState())

    val uiState: StateFlow<WorkoutUiState> = combine(
        exerciseRepository.observeExercises(),
        workoutRepository.observeWorkouts(),
        workoutRepository.observeWorkoutSets(),
        draftState,
        dailyRecommendationRepository.observeForDate(today)
    ) { exercises, workouts, workoutSets, draft, recommendation ->
        buildUiState(exercises, workouts, workoutSets, draft, recommendation, generateWorkoutPlanUseCase, today)
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
        draftState.update { it.copy(overallRpe = value.filter { char -> char.isDigit() }.take(2)) }
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
                    reps = exercise.setReps.toIntOrNull(),
                    weight = exercise.setWeight.toDoubleOrNull(),
                    rpe = exercise.setRpe.toIntOrNull(),
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

    fun startWorkoutWithPlan(exerciseIds: List<Long>) {
        draftState.update { draft ->
            draft.copy(
                isWorkoutStarted = true,
                selectedExercises = exerciseIds.map { DraftExerciseState(exerciseId = it, isExpanded = false) }
            )
        }
    }

    fun saveWorkout() {
        val draft = draftState.value
        viewModelScope.launch {
            workoutRepository.saveWorkout(
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
                            reps = it.reps,
                            weight = it.weight,
                            rpe = it.rpe,
                            notes = it.notes
                        )
                    }
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
    val workoutNotes: String = "",
    val selectedStatus: WorkoutStatus = WorkoutStatus.COMPLETED,
    val selectedExercises: List<DraftExerciseState> = emptyList(),
    val selectedWorkoutId: Long? = null
)

private data class DraftExerciseState(
    val exerciseId: Long,
    val sets: List<DraftSetState> = emptyList(),
    val setReps: String = "",
    val setWeight: String = "",
    val setRpe: String = "",
    val setNotes: String = "",
    val exerciseNotes: String = "",
    val isExpanded: Boolean = true
)

private data class DraftSetState(
    val exerciseId: Long,
    val setNumber: Int,
    val reps: Int?,
    val weight: Double?,
    val rpe: Int?,
    val notes: String?
)

private fun buildUiState(
    exercises: List<Exercise>,
    workouts: List<Workout>,
    workoutSets: List<WorkoutExercise>,
    draft: WorkoutDraftState,
    recommendation: DailyRecommendation?,
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
        WorkoutHistoryUiState(
            id = workout.id,
            date = workout.date,
            name = workout.name,
            statusLabel = WorkoutStatus.fromStorageValue(workout.status).label,
            durationText = workout.durationMinutes?.let { "$it min" } ?: "",
            exerciseCount = exerciseCount,
            setCount = sets.size,
            avgRpe = avgRpe,
            muscleGroups = muscleGroups.joinToString()
        )
    }

    val selectedDetail = draft.selectedWorkoutId?.let { id ->
        val workout = sortedWorkouts.firstOrNull { it.id == id } ?: return@let null
        val sets = setsByWorkoutId[id].orEmpty()
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
            }
        )
    }

    val workoutPlan = if (recommendation != null) {
        val plan = generateWorkoutPlanUseCase.generate(
            recommendationType = recommendation.recommendationType,
            exercises = exercises,
            recentWorkouts = workouts,
            recentSets = workoutSets,
            today = today
        )
        WorkoutPlanUiState(
            focus = plan.focus,
            setsPerExercise = plan.setsPerExercise,
            repsRange = plan.repsRange,
            rpeTarget = plan.rpeTarget,
            durationMinutes = plan.durationMinutes,
            suggestedExercises = plan.suggestedExercises.map {
                SuggestedExerciseUiState(it.exerciseId, it.name, it.muscleGroup)
            },
            isStrengthDay = plan.isStrengthDay,
            reasons = plan.reasons,
            nonStrengthActivities = plan.nonStrengthActivities
        )
    } else null

    return WorkoutUiState(
        isWorkoutStarted = draft.isWorkoutStarted,
        workoutName = draft.workoutName,
        durationMinutes = draft.durationMinutes,
        overallRpe = draft.overallRpe,
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
                        reps = it.reps,
                        weight = it.weight,
                        rpe = it.rpe,
                        notes = it.notes
                    )
                },
                repsInput = draftExercise.setReps,
                weightInput = draftExercise.setWeight,
                rpeInput = draftExercise.setRpe,
                setNotesInput = draftExercise.setNotes,
                exerciseNotes = draftExercise.exerciseNotes,
                isExpanded = draftExercise.isExpanded
            )
        },
        recentWorkouts = historyList,
        selectedWorkoutDetail = selectedDetail,
        workoutPlan = workoutPlan
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

class WorkoutViewModelFactory(
    private val exerciseRepository: ExerciseRepository,
    private val workoutRepository: WorkoutRepository,
    private val dailyRecommendationRepository: DailyRecommendationRepository,
    private val generateWorkoutPlanUseCase: GenerateWorkoutPlanUseCase,
    private val today: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutViewModel::class.java)) {
            return WorkoutViewModel(
                exerciseRepository = exerciseRepository,
                workoutRepository = workoutRepository,
                dailyRecommendationRepository = dailyRecommendationRepository,
                generateWorkoutPlanUseCase = generateWorkoutPlanUseCase,
                today = today
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
