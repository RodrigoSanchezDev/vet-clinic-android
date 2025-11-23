package com.example.vet_clinic_android.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vet_clinic_android.ui.components.BannerCard
import com.example.vet_clinic_android.ui.viewmodels.VetClinicViewModel

/**
 * Pantalla de Resumen del Sistema
 * Muestra estadísticas generales y el estado actual de la clínica veterinaria
 *
 * Cumple con el requisito de mostrar:
 * - Número total de mascotas registradas
 * - Número total de consultas realizadas
 * - Nombre del último dueño registrado
 *
 * @param navController Controlador de navegación
 * @param viewModel ViewModel principal de la aplicación
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumenScreen(
    navController: NavController,
    viewModel: VetClinicViewModel = viewModel()
) {
    // Observar el estado de estadísticas
    val estadisticas by viewModel.estadisticas.collectAsState()

    // Actualizar estadísticas al entrar a la pantalla
    LaunchedEffect(Unit) {
        viewModel.actualizarEstadisticas()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Resumen del Sistema",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Banner de bienvenida
            item {
                BannerCard(
                    icon = Icons.Default.Dashboard,
                    title = "Panel de Estadísticas",
                    subtitle = "Información actualizada del sistema",
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            // Estadísticas principales (requeridas por la actividad)
            item {
                Text(
                    text = "Estadísticas Principales",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Card de mascotas
            item {
                EstadisticaCard(
                    icon = Icons.Default.Pets,
                    titulo = "Mascotas Registradas",
                    valor = estadisticas.totalMascotas.toString(),
                    descripcion = "Total de mascotas en el sistema",
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Card de consultas
            item {
                EstadisticaCard(
                    icon = Icons.Default.MedicalServices,
                    titulo = "Consultas Realizadas",
                    valor = estadisticas.totalConsultas.toString(),
                    descripcion = "Total de consultas registradas",
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            // Card de último dueño
            item {
                EstadisticaCard(
                    icon = Icons.Default.Person,
                    titulo = "Último Dueño Registrado",
                    valor = estadisticas.ultimoDuenoNombre,
                    descripcion = "Registro más reciente",
                    color = MaterialTheme.colorScheme.tertiary,
                    valorCustomStyle = MaterialTheme.typography.titleMedium
                )
            }

            // Estadísticas adicionales
            item {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = "Información Adicional",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Grid de estadísticas secundarias
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Dueños registrados
                    EstadisticaPequeñaCard(
                        icon = Icons.Default.People,
                        titulo = "Dueños",
                        valor = estadisticas.totalDuenos.toString(),
                        modifier = Modifier.weight(1f)
                    )

                    // Consultas pendientes
                    EstadisticaPequeñaCard(
                        icon = Icons.Default.PendingActions,
                        titulo = "Pendientes",
                        valor = estadisticas.consultasPendientes.toString(),
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Veterinarios
                    EstadisticaPequeñaCard(
                        icon = Icons.Default.LocalHospital,
                        titulo = "Veterinarios",
                        valor = estadisticas.totalVeterinarios.toString(),
                        modifier = Modifier.weight(1f)
                    )

                    // Spacer para mantener simetría
                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            // Botón de actualización con efecto hover
            item {
                val interactionSource = remember { MutableInteractionSource() }
                val isPressed by interactionSource.collectIsPressedAsState()

                val scale by animateFloatAsState(
                    targetValue = if (isPressed) 0.97f else 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    ),
                    label = "buttonScale"
                )

                val elevation by animateDpAsState(
                    targetValue = if (isPressed) 8.dp else 4.dp,
                    animationSpec = tween(durationMillis = 150),
                    label = "buttonElevation"
                )

                Button(
                    onClick = { viewModel.actualizarEstadisticas() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .scale(scale),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = elevation,
                        pressedElevation = 8.dp
                    ),
                    interactionSource = interactionSource
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("Actualizar Estadísticas")
                }
            }
        }
    }
}

/**
 * Composable para mostrar una estadística en formato de card grande
 */
@Composable
fun EstadisticaCard(
    icon: ImageVector,
    titulo: String,
    valor: String,
    descripcion: String,
    color: Color,
    valorCustomStyle: androidx.compose.ui.text.TextStyle? = null
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Icono
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = color,
                modifier = Modifier.size(64.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Contenido
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = valor,
                    style = valorCustomStyle ?: MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
                Text(
                    text = descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }
    }
}

/**
 * Composable para mostrar una estadística en formato compacto
 */
@Composable
fun EstadisticaPequeñaCard(
    icon: ImageVector,
    titulo: String,
    valor: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = valor,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = titulo,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

