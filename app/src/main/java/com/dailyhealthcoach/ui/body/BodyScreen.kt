package com.dailyhealthcoach.ui.body

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dailyhealthcoach.ui.premium.FloatingTitlePill
import com.dailyhealthcoach.ui.premium.MainFeatureCard
import com.dailyhealthcoach.ui.premium.PrimaryBlueButton
import com.dailyhealthcoach.ui.premium.ScreenHeader
import com.dailyhealthcoach.ui.premium.StatTile
import com.dailyhealthcoach.ui.theme.CyanAccent
import com.dailyhealthcoach.ui.theme.MainCard
import com.dailyhealthcoach.ui.theme.MutedText
import com.dailyhealthcoach.ui.theme.PrimaryText
import com.dailyhealthcoach.ui.theme.SecondaryCard

@Composable
fun BodyRoute(
    viewModel: BodyViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    BodyScreen(
        uiState = uiState,
        onFormChange = viewModel::updateForm,
        onSave = viewModel::saveMetrics,
        modifier = modifier
    )
}

@Composable
fun BodyScreen(
    uiState: BodyUiState,
    onFormChange: ((BodyMetricFormUiState) -> BodyMetricFormUiState) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.fillMaxSize(), color = Color.Transparent) {
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
            TodaySummaryCard(today = uiState.today)
            MetricsFormCard(
                form = uiState.form,
                onFormChange = onFormChange,
                onSave = onSave
            )
            RecentHistoryCard(logs = uiState.recentLogs)
        }
    }
}

