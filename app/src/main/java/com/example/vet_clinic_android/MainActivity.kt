package com.example.vet_clinic_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.vet_clinic_android.ui.navigation.VetClinicNavigation
import com.example.vet_clinic_android.ui.theme.VetClinicTheme

/**
 * MainActivity - Punto de entrada de la aplicación
 * Sistema de Gestión de Clínica Veterinaria
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 * @website sanchezdev.com
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VetClinicTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VetClinicNavigation()
                }
            }
        }
    }
}

