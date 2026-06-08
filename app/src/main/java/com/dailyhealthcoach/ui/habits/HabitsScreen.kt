package com.dailyhealthcoach.ui.habits

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dailyhealthcoach.domain.model.HabitStatus

@Composable
fun HabitsRoute(
    viewModel: HabitsViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    HabitsScreen(
        uiState = uiState,
        onStatusSelected = viewModel::setStatus,
        modifier = modifier
    )
}

@Composable
fun HabitsScreen(
    uiState: HabitsUiState,
    onStatusSelected: (Long, HabitStatus) -> Unit,
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
                    .padding(
                        start = 20.dp,
                        top = 20.dp,
                        end = 20.dp,
                        bottom = 72.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Habits",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold
                )

                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "${uiState.completionPercentage}% complete",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${uiState.completedCount} of ${uiState.trackableCount} today",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                uiState.habits.forEach { habit ->
                    HabitCard(
                        habit = habit,
                        onStatusSelected = onStatusSelected
                    )
                }
            }
        }
    }
}

@Composable
private fun HabitCard(
    habit: HabitRowUiState,
    onStatusSelected: (Long, HabitStatus) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = if (habit.isTodayTrackable) 2.dp else 1.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (habit.isTodayTrackable) {
                habit.status.cardTint()
            } else {
                Color(0xFFFBFAF7)
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = habit.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                FrequencyPill(
                    text = habit.targetText ?: habit.frequencyLabel,
                    isLongTermReminder = !habit.isTodayTrackable,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            Text(
                text = habit.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (habit.isTodayTrackable) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    HabitStatusChip(
                        label = "Complete",
                        status = HabitStatus.COMPLETE,
                        selected = habit.status == HabitStatus.COMPLETE,
                        onClick = { onStatusSelected(habit.id, HabitStatus.COMPLETE) },
                        modifier = Modifier.weight(1f)
                    )
                    HabitStatusChip(
                        label = "Skipped",
                        status = HabitStatus.SKIPPED,
                        selected = habit.status == HabitStatus.SKIPPED,
                        onClick = { onStatusSelected(habit.id, HabitStatus.SKIPPED) },
                        modifier = Modifier.weight(1f)
                    )
                    HabitStatusChip(
                        label = "Not Done",
                        status = HabitStatus.NOT_DONE,
                        selected = habit.status == HabitStatus.NOT_DONE,
                        onClick = { onStatusSelected(habit.id, HabitStatus.NOT_DONE) },
                        modifier = Modifier.weight(1f)
                    )
                }
            } else {
                LongTermReminderNote()
            }
        }
    }
}

@Composable
private fun FrequencyPill(
    text: String,
    isLongTermReminder: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        color = if (isLongTermReminder) Color(0xFFF0ECE3) else Color(0xFFF1F3F1),
        contentColor = if (isLongTermReminder) Color(0xFF655B48) else MaterialTheme.colorScheme.onSurfaceVariant,
        shape = RoundedCornerShape(999.dp),
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        )
    }
}

@Composable
private fun LongTermReminderNote() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF4F1EA)
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Text(
            text = "Long-term reminder. Track this separately from daily completion.",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF665E4D),
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
        )
    }
}

@Composable
private fun HabitStatusChip(
    label: String,
    status: HabitStatus,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        shape = RoundedCornerShape(999.dp),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = status.selectedContainerColor(),
            selectedLabelColor = status.selectedTextColor()
        ),
        label = {
            Text(
                text = label,
                maxLines = 1,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
            )
        },
        modifier = modifier.defaultMinSize(minHeight = 40.dp)
    )
}

private fun HabitStatus.cardTint(): Color {
    return when (this) {
        HabitStatus.COMPLETE -> Color(0xFFF6FBF7)
        HabitStatus.SKIPPED -> Color(0xFFFFFBF0)
        HabitStatus.NOT_DONE -> Color(0xFFFAFAFA)
    }
}

private fun HabitStatus.selectedContainerColor(): Color {
    return when (this) {
        HabitStatus.COMPLETE -> Color(0xFFE8F5EC)
        HabitStatus.SKIPPED -> Color(0xFFFFF3D6)
        HabitStatus.NOT_DONE -> Color(0xFFEDEDED)
    }
}

private fun HabitStatus.selectedTextColor(): Color {
    return when (this) {
        HabitStatus.COMPLETE -> Color(0xFF2F6B3F)
        HabitStatus.SKIPPED -> Color(0xFF7A5A16)
        HabitStatus.NOT_DONE -> Color(0xFF4D4D4D)
    }
}
