package com.supplementtracker.ui.trends

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.supplementtracker.domain.DayStats
import kotlin.math.roundToInt

@Composable
fun TrendsScreen(viewModel: TrendsViewModel, modifier: Modifier = Modifier) {
    val state by viewModel.state.collectAsState()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TrendRange.values().forEach { range ->
                    FilterChip(
                        selected = state.range == range,
                        onClick = { viewModel.setRange(range) },
                        label = { Text(range.label) }
                    )
                }
            }
        }

        item {
            val chartTitle = if (state.selectedSupplementName != null)
                "ADHERENCE · ${state.selectedSupplementName!!.uppercase()}"
            else "ADHERENCE"

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        chartTitle,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )
                    if (state.selectedSupplementId != null) {
                        TextButton(onClick = { viewModel.selectSupplement(null) }) { Text("All") }
                    }
                }
                Spacer(Modifier.height(8.dp))
                AdherenceChart(
                    dayStats = state.dayStats,
                    modifier = Modifier.fillMaxWidth().height(180.dp)
                )
            }
        }

        state.summary?.let { summary ->
            item {
                Spacer(Modifier.height(16.dp))
                SummaryCards(
                    adherencePercent = summary.adherencePercent,
                    streak = summary.currentStreak,
                    perfectDays = summary.perfectDays,
                    totalDays = summary.totalDays,
                    rangeLabel = state.range.label
                )
            }
        }

        if (state.perSupplement.isNotEmpty()) {
            item {
                Spacer(Modifier.height(16.dp))
                Text(
                    "PER SUPPLEMENT",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(4.dp))
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            }
            items(state.perSupplement, key = { it.supplementId }) { adh ->
                val isSelected = adh.supplementId == state.selectedSupplementId
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.selectSupplement(if (isSelected) null else adh.supplementId)
                        }
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primaryContainer
                            else Color.Transparent
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(adh.supplementName, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
                    Text(
                        "${adh.percent.roundToInt()}%",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = adherenceColor(adh.percent)
                    )
                }
            }
        }

        if (state.loading) {
            item {
                Box(Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun SummaryCards(
    adherencePercent: Float,
    streak: Int,
    perfectDays: Int,
    totalDays: Int,
    rangeLabel: String
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            StatCard(
                label = "$rangeLabel adherence",
                value = "${adherencePercent.roundToInt()}%",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                label = "Current streak",
                value = "$streak days",
                modifier = Modifier.weight(1f)
            )
        }
        StatCard(
            label = "Perfect days",
            value = "$perfectDays / $totalDays",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, shape = RoundedCornerShape(12.dp), tonalElevation = 2.dp) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun AdherenceChart(dayStats: List<DayStats>, modifier: Modifier = Modifier) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val gridColor = MaterialTheme.colorScheme.outlineVariant

    if (dayStats.isEmpty()) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Text("No data yet", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
        }
        return
    }

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val padT = 8f
        val padB = 8f
        val chartH = h - padT - padB

        listOf(25f, 50f, 75f, 100f).forEach { pct ->
            val y = padT + chartH * (1f - pct / 100f)
            drawLine(gridColor, Offset(0f, y), Offset(w, y), strokeWidth = 1f)
        }

        val n = dayStats.size
        val pts = dayStats.mapIndexed { i, ds ->
            val x = if (n <= 1) w / 2f else w * i / (n - 1).toFloat()
            val y = padT + chartH * (1f - ds.percentage / 100f)
            Offset(x, y)
        }

        if (pts.size >= 2) {
            val fillPath = Path().apply {
                moveTo(pts.first().x, h - padB)
                pts.forEach { lineTo(it.x, it.y) }
                lineTo(pts.last().x, h - padB)
                close()
            }
            drawPath(fillPath, primaryColor.copy(alpha = 0.12f))

            val linePath = Path().apply {
                moveTo(pts.first().x, pts.first().y)
                pts.drop(1).forEach { lineTo(it.x, it.y) }
            }
            drawPath(linePath, primaryColor, style = Stroke(width = 2.5f))
        }

        if (pts.isNotEmpty()) {
            drawCircle(primaryColor, radius = 4f, center = pts.last())
        }
    }
}

@Composable
private fun adherenceColor(pct: Float): Color {
    return when {
        pct >= 80f -> MaterialTheme.colorScheme.primary
        pct >= 50f -> MaterialTheme.colorScheme.secondary
        else -> MaterialTheme.colorScheme.error
    }
}
