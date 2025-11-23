package com.example.vet_clinic_android.service

import android.annotation.SuppressLint
import com.example.vet_clinic_android.model.Consulta
import com.example.vet_clinic_android.model.ConsultaCompleta
import com.example.vet_clinic_android.model.Dueno
import com.example.vet_clinic_android.model.Mascota
import com.example.vet_clinic_android.model.Veterinario
import com.example.vet_clinic_android.util.formatearMoneda
import com.example.vet_clinic_android.util.solicitarEnteroEnRango
import com.example.vet_clinic_android.util.solicitarTexto

/**
 * Servicio para gestión de consultas veterinarias
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 */

@SuppressLint("NewApi") // Desugaring habilitado para java.time en API 24+
class ConsultaService {

    // Arreglo para almacenar todas las consultas registradas
    private val consultasRegistradas = mutableListOf<ConsultaCompleta>()

    init {
        // Cargar consultas de ejemplo al inicializar
        cargarConsultasDeEjemplo()
    }

    /**
     * Carga consultas de ejemplo para demostración
     */
    private fun cargarConsultasDeEjemplo() {
        // Veterinario de ejemplo
        val vet1 = Veterinario(nombre = "Dr. Juan Pérez", especialidad = "Medicina General")
        val vet2 = Veterinario(nombre = "Dra. María Silva", especialidad = "Cirugía")
        val vet3 = Veterinario(nombre = "Dr. Carlos López", especialidad = "Emergencias")

        // Consulta 1: Control rutinario
        val mascota1 = Mascota("Luna", "Perro", 3, 12.5, "Labrador", "Dorado", "Hembra")
        val dueno1 = Dueno("María González", "+56912345678", "maria.gonzalez@email.com", "Av. Providencia 123", "12.345.678-9")
        val consulta1 = Consulta(
            idConsulta = 1001,
            descripcion = "Control de rutina y vacunación anual",
            costoConsulta = 18000.0,
            estado = "Completada",
            tipoServicio = "Control",
            fechaHora = "2025-11-20 10:00",
            comentariosAdicionales = "Mascota en excelente estado de salud"
        )
        consultasRegistradas.add(ConsultaCompleta(consulta1, dueno1, mascota1, vet1, "2025-11-20 10:00"))

        // Consulta 2: Emergencia
        val mascota2 = Mascota("Max", "Gato", 5, 4.8, "Persa", "Blanco", "Macho")
        val dueno2 = Dueno("Carlos Rodríguez", "+56987654321", "carlos.r@email.com", "Los Leones 456", "23.456.789-0")
        val consulta2 = Consulta(
            idConsulta = 1002,
            descripcion = "Emergencia: Intoxicación alimentaria",
            costoConsulta = 50000.0,
            estado = "Completada",
            tipoServicio = "Emergencia",
            fechaHora = "2025-11-21 15:30",
            comentariosAdicionales = "Tratamiento exitoso, mascota recuperada"
        )
        consultasRegistradas.add(ConsultaCompleta(consulta2, dueno2, mascota2, vet3, "2025-11-21 15:30"))

        // Consulta 3: Cirugía menor
        val mascota3 = Mascota("Rocky", "Perro", 7, 28.0, "Pastor Alemán", "Negro y café", "Macho")
        val dueno3 = Dueno("Ana Martínez", "+56998765432", "ana.martinez@email.com", "Las Condes 789", "34.567.890-1")
        val consulta3 = Consulta(
            idConsulta = 1003,
            descripcion = "Extracción de masa cutánea",
            costoConsulta = 80000.0,
            estado = "Completada",
            tipoServicio = "Cirugía Menor",
            fechaHora = "2025-11-22 09:00",
            comentariosAdicionales = "Procedimiento exitoso, resultados de biopsia benignos"
        )
        consultasRegistradas.add(ConsultaCompleta(consulta3, dueno3, mascota3, vet2, "2025-11-22 09:00"))

        // Consulta 4: Consulta general pendiente
        val mascota4 = Mascota("Mimi", "Gato", 2, 3.5, "Siamés", "Crema", "Hembra")
        val dueno4 = Dueno("Pedro Silva", "+56976543210", "pedro.silva@email.com", "Vitacura 321", "45.678.901-2")
        val consulta4 = Consulta(
            idConsulta = 1004,
            descripcion = "Revisión por pérdida de apetito",
            costoConsulta = 25000.0,
            estado = "Pendiente",
            tipoServicio = "Consulta General",
            fechaHora = "2025-11-25 14:00",
            comentariosAdicionales = null
        )
        consultasRegistradas.add(ConsultaCompleta(consulta4, dueno4, mascota4, vet1, "2025-11-25 14:00"))

        // Consulta 5: Desparasitación
        val mascota5 = Mascota("Bobby", "Perro", 1, 8.0, "Beagle", "Tricolor", "Macho")
        val dueno5 = Dueno("Laura Fernández", "+56965432109", "laura.f@email.com", "Ñuñoa 654", "56.789.012-3")
        val consulta5 = Consulta(
            idConsulta = 1005,
            descripcion = "Desparasitación preventiva",
            costoConsulta = 12000.0,
            estado = "Completada",
            tipoServicio = "Desparasitación",
            fechaHora = "2025-11-23 11:30",
            comentariosAdicionales = "Próxima desparasitación en 3 meses"
        )
        consultasRegistradas.add(ConsultaCompleta(consulta5, dueno5, mascota5, vet1, "2025-11-23 11:30"))
    }

