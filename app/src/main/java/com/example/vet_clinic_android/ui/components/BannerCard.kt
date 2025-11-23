package com.example.vet_clinic_android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.example.vet_clinic_android.ui.theme.BannerCardDefaults

/**
 * Componente BannerCard optimizado con estilos centralizados
 * Permite mostrar información destacada con icono, título y subtítulo opcional
 *
 * @param modifier Modificador de Compose
 * @param icon Icono a mostrar
 * @param title Título principal
 * @param subtitle Subtítulo opcional
 * @param containerColor Color de fondo del banner
 * @param contentColor Color del contenido (texto e iconos)
 * @param verticalLayout Si es true, usa layout vertical; si es false, usa horizontal
 * @param iconSize Tamaño del icono
 */
@Composable
fun BannerCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    verticalLayout: Boolean = true,
    iconSize: Dp = BannerCardDefaults.defaultIconSize
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        val baseModifier = Modifier
            .fillMaxWidth()
            .padding(BannerCardDefaults.contentPadding)

        val iconModifier = Modifier.size(iconSize)

        if (verticalLayout) {
            BannerVerticalLayout(
                modifier = baseModifier,
                icon = icon,
                iconModifier = iconModifier,
                title = title,
                subtitle = subtitle,
                contentColor = contentColor
            )
        } else {
            BannerHorizontalLayout(
                modifier = baseModifier,
                icon = icon,
                iconModifier = iconModifier,
                title = title,
                subtitle = subtitle,
                contentColor = contentColor
            )
        }
    }
}

/**
 * Layout vertical interno del banner
 */
@Composable
private fun BannerVerticalLayout(
    modifier: Modifier,
    icon: ImageVector,
    iconModifier: Modifier,
    title: String,
    subtitle: String?,
    contentColor: Color
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = iconModifier
        )
        Spacer(modifier = Modifier.height(BannerCardDefaults.verticalSpacing))
        Text(
            text = title,
            style = BannerCardDefaults.titleTextStyle(),
            textAlign = TextAlign.Center,
            color = contentColor
        )
        subtitle?.takeIf { it.isNotBlank() }?.let {
            Spacer(modifier = Modifier.height(BannerCardDefaults.textSpacing))
            Text(
                text = it,
                style = BannerCardDefaults.subtitleTextStyle(),
                textAlign = TextAlign.Center,
                color = contentColor.copy(alpha = BannerCardDefaults.subtitleAlpha)
            )
        }
    }
}

/**
 * Layout horizontal interno del banner
 */
@Composable
private fun BannerHorizontalLayout(
    modifier: Modifier,
    icon: ImageVector,
    iconModifier: Modifier,
    title: String,
    subtitle: String?,
    contentColor: Color
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BannerCardDefaults.horizontalSpacing)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = iconModifier
        )
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = BannerCardDefaults.titleTextStyle(),
                textAlign = TextAlign.Center,
                color = contentColor
            )
            subtitle?.takeIf { it.isNotBlank() }?.let {
                Spacer(modifier = Modifier.height(BannerCardDefaults.textSpacing))
                Text(
                    text = it,
                    style = BannerCardDefaults.subtitleTextStyle(),
                    textAlign = TextAlign.Center,
                    color = contentColor.copy(alpha = BannerCardDefaults.subtitleAlpha)
                )
            }
        }
    }
}
