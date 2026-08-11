package com.dailyhealthcoach.ui.premium

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dailyhealthcoach.domain.model.WorkoutStatus
import com.dailyhealthcoach.ui.theme.AccentBlue
import com.dailyhealthcoach.ui.theme.CyanAccent
import com.dailyhealthcoach.ui.theme.MainCard
import com.dailyhealthcoach.ui.theme.MutedControl
import com.dailyhealthcoach.ui.theme.MutedText
import com.dailyhealthcoach.ui.theme.PositiveAccent
import com.dailyhealthcoach.ui.theme.PrimaryText
import com.dailyhealthcoach.ui.theme.SecondaryCard
import com.dailyhealthcoach.ui.theme.WarningAccent
import com.dailyhealthcoach.ui.nutrition.FoodEntryFormUiState
import com.dailyhealthcoach.ui.nutrition.FoodEntryUiState
import com.dailyhealthcoach.ui.nutrition.MealSectionUiState
import com.dailyhealthcoach.ui.nutrition.NutritionUiState
import com.dailyhealthcoach.ui.nutrition.NutritionViewModel
import com.dailyhealthcoach.ui.nutrition.QuickAddFoodUiState
import com.dailyhealthcoach.ui.nutrition.RecipeSuggestionUiState
import com.dailyhealthcoach.ui.nutrition.UsualMealUiState
import androidx.compose.foundation.BorderStroke
import com.dailyhealthcoach.ui.workout.ActivityDraft
import com.dailyhealthcoach.ui.workout.CalendarDayUiState
import com.dailyhealthcoach.ui.workout.DraftWorkoutSetUiState
import com.dailyhealthcoach.ui.workout.ExerciseDetailUiState
import com.dailyhealthcoach.ui.workout.ExerciseOptionUiState
import com.dailyhealthcoach.ui.workout.PersonalRecordUiState
import com.dailyhealthcoach.ui.workout.RecoveryActivityDetailUiState
import com.dailyhealthcoach.ui.workout.SelectedExerciseUiState
import com.dailyhealthcoach.ui.workout.WeeklyLoadUiState
import com.dailyhealthcoach.ui.workout.WorkoutDetailUiState
import com.dailyhealthcoach.ui.workout.WorkoutHistoryUiState
import com.dailyhealthcoach.ui.workout.SuggestedExerciseUiState
import com.dailyhealthcoach.ui.workout.TimerPhase
import com.dailyhealthcoach.ui.workout.TimerState
import com.dailyhealthcoach.ui.workout.WorkoutPlanUiState
import com.dailyhealthcoach.ui.workout.WorkoutUiState
import com.dailyhealthcoach.ui.workout.WorkoutViewModel
import com.dailyhealthcoach.barcode.BarcodeScannerScreen
import com.dailyhealthcoach.barcode.NutritionLabelScannerScreen
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun NutritionRoute(viewModel: NutritionViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var pendingUsualMeal by remember { mutableStateOf<UsualMealUiState?>(null) }

    pendingUsualMeal?.let { meal ->
        MealPickerDialog(
            foodName = meal.foodName,
            onSelect = { selectedMealName ->
                viewModel.logUsualMeal(meal, selectedMealName)
                pendingUsualMeal = null
            },
            onDismiss = { pendingUsualMeal = null }
        )
    }

    when {
        uiState.isScannerVisible -> BarcodeScannerScreen(
            onBarcodeDetected = viewModel::onBarcodeDetected,
            onBack = viewModel::hideScanner
        )
        uiState.isLabelScannerVisible -> NutritionLabelScannerScreen(
            onTextRecognized = viewModel::onLabelOcrText,
            onBack = viewModel::hideLabelScanner
        )
        else -> NutritionPlanScreen(
            uiState = uiState,
            onAddFood = viewModel::showAddForm,
            onScanBarcode = viewModel::showScanner,
            onScanLabel = viewModel::showLabelScanner,
            onAiLog = viewModel::showAiLog,
            onHideAiLog = viewModel::hideAiLog,
            onAiInputChange = viewModel::onAiInputChange,
            onSubmitAiLog = viewModel::submitAiLog,
            onLogUsualMeal = { meal -> pendingUsualMeal = meal },
            onCancelForm = viewModel::hideForm,
            onSaveForm = viewModel::saveForm,
            onEditEntry = viewModel::editEntry,
            onDeleteEntry = viewModel::deleteEntry,
            onFormChange = viewModel::updateForm,
            onToggleSaved = { id, saved -> viewModel.toggleSaved(id, saved) },
            onQuickAddFood = viewModel::showQuickAddDialog,
            onDismissQuickAdd = viewModel::hideQuickAddDialog,
            onConfirmQuickAdd = viewModel::confirmQuickAdd,
            onQuickAddMealChange = viewModel::updateQuickAddMealName,
            onQuickAddQuantityChange = viewModel::updateQuickAddQuantity,
            onQuickAddTimeChange = viewModel::updateQuickAddTime,
            onCopyYesterday = viewModel::copyYesterday
        )
    }
}

@Composable
fun PremiumWorkoutRoute(viewModel: WorkoutViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    PremiumWorkoutScreen(
        uiState = uiState,
        onStartWorkout = {
            val plan = uiState.workoutPlan
            when {
                plan != null && !plan.isStrengthDay -> viewModel.startNonStrengthSession(plan.focus, plan.nonStrengthActivities)
                plan != null && plan.isStrengthDay -> viewModel.startWorkoutWithPlan(
                    suggestedExercises = plan.suggestedExercises,
                    warmUpNames = plan.warmUp,
                    coolDownNames = plan.coolDown
                )
                else -> viewModel.startWorkout()
            }
        },
        onStartWorkoutWithPlan = { _ ->
            uiState.workoutPlan?.let { plan ->
                viewModel.startWorkoutWithPlan(
                    suggestedExercises = plan.suggestedExercises,
                    warmUpNames = plan.warmUp,
                    coolDownNames = plan.coolDown
                )
            }
        },
        onBackToPlan = viewModel::closeActiveWorkout,
        onWorkoutSelected = viewModel::selectWorkout,
        onClearWorkout = viewModel::clearSelectedWorkout,
        onWorkoutNameChange = viewModel::updateWorkoutName,
        onDurationChange = viewModel::updateDuration,
        onOverallRpeChange = viewModel::updateOverallRpe,
        onWorkoutNotesChange = viewModel::updateWorkoutNotes,
        onStatusSelected = viewModel::selectStatus,
        onExerciseSelected = viewModel::addExerciseToWorkout,
        onExerciseExpandedToggle = viewModel::toggleExerciseExpanded,
        onSetRepsChange = viewModel::updateSetReps,
        onSetWeightChange = viewModel::updateSetWeight,
        onSetRpeChange = viewModel::updateSetRpe,
        onSetNotesChange = viewModel::updateSetNotes,
        onExerciseNotesChange = viewModel::updateExerciseNotes,
        onAddSet = viewModel::addSet,
        onRemoveSet = viewModel::removeSet,
        onSetInlineRepsChange = viewModel::updateSetFieldReps,
        onSetInlineWeightChange = viewModel::updateSetFieldWeight,
        onSetInlineRpeChange = viewModel::updateSetFieldRpe,
        onSaveWorkout = viewModel::saveWorkout,
        onSaveRecoverySession = viewModel::saveRecoverySession,
        onDismissPr = viewModel::dismissPrCelebration,
        onNavigateCalendarWeek = viewModel::navigateCalendarWeek,
        onWarmUpDraftChange = viewModel::updateWarmUpDraft,
        onCoolDownDraftChange = viewModel::updateCoolDownDraft,
        onNonStrengthDraftChange = viewModel::updateNonStrengthDraft,
        onNonStrengthNotesChange = viewModel::updateNonStrengthNotes,
        onTimerStart = viewModel::startTimer,
        onTimerPause = viewModel::pauseTimer,
        onTimerResume = viewModel::resumeTimer,
        onTimerSkip = viewModel::skipTimer,
        onStartWorkoutAnyway = { viewModel.startWorkout() },
        onStartEditWorkout = viewModel::startEditWorkout,
        onSaveEditedWorkout = viewModel::saveEditedWorkout,
        onCancelEditWorkout = viewModel::cancelEditWorkout,
        onRequestDeleteWorkout = viewModel::requestDeleteWorkout,
        onCancelDeleteWorkout = viewModel::cancelDeleteWorkout,
        onConfirmDeleteWorkout = viewModel::confirmDeleteWorkout,
        onResumeWorkout = viewModel::resumeWorkout
    )
}

