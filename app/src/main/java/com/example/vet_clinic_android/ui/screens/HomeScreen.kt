package com.example.vet_clinic_android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vet_clinic_android.ui.components.BannerCard
import com.example.vet_clinic_android.ui.navigation.Screen

/**
 * Pantalla principal con menú de opciones
 * Mantiene toda la funcionalidad del sistema original
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Clínica Veterinaria",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Santiago, Chile",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
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
            // Mensaje de bienvenida
            BannerCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                icon = Icons.Default.Pets,
                title = "Sistema de Gestión Veterinaria",
                subtitle = "Desarrollado por Rodrigo Sánchez"
            )

            // Grid de opciones del menú
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(menuOptions) { option ->
                    MenuOptionCard(
                        option = option,
                        onClick = {
                            navController.navigate(option.route)
                        }
                    )
                }
            }
        }
    }
}

/**
 * Card individual para cada opción del menú
 */
@Composable
fun MenuOptionCard(
    option: MenuOption,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = option.icon,
                contentDescription = option.title,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = option.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Data class para las opciones del menú
 */
data class MenuOption(
    val title: String,
    val icon: ImageVector,
    val route: String
)

/**
 * Lista de todas las opciones del menú
 * Refleja el menú completo del sistema original de consola
 */
val menuOptions = listOf(
    MenuOption(
        title = "Registrar Nueva Consulta",
        icon = Icons.Default.Add,
        route = Screen.RegisterConsulta.route
    ),
    MenuOption(
        title = "Informe de Consultas",
        icon = Icons.Default.List,
        route = Screen.ListConsultas.route
    ),
    MenuOption(
        title = "Consultas Pendientes",
        icon = Icons.Default.PendingActions,
        route = Screen.ConsultasPendientes.route
    ),
    MenuOption(
        title = "Estadísticas Sistema",
        icon = Icons.Default.BarChart,
        route = Screen.Estadisticas.route
    ),
    MenuOption(
        title = "Agenda Veterinarios",
        icon = Icons.Default.Schedule,
        route = Screen.AgendaVeterinarios.route
    ),
    MenuOption(
        title = "Estadísticas Veterinarios",
        icon = Icons.Default.Science,
        route = Screen.EstadisticasVeterinarios.route
    ),
    MenuOption(
        title = "Buscar Veterinario",
        icon = Icons.Default.Search,
        route = Screen.BuscarVeterinario.route
    ),
    MenuOption(
        title = "Validar Productos (Ranges)",
        icon = Icons.Default.Inventory,
        route = Screen.ValidarProductos.route
    ),
    MenuOption(
        title = "Crear Pedido Medicamentos",
        icon = Icons.Default.ShoppingCart,
        route = Screen.CrearPedido.route
    ),
    MenuOption(
        title = "Ver Promociones",
        icon = Icons.Default.LocalOffer,
        route = Screen.VerPromociones.route
    ),
    MenuOption(
        title = "Analizar con Reflection",
        icon = Icons.Default.Code,
        route = Screen.Reflection.route
    ),
    MenuOption(
        title = "Combinar Pedidos (+ Operator)",
        icon = Icons.Default.Add,
        route = Screen.CombinarPedidos.route
    ),
    MenuOption(
        title = "Comparar Medicamentos (==)",
        icon = Icons.Default.CompareArrows,
        route = Screen.CompararMedicamentos.route
    ),
    MenuOption(
        title = "Desestructurar Cliente",
        icon = Icons.Default.PersonRemove,
        route = Screen.DesestructurarCliente.route
    ),
    MenuOption(
        title = "Desestructurar Pedido",
        icon = Icons.Default.ShoppingBag,
        route = Screen.DesestructurarPedido.route
    ),
    MenuOption(
        title = "Validar Duplicados Clientes",
        icon = Icons.Default.People,
        route = Screen.ValidarDuplicadosClientes.route
    ),
    MenuOption(
        title = "Validar Duplicados Medicamentos",
        icon = Icons.Default.MedicalServices,
        route = Screen.ValidarDuplicadosMedicamentos.route
    ),
    MenuOption(
        title = "Reporte Completo Integrado",
        icon = Icons.Default.Assessment,
        route = Screen.ReporteCompleto.route
    )
)
