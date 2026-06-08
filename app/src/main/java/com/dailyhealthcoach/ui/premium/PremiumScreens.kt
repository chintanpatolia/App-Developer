package com.dailyhealthcoach.ui.premium

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.dailyhealthcoach.ui.workout.DraftWorkoutSetUiState
import com.dailyhealthcoach.ui.workout.ExerciseOptionUiState
import com.dailyhealthcoach.ui.workout.SelectedExerciseUiState
import com.dailyhealthcoach.ui.workout.WorkoutHistoryUiState
import com.dailyhealthcoach.ui.workout.WorkoutUiState
import com.dailyhealthcoach.ui.workout.WorkoutViewModel

@Composable
fun NutritionRoute(viewModel: NutritionViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    NutritionPlanScreen(
        uiState = uiState,
        onAddFood = viewModel::showAddForm,
        onCancelForm = viewModel::hideForm,
        onSaveForm = viewModel::saveForm,
        onEditEntry = viewModel::editEntry,
        onDeleteEntry = viewModel::deleteEntry,
        onFormChange = viewModel::updateForm
    )
}

@Composable
fun PremiumWorkoutRoute(viewModel: WorkoutViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var showActiveWorkout by remember { mutableStateOf(false) }

    PremiumWorkoutScreen(
        uiState = uiState,
        showActiveWorkout = showActiveWorkout,
        onStartWorkout = {
            viewModel.startWorkout()
            showActiveWorkout = true
        },
        onBackToPlan = {
            viewModel.closeActiveWorkout()
            showActiveWorkout = false
        },
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
        }
    )
}

@Composable
private fun PremiumWorkoutScreen(
    uiState: WorkoutUiState,
    showActiveWorkout: Boolean,
    onStartWorkout: () -> Unit,
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
    Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
        if (showActiveWorkout) {
            ActiveWorkoutScreen(
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
        } else {
            PremiumWorkoutPlanCard(onStartWorkout = onStartWorkout)
            RecentHistoryCard(workouts = uiState.recentWorkouts)
        }
    }
}

@Composable
private fun PremiumWorkoutPlanCard(onStartWorkout: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        MainFeatureCard(modifier = Modifier.padding(top = 22.dp)) {
            Column(
                modifier = Modifier.padding(top = 14.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Next workout", color = MutedText, style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "Buttocks & Hamstrings",
                    color = PrimaryText,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                PlaceholderVisual(label = "Muscle\nfocus", size = 168.dp)
                Surface(shape = RoundedCornerShape(24.dp), color = SecondaryCard.copy(alpha = 0.75f)) {
                    Text(
                        text = "Target area: Glutes, hamstrings, posterior chain",
                        color = MutedText,
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp)
                    )
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    StatTile(label = "Duration", value = "34 min", modifier = Modifier.weight(1f))
                    StatTile(label = "Exercises", value = "4", modifier = Modifier.weight(1f))
                }
                PrimaryBlueButton(text = "Start Workout", onClick = onStartWorkout, modifier = Modifier.fillMaxWidth())
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
                    label = { Text("RPE") },
                    singleLine = true,
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
        shape = RoundedCornerShape(22.dp),
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
        shape = RoundedCornerShape(26.dp),
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
private fun RecentHistoryCard(workouts: List<WorkoutHistoryUiState>) {
    MainFeatureCard {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(text = "Recent workouts", color = PrimaryText, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            if (workouts.isEmpty()) {
                Text(text = "Saved workouts will appear here.", color = MutedText)
            } else {
                workouts.take(4).forEach { workout ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(22.dp))
                            .background(SecondaryCard.copy(alpha = 0.72f))
                            .padding(14.dp)
                    ) {
                        Text(text = workout.name, color = PrimaryText, fontWeight = FontWeight.Bold)
                        Text(
                            text = "${workout.date} | ${workout.durationText} | ${workout.setCount} sets",
                            color = MutedText,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(text = workout.muscleGroups, color = CyanAccent)
                    }
                }
            }
        }
    }
}

@Composable
fun NutritionPlanScreen(
    uiState: NutritionUiState,
    onAddFood: () -> Unit,
    onCancelForm: () -> Unit,
    onSaveForm: () -> Unit,
    onEditEntry: (FoodEntryUiState) -> Unit,
    onDeleteEntry: (Long) -> Unit,
    onFormChange: ((FoodEntryFormUiState) -> FoodEntryFormUiState) -> Unit
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
                PrimaryBlueButton(text = "Add Food", onClick = onAddFood, modifier = Modifier.fillMaxWidth())
                if (uiState.isFormVisible) {
                    FoodEntryForm(
                        form = uiState.form,
                        onFormChange = onFormChange,
                        onSave = onSaveForm,
                        onCancel = onCancelForm
                    )
                }
                uiState.mealSections.forEach { section ->
                    MealSection(
                        section = section,
                        onEditEntry = onEditEntry,
                        onDeleteEntry = onDeleteEntry
                    )
                }
            }
        }
        FloatingTitlePill(text = "Nutrition Plan", modifier = Modifier.align(Alignment.TopCenter))
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
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = SecondaryCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(text = if (form.editingId == 0L) "Manual food entry" else "Edit food", color = PrimaryText, fontWeight = FontWeight.Bold)
            MealChips(selectedMeal = form.mealName, onMealSelected = { meal -> onFormChange { it.copy(mealName = meal) } })
            OutlinedTextField(value = form.foodName, onValueChange = { value -> onFormChange { it.copy(foodName = value) } }, label = { Text("Food name") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            OutlinedTextField(value = form.brandName, onValueChange = { value -> onFormChange { it.copy(brandName = value) } }, label = { Text("Brand optional") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            OutlinedTextField(value = form.servingDescription, onValueChange = { value -> onFormChange { it.copy(servingDescription = value) } }, label = { Text("Serving size") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
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
private fun MealSection(
    section: MealSectionUiState,
    onEditEntry: (FoodEntryUiState) -> Unit,
    onDeleteEntry: (Long) -> Unit
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
                FoodEntryRow(entry = entry, onEditEntry = onEditEntry, onDeleteEntry = onDeleteEntry)
            }
        }
    }
}

@Composable
private fun FoodEntryRow(
    entry: FoodEntryUiState,
    onEditEntry: (FoodEntryUiState) -> Unit,
    onDeleteEntry: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
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
fun BodyScreen() {
    Box(modifier = Modifier.fillMaxWidth()) {
        MainFeatureCard(modifier = Modifier.padding(top = 22.dp)) {
            Column(
                modifier = Modifier.padding(top = 18.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProgressRing(progress = 0.58f, modifier = Modifier.size(180.dp)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "204.9 lb", color = PrimaryText, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Text(text = "current", color = MutedText)
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MeasurementTile("Goal", "186.9", Modifier.weight(1f))
                    MeasurementTile("Remaining", "18.0", Modifier.weight(1f))
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MeasurementTile("Chest", "42 in", Modifier.weight(1f))
                    MeasurementTile("Waist", "36 in", Modifier.weight(1f))
                    MeasurementTile("Arms", "15 in", Modifier.weight(1f))
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    PhotoTile("Front", Modifier.weight(1f))
                    PhotoTile("Side", Modifier.weight(1f))
                    PhotoTile("Back", Modifier.weight(1f))
                }
            }
        }
        FloatingTitlePill(text = "Body", modifier = Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun PhotoTile(label: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(100.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(SecondaryCard.copy(alpha = 0.72f)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, color = MutedText, fontWeight = FontWeight.SemiBold)
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
