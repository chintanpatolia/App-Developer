package com.dailyhealthcoach.ui.dashboard

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dailyhealthcoach.ui.premium.HealthMetricTile
import com.dailyhealthcoach.ui.premium.ProgressRing
import com.dailyhealthcoach.ui.theme.AccentBlue
import com.dailyhealthcoach.ui.theme.CyanAccent
import com.dailyhealthcoach.ui.theme.MainCard
import com.dailyhealthcoach.ui.theme.MutedControl
import com.dailyhealthcoach.ui.theme.MutedText
import com.dailyhealthcoach.ui.theme.PositiveAccent
import com.dailyhealthcoach.ui.theme.PrimaryText
import com.dailyhealthcoach.ui.theme.SecondaryCard
import com.dailyhealthcoach.ui.theme.WarningAccent

@Composable
fun DashboardRoute(
    viewModel: DashboardViewModel,
    onNavigateToProgress: () -> Unit,
    onNavigateToSettings: () -> Unit = {},
    onNavigateToWorkout: () -> Unit = {},
    onNavigateToNutrition: () -> Unit = {},
    onNavigateToBody: () -> Unit = {},
    onNavigateToHabits: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val layoutState by viewModel.layoutState.collectAsState()
    DashboardScreen(
        uiState = uiState,
        layoutState = layoutState,
        onNavigateToProgress = onNavigateToProgress,
        onNavigateToSettings = onNavigateToSettings,
        onNavigateToWorkout = onNavigateToWorkout,
        onNavigateToNutrition = onNavigateToNutrition,
        onNavigateToBody = onNavigateToBody,
        onNavigateToHabits = onNavigateToHabits,
        onEnterEditMode = viewModel::enterEditMode,
        onExitEditMode = viewModel::exitEditMode,
        onToggleWidget = viewModel::toggleWidget,
        onMoveWidget = viewModel::moveWidget,
        onShowResetConfirm = viewModel::showResetConfirm,
        onDismissResetConfirm = viewModel::dismissResetConfirm,
        onConfirmReset = viewModel::resetLayout,
        modifier = modifier
    )
}

@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    layoutState: DashboardLayoutState = DashboardLayoutState(),
    onNavigateToProgress: () -> Unit,
    onNavigateToSettings: () -> Unit = {},
    onNavigateToWorkout: () -> Unit = {},
    onNavigateToNutrition: () -> Unit = {},
    onNavigateToBody: () -> Unit = {},
    onNavigateToHabits: () -> Unit = {},
    onEnterEditMode: () -> Unit = {},
    onExitEditMode: () -> Unit = {},
    onToggleWidget: (DashboardCardKey) -> Unit = { _ -> },
    onMoveWidget: (Int, Int) -> Unit = { _, _ -> },
    onShowResetConfirm: () -> Unit = {},
    onDismissResetConfirm: () -> Unit = {},
    onConfirmReset: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Surface(modifier = modifier.fillMaxSize(), color = Color.Transparent) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(start = 18.dp, top = 24.dp, end = 18.dp, bottom = 36.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            DashboardHeader(onNavigateToSettings = onNavigateToSettings)

            HeroWidget(uiState = uiState, onStartWorkout = onNavigateToWorkout)

            Row(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                    WorkoutWidget(uiState = uiState, onNavigateToWorkout = onNavigateToWorkout)
                }
                Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                    NutritionWidget(uiState = uiState, onNavigateToNutrition = onNavigateToNutrition)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                    StepsWidget(uiState = uiState, onNavigateToBody = onNavigateToBody)
                }
                Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                    SleepWidget(uiState = uiState, onNavigateToBody = onNavigateToBody)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                    WeightWidget(uiState = uiState, onNavigateToBody = onNavigateToBody)
                }
                Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                    BodyFatWidget(uiState = uiState, onNavigateToBody = onNavigateToBody)
                }
            }

            RecoveryHeroCard(uiState = uiState, onNavigateToBody = onNavigateToBody)

            HabitsWidget(uiState = uiState, onNavigateToHabits = onNavigateToHabits)

            RecommendationCard(
                recommendation = uiState.nextDayRecommendation,
                workoutCompletedToday = uiState.workoutCompletedToday
            )

            ViewProgressCard(onClick = onNavigateToProgress)

            Text(
                text = "General wellness guidance only. Not medical advice.",
                style = MaterialTheme.typography.bodySmall,
                color = MutedText.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun DashboardHeader(
    onNavigateToSettings: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.weight(1f)) {
            Text(
                text = "Daily Health Coach",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = PrimaryText
            )
            Text(
                text = "Today's snapshot",
                style = MaterialTheme.typography.bodySmall,
                color = MutedText
            )
        }
        ProfileAvatarButton(onClick = onNavigateToSettings)
    }
}

