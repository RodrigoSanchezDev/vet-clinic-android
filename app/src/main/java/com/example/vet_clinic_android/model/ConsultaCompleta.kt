package com.example.vet_clinic_android.model

/**
 * Representa una consulta completa con información del dueño, mascota y veterinario
 * Usa composición de objetos POO
 */
data class ConsultaCompleta(
    val consulta: Consulta,
    val dueno: Dueno,
    val mascota: Mascota,
    val veterinario: Veterinario,
    val fechaHora: String
) {
    /**
     * Método para mostrar información completa
     */
    fun mostrarInformacionCompleta(): String {
        return """
            ╔═══════════════════════════════════════════════════════════════╗
            ║              INFORMACIÓN COMPLETA DE CONSULTA                 ║
            ╚═══════════════════════════════════════════════════════════════╝
            
            ${consulta.generarResumen()}
            
            ${veterinario.mostrarInformacion()}
            
            ${mascota.mostrarInformacion()}
            
            ${dueno.mostrarInformacion()}
        """.trimIndent()
    }

    /**
     * Método para verificar si está activa
     */
    fun estaActiva(): Boolean = consulta.estaProgramada() || consulta.estaPendiente()
}

