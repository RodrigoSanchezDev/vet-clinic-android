
package com.example.vet_clinic_android.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Utilidades para formateo de datos
 * UNIFICA formatos de moneda, fecha/hora y otros datos
 * Consistencia mejorada según recomendaciones del profesor
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 */
object Formateo {

    // ==================== FORMATOS DE FECHA/HORA ====================
    private val localeChileno = Locale.Builder().setLanguage("es").setRegion("CL").build()
    private val formatoFechaHora = SimpleDateFormat("dd/MM/yyyy HH:mm", localeChileno)
    private val formatoFecha = SimpleDateFormat("dd/MM/yyyy", localeChileno)
    private val formatoHora = SimpleDateFormat("HH:mm", localeChileno)
    private val formatoFechaCorta = SimpleDateFormat("dd/MM/yy", localeChileno)

    /**
     * Formatea fecha y hora completa (FORMATO UNIFICADO)
     * Ejemplo: 09/11/2025 14:30
     */
    fun formatearFechaHora(fecha: Date = Date()): String {
        return formatoFechaHora.format(fecha)
    }

    /**
     * Formatea solo fecha (FORMATO UNIFICADO)
     * Ejemplo: 09/11/2025
     */
    fun formatearFecha(fecha: Date = Date()): String {
        return formatoFecha.format(fecha)
    }

    /**
     * Formatea solo hora (FORMATO UNIFICADO)
     * Ejemplo: 14:30
     */
    fun formatearHora(fecha: Date = Date()): String {
        return formatoHora.format(fecha)
    }

    /**
     * Formatea fecha corta
     * Ejemplo: 09/11/25
     */
    fun formatearFechaCorta(fecha: Date = Date()): String {
        return formatoFechaCorta.format(fecha)
    }

    /**
     * Parsea string a Date con manejo robusto
     */
    fun parsearFechaHora(fechaStr: String): Date? {
        return try {
            formatoFechaHora.parse(fechaStr)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Obtiene fecha y hora actual formateada
     */
    fun obtenerFechaHoraActual(): String = formatearFechaHora()

    /**
     * Obtiene fecha actual formateada
     */
    fun obtenerFechaActual(): String = formatearFecha()

    // ==================== FORMATO DE MONEDA ====================

    /**
     * Formatea moneda chilena (FORMATO UNIFICADO)
     * Ejemplo: CLP $25.000
     */
    fun formatearMoneda(monto: Double): String {
        return "CLP $${formatearNumeroConSeparadores(monto)}"
    }

    /**
     * Formatea moneda simplificada
     * Ejemplo: $25.000
     */
    fun formatearMonedaSimple(monto: Double): String {
        return "$${formatearNumeroConSeparadores(monto)}"
    }

    /**
     * Formatea número con separadores de miles
     * Ejemplo: 25000 -> 25.000
     */
    private fun formatearNumeroConSeparadores(numero: Double): String {
        return String.format(localeChileno, "%,.0f", numero)
    }

    /**
     * Formatea porcentaje
     * Ejemplo: 0.15 -> 15%
     */
    fun formatearPorcentaje(decimal: Double): String {
        return String.format("%.0f%%", decimal * 100)
    }

    // ==================== FORMATO DE TELÉFONO ====================
    // NOTA: formatearTelefono está definido en Validaciones.kt para evitar duplicación

    /**
     * Formatea teléfono simple sin código de país
     */
    fun formatearTelefonoSimple(telefono: String): String {
        val limpio = telefono.replace(Regex("[^0-9]"), "")
        return when {
            limpio.length == 9 -> "${limpio.substring(0, 1)} ${limpio.substring(1, 5)} ${limpio.substring(5)}"
            limpio.length == 8 -> "${limpio.substring(0, 4)} ${limpio.substring(4)}"
            else -> limpio
        }
    }

    // ==================== FORMATO DE EMAIL ====================

    /**
     * Formatea email a minúsculas y sin espacios
     */
    fun formatearEmail(email: String): String {
        return email.trim().lowercase()
    }

    /**
     * Valida y formatea email
     */
    fun formatearEmailValidado(email: String?): String {
        if (email.isNullOrBlank()) return "correo@invalido.com"
        val limpio = formatearEmail(email)
        return if (validarEmail(limpio)) limpio else "correo@invalido.com"
    }

    private fun validarEmail(email: String): Boolean {
        val partes = email.split("@")
        if (partes.size != 2) return false
        if (partes[0].isEmpty() || partes[1].isEmpty()) return false
        return partes[1].contains(".")
    }

    // ==================== FORMATO DE TEXTO ====================

    /**
     * Capitaliza primera letra de cada palabra
     */
    fun capitalizarPalabras(texto: String): String {
        return texto.split(" ")
            .joinToString(" ") { palabra ->
                palabra.lowercase().replaceFirstChar { it.uppercase() }
            }
    }

    /**
     * Capitaliza solo primera letra
     */
    fun capitalizar(texto: String): String {
        return texto.lowercase().replaceFirstChar { it.uppercase() }
    }

    /**
     * Trunca texto con elipsis
     */
    fun truncar(texto: String, maxLength: Int): String {
        return if (texto.length > maxLength) {
            "${texto.take(maxLength - 3)}..."
        } else {
            texto
        }
    }

    // ==================== FORMATO DE RUT CHILENO ====================

    /**
     * Formatea RUT chileno
     * Ejemplo: 12345678-9
     */
    fun formatearRut(rut: String): String {
        val limpio = rut.replace(Regex("[^0-9kK]"), "")
        if (limpio.length < 2) return rut

        val cuerpo = limpio.dropLast(1)
        val dv = limpio.last()

        val formateado = StringBuilder()
        cuerpo.reversed().forEachIndexed { index, char ->
            if (index > 0 && index % 3 == 0) {
                formateado.append(".")
            }
            formateado.append(char)
        }

        return "${formateado.reverse()}-$dv"
    }

    // ==================== FORMATO DE DURACIÓN ====================

    /**
     * Formatea minutos a horas y minutos
     * Ejemplo: 90 minutos -> 1h 30m
     */
    fun formatearDuracion(minutos: Int): String {
        val horas = minutos / 60
        val mins = minutos % 60

        return when {
            horas > 0 && mins > 0 -> "${horas}h ${mins}m"
            horas > 0 -> "${horas}h"
            else -> "${mins}m"
        }
    }
}

// Funciones de compatibilidad global (mantener funciones anteriores)
fun formatearMoneda(monto: Double): String = Formateo.formatearMoneda(monto)
// formatearTelefono se encuentra en Validaciones.kt para evitar duplicación