    private val tiposServicio = mapOf(
        1 to Pair("Consulta General", 25000.0),
        2 to Pair("Vacunación", 15000.0),
        3 to Pair("Cirugía Menor", 80000.0),
        4 to Pair("Cirugía Mayor", 250000.0),
        5 to Pair("Emergencia", 50000.0),
        6 to Pair("Control", 18000.0),
        7 to Pair("Desparasitación", 12000.0)
    )

    fun mostrarTiposServicio() {
        println("\nTipos de servicio disponibles:")
        tiposServicio.forEach { (key, value) ->
            println("$key. ${value.first}")
        }
    }

    fun calcularCostoConsulta(tipoServicio: String, tiempoMinutos: Int): Double {
        val costoBase = when (tipoServicio.lowercase()) {
            "consulta general" -> 25000.0
            "vacunación" -> 15000.0
            "cirugía menor" -> 80000.0
            "cirugía mayor" -> 250000.0
            "emergencia" -> 50000.0
            "control" -> 18000.0
            "desparasitación" -> 12000.0
            else -> 20000.0
        }

        val costoTiempo = if (tiempoMinutos > 30) {
            ((tiempoMinutos - 30) / 10) * 500.0
        } else {
            0.0
        }

        return costoBase + costoTiempo
    }

    fun aplicarDescuento(costo: Double, numeroMascotas: Int): Double {
        return if (numeroMascotas > 1) {
            val descuento = costo * 0.15
            val costoFinal = costo - descuento
            println("\nDescuento aplicado: 15% por atención de múltiples mascotas")
            println("   Descuento: ${formatearMoneda(descuento)}")
            println("   Costo original: ${formatearMoneda(costo)}")
            println("   Costo final: ${formatearMoneda(costoFinal)}")
            costoFinal
        } else {
            costo
        }
    }

    fun obtenerNombreServicio(opcion: Int): String {
        return tiposServicio[opcion]?.first ?: "Consulta General"
    }

    /**
     * Crea consulta con manejo robusto de excepciones
     * Try-catch para entradas inválidas
     */
    fun crearConsultaSegura(
        idConsulta: Int,
        descripcion: String,
        costoFinal: Double,
        estado: String = "Pendiente",
        tipoServicio: String = "Consulta General",
        comentarios: String? = null
    ): Consulta? {
        return try {
            // Validaciones con excepciones específicas
            if (descripcion.isBlank()) {
                throw IllegalArgumentException("La descripción no puede estar vacía")
            }

            if (costoFinal < 0) {
                throw IllegalArgumentException("El costo no puede ser negativo")
            }

            Consulta(
                idConsulta = idConsulta,
                descripcion = descripcion,
                costoConsulta = costoFinal,
                estado = estado,
                tipoServicio = tipoServicio,
                comentariosAdicionales = comentarios  // Campo opcional
            )
        } catch (e: IllegalArgumentException) {
            println("❌ ERROR al crear consulta: ${e.message}")
            println("   Usando valores por defecto")
            Consulta(
                idConsulta = idConsulta,
                descripcion = descripcion.ifBlank { "Sin descripción" },
                costoConsulta = if (costoFinal < 0) 0.0 else costoFinal,
                estado = estado,
                tipoServicio = tipoServicio
            )
        } catch (e: Exception) {
            println("❌ ERROR inesperado al crear consulta: ${e.message}")
            null
        }
    }

