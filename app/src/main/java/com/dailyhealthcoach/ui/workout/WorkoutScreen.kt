package com.dailyhealthcoach.ui.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dailyhealthcoach.domain.model.WorkoutStatus

@Composable
fun WorkoutRoute(
    viewModel: WorkoutViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    WorkoutScreen(
        uiState = uiState,
        onStartWorkout = viewModel::startWorkout,
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
        onSaveWorkout = viewModel::saveWorkout,
        modifier = modifier
    )
}

@Composable
fun WorkoutScreen(
    uiState: WorkoutUiState,
    onStartWorkout: () -> Unit,
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
    modifier: Modifier = Modifier
) {
    Scaffold { paddingValues ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .navigationBarsPadding()
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 72.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Workout",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold
                )

                if (uiState.isWorkoutStarted) {
                    ActiveWorkout(
                        uiState = uiState,
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
                    StartWorkoutCard(onStartWorkout = onStartWorkout)
                }

                RecentWorkoutHistory(workouts = uiState.recentWorkouts)
            }
        }
    }
}

@Composable
private fun StartWorkoutCard(onStartWorkout: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7FAF8))
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Ready to train?", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
            Text(
                text = "Start a workout, choose exercises, then add sets inside each exercise card.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Button(onClick = onStartWorkout, shape = RoundedCornerShape(999.dp)) {
                Text("Start New Workout")
            }
        }
    }
}

@Composable
private fun ActiveWorkout(
    uiState: WorkoutUiState,
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
    WorkoutDetailsCard(
        uiState = uiState,
        onWorkoutNameChange = onWorkoutNameChange,
        onDurationChange = onDurationChange,
        onOverallRpeChange = onOverallRpeChange,
        onStatusSelected = onStatusSelected
    )

    ExerciseSelector(
        exercises = uiState.exercises,
        selectedExerciseIds = uiState.selectedExercises.map { it.exerciseId }.toSet(),
        onExerciseSelected = onExerciseSelected
    )

    if (uiState.selectedExercises.isEmpty()) {
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAF8))
        ) {
            Text(
                text = "Select an exercise above to add sets.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(20.dp)
            )
        }
    }

    uiState.selectedExercises.forEach { exercise ->
        SelectedExerciseCard(
            exercise = exercise,
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

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAF8))
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            OutlinedTextField(
                value = uiState.workoutNotes,
                onValueChange = onWorkoutNotesChange,
                label = { Text("Overall workout notes") },
                minLines = 2,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = onSaveWorkout,
                shape = RoundedCornerShape(999.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Workout")
            }
        }
    }
}

@Composable
private fun WorkoutDetailsCard(
    uiState: WorkoutUiState,
    onWorkoutNameChange: (String) -> Unit,
    onDurationChange: (String) -> Unit,
    onOverallRpeChange: (String) -> Unit,
    onStatusSelected: (WorkoutStatus) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7FAF8))
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Current workout", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = uiState.workoutName,
                onValueChange = onWorkoutNameChange,
                label = { Text("Workout name") },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = uiState.durationMinutes,
                    onValueChange = onDurationChange,
                    label = { Text("Minutes") },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = uiState.overallRpe,
                    onValueChange = onOverallRpeChange,
                    label = { Text("RPE 1-10") },
                    singleLine = true,
                    isError = uiState.overallRpeError != null,
                    supportingText = uiState.overallRpeError?.let { { Text(it) } },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.weight(1f)
                )
            }
            WorkoutStatusSelector(uiState.selectedStatus, onStatusSelected)
        }
    }
}

@Composable
private fun WorkoutStatusSelector(
    selectedStatus: WorkoutStatus,
    onStatusSelected: (WorkoutStatus) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Workout status", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            WorkoutStatus.entries.filter { it != WorkoutStatus.NOT_STARTED }.forEach { status ->
                FilterChip(
                    selected = selectedStatus == status,
                    onClick = { onStatusSelected(status) },
                    shape = RoundedCornerShape(999.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = status.statusColor(),
                        selectedLabelColor = Color(0xFF3F4F45)
                    ),
                    label = {
                        Text(
                            text = status.label,
                            maxLines = 1,
                            fontWeight = if (selectedStatus == status) FontWeight.SemiBold else FontWeight.Normal
                        )
                    },
                    modifier = Modifier.weight(1f).defaultMinSize(minHeight = 40.dp)
                )
            }
        }
    }
}