@Composable
private fun PremiumWorkoutScreen(
    uiState: WorkoutUiState,
    onStartWorkout: () -> Unit,
    onStartWorkoutWithPlan: (List<Long>) -> Unit,
    onBackToPlan: () -> Unit,
    onWorkoutSelected: (Long) -> Unit,
    onClearWorkout: () -> Unit,
    onWorkoutNameChange: (String) -> Unit,
    onDurationChange: (String) -> Unit,
    onOverallRpeChange: (String) -> Unit,
    onWorkoutNotesChange: (String) -> Unit,
    onStatusSelected: (WorkoutStatus) -> Unit,
    onExerciseSelected: (Long) -> Unit,
    onExerciseExpandedToggle: (Long) -> Unit,
    onSetRepsChange: (Long, String) -> Unit,
    onSetWeightChange: (Long, String) -> Unit,
    onSetRpeChange: (Long, String) -> Unit,
    onSetNotesChange: (Long, String) -> Unit,
    onExerciseNotesChange: (Long, String) -> Unit,
    onAddSet: (Long) -> Unit,
    onRemoveSet: (Long, Int) -> Unit,
    onSetInlineRepsChange: (Long, Int, String) -> Unit,
    onSetInlineWeightChange: (Long, Int, String) -> Unit,
    onSetInlineRpeChange: (Long, Int, String) -> Unit,
    onSaveWorkout: () -> Unit,
    onSaveRecoverySession: () -> Unit,
    onDismissPr: () -> Unit,
    onNavigateCalendarWeek: (Int) -> Unit = {},
    onWarmUpDraftChange: (Int, ActivityDraft) -> Unit = { _, _ -> },
    onCoolDownDraftChange: (Int, ActivityDraft) -> Unit = { _, _ -> },
    onNonStrengthDraftChange: (Int, ActivityDraft) -> Unit = { _, _ -> },
    onNonStrengthNotesChange: (String) -> Unit = {},
    onTimerStart: (Int) -> Unit = {},
    onTimerPause: () -> Unit = {},
    onTimerResume: () -> Unit = {},
    onTimerSkip: () -> Unit = {},
    onStartWorkoutAnyway: () -> Unit = {},
    onStartEditWorkout: (Long) -> Unit = {},
    onSaveEditedWorkout: () -> Unit = {},
    onCancelEditWorkout: () -> Unit = {},
    onRequestDeleteWorkout: () -> Unit = {},
    onCancelDeleteWorkout: () -> Unit = {},
    onConfirmDeleteWorkout: () -> Unit = {},
    onResumeWorkout: (Long) -> Unit = {}
) {
    Column(modifier = Modifier.padding(bottom = 40.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
        when {
            uiState.isWorkoutStarted -> ActiveWorkoutScreen(
                uiState = uiState,
                onBackToPlan = onBackToPlan,
                onWorkoutNameChange = onWorkoutNameChange,
                onDurationChange = onDurationChange,
                onOverallRpeChange = onOverallRpeChange,
                onWorkoutNotesChange = onWorkoutNotesChange,
                onStatusSelected = onStatusSelected,
                onExerciseSelected = onExerciseSelected,
                onExerciseExpandedToggle = onExerciseExpandedToggle,
                onSetRepsChange = onSetRepsChange,
                onSetWeightChange = onSetWeightChange,
                onSetRpeChange = onSetRpeChange,
                onSetNotesChange = onSetNotesChange,
                onExerciseNotesChange = onExerciseNotesChange,
                onAddSet = onAddSet,
                onRemoveSet = onRemoveSet,
                onSetInlineRepsChange = onSetInlineRepsChange,
                onSetInlineWeightChange = onSetInlineWeightChange,
                onSetInlineRpeChange = onSetInlineRpeChange,
                onSaveWorkout = onSaveWorkout,
                onWarmUpDraftChange = onWarmUpDraftChange,
                onCoolDownDraftChange = onCoolDownDraftChange,
                onTimerStart = onTimerStart,
                onTimerPause = onTimerPause,
                onTimerResume = onTimerResume,
                onTimerSkip = onTimerSkip
            )
            uiState.isNonStrengthSessionStarted -> PlanSessionScreen(
                uiState = uiState,
                onBack = onBackToPlan,
                onSaveSession = onSaveRecoverySession,
                onActivityDraftChange = onNonStrengthDraftChange,
                onNotesChange = onNonStrengthNotesChange,
                onTimerStart = onTimerStart,
                onTimerPause = onTimerPause,
                onTimerResume = onTimerResume,
                onTimerSkip = onTimerSkip
            )
            uiState.editingWorkoutId != null -> WorkoutEditView(
                uiState = uiState,
                onBack = onCancelEditWorkout,
                onSave = onSaveEditedWorkout,
                onWorkoutNameChange = onWorkoutNameChange,
                onDurationChange = onDurationChange,
                onOverallRpeChange = onOverallRpeChange,
                onWorkoutNotesChange = onWorkoutNotesChange,
                onStatusSelected = onStatusSelected,
                onExerciseExpandedToggle = onExerciseExpandedToggle,
                onSetRepsChange = onSetRepsChange,
                onSetWeightChange = onSetWeightChange,
                onSetRpeChange = onSetRpeChange,
                onSetNotesChange = onSetNotesChange,
                onExerciseNotesChange = onExerciseNotesChange,
                onAddSet = onAddSet,
                onRemoveSet = onRemoveSet,
                onSetInlineRepsChange = onSetInlineRepsChange,
                onSetInlineWeightChange = onSetInlineWeightChange,
                onSetInlineRpeChange = onSetInlineRpeChange
            )
            uiState.selectedWorkoutDetail != null -> WorkoutDetailView(
                detail = uiState.selectedWorkoutDetail,
                onBack = onClearWorkout,
                onEdit = { onStartEditWorkout(uiState.selectedWorkoutDetail.id) },
                onDelete = onRequestDeleteWorkout,
                onContinueWorkout = { onResumeWorkout(uiState.selectedWorkoutDetail.id) },
                isDeleteConfirmShowing = uiState.isDeleteConfirmShowing,
                onCancelDelete = onCancelDeleteWorkout,
                onConfirmDelete = onConfirmDeleteWorkout
            )
            else -> {
                if (uiState.newPrAchievements.isNotEmpty()) {
                    PrCelebrationBanner(
                        achievements = uiState.newPrAchievements,
                        onDismiss = onDismissPr
                    )
                }
                if (uiState.calendarDays.isNotEmpty()) {
                    WorkoutCalendarCard(
                        calendarDays = uiState.calendarDays,
                        hasWorkoutTodayCompleted = uiState.hasWorkoutTodayCompleted,
                        weekOffset = uiState.selectedWeekOffset,
                        weekLabel = uiState.calendarWeekLabel,
                        onDayTapped = { day -> day.completedWorkoutId?.let { onWorkoutSelected(it) } },
                        onStartTodayWorkout = onStartWorkout,
                        onNavigateWeek = onNavigateCalendarWeek
                    )
                }
                PremiumWorkoutPlanCard(
                    workoutPlan = uiState.workoutPlan,
                    hasWorkoutTodayCompleted = uiState.hasWorkoutTodayCompleted,
                    onStartWorkout = onStartWorkout,
                    onStartWorkoutWithPlan = onStartWorkoutWithPlan,
                    onStartWorkoutAnyway = onStartWorkoutAnyway
                )
                WorkoutHistoryCard(workouts = uiState.recentWorkouts, onWorkoutSelected = onWorkoutSelected)
                if (uiState.personalRecords.isNotEmpty()) {
                    PersonalRecordsCard(records = uiState.personalRecords)
                }
                if (uiState.weeklyLoads.isNotEmpty()) {
                    WeeklyLoadSection(loads = uiState.weeklyLoads)
                }
            }
        }
    }
}

@Composable
private fun PremiumWorkoutPlanCard(
    workoutPlan: WorkoutPlanUiState?,
    hasWorkoutTodayCompleted: Boolean,
    onStartWorkout: () -> Unit,
    onStartWorkoutWithPlan: (List<Long>) -> Unit,
    onStartWorkoutAnyway: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        MainFeatureCard(modifier = Modifier.padding(top = 22.dp)) {
            Column(
                modifier = Modifier.padding(top = 14.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (workoutPlan == null) {
                    Text(text = "Next workout", color = MutedText, style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = "Log today's recovery to unlock your plan",
                        color = PrimaryText,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                    PlaceholderVisual(label = "Muscle\nfocus", size = 168.dp)
                    PrimaryBlueButton(text = "Start Workout", onClick = onStartWorkout, modifier = Modifier.fillMaxWidth())
                } else {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Today's plan", color = MutedText, style = MaterialTheme.typography.bodyMedium)
                        val dayBadgeColor = if (workoutPlan.isStrengthDay) AccentBlue else PositiveAccent
                        Surface(
                            shape = RoundedCornerShape(999.dp),
                            color = dayBadgeColor.copy(alpha = 0.18f)
                        ) {
                            Text(
                                text = if (workoutPlan.isStrengthDay) "STRENGTH" else "RECOVERY",
                                color = dayBadgeColor,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }
                    Text(
                        text = workoutPlan.focus,
                        color = PrimaryText,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .background(SecondaryCard.copy(alpha = 0.6f))
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        workoutPlan.reasons.forEach { reason ->
                            Text(
                                text = "· $reason",
                                color = MutedText,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    if (workoutPlan.isStrengthDay) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                            StatTile(label = "Duration", value = "${workoutPlan.durationMinutes} min", modifier = Modifier.weight(1f))
                            StatTile(label = "Exercises", value = "${workoutPlan.suggestedExercises.size}", modifier = Modifier.weight(1f))
                        }
                        Surface(shape = RoundedCornerShape(14.dp), color = SecondaryCard.copy(alpha = 0.75f)) {
                            Text(
                                text = "${workoutPlan.setsPerExercise} sets · ${workoutPlan.repsRange} reps · RPE ${workoutPlan.rpeTarget}",
                                color = CyanAccent,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp)
                            )
                        }
                        if (workoutPlan.warmUp.isNotEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(SecondaryCard.copy(alpha = 0.55f))
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text("Warm-up", color = CyanAccent, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                                workoutPlan.warmUp.forEach { item ->
                                    Text("· $item", color = MutedText, style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(18.dp))
                                .background(SecondaryCard.copy(alpha = 0.45f))
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            workoutPlan.suggestedExercises.forEach { ex ->
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(
                                            modifier = Modifier.weight(1f),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier.size(6.dp).clip(CircleShape).background(muscleGroupColor(ex.muscleGroup))
                                            )
                                            Text(text = ex.name, color = PrimaryText, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                                        }
                                        Surface(
                                            shape = RoundedCornerShape(999.dp),
                                            color = muscleGroupColor(ex.muscleGroup).copy(alpha = 0.15f)
                                        ) {
                                            Text(
                                                text = ex.muscleGroup,
                                                color = muscleGroupColor(ex.muscleGroup),
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Medium,
                                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                                            )
                                        }
                                    }
                                    if (ex.progressionNote.isNotBlank()) {
                                        Text(
                                            text = ex.progressionNote,
                                            color = CyanAccent,
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                }
                            }
                        }
                        if (workoutPlan.coolDown.isNotEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(SecondaryCard.copy(alpha = 0.55f))
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text("Cool-down", color = PositiveAccent, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                                workoutPlan.coolDown.forEach { item ->
                                    Text("· $item", color = MutedText, style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                        if (hasWorkoutTodayCompleted) {
                            Surface(
                                shape = RoundedCornerShape(999.dp),
                                color = PositiveAccent.copy(alpha = 0.15f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Today's workout complete",
                                    color = PositiveAccent,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp)
                                )
                            }
                            if (workoutPlan.postWorkoutRecommendations.isNotEmpty()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(SecondaryCard.copy(alpha = 0.55f))
                                        .padding(horizontal = 14.dp, vertical = 10.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text("Post-workout", color = WarningAccent, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                                    workoutPlan.postWorkoutRecommendations.forEach { item ->
                                        Text("· $item", color = MutedText, style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                            }
                            OutlinedButton(
                                onClick = { onStartWorkoutWithPlan(workoutPlan.suggestedExercises.map { it.exerciseId }) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(999.dp)
                            ) {
                                Text("Start Another Workout", color = MutedText)
                            }
                        } else {
                            PrimaryBlueButton(
                                text = "Start Workout",
                                onClick = { onStartWorkoutWithPlan(workoutPlan.suggestedExercises.map { it.exerciseId }) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    } else {
                        StatTile(label = "Duration", value = "${workoutPlan.durationMinutes} min", modifier = Modifier.fillMaxWidth())
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(18.dp))
                                .background(SecondaryCard.copy(alpha = 0.6f))
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            workoutPlan.nonStrengthActivities.forEach { activity ->
                                Text(text = "· $activity", color = MutedText, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                        if (workoutPlan.focus == "Rest Day") {
                            PrimaryBlueButton(text = "Log Rest Day", onClick = onStartWorkout, modifier = Modifier.fillMaxWidth())
                            OutlinedButton(
                                onClick = onStartWorkoutAnyway,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(999.dp)
                            ) {
                                Text("Start Workout Anyway", color = CyanAccent)
                            }
                        } else {
                            val nonStrengthLabel = when (workoutPlan.focus) {
                                "Active Recovery" -> "Start Recovery"
                                "Walking & Mobility" -> "Start Mobility"
                                else -> "Start Workout"
                            }
                            PrimaryBlueButton(text = nonStrengthLabel, onClick = onStartWorkout, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        }
        FloatingTitlePill(text = "Workout Plan", modifier = Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun ActiveWorkoutScreen(
    uiState: WorkoutUiState,
    onBackToPlan: () -> Unit,
    onWorkoutNameChange: (String) -> Unit,
    onDurationChange: (String) -> Unit,
    onOverallRpeChange: (String) -> Unit,
    onWorkoutNotesChange: (String) -> Unit,
    onStatusSelected: (WorkoutStatus) -> Unit,
    onExerciseSelected: (Long) -> Unit,
    onExerciseExpandedToggle: (Long) -> Unit,
    onSetRepsChange: (Long, String) -> Unit,
    onSetWeightChange: (Long, String) -> Unit,
    onSetRpeChange: (Long, String) -> Unit,
    onSetNotesChange: (Long, String) -> Unit,
    onExerciseNotesChange: (Long, String) -> Unit,
    onAddSet: (Long) -> Unit,
    onRemoveSet: (Long, Int) -> Unit,
    onSetInlineRepsChange: (Long, Int, String) -> Unit,
    onSetInlineWeightChange: (Long, Int, String) -> Unit,
    onSetInlineRpeChange: (Long, Int, String) -> Unit,
    onSaveWorkout: () -> Unit,
    onWarmUpDraftChange: (Int, ActivityDraft) -> Unit,
    onCoolDownDraftChange: (Int, ActivityDraft) -> Unit,
    onTimerStart: (Int) -> Unit,
    onTimerPause: () -> Unit,
    onTimerResume: () -> Unit,
    onTimerSkip: () -> Unit
) {
    val warmUpDrafts = uiState.warmUpDrafts
    val coolDownDrafts = uiState.coolDownDrafts
    Box(modifier = Modifier.fillMaxWidth()) {
        MainFeatureCard(modifier = Modifier.padding(top = 22.dp)) {
            Column(
                modifier = Modifier.padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                TextButton(onClick = onBackToPlan) {
                    Text(text = "Back", color = CyanAccent, fontWeight = FontWeight.Bold)
                }
                Text(
                    text = "Active Workout",
                    color = PrimaryText,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            OutlinedTextField(
                value = uiState.workoutName,
                onValueChange = onWorkoutNameChange,
                label = { Text("Workout name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = uiState.durationMinutes,
                    onValueChange = onDurationChange,
                    label = { Text("Min") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = uiState.overallRpe,
                    onValueChange = onOverallRpeChange,
                    label = { Text("RPE 1–10") },
                    singleLine = true,
                    isError = uiState.overallRpeError != null,
                    supportingText = uiState.overallRpeError?.let { err ->
                        { Text(err, color = WarningAccent, style = MaterialTheme.typography.bodySmall) }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            WorkoutStatusChips(selectedStatus = uiState.selectedStatus, onStatusSelected = onStatusSelected)
            if (warmUpDrafts.isNotEmpty()) {
                Text("Warm-up", color = CyanAccent, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                warmUpDrafts.forEachIndexed { index, draft ->
                    ActivityDraftCard(
                        draft = draft,
                        cardIndex = index,
                        activeTimer = uiState.activeTimer,
                        onStatusSelected = { status -> onWarmUpDraftChange(index, draft.copy(status = status)) },
                        onDurationChange = { value -> onWarmUpDraftChange(index, draft.copy(durationInput = value.filter { c -> c.isDigit() })) },
                        onRpeChange = { value -> onWarmUpDraftChange(index, draft.copy(rpeInput = value.filter { c -> c.isDigit() }.take(2))) },
                        onNotesChange = { value -> onWarmUpDraftChange(index, draft.copy(notesInput = value)) },
                        onTimerStart = { onTimerStart(index) },
                        onTimerPause = onTimerPause,
                        onTimerResume = onTimerResume,
                        onTimerSkip = onTimerSkip
                    )
                }
            }
            ActiveExerciseSection(
                exercises = uiState.exercises,
                selectedExercises = uiState.selectedExercises,
                onExerciseSelected = onExerciseSelected,
                onExerciseExpandedToggle = onExerciseExpandedToggle,
                onSetRepsChange = onSetRepsChange,
                onSetWeightChange = onSetWeightChange,
                onSetRpeChange = onSetRpeChange,
                onSetNotesChange = onSetNotesChange,
                onExerciseNotesChange = onExerciseNotesChange,
                onAddSet = onAddSet,
                onRemoveSet = onRemoveSet,
                onSetInlineRepsChange = onSetInlineRepsChange,
                onSetInlineWeightChange = onSetInlineWeightChange,
                onSetInlineRpeChange = onSetInlineRpeChange
            )
            if (coolDownDrafts.isNotEmpty()) {
                Text("Cool-down", color = PositiveAccent, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                coolDownDrafts.forEachIndexed { index, draft ->
                    val globalIndex = warmUpDrafts.size + uiState.selectedExercises.size + index
                    ActivityDraftCard(
                        draft = draft,
                        cardIndex = globalIndex,
                        activeTimer = uiState.activeTimer,
                        onStatusSelected = { status -> onCoolDownDraftChange(index, draft.copy(status = status)) },
                        onDurationChange = { value -> onCoolDownDraftChange(index, draft.copy(durationInput = value.filter { c -> c.isDigit() })) },
                        onRpeChange = { value -> onCoolDownDraftChange(index, draft.copy(rpeInput = value.filter { c -> c.isDigit() }.take(2))) },
                        onNotesChange = { value -> onCoolDownDraftChange(index, draft.copy(notesInput = value)) },
                        onTimerStart = { onTimerStart(globalIndex) },
                        onTimerPause = onTimerPause,
                        onTimerResume = onTimerResume,
                        onTimerSkip = onTimerSkip
                    )
                }
            }
            OutlinedTextField(
                value = uiState.workoutNotes,
                onValueChange = onWorkoutNotesChange,
                label = { Text("Overall workout notes") },
                minLines = 2,
                modifier = Modifier.fillMaxWidth()
            )
            PrimaryBlueButton(text = "Save Workout", onClick = onSaveWorkout, modifier = Modifier.fillMaxWidth())
        }
    }
        FloatingTitlePill(text = "Active Workout", modifier = Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun PlanSessionScreen(
    uiState: WorkoutUiState,
    onBack: () -> Unit,
    onSaveSession: () -> Unit,
    onActivityDraftChange: (Int, ActivityDraft) -> Unit,
    onNotesChange: (String) -> Unit,
    onTimerStart: (Int) -> Unit,
    onTimerPause: () -> Unit,
    onTimerResume: () -> Unit,
    onTimerSkip: () -> Unit
) {
    val sessionTitle = when (uiState.workoutPlan?.focus) {
        "Active Recovery" -> "Recovery Session"
        "Walking & Mobility" -> "Mobility Session"
        "Rest Day" -> "Rest Day Log"
        else -> uiState.workoutPlan?.focus ?: "Session"
    }
    val activityDrafts = uiState.nonStrengthDrafts
    val overallNotes = uiState.nonStrengthOverallNotes

    Box(modifier = Modifier.fillMaxWidth()) {
        MainFeatureCard(modifier = Modifier.padding(top = 22.dp)) {
            Column(
                modifier = Modifier.padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    TextButton(onClick = onBack) {
                        Text(text = "Back", color = CyanAccent, fontWeight = FontWeight.Bold)
                    }
                    Text(
                        text = sessionTitle,
                        color = PrimaryText,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                activityDrafts.forEachIndexed { index, draft ->
                    ActivityDraftCard(
                        draft = draft,
                        cardIndex = index,
                        activeTimer = uiState.activeTimer,
                        onStatusSelected = { status -> onActivityDraftChange(index, draft.copy(status = status)) },
                        onDurationChange = { value -> onActivityDraftChange(index, draft.copy(durationInput = value.filter { c -> c.isDigit() })) },
                        onRpeChange = { value -> onActivityDraftChange(index, draft.copy(rpeInput = value.filter { c -> c.isDigit() }.take(2))) },
                        onNotesChange = { value -> onActivityDraftChange(index, draft.copy(notesInput = value)) },
                        onTimerStart = { onTimerStart(index) },
                        onTimerPause = onTimerPause,
                        onTimerResume = onTimerResume,
                        onTimerSkip = onTimerSkip
                    )
                }
                OutlinedTextField(
                    value = overallNotes,
                    onValueChange = onNotesChange,
                    label = { Text("Overall notes (optional)") },
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )
                PrimaryBlueButton(
                    text = "Save Session",
                    onClick = onSaveSession,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        FloatingTitlePill(text = sessionTitle, modifier = Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun ActivityDraftCard(
    draft: ActivityDraft,
    cardIndex: Int = 0,
    activeTimer: TimerState? = null,
    onStatusSelected: (WorkoutStatus) -> Unit,
    onDurationChange: (String) -> Unit,
    onRpeChange: (String) -> Unit,
    onNotesChange: (String) -> Unit,
    onTimerStart: () -> Unit = {},
    onTimerPause: () -> Unit = {},
    onTimerResume: () -> Unit = {},
    onTimerSkip: () -> Unit = {}
) {
    val statusColor = when (draft.status) {
        WorkoutStatus.NOT_STARTED -> MutedText
        WorkoutStatus.COMPLETED -> PositiveAccent
        WorkoutStatus.PARTIAL -> WarningAccent
        WorkoutStatus.SKIPPED -> MutedText
    }
    val isThisTimerActive = activeTimer?.activityIndex == cardIndex
    val isRunning = isThisTimerActive && activeTimer?.phase == TimerPhase.RUNNING
    val isPaused = isThisTimerActive && activeTimer?.phase == TimerPhase.PAUSED
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = SecondaryCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = draft.name,
                    color = PrimaryText,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = statusColor.copy(alpha = 0.18f)
                ) {
                    Text(
                        text = draft.status.label,
                        color = statusColor,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                WorkoutStatus.entries.filter { it != WorkoutStatus.NOT_STARTED }.forEach { status ->
                    FilterChip(
                        selected = draft.status == status,
                        onClick = { onStatusSelected(status) },
                        shape = RoundedCornerShape(999.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = when (status) {
                                WorkoutStatus.COMPLETED -> PositiveAccent
                                WorkoutStatus.PARTIAL -> WarningAccent
                                WorkoutStatus.SKIPPED -> MutedControl
                                else -> MutedControl
                            },
                            selectedLabelColor = PrimaryText,
                            labelColor = MutedText
                        ),
                        label = { Text(status.label, maxLines = 1) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            if (draft.isTimedActivity) {
                val displaySeconds = if (isThisTimerActive) activeTimer?.remainingSeconds ?: draft.durationSeconds ?: 30
                                     else draft.durationSeconds ?: 30
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = formatTimerSeconds(displaySeconds),
                        color = if (isRunning) CyanAccent else PrimaryText,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        when {
                            isRunning -> {
                                OutlinedButton(onClick = onTimerPause) { Text("Pause") }
                                OutlinedButton(onClick = onTimerSkip) { Text("Skip") }
                            }
                            isPaused -> {
                                androidx.compose.material3.Button(
                                    onClick = onTimerResume,
                                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = CyanAccent)
                                ) { Text("Resume") }
                                OutlinedButton(onClick = onTimerSkip) { Text("Skip") }
                            }
                            else -> {
                                androidx.compose.material3.Button(
                                    onClick = onTimerStart,
                                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = CyanAccent)
                                ) { Text("▶ Start") }
                            }
                        }
                    }
                }
            } else {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = draft.durationInput,
                        onValueChange = onDurationChange,
                        label = { Text("Duration (min)") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = draft.rpeInput,
                        onValueChange = onRpeChange,
                        label = { Text("Effort (RPE)") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            if (draft.isTimedActivity) {
                OutlinedTextField(
                    value = draft.rpeInput,
                    onValueChange = onRpeChange,
                    label = { Text("Effort (RPE)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            OutlinedTextField(
                value = draft.notesInput,
                onValueChange = onNotesChange,
                label = { Text("Notes") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

private fun formatTimerSeconds(seconds: Int): String {
    val mins = seconds / 60
    val secs = seconds % 60
    return if (mins > 0) "$mins:${"%02d".format(secs)}" else "${secs}s"
}

@Composable
private fun WorkoutStatusChips(
    selectedStatus: WorkoutStatus,
    onStatusSelected: (WorkoutStatus) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        WorkoutStatus.entries.filter { it != WorkoutStatus.NOT_STARTED }.forEach { status ->
            FilterChip(
                selected = selectedStatus == status,
                onClick = { onStatusSelected(status) },
                shape = RoundedCornerShape(999.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = when (status) {
                        WorkoutStatus.COMPLETED -> PositiveAccent
                        WorkoutStatus.PARTIAL -> WarningAccent
                        WorkoutStatus.SKIPPED -> MutedControl
                        else -> MutedControl
                    },
                    selectedLabelColor = PrimaryText,
                    labelColor = MutedText
                ),
                label = { Text(status.label, maxLines = 1) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ActiveExerciseSection(
    exercises: List<ExerciseOptionUiState>,
    selectedExercises: List<SelectedExerciseUiState>,
    onExerciseSelected: (Long) -> Unit,
    onExerciseExpandedToggle: (Long) -> Unit,
    onSetRepsChange: (Long, String) -> Unit,
    onSetWeightChange: (Long, String) -> Unit,
    onSetRpeChange: (Long, String) -> Unit,
    onSetNotesChange: (Long, String) -> Unit,
    onExerciseNotesChange: (Long, String) -> Unit,
    onAddSet: (Long) -> Unit,
    onRemoveSet: (Long, Int) -> Unit,
    onSetInlineRepsChange: (Long, Int, String) -> Unit,
    onSetInlineWeightChange: (Long, Int, String) -> Unit,
    onSetInlineRpeChange: (Long, Int, String) -> Unit
) {
    var showLibrary by remember { mutableStateOf(false) }
    val selectedIds = selectedExercises.map { it.exerciseId }.toSet()
    val unselectedExercises = exercises.filter { it.id !in selectedIds }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        if (selectedExercises.isEmpty()) {
            Text("No exercises loaded. Use Add Exercise below.", color = MutedText, style = MaterialTheme.typography.bodySmall)
        }
        selectedExercises.forEach { exercise ->
            ExerciseCard(
                exercise = exercise,
                onExerciseSelected = onExerciseSelected,
                onExerciseExpandedToggle = onExerciseExpandedToggle,
                onSetRepsChange = onSetRepsChange,
                onSetWeightChange = onSetWeightChange,
                onSetRpeChange = onSetRpeChange,
                onSetNotesChange = onSetNotesChange,
                onExerciseNotesChange = onExerciseNotesChange,
                onAddSet = onAddSet,
                onRemoveSet = onRemoveSet,
                onSetInlineRepsChange = onSetInlineRepsChange,
                onSetInlineWeightChange = onSetInlineWeightChange,
                onSetInlineRpeChange = onSetInlineRpeChange
            )
        }
        OutlinedButton(
            onClick = { showLibrary = !showLibrary },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = CyanAccent)
        ) {
            Text(if (showLibrary) "Hide Library" else "+ Add Exercise")
        }
        if (showLibrary) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Exercise Library",
                    color = MutedText,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
                unselectedExercises.forEach { exercise ->
                    CollapsedExerciseRow(
                        exercise = exercise,
                        setCount = 0,
                        onClick = {
                            onExerciseSelected(exercise.id)
                            showLibrary = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ExercisePicker(
    exercises: List<ExerciseOptionUiState>,
    selectedExercises: List<SelectedExerciseUiState>,
    onExerciseSelected: (Long) -> Unit,
    onExerciseExpandedToggle: (Long) -> Unit,
    onSetRepsChange: (Long, String) -> Unit,
    onSetWeightChange: (Long, String) -> Unit,
    onSetRpeChange: (Long, String) -> Unit,
    onSetNotesChange: (Long, String) -> Unit,
    onExerciseNotesChange: (Long, String) -> Unit,
    onAddSet: (Long) -> Unit,
    onRemoveSet: (Long, Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(text = "Exercise library", color = PrimaryText, fontWeight = FontWeight.Bold)
        exercises.forEach { exercise ->
            val selectedExercise = selectedExercises.firstOrNull { it.exerciseId == exercise.id }
            if (selectedExercise == null) {
                CollapsedExerciseRow(
                    exercise = exercise,
                    setCount = 0,
                    onClick = { onExerciseSelected(exercise.id) }
                )
            } else {
                ExerciseCard(
                    exercise = selectedExercise,
                    onExerciseSelected = onExerciseSelected,
                    onExerciseExpandedToggle = onExerciseExpandedToggle,
                    onSetRepsChange = onSetRepsChange,
                    onSetWeightChange = onSetWeightChange,
                    onSetRpeChange = onSetRpeChange,
                    onSetNotesChange = onSetNotesChange,
                    onExerciseNotesChange = onExerciseNotesChange,
                    onAddSet = onAddSet,
                    onRemoveSet = onRemoveSet,
                    onSetInlineRepsChange = { _, _, _ -> },
                    onSetInlineWeightChange = { _, _, _ -> },
                    onSetInlineRpeChange = { _, _, _ -> }
                )
            }
        }
    }
}

@Composable
private fun CollapsedExerciseRow(
    exercise: ExerciseOptionUiState,
    setCount: Int,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = PrimaryText
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = exercise.name, fontWeight = FontWeight.Bold)
                Text(
                    text = "${exercise.muscleGroup} | ${exercise.equipmentType} | ${exercise.movementPattern.toTitleCase()}",
                    color = MutedText,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                text = setCountLabel(setCount),
                color = CyanAccent,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ExerciseCard(
    exercise: SelectedExerciseUiState,
    onExerciseSelected: (Long) -> Unit,
    onExerciseExpandedToggle: (Long) -> Unit,
    onSetRepsChange: (Long, String) -> Unit,
    onSetWeightChange: (Long, String) -> Unit,
    onSetRpeChange: (Long, String) -> Unit,
    onSetNotesChange: (Long, String) -> Unit,
    onExerciseNotesChange: (Long, String) -> Unit,
    onAddSet: (Long) -> Unit,
    onRemoveSet: (Long, Int) -> Unit,
    onSetInlineRepsChange: (Long, Int, String) -> Unit,
    onSetInlineWeightChange: (Long, Int, String) -> Unit,
    onSetInlineRpeChange: (Long, Int, String) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = SecondaryCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .clickable {
                        if (exercise.isExpanded) {
                            onExerciseExpandedToggle(exercise.exerciseId)
                        } else {
                            onExerciseSelected(exercise.exerciseId)
                        }
                    }
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = exercise.name, color = PrimaryText, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(
                        text = "${exercise.muscleGroup} | ${exercise.equipmentType} | ${exercise.movementPattern.toTitleCase()}",
                        color = MutedText,
                        style = MaterialTheme.typography.bodySmall
                    )
                    if (exercise.sets.size < exercise.prescribedSets && exercise.prescribedSets > 0) {
                        val prescriptionText = buildString {
                            append("${exercise.prescribedSets} sets")
                            if (exercise.prescribedRepsRange.isNotBlank()) append(" · ${exercise.prescribedRepsRange} reps")
                            if (exercise.prescribedRpe.isNotBlank()) append(" · RPE ${exercise.prescribedRpe}")
                        }.trim()
                        if (prescriptionText.isNotBlank()) {
                            Text(
                                text = prescriptionText,
                                color = CyanAccent,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
                Text(
                    text = if (exercise.prescribedSets > 0) "${exercise.sets.size} / ${exercise.prescribedSets} sets" else setCountLabel(exercise.sets.size),
                    color = CyanAccent,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
            if (exercise.isExpanded) {
                if (exercise.prescribedSets > 0 && exercise.progressionNote.isNotBlank()) {
                    Text(
                        text = exercise.progressionNote,
                        color = CyanAccent,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                exercise.sets.forEach { set ->
                    SetRow(
                        set = set,
                        onRepsChange = { v -> onSetInlineRepsChange(exercise.exerciseId, set.setNumber, v) },
                        onWeightChange = { v -> onSetInlineWeightChange(exercise.exerciseId, set.setNumber, v) },
                        onRpeChange = { v -> onSetInlineRpeChange(exercise.exerciseId, set.setNumber, v) },
                        onRemove = { onRemoveSet(exercise.exerciseId, set.setNumber) }
                    )
                }
                val prescribed = exercise.prescribedSets
                val logged = exercise.sets.size
                if (prescribed == 0 || logged < prescribed) {
                    if (prescribed > 0) {
                        Text(
                            text = "Set ${logged + 1} of $prescribed",
                            color = CyanAccent,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = exercise.repsInput,
                            onValueChange = { onSetRepsChange(exercise.exerciseId, it) },
                            label = { Text("Reps") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = exercise.weightInput,
                            onValueChange = { onSetWeightChange(exercise.exerciseId, it) },
                            label = { Text("Weight") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = exercise.rpeInput,
                            onValueChange = { onSetRpeChange(exercise.exerciseId, it) },
                            label = { Text("RPE") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    OutlinedTextField(
                        value = exercise.setNotesInput,
                        onValueChange = { onSetNotesChange(exercise.exerciseId, it) },
                        label = { Text("Set notes") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    PrimaryBlueButton(
                        text = if (prescribed > 0) "Log Set" else "Add Set",
                        onClick = { onAddSet(exercise.exerciseId) },
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    OutlinedButton(
                        onClick = { onAddSet(exercise.exerciseId) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, MutedControl)
                    ) {
                        Text("+ Add Extra Set", color = MutedText)
                    }
                }
                OutlinedTextField(
                    value = exercise.exerciseNotes,
                    onValueChange = { onExerciseNotesChange(exercise.exerciseId, it) },
                    label = { Text("Exercise notes") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun SetRow(
    set: DraftWorkoutSetUiState,
    onRepsChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onRpeChange: (String) -> Unit,
    onRemove: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(MainCard.copy(alpha = 0.75f))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Set ${set.setNumber}",
                color = PrimaryText,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold
            )
            TextButton(onClick = onRemove, contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)) {
                Text(text = "Remove", color = MutedText, style = MaterialTheme.typography.labelSmall)
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = set.repsText,
                onValueChange = onRepsChange,
                label = { Text("Reps") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = set.weightText,
                onValueChange = onWeightChange,
                label = { Text("Weight") },
                singleLine = true,
                modifier = Modifier.weight(1.5f)
            )
            OutlinedTextField(
                value = set.rpeText,
                onValueChange = onRpeChange,
                label = { Text("RPE") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun WorkoutHistoryCard(
    workouts: List<WorkoutHistoryUiState>,
    onWorkoutSelected: (Long) -> Unit
) {
    MainFeatureCard {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = "Workout History",
                color = PrimaryText,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            if (workouts.isEmpty()) {
                Text(text = "No workouts logged yet.", color = MutedText)
            } else {
                workouts.forEach { workout ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .background(SecondaryCard.copy(alpha = 0.72f))
                            .clickable { onWorkoutSelected(workout.id) }
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = workout.name,
                                color = PrimaryText,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = workout.statusLabel,
                                color = statusColor(workout.statusLabel),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Text(
                            text = buildString {
                                append(workout.date)
                                if (workout.durationText.isNotEmpty()) append(" · ${workout.durationText}")
                                append(" · ${workout.exerciseCount} exercises · ${workout.setCount} sets")
                                if (workout.avgRpe.isNotEmpty()) append(" · ${workout.avgRpe}")
                                if (workout.totalVolumeText.isNotEmpty()) append(" · ${workout.totalVolumeText}")
                            },
                            color = MutedText,
                            style = MaterialTheme.typography.bodySmall
                        )
                        if (workout.muscleGroups.isNotEmpty()) {
                            Text(text = workout.muscleGroups, color = CyanAccent, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WorkoutDetailView(
    detail: WorkoutDetailUiState,
    onBack: () -> Unit,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {},
    onContinueWorkout: () -> Unit = {},
    isDeleteConfirmShowing: Boolean = false,
    onCancelDelete: () -> Unit = {},
    onConfirmDelete: () -> Unit = {}
) {
    if (isDeleteConfirmShowing) {
        AlertDialog(
            onDismissRequest = onCancelDelete,
            title = { Text("Delete workout?") },
            text = { Text("This will permanently delete this workout and all its sets. This cannot be undone.") },
            confirmButton = {
                Button(
                    onClick = onConfirmDelete,
                    colors = ButtonDefaults.buttonColors(containerColor = WarningAccent)
                ) { Text("Delete", color = PrimaryText) }
            },
            dismissButton = {
                TextButton(onClick = onCancelDelete) { Text("Cancel", color = CyanAccent) }
            }
        )
    }
    Box(modifier = Modifier.fillMaxWidth()) {
        MainFeatureCard(modifier = Modifier.padding(top = 22.dp)) {
            Column(
                modifier = Modifier.padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onBack) {
                        Text(text = "< Back", color = CyanAccent, fontWeight = FontWeight.Bold)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                        TextButton(onClick = onEdit) {
                            Text(text = "Edit", color = CyanAccent, fontWeight = FontWeight.SemiBold)
                        }
                        TextButton(onClick = onDelete) {
                            Text(text = "Delete", color = WarningAccent, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
                OutlinedButton(
                    onClick = onContinueWorkout,
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, CyanAccent)
                ) {
                    Text("Continue Workout", color = CyanAccent, fontWeight = FontWeight.SemiBold)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = detail.name, color = PrimaryText, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text(
                        text = detail.statusLabel,
                        color = statusColor(detail.statusLabel),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Text(
                    text = buildString {
                        append(detail.date)
                        if (detail.durationText.isNotEmpty()) append(" · ${detail.durationText}")
                    },
                    color = MutedText,
                    style = MaterialTheme.typography.bodySmall
                )
                val summaryParts = listOfNotNull(
                    detail.totalVolumeText.takeIf { it.isNotEmpty() },
                    detail.totalSetsText.takeIf { it.isNotEmpty() },
                    detail.avgRpeText.takeIf { it.isNotEmpty() }
                )
                if (summaryParts.isNotEmpty()) {
                    Surface(shape = RoundedCornerShape(12.dp), color = SecondaryCard.copy(alpha = 0.5f)) {
                        Text(
                            text = summaryParts.joinToString(" · "),
                            color = CyanAccent,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
                if (!detail.notes.isNullOrBlank()) {
                    Surface(shape = RoundedCornerShape(14.dp), color = SecondaryCard.copy(alpha = 0.6f)) {
                        Text(
                            text = detail.notes,
                            color = MutedText,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
                if (detail.exercises.isNotEmpty()) {
                    detail.exercises.forEach { exercise ->
                        ExerciseDetailSection(exercise = exercise)
                    }
                }
                if (detail.recoveryActivities.isNotEmpty()) {
                    if (detail.exercises.isNotEmpty()) {
                        Text(
                            text = "Warm-up & Cool-down",
                            color = CyanAccent,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    detail.recoveryActivities.forEach { activity ->
                        RecoveryActivityDetailSection(activity = activity)
                    }
                }
                if (detail.exercises.isEmpty() && detail.recoveryActivities.isEmpty()) {
                    Text(text = "No activities logged for this session.", color = MutedText)
                }
            }
        }
        FloatingTitlePill(text = "Workout Details", modifier = Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun WorkoutEditView(
    uiState: WorkoutUiState,
    onBack: () -> Unit,
    onSave: () -> Unit,
    onWorkoutNameChange: (String) -> Unit,
    onDurationChange: (String) -> Unit,
    onOverallRpeChange: (String) -> Unit,
    onWorkoutNotesChange: (String) -> Unit,
    onStatusSelected: (WorkoutStatus) -> Unit,
    onExerciseExpandedToggle: (Long) -> Unit,
    onSetRepsChange: (Long, String) -> Unit,
    onSetWeightChange: (Long, String) -> Unit,
    onSetRpeChange: (Long, String) -> Unit,
    onSetNotesChange: (Long, String) -> Unit,
    onExerciseNotesChange: (Long, String) -> Unit,
    onAddSet: (Long) -> Unit,
    onRemoveSet: (Long, Int) -> Unit,
    onSetInlineRepsChange: (Long, Int, String) -> Unit,
    onSetInlineWeightChange: (Long, Int, String) -> Unit,
    onSetInlineRpeChange: (Long, Int, String) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        MainFeatureCard(modifier = Modifier.padding(top = 22.dp)) {
            Column(
                modifier = Modifier.padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onBack) {
                        Text(text = "< Cancel", color = MutedText, fontWeight = FontWeight.Bold)
                    }
                    TextButton(onClick = onSave) {
                        Text(text = "Save Changes", color = CyanAccent, fontWeight = FontWeight.Bold)
                    }
                }
                OutlinedTextField(
                    value = uiState.workoutName,
                    onValueChange = onWorkoutNameChange,
                    label = { Text("Workout name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = uiState.durationMinutes,
                        onValueChange = onDurationChange,
                        label = { Text("Min") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = uiState.overallRpe,
                        onValueChange = onOverallRpeChange,
                        label = { Text("RPE 1–10") },
                        singleLine = true,
                        isError = uiState.overallRpeError != null,
                        supportingText = uiState.overallRpeError?.let { err ->
                            { Text(err, color = WarningAccent, style = MaterialTheme.typography.bodySmall) }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                WorkoutStatusChips(selectedStatus = uiState.selectedStatus, onStatusSelected = onStatusSelected)
                if (uiState.selectedExercises.isNotEmpty()) {
                    Text("Exercises", color = CyanAccent, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                    uiState.selectedExercises.forEach { exercise ->
                        ExerciseCard(
                            exercise = exercise,
                            onExerciseSelected = { id -> onExerciseExpandedToggle(id) },
                            onExerciseExpandedToggle = onExerciseExpandedToggle,
                            onSetRepsChange = onSetRepsChange,
                            onSetWeightChange = onSetWeightChange,
                            onSetRpeChange = onSetRpeChange,
                            onSetNotesChange = onSetNotesChange,
                            onExerciseNotesChange = onExerciseNotesChange,
                            onAddSet = onAddSet,
                            onRemoveSet = onRemoveSet,
                            onSetInlineRepsChange = onSetInlineRepsChange,
                            onSetInlineWeightChange = onSetInlineWeightChange,
                            onSetInlineRpeChange = onSetInlineRpeChange
                        )
                    }
                }
                OutlinedTextField(
                    value = uiState.workoutNotes,
                    onValueChange = onWorkoutNotesChange,
                    label = { Text("Workout notes") },
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )
                PrimaryBlueButton(text = "Save Changes", onClick = onSave, modifier = Modifier.fillMaxWidth())
            }
        }
        FloatingTitlePill(text = "Edit Workout", modifier = Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun ExerciseDetailSection(exercise: ExerciseDetailUiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(SecondaryCard.copy(alpha = 0.55f))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(text = exercise.exerciseName, color = PrimaryText, fontWeight = FontWeight.SemiBold)
        if (exercise.muscleGroup.isNotEmpty()) {
            Text(text = exercise.muscleGroup, color = CyanAccent, style = MaterialTheme.typography.bodySmall)
        }
        exercise.sets.forEach { set ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = buildString {
                        append("Set ${set.setNumber}:")
                        set.reps?.let { append("  $it reps") }
                        set.weight?.let { append("  ${"%.1f".format(it)} lb") }
                        set.rpe?.let { append("  RPE $it") }
                    },
                    color = PrimaryText,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            if (!set.notes.isNullOrBlank()) {
                Text(text = set.notes, color = MutedText, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun RecoveryActivityDetailSection(activity: RecoveryActivityDetailUiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(SecondaryCard.copy(alpha = 0.55f))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = activity.name, color = PrimaryText, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
            Text(text = activity.statusLabel, color = statusColor(activity.statusLabel), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
        }
        val meta = listOfNotNull(
            activity.durationText.takeIf { it.isNotEmpty() },
            activity.rpe.takeIf { it.isNotEmpty() }
        ).joinToString("  ·  ")
        if (meta.isNotEmpty()) {
            Text(text = meta, color = CyanAccent, style = MaterialTheme.typography.bodySmall)
        }
        if (!activity.notes.isNullOrBlank()) {
            Text(text = activity.notes, color = MutedText, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun WorkoutCalendarCard(
    calendarDays: List<CalendarDayUiState>,
    hasWorkoutTodayCompleted: Boolean,
    weekOffset: Int,
    weekLabel: String,
    onDayTapped: (CalendarDayUiState) -> Unit,
    onStartTodayWorkout: () -> Unit,
    onNavigateWeek: (Int) -> Unit
) {
    val isProjectedWeek = weekOffset > 0
    val todayDay = calendarDays.firstOrNull { it.isToday }
    val showStartButton = todayDay != null && !hasWorkoutTodayCompleted && todayDay.workoutTag == "STRENGTH"
    val showLogButton = todayDay != null && !hasWorkoutTodayCompleted && todayDay.workoutTag != "STRENGTH"
    var previewDay by remember { mutableStateOf<CalendarDayUiState?>(null) }

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MainCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Title row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Workout Calendar",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryText
                )
                if (isProjectedWeek) {
                    Surface(
                        shape = RoundedCornerShape(999.dp),
                        color = CyanAccent.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = "Projected plan",
                            style = MaterialTheme.typography.labelSmall,
                            color = CyanAccent,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }
            // Week navigation row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = { onNavigateWeek(-1) },
                    enabled = weekOffset > -4
                ) {
                    Text(text = "< Prev", color = if (weekOffset > -4) CyanAccent else MutedControl)
                }
                Text(
                    text = weekLabel,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryText
                )
                TextButton(
                    onClick = { onNavigateWeek(+1) },
                    enabled = weekOffset < 4
                ) {
                    Text(text = "Next >", color = if (weekOffset < 4) CyanAccent else MutedControl)
                }
            }
            // Day pills
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(horizontal = 2.dp)
            ) {
                items(calendarDays) { day ->
                    CalendarDayPill(
                        day = day,
                        onTap = { if (day.isCompleted) onDayTapped(day) else previewDay = day }
                    )
                }
            }
            // Action buttons — only shown on current week
            if (!isProjectedWeek) {
                if (showStartButton) {
                    PrimaryBlueButton(
                        text = "Start Today's Workout",
                        onClick = onStartTodayWorkout,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else if (showLogButton) {
                    OutlinedButton(
                        onClick = onStartTodayWorkout,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, PositiveAccent)
                    ) {
                        Text(
                            text = "Log ${todayDay?.workoutTypeLabel?.take(20) ?: "Session"}",
                            color = PositiveAccent,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }

    previewDay?.let { selected ->
        val previewIndex = calendarDays.indexOfFirst { it.date == selected.date }
        CalendarDayPreviewDialog(
            day = selected,
            onDismiss = { previewDay = null },
            onStartWorkout = { onStartTodayWorkout(); previewDay = null },
            onPrevious = if (previewIndex > 0) { { previewDay = calendarDays[previewIndex - 1] } } else null,
            onNext = if (previewIndex < calendarDays.lastIndex) { { previewDay = calendarDays[previewIndex + 1] } } else null
        )
    }
}

@Composable
private fun CalendarDayPreviewDialog(
    day: CalendarDayUiState,
    onDismiss: () -> Unit,
    onStartWorkout: () -> Unit,
    onPrevious: (() -> Unit)?,
    onNext: (() -> Unit)?
) {
    val plan = day.projectedPlan
    val showStartEnabled = day.isToday && day.workoutTag != "SKIPPED"
    val startLabel = when {
        plan?.isStrengthDay == true -> "Start Workout"
        day.workoutTag == "REST" -> "Start Workout Anyway"
        else -> "Log Session"
    }
    val headerTag = when {
        day.isToday -> "Today"
        day.isProjected -> "Projected"
        day.isFuture -> "Planned"
        day.workoutTag == "SKIPPED" -> "Skipped"
        else -> "Past"
    }
    val headerTagColor = when (headerTag) {
        "Today" -> AccentBlue
        "Projected", "Planned" -> CyanAccent
        "Skipped" -> WarningAccent
        else -> MutedText
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.88f)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            color = MainCard
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Scrollable content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${day.dayLabel}, ${day.dateNumber}",
                                style = MaterialTheme.typography.labelMedium,
                                color = MutedText
                            )
                            Text(
                                text = plan?.focus ?: day.workoutTypeLabel,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryText
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(999.dp),
                            color = headerTagColor.copy(alpha = 0.14f)
                        ) {
                            Text(
                                text = headerTag,
                                style = MaterialTheme.typography.labelSmall,
                                color = headerTagColor,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    if (plan != null) {
                        val statLine = buildList {
                            if (plan.durationMinutes.isNotEmpty()) add("${plan.durationMinutes} min")
                            if (plan.setsPerExercise > 0) add("${plan.setsPerExercise} sets")
                            if (plan.repsRange.isNotEmpty()) add("${plan.repsRange} reps")
                            if (plan.rpeTarget.isNotEmpty()) add("RPE ${plan.rpeTarget}")
                        }.joinToString(" · ")
                        if (statLine.isNotEmpty()) {
                            Text(text = statLine, style = MaterialTheme.typography.bodySmall, color = MutedText)
                        }

                        if (plan.isStrengthDay && plan.suggestedExercises.isNotEmpty()) {
                            if (plan.warmUp.isNotEmpty()) {
                                PlanSection(title = "Warm-Up") { plan.warmUp.forEach { BulletRow(it) } }
                            }
                            PlanSection(title = "Exercises") {
                                plan.suggestedExercises.forEach { ExercisePreviewRow(it) }
                            }
                            if (plan.coolDown.isNotEmpty()) {
                                PlanSection(title = "Cool-Down") { plan.coolDown.forEach { BulletRow(it) } }
                            }
                        } else if (plan.nonStrengthActivities.isNotEmpty()) {
                            if (plan.warmUp.isNotEmpty()) {
                                PlanSection(title = "Warm-Up") { plan.warmUp.forEach { BulletRow(it) } }
                            }
                            PlanSection(title = "Activities") {
                                plan.nonStrengthActivities.forEach { BulletRow(it) }
                            }
                            if (plan.coolDown.isNotEmpty()) {
                                PlanSection(title = "Cool-Down") { plan.coolDown.forEach { BulletRow(it) } }
                            }
                        }

                        if (plan.postWorkoutRecommendations.isNotEmpty()) {
                            PlanSection(title = "Post-Session") {
                                plan.postWorkoutRecommendations.forEach { BulletRow(it) }
                            }
                        }
                        if (plan.reasons.isNotEmpty()) {
                            PlanSection(title = "Why This Plan") {
                                plan.reasons.forEach { BulletRow(it) }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Future-day hint
                if (!showStartEnabled && (day.isFuture || day.isProjected)) {
                    Text(
                        text = "Available on ${day.dayLabel}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MutedText,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                // Fixed bottom: Prev · Start/Close · Next
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = { onPrevious?.invoke() },
                        enabled = onPrevious != null
                    ) {
                        Text(
                            text = "< Prev",
                            color = if (onPrevious != null) CyanAccent else MutedControl
                        )
                    }
                    if (showStartEnabled) {
                        PrimaryBlueButton(text = startLabel, onClick = onStartWorkout)
                    } else {
                        TextButton(onClick = onDismiss) {
                            Text(text = "Close", color = MutedText)
                        }
                    }
                    TextButton(
                        onClick = { onNext?.invoke() },
                        enabled = onNext != null
                    ) {
                        Text(
                            text = "Next >",
                            color = if (onNext != null) CyanAccent else MutedControl
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PlanSection(title: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = CyanAccent
        )
        content()
    }
}

@Composable
private fun BulletRow(text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(text = "·", style = MaterialTheme.typography.bodySmall, color = MutedText)
        Text(text = text, style = MaterialTheme.typography.bodySmall, color = MutedText)
    }
}

@Composable
private fun ExercisePreviewRow(exercise: SuggestedExerciseUiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = exercise.name,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryText,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = exercise.muscleGroup,
                style = MaterialTheme.typography.labelSmall,
                color = MutedText
            )
        }
        val detail = buildList {
            if (exercise.prescribedSets > 0) add("${exercise.prescribedSets} sets")
            if (exercise.prescribedRepsRange.isNotEmpty()) add("${exercise.prescribedRepsRange} reps")
            if (exercise.prescribedRpe.isNotEmpty()) add("RPE ${exercise.prescribedRpe}")
        }.joinToString(" · ")
        if (detail.isNotEmpty()) {
            Text(text = detail, style = MaterialTheme.typography.labelSmall, color = MutedText)
        }
        if (exercise.suggestedWeightText.isNotEmpty()) {
            Text(
                text = exercise.suggestedWeightText,
                style = MaterialTheme.typography.labelSmall,
                color = CyanAccent
            )
        }
        if (exercise.progressionNote.isNotEmpty()) {
            Text(
                text = exercise.progressionNote,
                style = MaterialTheme.typography.labelSmall,
                color = PositiveAccent
            )
        }
    }
}

@Composable
private fun CalendarDayPill(day: CalendarDayUiState, onTap: () -> Unit) {
    val isInteractive = true
    val dotColor = when {
        day.isCompleted -> PositiveAccent
        day.isToday -> when (day.workoutTag) {
            "STRENGTH" -> AccentBlue
            "MOBILITY" -> CyanAccent
            "RECOVERY", "WALK" -> PositiveAccent
            else -> MutedText
        }
        day.isFuture -> when (day.workoutTag) {
            "STRENGTH" -> AccentBlue.copy(alpha = 0.4f)
            "MOBILITY" -> CyanAccent.copy(alpha = 0.4f)
            "RECOVERY", "WALK" -> PositiveAccent.copy(alpha = 0.4f)
            else -> MutedText.copy(alpha = 0.25f)
        }
        else -> when (day.workoutTag) {
            "SKIPPED" -> WarningAccent.copy(alpha = 0.5f)
            else -> MutedText.copy(alpha = 0.2f)
        }
    }
    val dotSymbol = when {
        day.isCompleted -> "✓"
        day.workoutTag == "STRENGTH" -> "S"
        day.workoutTag == "MOBILITY" -> "M"
        day.workoutTag == "RECOVERY" || day.workoutTag == "WALK" -> "R"
        day.workoutTag == "SKIPPED" -> "✕"
        else -> "–"
    }
    val typeShort = when (day.workoutTag) {
        "STRENGTH" -> day.workoutTypeLabel.take(9)
        "MOBILITY" -> "Mobility"
        "RECOVERY" -> "Recovery"
        "WALK" -> "Walking"
        "REST" -> "Rest"
        "SKIPPED" -> "Skipped"
        else -> day.workoutTypeLabel.take(9)
    }
    val containerColor = if (day.isToday) SecondaryCard else Color.Transparent
    val borderColor = if (day.isToday) CyanAccent else Color.Transparent

    Surface(
        modifier = Modifier
            .width(50.dp)
            .clickable(enabled = isInteractive, onClick = onTap),
        shape = RoundedCornerShape(12.dp),
        color = containerColor,
        border = BorderStroke(if (day.isToday) 1.5.dp else 0.dp, borderColor)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = day.dayLabel,
                style = MaterialTheme.typography.labelSmall,
                color = if (day.isToday) CyanAccent else MutedText,
                fontWeight = if (day.isToday) FontWeight.Bold else FontWeight.Normal
            )
            Text(
                text = "${day.dateNumber}",
                style = MaterialTheme.typography.bodySmall,
                color = if (day.isToday) PrimaryText else MutedText,
                fontWeight = if (day.isToday) FontWeight.SemiBold else FontWeight.Normal
            )
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .clip(CircleShape)
                    .background(dotColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = dotSymbol,
                    style = MaterialTheme.typography.labelSmall,
                    color = PrimaryText,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = typeShort,
                style = MaterialTheme.typography.labelSmall,
                color = if (day.isToday) PrimaryText else MutedText.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun PrCelebrationBanner(achievements: List<String>, onDismiss: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = PositiveAccent.copy(alpha = 0.12f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🏆 New Personal Record!",
                    color = PositiveAccent,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleSmall
                )
                TextButton(onClick = onDismiss) {
                    Text(text = "Dismiss", color = MutedText, style = MaterialTheme.typography.bodySmall)
                }
            }
            achievements.forEach { achievement ->
                Text(
                    text = "· $achievement",
                    color = PrimaryText,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun PersonalRecordsCard(records: List<PersonalRecordUiState>) {
    Box(modifier = Modifier.fillMaxWidth()) {
        MainFeatureCard(modifier = Modifier.padding(top = 22.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Personal Records",
                    color = PrimaryText,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                records.forEach { record ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(SecondaryCard.copy(alpha = 0.72f))
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = record.exerciseName, color = PrimaryText, fontWeight = FontWeight.SemiBold)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            PrStatItem(label = "Best", value = record.bestWeightText, modifier = Modifier.weight(1f))
                            PrStatItem(label = "Best Set", value = record.bestVolumeSetText, modifier = Modifier.weight(1f))
                            PrStatItem(label = "Est. 1RM", value = record.estimated1RmText, modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
        FloatingTitlePill(text = "PRs", modifier = Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun PrStatItem(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = value, color = CyanAccent, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
        Text(text = label, color = MutedText, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
private fun WeeklyLoadSection(loads: List<WeeklyLoadUiState>) {
    Box(modifier = Modifier.fillMaxWidth()) {
        MainFeatureCard(modifier = Modifier.padding(top = 22.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Weekly Training Load",
                    color = PrimaryText,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                loads.forEach { load ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(SecondaryCard.copy(alpha = 0.72f))
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(text = load.weekLabel, color = CyanAccent, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodySmall)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "${load.workoutCount} workouts", color = PrimaryText, style = MaterialTheme.typography.bodySmall)
                            Text(text = "${load.totalSets} sets", color = MutedText, style = MaterialTheme.typography.bodySmall)
                            Text(text = load.totalVolumeText, color = MutedText, style = MaterialTheme.typography.bodySmall)
                            Text(text = "RPE ${load.avgRpe}", color = MutedText, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
        FloatingTitlePill(text = "Load", modifier = Modifier.align(Alignment.TopCenter))
    }
}

private fun statusColor(label: String): Color = when (label.uppercase()) {
    "COMPLETED" -> PositiveAccent
    "PARTIAL" -> WarningAccent
    else -> MutedText
}

@Composable
fun NutritionPlanScreen(
    uiState: NutritionUiState,
    onAddFood: () -> Unit,
    onScanBarcode: () -> Unit,
    onScanLabel: () -> Unit,
    onAiLog: () -> Unit,
    onHideAiLog: () -> Unit,
    onAiInputChange: (String) -> Unit,
    onSubmitAiLog: () -> Unit,
    onLogUsualMeal: (UsualMealUiState) -> Unit,
    onCancelForm: () -> Unit,
    onSaveForm: () -> Unit,
    onEditEntry: (FoodEntryUiState) -> Unit,
    onDeleteEntry: (Long) -> Unit,
    onFormChange: ((FoodEntryFormUiState) -> FoodEntryFormUiState) -> Unit,
    onToggleSaved: (Long, Boolean) -> Unit,
    onQuickAddFood: (QuickAddFoodUiState) -> Unit,
    onDismissQuickAdd: () -> Unit,
    onConfirmQuickAdd: () -> Unit,
    onQuickAddMealChange: (String) -> Unit,
    onQuickAddQuantityChange: (String) -> Unit,
    onQuickAddTimeChange: (String) -> Unit,
    onCopyYesterday: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        MainFeatureCard(modifier = Modifier.padding(top = 22.dp)) {
            Column(
                modifier = Modifier.padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                MacroSummaryCard(uiState = uiState)
                MacroBar(
                    label = "Protein",
                    value = "${uiState.proteinGrams.clean()}g / ${uiState.proteinGoalMin}-${uiState.proteinGoalMax}g",
                    progress = uiState.proteinProgress(),
                    color = CyanAccent
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Latest meal", color = MutedText)
                    Text(text = uiState.latestMealTime, color = CyanAccent, fontWeight = FontWeight.Bold)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    PrimaryBlueButton(text = "Add Food", onClick = onAddFood, modifier = Modifier.weight(1f))
                    OutlinedButton(
                        onClick = onCopyYesterday,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(999.dp)
                    ) {
                        Text("Copy Yesterday", color = MutedText)
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedButton(
                        onClick = onScanBarcode,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(999.dp)
                    ) {
                        Text("Scan Barcode", color = CyanAccent)
                    }
                    OutlinedButton(
                        onClick = onAiLog,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(999.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CyanAccent.copy(alpha = 0.6f))
                    ) {
                        Text("AI Quick Log", color = CyanAccent, fontWeight = FontWeight.SemiBold)
                    }
                }
                if (uiState.isAiLogVisible) {
                    AiQuickLogInput(
                        input = uiState.aiInput,
                        isParsing = uiState.isAiParsing,
                        onInputChange = onAiInputChange,
                        onSubmit = onSubmitAiLog,
                        onCancel = onHideAiLog
                    )
                }
                uiState.quickAddDialogFood?.let { food ->
                    QuickAddDialog(
                        food = food,
                        mealName = uiState.quickAddMealName,
                        quantity = uiState.quickAddQuantity,
                        time = uiState.quickAddTime,
                        onMealChange = onQuickAddMealChange,
                        onQuantityChange = onQuickAddQuantityChange,
                        onTimeChange = onQuickAddTimeChange,
                        onConfirm = onConfirmQuickAdd,
                        onDismiss = onDismissQuickAdd
                    )
                }
                if (uiState.isFormVisible) {
                    Dialog(
                        onDismissRequest = onCancelForm,
                        properties = DialogProperties(usePlatformDefaultWidth = false)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.92f)
                                .padding(horizontal = 16.dp)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            uiState.barcodeMessage?.let { msg ->
                                Text(
                                    msg,
                                    color = WarningAccent,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                            }
                            uiState.labelScanMessage?.let {
                                ElevatedCard(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.elevatedCardColors(containerColor = SecondaryCard),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(14.dp),
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text(
                                            "Missing nutrition details?",
                                            color = PrimaryText,
                                            fontWeight = FontWeight.Bold,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Text(
                                            "Scan the Nutrition Facts or Supplement Facts label to fill macros, vitamins, and minerals.",
                                            color = MutedText,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                        Button(
                                            onClick = onScanLabel,
                                            modifier = Modifier.fillMaxWidth(),
                                            shape = RoundedCornerShape(999.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = CyanAccent)
                                        ) {
                                            Text("Scan Label", color = Color.Black, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                            uiState.ocrConfirmMessage?.let { msg ->
                                Text(
                                    msg,
                                    color = PositiveAccent,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                            }
                            uiState.aiConfidenceMessage?.let { msg ->
                                Text(
                                    msg,
                                    color = WarningAccent,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                                Text(
                                    "AI estimates can be inaccurate. Review and edit before saving.",
                                    color = MutedText,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                            }
                            FoodEntryForm(
                                form = uiState.form,
                                onFormChange = onFormChange,
                                onSave = onSaveForm,
                                onCancel = onCancelForm
                            )
                        }
                    }
                }
                if (uiState.todayPlanMeals.isNotEmpty()) {
                    RecipeSuggestionsSection(
                        title = "Today's Meal Plan",
                        suggestions = uiState.todayPlanMeals,
                        onAdd = onQuickAddFood
                    )
                }
                UsualMealsSection(
                    meals = uiState.usualMeals,
                    onLog = onLogUsualMeal
                )
                if (uiState.savedFoods.isNotEmpty()) {
                    QuickAddSection(
                        title = "Saved Foods",
                        foods = uiState.savedFoods,
                        onAdd = onQuickAddFood,
                        onToggleSaved = { food -> onToggleSaved(food.sourceEntryId, !food.isSaved) }
                    )
                }
                if (uiState.recentFoods.isNotEmpty()) {
                    QuickAddSection(
                        title = "Recent Foods",
                        foods = uiState.recentFoods,
                        onAdd = onQuickAddFood,
                        onToggleSaved = { food -> onToggleSaved(food.sourceEntryId, !food.isSaved) }
                    )
                }
                if (uiState.suggestions.isNotEmpty()) {
                    RecipeSuggestionsSection(
                        title = "Suggested Meals",
                        suggestions = uiState.suggestions,
                        onAdd = onQuickAddFood
                    )
                }
                uiState.mealSections.forEach { section ->
                    MealSection(
                        section = section,
                        onEditEntry = onEditEntry,
                        onDeleteEntry = onDeleteEntry,
                        onToggleSaved = onToggleSaved
                    )
                }
            }
        }
        FloatingTitlePill(text = "Nutrition Plan", modifier = Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun AiQuickLogInput(
    input: String,
    isParsing: Boolean,
    onInputChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(SecondaryCard.copy(alpha = 0.7f))
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Describe your meal",
            color = CyanAccent,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold
        )
        OutlinedTextField(
            value = input,
            onValueChange = onInputChange,
            placeholder = {
                Text(
                    "e.g. 2 rotis, dal, rice, paneer, and chai",
                    style = MaterialTheme.typography.bodySmall,
                    color = MutedText
                )
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
            maxLines = 4
        )
        Text(
            text = "AI estimates can be inaccurate. You will review before saving.",
            color = MutedText,
            style = MaterialTheme.typography.bodySmall
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedButton(onClick = onCancel, modifier = Modifier.weight(1f), shape = RoundedCornerShape(999.dp)) {
                Text("Cancel", color = MutedText)
            }
            PrimaryBlueButton(
                text = if (isParsing) "Estimating..." else "Estimate",
                onClick = onSubmit,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MacroSummaryCard(uiState: NutritionUiState) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        ProgressRing(progress = uiState.proteinProgress(), modifier = Modifier.size(156.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "${uiState.proteinGrams.clean()}g", color = PrimaryText, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Text(text = "protein", color = MutedText)
            }
        }
        // Macro tiles with inline progress rings
        val calProgress = if (uiState.calorieTarget > 0) (uiState.calories.toFloat() / uiState.calorieTarget).coerceIn(0f, 1f) else 0f
        val carbProgress = if (uiState.carbGoal > 0) (uiState.carbGrams / uiState.carbGoal).toFloat().coerceIn(0f, 1f) else 0f
        val fatProgress = if (uiState.fatGoal > 0) (uiState.fatGrams / uiState.fatGoal).toFloat().coerceIn(0f, 1f) else 0f
        val fiberProgress = if (uiState.fiberGoal > 0) (uiState.fiberGrams / uiState.fiberGoal).toFloat().coerceIn(0f, 1f) else 0f
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            MacroMiniTile("kcal", "${uiState.calories}", if (uiState.calorieTarget > 0) "${uiState.calorieTarget}" else "–", calProgress, Color(0xFFFF8C42), Modifier.weight(1f))
            MacroMiniTile("carbs", "${uiState.carbGrams.clean()}g", if (uiState.carbGoal > 0) "${uiState.carbGoal}g" else "–", carbProgress, WarningAccent, Modifier.weight(1f))
            MacroMiniTile("fat", "${uiState.fatGrams.clean()}g", if (uiState.fatGoal > 0) "${uiState.fatGoal}g" else "–", fatProgress, AccentBlue, Modifier.weight(1f))
            MacroMiniTile("fiber", "${uiState.fiberGrams.clean()}g", if (uiState.fiberGoal > 0) "${uiState.fiberGoal}g" else "–", fiberProgress, PositiveAccent, Modifier.weight(1f))
        }
    }
}

@Composable
private fun MacroMiniTile(
    label: String,
    consumed: String,
    target: String,
    progress: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.10f))
            .padding(horizontal = 6.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = MutedText)
        ProgressRing(
            progress = progress,
            modifier = Modifier.size(48.dp),
            strokeWidth = 4.dp,
            color = color
        ) {
            Text(consumed, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = color, maxLines = 1)
        }
        Text("/ $target", style = MaterialTheme.typography.labelSmall, color = MutedText, maxLines = 1)
    }
}

@Composable
private fun FoodEntryForm(
    form: FoodEntryFormUiState,
    onFormChange: ((FoodEntryFormUiState) -> FoodEntryFormUiState) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = SecondaryCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(text = if (form.editingId == 0L) "Manual food entry" else "Edit food", color = PrimaryText, fontWeight = FontWeight.Bold)
            MealChips(selectedMeal = form.mealName, onMealSelected = { meal -> onFormChange { it.copy(mealName = meal) } })
            OutlinedTextField(value = form.foodName, onValueChange = { value -> onFormChange { it.copy(foodName = value) } }, label = { Text("Food name") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            OutlinedTextField(value = form.brandName, onValueChange = { value -> onFormChange { it.copy(brandName = value) } }, label = { Text("Brand optional") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(value = form.servingDescription, onValueChange = { value -> onFormChange { it.copy(servingDescription = value) } }, label = { Text("Serving size") }, modifier = Modifier.weight(2f), singleLine = true)
                MacroInput("Quantity", form.quantity, Modifier.weight(1f)) { value -> onFormChange { it.copy(quantity = value.filterDecimal().ifBlank { "1" }) } }
            }
            Text(text = "Macros per 1 serving", color = MutedText, style = MaterialTheme.typography.labelSmall)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                MacroInput("Calories", form.calories, Modifier.weight(1f)) { value -> onFormChange { it.copy(calories = value.filterDigits()) } }
                MacroInput("Protein", form.proteinGrams, Modifier.weight(1f)) { value -> onFormChange { it.copy(proteinGrams = value.filterDecimal()) } }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                MacroInput("Carbs", form.carbGrams, Modifier.weight(1f)) { value -> onFormChange { it.copy(carbGrams = value.filterDecimal()) } }
                MacroInput("Fat", form.fatGrams, Modifier.weight(1f)) { value -> onFormChange { it.copy(fatGrams = value.filterDecimal()) } }
                MacroInput("Fiber", form.fiberGrams, Modifier.weight(1f)) { value -> onFormChange { it.copy(fiberGrams = value.filterDecimal()) } }
            }
            OutlinedTextField(value = form.mealTime, onValueChange = { value -> onFormChange { it.copy(mealTime = value) } }, label = { Text("Time eaten HH:mm") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            FoodFlagChips(form = form, onFormChange = onFormChange)
            VitaminsMineralsSection(form = form, onFormChange = onFormChange)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(onClick = onCancel, modifier = Modifier.weight(1f), shape = RoundedCornerShape(999.dp)) {
                    Text("Cancel", color = MutedText)
                }
                PrimaryBlueButton(text = "Save Food", onClick = onSave, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun VitaminsMineralsSection(
    form: FoodEntryFormUiState,
    onFormChange: ((FoodEntryFormUiState) -> FoodEntryFormUiState) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Vitamins & Minerals", color = MutedText, style = MaterialTheme.typography.labelMedium)
            Text(if (expanded) "▲" else "▼", color = MutedText, style = MaterialTheme.typography.labelSmall)
        }
        if (expanded) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Vitamins", color = MutedText, style = MaterialTheme.typography.labelSmall)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MacroInput("Vit A (mcg)", form.vitaminA, Modifier.weight(1f)) { v -> onFormChange { it.copy(vitaminA = v.filterDecimal()) } }
                    MacroInput("Vit C (mg)", form.vitaminC, Modifier.weight(1f)) { v -> onFormChange { it.copy(vitaminC = v.filterDecimal()) } }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MacroInput("Vit D (mcg)", form.vitaminD, Modifier.weight(1f)) { v -> onFormChange { it.copy(vitaminD = v.filterDecimal()) } }
                    MacroInput("Vit E (mg)", form.vitaminE, Modifier.weight(1f)) { v -> onFormChange { it.copy(vitaminE = v.filterDecimal()) } }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MacroInput("Vit K (mcg)", form.vitaminK, Modifier.weight(1f)) { v -> onFormChange { it.copy(vitaminK = v.filterDecimal()) } }
                    MacroInput("B1/Thiamin (mg)", form.vitaminB1, Modifier.weight(1f)) { v -> onFormChange { it.copy(vitaminB1 = v.filterDecimal()) } }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MacroInput("B2/Riboflavin (mg)", form.vitaminB2, Modifier.weight(1f)) { v -> onFormChange { it.copy(vitaminB2 = v.filterDecimal()) } }
                    MacroInput("B3/Niacin (mg)", form.vitaminB3, Modifier.weight(1f)) { v -> onFormChange { it.copy(vitaminB3 = v.filterDecimal()) } }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MacroInput("B5/Pantothenic (mg)", form.pantothenicAcid, Modifier.weight(1f)) { v -> onFormChange { it.copy(pantothenicAcid = v.filterDecimal()) } }
                    MacroInput("B6 (mg)", form.vitaminB6, Modifier.weight(1f)) { v -> onFormChange { it.copy(vitaminB6 = v.filterDecimal()) } }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MacroInput("B12 (mcg)", form.vitaminB12, Modifier.weight(1f)) { v -> onFormChange { it.copy(vitaminB12 = v.filterDecimal()) } }
                    MacroInput("Folate (mcg)", form.folate, Modifier.weight(1f)) { v -> onFormChange { it.copy(folate = v.filterDecimal()) } }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MacroInput("Biotin (mcg)", form.biotin, Modifier.weight(1f)) { v -> onFormChange { it.copy(biotin = v.filterDecimal()) } }
                    Spacer(Modifier.weight(1f))
                }
                Text("Minerals", color = MutedText, style = MaterialTheme.typography.labelSmall)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MacroInput("Calcium (mg)", form.calcium, Modifier.weight(1f)) { v -> onFormChange { it.copy(calcium = v.filterDecimal()) } }
                    MacroInput("Iron (mg)", form.iron, Modifier.weight(1f)) { v -> onFormChange { it.copy(iron = v.filterDecimal()) } }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MacroInput("Potassium (mg)", form.potassium, Modifier.weight(1f)) { v -> onFormChange { it.copy(potassium = v.filterDecimal()) } }
                    MacroInput("Magnesium (mg)", form.magnesium, Modifier.weight(1f)) { v -> onFormChange { it.copy(magnesium = v.filterDecimal()) } }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MacroInput("Sodium (mg)", form.sodium, Modifier.weight(1f)) { v -> onFormChange { it.copy(sodium = v.filterDecimal()) } }
                    MacroInput("Zinc (mg)", form.zinc, Modifier.weight(1f)) { v -> onFormChange { it.copy(zinc = v.filterDecimal()) } }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MacroInput("Phosphorus (mg)", form.phosphorus, Modifier.weight(1f)) { v -> onFormChange { it.copy(phosphorus = v.filterDecimal()) } }
                    MacroInput("Iodine (mcg)", form.iodine, Modifier.weight(1f)) { v -> onFormChange { it.copy(iodine = v.filterDecimal()) } }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MacroInput("Selenium (mcg)", form.selenium, Modifier.weight(1f)) { v -> onFormChange { it.copy(selenium = v.filterDecimal()) } }
                    MacroInput("Copper (mg)", form.copper, Modifier.weight(1f)) { v -> onFormChange { it.copy(copper = v.filterDecimal()) } }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MacroInput("Manganese (mg)", form.manganese, Modifier.weight(1f)) { v -> onFormChange { it.copy(manganese = v.filterDecimal()) } }
                    MacroInput("Chromium (mcg)", form.chromium, Modifier.weight(1f)) { v -> onFormChange { it.copy(chromium = v.filterDecimal()) } }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MacroInput("Molybdenum (mcg)", form.molybdenum, Modifier.weight(1f)) { v -> onFormChange { it.copy(molybdenum = v.filterDecimal()) } }
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun MacroInput(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(value = value, onValueChange = onValueChange, label = { Text(label) }, singleLine = true, modifier = modifier)
}

@Composable
private fun MealChips(selectedMeal: String, onMealSelected: (String) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        listOf("Breakfast", "Lunch", "Dinner", "Snack").forEach { meal ->
            FilterChip(
                selected = selectedMeal == meal,
                onClick = { onMealSelected(meal) },
                label = { Text(meal, maxLines = 1) },
                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AccentBlue, selectedLabelColor = PrimaryText, labelColor = MutedText),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun FoodFlagChips(
    form: FoodEntryFormUiState,
    onFormChange: ((FoodEntryFormUiState) -> FoodEntryFormUiState) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        FilterChip(
            selected = form.isWholeFoodBased,
            onClick = { onFormChange { it.copy(isWholeFoodBased = true, isProcessed = false) } },
            label = { Text("Whole") },
            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = PositiveAccent, selectedLabelColor = PrimaryText, labelColor = MutedText),
            modifier = Modifier.weight(1f)
        )
        FilterChip(
            selected = form.isProcessed,
            onClick = { onFormChange { it.copy(isProcessed = true, isWholeFoodBased = false) } },
            label = { Text("Processed") },
            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = WarningAccent, selectedLabelColor = PrimaryText, labelColor = MutedText),
            modifier = Modifier.weight(1f)
        )
        FilterChip(
            selected = form.isFermented,
            onClick = { onFormChange { it.copy(isFermented = !it.isFermented) } },
            label = { Text("Fermented") },
            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = CyanAccent, selectedLabelColor = PrimaryText, labelColor = MutedText),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun MealPickerDialog(
    foodName: String,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = SecondaryCard,
        titleContentColor = PrimaryText,
        textContentColor = MutedText,
        title = { Text("Log under which meal?", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text(foodName, color = CyanAccent, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(10.dp))
                listOf("Breakfast", "Lunch", "Dinner", "Snack").forEach { option ->
                    TextButton(
                        onClick = { onSelect(option) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(option, color = PrimaryText)
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = MutedText)
            }
        }
    )
}

@Composable
private fun UsualMealsSection(
    meals: List<UsualMealUiState>,
    onLog: (UsualMealUiState) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Suggested Meals", color = PrimaryText, fontWeight = FontWeight.Bold)
        if (meals.isEmpty()) {
            Text(
                text = "Log the same food 3 or more times to see suggestions here.",
                color = MutedText,
                style = MaterialTheme.typography.bodySmall
            )
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 2.dp)
            ) {
                items(meals) { meal ->
                    UsualMealChip(meal = meal, onLog = { onLog(meal) })
                }
            }
        }
    }
}

@Composable
private fun UsualMealChip(meal: UsualMealUiState, onLog: () -> Unit) {
    Surface(
        modifier = Modifier.width(164.dp),
        shape = RoundedCornerShape(14.dp),
        color = SecondaryCard,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = meal.foodName,
                color = PrimaryText,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = meal.mealName, color = CyanAccent, style = MaterialTheme.typography.labelSmall)
            if (meal.calories > 0) {
                Text(
                    text = "${meal.calories} kcal · P${meal.proteinGrams.clean()}g",
                    color = MutedText,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1
                )
            }
            Text(
                text = "×${meal.timesLogged}",
                color = MutedText,
                style = MaterialTheme.typography.labelSmall
            )
            TextButton(
                onClick = onLog,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Log", color = CyanAccent, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Composable
private fun UsualMealRow(
    meal: UsualMealUiState,
    onLog: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(SecondaryCard.copy(alpha = 0.55f))
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = meal.foodName, color = PrimaryText, fontWeight = FontWeight.SemiBold)
                Text(
                    text = meal.mealName,
                    color = CyanAccent,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            val macros = buildString {
                if (meal.calories > 0) append("${meal.calories} kcal")
                if (meal.proteinGrams > 0) append("  P${meal.proteinGrams.clean()}g")
                if (meal.carbGrams > 0) append("  C${meal.carbGrams.clean()}g")
                if (meal.fatGrams > 0) append("  F${meal.fatGrams.clean()}g")
            }
            if (macros.isNotEmpty()) {
                Text(text = macros, color = MutedText, style = MaterialTheme.typography.bodySmall)
            }
            Text(
                text = "Logged ${meal.timesLogged}×",
                color = MutedText,
                style = MaterialTheme.typography.labelSmall
            )
        }
        TextButton(onClick = onLog) {
            Text("Choose Meal", color = CyanAccent, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun RecipeSuggestionsSection(
    suggestions: List<RecipeSuggestionUiState>,
    onAdd: (QuickAddFoodUiState) -> Unit,
    title: String = "Recipe Suggestions"
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = title,
            color = PrimaryText,
            fontWeight = FontWeight.Bold
        )
        suggestions.forEach { suggestion ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SecondaryCard.copy(alpha = 0.55f))
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = suggestion.mealName,
                        color = MutedText,
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = suggestion.food.foodName,
                        color = PrimaryText,
                        fontWeight = FontWeight.SemiBold
                    )
                    val info = buildString {
                        if (suggestion.food.calories > 0) append("${suggestion.food.calories} kcal")
                        if (suggestion.food.proteinGrams > 0) append("  P${suggestion.food.proteinGrams.clean()}g")
                    }
                    if (info.isNotEmpty()) {
                        Text(info, color = MutedText, style = MaterialTheme.typography.bodySmall)
                    }
                }
                TextButton(onClick = { onAdd(suggestion.food) }) {
                    Text("Add", color = CyanAccent, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun QuickAddDialog(
    food: QuickAddFoodUiState,
    mealName: String,
    quantity: String,
    time: String,
    onMealChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onTimeChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            color = MainCard
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = food.foodName,
                    color = PrimaryText,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                food.brandName?.let {
                    Text(it, color = MutedText, style = MaterialTheme.typography.bodySmall)
                }
                val qty = quantity.toDoubleOrNull()?.coerceAtLeast(0.01) ?: 1.0
                val scaledCal = (food.calories * qty).toInt()
                val scaledP = food.proteinGrams * qty
                val scaledC = food.carbGrams * qty
                val scaledF = food.fatGrams * qty
                val macroLine = buildString {
                    if (scaledCal > 0) append("${scaledCal} kcal")
                    if (scaledP > 0) append("  P${scaledP.clean()}g")
                    if (scaledC > 0) append("  C${scaledC.clean()}g")
                    if (scaledF > 0) append("  F${scaledF.clean()}g")
                }
                if (macroLine.isNotEmpty()) {
                    Text(macroLine, color = CyanAccent, style = MaterialTheme.typography.bodySmall)
                }
                MealChips(selectedMeal = mealName, onMealSelected = onMealChange)
                OutlinedTextField(
                    value = quantity,
                    onValueChange = onQuantityChange,
                    label = { Text("Servings") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = time,
                    onValueChange = onTimeChange,
                    label = { Text("Time (HH:mm)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(999.dp)
                    ) {
                        Text("Cancel", color = MutedText)
                    }
                    Button(
                        onClick = onConfirm,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(999.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CyanAccent)
                    ) {
                        Text("Add", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickAddSection(
    title: String,
    foods: List<QuickAddFoodUiState>,
    onAdd: (QuickAddFoodUiState) -> Unit,
    onToggleSaved: (QuickAddFoodUiState) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = title, color = PrimaryText, fontWeight = FontWeight.Bold)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
            items(foods) { food ->
                QuickAddFoodChip(food = food, onAdd = onAdd, onToggleSaved = onToggleSaved)
            }
        }
    }
}

@Composable
private fun QuickAddFoodChip(
    food: QuickAddFoodUiState,
    onAdd: (QuickAddFoodUiState) -> Unit,
    onToggleSaved: (QuickAddFoodUiState) -> Unit
) {
    Surface(
        modifier = Modifier.width(164.dp),
        shape = RoundedCornerShape(14.dp),
        color = SecondaryCard,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = food.foodName,
                color = PrimaryText,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (food.calories > 0) {
                Text(
                    text = "${food.calories} kcal · P${food.proteinGrams.clean()}g",
                    color = MutedText,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { onToggleSaved(food) }, contentPadding = PaddingValues(0.dp)) {
                    Text(
                        text = if (food.isSaved) "♥" else "♡",
                        color = if (food.isSaved) PositiveAccent else MutedText
                    )
                }
                TextButton(onClick = { onAdd(food) }, contentPadding = PaddingValues(0.dp)) {
                    Text("Add", color = CyanAccent, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}

@Composable
private fun QuickAddFoodRow(
    food: QuickAddFoodUiState,
    onAdd: (QuickAddFoodUiState) -> Unit,
    onToggleSaved: (QuickAddFoodUiState) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(SecondaryCard.copy(alpha = 0.55f))
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = food.foodName, color = PrimaryText, fontWeight = FontWeight.SemiBold)
            val detail = buildString {
                if (food.calories > 0) append("${food.calories} kcal")
                if (food.proteinGrams > 0) append("  P${food.proteinGrams.clean()}g")
                if (food.carbGrams > 0) append("  C${food.carbGrams.clean()}g")
                if (food.fatGrams > 0) append("  F${food.fatGrams.clean()}g")
            }
            if (detail.isNotEmpty()) {
                Text(text = detail, color = MutedText, style = MaterialTheme.typography.bodySmall)
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = { onToggleSaved(food) }) {
                Text(
                    text = if (food.isSaved) "♥" else "♡",
                    color = if (food.isSaved) PositiveAccent else MutedText
                )
            }
            TextButton(onClick = { onAdd(food) }) {
                Text("Add", color = CyanAccent, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun MealSection(
    section: MealSectionUiState,
    onEditEntry: (FoodEntryUiState) -> Unit,
    onDeleteEntry: (Long) -> Unit,
    onToggleSaved: (Long, Boolean) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = section.mealName, color = PrimaryText, fontWeight = FontWeight.Bold)
            Text(text = "${section.entries.sumOf { it.calories }} kcal", color = CyanAccent, fontWeight = FontWeight.Bold)
        }
        if (section.entries.isEmpty()) {
            Text(text = "No foods logged.", color = MutedText, style = MaterialTheme.typography.bodySmall)
        } else {
            section.entries.forEach { entry ->
                FoodEntryRow(entry = entry, onEditEntry = onEditEntry, onDeleteEntry = onDeleteEntry, onToggleSaved = onToggleSaved)
            }
        }
    }
}

@Composable
private fun FoodEntryRow(
    entry: FoodEntryUiState,
    onEditEntry: (FoodEntryUiState) -> Unit,
    onDeleteEntry: (Long) -> Unit,
    onToggleSaved: (Long, Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(SecondaryCard.copy(alpha = 0.72f))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = entry.foodName, color = PrimaryText, fontWeight = FontWeight.Bold)
                Text(text = listOfNotNull(entry.brandName, entry.servingDescription, entry.mealTime).joinToString(" | "), color = MutedText, style = MaterialTheme.typography.bodySmall)
            }
            Text(text = "${entry.calories} kcal", color = CyanAccent, fontWeight = FontWeight.Bold)
        }
        Text(text = "P ${entry.proteinGrams.clean()}g | C ${entry.carbGrams.clean()}g | F ${entry.fatGrams.clean()}g | Fiber ${entry.fiberGrams.clean()}g", color = MutedText, style = MaterialTheme.typography.bodySmall)
        Text(text = "${if (entry.isWholeFoodBased) "Whole-food" else "Processed"}${if (entry.isFermented) " | Fermented" else ""}", color = MutedText, style = MaterialTheme.typography.bodySmall)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextButton(onClick = { onEditEntry(entry) }) { Text("Edit", color = CyanAccent) }
            TextButton(onClick = { onDeleteEntry(entry.id) }) { Text("Delete", color = MutedText) }
            TextButton(onClick = { onToggleSaved(entry.id, !entry.isSaved) }) {
                Text(
                    text = if (entry.isSaved) "♥ Saved" else "♡ Save",
                    color = if (entry.isSaved) PositiveAccent else MutedText
                )
            }
        }
    }
}

@Composable
fun AchievementsScreen() {
    Box(modifier = Modifier.fillMaxWidth()) {
        MainFeatureCard(modifier = Modifier.padding(top = 22.dp)) {
            Column(
                modifier = Modifier.padding(top = 18.dp),
                verticalArrangement = Arrangement.spacedBy(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .clip(CircleShape)
                            .background(SecondaryCard.copy(alpha = 0.35f))
                    )
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .background(AccentBlue.copy(alpha = 0.25f))
                    )
                    PlaceholderVisual(label = "Badge", size = 110.dp)
                }
                Text(text = "Your Gym Rank", color = MutedText)
                Text(text = "Grinder", color = PrimaryText, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                MacroBar(label = "XP to next level", value = "640 / 1,000 XP", progress = 0.64f, color = CyanAccent)
            }
        }
        FloatingTitlePill(text = "Achievements", modifier = Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun InsightsScreen() {
    Box(modifier = Modifier.fillMaxWidth()) {
        MainFeatureCard(modifier = Modifier.padding(top = 22.dp)) {
            Column(
                modifier = Modifier.padding(top = 18.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Text(text = "Workout Statistics", color = PrimaryText, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text(text = "Last 7 days", color = MutedText)
                Row(horizontalArrangement = Arrangement.spacedBy(14.dp), modifier = Modifier.fillMaxWidth()) {
                    StatTile("Volume", "18.4k", Modifier.weight(1f))
                    StatTile("Calories Burned", "2,420", Modifier.weight(1f))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(14.dp), modifier = Modifier.fillMaxWidth()) {
                    StatTile("Workout Time", "3h 42m", Modifier.weight(1f))
                    StatTile("Sessions", "4", Modifier.weight(1f))
                }
                PrimaryBlueButton(text = "See more insights", onClick = {}, modifier = Modifier.fillMaxWidth())
            }
        }
        FloatingTitlePill(text = "Insights", modifier = Modifier.align(Alignment.TopCenter))
    }
}

private fun String.toTitleCase(): String {
    return split(" ").joinToString(" ") { word ->
        word.lowercase().replaceFirstChar { char -> char.titlecase() }
    }
}

private fun setCountLabel(count: Int): String {
    return if (count == 1) "1 set" else "$count sets"
}

private fun Double.clean(): String {
    return if (this % 1.0 == 0.0) toInt().toString() else String.format("%.1f", this)
}

private fun NutritionUiState.proteinProgress(): Float {
    if (proteinGoalMin <= 0) return 0f
    return (proteinGrams / proteinGoalMin.toDouble()).toFloat().coerceIn(0f, 1f)
}

private fun String.filterDigits(): String {
    return filter { it.isDigit() }
}

private fun muscleGroupColor(group: String): Color {
    val g = group.lowercase()
    return when {
        "chest" in g -> Color(0xFFFF7AA2)
        "back" in g -> AccentBlue
        "leg" in g || "quad" in g || "hamstring" in g || "glute" in g -> PositiveAccent
        "shoulder" in g || "delt" in g -> Color(0xFF9C77E0)
        "arm" in g || "bicep" in g || "tricep" in g -> WarningAccent
        "core" in g || "abs" in g -> CyanAccent
        "cardio" in g || "hiit" in g -> Color(0xFFFF8C42)
        else -> MutedText
    }
}

private fun String.filterDecimal(): String {
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
