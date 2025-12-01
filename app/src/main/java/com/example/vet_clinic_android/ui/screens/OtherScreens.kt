package com.example.vet_clinic_android.ui.screens

import androidx.compose.foundation.layout.*
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
import com.example.vet_clinic_android.ui.viewmodels.VetClinicViewModel

/**
 * Pantalla de gestión de medicamentos
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionMedicamentosScreen(
    navController: NavController,
    @Suppress("UNUSED_PARAMETER")
    viewModel: VetClinicViewModel = viewModel()
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Medicamentos") },
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
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.MedicalServices,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Gestión de Medicamentos",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Funcionalidades de medicamentos y promociones",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text(
                text = "Funcionalidades Disponibles:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            InfoCard(
                title = "Medicamentos con Promoción",
                description = "Sistema de anotaciones @Promocionable para identificar medicamentos con descuento",
                icon = Icons.Default.LocalOffer
            )

            InfoCard(
                title = "Crear Pedido",
                description = "Crear pedidos de medicamentos con sobrecarga de operadores",
                icon = Icons.Default.ShoppingCart
            )

            InfoCard(
                title = "Combinar Pedidos",
                description = "Uso del operador + para combinar múltiples pedidos",
                icon = Icons.Default.Add
            )

            InfoCard(
                title = "Comparar Medicamentos",
                description = "Uso del operador == para comparar medicamentos",
                icon = Icons.Default.Compare
            )
        }
    }
}

/**
 * Pantalla de validación de productos (RANGES)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ValidarProductosScreen(
    navController: NavController,
    viewModel: VetClinicViewModel = viewModel()
) {
    var cantidad by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Validar Productos") },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        Icons.Default.Inventory,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Validación con RANGES",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Valida la cantidad de productos según rangos definidos",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Card {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Rangos de Validación:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    RangeInfoRow("0-10", "Stock Bajo", MaterialTheme.colorScheme.error)
                    RangeInfoRow("11-50", "Stock Medio", MaterialTheme.colorScheme.tertiary)
                    RangeInfoRow("51-100", "Stock Bueno", MaterialTheme.colorScheme.secondary)
                    RangeInfoRow("101+", "Stock Excelente", MaterialTheme.colorScheme.primary)
                }
            }

            OutlinedTextField(
                value = cantidad,
                onValueChange = { if (it.all { char -> char.isDigit() }) cantidad = it },
                label = { Text("Cantidad de productos") },
                leadingIcon = { Icon(Icons.Default.Inventory2, null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Button(
                onClick = {
                    val cant = cantidad.toIntOrNull() ?: 0
                    resultado = viewModel.promocionService.validarCantidadProductos(cant)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = cantidad.isNotBlank()
            ) {
                Icon(Icons.Default.Check, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Validar")
            }

            resultado?.let { res ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Resultado:",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = res,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

/**
 * Pantalla de Reflection
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReflectionScreen(
    navController: NavController,
    viewModel: VetClinicViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    var claseSeleccionada by remember { mutableStateOf("Mascota") }
    var expandedClase by remember { mutableStateOf(false) }
    var infoReflection by remember { mutableStateOf<String?>(null) }

    val clases = listOf("Mascota", "Consulta", "Veterinario", "Dueno")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Análisis con Reflection") },
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
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        Icons.Default.Code,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Reflection API",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Analiza clases en tiempo de ejecución",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            ExposedDropdownMenuBox(
                expanded = expandedClase,
                onExpandedChange = { expandedClase = it }
            ) {
                OutlinedTextField(
                    value = claseSeleccionada,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Seleccionar clase") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedClase) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandedClase,
                    onDismissRequest = { expandedClase = false }
                ) {
                    clases.forEach { clase ->
                        DropdownMenuItem(
                            text = { Text(clase) },
                            onClick = {
                                claseSeleccionada = clase
                                expandedClase = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    infoReflection = viewModel.reflectionService.analizarClase(claseSeleccionada)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Analytics, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Analizar Clase")
            }

            infoReflection?.let { info ->
                Card {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Análisis de $claseSeleccionada:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = info,
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                        )
                    }
                }
            }
        }
    }
}

/**
 * Pantalla de reporte completo integrado
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReporteCompletoScreen(
    navController: NavController,
    @Suppress("UNUSED_PARAMETER")
    viewModel: VetClinicViewModel = viewModel()
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reporte Completo") },
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
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Assessment,
                        contentDescription = null,
                        modifier = Modifier.size(56.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Reporte Completo Integrado",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Text(
                        text = "Todas las funcionalidades del sistema",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text(
                text = "Funcionalidades Implementadas:",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            FeatureCard(
                "✅ POO (Programación Orientada a Objetos)",
                "Clases, Herencia, Polimorfismo, Encapsulamiento",
                Icons.Default.AccountTree
            )

            FeatureCard(
                "✅ Colecciones Avanzadas",
                "Filter, Map, Find, GroupBy, Sorted",
                Icons.Default.FilterList
            )

            FeatureCard(
                "✅ Funciones de Alcance",
                "Let, Apply, Also, Run, With",
                Icons.Default.Functions
            )

            FeatureCard(
                "✅ Ranges y Validaciones",
                "Validación de cantidades con rangos",
                Icons.Default.LinearScale
            )

            FeatureCard(
                "✅ Operadores Sobrecargados",
                "Operator +, ==, compareTo",
                Icons.Default.Calculate
            )

            FeatureCard(
                "✅ Destructuring",
                "Desestructuración de objetos",
                Icons.Default.DynamicForm
            )

            FeatureCard(
                "✅ Anotaciones Personalizadas",
                "@Promocionable para medicamentos",
                Icons.Default.LocalOffer
            )

            FeatureCard(
                "✅ Reflection API",
                "Análisis de clases en runtime",
                Icons.Default.Code
            )

            FeatureCard(
                "✅ Manejo de Excepciones",
                "Try-catch, custom exceptions",
                Icons.Default.ErrorOutline
            )

            FeatureCard(
                "✅ Jetpack Compose",
                "UI moderna y declarativa para Android",
                Icons.Default.Smartphone
            )
        }
    }
}

/**
 * Componentes auxiliares
 */
@Composable
fun InfoCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun RangeInfoRow(
    range: String,
    label: String,
    color: androidx.compose.ui.graphics.Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = range,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        Surface(
            color = color.copy(alpha = 0.2f),
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = label,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                style = MaterialTheme.typography.labelMedium,
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun FeatureCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

