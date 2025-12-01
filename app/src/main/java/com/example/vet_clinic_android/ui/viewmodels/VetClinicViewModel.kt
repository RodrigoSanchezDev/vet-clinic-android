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
 *
 * Este ViewModel actúa como una capa de coordinación entre la UI y los servicios de negocio,
 * manteniendo el estado de la aplicación de forma reactiva mediante StateFlows.
 *
 * @author Rodrigo Sánchez
 */
class VetClinicViewModel : ViewModel() {

    // ============================================================================
    // SERVICIOS - Capa de lógica de negocio
    // ============================================================================

    /**
     * Servicios de dominio para gestión de entidades
     */
    val mascotaService = MascotaService()
    val duenoService = DuenoService()
    val consultaService = ConsultaService()
    val veterinarioService = VeterinarioService()
    val vacunaService = VacunaService()
    val medicamentoService = MedicamentoService()
    val promocionService = PromocionService()
    val reflectionService = ReflectionService()

    // ============================================================================
    // ESTADOS REACTIVOS - Flujos de estado para la UI
    // ============================================================================

    /**
     * Estado de la mascota actualmente en proceso de registro
     */
    private val _currentMascota = MutableStateFlow<Mascota?>(null)
    val currentMascota: StateFlow<Mascota?> = _currentMascota.asStateFlow()

    /**
     * Estado del dueño actualmente en proceso de registro
     */
    private val _currentDueno = MutableStateFlow<Dueno?>(null)
    val currentDueno: StateFlow<Dueno?> = _currentDueno.asStateFlow()

    /**
     * Estado de la consulta recién creada
     */
    private val _consultaCreada = MutableStateFlow<Consulta?>(null)
    val consultaCreada: StateFlow<Consulta?> = _consultaCreada.asStateFlow()

    /**
     * Estado de mensajes para mostrar feedback al usuario
     */
    private val _messageState = MutableStateFlow<String?>(null)
    val messageState: StateFlow<String?> = _messageState.asStateFlow()

    /**
     * Estado de estadísticas del sistema
     * Contiene resumen de datos para la pantalla principal
     */
    private val _estadisticas = MutableStateFlow(ResumenEstadisticas())
    val estadisticas: StateFlow<ResumenEstadisticas> = _estadisticas.asStateFlow()

    /**
     * 🎬 Estado de carga para indicadores de progreso
     * Controla la visibilidad del loading indicator en la UI
     */
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    /**
     * 🎬 Mensaje de carga personalizado
     * Muestra el estado actual de la operación en progreso
     */
    private val _loadingMessage = MutableStateFlow("Cargando...")
    val loadingMessage: StateFlow<String> = _loadingMessage.asStateFlow()

    // ============================================================================
    // DATA CLASSES PARA ESTADOS
    // ============================================================================

    /**
     * Data class que contiene el resumen de estadísticas del sistema
     * Utilizado para mostrar información dinámica en la UI
     *
     * @property totalMascotas Número total de mascotas registradas
     * @property totalConsultas Número total de consultas realizadas
     * @property ultimoDuenoNombre Nombre del último dueño registrado
     * @property totalDuenos Número total de dueños en el sistema
     * @property consultasPendientes Número de consultas pendientes
     * @property totalVeterinarios Número de veterinarios registrados
     */
    data class ResumenEstadisticas(
        val totalMascotas: Int = 0,
        val totalConsultas: Int = 0,
        val ultimoDuenoNombre: String = "Ninguno",
        val totalDuenos: Int = 0,
        val consultasPendientes: Int = 0,
        val totalVeterinarios: Int = 0
    )

    // ============================================================================
    // CONSTANTES
    // ============================================================================

    companion object {
        private const val DEFAULT_RAZA = "Mestizo"
        private const val ESTADO_PENDIENTE = "Pendiente"

        // Mensajes de error
        private const val ERROR_DATOS_FALTANTES = "Error: Faltan datos de mascota o dueño"
        private const val ERROR_REGISTRO_MASCOTA = "Error al registrar mascota"
        private const val ERROR_REGISTRO_DUENO = "Error al registrar dueño"
        private const val ERROR_CREAR_CONSULTA = "Error al crear consulta"

        // Mensajes de éxito
        private const val MSG_MASCOTA_REGISTRADA = "Mascota registrada"
        private const val MSG_DUENO_REGISTRADO = "Dueño registrado"
        private const val MSG_CONSULTA_CREADA = "Consulta creada exitosamente - ID"
    }

    // ============================================================================
    // OPERACIONES DE REGISTRO
    // ============================================================================

