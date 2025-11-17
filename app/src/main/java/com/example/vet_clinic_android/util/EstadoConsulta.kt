package com.example.vet_clinic_android.util

/**
 * Enum para estados de consulta - Tipo seguro
 * Mejora la consistencia y previene errores de strings
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 */
enum class EstadoConsulta(val descripcion: String, val emoji: String) {
    PENDIENTE("Pendiente", "⏳"),
    PROGRAMADA("Programada", "📅"),
    REALIZADA("Realizada", "✅"),
    CANCELADA("Cancelada", "❌"),
    PAGADA("Pagada", "💰"),
    EN_PROCESO("En Proceso", "🔄");

    /**
     * Obtiene la representación formateada del estado
     */
    fun formato(): String = "$emoji $descripcion"

    /**
     * Verifica si el estado es final (no puede cambiar)
     */
    fun esFinal(): Boolean = this in listOf(REALIZADA, CANCELADA, PAGADA)

    /**
     * Verifica si requiere atención
     */
    fun requiereAtencion(): Boolean = this == PENDIENTE

    companion object {
        /**
         * Convierte un string a EstadoConsulta de forma segura
         */
        fun fromString(estado: String): EstadoConsulta {
            return when (estado.trim().lowercase()) {
                "pendiente" -> PENDIENTE
                "programada" -> PROGRAMADA
                "realizada", "completada" -> REALIZADA
                "cancelada" -> CANCELADA
                "pagada" -> PAGADA
                "en proceso", "enproceso" -> EN_PROCESO
                else -> PENDIENTE // Valor por defecto
            }
        }

        /**
         * Obtiene todos los estados disponibles
         */
        fun obtenerTodos(): List<EstadoConsulta> = values().toList()

        /**
         * Obtiene estados que pueden ser asignados manualmente
         */
        fun obtenerAsignables(): List<EstadoConsulta> {
            return listOf(PENDIENTE, PROGRAMADA, EN_PROCESO)
        }
    }
}

