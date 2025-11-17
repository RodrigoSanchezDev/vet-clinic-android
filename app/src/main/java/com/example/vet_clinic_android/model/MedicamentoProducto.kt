package com.example.vet_clinic_android.model

import com.example.vet_clinic_android.annotations.Promocionable

/**
 * Clase base para Medicamentos
 *
 * @property nombre Nombre del medicamento
 * @property precio Precio base del medicamento
 * @property stock Cantidad disponible en inventario
 * @property descripcion Descripción del medicamento
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 */
open class Medicamento(
    val nombre: String,
    var precio: Double,
    var stock: Int,
    val descripcion: String = "",
    val dosificacion: String = "Estándar"
) {
    /**
     * Verifica si hay stock disponible
     */
    fun hayStock(cantidad: Int = 1): Boolean {
        return stock >= cantidad
    }

    /**
     * Reduce el stock cuando se vende
     */
    fun vender(cantidad: Int): Boolean {
        return if (hayStock(cantidad)) {
            stock -= cantidad
            true
        } else {
            false
        }
    }

    /**
     * Aumenta el stock cuando se reabastece
     */
    fun reabastecer(cantidad: Int) {
        stock += cantidad
    }

    override fun toString(): String {
        return "$nombre - $${precio} (Stock: $stock) - Dosificación: $dosificacion"
    }

    /**
     * OPERATOR OVERLOADING: Sobrecarga del operador ==
     * Compara si dos medicamentos son iguales basándose en nombre y dosificación
     *
     * @param other El otro objeto a comparar
     * @return true si son el mismo medicamento, false en caso contrario
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Medicamento) return false

        return nombre.equals(other.nombre, ignoreCase = true) &&
               dosificacion.equals(other.dosificacion, ignoreCase = true)
    }

    /**
     * HashCode consistente con equals
     */
    override fun hashCode(): Int {
        var result = nombre.lowercase().hashCode()
        result = 31 * result + dosificacion.lowercase().hashCode()
        return result
    }
}

/**
 * Medicamento con promoción especial
 * Anotado con @Promocionable para descuento del 15%
 */
@Promocionable(descuento = 15, descripcion = "Antipulgas en promoción - 15% descuento")
class Antipulgas(
    precio: Double = 8000.0,
    stock: Int = 50
) : Medicamento(
    nombre = "Antipulgas Premium",
    precio = precio,
    stock = stock,
    descripcion = "Tratamiento antipulgas de acción prolongada",
    dosificacion = "Aplicar cada 30 días"
)

/**
 * Medicamento con promoción especial
 * Anotado con @Promocionable para descuento del 20%
 */
@Promocionable(descuento = 20, descripcion = "Desparasitante en oferta - 20% descuento")
class Desparasitante(
    precio: Double = 12000.0,
    stock: Int = 30
) : Medicamento(
    nombre = "Desparasitante Total",
    precio = precio,
    stock = stock,
    descripcion = "Desparasitante de amplio espectro",
    dosificacion = "Cada 3 meses según peso"
)

/**
 * Medicamento con promoción especial
 * Anotado con @Promocionable para descuento del 10%
 */
@Promocionable(descuento = 10, descripcion = "Vitaminas con 10% descuento")
class Vitaminas(
    precio: Double = 15000.0,
    stock: Int = 40
) : Medicamento(
    nombre = "Complejo Vitamínico",
    precio = precio,
    stock = stock,
    descripcion = "Vitaminas para mascotas de todas las edades",
    dosificacion = "1 tableta diaria"
)

/**
 * Medicamento SIN promoción (no tiene anotación @Promocionable)
 */
class Antibiotico(
    precio: Double = 25000.0,
    stock: Int = 20
) : Medicamento(
    nombre = "Antibiótico Veterinario",
    precio = precio,
    stock = stock,
    descripcion = "Antibiótico de uso veterinario",
    dosificacion = "Cada 12 horas por 7 días"
)

/**
 * Medicamento SIN promoción
 */
class Analgesico(
    precio: Double = 18000.0,
    stock: Int = 35
) : Medicamento(
    nombre = "Analgésico Canino",
    precio = precio,
    stock = stock,
    descripcion = "Para alivio del dolor en perros",
    dosificacion = "Cada 8 horas según peso"
)

