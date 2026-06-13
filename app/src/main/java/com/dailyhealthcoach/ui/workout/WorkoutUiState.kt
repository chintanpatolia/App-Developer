package com.dailyhealthcoach.ui.workout

import com.dailyhealthcoach.domain.model.WorkoutStatus

data class WorkoutUiState(
    val isWorkoutStarted: Boolean = false,
    val workoutName: String = "Strength Session",
    val durationMinutes: String = "",
    val overallRpe: String = "",
    val overallRpeError: String? = null,
    val workoutNotes: String = "",
    val selectedStatus: WorkoutStatus = WorkoutStatus.COMPLETED,
    val exercises: List<ExerciseOptionUiState> = emptyList(),
    val selectedExercises: List<SelectedExerciseUiState> = emptyList(),
    val recentWorkouts: List<WorkoutHistoryUiState> = emptyList(),
    val selectedWorkoutDetail: WorkoutDetailUiState? = null,
    val workoutPlan: WorkoutPlanUiState? = null
)

data class SuggestedExerciseUiState(
    val exerciseId: Long,
    val name: String,
    val muscleGroup: String
)

data class WorkoutPlanUiState(
    val focus: String,
    val setsPerExercise: Int,
    val repsRange: String,
    val rpeTarget: String,
    val durationMinutes: String,
    val suggestedExercises: List<SuggestedExerciseUiState>,
    val isStrengthDay: Boolean,
    val reasons: List<String>,
    val nonStrengthActivities: List<String>
)

data class ExerciseOptionUiState(
    val id: Long,
    val name: String,
    val muscleGroup: String,
    val equipmentType: String,
    val movementPattern: String
)

data class DraftWorkoutSetUiState(
    val exerciseId: Long,
    val setNumber: Int,
    val reps: Int?,
    val weight: Double?,
    val rpe: Int?,
    val notes: String?
)

data class SelectedExerciseUiState(
    val exerciseId: Long,
    val name: String,
    val muscleGroup: String,
    val equipmentType: String,
    val movementPattern: String,
    val sets: List<DraftWorkoutSetUiState>,
    val repsInput: String,
    val weightInput: String,
    val rpeInput: String,
    val setNotesInput: String,
    val exerciseNotes: String,
    val isExpanded: Boolean
)

data class WorkoutHistoryUiState(
    val id: Long,
    val date: String,
    val name: String,
    val statusLabel: String,
    val durationText: String,
    val exerciseCount: Int,
    val setCount: Int,
    val avgRpe: String,
    val muscleGroups: String
)

data class SetDetailUiState(
    val setNumber: Int,
    val reps: Int?,
    val weight: Double?,
    val rpe: Int?,
    val notes: String?
)

data class ExerciseDetailUiState(
    val exerciseName: String,
    val muscleGroup: String,
    val sets: List<SetDetailUiState>
)

data class ActivityDraft(
    val name: String,
    val status: WorkoutStatus = WorkoutStatus.COMPLETED,
    val durationInput: String = "",
    val rpeInput: String = "",
    val notesInput: String = ""
)

data class RecoveryActivityDetailUiState(
    val name: String,
    val statusLabel: String,
    val durationText: String,
    val rpe: String,
    val notes: String?
)

data class WorkoutDetailUiState(
    val id: Long,
    val date: String,
    val name: String,
    val statusLabel: String,
    val durationText: String,
    val notes: String?,
    val exercises: List<ExerciseDetailUiState>,
    val recoveryActivities: List<RecoveryActivityDetailUiState> = emptyList()
)
