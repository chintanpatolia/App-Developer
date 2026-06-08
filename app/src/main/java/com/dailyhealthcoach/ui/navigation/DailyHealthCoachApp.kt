package com.dailyhealthcoach.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dailyhealthcoach.data.AppContainer
import com.dailyhealthcoach.ui.premium.AchievementsScreen
import com.dailyhealthcoach.ui.premium.AppBackground
import com.dailyhealthcoach.ui.premium.BodyScreen
import com.dailyhealthcoach.ui.premium.InsightsScreen
import com.dailyhealthcoach.ui.premium.NutritionRoute
import com.dailyhealthcoach.ui.premium.PremiumWorkoutRoute
import com.dailyhealthcoach.ui.premium.ScreenHeader
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
    var selectedScreen by remember { mutableStateOf(AppScreen.WORKOUT) }

    AppBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
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
                                workoutRepository = appContainer.workoutRepository
                            )
                        )
                        PremiumWorkoutRoute(viewModel = workoutViewModel)
                    }

                    AppScreen.NUTRITION -> {
                        val nutritionViewModel: NutritionViewModel = viewModel(
                            factory = NutritionViewModelFactory(
                                nutritionRepository = appContainer.nutritionRepository,
                                macroTargetRepository = appContainer.macroTargetRepository
                            )
                        )
                        NutritionRoute(viewModel = nutritionViewModel)
                    }
                    AppScreen.ACHIEVEMENTS -> AchievementsScreen()
                    AppScreen.BODY -> BodyScreen()
                    AppScreen.INSIGHTS -> InsightsScreen()
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

            FloatingAssistantButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(bottom = 72.dp)
            )

            MiniCircleButton(
                text = "S",
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .navigationBarsPadding()
                    .padding(start = 24.dp, bottom = 82.dp)
            )

            MiniCircleButton(
                text = "M",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(end = 24.dp, bottom = 82.dp)
            )
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
            AppScreen.entries.forEach { screen ->
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
