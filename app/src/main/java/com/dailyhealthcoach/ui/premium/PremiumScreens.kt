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
import com.dailyhealthcoach.ui.nutrition.UsualMealUiState
import com.dailyhealthcoach.ui.workout.ActivityDraft
import com.dailyhealthcoach.ui.workout.DraftWorkoutSetUiState
import com.dailyhealthcoach.ui.workout.ExerciseDetailUiState
import com.dailyhealthcoach.ui.workout.ExerciseOptionUiState
import com.dailyhealthcoach.ui.workout.RecoveryActivityDetailUiState
import com.dailyhealthcoach.ui.workout.SelectedExerciseUiState
import com.dailyhealthcoach.ui.workout.WorkoutDetailUiState
import com.dailyhealthcoach.ui.workout.WorkoutHistoryUiState
import com.dailyhealthcoach.ui.workout.WorkoutPlanUiState
import com.dailyhealthcoach.ui.workout.WorkoutUiState
import com.dailyhealthcoach.ui.workout.WorkoutViewModel
import com.dailyhealthcoach.barcode.BarcodeScannerScreen
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

    if (uiState.isScannerVisible) {
        BarcodeScannerScreen(
            onBarcodeDetected = viewModel::onBarcodeDetected,
            onBack = viewModel::hideScanner
        )
    } else {
        NutritionPlanScreen(
            uiState = uiState,
            onAddFood = viewModel::showAddForm,
            onScanBarcode = viewModel::showScanner,
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
            onQuickAddFood = viewModel::quickAddFood,
            onCopyYesterday = viewModel::copyYesterday
        )
    }
}

@Composable
fun PremiumWorkoutRoute(viewModel: WorkoutViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var showActiveWorkout by remember { mutableStateOf(false) }
    var showNonStrengthSession by remember { mutableStateOf(false) }

    PremiumWorkoutScreen(
        uiState = uiState,
        showActiveWorkout = showActiveWorkout,
        showNonStrengthSession = showNonStrengthSession,
        onStartWorkout = {
            val plan = uiState.workoutPlan
            if (plan != null && !plan.isStrengthDay) {
                viewModel.updateWorkoutName(plan.focus)
                showNonStrengthSession = true
            } else {
                viewModel.startWorkout()
                showActiveWorkout = true
            }
        },
        onStartWorkoutWithPlan = { exerciseIds ->
            viewModel.startWorkoutWithPlan(exerciseIds)
            showActiveWorkout = true
        },
        onBackToPlan = {
            viewModel.closeActiveWorkout()
            showActiveWorkout = false
            showNonStrengthSession = false
        },
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
        onSaveWorkout = {
            viewModel.saveWorkout()
            showActiveWorkout = false
        },
        onSaveRecoverySession = { drafts, notes ->
            viewModel.saveRecoverySession(drafts, notes)
            showNonStrengthSession = false
        }
    )
}

