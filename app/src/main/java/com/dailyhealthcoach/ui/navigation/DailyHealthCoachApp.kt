package com.dailyhealthcoach.ui.navigation

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlinx.coroutines.launch
import com.dailyhealthcoach.healthconnect.HealthConnectManager
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import android.app.Application
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dailyhealthcoach.data.AppContainer
import com.dailyhealthcoach.ui.body.BodyRoute
import com.dailyhealthcoach.ui.body.BodyViewModel
import com.dailyhealthcoach.ui.body.BodyViewModelFactory
import com.dailyhealthcoach.ui.dashboard.DashboardRoute
import com.dailyhealthcoach.ui.dashboard.DashboardViewModel
import com.dailyhealthcoach.ui.dashboard.DashboardViewModelFactory
import com.dailyhealthcoach.ui.habits.HabitsRoute
import com.dailyhealthcoach.ui.habits.HabitsViewModel
import com.dailyhealthcoach.ui.habits.HabitsViewModelFactory
import com.dailyhealthcoach.ui.progress.ProgressRoute
import com.dailyhealthcoach.ui.progress.ProgressViewModel
import com.dailyhealthcoach.ui.progress.ProgressViewModelFactory
import java.time.LocalDate
import com.dailyhealthcoach.ui.premium.AppBackground
import com.dailyhealthcoach.ui.premium.NutritionRoute
import com.dailyhealthcoach.ui.premium.PremiumWorkoutRoute
import com.dailyhealthcoach.ui.premium.ScreenHeader
import com.dailyhealthcoach.ui.profile.ProfileRoute
import com.dailyhealthcoach.ui.profile.ProfileViewModel
import com.dailyhealthcoach.ui.profile.ProfileViewModelFactory
import com.dailyhealthcoach.ui.reminders.ReminderRoute
import com.dailyhealthcoach.ui.reminders.ReminderViewModel
import com.dailyhealthcoach.ui.reminders.ReminderViewModelFactory
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import com.dailyhealthcoach.ui.theme.AccentBlue
import com.dailyhealthcoach.ui.theme.CyanAccent
import com.dailyhealthcoach.ui.theme.MainCard
import com.dailyhealthcoach.ui.theme.MutedText
import com.dailyhealthcoach.ui.theme.PrimaryText
import com.dailyhealthcoach.ui.theme.SecondaryCard
import com.dailyhealthcoach.ui.nutrition.NutritionViewModel
import com.dailyhealthcoach.ui.nutrition.NutritionViewModelFactory
import com.dailyhealthcoach.ui.recipes.RecipesRoute
import com.dailyhealthcoach.ui.recipes.RecipesViewModel
import com.dailyhealthcoach.ui.recipes.RecipesViewModelFactory
import com.dailyhealthcoach.ui.workout.WorkoutViewModel
import com.dailyhealthcoach.ui.workout.WorkoutViewModelFactory

