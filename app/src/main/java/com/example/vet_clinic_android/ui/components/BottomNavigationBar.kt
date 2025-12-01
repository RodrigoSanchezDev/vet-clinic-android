package com.example.vet_clinic_android.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vet_clinic_android.ui.navigation.Screen

/**
 * 📱 BottomNavigationBar - Barra de navegación inferior profesional
 *
 * Componente reutilizable que se muestra en todas las pantallas principales.
 * Proporciona acceso rápido a las 4 funcionalidades más importantes:
 * - Home (Inicio)
 * - Consultas (Lista)
 * - Registrar (Nueva consulta)
 * - Agenda (Veterinarios)
 *
 * @param navController Controlador de navegación
 */
@Composable
fun VetClinicBottomNavigationBar(
    navController: NavController
) {
    // Observar la ruta actual para marcar el item seleccionado
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Ocultar barra si estamos en Intro (si deseas que esté en absolutamente todas, comenta este if)
    if (currentRoute == Screen.Intro.route) return

    // Definir los items del bottom nav
    val bottomNavItems = listOf(
        BottomNavItem(
            route = Screen.Home.route,
            icon = Icons.Default.Home,
            label = "Inicio"
        ),
        BottomNavItem(
            route = Screen.ListConsultas.route,
            icon = Icons.Default.List,
            label = "Consultas"
        ),
        BottomNavItem(
            route = Screen.RegisterConsulta.route,
            icon = Icons.Default.Add,
            label = "Registrar"
        ),
        BottomNavItem(
            route = Screen.AgendaVeterinarios.route,
            icon = Icons.Default.Schedule,
            label = "Agenda"
        )
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        bottomNavItems.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                selected = isSelected,
                onClick = {
                    // No navegar si ya estamos en la pantalla actual
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            // Estrategia diferente según la pantalla destino
                            when (item.route) {
                                Screen.Home.route -> {
                                    // Home: limpiar stack hasta Intro (no incluirlo)
                                    popUpTo(Screen.Intro.route) {
                                        inclusive = false
                                        saveState = true
                                    }
                                }
                                else -> {
                                    // Otras pantallas: mantener Home en el stack
                                    popUpTo(Screen.Home.route) {
                                        inclusive = false
                                        saveState = true
                                    }
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

/**
 * Data class para los items del bottom navigation
 */
private data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)