@Composable
private fun ProfileAvatarButton(onClick: () -> Unit) {
    val context = LocalContext.current
    val photoPath = remember {
        context.getSharedPreferences("app_prefs", android.content.Context.MODE_PRIVATE)
            .getString("profile_photo_path", null)
    }
    val bitmap = remember(photoPath) {
        photoPath?.let {
            BitmapFactory.decodeFile(it, BitmapFactory.Options().apply { inSampleSize = 2 })
                ?.asImageBitmap()
        }
    }
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(AccentBlue)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap,
                contentDescription = "Profile",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Text("👤", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun HeroWidget(uiState: DashboardUiState, onStartWorkout: () -> Unit) {
    val scoreColor = healthScoreColor(uiState.healthScore)
    val btnInteraction = remember { MutableInteractionSource() }
    val isBtnPressed by btnInteraction.collectIsPressedAsState()
    val btnScale by animateFloatAsState(
        targetValue = if (isBtnPressed) 0.97f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "hero_btn_scale"
    )
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MainCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().background(
                Brush.verticalGradient(listOf(Color.White.copy(alpha = 0.07f), Color.Transparent))
            )
        ) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TODAY",
                        style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 2.sp),
                        color = MutedText,
                        fontWeight = FontWeight.SemiBold
                    )
                    if (uiState.coachLine != null) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(CyanAccent.copy(alpha = 0.28f), AccentBlue.copy(alpha = 0.14f))
                                    )
                                )
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(11.dp))
                                Text(text = "Coach", style = MaterialTheme.typography.labelSmall, color = CyanAccent, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProgressRing(
                        progress = (uiState.healthScore / 100f).coerceIn(0f, 1f),
                        modifier = Modifier.size(88.dp),
                        strokeWidth = 10.dp,
                        color = scoreColor
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "${uiState.healthScore}", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, color = scoreColor)
                            Text(text = "/ 100", style = MaterialTheme.typography.labelSmall, color = MutedText)
                        }
                    }
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = "HEALTH SCORE", style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp), color = MutedText, fontWeight = FontWeight.SemiBold)
                        Text(text = healthScoreLabel(uiState.healthScore), style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, color = scoreColor)
                        val focus = uiState.nextDayRecommendation?.suggestedFocus
                        if (focus != null) {
                            Text(text = "Focus: $focus", style = MaterialTheme.typography.bodySmall, color = MutedText)
                        }
                        if (uiState.coachLine != null) {
                            Text(text = uiState.coachLine, style = MaterialTheme.typography.bodySmall, color = CyanAccent)
                        }
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    HeroMiniBar(label = "Recovery", progress = ((uiState.recoveryScore ?: 0) / 100f).coerceIn(0f, 1f), color = recoveryColor(uiState.recoveryScore ?: 0), modifier = Modifier.weight(1f))
                    HeroMiniBar(label = "Nutrition", progress = progress(uiState.caloriesToday, uiState.calorieGoal), color = PositiveAccent, modifier = Modifier.weight(1f))
                    HeroMiniBar(label = "Habits", progress = progress(uiState.completedHabits, uiState.totalHabits), color = CyanAccent, modifier = Modifier.weight(1f))
                }
                if (!uiState.workoutCompletedToday) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .scale(btnScale)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                Brush.horizontalGradient(listOf(Color(0xFF3DB8FF), AccentBlue))
                            )
                            .clickable(interactionSource = btnInteraction, indication = null) { onStartWorkout() }
                            .padding(vertical = 13.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Start Today's Workout", color = PrimaryText, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                    }
                } else if (uiState.todayWorkoutName != null) {
                    Surface(shape = RoundedCornerShape(12.dp), color = PositiveAccent.copy(alpha = 0.12f), modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = uiState.todayWorkoutName, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = PositiveAccent)
                            Text(text = uiState.todayWorkoutStatusLabel ?: "Done", style = MaterialTheme.typography.labelSmall, color = PositiveAccent)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HeroMiniBar(label: String, progress: Float, color: Color, modifier: Modifier = Modifier) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing),
        label = "minibar_${label}_progress"
    )
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = MutedText)
        Box(modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(999.dp)).background(MutedControl.copy(alpha = 0.4f))) {
            Box(modifier = Modifier.fillMaxWidth(animatedProgress).height(4.dp).clip(RoundedCornerShape(999.dp)).background(color))
        }
    }
}