@Composable
private fun TodaySummaryCard(today: BodyMetricLogUiState?) {
    Box(modifier = Modifier.fillMaxWidth()) {
        MainFeatureCard(modifier = Modifier.padding(top = 22.dp)) {
            Column(
                modifier = Modifier.padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatTile("Weight", today?.bodyWeight.value("lb"), Modifier.weight(1f))
                    StatTile("Body Fat", today.bodyFatDisplay(), Modifier.weight(1f))
                    StatTile("Sleep", today?.sleepHours.value("h"), Modifier.weight(1f))
                }
                Text(
                    text = today.bodyFatSourceLabel(),
                    color = MutedText,
                    style = MaterialTheme.typography.bodySmall
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatTile("Energy", today?.energyLevel.level(), Modifier.weight(1f))
                    StatTile("Stress", today?.stressLevel.level(), Modifier.weight(1f))
                    StatTile("Sore", today?.sorenessLevel.level(), Modifier.weight(1f))
                }
            }
        }
        FloatingTitlePill(text = "Body Metrics", modifier = Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun MetricsFormCard(
    form: BodyMetricFormUiState,
    onFormChange: ((BodyMetricFormUiState) -> BodyMetricFormUiState) -> Unit,
    onSave: () -> Unit
) {
    MainFeatureCard {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Text(
                text = "Add / update today's metrics",
                color = PrimaryText,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                MetricInput("Weight", form.bodyWeight, Modifier.weight(1f)) {
                    onFormChange { state -> state.copy(bodyWeight = it.filterDecimal()) }
                }
                MetricInput("Feet", form.heightFeet, Modifier.weight(1f)) {
                    onFormChange { state -> state.copy(heightFeet = it.filterDigits().take(1)) }
                }
                MetricInput("Inches", form.heightInches, Modifier.weight(1f)) {
                    onFormChange { state -> state.copy(heightInches = it.filterDigits().take(2)) }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                MetricInput("Waist", form.waistMeasurement, Modifier.weight(1f)) {
                    onFormChange { state -> state.copy(waistMeasurement = it.filterDecimal()) }
                }
                MetricInput("Neck", form.neckMeasurement, Modifier.weight(1f)) {
                    onFormChange { state -> state.copy(neckMeasurement = it.filterDecimal()) }
                }
            }
            BodyFatEstimateSection(form = form, onFormChange = onFormChange)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                MetricInput("Chest", form.chestMeasurement, Modifier.weight(1f)) {
                    onFormChange { state -> state.copy(chestMeasurement = it.filterDecimal()) }
                }
                MetricInput("Arm", form.armMeasurement, Modifier.weight(1f)) {
                    onFormChange { state -> state.copy(armMeasurement = it.filterDecimal()) }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                MetricInput("Sleep", form.sleepHours, Modifier.weight(1f)) {
                    onFormChange { state -> state.copy(sleepHours = it.filterDecimal()) }
                }
                MetricInput("Steps", form.stepCount, Modifier.weight(1f)) {
                    onFormChange { state -> state.copy(stepCount = it.filterDigits()) }
                }
                MetricInput("RHR", form.restingHeartRate, Modifier.weight(1f)) {
                    onFormChange { state -> state.copy(restingHeartRate = it.filterDigits()) }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                MetricInput("Energy 1-10", form.energyLevel, Modifier.weight(1f)) {
                    onFormChange { state -> state.copy(energyLevel = it.filterDigits().take(2)) }
                }
                MetricInput("Stress 1-10", form.stressLevel, Modifier.weight(1f)) {
                    onFormChange { state -> state.copy(stressLevel = it.filterDigits().take(2)) }
                }
                MetricInput("Sore 1-10", form.sorenessLevel, Modifier.weight(1f)) {
                    onFormChange { state -> state.copy(sorenessLevel = it.filterDigits().take(2)) }
                }
            }
            OutlinedTextField(
                value = form.notes,
                onValueChange = { value -> onFormChange { it.copy(notes = value) } },
                label = { Text("Notes") },
                minLines = 2,
                modifier = Modifier.fillMaxWidth()
            )
            PrimaryBlueButton(text = "Save Metrics", onClick = onSave, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun BodyFatEstimateSection(
    form: BodyMetricFormUiState,
    onFormChange: ((BodyMetricFormUiState) -> BodyMetricFormUiState) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SecondaryCard.copy(alpha = 0.72f)),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (form.isBodyFatOverridden) "Manual Body Fat %" else "Estimated Body Fat %",
                        color = PrimaryText,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = form.bodyFatHelperText,
                        color = MutedText,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = form.isBodyFatOverridden,
                        onCheckedChange = { checked -> onFormChange { it.copy(isBodyFatOverridden = checked) } }
                    )
                    Text(text = "Override", color = MutedText, style = MaterialTheme.typography.bodySmall)
                }
            }
            if (form.isBodyFatOverridden) {
                MetricInput("Manual body fat %", form.bodyFatPercentage, Modifier.fillMaxWidth()) {
                    onFormChange { state -> state.copy(bodyFatPercentage = it.filterDecimal()) }
                }
            } else {
                Surface(shape = RoundedCornerShape(18.dp), color = MainCard.copy(alpha = 0.8f)) {
                    Text(
                        text = form.calculatedBodyFatPercent.value("%"),
                        color = CyanAccent,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                    )
                }
            }
            Text(
                text = "Body fat percentage is an estimate and may differ from clinical or scan-based measurements.",
                color = MutedText,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun MetricInput(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}

@Composable
private fun RecentHistoryCard(logs: List<BodyMetricLogUiState>) {
    MainFeatureCard {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = "Recent history",
                color = PrimaryText,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            if (logs.isEmpty()) {
                Text(text = "Saved body metrics will appear here.", color = MutedText)
            } else {
                logs.forEach { log ->
                    HistoryRow(log = log)
                }
            }
        }
    }
}

@Composable
private fun HistoryRow(log: BodyMetricLogUiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(SecondaryCard.copy(alpha = 0.72f))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = log.date, color = PrimaryText, fontWeight = FontWeight.Bold)
            Text(text = log.bodyWeight.value("lb"), color = CyanAccent, fontWeight = FontWeight.Bold)
        }
        Text(
            text = "Body fat ${log.bodyFatDisplay()} ${log.bodyFatSourceShort()} | Sleep ${log.sleepHours.value("h")} | Energy ${log.energyLevel.level()} | Stress ${log.stressLevel.level()} | Sore ${log.sorenessLevel.level()}",
            color = MutedText,
            style = MaterialTheme.typography.bodySmall
        )
        if (!log.notes.isNullOrBlank()) {
            Text(text = log.notes, color = MutedText, style = MaterialTheme.typography.bodySmall)
        }
    }
}

private fun Double?.value(unit: String): String {
    if (this == null) return "-"
    val cleaned = String.format("%.1f", this)
    return "$cleaned $unit"
}

private fun BodyMetricLogUiState?.bodyFatDisplay(): String {
    return this?.bodyFatPercentage.value("%")
}

private fun BodyMetricLogUiState?.bodyFatSourceLabel(): String {
    if (this?.bodyFatPercentage == null) return "Body fat estimate needs height, waist, and neck."
    return if (isBodyFatOverridden) {
        "Manual"
    } else {
        "Estimated"
    }
}

private fun BodyMetricLogUiState.bodyFatSourceShort(): String {
    if (bodyFatPercentage == null) return ""
    return if (isBodyFatOverridden) "(manual)" else "(estimated)"
}

private fun Int?.level(): String {
    return this?.let { "$it/10" } ?: "-"
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
