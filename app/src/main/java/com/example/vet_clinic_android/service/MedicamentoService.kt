package com.example.vet_clinic_android.service

import android.annotation.SuppressLint
import com.example.vet_clinic_android.model.Mascota
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Servicio para cálculos de medicamentos y próximas vacunaciones
 * Promueve modularidad con funciones reutilizables
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 */

@SuppressLint("NewApi") // Desugaring habilitado para java.time en API 24+
class MedicamentoService {

    /**
     * Calcula la próxima fecha de vacunación según especie
     * Función reutilizable que retorna LocalDate
     *
     * @param ultimaVacunacion Fecha de la última vacunación en formato "dd/MM/yyyy"
     * @param especie Especie de la mascota
     * @return Fecha de la próxima vacunación
     */
    fun calcularProximaVacunacion(ultimaVacunacion: String, especie: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val fechaUltima = try {
            LocalDate.parse(ultimaVacunacion, formatter)
        } catch (e: Exception) {
            LocalDate.now()
        }

        // Calcular meses según especie usando when como expresión
        val mesesHastaProxima = when (especie.lowercase()) {
            "perro" -> 12      // Anual
            "gato" -> 12       // Anual
            "conejo" -> 6      // Semestral
            "ave", "loro", "canario" -> 12
            "hamster", "cobayo" -> 6
            else -> 12
        }

        return fechaUltima.plusMonths(mesesHastaProxima.toLong())
    }

    /**
     * Determina la dosis de medicamento según peso y edad
     * Función modular que retorna objeto DosisRecomendada
     *
     * @param peso Peso de la mascota en kg
     * @param edad Edad de la mascota en años
     * @return Dosis recomendada con cantidad y frecuencia
     */
    fun determinarDosisRecomendada(peso: Double, edad: Int): DosisRecomendada {
        // Dosis base: 10mg por kg de peso
        val dosisBasePorKg = 10.0
        var dosisTotal = peso * dosisBasePorKg

        // Ajuste por edad usando when
        val factorEdad = when {
            edad < 1 -> 0.5      // Cachorros/jóvenes: 50% de dosis
            edad in 1..7 -> 1.0  // Adultos: 100% de dosis
            edad > 7 -> 0.75     // Seniors: 75% de dosis
            else -> 1.0
        }

        dosisTotal *= factorEdad

        // Determinar frecuencia
        val frecuencia = when {
            peso < 5.0 -> "Cada 12 horas"
            peso in 5.0..20.0 -> "Cada 8 horas"
            peso > 20.0 -> "Cada 6 horas"
            else -> "Cada 8 horas"
        }

        return DosisRecomendada(
            dosis = dosisTotal,
            unidad = "mg",
            frecuencia = frecuencia,
            observaciones = generarObservaciones(peso, edad)
        )
    }

    /**
     * Genera observaciones según características de la mascota
     * Función auxiliar reutilizable
     */
    private fun generarObservaciones(peso: Double, edad: Int): String {
        return when {
            edad < 1 && peso < 3.0 -> "Dosis pediátrica reducida. Monitorear de cerca."
            edad > 10 -> "Ajuste para mascota senior. Revisar función renal."
            peso > 30.0 -> "Dosis para mascota de gran tamaño. Dividir en 2 tomas."
            else -> "Dosis estándar. Administrar con alimento."
        }
    }

    /**
     * Formatea la fecha para mostrar al usuario
     * Función utilitaria reutilizable
     */
    fun formatearFecha(fecha: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return fecha.format(formatter)
    }

    /**
     * Calcula días hasta la próxima vacunación
     * Función reutilizable para recordatorios
     */
    fun calcularDiasHastaVacunacion(proximaFecha: LocalDate): Long {
        val hoy = LocalDate.now()
        return java.time.temporal.ChronoUnit.DAYS.between(hoy, proximaFecha)
    }

    /**
     * Muestra información completa de medicamento para una mascota
     */
    fun mostrarInformacionMedicamento(mascota: Mascota, ultimaVacunacion: String) {
        println("\n╔═══════════════════════════════════════════════════════════════╗")
        println("║        INFORMACIÓN DE MEDICAMENTO Y VACUNACIÓN               ║")
        println("╚═══════════════════════════════════════════════════════════════╝")
        println("  Mascota:     ${mascota.nombre} (${mascota.especie})")
        println("  Edad:        ${mascota.edad} año(s)")
        println("  Peso:        ${mascota.peso} kg")
        println("  ─────────────────────────────────────────────────────────────")

        // Calcular dosis
        val dosis = determinarDosisRecomendada(mascota.peso, mascota.edad)
        println("  📊 DOSIS RECOMENDADA:")
        println("     Cantidad:    ${String.format("%.2f", dosis.dosis)} ${dosis.unidad}")
        println("     Frecuencia:  ${dosis.frecuencia}")
        println("     Nota:        ${dosis.observaciones}")
        println("  ─────────────────────────────────────────────────────────────")

        // Calcular próxima vacunación
        val proximaVacuna = calcularProximaVacunacion(ultimaVacunacion, mascota.especie)
        val diasRestantes = calcularDiasHastaVacunacion(proximaVacuna)

        println("  💉 PRÓXIMA VACUNACIÓN:")
        println("     Última vacuna: $ultimaVacunacion")
        println("     Próxima fecha: ${formatearFecha(proximaVacuna)}")
        println("     Días restantes: $diasRestantes días")

        if (diasRestantes < 0) {
            println("     ⚠️  ATENCIÓN: Vacuna VENCIDA")
        } else if (diasRestantes <= 30) {
            println("     ⚠️  Vacuna próxima a vencer")
        }

        println("╚═══════════════════════════════════════════════════════════════╝")
    }
}

/**
 * Data class para dosis recomendada
 * Encapsula información de forma modular
 */
data class DosisRecomendada(
    val dosis: Double,
    val unidad: String,
    val frecuencia: String,
    val observaciones: String
)

