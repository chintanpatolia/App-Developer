package com.supplementtracker.ui.supplements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.supplementtracker.data.entity.ScheduleGroupEntity
import com.supplementtracker.data.entity.SupplementEntity
import com.supplementtracker.notifications.AlarmScheduler
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupplementsScreen(
    viewModel: SupplementsViewModel,
    onExportBackup: () -> Unit,
    onImportBackup: () -> Unit,
    onAbout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val groups by viewModel.getGroups().collectAsState(initial = emptyList())

    var showAddSheet by remember { mutableStateOf(false) }
    var editingSupplement by remember { mutableStateOf<SupplementEntity?>(null) }
    var showOverflow by remember { mutableStateOf(false) }

    val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d")
    val dateLabel = try { LocalDate.parse(state.today).format(formatter) } catch (e: Exception) { state.today }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("TODAY", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                        Text(dateLabel, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
                actions = {
                    IconButton(onClick = { showOverflow = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                    DropdownMenu(expanded = showOverflow, onDismissRequest = { showOverflow = false }) {
                        DropdownMenuItem(text = { Text("Export Backup") }, onClick = { showOverflow = false; onExportBackup() })
                        DropdownMenuItem(text = { Text("Import Backup") }, onClick = { showOverflow = false; onImportBackup() })
                        DropdownMenuItem(text = { Text("About") }, onClick = { showOverflow = false; onAbout() })
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddSheet = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add supplement")
            }
        },
        bottomBar = {
            if (!state.loading && state.totalScheduled > 0) {
                Surface(tonalElevation = 2.dp) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("TODAY", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                        Text(
                            "${state.totalCompleted} of ${state.totalScheduled} completed",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(bottom = 88.dp)
        ) {
            if (!state.exactAlarmAvailable) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Exact reminders unavailable. Go to Settings → Special app access → Alarms & reminders to enable.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            state.groups.forEach { gwi ->
                item(key = "header_${gwi.group.id}") {
                    GroupHeader(group = gwi.group)
                }
                items(gwi.supplements, key = { "supp_${it.id}" }) { supplement ->
                    val occurrence = gwi.occurrences[supplement.id]
                    val completed = occurrence?.completed ?: false
                    SupplementRow(
                        supplement = supplement,
                        completed = completed,
                        onToggle = {
                            viewModel.toggleCompletionWithContext(supplement.id, completed) { groupId, date ->
                                AlarmScheduler.cancelSnooze(context, groupId, date)
                            }
                        },
                        onEdit = { editingSupplement = supplement }
                    )
                }
                if (gwi.group.groupNote.isNotBlank()) {
                    item(key = "note_${gwi.group.id}") {
                        Text(
                            gwi.group.groupNote,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 56.dp, end = 16.dp, bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }

    if (showAddSheet) {
        SupplementBottomSheet(
            supplement = null,
            groups = groups,
            onSave = { supp, updatedGroup ->
                if (updatedGroup != null) viewModel.updateGroup(updatedGroup)
                viewModel.saveSupplement(supp, isNew = true)
            },
            onDelete = null,
            onDismiss = { showAddSheet = false }
        )
    }

    editingSupplement?.let { supp ->
        SupplementBottomSheet(
            supplement = supp,
            groups = groups,
            onSave = { updated, updatedGroup ->
                if (updatedGroup != null) viewModel.updateGroup(updatedGroup)
                viewModel.saveSupplement(updated, isNew = false)
            },
            onDelete = { viewModel.deleteSupplement(it) },
            onDismiss = { editingSupplement = null }
        )
    }
}

@Composable
private fun GroupHeader(group: ScheduleGroupEntity) {
    val timeLabel = "%02d:%02d %s".format(
        if (group.reminderHour % 12 == 0) 12 else group.reminderHour % 12,
        group.reminderMinute,
        if (group.reminderHour < 12) "AM" else "PM"
    )
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 4.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                group.label.uppercase(),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(" · $timeLabel", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        HorizontalDivider(modifier = Modifier.padding(top = 4.dp), color = MaterialTheme.colorScheme.outlineVariant)
    }
}

@Composable
private fun SupplementRow(
    supplement: SupplementEntity,
    completed: Boolean,
    onToggle: () -> Unit,
    onEdit: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEdit() }
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = completed,
            onCheckedChange = { onToggle() },
            modifier = Modifier.size(48.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                supplement.name,
                style = MaterialTheme.typography.bodyLarge,
                color = if (completed) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
            )
            if (supplement.dose.isNotBlank()) {
                Text(
                    supplement.dose,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
