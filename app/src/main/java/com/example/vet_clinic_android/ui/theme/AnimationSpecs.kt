package com.example.vet_clinic_android.ui.theme

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 🎬 Sistema de Animaciones Profesional
 * Especificaciones de animación coherentes para toda la aplicación
 * Siguiendo Material Design 3 Motion guidelines
 */
object AnimationSpecs {

    // ⏱️ DURACIONES ESTÁNDAR
    /**
     * Animaciones rápidas para micro-interacciones (hover, click)
     * Duración: 100-150ms
     */
    const val DURATION_FAST = 150

    /**
     * Animaciones normales para transiciones de contenido
     * Duración: 300-400ms (estándar Material Design)
     */
    const val DURATION_NORMAL = 300

    /**
     * Animaciones lentas para transiciones de pantalla completa
     * Duración: 400-500ms
     */
    const val DURATION_SLOW = 400

    /**
     * Animaciones muy lentas para efectos dramáticos
     * Duración: 600-800ms
     */
    const val DURATION_EXTRA_SLOW = 600

    // 📐 DELAYS ESCALONADOS (Staggered animations)
    const val STAGGER_DELAY_SHORT = 50
    const val STAGGER_DELAY_MEDIUM = 100
    const val STAGGER_DELAY_LONG = 150

    // 🎯 EASINGS (Curvas de animación)
    /**
     * Easing estándar para la mayoría de animaciones
     * Aceleración rápida, desaceleración suave
     */
    val EasingStandard = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)

    /**
     * Easing para elementos que entran a pantalla
     * Acelera desde reposo hasta velocidad completa
     */
    val EasingDecelerate = CubicBezierEasing(0.0f, 0.0f, 0.2f, 1.0f)

    /**
     * Easing para elementos que salen de pantalla
     * Mantiene velocidad y acelera al salir
     */
    val EasingAccelerate = CubicBezierEasing(0.4f, 0.0f, 1.0f, 1.0f)

    /**
     * Easing suave para transiciones elegantes
     */
    val EasingEmphasized = CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f)

    // 🎨 FADE ANIMATIONS
    /**
     * Fade In estándar - Aparición suave
     */
    fun fadeInSpec(duration: Int = DURATION_NORMAL): TweenSpec<Float> {
        return tween(
            durationMillis = duration,
            easing = EasingDecelerate
        )
    }

    /**
     * Fade Out estándar - Desaparición suave
     */
    fun fadeOutSpec(duration: Int = DURATION_NORMAL): TweenSpec<Float> {
        return tween(
            durationMillis = duration,
            easing = EasingAccelerate
        )
    }

    // 📍 SLIDE ANIMATIONS
    /**
     * Slide In desde arriba
     */
    fun slideInFromTopSpec(duration: Int = DURATION_NORMAL): TweenSpec<Float> {
        return tween(
            durationMillis = duration,
            easing = EasingDecelerate
        )
    }

    /**
     * Slide Out hacia arriba
     */
    fun slideOutToTopSpec(duration: Int = DURATION_NORMAL): TweenSpec<Float> {
        return tween(
            durationMillis = duration,
            easing = EasingAccelerate
        )
    }

    // 🎪 SCALE ANIMATIONS
    /**
     * Scale In - Crecimiento desde pequeño
     */
    fun scaleInSpec(duration: Int = DURATION_NORMAL): TweenSpec<Float> {
        return tween(
            durationMillis = duration,
            easing = EasingDecelerate
        )
    }

    /**
     * Scale Out - Reducción hasta desaparecer
     */
    fun scaleOutSpec(duration: Int = DURATION_NORMAL): TweenSpec<Float> {
        return tween(
            durationMillis = duration,
            easing = EasingAccelerate
        )
    }

    // 🌊 SPRING ANIMATIONS
    /**
     * Spring suave para animaciones naturales
     */
    fun <T> springSpec(
        dampingRatio: Float = Spring.DampingRatioMediumBouncy,
        stiffness: Float = Spring.StiffnessLow
    ): SpringSpec<T> {
        return spring(
            dampingRatio = dampingRatio,
            stiffness = stiffness
        )
    }

    // 🎭 ENTER/EXIT TRANSITIONS PRECONFIGURADAS

    /**
     * ✨ Fade In + Slide Up (Entrada elegante)
     * Perfecto para contenido que aparece desde abajo
     */
    fun enterFadeSlideUp(
        duration: Int = DURATION_NORMAL,
        initialOffsetY: Dp = 30.dp
    ): EnterTransition {
        return fadeIn(
            animationSpec = tween(duration, easing = EasingDecelerate)
        ) + slideInVertically(
            animationSpec = tween(duration, easing = EasingDecelerate),
            initialOffsetY = { initialOffsetY.value.toInt() }
        )
    }

    /**
     * ✨ Fade Out + Slide Up (Salida elegante)
     * Perfecto para contenido que desaparece hacia arriba
     */
    fun exitFadeSlideUp(
        duration: Int = DURATION_NORMAL,
        targetOffsetY: Dp = (-30).dp
    ): ExitTransition {
        return fadeOut(
            animationSpec = tween(duration, easing = EasingAccelerate)
        ) + slideOutVertically(
            animationSpec = tween(duration, easing = EasingAccelerate),
            targetOffsetY = { targetOffsetY.value.toInt() }
        )
    }

    /**
     * ✨ Fade In + Scale (Entrada con zoom)
     * Perfecto para elementos que aparecen con énfasis
     */
    fun enterFadeScale(
        duration: Int = DURATION_NORMAL,
        initialScale: Float = 0.8f
    ): EnterTransition {
        return fadeIn(
            animationSpec = tween(duration, easing = EasingDecelerate)
        ) + scaleIn(
            animationSpec = tween(duration, easing = EasingDecelerate),
            initialScale = initialScale
        )
    }

    /**
     * ✨ Fade Out + Scale (Salida con zoom)
     * Perfecto para elementos que desaparecen con énfasis
     */
    fun exitFadeScale(
        duration: Int = DURATION_NORMAL,
        targetScale: Float = 0.8f
    ): ExitTransition {
        return fadeOut(
            animationSpec = tween(duration, easing = EasingAccelerate)
        ) + scaleOut(
            animationSpec = tween(duration, easing = EasingAccelerate),
            targetScale = targetScale
        )
    }

    /**
     * ✨ Slide Horizontal (Navegación entre pantallas)
     * Slide In desde la derecha
     */
    fun enterSlideLeft(duration: Int = DURATION_NORMAL): EnterTransition {
        return slideInHorizontally(
            animationSpec = tween(duration, easing = EasingDecelerate),
            initialOffsetX = { it }
        ) + fadeIn(
            animationSpec = tween(duration, easing = EasingDecelerate)
        )
    }

    /**
     * ✨ Slide Horizontal (Navegación entre pantallas)
     * Slide Out hacia la izquierda
     */
    fun exitSlideLeft(duration: Int = DURATION_NORMAL): ExitTransition {
        return slideOutHorizontally(
            animationSpec = tween(duration, easing = EasingAccelerate),
            targetOffsetX = { -it }
        ) + fadeOut(
            animationSpec = tween(duration, easing = EasingAccelerate)
        )
    }

    /**
     * ✨ Entrada simple con Fade In
     * La más suave y elegante
     */
    fun enterFadeIn(duration: Int = DURATION_NORMAL): EnterTransition {
        return fadeIn(
            animationSpec = tween(duration, easing = EasingDecelerate)
        )
    }

    /**
     * ✨ Salida simple con Fade Out
     * La más suave y elegante
     */
    fun exitFadeOut(duration: Int = DURATION_NORMAL): ExitTransition {
        return fadeOut(
            animationSpec = tween(duration, easing = EasingAccelerate)
        )
    }
}

