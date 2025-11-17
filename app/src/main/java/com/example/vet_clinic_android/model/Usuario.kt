package com.example.vet_clinic_android.model

/**
 * Clase base Usuario - Principios de POO con Herencia
 * Incluye atributos comunes entre Veterinario y Dueño
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 */
open class Usuario(
    val nombre: String,
    val telefono: String,
    val email: String
) {
    /**
     * Método para mostrar información básica del usuario
     * Puede ser sobreescrito por clases hijas (Polimorfismo)
     */
    open fun mostrarInformacion(): String {
        return """
            Nombre:   $nombre
            Teléfono: $telefono
            Email:    $email
        """.trimIndent()
    }

    /**
     * Método para validar email
     */
    fun validarEmail(): Boolean {
        return email.contains("@") && email.contains(".")
    }

    /**
     * Método para obtener nombre formateado
     */
    fun obtenerNombreFormateado(): String {
        return nombre.split(" ").joinToString(" ") {
            it.replaceFirstChar { char -> char.uppercase() }
        }
    }

    /**
     * Método toString para representación de texto
     */
    override fun toString(): String {
        return "Usuario(nombre='$nombre', telefono='$telefono', email='$email')"
    }

    /**
     * Método equals para comparación
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Usuario) return false
        return email == other.email
    }

    /**
     * Método hashCode
     */
    override fun hashCode(): Int {
        return email.hashCode()
    }
}

