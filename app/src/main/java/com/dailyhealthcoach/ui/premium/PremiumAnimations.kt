package com.dailyhealthcoach.ui.premium

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

/** Returns an animated [0..1] progress value that smoothly tracks [target]. */
@Composable
fun rememberProgressAnimation(target: Float): Float {
    val animated by animateFloatAsState(
        targetValue = target.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = DashboardAnimations.progressMs, easing = FastOutSlowInEasing),
        label = "progress_anim"
    )
    return animated
}

/** Continuous scale pulse between [minVal] and [maxVal] for glow/breathing effects. */
@Composable
fun rememberGlowPulse(minVal: Float = 1f, maxVal: Float = 1.07f): Float {
    val transition = rememberInfiniteTransition(label = "glow_pulse")
    val pulse by transition.animateFloat(
        initialValue = minVal,
        targetValue = maxVal,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = DashboardAnimations.glowMs, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_val"
    )
    return pulse
}

/** Continuous vertical float offset for illustration elements (in dp). */
@Composable
fun rememberFloatAnimation(amplitude: Float = 4f): Float {
    val transition = rememberInfiniteTransition(label = "float_anim")
    val floatY by transition.animateFloat(
        initialValue = 0f,
        targetValue = amplitude,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = DashboardAnimations.floatMs, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float_val"
    )
    return floatY
}
