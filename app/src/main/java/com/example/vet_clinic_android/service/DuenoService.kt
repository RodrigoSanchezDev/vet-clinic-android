package com.example.vet_clinic_android.service

import com.example.vet_clinic_android.model.Dueno
import com.example.vet_clinic_android.util.solicitarTexto
import com.example.vet_clinic_android.util.solicitarEmailValido
import com.example.vet_clinic_android.util.solicitarTelefonoValido
import com.example.vet_clinic_android.util.validarEmail

/**
 * Servicio para gestión de dueños de mascotas
 * Incluye manejo robusto de excepciones y nulos
 * Usa Regex para validación de email y teléfono
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 */

class DuenoService {

    /**
     * Registra un nuevo dueño con validación Regex
     * - Email: valida formato nombre@dominio.com
     * - Teléfono: formatea a estilo uniforme +XX (XXX) XXX-XXXX
     */
    fun registrarDueno(): Dueno {
        println("\n╔═══════════════════════════════════════╗")
        println("║    REGISTRO DE DUEÑO                  ║")
        println("╚═══════════════════════════════���═══════╝")

        val nombreDueno = solicitarTexto("Nombre del dueño: ")

        // Validación y formateo de teléfono con Regex
        println("\nIngrese el teléfono del dueño")
        println("Puede ingresar: 912345678, +56912345678, etc.")
        val telefono = solicitarTelefonoValido()

        // Validación de email con Regex
        println("\nIngrese el email del dueño")
        println("Debe seguir el formato: nombre@dominio.com")
        val email = solicitarEmailValido()

        val dueno = Dueno(nombreDueno, telefono, email)

        println("\n✅ Dueño registrado exitosamente:")
        println("   Nombre:   ${dueno.nombreDueno}")
        println("   Teléfono: ${dueno.telefono}")
        println("   Email:    ${dueno.email}")

        return dueno
    }

    /**
     * Envía recordatorio solo si el email es válido
     * Usa let para ejecutar acción solo si no es null y es válido
     */
    fun enviarRecordatorioEmail(dueno: Dueno, mensaje: String): Boolean {
        // Operador let - solo ejecuta si email es válido
        return dueno.email.takeIf { validarEmail(it).isValid }?.let { emailValido ->
            try {
                println("\n📧 Enviando recordatorio por email...")
                println("   Destinatario: $emailValido")
                println("   Mensaje: $mensaje")
                println("✅ Email enviado exitosamente")
                true
            } catch (e: Exception) {
                println("❌ ERROR al enviar email: ${e.message}")
                false
            }
        } ?: run {
            println("⚠️  No se puede enviar email. Email inválido: ${dueno.email}")
            false
        }
    }

    /**
     * Envía recordatorio por SMS con manejo de excepciones
     */
    fun enviarRecordatorioSMS(dueno: Dueno, mensaje: String): Boolean {
        return try {
            // Operador safe call ?. para validar teléfono
            dueno.telefono.takeIf { it.isNotBlank() }?.let { telefonoValido ->
                println("\n📱 Enviando recordatorio por SMS...")
                println("   Destinatario: $telefonoValido")
                println("   Mensaje: $mensaje")
                println("✅ SMS enviado exitosamente")
                true
            } ?: run {
                println("⚠️  No se puede enviar SMS. Teléfono vacío")
                false
            }
        } catch (e: Exception) {
            println("❌ ERROR al enviar SMS: ${e.message}")
            false
        }
    }

    fun mostrarDetallesDueno(dueno: Dueno) {
        println("\n┌─────────────────────────────────────────┐")
        println("│ DATOS DEL DUEÑO                         │")
        println("└─────────────────────────────────────────┘")
        // Usar método POO de la clase Usuario (heredado por Dueno)
        println(dueno.mostrarInformacion().prependIndent("  "))
    }
}
