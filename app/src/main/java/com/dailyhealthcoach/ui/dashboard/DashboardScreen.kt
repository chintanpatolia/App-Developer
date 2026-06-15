package com.dailyhealthcoach.ui.dashboard

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.Icon
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
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    DashboardScreen(uiState = uiState, onNavigateToProgress = onNavigateToProgress, onNavigateToSettings = onNavigateToSettings, modifier = modifier)
}

@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onNavigateToProgress: () -> Unit,
    onNavigateToSettings: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.fillMaxSize(), color = Color.Transparent) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(start = 18.dp, top = 24.dp, end = 18.dp, bottom = 36.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DashboardHeader(onNavigateToSettings = onNavigateToSettings)
            RecoveryHeroCard(uiState = uiState)
            SummaryGrid(uiState = uiState)
            RecommendationCard(recommendation = uiState.nextDayRecommendation, workoutCompletedToday = uiState.workoutCompletedToday)
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
private fun DashboardHeader(onNavigateToSettings: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.weight(1f)) {
            Text(
                text = "Daily Health Coach",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = PrimaryText
            )
            Text(
                text = "Today's health snapshot",
                style = MaterialTheme.typography.bodyMedium,
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
private fun SummaryGrid(uiState: DashboardUiState) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            HealthMetricTile(
                icon = Icons.Default.AutoAwesome,
                iconColor = CyanAccent,
                value = "${uiState.completedHabits} / ${uiState.totalHabits}",
                label = "Habits",
                progress = progress(uiState.completedHabits, uiState.totalHabits),
                modifier = Modifier.weight(1f).fillMaxHeight()
            )
            HealthMetricTile(
                icon = Icons.Default.LocalDining,
                iconColor = PositiveAccent,
                value = "${uiState.proteinConsumedGrams}g",
                subValue = "goal ${uiState.proteinMinGoalGrams}g",
                label = "Protein",
                progress = progress(uiState.proteinConsumedGrams, uiState.proteinMinGoalGrams),
                modifier = Modifier.weight(1f).fillMaxHeight()
            )
        }
        Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            HealthMetricTile(
                icon = Icons.Default.DarkMode,
                iconColor = Color(0xFF9C77E0),
                value = String.format("%.1f h", uiState.sleepHours),
                label = "Sleep",
                modifier = Modifier.weight(1f).fillMaxHeight()
            )
            HealthMetricTile(
                icon = Icons.Default.Route,
                iconColor = AccentBlue,
                value = "${uiState.steps}",
                subValue = "/ ${uiState.stepGoal}",
                label = "Steps",
                progress = if (uiState.stepGoal > 0) (uiState.steps.toFloat() / uiState.stepGoal).coerceIn(0f, 1f) else 0f,
                modifier = Modifier.weight(1f).fillMaxHeight()
            )
        }
    }
}

@Composable
private fun SummaryTile(label: String, value: String, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = SecondaryCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = label, style = MaterialTheme.typography.bodySmall, color = MutedText)
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryText
            )
        }
    }
}

@Composable
private fun SummaryProgressTile(
    label: String,
    value: String,
    progress: Float,
    color: Color = CyanAccent,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = SecondaryCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = label, style = MaterialTheme.typography.bodySmall, color = MutedText)
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryText
            )
            ThinProgressBar(progress = progress, color = color)
        }
    }
}

@Composable
private fun ThinProgressBar(progress: Float, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp)
            .clip(RoundedCornerShape(999.dp))
            .background(MutedControl.copy(alpha = 0.45f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .height(4.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(color)
        )
    }
}

@Composable
private fun RecoveryHeroCard(uiState: DashboardUiState) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MainCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Recovery",
                style = MaterialTheme.typography.labelMedium,
                color = MutedText,
                fontWeight = FontWeight.SemiBold
            )
            if (uiState.recoveryScore == null) {
                Text(
                    text = "Log sleep, nutrition, and workouts to see your recovery score.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MutedText
                )
            } else {
                val ringColor = recoveryColor(uiState.recoveryScore)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProgressRing(
                        progress = (uiState.recoveryScore / 100f).coerceIn(0f, 1f),
                        modifier = Modifier.size(96.dp),
                        strokeWidth = 10.dp,
                        color = ringColor
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${uiState.recoveryScore}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = ringColor
                            )
                            Text(text = "/ 100", style = MaterialTheme.typography.labelSmall, color = MutedText)
                        }
                    }
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = uiState.recoveryLabel,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = ringColor
                        )
                        uiState.recoveryReasons.take(3).forEach { reason ->
                            Text(text = "· $reason", style = MaterialTheme.typography.bodySmall, color = MutedText)
                        }
                    }
                }
            }
        }
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
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (workoutCompletedToday) {
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = PositiveAccent.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "WORKOUT DONE TODAY",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = PositiveAccent,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
            Text(
                text = "TOMORROW",
                style = MaterialTheme.typography.labelSmall,
                color = MutedText,
                fontWeight = FontWeight.SemiBold
            )
            if (recommendation == null) {
                Text(
                    text = "Log body metrics, nutrition, and workouts to unlock your daily recommendation.",
                    style = MaterialTheme.typography.bodyMedium,
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
                        Surface(
                            shape = RoundedCornerShape(999.dp),
                            color = badgeColor.copy(alpha = 0.18f)
                        ) {
                            Text(
                                text = recommendation.title.uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = badgeColor,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                            )
                        }
                        Text(
                            text = recommendation.suggestedFocus,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryText
                        )
                        recommendation.reasons.take(2).forEach { reason ->
                            Text(text = "· $reason", style = MaterialTheme.typography.bodySmall, color = MutedText)
                        }
                    }
                    RecommendationIllustration(title = recommendation.title)
                }
            }
        }
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Progress & Trends",
                style = MaterialTheme.typography.titleSmall,
                color = PrimaryText
            )
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
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(34.dp)
        )
    }
}

private fun recoveryColor(score: Int): Color = when {
    score >= 80 -> PositiveAccent
    score >= 60 -> CyanAccent
    score >= 40 -> WarningAccent
    else -> Color(0xFFFF6B6B)
}

private fun progress(value: Int, target: Int): Float {
    if (target <= 0) return 0f
    return (value.toFloat() / target.toFloat()).coerceIn(0f, 1f)
}
