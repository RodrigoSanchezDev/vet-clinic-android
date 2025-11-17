package com.example.vet_clinic_android.service

import com.example.vet_clinic_android.model.Mascota

/**
 * Servicio para gestión de vacunas según especie y edad de la mascota
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 */

class VacunaService {

    /**
     * Determina el tipo de vacuna necesario según especie y edad
     * Utiliza when como expresión para decisión
     */
    fun determinarVacuna(mascota: Mascota): String {
        return when (mascota.especie.lowercase()) {
            "perro" -> when {
                mascota.edad < 1 -> "Anual - Vacuna Múltiple (Parvovirus, Moquillo, Hepatitis)"
                mascota.edad in 1..7 -> "Anual - Refuerzo + Rabia"
                mascota.edad > 7 -> "Semestral - Refuerzo Senior + Chequeo"
                else -> "Opcional - Consultar con veterinario"
            }
            "gato" -> when {
                mascota.edad < 1 -> "Anual - Vacuna Triple Felina"
                mascota.edad in 1..10 -> "Anual - Refuerzo + Rabia"
                mascota.edad > 10 -> "Semestral - Refuerzo Senior"
                else -> "Opcional - Consultar con veterinario"
            }
            "conejo" -> when {
                mascota.edad < 2 -> "Semestral - Mixomatosis y Enfermedad Hemorrágica"
                else -> "Anual - Refuerzo"
            }
            "ave", "loro", "canario" -> when {
                mascota.edad < 1 -> "Anual - Vacuna Aviar Polivalente"
                else -> "Opcional - Según ambiente"
            }
            else -> when {
                mascota.edad < 2 -> "Consultar - Vacunación según especie"
                else -> "No requiere - O consultar con especialista"
            }
        }
    }

    /**
     * Obtiene la frecuencia de vacunación
     */
    fun obtenerFrecuenciaVacunacion(mascota: Mascota): String {
        val vacuna = determinarVacuna(mascota)
        return when {
            vacuna.startsWith("Anual") -> "Anual"
            vacuna.startsWith("Semestral") -> "Semestral"
            vacuna.startsWith("Opcional") -> "Opcional"
            vacuna.startsWith("No requiere") -> "No requiere"
            else -> "Consultar"
        }
    }

    /**
     * Muestra el plan de vacunación recomendado
     */
    fun mostrarPlanVacunacion(mascota: Mascota) {
        println("\n╔═══════════════════════════════════════════════════════════════╗")
        println("║           PLAN DE VACUNACIÓN RECOMENDADO                     ║")
        println("╚═══════════════════════════════════════════════════════════════╝")
        println("  Mascota:     ${mascota.nombre}")
        println("  Especie:     ${mascota.especie}")
        println("  Edad:        ${mascota.edad} año(s)")
        println("  ─────────────────────────────────────────────────────────────")

        val vacuna = determinarVacuna(mascota)
        val frecuencia = obtenerFrecuenciaVacunacion(mascota)

        println("  Frecuencia:  $frecuencia")
        println("  Vacuna:      $vacuna")
        println("╚═══════════════════════════════════════════════════════════════╝")
    }

    /**
     * Verifica si necesita vacuna urgente
     */
    fun necesitaVacunaUrgente(mascota: Mascota): Boolean {
        return when (mascota.especie.lowercase()) {
            "perro", "gato" -> mascota.edad < 1
            "conejo" -> mascota.edad < 2
            else -> false
        }
    }
}

