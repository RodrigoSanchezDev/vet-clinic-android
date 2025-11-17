package com.example.vet_clinic_android.util

/**
 * Centralización de mensajes del sistema
 * Facilita mantenimiento y consistencia
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 */
object Mensajes {

    // ==================== ENCABEZADOS ====================
    const val TITULO_SISTEMA = """
╔═══════════════════════════════════════════════════════════════╗
║                                                               ║
║       SISTEMA DE GESTIÓN DE CLÍNICA VETERINARIA              ║
║              Santiago, Chile                                  ║
║                                                               ║
╚═══════════════════════════════════════════════════════════════╝
"""

    const val MENU_PRINCIPAL = """
╔═══════════════════════════════════════╗
║         MENÚ PRINCIPAL                ║
╚═══════════════════════════════════════╝
1. Registrar nueva consulta
2. Ver informe de todas las consultas
3. Ver consultas pendientes
4. Ver consultas programadas
5. Ver estadísticas del sistema
6. Ver agenda de veterinarios
7. Ver estadísticas de veterinarios
8. Buscar veterinario por especialidad
9. Salir
"""

    const val DESPEDIDA = """
╔═══════════════════════════════════════════════════════════════╗
║  Gracias por usar el Sistema de Gestión Veterinaria          ║
╚═══════════════════════════════════════════════════════════════╝
"""

    // ==================== SECCIONES ====================
    fun seccion(titulo: String): String = """
╔═══════════════════════════════════════════════════════════════╗
║ ${titulo.padEnd(61)} ║
╚═══════════════════════════════════════════════════════════════╝
"""

    fun subseccion(titulo: String): String = """
╔═══════════════════════════════════════╗
║ ${titulo.padEnd(37)} ║
╚═══════════════════════════════════════╝
"""

    // ==================== ERRORES ====================
    const val ERROR_OPCION_INVALIDA = "⚠️  Opción inválida. Intente nuevamente."
    const val ERROR_DATO_VACIO = "❌ El valor no puede estar vacío. Intente nuevamente."
    const val ERROR_NUMERO_INVALIDO = "❌ ERROR: Ingrese un número válido"
    const val ERROR_FORMATO_FECHA = "❌ ERROR: Formato de fecha inválido. Use dd/MM/yyyy HH:mm"
    const val ERROR_FORMATO_EMAIL = "⚠️  Email inválido. Usando valor predeterminado: correo@invalido.com"

    fun errorRango(min: Int, max: Int): String =
        "❌ ERROR: El número debe estar entre $min y $max"

    fun errorCampo(campo: String, valorIngresado: String, ejemplo: String): String = """
❌ ERROR: Formato inválido en campo '$campo'
   Valor ingresado: '$valorIngresado'
   Ejemplo válido: $ejemplo
"""

    // ==================== ÉXITO ====================
    const val EXITO_REGISTRO = "✅ Registro completado exitosamente"
    const val EXITO_ACTUALIZACION = "✅ Actualización realizada correctamente"
    const val EXITO_ELIMINACION = "✅ Eliminación completada"

    fun exitoEstado(estadoAnterior: String, estadoNuevo: String): String =
        "✅ Estado actualizado: $estadoAnterior → $estadoNuevo"

    // ==================== ADVERTENCIAS ====================
    const val ADVERTENCIA_VACUNA_URGENTE = "⚠️  ATENCIÓN: Esta mascota necesita vacunación urgente"
    const val ADVERTENCIA_CAMPO_OPCIONAL = "ℹ️  Campo opcional - presione Enter para omitir"

    fun advertenciaValorDefecto(campo: String, valor: String): String =
        "⚠️  ADVERTENCIA: Campo '$campo' vacío. Usando valor por defecto: '$valor'"

    // ==================== INFORMACIÓN ====================
    fun totalRegistros(tipo: String, cantidad: Int): String =
        "✅ Total de $tipo en el sistema: $cantidad"

    fun registroEncontrado(tipo: String, nombre: String): String =
        "✅ $tipo encontrado: $nombre"

    fun sinResultados(tipo: String): String =
        "⚠️  No se encontraron $tipo"

    // ==================== PROMPTS ====================
    const val PROMPT_SELECCIONAR_OPCION = "\nSeleccione una opción: "
    const val PROMPT_CONTINUAR = "\n¿Desea continuar? (s/n): "
    const val PROMPT_CONFIRMAR = "¿Está seguro? (s/n): "

    // ==================== SEPARADORES ====================
    val SEPARADOR = "═".repeat(65)
    val LINEA = "─".repeat(65)
    const val ESPACIO = "\n"

    fun lineaTitulo(titulo: String): String = """
$SEPARADOR
  $titulo
$SEPARADOR
"""
}

