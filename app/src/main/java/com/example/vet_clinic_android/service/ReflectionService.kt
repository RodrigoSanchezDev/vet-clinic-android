package com.example.vet_clinic_android.service

import com.example.vet_clinic_android.annotations.Promocionable
import kotlin.reflect.KClass
import kotlin.reflect.full.*
import kotlin.reflect.jvm.isAccessible

/**
 * Servicio para análisis de clases usando Reflection
 * Permite inspeccionar dinámicamente propiedades, métodos y anotaciones
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 */
class ReflectionService {

    /**
     * Analiza y muestra toda la información de una clase usando reflection
     */
    fun analizarClase(instancia: Any) {
        val kClass = instancia::class

        println("\n╔═══════════════════════════════════════════════════════════════╗")
        println("║           ANÁLISIS DE CLASE CON REFLECTION                    ║")
        println("╚═══════════════════════════════════════════════════════════════╝")
        println()

        mostrarInformacionBasica(kClass)
        mostrarAnotaciones(kClass)
        mostrarPropiedades(instancia, kClass)
        mostrarMetodos(kClass)
        mostrarConstructores(kClass)
    }

    /**
     * Muestra información básica de la clase
     */
    private fun mostrarInformacionBasica(kClass: KClass<*>) {
        println("📦 INFORMACIÓN BÁSICA")
        println("─".repeat(65))
        println("  Nombre simple:     ${kClass.simpleName}")
        println("  Nombre completo:   ${kClass.qualifiedName}")
        println("  Es data class:     ${kClass.isData}")
        println("  Es sealed:         ${kClass.isSealed}")
        println("  Es abstract:       ${kClass.isAbstract}")
        println("  Es final:          ${kClass.isFinal}")
        println("  Es open:           ${kClass.isOpen}")
        println()
    }

    /**
     * Muestra las anotaciones de la clase
     */
    private fun mostrarAnotaciones(kClass: KClass<*>) {
        println("🏷️  ANOTACIONES")
        println("─".repeat(65))

        val anotaciones = kClass.annotations

        if (anotaciones.isEmpty()) {
            println("  ⚠️  No tiene anotaciones")
        } else {
            anotaciones.forEach { anotacion ->
                println("  ✅ ${anotacion.annotationClass.simpleName}")

                // Mostrar detalles de la anotación
                anotacion.annotationClass.members
                    .filter { it.name != "equals" && it.name != "hashCode" && it.name != "toString" }
                    .forEach { member ->
                        try {
                            member.isAccessible = true
                            val valor = member.call(anotacion)
                            println("     - ${member.name}: $valor")
                        } catch (e: Exception) {
                            // Ignorar si no se puede acceder
                        }
                    }
            }
        }
        println()
    }

    /**
     * Muestra las propiedades de la clase
     */
    private fun mostrarPropiedades(instancia: Any, kClass: KClass<*>) {
        println("📋 PROPIEDADES")
        println("─".repeat(65))

        val propiedades = kClass.memberProperties

        if (propiedades.isEmpty()) {
            println("  ⚠️  No tiene propiedades")
        } else {
            println("  Total: ${propiedades.size}")
            println()

            propiedades.forEachIndexed { index, propiedad ->
                try {
                    propiedad.isAccessible = true
                    val valor = propiedad.getter.call(instancia)
                    val tipo = propiedad.returnType
                    val visibilidad = when {
                        propiedad.visibility?.name == "PUBLIC" -> "public"
                        propiedad.visibility?.name == "PRIVATE" -> "private"
                        propiedad.visibility?.name == "PROTECTED" -> "protected"
                        else -> "internal"
                    }

                    println("  ${index + 1}. ${propiedad.name}")
                    println("     Tipo:         $tipo")
                    println("     Visibilidad:  $visibilidad")
                    println("     Es var:       ${propiedad is kotlin.reflect.KMutableProperty<*>}")
                    println("     Valor actual: $valor")
                    println()
                } catch (e: Exception) {
                    println("  ${index + 1}. ${propiedad.name} (no accesible)")
                    println()
                }
            }
        }
    }

    /**
     * Muestra los métodos de la clase
     */
    private fun mostrarMetodos(kClass: KClass<*>) {
        println("🔧 MÉTODOS")
        println("─".repeat(65))

        val metodos = kClass.memberFunctions
            .filter { !it.name.startsWith("component") } // Excluir métodos de data class
            .filter { it.name != "equals" && it.name != "hashCode" && it.name != "toString" }

        if (metodos.isEmpty()) {
            println("  ⚠️  No tiene métodos personalizados")
        } else {
            println("  Total: ${metodos.size}")
            println()

            metodos.forEachIndexed { index, metodo ->
                val parametros = metodo.parameters
                    .drop(1) // Excluir el receptor (this)
                    .joinToString(", ") { "${it.name}: ${it.type}" }

                val visibilidad = when {
                    metodo.visibility?.name == "PUBLIC" -> "public"
                    metodo.visibility?.name == "PRIVATE" -> "private"
                    metodo.visibility?.name == "PROTECTED" -> "protected"
                    else -> "internal"
                }

                println("  ${index + 1}. $visibilidad fun ${metodo.name}($parametros): ${metodo.returnType}")
            }
        }
        println()
    }

