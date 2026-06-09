package com.dailyhealthcoach.ui.habits

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dailyhealthcoach.domain.model.HabitStatus
import com.dailyhealthcoach.ui.premium.FloatingTitlePill
import com.dailyhealthcoach.ui.premium.MacroBar
import com.dailyhealthcoach.ui.premium.ScreenHeader
import com.dailyhealthcoach.ui.theme.CyanAccent
import com.dailyhealthcoach.ui.theme.MainCard
import com.dailyhealthcoach.ui.theme.MutedControl
import com.dailyhealthcoach.ui.theme.MutedText
import com.dailyhealthcoach.ui.theme.PositiveAccent
import com.dailyhealthcoach.ui.theme.PrimaryText
import com.dailyhealthcoach.ui.theme.SecondaryCard
import com.dailyhealthcoach.ui.theme.WarningAccent

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
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .padding(start = 18.dp, top = 24.dp, end = 18.dp, bottom = 36.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenHeader()
            HabitSummaryCard(uiState = uiState)

            uiState.habits.forEach { habit ->
                HabitCard(
                    habit = habit,
                    onStatusSelected = onStatusSelected
                )
            }
        }
    }
}

@Composable
private fun HabitSummaryCard(uiState: HabitsUiState) {
    Box(modifier = Modifier.fillMaxWidth()) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp),
            shape = RoundedCornerShape(34.dp),
            colors = CardDefaults.cardColors(containerColor = MainCard),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(start = 24.dp, top = 34.dp, end = 24.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "${uiState.completionPercentage}% complete",
                    color = PrimaryText,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${uiState.completedCount} of ${uiState.trackableCount} daily habits complete",
                    color = MutedText,
                    style = MaterialTheme.typography.bodyMedium
                )
                MacroBar(
                    label = "Today",
                    value = "${uiState.completedCount}/${uiState.trackableCount}",
                    progress = progress(uiState.completedCount, uiState.trackableCount),
                    color = CyanAccent
                )
            }
        }
        FloatingTitlePill(text = "Habits", modifier = Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun HabitCard(
    habit: HabitRowUiState,
    onStatusSelected: (Long, HabitStatus) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = if (habit.isTodayTrackable) 6.dp else 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (habit.isTodayTrackable) {
                habit.status.cardTint()
            } else {
                SecondaryCard.copy(alpha = 0.46f)
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = habit.name,
                    color = PrimaryText,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
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
                color = MutedText
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
        color = if (isLongTermReminder) WarningAccent.copy(alpha = 0.18f) else SecondaryCard.copy(alpha = 0.8f),
        contentColor = if (isLongTermReminder) WarningAccent else CyanAccent,
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
            containerColor = MainCard.copy(alpha = 0.78f)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = "Long-term reminder. Track separately from daily completion.",
            style = MaterialTheme.typography.bodySmall,
            color = MutedText,
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
    val chipColor = if (selected) status.selectedContainerColor() else Color.Transparent
    val borderColor = if (selected) status.selectedBorderColor() else MutedControl.copy(alpha = 0.8f)
    val textColor = if (selected) status.selectedTextColor() else MutedText

    Surface(
        modifier = modifier
            .defaultMinSize(minHeight = 42.dp)
            .clip(RoundedCornerShape(999.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(999.dp),
        color = chipColor,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                color = textColor,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
            )
        }
    }
}

private fun HabitStatus.cardTint(): Color {
    return when (this) {
        HabitStatus.COMPLETE -> PositiveAccent.copy(alpha = 0.18f)
        HabitStatus.SKIPPED -> WarningAccent.copy(alpha = 0.18f)
        HabitStatus.NOT_DONE -> MainCard
    }
}

private fun HabitStatus.selectedContainerColor(): Color {
    return when (this) {
        HabitStatus.COMPLETE -> Color(0xFFE8F5EC)
        HabitStatus.SKIPPED -> Color(0xFFFFF3D6)
        HabitStatus.NOT_DONE -> Color(0xFFEDEDED)
    }
}

private fun HabitStatus.selectedBorderColor(): Color {
    return when (this) {
        HabitStatus.COMPLETE -> PositiveAccent
        HabitStatus.SKIPPED -> WarningAccent
        HabitStatus.NOT_DONE -> MutedControl
    }
}

private fun HabitStatus.selectedTextColor(): Color {
    return when (this) {
        HabitStatus.COMPLETE -> Color(0xFF2F6B3F)
        HabitStatus.SKIPPED -> Color(0xFF7A5A16)
        HabitStatus.NOT_DONE -> Color(0xFF4D4D4D)
    }
}

private fun progress(value: Int, target: Int): Float {
    if (target <= 0) return 0f
    return (value.toFloat() / target.toFloat()).coerceIn(0f, 1f)
}
