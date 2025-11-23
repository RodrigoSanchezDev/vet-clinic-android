package com.example.vet_clinic_android.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vet_clinic_android.ui.components.BannerCard
import com.example.vet_clinic_android.ui.navigation.Screen
import com.example.vet_clinic_android.ui.theme.MenuCardDefaults
import com.example.vet_clinic_android.ui.viewmodels.VetClinicViewModel

/**
 * Pantalla principal con menú de opciones
 * Mantiene toda la funcionalidad del sistema original
 * Ahora incluye estadísticas dinámicas del sistema
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: VetClinicViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    // Observar estadísticas
    val estadisticas by viewModel.estadisticas.collectAsState()

    // Estado para controlar si el resumen está expandido
    var resumenExpandido by remember { mutableStateOf(true) }

    // Actualizar estadísticas al entrar
    LaunchedEffect(Unit) {
        viewModel.actualizarEstadisticas()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Clínica Veterinaria",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Santiago, Chile",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Banner de bienvenida con gradiente moderno
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Círculo decorativo con icono
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Pets,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Pet Care",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = "Gestión Veterinaria",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }

            // Card de resumen rápido COLAPSABLE (Requisito Semana 4)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiary // Rosa vibrante para texto blanco
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Header del resumen (siempre visible)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { resumenExpandido = !resumenExpandido }
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Dashboard,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Resumen Rápido",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { navController.navigate(Screen.Resumen.route) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.OpenInNew,
                                    contentDescription = "Abrir pantalla completa",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Icon(
                                imageVector = if (resumenExpandido) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = if (resumenExpandido) "Contraer" else "Expandir",
                                tint = Color.White
                            )
                        }
                    }

                    // Contenido colapsable con animación
                    AnimatedVisibility(
                        visible = resumenExpandido,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        ) {
                            Divider(
                                modifier = Modifier.padding(bottom = 12.dp),
                                color = Color.White.copy(alpha = 0.3f)
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                ResumenQuickStat(
                                    label = "Mascotas",
                                    value = estadisticas.totalMascotas.toString(),
                                    textColor = Color.White
                                )
                                ResumenQuickStat(
                                    label = "Consultas",
                                    value = estadisticas.totalConsultas.toString(),
                                    textColor = Color.White
                                )
                                ResumenQuickStat(
                                    label = "Pendientes",
                                    value = estadisticas.consultasPendientes.toString(),
                                    textColor = Color.White
                                )
                            }
                            if (estadisticas.ultimoDuenoNombre != "Ninguno") {
                                Spacer(modifier = Modifier.height(12.dp))
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = MaterialTheme.shapes.small,
                                    color = Color.White.copy(alpha = 0.2f)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp),
                                            tint = Color.White.copy(alpha = 0.9f)
                                        )
                                        Text(
                                            text = "Último registro: ${estadisticas.ultimoDuenoNombre}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.White.copy(alpha = 0.9f),
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Grid de opciones del menú
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(menuOptions) { option ->
                    MenuOptionCard(
                        option = option,
                        onClick = {
                            navController.navigate(option.route)
                        }
                    )
                }
            }
        }
    }
}

/**
 * Composable para mostrar una estadística rápida
 */
@Composable
fun ResumenQuickStat(
    label: String,
    value: String,
    textColor: Color = MaterialTheme.colorScheme.onSecondaryContainer
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = textColor.copy(alpha = 0.8f)
        )
    }
}

/**
 * Card individual para cada opción del menú
 * Diseño moderno con colores vibrantes y efectos hover profesionales
 */
