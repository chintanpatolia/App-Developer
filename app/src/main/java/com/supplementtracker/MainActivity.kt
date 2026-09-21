package com.supplementtracker

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.supplementtracker.notifications.AlarmScheduler
import com.supplementtracker.ui.backup.BackupService
import com.supplementtracker.ui.supplements.SupplementsViewModel
import com.supplementtracker.ui.supplements.SupplementsScreen
import com.supplementtracker.ui.theme.SupplementTrackerTheme
import com.supplementtracker.ui.trends.TrendsScreen
import com.supplementtracker.ui.trends.TrendsViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        setContent {
            SupplementTrackerTheme {
                MainNavigation()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MainNavigation() {
        val app = application as SupplementTrackerApp
        val context = this
        val coroutineScope = rememberCoroutineScope()

        var selectedTab by remember { mutableIntStateOf(0) }
        var showAboutDialog by remember { mutableStateOf(false) }

        val exportLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.CreateDocument("application/json")
        ) { uri ->
            uri ?: return@rememberLauncherForActivityResult
            coroutineScope.launch {
                val backup = BackupService(app.db).export()
                contentResolver.openOutputStream(uri)?.use { it.write(backup.toByteArray()) }
            }
        }

        val importLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            uri ?: return@rememberLauncherForActivityResult
            coroutineScope.launch {
                val json = contentResolver.openInputStream(uri)
                    ?.bufferedReader()?.readText() ?: return@launch
                BackupService(app.db).import(json)
            }
        }

        val supplementsVm = remember {
            SupplementsViewModel(
                repo = app.repository,
                alarmScheduler = { group -> AlarmScheduler.scheduleGroup(context, group) },
                cancelAlarm = { groupId -> AlarmScheduler.cancelGroup(context, groupId) },
                rescheduleAll = {
                    coroutineScope.launch {
                        app.db.scheduleGroupDao().getAll()
                            .forEach { AlarmScheduler.scheduleGroup(context, it) }
                    }
                },
                canScheduleExact = { AlarmScheduler.canScheduleExact(context) }
            )
        }
        val trendsVm = remember { TrendsViewModel(app.repository) }

        if (showAboutDialog) {
            AlertDialog(
                onDismissRequest = { showAboutDialog = false },
                title = { Text("Supplements") },
                text = { Text("Personal supplement tracker.\nVersion 1.0\n\nLocal-only. No network. No cloud.") },
                confirmButton = {
                    TextButton(onClick = { showAboutDialog = false }) { Text("OK") }
                }
            )
        }

        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        icon = { Icon(Icons.Default.List, contentDescription = "Supplements") },
                        label = { Text("Supplements") }
                    )
                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        icon = { Icon(Icons.Default.ShowChart, contentDescription = "Trends") },
                        label = { Text("Trends") }
                    )
                }
            }
        ) { padding ->
            when (selectedTab) {
                0 -> SupplementsScreen(
                    viewModel = supplementsVm,
                    onExportBackup = { exportLauncher.launch("supplement_backup.json") },
                    onImportBackup = { importLauncher.launch("application/json") },
                    onAbout = { showAboutDialog = true },
                    modifier = Modifier.padding(padding)
                )
                1 -> TrendsScreen(
                    viewModel = trendsVm,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}
