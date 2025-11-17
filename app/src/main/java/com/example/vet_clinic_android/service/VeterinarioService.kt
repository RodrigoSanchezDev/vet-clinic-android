package com.example.vet_clinic_android.service

import android.annotation.SuppressLint
import com.example.vet_clinic_android.model.Veterinario
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Servicio para gestión de veterinarios con colecciones avanzadas
 * Usa filter, find y Map para agenda
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 */

@SuppressLint("NewApi") // Desugaring habilitado para java.time en API 24+
class VeterinarioService {

    // Colección de veterinarios disponibles
    private val veterinarios = listOf(
        Veterinario(
            nombre = "María González",
            telefono = "+56911111111",
            email = "maria.gonzalez@veterinaria.cl",
            especialidad = "Medicina General",
            disponible = true
        ),
        Veterinario(
            nombre = "Carlos Rojas",
            telefono = "+56922222222",
            email = "carlos.rojas@veterinaria.cl",
            especialidad = "Cirugía",
            disponible = true
        ),
        Veterinario(
            nombre = "Ana Martínez",
            telefono = "+56933333333",
            email = "ana.martinez@veterinaria.cl",
            especialidad = "Dermatología",
            disponible = false
        ),
        Veterinario(
            nombre = "Luis Fernández",
            telefono = "+56944444444",
            email = "luis.fernandez@veterinaria.cl",
            especialidad = "Medicina General",
            disponible = true
        ),
        Veterinario(
            nombre = "Patricia Silva",
            telefono = "+56955555555",
            email = "patricia.silva@veterinaria.cl",
            especialidad = "Emergencias",
            disponible = false
        )
    )

    // Map para agenda: nombre del veterinario -> lista de horarios ocupados
    private val agendaVeterinarios = mutableMapOf<String, MutableList<String>>(
        "María González" to mutableListOf("01/11/2025 10:00", "01/11/2025 14:00"),
        "Carlos Rojas" to mutableListOf("02/11/2025 09:00"),
        "Ana Martínez" to mutableListOf(),
        "Luis Fernández" to mutableListOf("01/11/2025 11:00"),
        "Patricia Silva" to mutableListOf()
    )

    /**
     * Filtra veterinarios que estén disponibles usando filter
     * Función reutilizable con colección avanzada
     */
    fun obtenerVeterinariosDisponibles(): List<Veterinario> {
        return veterinarios.filter { it.disponible }
    }

    /**
     * Filtra veterinarios libres en un horario específico
     * Usa filter y Map combinados
     */
    fun obtenerVeterinariosLibresEnHorario(fechaHora: String): List<Veterinario> {
        return veterinarios.filter { veterinario ->
            // Verificar que esté disponible
            veterinario.disponible &&
            // Verificar que no tenga ese horario ocupado en su agenda
            !(agendaVeterinarios[veterinario.nombre]?.contains(fechaHora) ?: false)
        }
    }

    /**
     * Busca un veterinario por especialidad usando find
     * Retorna el primer veterinario disponible con esa especialidad
     */
    fun buscarVeterinarioPorEspecialidad(especialidad: String): Veterinario? {
        return veterinarios.find {
            it.especialidad.equals(especialidad, ignoreCase = true) && it.disponible
        }
    }

    /**
     * Busca veterinario por nombre usando find
     */
    fun buscarVeterinarioPorNombre(nombre: String): Veterinario? {
        return veterinarios.find {
            it.nombre.equals(nombre, ignoreCase = true)
        }
    }

    /**
     * Registra una cita en la agenda del veterinario
     * Usa Map para gestionar la agenda
     */
    fun registrarCitaEnAgenda(nombreVeterinario: String, fechaHora: String): Boolean {
        val agenda = agendaVeterinarios[nombreVeterinario]
        return if (agenda != null) {
            if (!agenda.contains(fechaHora)) {
                agenda.add(fechaHora)
                true
            } else {
                false // Ya está ocupado en ese horario
            }
        } else {
            false
        }
    }

    /**
     * Obtiene la agenda completa de un veterinario usando Map
     */
    fun obtenerAgendaVeterinario(nombreVeterinario: String): List<String> {
        return agendaVeterinarios[nombreVeterinario]?.toList() ?: emptyList()
    }

    /**
     * Muestra todos los veterinarios disponibles usando filter
     */
    fun mostrarVeterinariosDisponibles() {
        val disponibles = obtenerVeterinariosDisponibles()

        println("\nVeterinarios disponibles:")
        disponibles.forEachIndexed { index, vet ->
            val citasAgendadas = agendaVeterinarios[vet.nombre]?.size ?: 0
            println("${index + 1}. Dr(a). ${vet.nombre} - ${vet.especialidad} ($citasAgendadas citas agendadas)")
        }
    }