@Composable
private fun WorkoutWidget(uiState: DashboardUiState, onNavigateToWorkout: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = tween(durationMillis = 120),
        label = "workout_scale"
    )
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().scale(cardScale)
            .clickable(interactionSource = interactionSource, indication = null) { onNavigateToWorkout() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = SecondaryCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().background(Brush.verticalGradient(listOf(Color.White.copy(alpha = 0.05f), Color.Transparent)))) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(Icons.Default.Bolt, contentDescription = null, tint = AccentBlue, modifier = Modifier.size(15.dp))
                    Text(text = "Workout", style = MaterialTheme.typography.labelSmall, color = MutedText, fontWeight = FontWeight.SemiBold)
                }
                if (uiState.todayWorkoutName != null) {
                    Text(text = uiState.todayWorkoutName, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = PrimaryText, maxLines = 2)
                    Text(text = uiState.todayWorkoutStatusLabel ?: "", style = MaterialTheme.typography.labelSmall, color = PositiveAccent)
                } else {
                    Text(text = "No workout yet", style = MaterialTheme.typography.bodySmall, color = MutedText)
                    Text(text = "Tap to start →", style = MaterialTheme.typography.labelSmall, color = AccentBlue)
                }
            }
        }
    }
}

@Composable
private fun NutritionWidget(uiState: DashboardUiState, onNavigateToNutrition: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = tween(durationMillis = 120),
        label = "nutrition_scale"
    )
    val calorieProgress by animateFloatAsState(
        targetValue = progress(uiState.caloriesToday, uiState.calorieGoal),
        animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing),
        label = "calorie_progress"
    )
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().scale(cardScale)
            .clickable(interactionSource = interactionSource, indication = null) { onNavigateToNutrition() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = SecondaryCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().background(Brush.verticalGradient(listOf(Color.White.copy(alpha = 0.05f), Color.Transparent)))) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    Icon(Icons.Default.LocalDining, contentDescription = null, tint = PositiveAccent, modifier = Modifier.size(14.dp))
                    Text(text = "Nutrition", style = MaterialTheme.typography.labelSmall, color = MutedText, fontWeight = FontWeight.SemiBold)
                }
                Text(text = "${uiState.caloriesToday} kcal", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PrimaryText)
                Text(text = "goal ${uiState.calorieGoal}", style = MaterialTheme.typography.labelSmall, color = MutedText)
                Box(modifier = Modifier.fillMaxWidth().height(3.dp).clip(RoundedCornerShape(999.dp)).background(MutedControl.copy(alpha = 0.4f))) {
                    Box(modifier = Modifier.fillMaxWidth(calorieProgress).height(3.dp).clip(RoundedCornerShape(999.dp)).background(PositiveAccent))
                }
                Text(text = "P ${uiState.proteinConsumedGrams}g / ${uiState.proteinMinGoalGrams}g", style = MaterialTheme.typography.labelSmall, color = MutedText)
            }
        }
    }
}

