package com.example.vet_clinic_android.model

/**
 * Clase Veterinario - Hereda de Usuario
 * Implementa Polimorfismo con métodos sobrescritos
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 */
class Veterinario(
    nombre: String,
    telefono: String = "+56900000000",
    email: String = "veterinario@clinica.cl",
    val especialidad: String,
    var disponible: Boolean = true,
    val licenciaProfesional: String = generarLicencia()
) : Usuario(nombre, telefono, email) {

    /**
     * Polimorfismo: Sobrescribe método de Usuario
     */
    override fun mostrarInformacion(): String {
        val estado = if (disponible) "✅ Disponible" else "❌ No disponible"
        return """
            Dr(a). ${obtenerNombreFormateado()}
            Especialidad: $especialidad
            Estado:       $estado
            Licencia:     $licenciaProfesional
            Teléfono:     $telefono
            Email:        $email
        """.trimIndent()
    }

    /**
     * Método específico de Veterinario
     */
    fun cambiarDisponibilidad(nueva: Boolean) {
        disponible = nueva
    }

    /**
     * Método para obtener título profesional
     */
    fun obtenerTituloProfesional(): String {
        return "Dr(a). $nombre"
    }

    /**
     * Método para verificar si puede atender emergencia
     */
    fun puedeAtenderEmergencia(): Boolean {
        return disponible && (especialidad.contains("Emergencia", ignoreCase = true) ||
                             especialidad.contains("General", ignoreCase = true))
    }

    companion object {
        /**
         * Método estático para generar licencia
         */
        fun generarLicencia(): String {
            return "VET-${(10000..99999).random()}"
        }
    }

    override fun toString(): String {
        return "Veterinario(nombre='$nombre', especialidad='$especialidad', disponible=$disponible)"
    }
}

