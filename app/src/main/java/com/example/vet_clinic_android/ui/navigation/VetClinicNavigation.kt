package com.example.vet_clinic_android.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vet_clinic_android.ui.components.VetClinicBottomNavigationBar
import com.example.vet_clinic_android.ui.screens.*
import com.example.vet_clinic_android.ui.theme.ScreenTransitions
import com.example.vet_clinic_android.ui.viewmodels.VetClinicViewModel

/**
 * 🎬 Sistema de navegación de la aplicación
 * Maneja todas las rutas y transiciones entre pantallas con animaciones profesionales
 * Incluye Bottom Navigation Bar en todas las pantallas principales
 */
@Composable
fun VetClinicNavigation() {
    val navController = rememberNavController()
    val viewModel: VetClinicViewModel = viewModel()

    Scaffold(
        bottomBar = {
            VetClinicBottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Intro.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            // 🎬 Pantalla de introducción (sin bottom nav)
            composable(Screen.Intro.route) {
                IntroScreen(navController = navController)
            }

            // 🎬 Pantalla principal con transiciones
            composable(
                route = Screen.Home.route,
                enterTransition = { ScreenTransitions.enterTransition() },
                exitTransition = { ScreenTransitions.exitTransition() },
                popEnterTransition = { ScreenTransitions.popEnterTransition() },
                popExitTransition = { ScreenTransitions.popExitTransition() }
            ) {
                HomeScreen(navController = navController)
            }

            // 🎬 Pantalla de resumen con transiciones
            composable(
                route = Screen.Resumen.route,
                enterTransition = { ScreenTransitions.enterTransition() },
                exitTransition = { ScreenTransitions.exitTransition() },
                popEnterTransition = { ScreenTransitions.popEnterTransition() },
                popExitTransition = { ScreenTransitions.popExitTransition() }
            ) {
                ResumenScreen(navController = navController, viewModel = viewModel)
            }

            // 🎬 Registro de consulta con transiciones
            composable(
                route = Screen.RegisterConsulta.route,
                enterTransition = { ScreenTransitions.enterTransition() },
                exitTransition = { ScreenTransitions.exitTransition() },
                popEnterTransition = { ScreenTransitions.popEnterTransition() },
                popExitTransition = { ScreenTransitions.popExitTransition() }
            ) {
                RegisterConsultaScreen(navController = navController, viewModel = viewModel)
            }

            // 🎬 Lista de consultas con transiciones
            composable(
                route = Screen.ListConsultas.route,
                enterTransition = { ScreenTransitions.enterTransition() },
                exitTransition = { ScreenTransitions.exitTransition() },
                popEnterTransition = { ScreenTransitions.popEnterTransition() },
                popExitTransition = { ScreenTransitions.popExitTransition() }
            ) {
                ListConsultasScreen(navController = navController, viewModel = viewModel)
            }

            // 🎬 Consultas pendientes con transiciones
            composable(
                route = Screen.ConsultasPendientes.route,
                enterTransition = { ScreenTransitions.enterTransition() },
                exitTransition = { ScreenTransitions.exitTransition() },
                popEnterTransition = { ScreenTransitions.popEnterTransition() },
                popExitTransition = { ScreenTransitions.popExitTransition() }
            ) {
                ConsultasPendientesScreen(navController = navController, viewModel = viewModel)
            }

            // 🎬 Estadísticas con transiciones
            composable(
                route = Screen.Estadisticas.route,
                enterTransition = { ScreenTransitions.enterTransition() },
                exitTransition = { ScreenTransitions.exitTransition() },
                popEnterTransition = { ScreenTransitions.popEnterTransition() },
                popExitTransition = { ScreenTransitions.popExitTransition() }
            ) {
                EstadisticasScreen(navController = navController, viewModel = viewModel)
            }

            // 🎬 Agenda de veterinarios con transiciones
            composable(
                route = Screen.AgendaVeterinarios.route,
                enterTransition = { ScreenTransitions.enterTransition() },
                exitTransition = { ScreenTransitions.exitTransition() },
                popEnterTransition = { ScreenTransitions.popEnterTransition() },
                popExitTransition = { ScreenTransitions.popExitTransition() }
            ) {
                AgendaVeterinariosScreen(navController = navController, viewModel = viewModel)
            }

            // 🎬 Estadísticas de veterinarios con transiciones
            composable(
                route = Screen.EstadisticasVeterinarios.route,
                enterTransition = { ScreenTransitions.enterTransition() },
                exitTransition = { ScreenTransitions.exitTransition() },
                popEnterTransition = { ScreenTransitions.popEnterTransition() },
                popExitTransition = { ScreenTransitions.popExitTransition() }
            ) {
                EstadisticasVeterinariosScreen(navController = navController, viewModel = viewModel)
            }

            // 🎬 Buscar veterinario con transiciones
            composable(
                route = Screen.BuscarVeterinario.route,
                enterTransition = { ScreenTransitions.enterTransition() },
                exitTransition = { ScreenTransitions.exitTransition() },
                popEnterTransition = { ScreenTransitions.popEnterTransition() },
                popExitTransition = { ScreenTransitions.popExitTransition() }
            ) {
                BuscarVeterinarioScreen(navController = navController, viewModel = viewModel)
            }

            // 🎬 Validar productos con transiciones
            composable(
                route = Screen.ValidarProductos.route,
                enterTransition = { ScreenTransitions.enterTransition() },
                exitTransition = { ScreenTransitions.exitTransition() },
                popEnterTransition = { ScreenTransitions.popEnterTransition() },
                popExitTransition = { ScreenTransitions.popExitTransition() }
            ) {
                ValidarProductosScreen(navController = navController, viewModel = viewModel)
            }

            // 🎬 Crear pedido con transiciones
            composable(
                route = Screen.CrearPedido.route,
                enterTransition = { ScreenTransitions.enterTransition() },
                exitTransition = { ScreenTransitions.exitTransition() },
                popEnterTransition = { ScreenTransitions.popEnterTransition() },
                popExitTransition = { ScreenTransitions.popExitTransition() }
            ) {
                CrearPedidoScreen(navController = navController, viewModel = viewModel)
            }

            // 🎬 Ver promociones con transiciones
            composable(
                route = Screen.VerPromociones.route,
                enterTransition = { ScreenTransitions.enterTransition() },
                exitTransition = { ScreenTransitions.exitTransition() },
                popEnterTransition = { ScreenTransitions.popEnterTransition() },
                popExitTransition = { ScreenTransitions.popExitTransition() }
            ) {
                VerPromocionesScreen(navController = navController, viewModel = viewModel)
            }

            // 🎬 Reflection con transiciones
            composable(
                route = Screen.Reflection.route,
                enterTransition = { ScreenTransitions.enterTransition() },
                exitTransition = { ScreenTransitions.exitTransition() },
                popEnterTransition = { ScreenTransitions.popEnterTransition() },
                popExitTransition = { ScreenTransitions.popExitTransition() }
            ) {
                ReflectionScreen(navController = navController, viewModel = viewModel)
            }

            // 🎬 Combinar pedidos con transiciones
            composable(
                route = Screen.CombinarPedidos.route,
                enterTransition = { ScreenTransitions.enterTransition() },
                exitTransition = { ScreenTransitions.exitTransition() },
                popEnterTransition = { ScreenTransitions.popEnterTransition() },
                popExitTransition = { ScreenTransitions.popExitTransition() }
            ) {
                CombinarPedidosScreen(navController = navController, viewModel = viewModel)
            }

            // 🎬 Comparar medicamentos con transiciones
            composable(
                route = Screen.CompararMedicamentos.route,
                enterTransition = { ScreenTransitions.enterTransition() },
                exitTransition = { ScreenTransitions.exitTransition() },
                popEnterTransition = { ScreenTransitions.popEnterTransition() },
                popExitTransition = { ScreenTransitions.popExitTransition() }
            ) {
                CompararMedicamentosScreen(navController = navController, viewModel = viewModel)
            }

            // 🎬 Desestructurar cliente con transiciones
            composable(
                route = Screen.DesestructurarCliente.route,
                enterTransition = { ScreenTransitions.enterTransition() },
                exitTransition = { ScreenTransitions.exitTransition() },
                popEnterTransition = { ScreenTransitions.popEnterTransition() },
                popExitTransition = { ScreenTransitions.popExitTransition() }
            ) {
                DesestructurarClienteScreen(navController = navController, viewModel = viewModel)
            }

            // 🎬 Desestructurar pedido con transiciones
            composable(
                route = Screen.DesestructurarPedido.route,
                enterTransition = { ScreenTransitions.enterTransition() },
                exitTransition = { ScreenTransitions.exitTransition() },
                popEnterTransition = { ScreenTransitions.popEnterTransition() },
                popExitTransition = { ScreenTransitions.popExitTransition() }
            ) {
                DesestructurarPedidoScreen(navController = navController, viewModel = viewModel)
            }

            // 🎬 Validar duplicados clientes con transiciones
            composable(
                route = Screen.ValidarDuplicadosClientes.route,
                enterTransition = { ScreenTransitions.enterTransition() },
                exitTransition = { ScreenTransitions.exitTransition() },
                popEnterTransition = { ScreenTransitions.popEnterTransition() },
                popExitTransition = { ScreenTransitions.popExitTransition() }
            ) {
                ValidarDuplicadosClientesScreen(navController = navController, viewModel = viewModel)
            }

            // 🎬 Validar duplicados medicamentos con transiciones
            composable(
                route = Screen.ValidarDuplicadosMedicamentos.route,
                enterTransition = { ScreenTransitions.enterTransition() },
                exitTransition = { ScreenTransitions.exitTransition() },
                popEnterTransition = { ScreenTransitions.popEnterTransition() },
                popExitTransition = { ScreenTransitions.popExitTransition() }
            ) {
                ValidarDuplicadosMedicamentosScreen(navController = navController, viewModel = viewModel)
            }

            // 🎬 Reporte completo con transiciones
            composable(
                route = Screen.ReporteCompleto.route,
                enterTransition = { ScreenTransitions.enterTransition() },
                exitTransition = { ScreenTransitions.exitTransition() },
                popEnterTransition = { ScreenTransitions.popEnterTransition() },
                popExitTransition = { ScreenTransitions.popExitTransition() }
            ) {
                ReporteCompletoScreen(navController = navController, viewModel = viewModel)
            }
        }
    }
}