@Composable
private fun StepsWidget(uiState: DashboardUiState, onNavigateToBody: () -> Unit) {
    HealthMetricTile(
        icon = Icons.Default.Route,
        iconColor = AccentBlue,
        value = "${uiState.steps}",
        subValue = "/ ${uiState.stepGoal}",
        label = "Steps",
        progress = if (uiState.stepGoal > 0) (uiState.steps.toFloat() / uiState.stepGoal).coerceIn(0f, 1f) else 0f,
        modifier = Modifier.fillMaxWidth().fillMaxHeight().clickable { onNavigateToBody() }
    )
}

@Composable
private fun SleepWidget(uiState: DashboardUiState, onNavigateToBody: () -> Unit) {
    HealthMetricTile(
        icon = Icons.Default.DarkMode,
        iconColor = Color(0xFF9C77E0),
        value = String.format("%.1f h", uiState.sleepHours),
        label = "Sleep",
        modifier = Modifier.fillMaxWidth().fillMaxHeight().clickable { onNavigateToBody() }
    )
}

@Composable
private fun WeightWidget(
    uiState: DashboardUiState,
    onNavigateToBody: () -> Unit,
    isEditMode: Boolean = false
) {
    if (uiState.weightKg == null && !isEditMode) return
    val weightInteraction = remember { MutableInteractionSource() }
    val isWeightPressed by weightInteraction.collectIsPressedAsState()
    val weightScale by animateFloatAsState(
        targetValue = if (isWeightPressed) 0.98f else 1f,
        animationSpec = tween(durationMillis = 120),
        label = "weight_scale"
    )
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().scale(weightScale)
            .clickable(interactionSource = weightInteraction, indication = null) { onNavigateToBody() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = SecondaryCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().background(Brush.verticalGradient(listOf(Color.White.copy(alpha = 0.05f), Color.Transparent)))) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = "Weight", style = MaterialTheme.typography.labelSmall, color = MutedText, fontWeight = FontWeight.SemiBold)
                if (uiState.weightKg != null) {
                    Text(text = String.format("%.1f kg", uiState.weightKg), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PrimaryText)
                } else {
                    Text(text = "No data logged yet", style = MaterialTheme.typography.bodySmall, color = MutedText)
                }
            }
        }
    }
}

@Composable
private fun BodyFatWidget(
    uiState: DashboardUiState,
    onNavigateToBody: () -> Unit,
    isEditMode: Boolean = false
) {
    if (uiState.bodyFatPercent == null && !isEditMode) return
    val bfInteraction = remember { MutableInteractionSource() }
    val isBfPressed by bfInteraction.collectIsPressedAsState()
    val bfScale by animateFloatAsState(
        targetValue = if (isBfPressed) 0.98f else 1f,
        animationSpec = tween(durationMillis = 120),
        label = "bodyfat_scale"
    )
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().scale(bfScale)
            .clickable(interactionSource = bfInteraction, indication = null) { onNavigateToBody() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = SecondaryCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().background(Brush.verticalGradient(listOf(Color.White.copy(alpha = 0.05f), Color.Transparent)))) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = "Body Fat", style = MaterialTheme.typography.labelSmall, color = MutedText, fontWeight = FontWeight.SemiBold)
                if (uiState.bodyFatPercent != null) {
                    Text(text = String.format("%.1f%%", uiState.bodyFatPercent), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PrimaryText)
                } else {
                    Text(text = "No data logged yet", style = MaterialTheme.typography.bodySmall, color = MutedText)
                }
            }
        }
    }
}

