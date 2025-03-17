package com.example.nuerd.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val GreenLightColorScheme = lightColorScheme(
    primary = mainBackgroundColor,
    secondary = buttonBackgroundColor,
    background = secondaryBackgroundColor,
    surface = highlightColor,
    onPrimary = textColor,
    onSecondary = textColor,
    onBackground = textColor,
    onSurface = textColor,
    error = errorColor,

)

private val GreenDarkColorScheme = darkColorScheme(
    primary = darkMainBackgroundColor,
    secondary = darkButtonBackgroundColor,
    background = darkSecondaryBackgroundColor,
    surface = darkHighlightColor,
    onPrimary = darkTextColor,
    onSecondary = darkTextColor,
    onBackground = darkTextColor,
    onSurface = darkTextColor,
    error = darkErrorColor
)

private val BlueLightColorScheme = lightColorScheme(
    primary = mainBackgroundColorBlue,
    secondary = buttonBackgroundColorBlue,
    background = secondaryBackgroundColorBlue,
    surface = highlightColorBlue,
    onPrimary = textColorBlue,
    onSecondary = textColorBlue,
    onBackground = textColorBlue,
    onSurface = textColorBlue,
    error = errorColorBlue
)

private val BlueDarkColorScheme = darkColorScheme(
    primary = darkMainBackgroundColorBlue,
    secondary = darkButtonBackgroundColorBlue,
    background = darkSecondaryBackgroundColorBlue,
    surface = darkHighlightColorBlue,
    onPrimary = darkTextColorBlue,
    onSecondary = darkTextColorBlue,
    onBackground = darkTextColorBlue,
    onSurface = darkTextColorBlue,
    error = darkErrorColorBlue
)

private val YellowLightColorScheme = lightColorScheme(
    primary = mainBackgroundColorYellow,
    secondary = buttonBackgroundColorYellow,
    background = secondaryBackgroundColorYellow,
    surface = highlightColorYellow,
    onPrimary = textColorYellow,
    onSecondary = textColorYellow,
    onBackground = textColorYellow,
    onSurface = textColorYellow,
    error = errorColorYellow
)

private val YellowDarkColorScheme = darkColorScheme(
    primary = darkMainBackgroundColorYellow,
    secondary = darkButtonBackgroundColorYellow,
    background = darkSecondaryBackgroundColorYellow,
    surface = darkHighlightColorYellow,
    onPrimary = darkTextColorYellow,
    onSecondary = darkTextColorYellow,
    onBackground = darkTextColorYellow,
    onSurface = darkTextColorYellow,
    error = darkErrorColorYellow
)


@Composable
fun NuerdTheme(
    theme: String = "Green",
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Never use dynamic colors
    // dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // Always use your custom color schemes
    val colorScheme = if (darkTheme) {
        when (theme) {
            "Blue" -> BlueDarkColorScheme
            "Yellow" -> YellowDarkColorScheme
            else -> GreenDarkColorScheme
        }
    } else {
        when (theme) {
            "Blue" -> BlueLightColorScheme
            "Yellow" -> YellowLightColorScheme
            else -> GreenLightColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = pressStartTypography(),
        content = content
    )
}