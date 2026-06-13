package com.dailyhealthcoach.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val AppBackground = Color(0xFF0E0F17)   // near-black, faint navy tint
val MainCard = Color(0xFF181B27)        // dark charcoal — replaces heavy purple
val SecondaryCard = Color(0xFF1F2235)   // slightly lighter charcoal for elevated surfaces
val AccentBlue = Color(0xFF2A8FFF)
val CyanAccent = Color(0xFF1EC8D5)
val PrimaryText = Color(0xFFF3F5FF)
val MutedText = Color(0xFF8C8FA8)       // cooler gray, less lavender-purple
val MutedControl = Color(0xFF353852)    // dark muted for track/bg fills
val PositiveAccent = Color(0xFF36D987)
val WarningAccent = Color(0xFFFFB15C)
val CardStroke = Color(0xFF252840)

@Composable
fun DailyHealthCoachTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            background = AppBackground,
            surface = MainCard,
            primary = AccentBlue,
            secondary = CyanAccent,
            onBackground = PrimaryText,
            onSurface = PrimaryText,
            onPrimary = PrimaryText,
            onSecondary = AppBackground,
            surfaceVariant = SecondaryCard,
            onSurfaceVariant = MutedText
        ),
        content = content
    )
}