@Composable
private fun HabitsWidget(uiState: DashboardUiState, onNavigateToHabits: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = tween(durationMillis = 120),
        label = "habits_scale"
    )
    val habitsProgress by animateFloatAsState(
        targetValue = progress(uiState.completedHabits, uiState.totalHabits),
        animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing),
        label = "habits_progress"
    )
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().scale(cardScale)
            .clickable(interactionSource = interactionSource, indication = null) { onNavigateToHabits() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = SecondaryCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().background(Brush.verticalGradient(listOf(Color.White.copy(alpha = 0.05f), Color.Transparent)))) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(14.dp))
                        Text(text = "Habits", style = MaterialTheme.typography.labelSmall, color = MutedText, fontWeight = FontWeight.SemiBold)
                    }
                    Text(
                        text = "${uiState.completedHabits} / ${uiState.totalHabits}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (uiState.totalHabits > 0 && uiState.completedHabits == uiState.totalHabits) PositiveAccent else PrimaryText
                    )
                }
                Box(modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(999.dp)).background(MutedControl.copy(alpha = 0.4f))) {
                    Box(modifier = Modifier.fillMaxWidth(habitsProgress).height(4.dp).clip(RoundedCornerShape(999.dp)).background(CyanAccent))
                }
                when {
                    uiState.totalHabits == 0 -> Text(text = "No habits set. Tap to add some.", style = MaterialTheme.typography.bodySmall, color = MutedText)
                    uiState.completedHabits == uiState.totalHabits -> Text(text = "All habits complete today.", style = MaterialTheme.typography.bodySmall, color = PositiveAccent)
                    else -> Text(text = "${uiState.totalHabits - uiState.completedHabits} remaining", style = MaterialTheme.typography.bodySmall, color = MutedText)
                }
            }
        }
    }
}

@Composable
private fun RecoveryHeroCard(uiState: DashboardUiState, onNavigateToBody: () -> Unit = {}) {
    var showBreakdown by remember { mutableStateOf(false) }
    val recoveryInteraction = remember { MutableInteractionSource() }
    val isRecoveryPressed by recoveryInteraction.collectIsPressedAsState()
    val recoveryCardScale by animateFloatAsState(
        targetValue = if (isRecoveryPressed) 0.98f else 1f,
        animationSpec = tween(durationMillis = 120),
        label = "recovery_scale"
    )
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().scale(recoveryCardScale)
            .clickable(interactionSource = recoveryInteraction, indication = null) { onNavigateToBody() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MainCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().background(Brush.verticalGradient(listOf(Color.White.copy(alpha = 0.05f), Color.Transparent)))) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "Recovery", style = MaterialTheme.typography.labelSmall, color = MutedText, fontWeight = FontWeight.SemiBold)
            if (uiState.recoveryScore == null) {
                Text(
                    text = "Log sleep, nutrition, and workouts to see your recovery score.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MutedText
                )
            } else {
                val ringColor = recoveryColor(uiState.recoveryScore)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProgressRing(
                        progress = (uiState.recoveryScore / 100f).coerceIn(0f, 1f),
                        modifier = Modifier.size(72.dp),
                        strokeWidth = 7.dp,
                        color = ringColor
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "${uiState.recoveryScore}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = ringColor)
                            Text(text = "/ 100", style = MaterialTheme.typography.labelSmall, color = MutedText)
                        }
                    }
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(text = uiState.recoveryLabel, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, color = ringColor)
                        uiState.recoveryReasons.take(3).forEach { reason ->
                            Text(text = "· $reason", style = MaterialTheme.typography.bodySmall, color = MutedText)
                        }
                    }
                }
                if (uiState.recoveryContributors.isNotEmpty()) {
                    TextButton(
                        onClick = { showBreakdown = !showBreakdown },
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            text = if (showBreakdown) "▲ Hide breakdown" else "▼ Recovery breakdown",
                            style = MaterialTheme.typography.labelMedium,
                            color = CyanAccent
                        )
                    }
                    if (showBreakdown) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(SecondaryCard, RoundedCornerShape(12.dp))
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = "Recovery Contributors", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, color = MutedText)
                            uiState.recoveryContributors.forEach { c ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = c.label, style = MaterialTheme.typography.bodySmall, color = PrimaryText)
                                        Text(text = c.detail, style = MaterialTheme.typography.labelSmall, color = MutedText)
                                    }
                                    Text(
                                        text = c.deltaText,
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.SemiBold,
                                        color = if (c.isPositive) PositiveAccent else WarningAccent
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = "Total", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, color = MutedText)
                                Text(text = "${uiState.recoveryScore}", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = ringColor)
                            }
                        }
                    }
                }
            }
        }
        } // gradient Box
    }
}

