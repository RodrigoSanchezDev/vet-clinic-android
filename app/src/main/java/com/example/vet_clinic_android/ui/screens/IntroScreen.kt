package com.example.vet_clinic_android.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.example.vet_clinic_android.ui.navigation.Screen
import com.example.vet_clinic_android.ui.theme.AnimationSpecs

private fun NavOptionsBuilder.clearIntroFromBackStack() {
    popUpTo(Screen.Intro.route) { inclusive = true }
    launchSingleTop = true
}

@Composable
fun IntroScreen(navController: NavController) {
    // 🎬 Estados de animación mejorados con Fade In escalonado
    var showLogo by remember { mutableStateOf(false) }
    var showTitle by remember { mutableStateOf(false) }
    var showSubtitle by remember { mutableStateOf(false) }
    var showButton by remember { mutableStateOf(false) }

    // 🎬 Secuencia de animaciones Fade In al cargar la pantalla
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(100)
        showLogo = true
        kotlinx.coroutines.delay(200)
        showTitle = true
        kotlinx.coroutines.delay(150)
        showSubtitle = true
        kotlinx.coroutines.delay(200)
        showButton = true
    }

    // Fondo con gradiente moderno
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primaryContainer
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Sección superior - Ilustración y título con Fade In
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // 🎬 Logo con Fade In + Scale
                AnimatedVisibility(
                    visible = showLogo,
                    enter = AnimationSpecs.enterFadeScale(
                        duration = AnimationSpecs.DURATION_SLOW,
                        initialScale = 0.7f
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(160.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.3f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Pets,
                                contentDescription = null,
                                modifier = Modifier.size(80.dp),
                                tint = Color.White
                            )
                        }
                    }
                }

                // 🎬 Título con Fade In + Slide Up
                AnimatedVisibility(
                    visible = showTitle,
                    enter = AnimationSpecs.enterFadeSlideUp(
                        duration = AnimationSpecs.DURATION_NORMAL,
                        initialOffsetY = 20.dp
                    )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Pet Care",
                            style = MaterialTheme.typography.displayLarge.copy(
                                fontSize = 48.sp,
                                fontWeight = FontWeight.ExtraBold
                            ),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // 🎬 Subtítulo con Fade In + Slide Up
                AnimatedVisibility(
                    visible = showSubtitle,
                    enter = AnimationSpecs.enterFadeSlideUp(
                        duration = AnimationSpecs.DURATION_NORMAL,
                        initialOffsetY = 15.dp
                    )
                ) {
                    Text(
                        text = "Por la salud y el cuidado de tu mascota",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            // 🎬 Botón con Fade In + Scale
            AnimatedVisibility(
                visible = showButton,
                enter = AnimationSpecs.enterFadeScale(
                    duration = AnimationSpecs.DURATION_NORMAL,
                    initialScale = 0.85f
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val interactionSource = remember { MutableInteractionSource() }
                    val isPressed by interactionSource.collectIsPressedAsState()

                    // Animación de escala con efecto hover
                    val scale by animateFloatAsState(
                        targetValue = if (isPressed) 0.9f else 1f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        ),
                        label = "buttonScale"
                    )

                    val elevation by animateDpAsState(
                        targetValue = if (isPressed) 16.dp else 12.dp,
                        animationSpec = tween(durationMillis = 150),
                        label = "buttonElevation"
                    )

                    // 🎬 Estado para controlar Fade Out al salir
                    var isExiting by remember { mutableStateOf(false) }

                    // Botón principal con animación hover y Fade Out
                    Button(
                        onClick = {
                            // Activar animación de salida
                            isExiting = true
                            // Navegar después de la animación
                            navController.navigate(Screen.Home.route) {
                                clearIntroFromBackStack()
                            }
                        },
                        modifier = Modifier
                            .size(72.dp)
                            .scale(scale)
                            .graphicsLayer {
                                alpha = if (isExiting) 0f else 1f
                            },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = elevation,
                            pressedElevation = 16.dp,
                            hoveredElevation = 14.dp
                        ),
                        interactionSource = interactionSource
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Comenzar",
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Text(
                        text = "Toca para comenzar",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}