    /**
     * Registra una nueva mascota en el sistema
     *
     * @param nombre Nombre de la mascota
     * @param especie Especie (perro, gato, etc.)
     * @param edad Edad en años
     * @param peso Peso en kilogramos
     * @param raza Raza de la mascota (por defecto: "Mestizo")
     * @param color Color del pelaje
     * @param sexo Sexo de la mascota
     */
    fun registrarMascota(
        nombre: String,
        especie: String,
        edad: Int,
        peso: Double,
        raza: String = DEFAULT_RAZA,
        color: String = "",
        sexo: String = ""
    ) {
        viewModelScope.launch {
            try {
                val mascota = Mascota(nombre, especie, edad, peso, raza, color, sexo)
                _currentMascota.value = mascota
                _messageState.value = "$MSG_MASCOTA_REGISTRADA: $nombre"
            } catch (e: Exception) {
                _messageState.value = "$ERROR_REGISTRO_MASCOTA: ${e.message}"
            }
        }
    }

    /**
     * Registra un nuevo dueño en el sistema
     *
     * @param nombre Nombre completo del dueño
     * @param telefono Teléfono de contacto
     * @param email Correo electrónico
     * @param direccion Dirección del domicilio
     * @param rut RUT del dueño
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
                _messageState.value = "$MSG_DUENO_REGISTRADO: $nombre"
            } catch (e: Exception) {
                _messageState.value = "$ERROR_REGISTRO_DUENO: ${e.message}"
            }
        }
    }

    /**
     * Crea una nueva consulta veterinaria y la guarda en el servicio
     *
     * Requiere que previamente se hayan registrado tanto la mascota como el dueño.
     * Calcula automáticamente el costo con descuentos aplicables.
     *
     * @param descripcion Descripción del motivo de la consulta
     * @param tipoServicio Tipo de servicio (consulta general, urgencia, etc.)
     * @param tiempoEstimado Tiempo estimado en minutos
     * @param numeroMascotas Número de mascotas (para calcular descuentos)
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
                    _messageState.value = ERROR_DATOS_FALTANTES
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
                    ESTADO_PENDIENTE,
                    tipoServicio
                )

                // Calcular descuento si aplica
                consulta.calcularCostoFinalConDescuento(numeroMascotas)

                // Guardar consulta en el servicio
                consultaService.agregarConsulta(consulta)

                _consultaCreada.value = consulta
                _messageState.value = "$MSG_CONSULTA_CREADA: $idConsulta"
            } catch (e: Exception) {
                _messageState.value = "$ERROR_CREAR_CONSULTA: ${e.message}"
            }
        }
    }

    // ============================================================================
    // OPERACIONES DE ESTADO
    // ============================================================================

    /**
     * Limpia el mensaje de estado actual
     * Útil después de mostrar un mensaje al usuario
     */
    fun clearMessage() {
        _messageState.value = null
    }

    /**
     * Reinicia el proceso de registro completo
     * Limpia todos los estados temporales de mascota, dueño y consulta
     */
    fun resetRegistro() {
        _currentMascota.value = null
        _currentDueno.value = null
        _consultaCreada.value = null
    }

    // ============================================================================
    // OPERACIONES DE ESTADÍSTICAS
    // ============================================================================

    /**
     * Actualiza las estadísticas del sistema
     * Recopila información de todos los servicios y actualiza el estado
     *
     * Este método debe llamarse cuando se quiera refrescar la información mostrada
     * en la pantalla principal o de resumen.
     */
    fun actualizarEstadisticas() {
        viewModelScope.launch {
            try {
                // 🎬 Activar indicador de progreso
                _isLoading.value = true
                _loadingMessage.value = "Generando resumen..."

                // Simular delay para mostrar el indicador (en producción esto sería real)
                kotlinx.coroutines.delay(2500)

                val todasMascotas = mascotaService.obtenerTodasMascotas()
                val todasConsultas = consultaService.obtenerTodasConsultas()
                val todosDuenos = duenoService.obtenerTodosDuenos()
                val consultasPendientes = consultaService.obtenerConsultasPendientes()
                val todosVeterinarios = veterinarioService.obtenerTodosVeterinarios()

                _estadisticas.value = ResumenEstadisticas(
                    totalMascotas = todasMascotas.size,
                    totalConsultas = todasConsultas.size,
                    ultimoDuenoNombre = todosDuenos.lastOrNull()?.nombre ?: "Ninguno",
                    totalDuenos = todosDuenos.size,
                    consultasPendientes = consultasPendientes.size,
                    totalVeterinarios = todosVeterinarios.size
                )
            } catch (e: Exception) {
                _messageState.value = "Error al actualizar estadísticas: ${e.message}"
            } finally {
                // 🎬 Desactivar indicador de progreso
                _isLoading.value = false
            }
        }
    }

    /**
     * 🎬 Método helper para operaciones con loading
     * Ejecuta una operación mostrando indicador de progreso
     *
     * @param loadingMsg Mensaje a mostrar durante la carga
     * @param operation Operación suspendida a ejecutar
     */
    private suspend fun <T> withLoading(
        loadingMsg: String,
        operation: suspend () -> T
    ): T {
        _isLoading.value = true
        _loadingMessage.value = loadingMsg
        return try {
            operation()
        } finally {
            _isLoading.value = false
        }
    }

    /**
     * Inicializa las estadísticas del sistema
     * Debe llamarse al inicio de la aplicación
     */
    init {
        actualizarEstadisticas()
    }
}