@Composable
private fun RecommendationCard(recommendation: DailyRecommendationUiState?, workoutCompletedToday: Boolean = false) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MainCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().background(Brush.verticalGradient(listOf(Color.White.copy(alpha = 0.05f), Color.Transparent)))) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (workoutCompletedToday) {
                Surface(shape = RoundedCornerShape(999.dp), color = PositiveAccent.copy(alpha = 0.15f)) {
                    Text(
                        text = "WORKOUT DONE TODAY",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = PositiveAccent,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
            Text(text = "TOMORROW", style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 2.sp), color = MutedText, fontWeight = FontWeight.SemiBold)
            if (recommendation == null) {
                Text(
                    text = "Log body metrics, nutrition, and workouts to unlock your daily recommendation.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MutedText
                )
            } else {
                val badgeColor = when {
                    recommendation.title.contains("Rest", ignoreCase = true) -> Color(0xFF9C77E0)
                    recommendation.title.contains("Strength", ignoreCase = true) -> AccentBlue
                    recommendation.title.contains("Active", ignoreCase = true) || recommendation.title.contains("Recovery", ignoreCase = true) -> PositiveAccent
                    recommendation.title.contains("Walk", ignoreCase = true) || recommendation.title.contains("Mobility", ignoreCase = true) -> CyanAccent
                    else -> CyanAccent
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Surface(shape = RoundedCornerShape(999.dp), color = badgeColor.copy(alpha = 0.18f)) {
                            Text(
                                text = recommendation.title.uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = badgeColor,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                            )
                        }
                        Text(text = recommendation.suggestedFocus, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = PrimaryText)
                        recommendation.reasons.take(2).forEach { reason ->
                            Text(text = "· $reason", style = MaterialTheme.typography.bodySmall, color = MutedText)
                        }
                    }
                    RecommendationIllustration(title = recommendation.title)
                }
            }
        }
        } // gradient Box
    }
}

@Composable
private fun ViewProgressCard(onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "progress_card_scale"
    )
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = SecondaryCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Progress & Trends", style = MaterialTheme.typography.titleSmall, color = PrimaryText)
            Text(text = "View →", color = CyanAccent, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.titleSmall)
        }
    }
}

@Composable
private fun RecommendationIllustration(title: String) {
    val infiniteTransition = rememberInfiniteTransition(label = "rec_anim")
    val floatY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rec_float"
    )
    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.07f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rec_pulse"
    )
    val (icon, iconColor) = when {
        title.contains("Rest", ignoreCase = true) -> Pair(Icons.Default.NightlightRound, Color(0xFF9C77E0))
        title.contains("Strength", ignoreCase = true) -> Pair(Icons.Default.Bolt, AccentBlue)
        title.contains("Walk", ignoreCase = true) || title.contains("Mobility", ignoreCase = true) -> Pair(Icons.Default.Route, CyanAccent)
        title.contains("Active", ignoreCase = true) || title.contains("Recovery", ignoreCase = true) -> Pair(Icons.Default.FavoriteBorder, PositiveAccent)
        else -> Pair(Icons.Default.AutoAwesome, WarningAccent)
    }
    Box(
        modifier = Modifier
            .size(68.dp)
            .scale(pulse)
            .offset(y = (-floatY).dp)
            .clip(CircleShape)
            .background(iconColor.copy(alpha = 0.14f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(34.dp))
    }
}

private fun recoveryColor(score: Int): Color = when {
    score >= 80 -> PositiveAccent
    score >= 60 -> CyanAccent
    score >= 40 -> WarningAccent
    else -> Color(0xFFFF6B6B)
}

private fun healthScoreColor(score: Int): Color = when {
    score >= 80 -> PositiveAccent
    score >= 60 -> CyanAccent
    score >= 40 -> WarningAccent
    score > 0 -> Color(0xFFFF6B6B)
    else -> MutedText
}

private fun healthScoreLabel(score: Int): String = when {
    score >= 80 -> "Excellent"
    score >= 60 -> "Good"
    score >= 40 -> "Fair"
    score > 0 -> "Needs attention"
    else -> "Log data to unlock"
}

private fun progress(value: Int, target: Int): Float {
    if (target <= 0) return 0f
    return (value.toFloat() / target.toFloat()).coerceIn(0f, 1f)
}
