package com.dailyhealthcoach.ui.progress

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    var selectedTrend by remember { mutableStateOf<TrendData?>(null) }
    val selected = selectedTrend
    if (selected != null) {
        TrendDetailScreen(
            trend = selected,
            onBack = { selectedTrend = null },
            modifier = modifier
        )
    } else {
        ProgressScreen(
            uiState = uiState,
            onNavigateBack = onNavigateBack,
            onTrendSelected = { selectedTrend = it },
            modifier = modifier
        )
    }
}

@Composable
fun ProgressScreen(
    uiState: ProgressUiState,
    onNavigateBack: () -> Unit,
    onTrendSelected: (TrendData) -> Unit,
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
                text = "Tap a metric to see its trend chart",
                style = MaterialTheme.typography.bodySmall,
                color = MutedText
            )
            val trends = listOfNotNull(
                uiState.weight,
                uiState.bodyFat,
                uiState.sleep,
                uiState.protein,
                uiState.steps,
                uiState.recovery,
                uiState.workoutFrequency,
                uiState.habitCompletion
            )
            if (trends.isEmpty()) {
                EmptyTrendsCard()
            } else {
                trends.forEach { trend ->
                    TrendCard(trend = trend, onClick = { onTrendSelected(trend) })
                }
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
private fun TrendCard(trend: TrendData, onClick: () -> Unit) {
    ElevatedCard(
        onClick = onClick,
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
                ChangeBadge(
                    label = trend.changeLabel,
                    positive = if (trend.points.size >= 5) trend.changePositive else null
                )
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
            if (trend.points.size >= 2) {
                LineChart(
                    points = trend.points,
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                )
            }
        }
    }
}

@Composable
private fun TrendDetailScreen(
    trend: TrendData,
    onBack: () -> Unit,
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = onBack) {
                    Text(text = "< Back", color = CyanAccent, fontWeight = FontWeight.SemiBold)
                }
            }
            Text(
                text = trend.label,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = PrimaryText
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = trend.currentValue,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = CyanAccent
                )
                Text(
                    text = trend.sevenDayAvg,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MutedText
                )
            }
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = MainCard),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
            ) {
                if (trend.points.size < 2) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (trend.points.isEmpty())
                                "No data yet. Keep logging to see your trend."
                            else
                                "Need at least 2 entries to draw a trend.",
                            color = MutedText,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Last ${trend.points.size} entries",
                            style = MaterialTheme.typography.bodySmall,
                            color = MutedText
                        )
                        LineChart(
                            points = trend.points,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                        )
                    }
                }
            }
            if (trend.last7.isNotEmpty()) {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.elevatedCardColors(containerColor = MainCard),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Recent entries",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = PrimaryText
                        )
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
}

@Composable
private fun LineChart(
    points: List<Float>,
    modifier: Modifier = Modifier
) {
    val lineColor = CyanAccent
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val minVal = points.min()
        val maxVal = points.max()
        val valueRange = maxVal - minVal
        val pad = 12.dp.toPx()
        val xStep = if (points.size > 1) (w - 2 * pad) / (points.size - 1) else 0f

        fun xAt(i: Int) = pad + i * xStep
        fun yAt(v: Float): Float {
            if (valueRange == 0f) return h / 2f
            return h - pad - ((v - minVal) / valueRange) * (h - 2 * pad)
        }

        if (points.size >= 2) {
            val path = Path()
            path.moveTo(xAt(0), yAt(points[0]))
            for (i in 1 until points.size) {
                path.lineTo(xAt(i), yAt(points[i]))
            }
            drawPath(
                path = path,
                color = lineColor,
                style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        for (i in points.indices) {
            drawCircle(
                color = lineColor,
                radius = 4.dp.toPx(),
                center = Offset(xAt(i), yAt(points[i]))
            )
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