    fun crearConsulta(
        idConsulta: Int,
        descripcion: String,
        costoFinal: Double,
        estado: String = "Pendiente",
        tipoServicio: String = "Consulta General"
    ): Consulta {
        return Consulta(
            idConsulta = idConsulta,
            descripcion = descripcion,
            costoConsulta = costoFinal,
            estado = estado,
            tipoServicio = tipoServicio
        )
    }

    fun generarIdConsulta(): Int {
        return (1000..9999).random()
    }

    fun generarResumen(
        dueno: Dueno,
        mascota: Mascota,
        consulta: Consulta,
        veterinario: Veterinario,
        mascotaService: MascotaService,
        duenoService: DuenoService
    ) {
        println("\n")
        println("╔═══════════════════════════════���═══════���═══════════════════════╗")
        println("║                  RESUMEN DE LA CONSULTA                       ║")
        println("╠═══════════════════════════════════════════════════════════════╣")
        println("║  CLÍNICA VETERINARIA - SANTIAGO, CHILE                        ║")
        println("╚═══════════════════════════════════════════════════════════════╝")

        duenoService.mostrarDetallesDueno(dueno)
        mascotaService.mostrarDetallesMascota(mascota)

        println("\n┌─────────────────────────────────────────┐")
        println("│ DETALLES DE LA CONSULTA                 │")
        println("└───���─────────────────────────────────────┘")
        println("  ID Consulta: #${consulta.idConsulta}")
        println("  Motivo:      ${consulta.descripcion}")
        println("  Veterinario: Dr(a). ${veterinario.nombre}")
        println("  Especialidad: ${veterinario.especialidad}")
        println("  Costo:       ${formatearMoneda(consulta.costoConsulta)}")
        println("  Estado:      ${consulta.estado}")

        println("\n╔═══════════════════════════════════════════════════════════════╗")
        println("║  GRACIAS POR CONFIAR EN NOSOTROS                              ║")
        println("║  Recordatorio: Recibirá un email con los detalles            ║")
        println("╚═══════════════════════���═══════════════════════════════════════╝\n")
    }

    /**
     * Envía recordatorios usando let - solo si email es válido
     * Manejo robusto de valores nulos
     */
    fun enviarRecordatorios(dueno: Dueno) {
        println("\n📮 Configurando recordatorios...")

        // Usar let para enviar email solo si es válido
        dueno.email.takeIf { it.contains("@") && it.contains(".") }?.let { emailValido ->
            println("✅ Se enviará recordatorio a $emailValido 24 horas antes de la cita")
        } ?: println("⚠️  Email inválido. No se enviará recordatorio por correo")

        // Usar let para enviar SMS solo si teléfono existe
        dueno.telefono.takeIf { it.isNotBlank() }?.let { telefonoValido ->
            println("✅ Se enviará SMS al número $telefonoValido")
        } ?: println("⚠️  Teléfono no disponible. No se enviará SMS")

        println()
    }

    /**
     * Agrega una consulta simple (sin veterinario aún asignado)
     * Para registro inicial desde la UI
     */
    fun agregarConsulta(consulta: Consulta) {
        try {
            // Crear datos temporales para el registro inicial
            val duenoTemp = Dueno(
                nombre = "Por asignar",
                telefono = "000000000",
                email = "temp@clinic.com"
            )
            val mascotaTemp = Mascota(
                nombre = "Por registrar",
                especie = "N/A",
                edad = 0,
                peso = 0.0
            )
            val veterinarioTemp = Veterinario(
                nombre = "Por asignar",
                especialidad = "General"
            )

            val consultaCompleta = ConsultaCompleta(
                consulta = consulta,
                dueno = duenoTemp,
                mascota = mascotaTemp,
                veterinario = veterinarioTemp,
                fechaHora = java.time.LocalDateTime.now().toString()
            )
            consultasRegistradas.add(consultaCompleta)
        } catch (e: Exception) {
            println("❌ ERROR al agregar consulta: ${e.message}")
        }
    }

    /**
     * Registra una consulta completa en el arreglo
     * Con manejo seguro de nulos
     */
    fun registrarConsultaCompleta(
        consulta: Consulta,
        dueno: Dueno,
        mascota: Mascota,
        veterinario: Veterinario,
        fechaHora: String
    ) {
        try {
            val consultaCompleta = ConsultaCompleta(consulta, dueno, mascota, veterinario, fechaHora)
            consultasRegistradas.add(consultaCompleta)
            println("✅ Consulta #${consulta.idConsulta} registrada en el sistema")
        } catch (e: Exception) {
            println("❌ ERROR al registrar consulta completa: ${e.message}")
        }
    }

