package com.example.vet_clinic_android.ui.navigation

/**
 * Define las rutas de navegación de la aplicación
 * Refleja todas las funcionalidades del sistema
 */
sealed class Screen(val route: String) {
    // Pantalla de bienvenida
    object Intro : Screen("intro")

    // Pantalla Principal
    object Home : Screen("home")

    // Pantalla de Resumen con Estadísticas (Requisito Semana 4)
    object Resumen : Screen("resumen")

    // Gestión de Consultas (Paso 1 y 2)
    object RegisterConsulta : Screen("register_consulta")
    object ListConsultas : Screen("list_consultas")
    object ConsultasPendientes : Screen("consultas_pendientes")
    object Estadisticas : Screen("estadisticas")

    // Gestión de Veterinarios (Paso 2)
    object AgendaVeterinarios : Screen("agenda_veterinarios")
    object EstadisticasVeterinarios : Screen("estadisticas_veterinarios")
    object BuscarVeterinario : Screen("buscar_veterinario")

    // Medicamentos y Pedidos (Paso 3)
    object ValidarProductos : Screen("validar_productos")
    object CrearPedido : Screen("crear_pedido")
    object VerPromociones : Screen("ver_promociones")

    // Reflection y Anotaciones (Paso 3 - Funcionalidades Avanzadas)
    object Reflection : Screen("reflection")

    // Operator Overloading (Paso 3)
    object CombinarPedidos : Screen("combinar_pedidos")
    object CompararMedicamentos : Screen("comparar_medicamentos")

    // Desestructuración (Paso 3)
    object DesestructurarCliente : Screen("desestructurar_cliente")
    object DesestructurarPedido : Screen("desestructurar_pedido")

    // Validación de Duplicados (Paso 3)
    object ValidarDuplicadosClientes : Screen("validar_duplicados_clientes")
    object ValidarDuplicadosMedicamentos : Screen("validar_duplicados_medicamentos")

    // Reporte Integrado Final
    object ReporteCompleto : Screen("reporte_completo")
}
