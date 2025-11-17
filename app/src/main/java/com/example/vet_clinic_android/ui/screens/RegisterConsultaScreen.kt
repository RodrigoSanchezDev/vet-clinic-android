package com.example.vet_clinic_android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vet_clinic_android.ui.components.BannerCard
import com.example.vet_clinic_android.ui.viewmodels.VetClinicViewModel

/**
 * Pantalla para registrar una nueva consulta
 * Incluye formularios para mascota, dueño y consulta
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterConsultaScreen(
    navController: NavController,
    viewModel: VetClinicViewModel = viewModel()
) {
    var currentStep by remember { mutableStateOf(1) }
    val scrollState = rememberScrollState()
    val consultaCreada by viewModel.consultaCreada.collectAsState()
    val mascota by viewModel.currentMascota.collectAsState()
    val dueno by viewModel.currentDueno.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (currentStep < 4) "Nueva Consulta - Paso $currentStep/3" else "Resumen de Consulta")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (currentStep > 1 && currentStep < 4) {
                            currentStep--
                        } else {
                            viewModel.resetRegistro()
                            navController.popBackStack()
                        }
                    }) {
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
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            when (currentStep) {
                1 -> MascotaFormStep(
                    viewModel = viewModel,
                    onNext = { currentStep = 2 }
                )
                2 -> DuenoFormStep(
                    viewModel = viewModel,
                    onNext = { currentStep = 3 }
                )
                3 -> ConsultaFormStep(
                    viewModel = viewModel,
                    onComplete = { currentStep = 4 }
                )
                4 -> ResumenConsultaStep(
                    consulta = consultaCreada,
                    mascota = mascota,
                    dueno = dueno,
                    onFinish = {
                        viewModel.resetRegistro()
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

/**
 * Paso 1: Formulario de registro de mascota
 */
