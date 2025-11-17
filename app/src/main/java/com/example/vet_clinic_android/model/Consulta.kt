package com.example.vet_clinic_android.model

import com.example.vet_clinic_android.util.formatearMoneda

/**
 * Clase Consulta con métodos POO
 * Implementa cálculo de costos, cambio de estado y generación de resumen
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 */
class Consulta(
    val idConsulta: Int,
    val descripcion: String,
    var costoConsulta: Double,
    var estado: String = "Pendiente",
    val tipoServicio: String = "Consulta General",
    val fechaHora: String = "",
    val comentariosAdicionales: String? = null  // Campo opcional (puede ser null)
) {
    // Atributos adicionales
    private var descuentoAplicado: Double = 0.0
    private var costoOriginal: Double = costoConsulta
    private val historialEstados = mutableListOf<String>()

    init {
        historialEstados.add("Creada - $estado")
    }

    /**
     * Obtiene comentarios con operador Elvis ?:
     * Si es null, retorna "Sin comentarios"
     */
    fun obtenerComentarios(): String {
        // Operador Elvis ?: para valor por defecto
        return comentariosAdicionales ?: "Sin comentarios"
    }

    /**
     * Método para calcular costo final con descuento
     * Incluye try-catch para manejo robusto de errores
     */
    fun calcularCostoFinalConDescuento(numeroMascotas: Int): Double {
        return try {
            if (numeroMascotas < 1) {
                throw IllegalArgumentException("El número de mascotas debe ser mayor a 0")
            }

            if (numeroMascotas > 1) {
                val porcentajeDescuento = 0.15
                descuentoAplicado = costoOriginal * porcentajeDescuento
                val costoFinal = costoOriginal - descuentoAplicado

                costoConsulta = costoFinal

                println("\n✅ Descuento aplicado: 15% por atención de múltiples mascotas")
                println("   Descuento:      ${formatearMoneda(descuentoAplicado)}")
                println("   Costo original: ${formatearMoneda(costoOriginal)}")
                println("   Costo final:    ${formatearMoneda(costoFinal)}")

                costoFinal
            } else {
                costoConsulta
            }
        } catch (e: IllegalArgumentException) {
            println("❌ ERROR: ${e.message}")
            println("   Usando 1 mascota por defecto")
            costoConsulta
        } catch (e: Exception) {
            println("❌ ERROR inesperado al calcular descuento: ${e.message}")
            costoConsulta
        }
    }

    /**
     * Método para cambiar estado de la consulta
     * Cumple con requisito de POO
     */
    fun cambiarEstado(nuevoEstado: String) {
        val estadoAnterior = estado
        estado = nuevoEstado
        historialEstados.add("$estadoAnterior → $nuevoEstado")
        println("✅ Estado actualizado: $estadoAnterior → $nuevoEstado")
    }

    /**
     * Método para generar resumen de la consulta
     * Usa operador safe call ?. para campos opcionales
     */
    fun generarResumen(): String {
        val descuentoInfo = if (descuentoAplicado > 0) {
            "\n   Descuento:    ${formatearMoneda(descuentoAplicado)}"
        } else {
            ""
        }

        // Usar let para mostrar comentarios solo si existen
        val comentariosInfo = comentariosAdicionales?.let {
            "\n   Comentarios:  $it"
        } ?: ""

        return """
            ┌─────────────────────────────────────────┐
            │ RESUMEN DE CONSULTA                     │
            └─────────────────────────────────────────┘
            ID:           #$idConsulta
            Tipo:         $tipoServicio
            Descripción:  $descripcion
            Estado:       $estado
            Fecha/Hora:   ${if (fechaHora.isNotEmpty()) fechaHora else "Por definir"}
            Costo:        ${formatearMoneda(costoConsulta)}$descuentoInfo$comentariosInfo
        """.trimIndent()
    }

    /**
     * Método para obtener historial de estados
     */
    fun obtenerHistorialEstados(): List<String> = historialEstados.toList()

    /**
     * Método para verificar si está pendiente
     */
    fun estaPendiente(): Boolean = estado.equals("Pendiente", ignoreCase = true)

    /**
     * Método para verificar si está completada
     */
    fun estaCompletada(): Boolean = estado.equals("Realizada", ignoreCase = true) ||
                                     estado.equals("Completada", ignoreCase = true)

    /**
     * Método para verificar si está programada
     */
    fun estaProgramada(): Boolean = estado.equals("Programada", ignoreCase = true)

    /**
     * Método para aplicar descuento personalizado
     */
    fun aplicarDescuentoPersonalizado(porcentaje: Double): Double {
        if (porcentaje in 0.0..100.0) {
            descuentoAplicado = costoOriginal * (porcentaje / 100)
            costoConsulta = costoOriginal - descuentoAplicado
            println("✅ Descuento de ${porcentaje}% aplicado")
        }
        return costoConsulta
    }

    /**
     * Método para obtener costo con recargo por urgencia
     */
    fun aplicarRecargoUrgencia(): Double {
        val recargo = costoOriginal * 0.50 // 50% de recargo
        costoConsulta = costoOriginal + recargo
        println("⚠️  Recargo por urgencia aplicado: ${formatearMoneda(recargo)}")
        return costoConsulta
    }

    /**
     * Método para marcar como pagada
     */
    fun marcarComoPagada() {
        cambiarEstado("Pagada")
    }

    /**
     * Método para cancelar consulta
     */
    fun cancelar(motivo: String = "") {
        cambiarEstado("Cancelada")
        if (motivo.isNotEmpty()) {
            println("   Motivo: $motivo")
        }
    }

    override fun toString(): String {
        return "Consulta(id=$idConsulta, tipo='$tipoServicio', estado='$estado', costo=${formatearMoneda(costoConsulta)})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Consulta) return false
        return idConsulta == other.idConsulta
    }

    override fun hashCode(): Int {
        return idConsulta.hashCode()
    }
}

