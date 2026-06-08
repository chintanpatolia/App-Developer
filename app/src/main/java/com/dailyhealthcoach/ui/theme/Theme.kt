package com.dailyhealthcoach.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val AppBackground = Color(0xFF1E214B)
val MainCard = Color(0xFF38388C)
val SecondaryCard = Color(0xFF4B4BB2)
val AccentBlue = Color(0xFF168BFF)
val CyanAccent = Color(0xFF19C6D3)
val PrimaryText = Color(0xFFF5F7FF)
val MutedText = Color(0xFFB7B9D9)
val MutedControl = Color(0xFF6E70A8)
val PositiveAccent = Color(0xFF36D987)
val WarningAccent = Color(0xFFFFB15C)

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
