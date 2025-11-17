package com.example.vet_clinic_android.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vet_clinic_android.model.*
import com.example.vet_clinic_android.service.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel principal que maneja el estado de la aplicación
 * Centraliza todos los servicios y la lógica de negocio
 */
class VetClinicViewModel : ViewModel() {

    // Servicios
    val mascotaService = MascotaService()
    val duenoService = DuenoService()
    val consultaService = ConsultaService()
    val veterinarioService = VeterinarioService()
    val vacunaService = VacunaService()
    val medicamentoService = MedicamentoService()
    val promocionService = PromocionService()
    val reflectionService = ReflectionService()

    // Estado para los formularios
    private val _currentMascota = MutableStateFlow<Mascota?>(null)
    val currentMascota: StateFlow<Mascota?> = _currentMascota.asStateFlow()

    private val _currentDueno = MutableStateFlow<Dueno?>(null)
    val currentDueno: StateFlow<Dueno?> = _currentDueno.asStateFlow()

    private val _consultaCreada = MutableStateFlow<Consulta?>(null)
    val consultaCreada: StateFlow<Consulta?> = _consultaCreada.asStateFlow()

    // Estado de mensajes
    private val _messageState = MutableStateFlow<String?>(null)
    val messageState: StateFlow<String?> = _messageState.asStateFlow()

    /**
     * Registra una nueva mascota
     */
    fun registrarMascota(
        nombre: String,
        especie: String,
        edad: Int,
        peso: Double,
        raza: String = "Mestizo",
        color: String = "",
        sexo: String = ""
    ) {
        viewModelScope.launch {
            try {
                val mascota = Mascota(nombre, especie, edad, peso, raza, color, sexo)
                _currentMascota.value = mascota
                _messageState.value = "Mascota registrada: $nombre"
            } catch (e: Exception) {
                _messageState.value = "Error al registrar mascota: ${e.message}"
            }
        }
    }

    /**
     * Registra un nuevo dueño
     */
    fun registrarDueno(
        nombre: String,
        telefono: String,
        email: String,
        direccion: String = "",
        rut: String = ""
    ) {
        viewModelScope.launch {
            try {
                val dueno = Dueno(nombre, telefono, email, direccion, rut)
                _currentDueno.value = dueno
                _messageState.value = "Dueño registrado: $nombre"
            } catch (e: Exception) {
                _messageState.value = "Error al registrar dueño: ${e.message}"
            }
        }
    }

    /**
     * Crea una nueva consulta y la guarda en el servicio
     */
    fun crearConsulta(
        descripcion: String,
        tipoServicio: String,
        tiempoEstimado: Int,
        numeroMascotas: Int = 1
    ) {
        viewModelScope.launch {
            try {
                val mascota = _currentMascota.value
                val dueno = _currentDueno.value

                if (mascota == null || dueno == null) {
                    _messageState.value = "Error: Faltan datos de mascota o dueño"
                    return@launch
                }

                // TODO: Asociar mascota con dueño cuando se implemente el método
                // duenoService.agregarMascota(dueno, mascota)

                val idConsulta = consultaService.generarIdConsulta()
                val costoInicial = consultaService.calcularCostoConsulta(tipoServicio, tiempoEstimado)

                val consulta = consultaService.crearConsulta(
                    idConsulta,
                    descripcion,
                    costoInicial,
                    "Pendiente",
                    tipoServicio
                )

                // Calcular descuento si aplica
                consulta.calcularCostoFinalConDescuento(numeroMascotas)

                // Guardar consulta en el servicio
                consultaService.agregarConsulta(consulta)

                _consultaCreada.value = consulta
                _messageState.value = "Consulta creada exitosamente - ID: $idConsulta"
            } catch (e: Exception) {
                _messageState.value = "Error al crear consulta: ${e.message}"
            }
        }
    }

    /**
     * Limpia el mensaje de estado
     */
    fun clearMessage() {
        _messageState.value = null
    }

    /**
     * Reinicia el proceso de registro
     */
    fun resetRegistro() {
        _currentMascota.value = null
        _currentDueno.value = null
        _consultaCreada.value = null
    }
}