    /**
     * Genera informe de todas las consultas usando ciclo for
     */
    fun generarInformeConsultas() {
        if (consultasRegistradas.isEmpty()) {
            println("\n⚠️  No hay consultas registradas en el sistema.")
            return
        }

        println("\n╔═══════════════════════════════════════���═══════════════════════╗")
        println("║              INFORME DE CONSULTAS REGISTRADAS                 ║")
        println("╚═══════════════════════════════════════════════════════════════╝")
        println("Total de consultas: ${consultasRegistradas.size}\n")

        // Ciclo for para recorrer todas las consultas
        for (i in consultasRegistradas.indices) {
            val cc = consultasRegistradas[i]
            println("┌─────────────── CONSULTA #${i + 1} ───────────────────────────┐")
            println("│ ID: #${cc.consulta.idConsulta} | Estado: ${cc.consulta.estado}")
            println("├──────────────────────────────────────────────────────────────┤")
            println("│ Dueño:       ${cc.dueno.nombreDueno}")
            println("│ Email:       ${cc.dueno.email}")
            println("│ Teléfono:    ${cc.dueno.telefono}")
            println("├──────────────────────────────────────────────────────────────┤")
            println("│ Mascota:     ${cc.mascota.nombre} (${cc.mascota.especie})")
            println("│ Edad:        ${cc.mascota.edad} año(s) | Peso: ${cc.mascota.peso} kg")
            println("├──────────────────────────────────────────────────────────────┤")
            println("│ Motivo:      ${cc.consulta.descripcion}")
            println("│ Veterinario: Dr(a). ${cc.veterinario.nombre}")
            println("│ Especialidad: ${cc.veterinario.especialidad}")
            println("│ Fecha/Hora:  ${cc.fechaHora}")
            println("│ Costo:       ${formatearMoneda(cc.consulta.costoConsulta)}")
            println("└──────────────────────────────────────────────────────────────┘\n")
        }
    }

    /**
     * Filtra consultas por estado (Pendiente/Programada/Realizada/Cancelada)
     */
    fun filtrarConsultasPorEstado(estado: String): List<ConsultaCompleta> {
        val consultasFiltradas = mutableListOf<ConsultaCompleta>()

        // Ciclo for para filtrar
        for (consulta in consultasRegistradas) {
            if (consulta.consulta.estado.equals(estado, ignoreCase = true)) {
                consultasFiltradas.add(consulta)
            }
        }

        return consultasFiltradas
    }

    /**
     * Muestra consultas pendientes agrupadas
     */
    fun mostrarConsultasPendientes() {
        val pendientes = filtrarConsultasPorEstado("Pendiente")

        if (pendientes.isEmpty()) {
            println("\n✅ No hay consultas pendientes.")
            return
        }

        println("\n╔═══════════════════════════════���═══════════════════════════════╗")
        println("║                  CONSULTAS PENDIENTES                         ║")
        println("╚═══════════════════════════════════════════════════════���═══════╝")
        println("Total pendientes: ${pendientes.size}\n")

        for ((index, cc) in pendientes.withIndex()) {
            println("${index + 1}. ID: #${cc.consulta.idConsulta} | ${cc.mascota.nombre} (${cc.dueno.nombreDueno})")
            println("   Motivo: ${cc.consulta.descripcion}")
            println("   Costo: ${formatearMoneda(cc.consulta.costoConsulta)}\n")
        }
    }

    /**
     * Muestra consultas programadas
     */
    fun mostrarConsultasProgramadas() {
        val programadas = filtrarConsultasPorEstado("Programada")

        if (programadas.isEmpty()) {
            println("\n⚠️  No hay consultas programadas.")
            return
        }

        println("\n╔═══════════════════════════════════════���═══════════════════════╗")
        println("║                 CONSULTAS PROGRAMADAS                         ║")
        println("╚═══════════════════════════════════════════════���═══════════════╝")
        println("Total programadas: ${programadas.size}\n")

        for ((index, cc) in programadas.withIndex()) {
            println("${index + 1}. ID: #${cc.consulta.idConsulta} | ${cc.mascota.nombre}")
            println("   Dueño: ${cc.dueno.nombreDueno} | Tel: ${cc.dueno.telefono}")
            println("   Veterinario: Dr(a). ${cc.veterinario.nombre}")
            println("   Fecha/Hora: ${cc.fechaHora}")
            println("   Costo: ${formatearMoneda(cc.consulta.costoConsulta)}\n")
        }
    }