@Composable
fun DailyHealthCoachApp(appContainer: AppContainer) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    // Re-schedule the periodic worker on every app start (restores it after reboot/reinstall)
    LaunchedEffect(Unit) {
        HealthConnectManager.scheduleIfEnabled(context.applicationContext)
    }

    // Sync HC data every time the app comes to foreground
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                coroutineScope.launch {
                    HealthConnectManager.syncToday(
                        context.applicationContext,
                        appContainer.bodyMetricRepository,
                        appContainer.habitAutoUpdateUseCase
                    )
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    var selectedScreen by remember { mutableStateOf(AppScreen.DASHBOARD) }

    AppBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            when (selectedScreen) {
                AppScreen.DASHBOARD -> {
                    val dashboardContext = LocalContext.current
                    LaunchedEffect(Unit) {
                        HealthConnectManager.syncToday(
                            dashboardContext.applicationContext,
                            appContainer.bodyMetricRepository,
                            appContainer.habitAutoUpdateUseCase
                        )
                    }
                    val dashboardViewModel: DashboardViewModel = viewModel(
                        factory = DashboardViewModelFactory(
                            getDashboardSummaryUseCase = appContainer.getDashboardSummaryUseCase
                        )
                    )
                    DashboardRoute(
                        viewModel = dashboardViewModel,
                        onNavigateToProgress = { selectedScreen = AppScreen.PROGRESS },
                        onNavigateToSettings = { selectedScreen = AppScreen.SETTINGS },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 112.dp)
                    )
                }

                AppScreen.SETTINGS -> {
                    val profileViewModel: ProfileViewModel = viewModel(
                        factory = ProfileViewModelFactory(
                            userProfileRepository = appContainer.userProfileRepository,
                            macroTargetRepository = appContainer.macroTargetRepository,
                            bodyMetricRepository = appContainer.bodyMetricRepository,
                            dataExportService = appContainer.dataExportService,
                            dataRestoreService = appContainer.dataRestoreService
                        )
                    )
                    ProfileRoute(
                        viewModel = profileViewModel,
                        onBack = { selectedScreen = AppScreen.DASHBOARD },
                        onNavigateToReminders = { selectedScreen = AppScreen.REMINDERS },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 112.dp)
                    )
                }

                AppScreen.PROGRESS -> {
                    val progressViewModel: ProgressViewModel = viewModel(
                        factory = ProgressViewModelFactory(
                            bodyMetricRepository = appContainer.bodyMetricRepository,
                            nutritionRepository = appContainer.nutritionRepository,
                            habitRepository = appContainer.habitRepository,
                            workoutRepository = appContainer.workoutRepository,
                            recoveryRepository = appContainer.recoveryRepository,
                            today = LocalDate.now().toString()
                        )
                    )
                    ProgressRoute(
                        viewModel = progressViewModel,
                        onNavigateBack = { selectedScreen = AppScreen.DASHBOARD },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 112.dp)
                    )
                }

                AppScreen.HABITS -> {
                    val habitsViewModel: HabitsViewModel = viewModel(
                        factory = HabitsViewModelFactory(
                            getTodayHabitsUseCase = appContainer.getTodayHabitsUseCase,
                            setHabitStatusForTodayUseCase = appContainer.setHabitStatusForTodayUseCase
                        )
                    )
                    HabitsRoute(
                        viewModel = habitsViewModel,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 112.dp)
                    )
                }

                AppScreen.REMINDERS -> {
                    val app = LocalContext.current.applicationContext as Application
                    val reminderViewModel: ReminderViewModel = viewModel(
                        factory = ReminderViewModelFactory(app)
                    )
                    ReminderRoute(
                        viewModel = reminderViewModel,
                        onBack = { selectedScreen = AppScreen.SETTINGS },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 112.dp)
                    )
                }

                AppScreen.WORKOUT,
                AppScreen.NUTRITION,
                AppScreen.RECIPES -> MainShellContent(
                    selectedScreen = selectedScreen,
                    appContainer = appContainer
                )

                AppScreen.BODY -> {
                    val bodyContext = LocalContext.current
                    LaunchedEffect(Unit) {
                        HealthConnectManager.syncToday(
                            bodyContext.applicationContext,
                            appContainer.bodyMetricRepository,
                            appContainer.habitAutoUpdateUseCase
                        )
                    }
                    val bodyViewModel: BodyViewModel = viewModel(
                        factory = BodyViewModelFactory(
                            bodyMetricRepository = appContainer.bodyMetricRepository,
                            userProfileRepository = appContainer.userProfileRepository,
                            habitAutoUpdateUseCase = appContainer.habitAutoUpdateUseCase,
                            context = bodyContext.applicationContext
                        )
                    )
                    BodyRoute(
                        viewModel = bodyViewModel,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 112.dp)
                    )
                }
            }

            BottomNavCapsule(
                selectedScreen = selectedScreen,
                onScreenSelected = { selectedScreen = it },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 18.dp)
            )

        }
    }
}

