package com.dailyhealthcoach.ui.premium

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dailyhealthcoach.ui.theme.MainCard
import com.dailyhealthcoach.ui.theme.SecondaryCard

/**
 * Base premium card. Handles press scale animation, gradient overlay, and elevation.
 * Pass [onClick] to enable press interaction; omit for static cards.
 */
@Composable
fun PremiumCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    containerColor: Color = SecondaryCard,
    cornerRadius: Dp = PremiumCornerRadius.lg,
    elevation: Dp = PremiumElevation.card,
    gradient: List<Color> = DashboardGradients.cardSurface,
    content: @Composable BoxScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed && onClick != null) 0.98f else 1f,
        animationSpec = tween(durationMillis = DashboardAnimations.pressMs),
        label = "premium_card_scale"
    )
    val clickModifier = if (onClick != null) {
        Modifier.clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
    } else Modifier
    ElevatedCard(
        modifier = modifier
            .scale(scale)
            .border(1.dp, Color.White.copy(alpha = 0.09f), RoundedCornerShape(cornerRadius))
            .then(clickModifier),
        shape = RoundedCornerShape(cornerRadius),
        colors = CardDefaults.elevatedCardColors(containerColor = containerColor),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(gradient)),
            content = content
        )
    }
}

/** Hero-scale card for the health score panel. No click — sub-elements handle interaction. */
@Composable
fun PremiumHeroCard(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(PremiumCornerRadius.hero)),
        shape = RoundedCornerShape(PremiumCornerRadius.hero),
        colors = CardDefaults.elevatedCardColors(containerColor = MainCard),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = PremiumElevation.hero)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(DashboardGradients.heroSurface)),
            content = content
        )
    }
}

/** Small metric tile: fills parent height, card padding, widget elevation. */
@Composable
fun PremiumMetricCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    PremiumCard(
        modifier = modifier.fillMaxWidth().fillMaxHeight(),
        onClick = onClick,
        elevation = PremiumElevation.widget
    ) {
        Column(modifier = Modifier.padding(PremiumSpacing.cardPadding), content = content)
    }
}

/** Full-width feature card: MainCard background, xl corners, raised elevation. */
@Composable
fun PremiumFeatureCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    containerColor: Color = MainCard,
    content: @Composable ColumnScope.() -> Unit
) {
    PremiumCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        containerColor = containerColor,
        cornerRadius = PremiumCornerRadius.xl,
        elevation = PremiumElevation.raised
    ) {
        Column(modifier = Modifier.padding(PremiumSpacing.lg), content = content)
    }
}
