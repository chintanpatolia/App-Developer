package com.dailyhealthcoach.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import com.dailyhealthcoach.healthconnect.HcStatus
import com.dailyhealthcoach.healthconnect.HealthConnectManager
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dailyhealthcoach.ui.theme.AccentBlue
import com.dailyhealthcoach.ui.theme.CyanAccent
import com.dailyhealthcoach.ui.theme.MainCard
import com.dailyhealthcoach.ui.theme.MutedText
import com.dailyhealthcoach.ui.theme.PositiveAccent
import com.dailyhealthcoach.ui.theme.PrimaryText
import com.dailyhealthcoach.ui.theme.SecondaryCard
import com.dailyhealthcoach.ui.theme.WarningAccent

private data class HcDataState(
    val steps: Long? = null,
    val sleepHours: Double? = null,
    val weightLbs: Double? = null,
    val restingHr: Int? = null,
    val loaded: Boolean = false
)

@Composable
fun ProfileRoute(
    viewModel: ProfileViewModel,
    onBack: () -> Unit,
    onNavigateToReminders: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    ProfileScreen(
        uiState = uiState,
        onUpdate = viewModel::updateForm,
        onSave = viewModel::save,
        onBack = onBack,
        onNavigateToReminders = onNavigateToReminders,
        modifier = modifier
    )
}

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onUpdate: ((ProfileUiState) -> ProfileUiState) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit,
    onNavigateToReminders: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MainCard)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
                .padding(start = 18.dp, top = 16.dp, end = 18.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onBack) {
                    Text("← Back", color = CyanAccent)
                }
                Text(
                    text = "Settings & Profile",
                    color = PrimaryText,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Box(modifier = Modifier.padding(horizontal = 16.dp))
            }

            ProfileCard(title = "Personal Info") {
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = { v -> onUpdate { it.copy(name = v) } },
                    label = { Text("Name (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = uiState.age,
                        onValueChange = { v -> onUpdate { it.copy(age = v.filter { c -> c.isDigit() }) } },
                        label = { Text("Age (optional)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Sex", color = MutedText, style = MaterialTheme.typography.bodySmall)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf("Male", "Female").forEach { option ->
                                FilterChip(
                                    selected = uiState.sex == option,
                                    onClick = { onUpdate { it.copy(sex = option) } },
                                    label = { Text(option, maxLines = 1) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = AccentBlue,
                                        selectedLabelColor = PrimaryText,
                                        labelColor = MutedText
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf("Other", "Prefer not").forEach { option ->
                                FilterChip(
                                    selected = uiState.sex == option,
                                    onClick = { onUpdate { it.copy(sex = option) } },
                                    label = { Text(option, maxLines = 1) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = AccentBlue,
                                        selectedLabelColor = PrimaryText,
                                        labelColor = MutedText
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
                Text("Height", color = MutedText, style = MaterialTheme.typography.bodySmall)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = uiState.heightFeet,
                        onValueChange = { v -> onUpdate { it.copy(heightFeet = v.filter { c -> c.isDigit() }) } },
                        label = { Text("Feet (3–8)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = uiState.heightInches,
                        onValueChange = { v -> onUpdate { it.copy(heightInches = v.filter { c -> c.isDigit() }) } },
                        label = { Text("Inches (0–11)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }
            }

            ProfileCard(title = "Body Goals") {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = uiState.weightGoal,
                        onValueChange = { v -> onUpdate { it.copy(weightGoal = v.filterDecimal()) } },
                        label = { Text("Weight goal (lbs)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = uiState.bodyFatGoal,
                        onValueChange = { v -> onUpdate { it.copy(bodyFatGoal = v.filterDecimal()) } },
                        label = { Text("Body fat goal (%)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }
            }

            ProfileCard(title = "Nutrition Targets") {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = uiState.proteinMin,
                        onValueChange = { v -> onUpdate { it.copy(proteinMin = v.filter { c -> c.isDigit() }) } },
                        label = { Text("Protein min (g/day)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = uiState.proteinMax,
                        onValueChange = { v -> onUpdate { it.copy(proteinMax = v.filter { c -> c.isDigit() }) } },
                        label = { Text("Protein max (g/day)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }
                Text("Daily protein target range in grams.", color = MutedText, style = MaterialTheme.typography.bodySmall)
            }

            ProfileCard(title = "Activity Targets") {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = uiState.stepMin,
                        onValueChange = { v -> onUpdate { it.copy(stepMin = v.filter { c -> c.isDigit() }) } },
                        label = { Text("Steps min/day") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = uiState.stepMax,
                        onValueChange = { v -> onUpdate { it.copy(stepMax = v.filter { c -> c.isDigit() }) } },
                        label = { Text("Steps max/day") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }
                Text("Dashboard step goal uses the minimum target.", color = MutedText, style = MaterialTheme.typography.bodySmall)
            }

            ProfileCard(title = "Sleep & Recovery") {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = uiState.sleepTarget,
                        onValueChange = { v -> onUpdate { it.copy(sleepTarget = v.filterDecimal()) } },
                        label = { Text("Sleep target (hrs)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = uiState.strengthTarget,
                        onValueChange = { v -> onUpdate { it.copy(strengthTarget = v.filter { c -> c.isDigit() }) } },
                        label = { Text("Strength days/week") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }
                Text("Sleep 4–12 hrs. Strength 1–7 days/week.", color = MutedText, style = MaterialTheme.typography.bodySmall)
            }

            HealthConnectCard()

            ProfileCard(title = "Reminders") {
                Text(
                    "Set daily reminders for habits, workouts, and check-ins.",
                    color = MutedText,
                    style = MaterialTheme.typography.bodySmall
                )
                OutlinedButton(
                    onClick = onNavigateToReminders,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text("Manage Reminders →", color = CyanAccent)
                }
            }

            if (uiState.error != null) {
                Text(
                    text = uiState.error,
                    color = WarningAccent,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
            if (uiState.savedSuccess) {
                Text(
                    text = "Profile saved.",
                    color = PositiveAccent,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }

            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(999.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
            ) {
                Text("Save Profile", color = PrimaryText, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 6.dp))
            }
        }
    }
}

@Composable
private fun ProfileCard(
    title: String,
    content: @Composable () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = SecondaryCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = title, color = CyanAccent, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            content()
        }
    }
}

@Composable
private fun HealthConnectCard() {
    val context = LocalContext.current
    val status = remember { HealthConnectManager.getSdkStatus(context) }
    ProfileCard(title = "Health Connect") {
        when (status) {
            HcStatus.UNAVAILABLE -> {
                Text(
                    "Health Connect is not available on this device. Install the Health Connect app to enable automatic data import.",
                    color = MutedText,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            HcStatus.AVAILABLE -> {
                var launchFailed by remember { mutableStateOf(false) }
                HcDataPreview()
                OutlinedButton(
                    onClick = {
                        launchFailed = !HealthConnectManager.openSettings(context)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text("Open Health Connect →", color = CyanAccent)
                }
                if (launchFailed) {
                    Text(
                        "Could not open Health Connect. It may not be installed on this device.",
                        color = WarningAccent,
                        style = MaterialTheme.typography.bodySmall
                    )
                } else {
                    Text(
                        "Grant permissions for this app in Health Connect, then return here.",
                        color = MutedText,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
private fun HcDataPreview() {
    val context = LocalContext.current
    val data by produceState(HcDataState()) {
        value = HcDataState(
            steps = HealthConnectManager.readTodaySteps(context),
            sleepHours = HealthConnectManager.readLastSleepHours(context),
            weightLbs = HealthConnectManager.readLastWeightPounds(context),
            restingHr = HealthConnectManager.readLastRestingHeartRate(context),
            loaded = true
        )
    }

    if (!data.loaded) return

    val hasAny = data.steps != null || data.sleepHours != null ||
            data.weightLbs != null || data.restingHr != null

    if (!hasAny) {
        Text(
            "No Health Connect data found yet. Grant permissions in Health Connect to import your steps, sleep, weight, and resting heart rate.",
            color = MutedText,
            style = MaterialTheme.typography.bodySmall
        )
        return
    }

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        HcDataRow("Steps today", data.steps?.let { "%,d steps".format(it) })
        HcDataRow("Sleep last night", data.sleepHours?.let { "%.1f h".format(it) })
        HcDataRow("Weight (latest)", data.weightLbs?.let { "%.1f lbs".format(it) })
        HcDataRow("Resting HR", data.restingHr?.let { "$it bpm" })
    }
    Text(
        "Read-only. Edit values manually or update in Health Connect.",
        color = MutedText,
        style = MaterialTheme.typography.bodySmall
    )
}

@Composable
private fun HcDataRow(label: String, value: String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = MutedText, style = MaterialTheme.typography.bodySmall)
        Text(
            text = value ?: "—",
            color = PrimaryText,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )
    }
}

private fun String.filterDecimal(): String {
    var hasDot = false
    return filter { c ->
        when {
            c.isDigit() -> true
            c == '.' && !hasDot -> { hasDot = true; true }
            else -> false
        }
    }
}
