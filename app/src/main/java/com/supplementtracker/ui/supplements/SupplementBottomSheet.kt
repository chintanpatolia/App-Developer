package com.supplementtracker.ui.supplements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.supplementtracker.data.entity.ScheduleGroupEntity
import com.supplementtracker.data.entity.SupplementEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupplementBottomSheet(
    supplement: SupplementEntity?,
    groups: List<ScheduleGroupEntity>,
    onSave: (SupplementEntity, ScheduleGroupEntity?) -> Unit,
    onDelete: ((SupplementEntity) -> Unit)?,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf(supplement?.name ?: "") }
    var dose by remember { mutableStateOf(supplement?.dose ?: "") }
    var notes by remember { mutableStateOf(supplement?.notes ?: "") }
    var active by remember { mutableStateOf(supplement?.active ?: true) }
    var selectedGroup by remember { mutableStateOf(groups.find { it.id == supplement?.scheduleGroupId } ?: groups.firstOrNull()) }
    var reminderHour by remember { mutableIntStateOf(selectedGroup?.reminderHour ?: 7) }
    var reminderMinute by remember { mutableIntStateOf(selectedGroup?.reminderMinute ?: 0) }
    var groupDropdownExpanded by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    if (showDeleteDialog && supplement != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Supplement") },
            text = { Text("Delete \"${supplement.name}\"? This cannot be undone.") },
            confirmButton = {
                TextButton(onClick = { onDelete?.invoke(supplement); onDismiss() }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = { TextButton(onClick = { showDeleteDialog = false }) { Text("Cancel") } }
        )
    }

    if (showTimePicker) {
        val tps = rememberTimePickerState(reminderHour, reminderMinute, is24Hour = false)
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            title = { Text("Set Reminder Time") },
            text = { TimePicker(state = tps, modifier = Modifier.fillMaxWidth()) },
            confirmButton = {
                TextButton(onClick = {
                    reminderHour = tps.hour
                    reminderMinute = tps.minute
                    showTimePicker = false
                }) { Text("OK") }
            },
            dismissButton = { TextButton(onClick = { showTimePicker = false }) { Text("Cancel") } }
        )
    }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = if (supplement == null) "Add Supplement" else "Edit Supplement",
                style = MaterialTheme.typography.titleLarge
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Supplement Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = dose,
                onValueChange = { dose = it },
                label = { Text("Dose (e.g. 5 g, 2 capsules)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            // Schedule Group dropdown
            ExposedDropdownMenuBox(
                expanded = groupDropdownExpanded,
                onExpandedChange = { groupDropdownExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedGroup?.label ?: "Select group",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Schedule Group") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = groupDropdownExpanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = groupDropdownExpanded,
                    onDismissRequest = { groupDropdownExpanded = false }
                ) {
                    groups.forEach { group ->
                        DropdownMenuItem(
                            text = { Text(group.label) },
                            onClick = {
                                selectedGroup = group
                                reminderHour = group.reminderHour
                                reminderMinute = group.reminderMinute
                                groupDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            // Reminder time
            OutlinedTextField(
                value = "%02d:%02d".format(reminderHour, reminderMinute),
                onValueChange = {},
                readOnly = true,
                label = { Text("Reminder Time") },
                trailingIcon = {
                    IconButton(onClick = { showTimePicker = true }) {
                        Icon(Icons.Default.AccessTime, contentDescription = "Set time")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes (optional)") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Active", modifier = Modifier.weight(1f))
                Switch(checked = active, onCheckedChange = { active = it })
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (supplement != null && onDelete != null) {
                    OutlinedButton(
                        onClick = { showDeleteDialog = true },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) { Text("Delete") }
                }
                Button(
                    onClick = {
                        if (name.isNotBlank()) {
                            val updated = (supplement ?: SupplementEntity(
                                name = "", dose = "", scheduleGroupId = selectedGroup?.id ?: 1L
                            )).copy(
                                name = name.trim(),
                                dose = dose.trim(),
                                notes = notes.trim(),
                                active = active,
                                scheduleGroupId = selectedGroup?.id ?: 1L
                            )
                            val updatedGroup = selectedGroup?.copy(reminderHour = reminderHour, reminderMinute = reminderMinute)
                            onSave(updated, updatedGroup)
                            onDismiss()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = name.isNotBlank()
                ) { Text("Save") }
            }
        }
    }
}
