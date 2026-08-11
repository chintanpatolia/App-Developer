package com.dailyhealthcoach.ui.dashboard

import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.Route
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dailyhealthcoach.ui.premium.ActionChip
import com.dailyhealthcoach.ui.premium.CoachBadge
import com.dailyhealthcoach.ui.premium.GlassButton
import com.dailyhealthcoach.ui.premium.HealthMetricTile
import com.dailyhealthcoach.ui.premium.MetricHeader
import com.dailyhealthcoach.ui.premium.MetricProgressBar
import com.dailyhealthcoach.ui.premium.MetricRing
import com.dailyhealthcoach.ui.premium.MetricValue
import com.dailyhealthcoach.ui.premium.PremiumCard
import com.dailyhealthcoach.ui.premium.PremiumCornerRadius
import com.dailyhealthcoach.ui.premium.PremiumElevation
import com.dailyhealthcoach.ui.premium.PremiumFeatureCard
import com.dailyhealthcoach.ui.premium.PremiumHeroCard
import com.dailyhealthcoach.ui.premium.PremiumSpacing
import com.dailyhealthcoach.ui.premium.SectionTitle
import com.dailyhealthcoach.ui.premium.SoftIllustrationContainer
import com.dailyhealthcoach.ui.premium.SupportingText
import com.dailyhealthcoach.ui.premium.rememberFloatAnimation
import com.dailyhealthcoach.ui.premium.rememberGlowPulse
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
    PremiumHeroCard {
        Column(modifier = Modifier.padding(PremiumSpacing.heroPadding), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                SectionTitle(text = "TODAY", letterSpacing = true)
                if (uiState.coachLine != null) CoachBadge()
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(20.dp), verticalAlignment = Alignment.CenterVertically) {
                MetricRing(
                    progress = (uiState.healthScore / 100f).coerceIn(0f, 1f),
                    modifier = Modifier.size(88.dp),
                    strokeWidth = 10.dp,
                    color = scoreColor
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        MetricValue(text = "${uiState.healthScore}", style = MaterialTheme.typography.headlineLarge, color = scoreColor)
                        Text(text = "/ 100", style = MaterialTheme.typography.labelSmall, color = MutedText)
                    }
                }
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    SectionTitle(text = "HEALTH SCORE")
                    Text(text = healthScoreLabel(uiState.healthScore), style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, color = scoreColor)
                    val focus = uiState.nextDayRecommendation?.suggestedFocus
                    if (focus != null) SupportingText(text = "Focus: $focus")
                    if (uiState.coachLine != null) SupportingText(text = uiState.coachLine, color = CyanAccent)
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                HeroMiniBar(label = "Recovery", progress = ((uiState.recoveryScore ?: 0) / 100f).coerceIn(0f, 1f), color = recoveryColor(uiState.recoveryScore ?: 0), modifier = Modifier.weight(1f))
                HeroMiniBar(label = "Nutrition", progress = progress(uiState.caloriesToday, uiState.calorieGoal), color = PositiveAccent, modifier = Modifier.weight(1f))
                HeroMiniBar(label = "Habits", progress = progress(uiState.completedHabits, uiState.totalHabits), color = CyanAccent, modifier = Modifier.weight(1f))
            }
            if (!uiState.workoutCompletedToday) {
                GlassButton(text = "Start Today's Workout", onClick = onStartWorkout)
            } else if (uiState.todayWorkoutName != null) {
                Surface(shape = RoundedCornerShape(PremiumCornerRadius.md), color = PositiveAccent.copy(alpha = 0.12f), modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(text = uiState.todayWorkoutName, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = PositiveAccent)
                        Text(text = uiState.todayWorkoutStatusLabel ?: "Done", style = MaterialTheme.typography.labelSmall, color = PositiveAccent)
                    }
                }
            }
        }
    }
}

@Composable
private fun HeroMiniBar(label: String, progress: Float, color: Color, modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = MutedText)
        MetricProgressBar(progress = progress, color = color, height = 4.dp)
    }
}