@Composable
fun MascotaFormStep(
    viewModel: VetClinicViewModel,
    onNext: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var especie by remember { mutableStateOf("Perro") }
    var edad by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var raza by remember { mutableStateOf("Mestizo") }
    var expandedEspecie by remember { mutableStateOf(false) }

    // Estados de validación
    var nombreError by remember { mutableStateOf<String?>(null) }
    var edadError by remember { mutableStateOf<String?>(null) }
    var pesoError by remember { mutableStateOf<String?>(null) }

    val especies = listOf("Perro", "Gato", "Conejo", "Ave", "Otro")

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Datos de la Mascota",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        nombreError = if (it.isNotBlank()) {
                            val validation = com.example.vet_clinic_android.util.validarNombre(it)
                            if (!validation.isValid) validation.message else null
                        } else null
                    },
                    label = { Text("Nombre de la mascota *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = nombreError != null,
                    supportingText = nombreError?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
                    placeholder = { Text("Ej: Firulais, Chuño, Ñoño") }
                )

                ExposedDropdownMenuBox(
                    expanded = expandedEspecie,
                    onExpandedChange = { expandedEspecie = it }
                ) {
                    OutlinedTextField(
                        value = especie,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Especie *") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEspecie) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedEspecie,
                        onDismissRequest = { expandedEspecie = false }
                    ) {
                        especies.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    especie = item
                                    expandedEspecie = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = raza,
                    onValueChange = { raza = it },
                    label = { Text("Raza") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = edad,
                        onValueChange = { if (it.all { char -> char.isDigit() }) edad = it },
                        label = { Text("Edad (años) *") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = peso,
                        onValueChange = {
                            if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                                peso = it
                            }
                        },
                        label = { Text("Peso (kg) *") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }
            }
        }

        Button(
            onClick = {
                if (nombre.isNotBlank() && edad.isNotBlank() && peso.isNotBlank()) {
                    viewModel.registrarMascota(
                        nombre = nombre,
                        especie = especie,
                        edad = edad.toIntOrNull() ?: 0,
                        peso = peso.toDoubleOrNull() ?: 0.0,
                        raza = raza
                    )
                    onNext()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = nombre.isNotBlank() && edad.isNotBlank() && peso.isNotBlank()
        ) {
            Text("Siguiente")
        }
    }
}

/**
 * Paso 2: Formulario de registro de dueño
 */
@Composable
fun DuenoFormStep(
    viewModel: VetClinicViewModel,
    onNext: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Datos del Dueño",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre completo *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text("Teléfono *") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("+56912345678") }
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email *") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("ejemplo@email.com") }
                )

                OutlinedTextField(
                    value = rut,
                    onValueChange = { rut = it },
                    label = { Text("RUT") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("12345678-9") }
                )

                OutlinedTextField(
                    value = direccion,
                    onValueChange = { direccion = it },
                    label = { Text("Dirección") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2
                )
            }
        }

        Button(
            onClick = {
                if (nombre.isNotBlank() && telefono.isNotBlank() && email.isNotBlank()) {
                    viewModel.registrarDueno(
                        nombre = nombre,
                        telefono = telefono,
                        email = email,
                        direccion = direccion,
                        rut = rut
                    )
                    onNext()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = nombre.isNotBlank() && telefono.isNotBlank() && email.isNotBlank()
        ) {
            Text("Siguiente")
        }
    }
}

/**
 * Paso 3: Formulario de datos de la consulta
 */
@Composable
fun ConsultaFormStep(
    viewModel: VetClinicViewModel,
    onComplete: () -> Unit
) {
    var descripcion by remember { mutableStateOf("") }
    var tipoServicio by remember { mutableStateOf("Consulta General") }
    var tiempoEstimado by remember { mutableStateOf("30") }
    var numeroMascotas by remember { mutableStateOf("1") }
    var expandedTipo by remember { mutableStateOf(false) }

    val tiposServicio = listOf(
        "Consulta General",
        "Vacunación",
        "Cirugía Menor",
        "Cirugía Mayor",
        "Emergencia",
        "Control",
        "Desparasitación"
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Datos de la Consulta",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = expandedTipo,
                    onExpandedChange = { expandedTipo = it }
                ) {
                    OutlinedTextField(
                        value = tipoServicio,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Tipo de Servicio *") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTipo) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedTipo,
                        onDismissRequest = { expandedTipo = false }
                    ) {
                        tiposServicio.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    tipoServicio = item
                                    expandedTipo = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción / Motivo *") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    placeholder = { Text("Describa el motivo de la consulta...") }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = tiempoEstimado,
                        onValueChange = { if (it.all { char -> char.isDigit() }) tiempoEstimado = it },
                        label = { Text("Tiempo (min) *") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = numeroMascotas,
                        onValueChange = { if (it.all { char -> char.isDigit() }) numeroMascotas = it },
                        label = { Text("Nº Mascotas *") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                if ((numeroMascotas.toIntOrNull() ?: 1) > 1) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Text(
                            text = "✅ Se aplicará descuento del 15% por múltiples mascotas",
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                if (descripcion.isNotBlank() && tiempoEstimado.isNotBlank()) {
                    viewModel.crearConsulta(
                        descripcion = descripcion,
                        tipoServicio = tipoServicio,
                        tiempoEstimado = tiempoEstimado.toIntOrNull() ?: 30,
                        numeroMascotas = numeroMascotas.toIntOrNull() ?: 1
                    )
                    onComplete()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = descripcion.isNotBlank() && tiempoEstimado.isNotBlank()
        ) {
            Text("Crear Consulta")
        }
    }
}

/**
 * Paso 4: Resumen de la consulta creada
 */
@Composable
fun ResumenConsultaStep(
    consulta: com.example.vet_clinic_android.model.Consulta?,
    mascota: com.example.vet_clinic_android.model.Mascota?,
    dueno: com.example.vet_clinic_android.model.Dueno?,
    onFinish: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header con ícono de éxito
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            BannerCard(
                modifier = Modifier.fillMaxWidth(),
                icon = Icons.Default.CheckCircle,
                title = "¡Consulta Creada Exitosamente!",
                subtitle = consulta?.let { "ID de Consulta: #${it.idConsulta}" } ?: "",
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        }

        // Resumen de la consulta
        consulta?.let { cons ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Detalles de la Consulta",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Divider()

                    InfoRow("Tipo de Servicio", cons.tipoServicio)
                    InfoRow("Descripción", cons.descripcion)
                    InfoRow("Estado", cons.estado)
                    InfoRow("Costo", com.example.vet_clinic_android.util.formatearMoneda(cons.costoConsulta))
                }
            }
        }

        // Datos de la mascota
        mascota?.let { masc ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Mascota",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Divider()

                    InfoRow("Nombre", masc.nombre)
                    InfoRow("Especie", masc.especie)
                    InfoRow("Edad", "${masc.edad} año(s)")
                    InfoRow("Peso", "${masc.peso} kg")
                    InfoRow("Raza", masc.raza)
                }
            }
        }

        // Datos del dueño
        dueno?.let { own ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Dueño",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Divider()

                    InfoRow("Nombre", own.nombreDueno)
                    InfoRow("Teléfono", own.telefono)
                    InfoRow("Email", own.email)
                    if (own.direccion.isNotBlank()) {
                        InfoRow("Dirección", own.direccion)
                    }
                }
            }
        }

        // Botón para finalizar
        Button(
            onClick = onFinish,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text("Finalizar")
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1.5f)
        )
    }
}
