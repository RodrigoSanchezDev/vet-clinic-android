package com.example.vet_clinic_android.ui.theme

import android.app.Activity
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

private val DarkColorScheme = darkColorScheme(
    primary = VetPrimaryLight,
    onPrimary = VetTextPrimary,
    primaryContainer = VetPrimaryDark,
    onPrimaryContainer = VetTextOnPrimary,

    secondary = VetSecondaryLight,
    onSecondary = VetTextPrimary,
    secondaryContainer = VetSecondaryDark,
    onSecondaryContainer = VetTextOnPrimary,

    tertiary = VetTertiaryLight,
    onTertiary = VetTextPrimary,
    tertiaryContainer = VetTertiaryDark,
    onTertiaryContainer = VetTextOnPrimary,

    background = Color(0xFF121212),
    onBackground = Color(0xFFE0E0E0),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE0E0E0),

    error = VetError,
    onError = VetTextOnPrimary
)

private val LightColorScheme = lightColorScheme(
    primary = VetPrimary,
    onPrimary = VetTextOnPrimary,
    primaryContainer = VetPrimaryLight,
    onPrimaryContainer = VetTextPrimary,

    secondary = VetSecondary,
    onSecondary = VetTextOnPrimary,
    secondaryContainer = VetSecondaryLight,
    onSecondaryContainer = VetTextPrimary,

    tertiary = VetTertiary,
    onTertiary = VetTextOnPrimary,
    tertiaryContainer = VetTertiaryLight,
    onTertiaryContainer = VetTextPrimary,

    background = VetBackground,
    onBackground = VetTextPrimary,
    surface = VetSurface,
    onSurface = VetTextPrimary,
    surfaceVariant = VetSurfaceVariant,
    onSurfaceVariant = VetTextSecondary,

    error = VetError,
    onError = VetTextOnPrimary,

    outline = Color(0xFFBDBDBD)
)

@Composable
fun VetclinicandroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Alias para compatibilidad con el nombre esperado
@Composable
fun VetClinicAndroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) = VetclinicandroidTheme(darkTheme, dynamicColor, content)

// Alias principal
@Composable
fun VetClinicTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Desactivado por defecto para mantener colores personalizados
    content: @Composable () -> Unit
) = VetclinicandroidTheme(darkTheme, dynamicColor, content)

