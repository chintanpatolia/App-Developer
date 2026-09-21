package com.supplementtracker.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1A6B5A),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB2DFDB),
    onPrimaryContainer = Color(0xFF00251E),
    secondary = Color(0xFF4A6362),
    background = Color(0xFFF8FAF9),
    surface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFFECF0EF),
    onSurface = Color(0xFF191C1C),
    onSurfaceVariant = Color(0xFF3F4948),
    outline = Color(0xFF6F797A),
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF80CBC4),
    onPrimary = Color(0xFF00362E),
    primaryContainer = Color(0xFF004F43),
    onPrimaryContainer = Color(0xFF9EF2E9),
    secondary = Color(0xFFB2CBCA),
    background = Color(0xFF191C1C),
    surface = Color(0xFF191C1C),
    surfaceVariant = Color(0xFF3F4948),
    onSurface = Color(0xFFE1E3E2),
    onSurfaceVariant = Color(0xFFBFC9C8),
    outline = Color(0xFF899392),
)

@Composable
fun SupplementTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