@Composable
private fun MainShellContent(
    selectedScreen: AppScreen,
    appContainer: AppContainer
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .padding(start = 18.dp, top = 24.dp, end = 18.dp, bottom = 116.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenHeader()
        when (selectedScreen) {
            AppScreen.WORKOUT -> {
                val workoutViewModel: WorkoutViewModel = viewModel(
                    factory = WorkoutViewModelFactory(
                        exerciseRepository = appContainer.exerciseRepository,
                        workoutRepository = appContainer.workoutRepository,
                        dailyRecommendationRepository = appContainer.dailyRecommendationRepository,
                        recoveryActivityRepository = appContainer.recoveryActivityRepository,
                        generateWorkoutPlanUseCase = appContainer.generateWorkoutPlanUseCase,
                        habitAutoUpdateUseCase = appContainer.habitAutoUpdateUseCase,
                        today = LocalDate.now().toString()
                    )
                )
                PremiumWorkoutRoute(viewModel = workoutViewModel)
            }

            AppScreen.NUTRITION -> {
                val nutritionViewModel: NutritionViewModel = viewModel(
                    factory = NutritionViewModelFactory(
                        nutritionRepository = appContainer.nutritionRepository,
                        macroTargetRepository = appContainer.macroTargetRepository,
                        userProfileRepository = appContainer.userProfileRepository,
                        foodLookupService = appContainer.foodLookupService,
                        aiFoodLoggingService = appContainer.aiFoodLoggingService,
                        habitAutoUpdateUseCase = appContainer.habitAutoUpdateUseCase
                    )
                )
                NutritionRoute(viewModel = nutritionViewModel)
            }

            AppScreen.RECIPES -> {
                val recipesViewModel: RecipesViewModel = viewModel(
                    factory = RecipesViewModelFactory(
                        nutritionRepository = appContainer.nutritionRepository,
                        macroTargetRepository = appContainer.macroTargetRepository,
                        userProfileRepository = appContainer.userProfileRepository,
                        habitAutoUpdateUseCase = appContainer.habitAutoUpdateUseCase
                    )
                )
                RecipesRoute(viewModel = recipesViewModel)
            }

            AppScreen.BODY,
            AppScreen.PROGRESS,
            AppScreen.SETTINGS,
            AppScreen.REMINDERS,
            AppScreen.DASHBOARD,
            AppScreen.HABITS -> Unit
        }
    }
}

@Composable
private fun BottomNavCapsule(
    selectedScreen: AppScreen,
    onScreenSelected: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(999.dp),
        color = MainCard,
        shadowElevation = 16.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppScreen.entries.filter { it.showInNav }.forEach { screen ->
                val selected = selectedScreen == screen
                val indicatorSize by animateDpAsState(if (selected) 32.dp else 28.dp, label = "nav_size")
                val indicatorColor by animateColorAsState(if (selected) AccentBlue else Color.Transparent, label = "nav_color")
                val labelColor by animateColorAsState(if (selected) CyanAccent else MutedText, label = "nav_label")
                val navIcon: ImageVector = when (screen) {
                    AppScreen.DASHBOARD -> Icons.Default.Home
                    AppScreen.HABITS -> Icons.Default.CheckCircle
                    AppScreen.WORKOUT -> Icons.Default.Bolt
                    AppScreen.NUTRITION -> Icons.Default.LocalDining
                    AppScreen.RECIPES -> Icons.Default.DinnerDining
                    AppScreen.BODY -> Icons.Default.MonitorWeight
                    else -> Icons.Default.Home
                }
                TextButton(
                    onClick = { onScreenSelected(screen) },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(indicatorSize)
                                .clip(CircleShape)
                                .background(indicatorColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = navIcon,
                                contentDescription = screen.label,
                                tint = if (selected) PrimaryText else MutedText,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Text(
                            text = screen.label.take(5),
                            color = labelColor,
                            textAlign = TextAlign.Center,
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FloatingAssistantButton(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.size(62.dp),
        shape = CircleShape,
        color = AccentBlue,
        shadowElevation = 14.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = "+", color = PrimaryText, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun MiniCircleButton(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.size(42.dp),
        shape = CircleShape,
        color = SecondaryCard,
        shadowElevation = 8.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = text, color = MutedText, fontWeight = FontWeight.Bold)
        }
    }
}