    /**
     * Muestra veterinarios libres en un horario específico
     * Usa filter avanzado
     */
    fun mostrarVeterinariosLibresEnHorario(fechaHora: String) {
        val libres = obtenerVeterinariosLibresEnHorario(fechaHora)

        println("\n╔═══════════════════════════════════════════════════════════════╗")
        println("║     VETERINARIOS LIBRES EN: $fechaHora")
        println("╚═══════════════════════════════════════════════════════════════╝")

        if (libres.isEmpty()) {
            println("  ⚠️  No hay veterinarios disponibles en ese horario")
            println("  💡 Horarios alternativos sugeridos:")
            sugerirHorariosAlternativos()
        } else {
            libres.forEach { vet ->
                println("  ✅ Dr(a). ${vet.nombre} - ${vet.especialidad}")
            }
        }
    }

    /**
     * Obtiene veterinario por opción del menú
     */
    fun obtenerVeterinario(opcion: Int): Veterinario {
        val disponibles = obtenerVeterinariosDisponibles()
        return disponibles.getOrElse(opcion - 1) { disponibles[0] }
    }

    /**
     * Verifica disponibilidad y registra en agenda
     */
    fun verificarDisponibilidad(
        veterinario: Veterinario,
        fechaHoraSolicitada: String
    ): Boolean {
        println("\n╔═══════════════════════════════════════╗")
        println("║    VERIFICACIÓN DE DISPONIBILIDAD     ║")
        println("╚═══════════════════════════════════════╝")

        println("Veterinario: ${veterinario.nombre}")
        println("Especialidad: ${veterinario.especialidad}")
        println("Fecha y hora solicitada: $fechaHoraSolicitada")

        // Verificar si está libre usando la agenda (Map)
        val agenda = agendaVeterinarios[veterinario.nombre]
        val estaOcupado = agenda?.contains(fechaHoraSolicitada) ?: false

        return if (veterinario.disponible && !estaOcupado) {
            // Registrar la cita en la agenda
            registrarCitaEnAgenda(veterinario.nombre, fechaHoraSolicitada)
            println("\nVeterinario disponible")
            println("Consulta registrada en la agenda del Dr(a). ${veterinario.nombre}")
            true
        } else {
            println("\nVeterinario NO disponible en ese horario")
            mostrarVeterinariosLibresEnHorario(fechaHoraSolicitada)
            false
        }
    }

    /**
     * Muestra la agenda completa de todos los veterinarios
     * Usa Map.forEach
     */
    fun mostrarAgendaCompleta() {
        println("\n╔═══════════════════════════════════════════════════════════════╗")
        println("║              AGENDA COMPLETA DE VETERINARIOS                  ║")
        println("╚═══════════════════════════════════════════════════════════════╝")

        agendaVeterinarios.forEach { (veterinario, citas) ->
            println("\n📅 Dr(a). $veterinario")
            if (citas.isEmpty()) {
                println("   Sin citas agendadas")
            } else {
                citas.forEachIndexed { index, cita ->
                    println("   ${index + 1}. $cita")
                }
            }
        }
    }

    /**
     * Cuenta total de citas agendadas usando Map
     */
    fun contarTotalCitas(): Int {
        return agendaVeterinarios.values.sumOf { it.size }
    }

    /**
     * Obtiene estadísticas por veterinario usando Map
     */
    fun mostrarEstadisticasVeterinarios() {
        println("\n╔═══════════════════════════════════════════════════════════════╗")
        println("║           ESTADÍSTICAS DE VETERINARIOS                        ║")
        println("╚═══════════════════════════════════════════════════════════════╝")

        val totalCitas = contarTotalCitas()
        println("  Total de citas en sistema: $totalCitas")
        println("  ─────────────────────────────────────────────────────────────")

        veterinarios.forEach { vet ->
            val citas = agendaVeterinarios[vet.nombre]?.size ?: 0
            val estado = if (vet.disponible) "✅ Disponible" else "❌ No disponible"
            println("  ${vet.nombre}")
            println("    Especialidad: ${vet.especialidad}")
            println("    Estado: $estado")
            println("    Citas agendadas: $citas")
            println()
        }
    }

    /**
     * Sugiere horarios alternativos
     */
    private fun sugerirHorariosAlternativos() {
        val ahora = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

        println("     - ${ahora.plusHours(2).format(formatter)}")
        println("     - ${ahora.plusDays(1).withHour(10).withMinute(0).format(formatter)}")
        println("     - ${ahora.plusDays(1).withHour(15).withMinute(30).format(formatter)}")
    }

    fun obtenerFechaHoraSugerida(): String {
        return LocalDateTime.now()
            .plusDays(1)
            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
    }

    /**
     * Obtiene total de veterinarios
     */
    fun getTotalVeterinarios(): Int = veterinarios.size

    /**
     * Obtiene total de veterinarios disponibles usando filter
     */
    fun getTotalVeterinariosDisponibles(): Int = veterinarios.filter { it.disponible }.size

    /**
     * Obtiene todos los veterinarios (para pantalla de agenda)
     */
    fun obtenerTodosVeterinarios(): List<Veterinario> = veterinarios.toList()

    /**
     * Busca veterinarios por especialidad
     */
    fun buscarPorEspecialidad(especialidad: String): List<Veterinario> {
        return veterinarios.filter {
            it.especialidad.equals(especialidad, ignoreCase = true)
        }
    }
}
