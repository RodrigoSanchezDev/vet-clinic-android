package com.example.vet_clinic_android.app

import android.app.Application
import android.util.Log

/**
 * Clase Application personalizada para la Clínica Veterinaria
 *
 * Se encarga de la inicialización global de la aplicación:
 * - Configuración inicial de servicios
 * - Logging y debugging
 * - Gestión de recursos globales
 *
 * Esta clase se ejecuta antes que cualquier Activity.
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 * @website sanchezdev.com
 */
class VetClinicApplication : Application() {

    companion object {
        private const val TAG = "VetClinicApp"
        lateinit var instance: VetClinicApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // Inicialización de la aplicación
        initializeApp()
    }

    /**
     * Inicializa los componentes globales de la aplicación
     */
    private fun initializeApp() {
        Log.d(TAG, "🏥 Iniciando Sistema de Gestión de Clínica Veterinaria")
        Log.d(TAG, "📍 Ubicación: Santiago, Chile")
        Log.d(TAG, "👨‍💻 Desarrollador: Rodrigo Sánchez")
        Log.d(TAG, "🌐 Website: sanchezdev.com")

        // Aquí puedes inicializar:
        // - Base de datos local (Room)
        // - Repositorios
        // - WorkManager para tareas en background
        // - Configuración de logs
        // - Analytics
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.d(TAG, "🛑 Cerrando aplicación")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.w(TAG, "⚠️ Memoria baja - Liberando recursos")
    }
}

