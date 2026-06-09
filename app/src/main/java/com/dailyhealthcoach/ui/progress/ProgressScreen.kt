package com.dailyhealthcoach.ui.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dailyhealthcoach.ui.theme.CyanAccent
import com.dailyhealthcoach.ui.theme.MainCard
import com.dailyhealthcoach.ui.theme.MutedText
import com.dailyhealthcoach.ui.theme.PositiveAccent
import com.dailyhealthcoach.ui.theme.PrimaryText
import com.dailyhealthcoach.ui.theme.SecondaryCard
import com.dailyhealthcoach.ui.theme.WarningAccent

@Composable
fun ProgressRoute(
    viewModel: ProgressViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    ProgressScreen(uiState = uiState, onNavigateBack = onNavigateBack, modifier = modifier)
}

@Composable
fun ProgressScreen(
    uiState: ProgressUiState,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.fillMaxSize(), color = Color.Transparent) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(start = 18.dp, top = 16.dp, end = 18.dp, bottom = 36.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProgressHeader(onNavigateBack = onNavigateBack)
            Text(
                text = "Last 7 entries per metric",
                style = MaterialTheme.typography.bodySmall,
                color = MutedText
            )
            listOfNotNull(
                uiState.weight,
                uiState.bodyFat,
                uiState.sleep,
                uiState.protein,
                uiState.recovery,
                uiState.workoutFrequency,
                uiState.habitCompletion
            ).forEach { trend ->
                TrendCard(trend = trend)
            }
            if (listOfNotNull(
                    uiState.weight, uiState.bodyFat, uiState.sleep, uiState.protein,
                    uiState.recovery, uiState.workoutFrequency, uiState.habitCompletion
                ).isEmpty()
            ) {
                EmptyTrendsCard()
            }
        }
    }
}

@Composable
private fun ProgressHeader(onNavigateBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = onNavigateBack) {
            Text(text = "< Back", color = CyanAccent, fontWeight = FontWeight.SemiBold)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = "Progress",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = PrimaryText
            )
            Text(
                text = "Trends over time",
                style = MaterialTheme.typography.bodyMedium,
                color = MutedText
            )
        }
    }
}

@Composable
private fun TrendCard(trend: TrendData) {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = trend.label,
                    style = MaterialTheme.typography.titleSmall,
                    color = MutedText
                )
                ChangeBadge(label = trend.changeLabel, positive = trend.changePositive)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = trend.currentValue,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryText
                )
                Text(
                    text = trend.sevenDayAvg,
                    style = MaterialTheme.typography.bodySmall,
                    color = MutedText
                )
            }
            if (trend.last7.isNotEmpty()) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    trend.last7.forEach { entry ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = entry.date,
                                style = MaterialTheme.typography.bodySmall,
                                color = MutedText
                            )
                            Text(
                                text = entry.displayValue,
                                style = MaterialTheme.typography.bodySmall,
                                color = PrimaryText
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ChangeBadge(label: String, positive: Boolean?) {
    val color = when (positive) {
        true -> PositiveAccent
        false -> WarningAccent
        null -> MutedText
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SecondaryCard)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = color)
    }
}

@Composable
private fun EmptyTrendsCard() {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MainCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "No trend data yet", color = PrimaryText, fontWeight = FontWeight.Bold)
            Text(
                text = "Log body metrics, nutrition, workouts, and habits for a few days to see your trends here.",
                style = MaterialTheme.typography.bodyMedium,
                color = MutedText
            )
        }
    }
}
