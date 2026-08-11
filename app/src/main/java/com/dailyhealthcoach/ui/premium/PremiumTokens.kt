package com.dailyhealthcoach.ui.premium

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dailyhealthcoach.ui.theme.AccentBlue
import com.dailyhealthcoach.ui.theme.CyanAccent

object PremiumSpacing {
    val xs = 4.dp
    val sm = 8.dp
    val md = 12.dp
    val lg = 16.dp
    val xl = 20.dp
    val xxl = 28.dp
    val cardPadding = 12.dp
    val widgetPadding = 14.dp
    val heroPadding = 18.dp
}

object PremiumCornerRadius {
    val sm = 8.dp
    val md = 12.dp
    val lg = 16.dp
    val xl = 20.dp
    val hero = 28.dp
    val pill = 999.dp
}

object PremiumElevation {
    val flat = 0.dp
    val card = 2.dp
    val widget = 3.dp
    val raised = 4.dp
    val hero = 8.dp
}

object DashboardAnimations {
    const val pressMs = 120
    const val heroPressMs = 100
    const val progressMs = 700
    const val ringMs = 1000
    const val glowMs = 1800
    const val floatMs = 2400
}

object DashboardGradients {
    val cardSurface: List<Color> = listOf(Color.White.copy(alpha = 0.13f), Color.White.copy(alpha = 0.03f), Color.Transparent)
    val heroSurface: List<Color> = listOf(Color.White.copy(alpha = 0.20f), Color.White.copy(alpha = 0.06f), Color.Transparent)
    val coachBadge: List<Color> = listOf(CyanAccent.copy(alpha = 0.28f), AccentBlue.copy(alpha = 0.14f))
    val ctaButton: List<Color> = listOf(Color(0xFF3DB8FF), AccentBlue)
}