@Composable
private fun WorkoutWidget(uiState: DashboardUiState, onNavigateToWorkout: () -> Unit) {
    PremiumCard(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        onClick = onNavigateToWorkout,
        elevation = PremiumElevation.widget
    ) {
        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            DumbbellIllustration(modifier = Modifier.size(90.dp).align(Alignment.BottomEnd))
            Column(
                modifier = Modifier.fillMaxHeight().padding(PremiumSpacing.cardPadding),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(PremiumSpacing.xs)
                ) {
                    Icon(imageVector = Icons.Default.Bolt, contentDescription = null, tint = AccentBlue, modifier = Modifier.size(13.dp))
                    SectionTitle(text = "WORKOUT", letterSpacing = true)
                }
                if (uiState.todayWorkoutName != null) {
                    Column(verticalArrangement = Arrangement.spacedBy(PremiumSpacing.xs)) {
                        MetricValue(text = uiState.todayWorkoutName, style = MaterialTheme.typography.titleMedium)
                        Surface(shape = RoundedCornerShape(PremiumCornerRadius.pill), color = PositiveAccent.copy(alpha = 0.18f)) {
                            Text(
                                text = uiState.todayWorkoutStatusLabel ?: "Done ✓",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = PositiveAccent,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(PremiumSpacing.sm)) {
                        SupportingText(text = "No workout yet")
                        Surface(shape = RoundedCornerShape(PremiumCornerRadius.pill), color = AccentBlue) {
                            Text(
                                text = "Start →",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DumbbellIllustration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val glow = AccentBlue.copy(alpha = 0.07f)
        val shape = AccentBlue.copy(alpha = 0.20f)
        val bar = AccentBlue.copy(alpha = 0.30f)
        val w = size.width; val h = size.height
        val cx = w * 0.5f; val cy = h * 0.5f

        rotate(-22f, pivot = Offset(cx, cy)) {
            // Soft background glow
            drawCircle(glow, radius = h * 0.46f, center = Offset(cx, cy))

            val plateW = w * 0.09f; val plateH = h * 0.44f
            val colW = w * 0.07f; val colH = h * 0.18f
            val gripHW = w * 0.26f; val gripH = h * 0.09f

            // Left plate (tall disc silhouette)
            drawRect(shape, Offset(cx - gripHW - colW - plateW, cy - plateH / 2f), Size(plateW, plateH))
            // Left collar
            drawRect(shape, Offset(cx - gripHW - colW, cy - colH / 2f), Size(colW, colH))
            // Grip bar (brighter to suggest metal)
            drawRect(bar, Offset(cx - gripHW, cy - gripH / 2f), Size(gripHW * 2f, gripH))
            // Right collar
            drawRect(shape, Offset(cx + gripHW, cy - colH / 2f), Size(colW, colH))
            // Right plate
            drawRect(shape, Offset(cx + gripHW + colW, cy - plateH / 2f), Size(plateW, plateH))
        }
    }
}

@Composable
private fun NutritionWidget(uiState: DashboardUiState, onNavigateToNutrition: () -> Unit) {
    PremiumCard(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        onClick = onNavigateToNutrition,
        elevation = PremiumElevation.widget
    ) {
        Column(modifier = Modifier.padding(PremiumSpacing.cardPadding), verticalArrangement = Arrangement.spacedBy(PremiumSpacing.sm)) {
            MetricHeader(text = "Nutrition", icon = Icons.Default.LocalDining, iconColor = PositiveAccent)
            MetricValue(text = "${uiState.caloriesToday} kcal", style = MaterialTheme.typography.titleLarge)
            SupportingText(text = "goal ${uiState.calorieGoal}")
            MetricProgressBar(progress = progress(uiState.caloriesToday, uiState.calorieGoal), color = PositiveAccent)
            SupportingText(text = "P ${uiState.proteinConsumedGrams}g / ${uiState.proteinMinGoalGrams}g")
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
private fun WeightWidget(uiState: DashboardUiState, onNavigateToBody: () -> Unit, isEditMode: Boolean = false) {
    if (uiState.weightKg == null && !isEditMode) return
    PremiumCard(modifier = Modifier.fillMaxWidth().fillMaxHeight(), onClick = onNavigateToBody, elevation = PremiumElevation.widget) {
        Column(modifier = Modifier.fillMaxSize().padding(PremiumSpacing.cardPadding), verticalArrangement = Arrangement.SpaceBetween) {
            SectionTitle(text = "Weight")
            if (uiState.weightKg != null) MetricValue(text = String.format("%.1f kg", uiState.weightKg), style = MaterialTheme.typography.titleLarge)
            else SupportingText(text = "No data")
        }
    }
}

@Composable
private fun BodyFatWidget(uiState: DashboardUiState, onNavigateToBody: () -> Unit, isEditMode: Boolean = false) {
    if (uiState.bodyFatPercent == null && !isEditMode) return
    PremiumCard(modifier = Modifier.fillMaxWidth().fillMaxHeight(), onClick = onNavigateToBody, elevation = PremiumElevation.widget) {
        Column(modifier = Modifier.fillMaxSize().padding(PremiumSpacing.cardPadding), verticalArrangement = Arrangement.SpaceBetween) {
            SectionTitle(text = "Body Fat")
            if (uiState.bodyFatPercent != null) MetricValue(text = String.format("%.1f%%", uiState.bodyFatPercent), style = MaterialTheme.typography.titleLarge)
            else SupportingText(text = "No data")
        }
    }
}

@Composable
private fun HabitsWidget(uiState: DashboardUiState, onNavigateToHabits: () -> Unit) {
    PremiumCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onNavigateToHabits,
        cornerRadius = PremiumCornerRadius.xl,
        elevation = PremiumElevation.widget
    ) {
        Column(modifier = Modifier.padding(PremiumSpacing.widgetPadding), verticalArrangement = Arrangement.spacedBy(PremiumSpacing.sm)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                MetricHeader(text = "Habits", icon = Icons.Default.FavoriteBorder, iconColor = CyanAccent)
                Text(
                    text = "${uiState.completedHabits} / ${uiState.totalHabits}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (uiState.totalHabits > 0 && uiState.completedHabits == uiState.totalHabits) PositiveAccent else PrimaryText
                )
            }
            MetricProgressBar(progress = progress(uiState.completedHabits, uiState.totalHabits), color = CyanAccent, height = 4.dp)
            val statusText = when {
                uiState.totalHabits == 0 -> "No habits set. Tap to add some."
                uiState.completedHabits == uiState.totalHabits -> "All habits complete today."
                else -> "${uiState.totalHabits - uiState.completedHabits} remaining"
            }
            val statusColor = if (uiState.completedHabits == uiState.totalHabits && uiState.totalHabits > 0) PositiveAccent else MutedText
            SupportingText(text = statusText, color = statusColor)
        }
    }
}

@Composable
private fun RecoveryHeroCard(uiState: DashboardUiState, onNavigateToBody: () -> Unit = {}) {
    var showBreakdown by remember { mutableStateOf(false) }
    PremiumCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onNavigateToBody,
        containerColor = MainCard,
        cornerRadius = PremiumCornerRadius.xl,
        elevation = PremiumElevation.raised
    ) {
        Column(modifier = Modifier.padding(PremiumSpacing.lg), verticalArrangement = Arrangement.spacedBy(PremiumSpacing.sm)) {
            SectionTitle(text = "Recovery")
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
                    MetricRing(
                        progress = (uiState.recoveryScore / 100f).coerceIn(0f, 1f),
                        modifier = Modifier.size(72.dp),
                        strokeWidth = 7.dp,
                        color = ringColor
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            MetricValue(text = "${uiState.recoveryScore}", style = MaterialTheme.typography.titleLarge, color = ringColor)
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
    }
}

@Composable
private fun RecommendationCard(recommendation: DailyRecommendationUiState?, workoutCompletedToday: Boolean = false) {
    PremiumFeatureCard {
        if (workoutCompletedToday) {
            Surface(shape = RoundedCornerShape(PremiumCornerRadius.pill), color = PositiveAccent.copy(alpha = 0.15f)) {
                Text(text = "WORKOUT DONE TODAY", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = PositiveAccent, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }
        }
        SectionTitle(text = "TOMORROW", letterSpacing = true)
        if (recommendation == null) {
            SupportingText(text = "Log body metrics, nutrition, and workouts to unlock your daily recommendation.")
        } else {
            val badgeColor = when {
                recommendation.title.contains("Rest", ignoreCase = true) -> Color(0xFF9C77E0)
                recommendation.title.contains("Strength", ignoreCase = true) -> AccentBlue
                recommendation.title.contains("Active", ignoreCase = true) || recommendation.title.contains("Recovery", ignoreCase = true) -> PositiveAccent
                recommendation.title.contains("Walk", ignoreCase = true) || recommendation.title.contains("Mobility", ignoreCase = true) -> CyanAccent
                else -> CyanAccent
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Surface(shape = RoundedCornerShape(PremiumCornerRadius.pill), color = badgeColor.copy(alpha = 0.18f)) {
                        Text(text = recommendation.title.uppercase(), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = badgeColor, modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp))
                    }
                    MetricValue(text = recommendation.suggestedFocus, style = MaterialTheme.typography.titleLarge)
                    recommendation.reasons.take(2).forEach { reason ->
                        SupportingText(text = "· $reason")
                    }
                }
                RecommendationIllustration(title = recommendation.title)
            }
        }
    }
}

@Composable
private fun ViewProgressCard(onClick: () -> Unit) {
    PremiumCard(modifier = Modifier.fillMaxWidth(), onClick = onClick) {
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
    val floatY = rememberFloatAnimation()
    val pulse = rememberGlowPulse()
    val (icon, iconColor) = when {
        title.contains("Rest", ignoreCase = true) -> Pair(Icons.Default.NightlightRound, Color(0xFF9C77E0))
        title.contains("Strength", ignoreCase = true) -> Pair(Icons.Default.Bolt, AccentBlue)
        title.contains("Walk", ignoreCase = true) || title.contains("Mobility", ignoreCase = true) -> Pair(Icons.Default.Route, CyanAccent)
        title.contains("Active", ignoreCase = true) || title.contains("Recovery", ignoreCase = true) -> Pair(Icons.Default.FavoriteBorder, PositiveAccent)
        else -> Pair(Icons.Default.FavoriteBorder, WarningAccent)
    }
    SoftIllustrationContainer(size = 68.dp, color = iconColor, modifier = Modifier.scale(pulse).offset(y = (-floatY).dp)) {
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
