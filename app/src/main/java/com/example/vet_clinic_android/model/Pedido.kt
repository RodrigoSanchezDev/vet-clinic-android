package com.example.vet_clinic_android.model

import android.annotation.SuppressLint
import com.example.vet_clinic_android.annotations.Promocionable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Clase Pedido para gestionar compras de medicamentos
 *
 * @property idPedido ID único del pedido
 * @property cliente Cliente que realiza el pedido
 * @property fechaPedido Fecha y hora del pedido
 * @property items Lista de items del pedido
 * @property estado Estado actual del pedido
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 */
@Suppress("NewApi") // Desugaring habilitado para java.time en API 24+
@SuppressLint("NewApi")
class Pedido(
    val idPedido: Int,
    val cliente: String,
    val fechaPedido: LocalDateTime = LocalDateTime.now(),
    private val items: MutableList<ItemPedido> = mutableListOf(),
    var estado: String = "Pendiente"
) {

    /**
     * Agrega un medicamento al pedido
     */
    fun agregarItem(medicamento: Medicamento, cantidad: Int): Boolean {
        return if (medicamento.hayStock(cantidad)) {
            val item = ItemPedido(medicamento, cantidad)
            items.add(item)
            true
        } else {
            false
        }
    }

    /**
     * Obtiene todos los items del pedido
     */
    fun obtenerItems(): List<ItemPedido> = items.toList()

    /**
     * Calcula el subtotal sin descuentos
     */
    fun calcularSubtotal(): Double {
        return items.sumOf { it.calcularSubtotal() }
    }

    /**
     * Calcula el total de descuentos aplicados
     */
    fun calcularDescuentos(): Double {
        return items.sumOf { it.calcularDescuento() }
    }

    /**
     * Calcula el total final con descuentos
     */
    fun calcularTotal(): Double {
        return calcularSubtotal() - calcularDescuentos()
    }

    /**
     * Cuenta cuántos items tienen promoción
     */
    fun contarItemsConPromocion(): Int {
        return items.count { it.tienePromocion() }
    }

    /**
     * Procesa el pedido (reduce stock)
     */
    fun procesar(): Boolean {
        // Verificar que todos los items tengan stock
        if (!items.all { it.medicamento.hayStock(it.cantidad) }) {
            return false
        }

        // Reducir stock de cada item
        items.forEach { it.medicamento.vender(it.cantidad) }
        estado = "Procesado"
        return true
    }

    /**
     * Genera un resumen del pedido
     */
    @Suppress("NewApi")
    @SuppressLint("NewApi")
    fun generarResumen(): String {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        val sb = StringBuilder()

        sb.appendLine("╔═══════════════════════════════════════════════════════════════╗")
        sb.appendLine("║                    PEDIDO #$idPedido                          ")
        sb.appendLine("╚═══════════════════════════════════════════════════════════════╝")
        sb.appendLine("Cliente:      $cliente")
        sb.appendLine("Fecha:        ${fechaPedido.format(formatter)}")
        sb.appendLine("Estado:       $estado")
        sb.appendLine()
        sb.appendLine("ITEMS:")
        sb.appendLine("─".repeat(65))

        items.forEachIndexed { index, item ->
            sb.appendLine("${index + 1}. ${item.generarDetalle()}")
        }

        sb.appendLine("─".repeat(65))
        sb.appendLine("Subtotal:     $${String.format("%,.0f", calcularSubtotal())}")
        sb.appendLine("Descuentos:   -$${String.format("%,.0f", calcularDescuentos())}")
        sb.appendLine("═".repeat(65))
        sb.appendLine("TOTAL:        $${String.format("%,.0f", calcularTotal())}")
        sb.appendLine()
        sb.appendLine("Items con promoción: ${contarItemsConPromocion()}/${items.size}")

        return sb.toString()
    }

    override fun toString(): String {
        return "Pedido(id=$idPedido, cliente='$cliente', items=${items.size}, total=$${calcularTotal()})"
    }

    /**
     * DESESTRUCTURACIÓN: Operadores component para destructuring declarations
     * Permite extraer cliente, items y total directamente
     * Ejemplo: val (cliente, productos, total) = pedido
     */
    operator fun component1(): String = cliente
    operator fun component2(): List<ItemPedido> = obtenerItems()
    operator fun component3(): Double = calcularTotal()

    /**
     * OPERATOR OVERLOADING: Sobrecarga del operador +
     * Combina dos pedidos en uno solo, sumando los productos y recalculando el total
     *
     * @param otro El otro pedido a combinar
     * @return Un nuevo pedido combinado
     */
    @Suppress("NewApi")
    @SuppressLint("NewApi")
    operator fun plus(otro: Pedido): Pedido {
        // Generar nuevo ID para el pedido combinado
        val nuevoId = (idPedido + otro.idPedido) / 2

        // Combinar nombres de clientes
        val clienteCombinado = if (cliente == otro.cliente) {
            cliente
        } else {
            "$cliente + ${otro.cliente}"
        }

        // Crear nuevo pedido combinado
        val pedidoCombinado = Pedido(
            idPedido = nuevoId,
            cliente = clienteCombinado,
            fechaPedido = LocalDateTime.now(),
            estado = "Pendiente"
        )

        // Agregar todos los items del primer pedido
        this.items.forEach { item ->
            pedidoCombinado.items.add(
                ItemPedido(item.medicamento, item.cantidad)
            )
        }

        // Agregar todos los items del segundo pedido
        otro.items.forEach { item ->
            // Verificar si el medicamento ya existe en el pedido combinado
            val itemExistente = pedidoCombinado.items.find {
                it.medicamento.nombre == item.medicamento.nombre
            }

            if (itemExistente != null) {
                // Si existe, crear nuevo item con cantidad sumada
                val index = pedidoCombinado.items.indexOf(itemExistente)
                val nuevaCantidad = itemExistente.cantidad + item.cantidad
                pedidoCombinado.items[index] = ItemPedido(item.medicamento, nuevaCantidad)
            } else {
                // Si no existe, agregar el item
                pedidoCombinado.items.add(
                    ItemPedido(item.medicamento, item.cantidad)
                )
            }
        }

        return pedidoCombinado
    }
}

