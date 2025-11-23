package com.example.vet_clinic_android.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Estilos centralizados para componentes reutilizables
 * Permite mantener consistencia y facilitar cambios futuros
 */

/**
 * Configuración de estilos para BannerCard
 */
object BannerCardDefaults {
    /**
     * Estilo de texto para el título del banner
     */
    @Composable
    fun titleTextStyle(): TextStyle = MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.Bold
    )

    /**
     * Estilo de texto para el subtítulo del banner
     */
    @Composable
    fun subtitleTextStyle(): TextStyle = MaterialTheme.typography.bodySmall

    /**
     * Padding interno del banner
     */
    val contentPadding: Dp = 20.dp

    /**
     * Espaciado entre elementos verticales
     */
    val verticalSpacing: Dp = 12.dp

    /**
     * Espaciado entre elementos horizontales
     */
    val horizontalSpacing: Dp = 12.dp

    /**
     * Espaciado pequeño entre título y subtítulo
     */
    val textSpacing: Dp = 4.dp

    /**
     * Tamaño por defecto del icono
     */
    val defaultIconSize: Dp = 48.dp

    /**
     * Alpha para el subtítulo
     */
    const val subtitleAlpha: Float = 0.9f
}

/**
 * Configuración de estilos para Cards del menú
 */
object MenuCardDefaults {
    /**
     * Altura por defecto de las cards del menú
     */
    val cardHeight: Dp = 140.dp

    /**
     * Tamaño del icono en las cards del menú
     */
    val iconSize: Dp = 40.dp

    /**
     * Espaciado entre icono y texto
     */
    val iconTextSpacing: Dp = 8.dp

    /**
     * Padding interno de las cards
     */
    val cardPadding: Dp = 12.dp

    /**
     * Elevación por defecto
     */
    val defaultElevation: Dp = 2.dp

    /**
     * Elevación al presionar
     */
    val pressedElevation: Dp = 8.dp

    /**
     * Estilo de texto para el título de la card
     */
    @Composable
    fun titleTextStyle(): TextStyle = MaterialTheme.typography.bodyMedium.copy(
        fontWeight = FontWeight.Medium
    )
}

/**
 * Configuración de espaciados comunes en la aplicación
 */
object AppSpacing {
    val extraSmall: Dp = 4.dp
    val small: Dp = 8.dp
    val medium: Dp = 16.dp
    val large: Dp = 24.dp
    val extraLarge: Dp = 32.dp
}

/**
 * Configuración de tamaños de iconos comunes
 */
object AppIconSizes {
    val small: Dp = 16.dp
    val medium: Dp = 24.dp
    val large: Dp = 32.dp
    val extraLarge: Dp = 48.dp
}

/**
 * Configuración de elevaciones comunes
 */
object AppElevation {
    val none: Dp = 0.dp
    val small: Dp = 2.dp
    val medium: Dp = 4.dp
    val large: Dp = 8.dp
    val extraLarge: Dp = 16.dp
}

/**
 * Configuración de bordes redondeados
 */
object AppCorners {
    val small: Dp = 8.dp
    val medium: Dp = 16.dp
    val large: Dp = 24.dp
    val extraLarge: Dp = 32.dp
}