@Composable
fun MenuOptionCard(
    option: MenuOption,
    onClick: () -> Unit
) {
    // Colores vibrantes para cada card
    val cardColors = listOf(
        Color(0xFF5B21B6) to Color(0xFFFFFFFF), // Morado - Texto blanco
        Color(0xFFF59E0B) to Color(0xFFFFFFFF), // Naranja - Texto blanco (CORREGIDO)
        Color(0xFF3B82F6) to Color(0xFFFFFFFF), // Azul - Texto blanco
        Color(0xFFEC4899) to Color(0xFFFFFFFF), // Rosa - Texto blanco
        Color(0xFF10B981) to Color(0xFFFFFFFF), // Verde - Texto blanco
        Color(0xFF8B5CF6) to Color(0xFFFFFFFF), // Morado claro - Texto blanco
        Color(0xFF06B6D4) to Color(0xFFFFFFFF), // Cyan - Texto blanco
        Color(0xFFEF4444) to Color(0xFFFFFFFF), // Rojo - Texto blanco
    )

    val colorIndex = option.title.hashCode().rem(cardColors.size).let { if (it < 0) it + cardColors.size else it }
    val (bgColor, textColor) = cardColors[colorIndex]

    // Estado de interacción para hover effect
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Animación de escala con efecto hover suave
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "cardScale"
    )

    // Animación de elevación (sombreado) profesional
    val elevation by animateDpAsState(
        targetValue = if (isPressed) 12.dp else 6.dp,
        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        ),
        label = "cardElevation"
    )

    // Animación del tamaño del icono
    val iconScale by animateFloatAsState(
        targetValue = if (isPressed) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "iconScale"
    )

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .scale(scale),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = bgColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation,
            pressedElevation = 12.dp,
            hoveredElevation = 8.dp
        ),
        interactionSource = interactionSource
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Círculo decorativo con icono y animación
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .scale(iconScale)
                    .clip(CircleShape)
                    .background(textColor.copy(alpha = if (isPressed) 0.3f else 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = option.icon,
                    contentDescription = option.title,
                    modifier = Modifier.size(28.dp),
                    tint = textColor
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = option.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                textAlign = TextAlign.Center,
                color = textColor,
                maxLines = 2
            )
        }
    }
}

/**
 * Data class para las opciones del menú
 */
data class MenuOption(
    val title: String,
    val icon: ImageVector,
    val route: String
)

/**
 * Lista de todas las opciones del menú
 * Refleja el menú completo del sistema original de consola
 */
val menuOptions = listOf(
    MenuOption(
        title = "Resumen del Sistema",
        icon = Icons.Default.Dashboard,
        route = Screen.Resumen.route
    ),
    MenuOption(
        title = "Registrar Nueva Consulta",
        icon = Icons.Default.Add,
        route = Screen.RegisterConsulta.route
    ),
    MenuOption(
        title = "Informe de Consultas",
        icon = Icons.Default.List,
        route = Screen.ListConsultas.route
    ),
    MenuOption(
        title = "Consultas Pendientes",
        icon = Icons.Default.PendingActions,
        route = Screen.ConsultasPendientes.route
    ),
    MenuOption(
        title = "Estadísticas Sistema",
        icon = Icons.Default.BarChart,
        route = Screen.Estadisticas.route
    ),
    MenuOption(
        title = "Agenda Veterinarios",
        icon = Icons.Default.Schedule,
        route = Screen.AgendaVeterinarios.route
    ),
    MenuOption(
        title = "Estadísticas Veterinarios",
        icon = Icons.Default.Science,
        route = Screen.EstadisticasVeterinarios.route
    ),
    MenuOption(
        title = "Buscar Veterinario",
        icon = Icons.Default.Search,
        route = Screen.BuscarVeterinario.route
    ),
    MenuOption(
        title = "Validar Productos (Ranges)",
        icon = Icons.Default.Inventory,
        route = Screen.ValidarProductos.route
    ),
    MenuOption(
        title = "Crear Pedido Medicamentos",
        icon = Icons.Default.ShoppingCart,
        route = Screen.CrearPedido.route
    ),
    MenuOption(
        title = "Ver Promociones",
        icon = Icons.Default.LocalOffer,
        route = Screen.VerPromociones.route
    ),
    MenuOption(
        title = "Analizar con Reflection",
        icon = Icons.Default.Code,
        route = Screen.Reflection.route
    ),
    MenuOption(
        title = "Combinar Pedidos (+ Operator)",
        icon = Icons.Default.Add,
        route = Screen.CombinarPedidos.route
    ),
    MenuOption(
        title = "Comparar Medicamentos (==)",
        icon = Icons.Default.CompareArrows,
        route = Screen.CompararMedicamentos.route
    ),
    MenuOption(
        title = "Desestructurar Cliente",
        icon = Icons.Default.PersonRemove,
        route = Screen.DesestructurarCliente.route
    ),
    MenuOption(
        title = "Desestructurar Pedido",
        icon = Icons.Default.ShoppingBag,
        route = Screen.DesestructurarPedido.route
    ),
    MenuOption(
        title = "Validar Duplicados Clientes",
        icon = Icons.Default.People,
        route = Screen.ValidarDuplicadosClientes.route
    ),
    MenuOption(
        title = "Validar Duplicados Medicamentos",
        icon = Icons.Default.MedicalServices,
        route = Screen.ValidarDuplicadosMedicamentos.route
    ),
    MenuOption(
        title = "Reporte Completo Integrado",
        icon = Icons.Default.Assessment,
        route = Screen.ReporteCompleto.route
    )
)
