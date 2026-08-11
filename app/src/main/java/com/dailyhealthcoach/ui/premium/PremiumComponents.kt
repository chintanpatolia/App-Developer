package com.dailyhealthcoach.ui.premium

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import com.dailyhealthcoach.ui.theme.AccentBlue
import com.dailyhealthcoach.ui.theme.AppBackground as PremiumBackground
import com.dailyhealthcoach.ui.theme.CyanAccent
import com.dailyhealthcoach.ui.theme.MainCard
import com.dailyhealthcoach.ui.theme.MutedControl
import com.dailyhealthcoach.ui.theme.MutedText
import com.dailyhealthcoach.ui.theme.PrimaryText
import com.dailyhealthcoach.ui.theme.CardStroke
import com.dailyhealthcoach.ui.theme.SecondaryCard

@Composable
fun AppBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PremiumBackground)
    ) {
        content()
    }
}

@Composable
fun MainFeatureCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth().border(1.dp, CardStroke, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MainCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.padding(18.dp)) {
            content()
        }
    }
}

@Composable
fun FloatingTitlePill(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(999.dp),
        color = AccentBlue,
        shadowElevation = 8.dp
    ) {
        Text(
            text = text,
            color = PrimaryText,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 10.dp)
        )
    }
}

@Composable
fun PrimaryBlueButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.96f else 1f, label = "btn_scale")
    Button(
        onClick = onClick,
        modifier = modifier.height(54.dp).scale(scale),
        shape = RoundedCornerShape(999.dp),
        colors = ButtonDefaults.buttonColors(containerColor = AccentBlue),
        interactionSource = interactionSource
    ) {
        Text(text = text, color = PrimaryText, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun StatTile(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.border(1.dp, CardStroke, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SecondaryCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = value, color = CyanAccent, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(text = label, color = MutedText, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun ProgressRing(
    progress: Float,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 10.dp,
    color: Color = CyanAccent,
    centerContent: @Composable () -> Unit
) {
    val animatable = remember { Animatable(0f) }
    LaunchedEffect(progress) {
        animatable.animateTo(
            targetValue = progress,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
    }
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val stroke = strokeWidth.toPx()
            val diameter = size.minDimension - stroke
            val topLeft = Offset((size.width - diameter) / 2f, (size.height - diameter) / 2f)
            val arcSize = Size(diameter, diameter)
            drawArc(
                color = MutedControl.copy(alpha = 0.4f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360f * animatable.value.coerceIn(0f, 1f),
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
        }
        centerContent()
    }
}

@Composable
fun MacroBar(
    label: String,
    value: String,
    progress: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "macro_bar_progress"
    )
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = label, color = PrimaryText, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyMedium)
            Text(text = value, color = MutedText, style = MaterialTheme.typography.bodySmall)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(MutedControl.copy(alpha = 0.5f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedProgress)
                    .height(5.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(color)
            )
        }
    }
}

@Composable
fun MeasurementTile(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.border(1.dp, CardStroke, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        color = SecondaryCard
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = value, color = CyanAccent, fontWeight = FontWeight.Bold)
            Text(text = label, color = MutedText, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun PlaceholderVisual(
    label: String,
    modifier: Modifier = Modifier,
    size: Dp = 150.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(SecondaryCard.copy(alpha = 0.65f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = MutedText,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(20.dp)
        )
    }
}

// Samsung Health-style metric tile: icon circle + large value + label + thin progress bar
@Composable
fun HealthMetricTile(
    icon: ImageVector,
    iconColor: Color,
    value: String,
    label: String,
    subValue: String = "",
    progress: Float = -1f,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "tile_scale"
    )
    val animatedProgress by animateFloatAsState(
        targetValue = if (progress >= 0f) progress else 0f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "tile_progress"
    )
    ElevatedCard(
        modifier = modifier
            .scale(scale)
            .clickable(interactionSource = interactionSource, indication = null) {},
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = SecondaryCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight().padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(iconColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(17.dp))
                }
                Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PrimaryText)
                if (subValue.isNotEmpty()) {
                    Text(subValue, style = MaterialTheme.typography.labelSmall, color = MutedText)
                }
                Text(label, style = MaterialTheme.typography.bodySmall, color = MutedText)
            }
            if (progress >= 0f) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(MutedControl.copy(alpha = 0.45f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(animatedProgress.coerceIn(0f, 1f))
                            .height(3.dp)
                            .clip(RoundedCornerShape(999.dp))
                            .background(iconColor)
                    )
                }
            }
        }
    }
}

// ─── Premium Design System — Dashboard Components ────────────────────────────

