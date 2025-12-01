package com.example.vet_clinic_android.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * 🎬 LoadingIndicator - Componente reutilizable de indicador de progreso
 *
 * Muestra un indicador circular de carga con mensaje personalizado.
 * Puede mostrarse como overlay modal o inline en la UI.
 *
 * @param isLoading Estado que controla la visibilidad del indicador
 * @param message Mensaje personalizado a mostrar (ej: "Cargando datos...")
 * @param modifier Modificador de Compose
 * @param isModal Si true, se muestra como dialog modal bloqueando la UI
 * @param useLinearProgress Si true, usa LinearProgressIndicator en lugar de Circular
 *
 * @author Rodrigo Sánchez
 */
@Composable
fun LoadingIndicator(
    isLoading: Boolean,
    message: String = "Cargando...",
    modifier: Modifier = Modifier,
    isModal: Boolean = true,
    useLinearProgress: Boolean = false
) {
    AnimatedVisibility(
        visible = isLoading,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        if (isModal) {
            LoadingDialog(
                message = message,
                useLinearProgress = useLinearProgress
            )
        } else {
            LoadingContent(
                message = message,
                useLinearProgress = useLinearProgress,
                modifier = modifier
            )
        }
    }
}

/**
 * Contenido del loading indicator como dialog modal
 */
@Composable
private fun LoadingDialog(
    message: String,
    useLinearProgress: Boolean
) {
    Dialog(
        onDismissRequest = { /* No permitir cerrar manualmente */ },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        LoadingContent(
            message = message,
            useLinearProgress = useLinearProgress,
            modifier = Modifier
                .width(280.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(32.dp)
        )
    }
}

/**
 * Contenido visual del loading indicator
 */
@Composable
private fun LoadingContent(
    message: String,
    useLinearProgress: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Indicador de progreso
        if (useLinearProgress) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        } else {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp
            )
        }

        // Mensaje personalizado
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * 🎬 LoadingOverlay - Overlay de carga que cubre toda la pantalla
 *
 * Útil para bloquear la UI completamente durante operaciones críticas
 *
 * @param isLoading Estado que controla la visibilidad
 * @param message Mensaje a mostrar
 */
@Composable
fun LoadingOverlay(
    isLoading: Boolean,
    message: String = "Cargando..."
) {
    AnimatedVisibility(
        visible = isLoading,
        enter = fadeIn(animationSpec = tween(400)),
        exit = fadeOut(animationSpec = tween(400))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f)),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 16.dp,
                modifier = Modifier
                    .widthIn(min = 280.dp, max = 340.dp)
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 40.dp, vertical = 48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 5.dp
                    )
                    Text(
                        text = message,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/**
 * 🎬 InlineLoadingIndicator - Indicador de progreso inline compacto
 *
 * Útil para mostrar loading dentro de cards o secciones específicas
 *
 * @param isLoading Estado que controla la visibilidad
 * @param message Mensaje a mostrar
 * @param modifier Modificador de Compose
 */
@Composable
fun InlineLoadingIndicator(
    isLoading: Boolean,
    message: String = "Cargando...",
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isLoading,
        enter = fadeIn() + androidx.compose.animation.expandVertically(),
        exit = fadeOut() + androidx.compose.animation.shrinkVertically()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 3.dp
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

/**
 * 🎬 PulsingDot - Indicador de carga con puntos pulsantes
 *
 * Alternativa visual más sutil para indicar actividad
 */
@Composable
fun PulsingDot(
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(visible = isLoading) {
        val infiniteTransition = rememberInfiniteTransition(label = "pulse")
        val alpha by infiniteTransition.animateFloat(
            initialValue = 0.3f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(800, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "alpha"
        )

        Box(
            modifier = modifier
                .size(8.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = alpha),
                    shape = RoundedCornerShape(50)
                )
        )
    }
}