@Composable
private fun ExerciseSelector(
    exercises: List<ExerciseOptionUiState>,
    selectedExerciseIds: Set<Long>,
    onExerciseSelected: (Long) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAF8))
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Exercise library", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            exercises.forEach { exercise ->
                val selected = exercise.id in selectedExerciseIds
                OutlinedButton(
                    onClick = { onExerciseSelected(exercise.id) },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (selected) Color(0xFFEFF6F1) else Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(exercise.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                        Text(
                            text = "${exercise.muscleGroup} | ${exercise.equipmentType} | ${exercise.movementPattern}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectedExerciseCard(
    exercise: SelectedExerciseUiState,
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
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7FAF8))
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(exercise.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Text(
                        text = "${exercise.muscleGroup} | ${exercise.equipmentType} | ${exercise.movementPattern.toTitleCase()}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                TextButton(onClick = { onExerciseExpandedToggle(exercise.exerciseId) }) {
                    Text(if (exercise.isExpanded) "Collapse" else "${exercise.sets.size} sets")
                }
            }

            if (exercise.isExpanded) {
                ExerciseSetsList(
                    sets = exercise.sets,
                    onRemoveSet = { setNumber -> onRemoveSet(exercise.exerciseId, setNumber) }
                )

                Text("Add set", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = exercise.repsInput,
                        onValueChange = { onSetRepsChange(exercise.exerciseId, it) },
                        label = { Text("Reps") },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = exercise.weightInput,
                        onValueChange = { onSetWeightChange(exercise.exerciseId, it) },
                        label = { Text("Weight") },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = exercise.rpeInput,
                        onValueChange = { onSetRpeChange(exercise.exerciseId, it) },
                        label = { Text("RPE") },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.weight(1f)
                    )
                }
                OutlinedTextField(
                    value = exercise.setNotesInput,
                    onValueChange = { onSetNotesChange(exercise.exerciseId, it) },
                    label = { Text("Set notes") },
                    minLines = 2,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = { onAddSet(exercise.exerciseId) },
                    shape = RoundedCornerShape(999.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Set")
                }
                OutlinedTextField(
                    value = exercise.exerciseNotes,
                    onValueChange = { onExerciseNotesChange(exercise.exerciseId, it) },
                    label = { Text("Exercise notes") },
                    minLines = 2,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun ExerciseSetsList(
    sets: List<DraftWorkoutSetUiState>,
    onRemoveSet: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (sets.isEmpty()) {
            Text(
                text = "No sets added for this exercise yet.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            sets.forEach { set ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "Set ${set.setNumber}: ${set.repsText.ifBlank { "-" }} reps | ${set.weightText.ifBlank { "-" }} | RPE ${set.rpeText.ifBlank { "-" }}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(onClick = { onRemoveSet(set.setNumber) }) {
                        Text("Remove")
                    }
                }
                if (!set.notes.isNullOrBlank()) {
                    Text(
                        text = set.notes,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun RecentWorkoutHistory(workouts: List<WorkoutHistoryUiState>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Recent workouts", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
        if (workouts.isEmpty()) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
            ) {
                Text(
                    text = "Saved workouts will appear here.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(20.dp)
                )
            }
        } else {
            workouts.forEach { workout ->
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAF8))
                ) {
                    Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = workout.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = workout.statusLabel,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                        Text(
                            text = "${workout.date} | ${workout.durationText} | ${workout.setCount} sets",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = workout.muscleGroups,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

private fun WorkoutStatus.statusColor(): Color {
    return when (this) {
        WorkoutStatus.NOT_STARTED -> Color(0xFFEDEDED)
        WorkoutStatus.COMPLETED -> Color(0xFFE8F5EC)
        WorkoutStatus.PARTIAL -> Color(0xFFFFF3D6)
        WorkoutStatus.SKIPPED -> Color(0xFFEDEDED)
    }
}

private fun String.toTitleCase(): String {
    return split(" ").joinToString(" ") { word ->
        word.lowercase().replaceFirstChar { char -> char.titlecase() }
    }
}
