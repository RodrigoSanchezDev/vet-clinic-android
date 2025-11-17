package com.example.vet_clinic_android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vet_clinic_android.ui.screens.*
import com.example.vet_clinic_android.ui.viewmodels.VetClinicViewModel

/**
 * Sistema de navegación de la aplicación
 * Maneja todas las rutas y transiciones entre pantallas
 */
@Composable
fun VetClinicNavigation() {
    val navController = rememberNavController()
    val viewModel: VetClinicViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Intro.route
    ) {
        composable(Screen.Intro.route) {
            IntroScreen(navController = navController)
        }

        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Screen.RegisterConsulta.route) {
            RegisterConsultaScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.ListConsultas.route) {
            ListConsultasScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.ConsultasPendientes.route) {
            ConsultasPendientesScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.Estadisticas.route) {
            EstadisticasScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.AgendaVeterinarios.route) {
            AgendaVeterinariosScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.EstadisticasVeterinarios.route) {
            EstadisticasVeterinariosScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.BuscarVeterinario.route) {
            BuscarVeterinarioScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.ValidarProductos.route) {
            ValidarProductosScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.CrearPedido.route) {
            CrearPedidoScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.VerPromociones.route) {
            VerPromocionesScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.Reflection.route) {
            ReflectionScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.CombinarPedidos.route) {
            CombinarPedidosScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.CompararMedicamentos.route) {
            CompararMedicamentosScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.DesestructurarCliente.route) {
            DesestructurarClienteScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.DesestructurarPedido.route) {
            DesestructurarPedidoScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.ValidarDuplicadosClientes.route) {
            ValidarDuplicadosClientesScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.ValidarDuplicadosMedicamentos.route) {
            ValidarDuplicadosMedicamentosScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.ReporteCompleto.route) {
            ReporteCompletoScreen(navController = navController, viewModel = viewModel)
        }
    }
}
