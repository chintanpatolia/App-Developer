package com.dailyhealthcoach.ui.workout

import com.dailyhealthcoach.domain.model.WorkoutStatus

data class WorkoutUiState(
    val isWorkoutStarted: Boolean = false,
    val workoutName: String = "Strength Session",
    val durationMinutes: String = "",
    val overallRpe: String = "",
    val workoutNotes: String = "",
    val selectedStatus: WorkoutStatus = WorkoutStatus.COMPLETED,
    val exercises: List<ExerciseOptionUiState> = emptyList(),
    val selectedExercises: List<SelectedExerciseUiState> = emptyList(),
    val recentWorkouts: List<WorkoutHistoryUiState> = emptyList()
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
    val setCount: Int,
    val muscleGroups: String
)
