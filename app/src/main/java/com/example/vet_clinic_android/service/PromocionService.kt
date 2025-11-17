package com.example.vet_clinic_android.service

import android.annotation.SuppressLint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.example.vet_clinic_android.util.formatearMoneda

/**
 * Servicio para gestión de promociones usando RANGES
 * Implementa rangos de fechas y cantidades según requisitos
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 */
@SuppressLint("NewApi") // Desugaring habilitado para java.time en API 24+
class PromocionService {

    // ==================== RANGOS DE FECHAS PARA PROMOCIONES ====================

    /**
     * Define rangos de fechas para diferentes promociones
     * Estructura: Nombre -> Rango de fechas
     */
    private val promocionesActivas = mapOf(
        "Black Friday Veterinaria" to (LocalDate.of(2025, 11, 20)..LocalDate.of(2025, 11, 30)),
        "Navidad Pet Friendly" to (LocalDate.of(2025, 12, 15)..LocalDate.of(2025, 12, 31)),
        "Año Nuevo Saludable" to (LocalDate.of(2025, 12, 26)..LocalDate.of(2026, 1, 10)),
        "Día del Animal" to (LocalDate.of(2025, 4, 25)..LocalDate.of(2025, 5, 5)),
        "Mes del Cachorro" to (LocalDate.of(2025, 6, 1)..LocalDate.of(2025, 6, 30))
    )

    /**
     * Descuentos por promoción (en porcentaje)
     */
    private val descuentosPromocion = mapOf(
        "Black Friday Veterinaria" to 30.0,
        "Navidad Pet Friendly" to 20.0,
        "Año Nuevo Saludable" to 25.0,
        "Día del Animal" to 15.0,
        "Mes del Cachorro" to 10.0
    )

    /**
     * Verifica si una fecha está dentro de un rango de promoción
     * Usa RANGES para comparación de fechas
     */
    fun verificarPromocionActiva(fecha: LocalDate): Pair<String?, Double> {
        for ((nombrePromocion, rangoFechas) in promocionesActivas) {
            // Usar operador IN para verificar si está en el RANGE
            if (fecha in rangoFechas) {
                val descuento = descuentosPromocion[nombrePromocion] ?: 0.0
                return Pair(nombrePromocion, descuento)
            }
        }
        return Pair(null, 0.0)
    }