/** Uppercase section label with optional letter-spacing for dashboard headers (TODAY, HEALTH SCORE). */
@Composable
fun SectionTitle(
    text: String,
    modifier: Modifier = Modifier,
    letterSpacing: Boolean = false
) {
    val style = if (letterSpacing)
        MaterialTheme.typography.labelSmall.copy(letterSpacing = 2.sp)
    else
        MaterialTheme.typography.labelSmall
    Text(text = text, style = style, color = MutedText, fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold, modifier = modifier)
}

/** Widget header row: optional icon chip or inline icon + label text. */
@Composable
fun MetricHeader(
    text: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    iconColor: Color = MutedText,
    showIconChip: Boolean = false
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        if (icon != null) {
            if (showIconChip) {
                Box(
                    modifier = Modifier.size(26.dp).clip(RoundedCornerShape(8.dp)).background(iconColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(14.dp))
                }
            } else {
                Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(14.dp))
            }
        }
        Text(text = text, style = MaterialTheme.typography.labelSmall, color = MutedText, fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold)
    }
}

/** Primary metric value text. */
@Composable
fun MetricValue(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    color: Color = PrimaryText
) {
    Text(text = text, style = style, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, color = color, modifier = modifier)
}

/** Muted secondary / supporting text. */
@Composable
fun SupportingText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MutedText
) {
    Text(text = text, style = MaterialTheme.typography.bodySmall, color = color, modifier = modifier)
}

/** Animated progress bar. Animates from current displayed value to [progress] automatically. */
@Composable
fun MetricProgressBar(
    progress: Float,
    color: Color,
    modifier: Modifier = Modifier,
    height: Dp = 3.dp
) {
    val animated by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing),
        label = "metric_progress_bar"
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(999.dp))
            .background(MutedControl.copy(alpha = 0.4f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(animated)
                .height(height)
                .clip(RoundedCornerShape(999.dp))
                .background(color)
        )
    }
}

/** Thin wrapper around [ProgressRing] with consistent premium defaults. */
@Composable
fun MetricRing(
    progress: Float,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 10.dp,
    color: Color = CyanAccent,
    centerContent: @Composable () -> Unit
) = ProgressRing(progress = progress, modifier = modifier, strokeWidth = strokeWidth, color = color, centerContent = centerContent)

/** Small pill chip for CTA affordances ("Tap to start →"). */
@Composable
fun ActionChip(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val chipModifier = if (onClick != null) modifier.clickable(onClick = onClick) else modifier
    Box(
        modifier = chipModifier
            .clip(RoundedCornerShape(6.dp))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.labelSmall, color = color, fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold)
    }
}

/** Full-width gradient CTA button with built-in press scale animation. */
@Composable
fun GlassButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    gradient: List<Color> = listOf(Color(0xFF3DB8FF), AccentBlue)
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "glass_btn_scale"
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .clip(RoundedCornerShape(14.dp))
            .background(Brush.horizontalGradient(gradient))
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
            .padding(vertical = 13.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = PrimaryText, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
    }
}

/** Horizontal gradient "Coach" badge chip. */
@Composable
fun CoachBadge(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(999.dp))
            .background(Brush.horizontalGradient(listOf(CyanAccent.copy(alpha = 0.28f), AccentBlue.copy(alpha = 0.14f))))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(11.dp))
            Text(text = "Coach", style = MaterialTheme.typography.labelSmall, color = CyanAccent, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
        }
    }
}

/** Subtle horizontal line divider. */
@Composable
fun PremiumDivider(
    modifier: Modifier = Modifier,
    color: Color = MutedControl,
    alpha: Float = 0.3f
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color.copy(alpha = alpha))
    )
}

/** Circular background container for illustration icons. Matches the RecommendationIllustration style. */
@Composable
fun SoftIllustrationContainer(
    size: Dp = 68.dp,
    color: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color.copy(alpha = 0.14f)),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

// ─── Samsung Health-style macro progress row: icon circle + label + bar + current/goal ───
@Composable
fun MacroProgressRow(
    icon: ImageVector,
    iconColor: Color,
    label: String,
    current: String,
    goal: String,
    progress: Float,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "macro_row_progress"
    )
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(iconColor.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(14.dp))
        }
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(label, style = MaterialTheme.typography.bodySmall, color = PrimaryText, fontWeight = FontWeight.Medium)
                Text("$current / $goal", style = MaterialTheme.typography.labelSmall, color = MutedText)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(MutedControl.copy(alpha = 0.45f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedProgress)
                        .height(4.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(iconColor)
                )
            }
        }
    }
}

@Composable
fun ScreenHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Daily Health Coach",
            color = PrimaryText,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Training, nutrition, body, and recovery", color = MutedText)
    }
}
