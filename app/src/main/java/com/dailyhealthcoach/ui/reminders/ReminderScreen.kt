package com.dailyhealthcoach.ui.reminders

import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.dailyhealthcoach.notifications.ReminderType
import com.dailyhealthcoach.ui.theme.AccentBlue
import com.dailyhealthcoach.ui.theme.CyanAccent
import com.dailyhealthcoach.ui.theme.MainCard
import com.dailyhealthcoach.ui.theme.MutedText
import com.dailyhealthcoach.ui.theme.PrimaryText
import com.dailyhealthcoach.ui.theme.SecondaryCard

@Composable
fun ReminderRoute(
    viewModel: ReminderViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    ReminderScreen(
        uiState = uiState,
        onToggle = viewModel::toggle,
        onUpdateTime = viewModel::updateTime,
        onBack = onBack,
        modifier = modifier
    )
}

@Composable
fun ReminderScreen(
    uiState: ReminderUiState,
    onToggle: (ReminderType, Boolean) -> Unit,
    onUpdateTime: (ReminderType, Int, Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var pendingType by remember { mutableStateOf<ReminderType?>(null) }

    val permLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) pendingType?.let { onToggle(it, true) }
        pendingType = null
    }

    fun handleToggle(type: ReminderType, enable: Boolean) {
        if (!enable) { onToggle(type, false); return }
        if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(context, "android.permission.POST_NOTIFICATIONS")
                != PackageManager.PERMISSION_GRANTED
            ) {
                pendingType = type
                permLauncher.launch("android.permission.POST_NOTIFICATIONS")
                return
            }
        }
        onToggle(type, true)
    }

    Box(modifier = modifier.fillMaxSize().background(MainCard)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
                .padding(start = 18.dp, top = 16.dp, end = 18.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onBack) { Text("← Back", color = CyanAccent) }
                Text(
                    text = "Reminders",
                    color = PrimaryText,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Box(modifier = Modifier.padding(horizontal = 16.dp))
            }

            Text(
                "Times are approximate — reminders fire within a few minutes of the set time.",
                color = MutedText,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            uiState.items.forEach { item ->
                ReminderCard(
                    item = item,
                    onToggle = { enabled -> handleToggle(item.type, enabled) },
                    onUpdateTime = { h, m -> onUpdateTime(item.type, h, m) }
                )
            }
        }
    }
}

@Composable
private fun ReminderCard(
    item: ReminderItemUiState,
    onToggle: (Boolean) -> Unit,
    onUpdateTime: (Int, Int) -> Unit
) {
    var timeInput by remember(item.timeLabel) { mutableStateOf(item.timeLabel) }

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = SecondaryCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(item.label, color = PrimaryText, fontWeight = FontWeight.SemiBold)
                    Text(item.body, color = MutedText, style = MaterialTheme.typography.bodySmall)
                }
                FilterChip(
                    selected = item.enabled,
                    onClick = { onToggle(!item.enabled) },
                    label = { Text(if (item.enabled) "On" else "Off") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AccentBlue,
                        selectedLabelColor = PrimaryText,
                        labelColor = MutedText
                    )
                )
            }
            if (item.enabled && item.type.isDaily) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Time (24 h):", color = MutedText, style = MaterialTheme.typography.bodySmall)
                    OutlinedTextField(
                        value = timeInput,
                        onValueChange = { timeInput = it },
                        placeholder = { Text("HH:mm", color = MutedText) },
                        singleLine = true,
                        modifier = Modifier
                            .width(96.dp)
                            .onFocusChanged { state ->
                                if (!state.isFocused) {
                                    val parsed = parseTime(timeInput)
                                    if (parsed != null) onUpdateTime(parsed.first, parsed.second)
                                    else timeInput = item.timeLabel
                                }
                            }
                    )
                }
            }
        }
    }
}

private fun parseTime(s: String): Pair<Int, Int>? {
    val parts = s.trim().split(":")
    if (parts.size != 2) return null
    val h = parts[0].toIntOrNull() ?: return null
    val m = parts[1].toIntOrNull() ?: return null
    if (h !in 0..23 || m !in 0..59) return null
    return h to m
}
