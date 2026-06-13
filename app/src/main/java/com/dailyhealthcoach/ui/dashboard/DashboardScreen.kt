package com.dailyhealthcoach.ui.dashboard

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
            SummaryGrid(uiState = uiState)
            RecoveryCard(uiState = uiState)
            RecommendationCard(recommendation = uiState.nextDayRecommendation)
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
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            SummaryProgressTile(
                label = "Habits",
                value = "${uiState.completedHabits} / ${uiState.totalHabits}",
                progress = progress(uiState.completedHabits, uiState.totalHabits),
                modifier = Modifier.weight(1f)
            )
            SummaryProgressTile(
                label = "Protein",
                value = "${uiState.proteinConsumedGrams}g",
                progress = progress(uiState.proteinConsumedGrams, uiState.proteinMinGoalGrams),
                modifier = Modifier.weight(1f)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            SummaryTile(
                label = "Sleep",
                value = String.format("%.1f h", uiState.sleepHours),
                modifier = Modifier.weight(1f)
            )
            SummaryTile(
                label = "Steps",
                value = "${uiState.steps}",
                modifier = Modifier.weight(1f)
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
            ThinProgressBar(progress = progress, color = CyanAccent)
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
private fun RecoveryCard(uiState: DashboardUiState) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MainCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Recovery", style = MaterialTheme.typography.titleSmall, color = MutedText)
            if (uiState.recoveryScore == null) {
                Text(
                    text = "Log sleep, nutrition, and workouts to calculate recovery.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MutedText
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${uiState.recoveryScore}/100",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText
                    )
                    Text(
                        text = uiState.recoveryLabel,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = CyanAccent
                    )
                }
                ThinProgressBar(
                    progress = (uiState.recoveryScore / 100f).coerceIn(0f, 1f),
                    color = recoveryColor(uiState.recoveryScore)
                )
                uiState.recoveryReasons.take(3).forEach { reason ->
                    Text(text = "· $reason", style = MaterialTheme.typography.bodySmall, color = MutedText)
                }
            }
        }
    }
}

@Composable
private fun RecommendationCard(recommendation: DailyRecommendationUiState?) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MainCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = "Tomorrow", style = MaterialTheme.typography.titleSmall, color = MutedText)
            if (recommendation == null) {
                Text(
                    text = "Log body metrics, nutrition, and workouts to generate a recommendation.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MutedText
                )
            } else {
                Text(
                    text = recommendation.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryText
                )
                Text(
                    text = recommendation.suggestedFocus,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CyanAccent
                )
                recommendation.reasons.take(3).forEach { reason ->
                    Text(text = "· $reason", style = MaterialTheme.typography.bodySmall, color = MutedText)
                }
            }
        }
    }
}

@Composable
private fun ViewProgressCard(onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = SecondaryCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Progress & Trends",
                style = MaterialTheme.typography.titleSmall,
                color = PrimaryText
            )
            TextButton(onClick = onClick) {
                Text(text = "View >", color = CyanAccent, fontWeight = FontWeight.SemiBold)
            }
        }
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
