package com.esaudev.clipchallenge.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val DarkColorPalette = darkColors(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    background = BackgroundColor,
    onBackground = DarkGray,
    surface = SurfaceColor,
    onSurface = DarkGray,
    onPrimary = onPrimaryColor,
    onSecondary = onSecondaryColor
)

private val LightColorPalette = lightColors(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    background = BackgroundColor,
    onBackground = DarkGray,
    surface = SurfaceColor,
    onSurface = DarkGray,
    onPrimary = onPrimaryColor,
    onSecondary = onSecondaryColor
)

@Composable
fun ClipChallengeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(LocalSpacing provides Dimensions()) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}
