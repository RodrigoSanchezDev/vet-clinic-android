package com.example.vet_clinic_android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

/**
 * 🎨 VetClinicScaffold - Scaffold reutilizable con Bottom Nav
 *
 * Wrapper profesional que proporciona estructura consistente a todas las pantallas:
 * - Top bar opcional (personalizable por pantalla)
 * - Bottom navigation bar (automático en todas las pantallas)
 * - Content area con padding correcto
 *
 * @param navController Controlador de navegación
 * @param topBar Composable opcional para el top bar
 * @param showBottomBar Si se debe mostrar la barra inferior (default: true)
 * @param content Contenido de la pantalla
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VetClinicScaffold(
    navController: NavController,
    topBar: @Composable () -> Unit = {},
    showBottomBar: Boolean = true,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = topBar,
        bottomBar = {
            if (showBottomBar) {
                VetClinicBottomNavigationBar(navController = navController)
            }
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}