    /**
     * Verifica promoción usando fecha en formato string
     */
    fun verificarPromocionActiva(fechaStr: String): Pair<String?, Double> {
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val fecha = LocalDate.parse(fechaStr, formatter)
            verificarPromocionActiva(fecha)
        } catch (e: Exception) {
            println("❌ ERROR: Formato de fecha inválido. Use dd/MM/yyyy")
            Pair(null, 0.0)
        }
    }

    /**
     * Aplica descuento por promoción al costo
     * Usa RANGE de fechas para verificar elegibilidad
     */
    fun aplicarDescuentoPromocion(costoOriginal: Double, fecha: LocalDate): Double {
        val (nombrePromocion, porcentajeDescuento) = verificarPromocionActiva(fecha)

        return if (nombrePromocion != null && porcentajeDescuento > 0) {
            val descuento = costoOriginal * (porcentajeDescuento / 100)
            val costoFinal = costoOriginal - descuento

            println("\n🎉 ¡PROMOCIÓN ACTIVA!")
            println("   Promoción:       $nombrePromocion")
            println("   Descuento:       ${porcentajeDescuento.toInt()}%")
            println("   Ahorro:          ${formatearMoneda(descuento)}")
            println("   Costo original:  ${formatearMoneda(costoOriginal)}")
            println("   Costo final:     ${formatearMoneda(costoFinal)}")

            costoFinal
        } else {
            println("\n💡 No hay promociones activas para esta fecha")
            costoOriginal
        }
    }

    /**
     * Muestra todas las promociones disponibles y sus rangos de fechas
     */
    fun mostrarPromocionesDisponibles() {
        println("\n╔═══════════════════════════════════════════════════════════════╗")
        println("║              PROMOCIONES DISPONIBLES                          ║")
        println("╚═══════════════════════════════════════════════════════════════╝")

        if (promocionesActivas.isEmpty()) {
            println("⚠️  No hay promociones activas en este momento")
            return
        }

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        var numero = 1

        promocionesActivas.forEach { (nombre, rango) ->
            val descuento = descuentosPromocion[nombre] ?: 0.0
            val fechaInicio = rango.start.format(formatter)
            val fechaFin = rango.endInclusive.format(formatter)

            println("$numero. $nombre")
            println("   Periodo:   $fechaInicio - $fechaFin")
            println("   Descuento: ${descuento.toInt()}%")
            println()
            numero++
        }
    }

    /**
     * Verifica si una fecha está dentro de un periodo personalizado
     * Usa RANGE para fechas
     */
    fun estaEnPeriodo(fecha: LocalDate, inicio: LocalDate, fin: LocalDate): Boolean {
        val rangoPersonalizado = inicio..fin
        return fecha in rangoPersonalizado
    }

    // ==================== RANGOS DE CANTIDADES ====================

    /**
     * Rango permitido para cantidad de productos/servicios
     */
    private val rangoCantidadPermitida: IntRange = 1..100

    /**
     * Rango para descuento por volumen (más de 5 unidades)
     */
    private val rangoDescuentoVolumen: IntRange = 6..100

    /**
     * Rango para alerta de stock bajo
     */
    private val rangoStockBajo: IntRange = 1..10

    /**
     * Valida si una cantidad está dentro del rango permitido
     * Usa RANGES para validación
     */
    fun validarCantidad(cantidad: Int): Boolean {
        return if (cantidad in rangoCantidadPermitida) {
            println("✅ Cantidad válida: $cantidad unidad(es)")
            true
        } else {
            println("❌ ERROR: Cantidad fuera de rango")
            println("   Valor ingresado: $cantidad")
            println("   Rango permitido: ${rangoCantidadPermitida.first}-${rangoCantidadPermitida.last}")

            when {
                cantidad < rangoCantidadPermitida.first -> {
                    println("   Motivo: La cantidad debe ser al menos ${rangoCantidadPermitida.first}")
                }
                cantidad > rangoCantidadPermitida.last -> {
                    println("   Motivo: La cantidad máxima permitida es ${rangoCantidadPermitida.last}")
                }
            }
            false
        }
    }

    /**
     * Verifica si aplica descuento por volumen
     * Usa RANGE para determinar elegibilidad
     */
    fun aplicarDescuentoVolumen(cantidad: Int, precioUnitario: Double): Pair<Double, String> {
        if (!validarCantidad(cantidad)) {
            return Pair(0.0, "Cantidad inválida")
        }

        val costoTotal = precioUnitario * cantidad

        return if (cantidad in rangoDescuentoVolumen) {
            val porcentajeDescuento = when (cantidad) {
                in 6..10 -> 5.0   // 5% de descuento
                in 11..25 -> 10.0 // 10% de descuento
                in 26..50 -> 15.0 // 15% de descuento
                in 51..100 -> 20.0 // 20% de descuento
                else -> 0.0
            }

            val descuento = costoTotal * (porcentajeDescuento / 100)
            val costoFinal = costoTotal - descuento

            val mensaje = buildString {
                appendLine("\n🎁 ¡DESCUENTO POR VOLUMEN!")
                appendLine("   Cantidad:        $cantidad unidad(es)")
                appendLine("   Descuento:       ${porcentajeDescuento.toInt()}%")
                appendLine("   Precio unitario: ${formatearMoneda(precioUnitario)}")
                appendLine("   Subtotal:        ${formatearMoneda(costoTotal)}")
                appendLine("   Ahorro:          ${formatearMoneda(descuento)}")
                appendLine("   Total final:     ${formatearMoneda(costoFinal)}")
            }

            Pair(costoFinal, mensaje)
        } else {
            val mensaje = "\n💰 Costo total: ${formatearMoneda(costoTotal)}"
            Pair(costoTotal, mensaje)
        }
    }

    /**
     * Verifica si el stock está bajo usando RANGE
     */
    fun verificarStockBajo(cantidadDisponible: Int): Boolean {
        return cantidadDisponible in rangoStockBajo
    }

    /**
     * Evalúa y muestra el estado del stock
     */
    fun evaluarStock(cantidadDisponible: Int) {
        when {
            cantidadDisponible !in rangoCantidadPermitida -> {
                println("❌ Stock inválido: $cantidadDisponible")
            }
            cantidadDisponible in rangoStockBajo -> {
                println("⚠️  ALERTA: Stock bajo - $cantidadDisponible unidades")
                println("   Se recomienda realizar un nuevo pedido")
            }
            cantidadDisponible in 11..50 -> {
                println("✅ Stock normal: $cantidadDisponible unidades")
            }
            cantidadDisponible in 51..100 -> {
                println("✅ Stock óptimo: $cantidadDisponible unidades")
            }
        }
    }

    /**
     * Calcula precio con descuentos combinados: promoción + volumen
     * Usa múltiples RANGES
     */
    fun calcularPrecioConDescuentosCompletos(
        precioUnitario: Double,
        cantidad: Int,
        fechaCompra: LocalDate
    ): Double {
        // Validar cantidad con RANGE
        if (!validarCantidad(cantidad)) {
            return 0.0
        }

        var costoTotal = precioUnitario * cantidad
        println("\n╔═══════════════════════════════════════════════════════════════╗")
        println("║           CÁLCULO DE PRECIO CON DESCUENTOS                    ║")
        println("╚═══════════════════════════════════════════════════════════════╝")
        println("Precio unitario: ${formatearMoneda(precioUnitario)}")
        println("Cantidad:        $cantidad unidad(es)")
        println("Subtotal:        ${formatearMoneda(costoTotal)}")

        // Aplicar descuento por promoción (usando RANGE de fechas)
        val (nombrePromocion, porcentajePromocion) = verificarPromocionActiva(fechaCompra)
        if (nombrePromocion != null && porcentajePromocion > 0) {
            val descuentoPromo = costoTotal * (porcentajePromocion / 100)
            costoTotal -= descuentoPromo
            println("\n✅ Descuento por promoción ($nombrePromocion): -${formatearMoneda(descuentoPromo)}")
        }

        // Aplicar descuento por volumen (usando RANGE de cantidad)
        if (cantidad in rangoDescuentoVolumen) {
            val porcentajeVolumen = when (cantidad) {
                in 6..10 -> 5.0
                in 11..25 -> 10.0
                in 26..50 -> 15.0
                in 51..100 -> 20.0
                else -> 0.0
            }
            val descuentoVolumen = (precioUnitario * cantidad) * (porcentajeVolumen / 100)
            costoTotal -= descuentoVolumen
            println("✅ Descuento por volumen (${porcentajeVolumen.toInt()}%): -${formatearMoneda(descuentoVolumen)}")
        }

        println("\n╔═══════════════════════════════════════════════════════════════╗")
        println("║  TOTAL A PAGAR: ${formatearMoneda(costoTotal).padEnd(44)}║")
        println("╚═══════════════════════════════════════════════════════════════╝")

        return costoTotal
    }

    /**
     * Verifica si un número de pedido está en rango válido
     */
    fun validarNumeroPedido(numero: Int): Boolean {
        val rangoPedidos = 1000..9999
        return numero in rangoPedidos
    }

    /**
     * Muestra información de rangos configurados
     */
    fun mostrarInformacionRangos() {
        println("\n╔═══════════════════════════════════════════════════════════════╗")
        println("║           INFORMACIÓN DE RANGOS DEL SISTEMA                   ║")
        println("╚═══════════════════════════════════════════════════════════════╝")
        println("\n📦 RANGO DE CANTIDADES:")
        println("   Permitido:         ${rangoCantidadPermitida.first}-${rangoCantidadPermitida.last} unidades")
        println("   Descuento volumen: ${rangoDescuentoVolumen.first}-${rangoDescuentoVolumen.last} unidades")
        println("   Alerta stock bajo: ${rangoStockBajo.first}-${rangoStockBajo.last} unidades")

        println("\n📅 PROMOCIONES ACTIVAS:")
        promocionesActivas.forEach { (nombre, rango) ->
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val descuento = descuentosPromocion[nombre] ?: 0.0
            println("   • $nombre")
            println("     ${rango.start.format(formatter)} al ${rango.endInclusive.format(formatter)} (${descuento.toInt()}%)")
        }
    }

    /**
     * Valida cantidad de productos y retorna mensaje
     * Para uso en interfaz Android
     */
    fun validarCantidadProductos(cantidad: Int): String {
        return when {
            cantidad in 0..10 -> "Stock Bajo ⚠️\nSe recomienda realizar nuevo pedido"
            cantidad in 11..50 -> "Stock Medio ✓\nNivel aceptable de inventario"
            cantidad in 51..100 -> "Stock Bueno ✓\nNivel óptimo de inventario"
            cantidad > 100 -> "Stock Excelente ✓\nInventario en nivel excelente"
            else -> "Cantidad inválida"
        }
    }
}
