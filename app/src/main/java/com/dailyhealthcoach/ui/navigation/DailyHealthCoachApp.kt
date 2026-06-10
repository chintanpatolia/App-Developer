package com.dailyhealthcoach.ui.navigation

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.dailyhealthcoach.ui.theme.AccentBlue
import com.dailyhealthcoach.ui.theme.CyanAccent
import com.dailyhealthcoach.ui.theme.MainCard
import com.dailyhealthcoach.ui.theme.MutedText
import com.dailyhealthcoach.ui.theme.PrimaryText
import com.dailyhealthcoach.ui.theme.SecondaryCard
import com.dailyhealthcoach.ui.nutrition.NutritionViewModel
import com.dailyhealthcoach.ui.nutrition.NutritionViewModelFactory
import com.dailyhealthcoach.ui.workout.WorkoutViewModel
import com.dailyhealthcoach.ui.workout.WorkoutViewModelFactory

@Composable
fun DailyHealthCoachApp(appContainer: AppContainer) {
    var selectedScreen by remember { mutableStateOf(AppScreen.DASHBOARD) }

    AppBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            when (selectedScreen) {
                AppScreen.DASHBOARD -> {
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
                AppScreen.NUTRITION -> MainShellContent(
                    selectedScreen = selectedScreen,
                    appContainer = appContainer
                )

                AppScreen.BODY -> {
                    val bodyViewModel: BodyViewModel = viewModel(
                        factory = BodyViewModelFactory(
                            bodyMetricRepository = appContainer.bodyMetricRepository,
                            userProfileRepository = appContainer.userProfileRepository
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
            .padding(start = 18.dp, top = 24.dp, end = 18.dp, bottom = 148.dp),
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
                        foodLookupService = appContainer.foodLookupService
                    )
                )
                NutritionRoute(viewModel = nutritionViewModel)
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
        color = MainCard.copy(alpha = 0.96f),
        shadowElevation = 12.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppScreen.entries.filter { it.showInNav }.forEach { screen ->
                val selected = selectedScreen == screen
                TextButton(
                    onClick = { onScreenSelected(screen) },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(if (selected) 34.dp else 28.dp)
                                .clip(CircleShape)
                                .background(if (selected) AccentBlue else SecondaryCard.copy(alpha = 0.55f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = screen.label.take(1),
                                color = PrimaryText,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = screen.label.take(5),
                            color = if (selected) CyanAccent else MutedText,
                            textAlign = TextAlign.Center,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
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
