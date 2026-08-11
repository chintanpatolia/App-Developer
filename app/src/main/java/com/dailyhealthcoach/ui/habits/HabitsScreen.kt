package com.dailyhealthcoach.ui.habits

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.dailyhealthcoach.ui.premium.ProgressRing
import com.dailyhealthcoach.ui.premium.ScreenHeader
import com.dailyhealthcoach.ui.theme.AccentBlue
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
                .statusBarsPadding()
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
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MainCard),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier.padding(start = 24.dp, top = 34.dp, end = 24.dp, bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProgressRing(
                    progress = progress(uiState.completedCount, uiState.trackableCount),
                    modifier = Modifier.size(86.dp),
                    strokeWidth = 9.dp,
                    color = if (uiState.completionPercentage == 100) PositiveAccent else CyanAccent
                ) {
                    Text(
                        text = "${uiState.completionPercentage}%",
                        color = if (uiState.completionPercentage == 100) PositiveAccent else CyanAccent,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "${uiState.completedCount} of ${uiState.trackableCount} done",
                        color = PrimaryText,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (uiState.completionPercentage == 100) "All habits complete today!"
                               else "${uiState.trackableCount - uiState.completedCount} remaining",
                        color = if (uiState.completionPercentage == 100) PositiveAccent else MutedText,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
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
    val targetCardColor = if (habit.isTodayTrackable) habit.status.cardTint() else SecondaryCard.copy(alpha = 0.46f)
    val animatedCardColor by animateColorAsState(
        targetValue = targetCardColor,
        animationSpec = tween(durationMillis = 350),
        label = "habit_card_color"
    )
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = if (habit.isTodayTrackable) 4.dp else 2.dp
        ),
        colors = CardDefaults.cardColors(containerColor = animatedCardColor)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (habit.isTodayTrackable) {
                    val iconColor = habitIconColor(habit.name)
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(iconColor.copy(alpha = if (habit.status == HabitStatus.COMPLETE) 0.24f else 0.13f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = habitIconFor(habit.name),
                            contentDescription = null,
                            tint = iconColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                Text(
                    text = habit.name,
                    color = PrimaryText,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                if (habit.isTodayTrackable && habit.status == HabitStatus.COMPLETE) {
                    androidx.compose.material3.Surface(
                        shape = RoundedCornerShape(999.dp),
                        color = PositiveAccent.copy(alpha = 0.22f),
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(
                            text = "Done",
                            color = PositiveAccent,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                } else if (habit.isTodayTrackable && habit.status == HabitStatus.SKIPPED) {
                    androidx.compose.material3.Surface(
                        shape = RoundedCornerShape(999.dp),
                        color = WarningAccent.copy(alpha = 0.18f),
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(
                            text = "Skipped",
                            color = WarningAccent,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                } else {
                    FrequencyPill(
                        text = habit.targetText ?: habit.frequencyLabel,
                        isLongTermReminder = !habit.isTodayTrackable,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                } // end inner Row
            } // end outer Row

            Text(
                text = habit.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MutedText
            )

            if (habit.autoCompleteSource != null) {
                Text(
                    text = habit.autoCompleteSource,
                    style = MaterialTheme.typography.labelSmall,
                    color = CyanAccent.copy(alpha = 0.75f)
                )
            }

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
        HabitStatus.COMPLETE -> PositiveAccent.copy(alpha = 0.14f)
        HabitStatus.SKIPPED -> WarningAccent.copy(alpha = 0.10f)
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

private fun habitIconFor(name: String): ImageVector {
    val n = name.lowercase()
    return when {
        "meditat" in n || "mindful" in n || "breath" in n || "yoga" in n || "stretch" in n -> Icons.Default.SelfImprovement
        "sleep" in n || "bed" in n || "rest" in n -> Icons.Default.Bedtime
        "water" in n || "drink" in n || "hydrat" in n -> Icons.Default.WaterDrop
        "walk" in n || "run" in n || "step" in n || "jog" in n || "hike" in n -> Icons.AutoMirrored.Filled.DirectionsWalk
        "workout" in n || "gym" in n || "lift" in n || "train" in n || "exercise" in n || "strength" in n -> Icons.Default.FitnessCenter
        "read" in n || "book" in n || "learn" in n || "study" in n -> Icons.AutoMirrored.Filled.MenuBook
        "journal" in n || "diary" in n || "write" in n -> Icons.Default.Edit
        "eat" in n || "food" in n || "meal" in n || "diet" in n || "nutrition" in n -> Icons.Default.Restaurant
        "vitamin" in n || "supplement" in n || "pill" in n || "medicine" in n -> Icons.Default.Medication
        "friend" in n || "social" in n || "people" in n || "connect" in n -> Icons.Default.People
        else -> Icons.Default.TaskAlt
    }
}

private fun habitIconColor(name: String): Color {
    val palette = listOf(CyanAccent, AccentBlue, PositiveAccent, Color(0xFF9C77E0), WarningAccent, Color(0xFFFF7AA2))
    return palette[kotlin.math.abs(name.hashCode()) % palette.size]
}
