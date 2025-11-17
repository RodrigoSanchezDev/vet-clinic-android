package com.example.vet_clinic_android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vet_clinic_android.model.ConsultaCompleta
import com.example.vet_clinic_android.ui.viewmodels.VetClinicViewModel
import com.example.vet_clinic_android.util.formatearMoneda

/**
 * Pantalla para listar todas las consultas registradas
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListConsultasScreen(
    navController: NavController,
    viewModel: VetClinicViewModel = viewModel()
) {
    val consultas = viewModel.consultaService.obtenerTodasConsultas()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todas las Consultas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
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
        if (consultas.isEmpty()) {
            EmptyStateView(
                icon = Icons.Default.EventNote,
                message = "No hay consultas registradas",
                modifier = Modifier.padding(paddingValues)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(consultas) { consulta ->
                    ConsultaCard(consulta = consulta)
                }
            }
        }
    }
}

/**
 * Pantalla para consultas pendientes
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultasPendientesScreen(
    navController: NavController,
    viewModel: VetClinicViewModel = viewModel()
) {
    val consultas = viewModel.consultaService.obtenerConsultasPendientes()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Consultas Pendientes") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        if (consultas.isEmpty()) {
            EmptyStateView(
                icon = Icons.Default.CheckCircle,
                message = "¡Genial! No hay consultas pendientes",
                modifier = Modifier.padding(paddingValues)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Warning,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "${consultas.size} consulta(s) pendiente(s) de programar",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                items(consultas) { consulta ->
                    ConsultaCard(consulta = consulta)
                }
            }
        }
    }
}

/**
 * Pantalla para consultas programadas
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultasProgramadasScreen(
    navController: NavController,
    viewModel: VetClinicViewModel = viewModel()
) {
    val consultas = viewModel.consultaService.obtenerConsultasProgramadas()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Consultas Programadas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        if (consultas.isEmpty()) {
            EmptyStateView(
                icon = Icons.Default.CalendarMonth,
                message = "No hay consultas programadas",
                modifier = Modifier.padding(paddingValues)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(consultas) { consulta ->
                    ConsultaCard(consulta = consulta)
                }
            }
        }
    }
}

/**
 * Card para mostrar información de una consulta
 */
@Composable
fun ConsultaCard(consulta: ConsultaCompleta) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Consulta #${consulta.consulta.idConsulta}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                StatusChip(estado = consulta.consulta.estado)
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Información de la mascota
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Pets,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${consulta.mascota.nombre} (${consulta.mascota.especie})",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Información del dueño
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = consulta.dueno.nombre,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Tipo de servicio
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.MedicalServices,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = consulta.consulta.tipoServicio,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Veterinario
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.LocalHospital,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Dr(a). ${consulta.veterinario.nombre}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Costo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Costo Total:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = formatearMoneda(consulta.consulta.costoConsulta),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

/**
 * Chip para mostrar el estado de la consulta
 */
@Composable
fun StatusChip(estado: String) {
    val (color, text) = when (estado.lowercase()) {
        "pendiente" -> MaterialTheme.colorScheme.tertiary to "Pendiente"
        "programada" -> MaterialTheme.colorScheme.secondary to "Programada"
        "completada" -> MaterialTheme.colorScheme.primary to "Completada"
        else -> MaterialTheme.colorScheme.outline to estado
    }

    Surface(
        color = color.copy(alpha = 0.2f),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Vista de estado vacío
 */
@Composable
fun EmptyStateView(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