    /**
     * Genera estadísticas de consultas
     */
    fun generarEstadisticas() {
        if (consultasRegistradas.isEmpty()) {
            println("\n⚠️  No hay datos para generar estadísticas.")
            return
        }

        var totalPendientes = 0
        var totalProgramadas = 0
        var totalRealizadas = 0
        var costoTotal = 0.0

        // Ciclo for para calcular estadísticas
        for (cc in consultasRegistradas) {
            when (cc.consulta.estado.lowercase()) {
                "pendiente" -> totalPendientes++
                "programada" -> totalProgramadas++
                "realizada" -> totalRealizadas++
            }
            costoTotal += cc.consulta.costoConsulta
        }

        println("\n╔═══════════════════════════════════════════════════════════════╗")
        println("║                 ESTADÍSTICAS DEL SISTEMA                      ║")
        println("╚═══════════════════════════════════════════════════════════════╝")
        println("  Total de consultas:    ${consultasRegistradas.size}")
        println("  ─────────────────────────────────────────────────────────────")
        println("  Pendientes:            $totalPendientes")
        println("  Programadas:           $totalProgramadas")
        println("  Realizadas:            $totalRealizadas")
        println("  ─────────────────────────────────────────────────────────────")
        println("  Ingreso Total:         ${formatearMoneda(costoTotal)}")
        println("  Promedio por consulta: ${formatearMoneda(costoTotal / consultasRegistradas.size)}")
        println("╚═══════════════════════════════════════════════════════════════╝\n")
    }

    /**
     * Obtiene el total de consultas registradas
     */
    fun getTotalConsultas(): Int = consultasRegistradas.size

    /**
     * Obtiene todas las consultas registradas
     */
    fun obtenerTodasConsultas(): List<ConsultaCompleta> = consultasRegistradas.toList()

    /**
     * Obtiene consultas pendientes
     */
    fun obtenerConsultasPendientes(): List<ConsultaCompleta> = filtrarConsultasPorEstado("Pendiente")

    /**
     * Obtiene consultas programadas
     */
    fun obtenerConsultasProgramadas(): List<ConsultaCompleta> = filtrarConsultasPorEstado("Programada")

    /**
     * Obtiene estadísticas del sistema en formato Map
     */
    fun obtenerEstadisticas(): Map<String, Any> {
        if (consultasRegistradas.isEmpty()) {
            return mapOf(
                "total" to 0,
                "pendientes" to 0,
                "programadas" to 0,
                "completadas" to 0,
                "ingresosTotal" to 0.0,
                "promedioConsulta" to 0.0
            )
        }

        var totalPendientes = 0
        var totalProgramadas = 0
        var totalCompletadas = 0
        var costoTotal = 0.0
        val serviciosCantidad = mutableMapOf<String, Int>()

        for (cc in consultasRegistradas) {
            when (cc.consulta.estado.lowercase()) {
                "pendiente" -> totalPendientes++
                "programada" -> totalProgramadas++
                "completada", "realizada" -> totalCompletadas++
            }
            costoTotal += cc.consulta.costoConsulta

            // Contar servicios
            val servicio = cc.consulta.tipoServicio
            serviciosCantidad[servicio] = (serviciosCantidad[servicio] ?: 0) + 1
        }

        return mapOf(
            "total" to consultasRegistradas.size,
            "pendientes" to totalPendientes,
            "programadas" to totalProgramadas,
            "completadas" to totalCompletadas,
            "ingresosTotal" to costoTotal,
            "promedioConsulta" to (costoTotal / consultasRegistradas.size),
            "serviciosMasSolicitados" to serviciosCantidad.toList().sortedByDescending { it.second }.take(5).toMap()
        )
    }

    fun crearConsultaInteractiva(): Consulta {
        val descripcion = solicitarTexto("Descripción de la consulta: ")
        val numeroMascotas = solicitarEnteroEnRango("Número de mascotas", 1..5)
        val tiempoEstimado = solicitarEnteroEnRango("Tiempo estimado (minutos)", 10..120)

        val costoConsulta = calcularCostoConsulta(obtenerNombreServicio(1), tiempoEstimado) // Por defecto, Consulta General
        val consulta = crearConsulta(generarIdConsulta(), descripcion, costoConsulta)

        println("\nConsulta creada exitosamente:")
        println("ID: ${consulta.idConsulta} | Motivo: ${consulta.descripcion} | Costo: ${formatearMoneda(consulta.costoConsulta)}")

        return consulta
    }
}
