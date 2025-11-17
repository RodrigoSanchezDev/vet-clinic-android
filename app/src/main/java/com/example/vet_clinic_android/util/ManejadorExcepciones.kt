package com.example.vet_clinic_android.util

/**
 * Manejador centralizado de excepciones
 * Mejora la robustez del sistema con mensajes claros
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 */

object ManejadorExcepciones {

    /**
     * Maneja excepciones de formato numérico con mensaje claro
     */
    fun manejarErrorNumerico(campo: String, valorIngresado: String): String {
        return """
            ❌ ERROR: Formato inválido en campo '$campo'
            Valor ingresado: '$valorIngresado'
            Se esperaba: Un número válido
            Tip: Ingrese solo dígitos (ejemplo: 3 o 15.5)
        """.trimIndent()
    }

    /**
     * Maneja excepciones de entrada/salida
     */
    fun manejarErrorIO(operacion: String, detalles: String = ""): String {
        return """
            ❌ ERROR DE I/O: Falló la operación '$operacion'
            ${if (detalles.isNotEmpty()) "Detalles: $detalles" else ""}
            Acción sugerida: Verifique permisos o conectividad
        """.trimIndent()
    }

    /**
     * Maneja excepciones genéricas con contexto
     */
    fun manejarErrorGeneral(operacion: String, error: Exception): String {
        return """
            ❌ ERROR INESPERADO: $operacion
            Tipo: ${error::class.simpleName}
            Mensaje: ${error.message ?: "Sin detalles disponibles"}
            Por favor, contacte al administrador del sistema
        """.trimIndent()
    }

    /**
     * Maneja valores nulos de forma segura
     */
    fun manejarValorNulo(campo: String, valorPorDefecto: String): String {
        return """
            ⚠️  ADVERTENCIA: Campo '$campo' está vacío
            Usando valor por defecto: '$valorPorDefecto'
        """.trimIndent()
    }

    /**
     * Valida y retorna resultado de operación Try-Catch
     */
    inline fun <T> ejecutarSeguro(
        operacion: String,
        onError: (Exception) -> T,
        block: () -> T
    ): T {
        return try {
            block()
        } catch (e: NumberFormatException) {
            println(manejarErrorNumerico(operacion, e.message ?: ""))
            onError(e)
        } catch (e: java.io.IOException) {
            println(manejarErrorIO(operacion, e.message ?: ""))
            onError(e)
        } catch (e: Exception) {
            println(manejarErrorGeneral(operacion, e))
            onError(e)
        }
    }
}