@Composable
private fun PremiumWorkoutScreen(
    uiState: WorkoutUiState,
    showActiveWorkout: Boolean,
    showNonStrengthSession: Boolean,
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
    onSaveWorkout: () -> Unit,
    onSaveRecoverySession: (List<ActivityDraft>, String) -> Unit
) {
    Column(modifier = Modifier.padding(bottom = 40.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
        when {
            showActiveWorkout -> ActiveWorkoutScreen(
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
                onSaveWorkout = onSaveWorkout
            )
            showNonStrengthSession -> PlanSessionScreen(
                workoutPlan = uiState.workoutPlan,
                onBack = onBackToPlan,
                onSaveSession = onSaveRecoverySession
            )
            uiState.selectedWorkoutDetail != null -> WorkoutDetailView(
                detail = uiState.selectedWorkoutDetail,
                onBack = onClearWorkout
            )
            else -> {
                PremiumWorkoutPlanCard(
                    workoutPlan = uiState.workoutPlan,
                    onStartWorkout = onStartWorkout,
                    onStartWorkoutWithPlan = onStartWorkoutWithPlan
                )
                WorkoutHistoryCard(workouts = uiState.recentWorkouts, onWorkoutSelected = onWorkoutSelected)
            }
        }
    }
}

@Composable
private fun PremiumWorkoutPlanCard(
    workoutPlan: WorkoutPlanUiState?,
    onStartWorkout: () -> Unit,
    onStartWorkoutWithPlan: (List<Long>) -> Unit
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
                    Text(text = "Today's plan", color = MutedText, style = MaterialTheme.typography.bodyMedium)
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
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(18.dp))
                                .background(SecondaryCard.copy(alpha = 0.45f))
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            workoutPlan.suggestedExercises.forEach { ex ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = ex.name, color = PrimaryText, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
                                    Text(text = ex.muscleGroup, color = CyanAccent, style = MaterialTheme.typography.labelSmall)
                                }
                            }
                        }
                        PrimaryBlueButton(
                            text = "Start Workout",
                            onClick = { onStartWorkoutWithPlan(workoutPlan.suggestedExercises.map { it.exerciseId }) },
                            modifier = Modifier.fillMaxWidth()
                        )
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
                        val nonStrengthLabel = when (workoutPlan.focus) {
                            "Active Recovery" -> "Start Recovery"
                            "Walking & Mobility" -> "Start Mobility"
                            "Rest Day" -> "Log Rest Day"
                            else -> "Start Workout"
                        }
                        PrimaryBlueButton(text = nonStrengthLabel, onClick = onStartWorkout, modifier = Modifier.fillMaxWidth())
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
    onSaveWorkout: () -> Unit
) {
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
            ExercisePicker(
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
                onRemoveSet = onRemoveSet
            )
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
    workoutPlan: WorkoutPlanUiState?,
    onBack: () -> Unit,
    onSaveSession: (List<ActivityDraft>, String) -> Unit
) {
    val sessionTitle = when (workoutPlan?.focus) {
        "Active Recovery" -> "Recovery Session"
        "Walking & Mobility" -> "Mobility Session"
        "Rest Day" -> "Rest Day Log"
        else -> workoutPlan?.focus ?: "Session"
    }
    var activityDrafts: List<ActivityDraft> by remember(workoutPlan) {
        mutableStateOf(
            workoutPlan?.nonStrengthActivities?.map { ActivityDraft(name = it) } ?: emptyList<ActivityDraft>()
        )
    }
    var overallNotes by remember { mutableStateOf("") }

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
                        onStatusSelected = { status ->
                            activityDrafts = activityDrafts.toMutableList().also { it[index] = draft.copy(status = status) }
                        },
                        onDurationChange = { value ->
                            activityDrafts = activityDrafts.toMutableList().also { it[index] = draft.copy(durationInput = value.filter { c -> c.isDigit() }) }
                        },
                        onRpeChange = { value ->
                            activityDrafts = activityDrafts.toMutableList().also { it[index] = draft.copy(rpeInput = value.filter { c -> c.isDigit() }.take(2)) }
                        },
                        onNotesChange = { value ->
                            activityDrafts = activityDrafts.toMutableList().also { it[index] = draft.copy(notesInput = value) }
                        }
                    )
                }
                OutlinedTextField(
                    value = overallNotes,
                    onValueChange = { overallNotes = it },
                    label = { Text("Overall notes (optional)") },
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )
                PrimaryBlueButton(
                    text = "Save Session",
                    onClick = { onSaveSession(activityDrafts, overallNotes) },
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
    onStatusSelected: (WorkoutStatus) -> Unit,
    onDurationChange: (String) -> Unit,
    onRpeChange: (String) -> Unit,
    onNotesChange: (String) -> Unit
) {
    val statusColor = when (draft.status) {
        WorkoutStatus.COMPLETED -> PositiveAccent
        WorkoutStatus.PARTIAL -> WarningAccent
        WorkoutStatus.SKIPPED -> MutedText
    }
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
                WorkoutStatus.entries.forEach { status ->
                    FilterChip(
                        selected = draft.status == status,
                        onClick = { onStatusSelected(status) },
                        shape = RoundedCornerShape(999.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = when (status) {
                                WorkoutStatus.COMPLETED -> PositiveAccent
                                WorkoutStatus.PARTIAL -> WarningAccent
                                WorkoutStatus.SKIPPED -> MutedControl
                            },
                            selectedLabelColor = PrimaryText,
                            labelColor = MutedText
                        ),
                        label = { Text(status.label, maxLines = 1) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
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

@Composable
private fun WorkoutStatusChips(
    selectedStatus: WorkoutStatus,
    onStatusSelected: (WorkoutStatus) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        WorkoutStatus.entries.forEach { status ->
            FilterChip(
                selected = selectedStatus == status,
                onClick = { onStatusSelected(status) },
                shape = RoundedCornerShape(999.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = when (status) {
                        WorkoutStatus.COMPLETED -> PositiveAccent
                        WorkoutStatus.PARTIAL -> WarningAccent
                        WorkoutStatus.SKIPPED -> MutedControl
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
                    onRemoveSet = onRemoveSet
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
    onRemoveSet: (Long, Int) -> Unit
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
                }
                Text(
                    text = setCountLabel(exercise.sets.size),
                    color = CyanAccent,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
            if (exercise.isExpanded) {
                if (exercise.sets.isEmpty()) {
                    Text(text = "No sets added yet.", color = MutedText)
                } else {
                    exercise.sets.forEach { set ->
                        SetRow(set = set, onRemove = { onRemoveSet(exercise.exerciseId, set.setNumber) })
                    }
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
                PrimaryBlueButton(text = "Add Set", onClick = { onAddSet(exercise.exerciseId) }, modifier = Modifier.fillMaxWidth())
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
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(MainCard.copy(alpha = 0.75f))
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Set ${set.setNumber}: ${set.reps ?: "-"} reps | ${set.weight ?: "-"} lb | RPE ${set.rpe ?: "-"}",
            color = PrimaryText,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        TextButton(onClick = onRemove) {
            Text(text = "Remove", color = MutedText)
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
    onBack: () -> Unit
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
                        Text(text = "< Back", color = CyanAccent, fontWeight = FontWeight.Bold)
                    }
                    Text(
                        text = detail.statusLabel,
                        color = statusColor(detail.statusLabel),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Text(text = detail.name, color = PrimaryText, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(
                    text = buildString {
                        append(detail.date)
                        if (detail.durationText.isNotEmpty()) append(" · ${detail.durationText}")
                    },
                    color = MutedText,
                    style = MaterialTheme.typography.bodySmall
                )
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
                when {
                    detail.recoveryActivities.isNotEmpty() -> {
                        detail.recoveryActivities.forEach { activity ->
                            RecoveryActivityDetailSection(activity = activity)
                        }
                    }
                    detail.exercises.isNotEmpty() -> {
                        detail.exercises.forEach { exercise ->
                            ExerciseDetailSection(exercise = exercise)
                        }
                    }
                    else -> Text(text = "No activities logged for this session.", color = MutedText)
                }
            }
        }
        FloatingTitlePill(text = "Workout Details", modifier = Modifier.align(Alignment.TopCenter))
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
        ProgressRing(progress = uiState.proteinProgress(), modifier = Modifier.size(178.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "${uiState.proteinGrams.clean()}g", color = PrimaryText, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Text(text = "protein", color = MutedText)
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatTile("Calories", uiState.calories.toString(), Modifier.weight(1f))
            StatTile("Carbs", "${uiState.carbGrams.clean()}g", Modifier.weight(1f))
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatTile("Fat", "${uiState.fatGrams.clean()}g", Modifier.weight(1f))
            StatTile("Fiber", "${uiState.fiberGrams.clean()}g", Modifier.weight(1f))
        }
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
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MacroInput("Vit A (mg)", form.vitaminA, Modifier.weight(1f)) { v -> onFormChange { it.copy(vitaminA = v.filterDecimal()) } }
                    MacroInput("Vit C (mg)", form.vitaminC, Modifier.weight(1f)) { v -> onFormChange { it.copy(vitaminC = v.filterDecimal()) } }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MacroInput("Vit D (mg)", form.vitaminD, Modifier.weight(1f)) { v -> onFormChange { it.copy(vitaminD = v.filterDecimal()) } }
                    MacroInput("B12 (mg)", form.vitaminB12, Modifier.weight(1f)) { v -> onFormChange { it.copy(vitaminB12 = v.filterDecimal()) } }
                }
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
        Text(
            text = "Your frequent foods — tap Log to choose a meal and add to today's log",
            color = MutedText,
            style = MaterialTheme.typography.bodySmall
        )
        if (meals.isEmpty()) {
            Text(
                text = "Log the same food 3 or more times to see suggestions here.",
                color = MutedText,
                style = MaterialTheme.typography.bodySmall
            )
        } else {
            meals.forEach { meal ->
                UsualMealRow(meal = meal, onLog = { onLog(meal) })
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
private fun QuickAddSection(
    title: String,
    foods: List<QuickAddFoodUiState>,
    onAdd: (QuickAddFoodUiState) -> Unit,
    onToggleSaved: (QuickAddFoodUiState) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = title, color = PrimaryText, fontWeight = FontWeight.Bold)
        foods.forEach { food ->
            QuickAddFoodRow(food = food, onAdd = onAdd, onToggleSaved = onToggleSaved)
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
