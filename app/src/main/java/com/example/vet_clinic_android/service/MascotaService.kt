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

    private val mascotas = mutableListOf<Mascota>()

    init {
        // Cargar mascotas de ejemplo
        mascotas.addAll(listOf(
            Mascota("Luna", "Perro", 3, 12.5, "Labrador", "Dorado", "Hembra"),
            Mascota("Max", "Gato", 5, 4.8, "Persa", "Blanco", "Macho"),
            Mascota("Rocky", "Perro", 7, 28.0, "Pastor Alemán", "Negro y café", "Macho"),
            Mascota("Mimi", "Gato", 2, 3.5, "Siamés", "Crema", "Hembra"),
            Mascota("Bobby", "Perro", 1, 8.0, "Beagle", "Tricolor", "Macho"),
            Mascota("Coco", "Gato", 4, 5.2, "Angora", "Gris", "Hembra"),
            Mascota("Thor", "Perro", 6, 35.0, "Rottweiler", "Negro", "Macho")
        ))
    }

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

    /**
     * Obtiene todas las mascotas registradas
     */
    fun obtenerTodasMascotas(): List<Mascota> = mascotas.toList()

    /**
     * Agrega una mascota a la lista
     */
    fun agregarMascota(mascota: Mascota) {
        mascotas.add(mascota)
    }
}
