package com.example.clickanime.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// TMDB brand colours
private val TmdbDarkBlue  = Color(0xFF0D253F)
private val TmdbBlue      = Color(0xFF01B4E4)
private val TmdbGreen     = Color(0xFF90CEA1)

private val LightScheme = lightColorScheme(
    primary         = TmdbDarkBlue,
    onPrimary       = Color.White,
    secondary       = TmdbBlue,
    onSecondary     = Color.White,
    tertiary        = TmdbGreen,
    onTertiary      = TmdbDarkBlue,
    background      = Color(0xFFF8F9FA),
    onBackground    = Color(0xFF1A1A2E),
    surface         = Color.White,
    onSurface       = Color(0xFF1A1A2E),
    primaryContainer   = Color(0xFFE8F4FD),
    onPrimaryContainer = TmdbDarkBlue,
    secondaryContainer = Color(0xFFD0EEF9),
    onSecondaryContainer = TmdbDarkBlue,
    surfaceVariant  = Color(0xFFF1F3F4),
    onSurfaceVariant = Color(0xFF5F6368),
    error           = Color(0xFFB00020),
)

private val DarkScheme = darkColorScheme(
    primary         = TmdbBlue,
    onPrimary       = Color.White,
    secondary       = TmdbGreen,
    onSecondary     = TmdbDarkBlue,
    tertiary        = TmdbGreen,
    onTertiary      = TmdbDarkBlue,
    background      = Color(0xFF0D0D1A),
    onBackground    = Color(0xFFE8EAF6),
    surface         = Color(0xFF1A1A2E),
    onSurface       = Color(0xFFE8EAF6),
    primaryContainer   = Color(0xFF0D253F),
    onPrimaryContainer = TmdbBlue,
    secondaryContainer = Color(0xFF1A3A4A),
    onSecondaryContainer = TmdbBlue,
    surfaceVariant  = Color(0xFF23233A),
    onSurfaceVariant = Color(0xFFAAABBD),
    error           = Color(0xFFCF6679),
)

@Composable
fun MovieAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val ctx = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(ctx) else dynamicLightColorScheme(ctx)
        }
        darkTheme -> DarkScheme
        else      -> LightScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        content     = content
    )
}