/**
 * Representa un item individual del pedido
 */
data class ItemPedido(
    val medicamento: Medicamento,
    val cantidad: Int
) {
    /**
     * Verifica si el medicamento tiene promoción usando reflection
     */
    fun tienePromocion(): Boolean {
        return medicamento::class.annotations.any { it is Promocionable }
    }

    /**
     * Obtiene el descuento del medicamento usando reflection
     */
    fun obtenerDescuento(): Int {
        val anotacion = medicamento::class.annotations
            .filterIsInstance<Promocionable>()
            .firstOrNull()
        return anotacion?.descuento ?: 0
    }

    /**
     * Calcula el subtotal sin descuento
     */
    fun calcularSubtotal(): Double {
        return medicamento.precio * cantidad
    }

    /**
     * Calcula el monto del descuento
     */
    fun calcularDescuento(): Double {
        val porcentaje = obtenerDescuento()
        return if (porcentaje > 0) {
            calcularSubtotal() * (porcentaje / 100.0)
        } else {
            0.0
        }
    }

    /**
     * Calcula el total con descuento
     */
    fun calcularTotal(): Double {
        return calcularSubtotal() - calcularDescuento()
    }

    /**
     * Genera detalle del item
     */
    fun generarDetalle(): String {
        val sb = StringBuilder()
        sb.append("${medicamento.nombre} x$cantidad")
        sb.append(" - $${String.format("%,.0f", calcularSubtotal())}")

        if (tienePromocion()) {
            val descuento = obtenerDescuento()
            sb.append(" 🎁 ($descuento% OFF)")
            sb.append(" = $${String.format("%,.0f", calcularTotal())}")
        }

        return sb.toString()
    }
}