/**
 * 🎬 Helper: AnimatedVisibility con transiciones predefinidas
 * Uso simplificado de AnimatedVisibility con animaciones coherentes
 */
@Composable
fun AnimatedContent(
    visible: Boolean,
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier,
    enter: EnterTransition = AnimationSpecs.enterFadeSlideUp(),
    exit: ExitTransition = AnimationSpecs.exitFadeSlideUp(),
    content: @Composable androidx.compose.animation.AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = enter,
        exit = exit,
        content = content
    )
}

/**
 * 🎬 Helper: Animación escalonada para listas
 * Calcula el delay basado en el índice del elemento
 */
fun calculateStaggerDelay(
    index: Int,
    delayPerItem: Int = AnimationSpecs.STAGGER_DELAY_MEDIUM
): Int {
    return index * delayPerItem
}

/**
 * 🎬 Helper: Transición de pantalla completa (Screen transitions)
 * Para usar en el NavHost
 * Solo Fade In/Out para transiciones suaves sin deslizamiento
 */
object ScreenTransitions {
    /**
     * Transición de navegación hacia adelante (push)
     * Solo Fade In suave
     */
    fun enterTransition(): EnterTransition {
        return AnimationSpecs.enterFadeIn(AnimationSpecs.DURATION_NORMAL)
    }

    /**
     * Transición de salida al navegar hacia adelante
     * Fade Out rápido
     */
    fun exitTransition(): ExitTransition {
        return AnimationSpecs.exitFadeOut(AnimationSpecs.DURATION_FAST)
    }

    /**
     * Transición de navegación hacia atrás (pop)
     * Fade In rápido al volver
     */
    fun popEnterTransition(): EnterTransition {
        return AnimationSpecs.enterFadeIn(AnimationSpecs.DURATION_FAST)
    }

    /**
     * Transición de salida al retroceder
     * Fade Out normal
     */
    fun popExitTransition(): ExitTransition {
        return AnimationSpecs.exitFadeOut(AnimationSpecs.DURATION_NORMAL)
    }
}

