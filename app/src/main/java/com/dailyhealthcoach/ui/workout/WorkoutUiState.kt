package com.dailyhealthcoach.ui.workout

import com.dailyhealthcoach.domain.model.WorkoutStatus

enum class TimerPhase { IDLE, RUNNING, PAUSED }

data class TimerState(
    val activityIndex: Int,
    val remainingSeconds: Int,
    val phase: TimerPhase
)

data class WorkoutUiState(
    val isWorkoutStarted: Boolean = false,
    val isNonStrengthSessionStarted: Boolean = false,
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
    val workoutPlan: WorkoutPlanUiState? = null,
    val hasWorkoutTodayCompleted: Boolean = false,
    val personalRecords: List<PersonalRecordUiState> = emptyList(),
    val newPrAchievements: List<String> = emptyList(),
    val weeklyLoads: List<WeeklyLoadUiState> = emptyList(),
    val calendarDays: List<CalendarDayUiState> = emptyList(),
    val selectedWeekOffset: Int = 0,
    val calendarWeekLabel: String = "This Week",
    val warmUpDrafts: List<ActivityDraft> = emptyList(),
    val coolDownDrafts: List<ActivityDraft> = emptyList(),
    val nonStrengthDrafts: List<ActivityDraft> = emptyList(),
    val nonStrengthOverallNotes: String = "",
    val activeTimer: TimerState? = null
)

data class CalendarDayUiState(
    val date: String,
    val dayLabel: String,
    val dateNumber: Int,
    val isToday: Boolean,
    val workoutTypeLabel: String,
    val workoutTag: String,           // "STRENGTH" | "MOBILITY" | "RECOVERY" | "WALK" | "REST" | "SKIPPED"
    val isCompleted: Boolean,
    val completedWorkoutId: Long? = null,
    val isFuture: Boolean = false,
    val isProjected: Boolean = false,
    val projectedPlan: WorkoutPlanUiState? = null
)

data class PersonalRecordUiState(
    val exerciseName: String,
    val bestWeightText: String,
    val bestVolumeSetText: String,
    val estimated1RmText: String
)

data class WeeklyLoadUiState(
    val weekLabel: String,
    val workoutCount: Int,
    val totalSets: Int,
    val totalVolumeText: String,
    val avgRpe: String
)

data class SuggestedExerciseUiState(
    val exerciseId: Long,
    val name: String,
    val muscleGroup: String,
    val prescribedSets: Int = 0,
    val prescribedRepsRange: String = "",
    val prescribedRpe: String = "",
    val suggestedWeightText: String = "",
    val progressionNote: String = "",
    val alternatives: List<String> = emptyList()
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
    val nonStrengthActivities: List<String>,
    val warmUp: List<String> = emptyList(),
    val coolDown: List<String> = emptyList(),
    val postWorkoutRecommendations: List<String> = emptyList()
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
    val repsText: String = "",
    val weightText: String = "",
    val rpeText: String = "",
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
    val isExpanded: Boolean,
    val prescribedSets: Int = 0,
    val prescribedRepsRange: String = "",
    val prescribedRpe: String = "",
    val suggestedWeightText: String = "",
    val progressionNote: String = ""
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
    val muscleGroups: String,
    val totalVolumeText: String = ""
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
    val notesInput: String = "",
    val isTimedActivity: Boolean = false,
    val durationSeconds: Int? = null
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
    val recoveryActivities: List<RecoveryActivityDetailUiState> = emptyList(),
    val totalVolumeText: String = "",
    val totalSetsText: String = "",
    val avgRpeText: String = ""
)
