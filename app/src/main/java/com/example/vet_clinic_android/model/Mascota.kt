package com.example.vet_clinic_android.model

/**
 * Clase Mascota con métodos POO
 * Define atributos y comportamientos de una mascota
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 */
class Mascota(
    val nombre: String,
    val especie: String,
    var edad: Int,
    var peso: Double,
    val raza: String = "Mestizo",
    val color: String = "",
    val sexo: String = ""
) {
    // Atributo calculado
    val esAdulto: Boolean
        get() = edad >= 1

    val esSenior: Boolean
        get() = when (especie.lowercase()) {
            "perro" -> edad > 7
            "gato" -> edad > 10
            else -> edad > 5
        }

    /**
     * Método para mostrar información ordenada de la mascota
     * Cumple con requisito de POO
     */
    fun mostrarInformacion(): String {
        return """
            ╔═══════════════════════════════════════╗
            ║   INFORMACIÓN DE LA MASCOTA           ║
            ╚═══════════════════════════════════════╝
            Nombre:    $nombre
            Especie:   $especie
            Raza:      $raza
            Edad:      $edad año(s) ${obtenerCategoriaEdad()}
            Peso:      $peso kg ${obtenerCategoriaPeso()}
            ${if (color.isNotEmpty()) "Color:     $color" else ""}
            ${if (sexo.isNotEmpty()) "Sexo:      $sexo" else ""}
        """.trimIndent()
    }

    /**
     * Método para obtener categoría de edad
     */
    fun obtenerCategoriaEdad(): String {
        return when {
            edad < 1 -> "(Cachorro/Joven)"
            esSenior -> "(Senior)"
            else -> "(Adulto)"
        }
    }

    /**
     * Método para obtener categoría de peso
     */
    fun obtenerCategoriaPeso(): String {
        return when (especie.lowercase()) {
            "perro" -> when {
                peso < 5 -> "(Pequeño)"
                peso in 5.0..20.0 -> "(Mediano)"
                peso > 20 -> "(Grande)"
                else -> ""
            }
            "gato" -> when {
                peso < 3 -> "(Pequeño)"
                peso in 3.0..5.0 -> "(Normal)"
                peso > 5 -> "(Grande)"
                else -> ""
            }
            else -> ""
        }
    }

    /**
     * Método para actualizar edad
     */
    fun cumplirAnios() {
        edad++
    }

    /**
     * Método para actualizar peso
     */
    fun actualizarPeso(nuevoPeso: Double) {
        if (nuevoPeso > 0) {
            peso = nuevoPeso
        }
    }

    /**
     * Método para verificar si necesita dieta
     */
    fun necesitaDieta(): Boolean {
        return when (especie.lowercase()) {
            "perro" -> peso > 30
            "gato" -> peso > 6
            else -> false
        }
    }

    /**
     * Método para obtener nombre completo con especie
     */
    fun obtenerNombreCompleto(): String {
        return "$nombre ($especie${if (raza != "Mestizo") " - $raza" else ""})"
    }

    /**
     * Método para verificar compatibilidad de vacuna
     */
    fun esCompatibleConVacuna(tipoVacuna: String): Boolean {
        return when (tipoVacuna.lowercase()) {
            "rabia" -> edad >= 1
            "múltiple" -> edad < 8
            else -> true
        }
    }

    override fun toString(): String {
        return "Mascota(nombre='$nombre', especie='$especie', edad=$edad, peso=$peso)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Mascota) return false
        return nombre == other.nombre && especie == other.especie
    }

    override fun hashCode(): Int {
        var result = nombre.hashCode()
        result = 31 * result + especie.hashCode()
        return result
    }
}

