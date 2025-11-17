package com.example.vet_clinic_android.service

import com.example.vet_clinic_android.model.Mascota
import com.example.vet_clinic_android.util.solicitarTexto
import com.example.vet_clinic_android.util.solicitarEnteroEnRango
import com.example.vet_clinic_android.util.solicitarDecimalEnRango

/**
 * Servicio para gestión de mascotas
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 */

class MascotaService {

    fun registrarMascota(): Mascota {
        println("\n╔═══════════════════════════════════════╗")
        println("║    REGISTRO DE NUEVA MASCOTA          ║")
        println("╚═══════════════════════════════════════╝")

        val nombre = solicitarTexto("Nombre de la mascota: ")
        val especie = solicitarTexto("Especie (Perro/Gato/Conejo/etc.): ", "Perro")
        val edad = solicitarEnteroEnRango("Edad (en años): ", 0..30)
        val peso = solicitarDecimalEnRango("Peso (en kg): ", 0.1, 200.0)

        val mascota = Mascota(nombre, especie, edad, peso)

        println("\nMascota registrada:")
        println("   Nombre: ${mascota.nombre}")
        println("   Especie: ${mascota.especie}")
        println("   Edad: ${mascota.edad} año(s)")
        println("   Peso: ${mascota.peso} kg")

        return mascota
    }

    fun mostrarDetallesMascota(mascota: Mascota) {
        // Usar método POO de la clase Mascota
        println(mascota.mostrarInformacion())
    }
}
