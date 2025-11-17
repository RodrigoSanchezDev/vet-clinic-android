package com.example.vet_clinic_android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.vet_clinic_android.model.Veterinario
import com.example.vet_clinic_android.ui.viewmodels.VetClinicViewModel

/**
 * Pantalla de agenda de veterinarios
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaVeterinariosScreen(
    navController: NavController,
    viewModel: VetClinicViewModel = viewModel()
) {
    val veterinarios = viewModel.veterinarioService.obtenerTodosVeterinarios()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agenda de Veterinarios") },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(veterinarios) { veterinario ->
                VeterinarioCard(veterinario = veterinario)
            }
        }
    }
}

/**
 * Pantalla de búsqueda de veterinario por especialidad
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuscarVeterinarioScreen(
    navController: NavController,
    viewModel: VetClinicViewModel = viewModel()
) {
    var especialidadBuscada by remember { mutableStateOf("") }
    var expandedEspecialidad by remember { mutableStateOf(false) }

    val especialidades = listOf(
        "Medicina General",
        "Cirugía",
        "Dermatología",
        "Cardiología",
        "Oftalmología"
    )

    val veterinarios = if (especialidadBuscada.isNotBlank()) {
        viewModel.veterinarioService.buscarPorEspecialidad(especialidadBuscada)
    } else {
        emptyList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buscar Veterinario") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Buscar por Especialidad",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    ExposedDropdownMenuBox(
                        expanded = expandedEspecialidad,
                        onExpandedChange = { expandedEspecialidad = it }
                    ) {
                        OutlinedTextField(
                            value = especialidadBuscada,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Seleccione especialidad") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expandedEspecialidad
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expandedEspecialidad,
                            onDismissRequest = { expandedEspecialidad = false }
                        ) {
                            especialidades.forEach { especialidad ->
                                DropdownMenuItem(
                                    text = { Text(especialidad) },
                                    onClick = {
                                        especialidadBuscada = especialidad
                                        expandedEspecialidad = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (veterinarios.isNotEmpty()) {
                Text(
                    text = "Resultados (${veterinarios.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(veterinarios) { veterinario ->
                        VeterinarioCard(veterinario = veterinario)
                    }
                }
            } else if (especialidadBuscada.isNotBlank()) {
                EmptyStateView(
                    icon = Icons.Default.SearchOff,
                    message = "No se encontraron veterinarios con esa especialidad"
                )
            } else {
                EmptyStateView(
                    icon = Icons.Default.Search,
                    message = "Seleccione una especialidad para buscar"
                )
            }
        }
    }
}

/**
 * Card de veterinario
 */
@Composable
fun VeterinarioCard(veterinario: Veterinario) {
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.LocalHospital,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Dr(a). ${veterinario.nombre}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = veterinario.especialidad,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // Estado de disponibilidad
                Surface(
                    color = if (veterinario.disponible) {
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    } else {
                        MaterialTheme.colorScheme.error.copy(alpha = 0.2f)
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            if (veterinario.disponible) Icons.Default.CheckCircle else Icons.Default.Cancel,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = if (veterinario.disponible) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.error
                            }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (veterinario.disponible) "Disponible" else "Ocupado",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (veterinario.disponible) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.error
                            }
                        )
                    }
                }
            }

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            // Información de contacto
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Phone,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = veterinario.telefono,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Email,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = veterinario.email,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Badge,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Licencia: ${veterinario.licenciaProfesional}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