    /**
     * Muestra los constructores de la clase
     */
    private fun mostrarConstructores(kClass: KClass<*>) {
        println("🏗️  CONSTRUCTORES")
        println("─".repeat(65))

        val constructores = kClass.constructors

        if (constructores.isEmpty()) {
            println("  ⚠️  No tiene constructores accesibles")
        } else {
            println("  Total: ${constructores.size}")
            println()

            constructores.forEachIndexed { index, constructor ->
                val parametros = constructor.parameters.joinToString(", ") {
                    "${it.name}: ${it.type}"
                }
                println("  ${index + 1}. constructor($parametros)")
            }
        }
        println()
    }

    /**
     * Busca todas las clases con una anotación específica en una lista
     */
    fun <T : Any> filtrarPorAnotacion(
        lista: List<T>,
        anotacion: KClass<out Annotation>
    ): List<T> {
        return lista.filter { item ->
            item::class.annotations.any { it.annotationClass == anotacion }
        }
    }

    /**
     * Obtiene el valor de una propiedad por nombre usando reflection
     */
    fun obtenerValorPropiedad(instancia: Any, nombrePropiedad: String): Any? {
        return try {
            val kClass = instancia::class
            val propiedad = kClass.memberProperties.find { it.name == nombrePropiedad }
            propiedad?.let {
                it.isAccessible = true
                it.getter.call(instancia)
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Lista todas las propiedades de tipo específico
     */
    fun listarPropiedadesPorTipo(instancia: Any, tipo: KClass<*>): List<String> {
        val kClass = instancia::class
        return kClass.memberProperties
            .filter { it.returnType.classifier == tipo }
            .map { it.name }
    }

    /**
     * Genera un reporte completo de las anotaciones @Promocionable encontradas
     */
    fun generarReportePromociones(medicamentos: List<Any>): String {
        val sb = StringBuilder()

        sb.appendLine("\n╔═══════════════════════════════════════════════════════════════╗")
        sb.appendLine("║      REPORTE DE MEDICAMENTOS CON PROMOCIÓN (REFLECTION)      ║")
        sb.appendLine("╚═══════════════════════════════════════════════════════════════╝")
        sb.appendLine()

        var totalConPromocion = 0
        var totalSinPromocion = 0

        medicamentos.forEach { medicamento ->
            val kClass = medicamento::class
            val anotacion = kClass.annotations.filterIsInstance<Promocionable>().firstOrNull()

            val nombre = obtenerValorPropiedad(medicamento, "nombre") ?: "Desconocido"
            val precio = obtenerValorPropiedad(medicamento, "precio") ?: 0.0

            if (anotacion != null) {
                totalConPromocion++
                sb.appendLine("✅ $nombre")
                sb.appendLine("   Precio: $$precio")
                sb.appendLine("   🎁 Descuento: ${anotacion.descuento}%")
                sb.appendLine("   📝 ${anotacion.descripcion}")
                sb.appendLine()
            } else {
                totalSinPromocion++
                sb.appendLine("⚪ $nombre - Sin promoción")
                sb.appendLine()
            }
        }

        sb.appendLine("─".repeat(65))
        sb.appendLine("Total con promoción:    $totalConPromocion")
        sb.appendLine("Total sin promoción:    $totalSinPromocion")
        sb.appendLine("Total medicamentos:     ${medicamentos.size}")

        return sb.toString()
    }

    /**
     * Analiza una clase por nombre (para uso en Android)
     * Retorna información como String
     */
    fun analizarClase(nombreClase: String): String {
        val sb = StringBuilder()

        try {
            val kClass = when (nombreClase) {
                "Mascota" -> com.example.vet_clinic_android.model.Mascota::class
                "Consulta" -> com.example.vet_clinic_android.model.Consulta::class
                "Veterinario" -> com.example.vet_clinic_android.model.Veterinario::class
                "Dueno" -> com.example.vet_clinic_android.model.Dueno::class
                else -> return "Clase no encontrada: $nombreClase"
            }

            sb.appendLine("📦 CLASE: ${kClass.simpleName}")
            sb.appendLine("═".repeat(50))
            sb.appendLine()

            // Información básica
            sb.appendLine("INFORMACIÓN BÁSICA:")
            sb.appendLine("• Nombre completo: ${kClass.qualifiedName}")
            sb.appendLine("• Es data class: ${kClass.isData}")
            sb.appendLine("• Es abstract: ${kClass.isAbstract}")
            sb.appendLine("• Es final: ${kClass.isFinal}")
            sb.appendLine()

            // Propiedades
            val propiedades = kClass.memberProperties
            sb.appendLine("PROPIEDADES (${propiedades.size}):")
            propiedades.take(10).forEach { prop ->
                sb.appendLine("• ${prop.name}: ${prop.returnType}")
            }
            if (propiedades.size > 10) {
                sb.appendLine("... y ${propiedades.size - 10} más")
            }
            sb.appendLine()

            // Métodos
            val metodos = kClass.members.filter { it.name != "equals" && it.name != "hashCode" && it.name != "toString" }
            sb.appendLine("MÉTODOS (${metodos.size}):")
            metodos.take(10).forEach { metodo ->
                sb.appendLine("• ${metodo.name}()")
            }
            if (metodos.size > 10) {
                sb.appendLine("... y ${metodos.size - 10} más")
            }

        } catch (e: Exception) {
            sb.appendLine("Error al analizar: ${e.message}")
        }

        return sb.toString()
    }
}
