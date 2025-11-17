package com.example.vet_clinic_android.util

import java.util.Locale

/**
 * Utilidades para validación de entradas del usuario
 * Incluye manejo robusto de excepciones y valores nulos
 * Usa Regex NATIVO de Kotlin (sin importaciones externas)
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 *
 * ============================================================================
 * NOTA IMPORTANTE SOBRE REGEX EN KOTLIN:
 * ============================================================================
 *
 * Regex es parte de la biblioteca estándar de Kotlin (kotlin.text.Regex)
 * NO necesitas importar ninguna librería externa
 *
 * Formas de usar Regex en Kotlin:
 *
 * 1. Constructor básico:
 *    val regex = Regex("patrón")
 *
 * 2. Método matches() - verifica si coincide completamente:
 *    regex.matches("texto")
 *
 * 3. Método replace() - reemplaza coincidencias:
 *    "texto".replace(Regex("patrón"), "reemplazo")
 *
 * 4. Método find() - busca primera coincidencia:
 *    regex.find("texto")
 *
 * 5. Método findAll() - busca todas las coincidencias:
 *    regex.findAll("texto")
 *
 * Ejemplos de patrones comunes:
 * - Email: ^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$
 * - Teléfono: ^\+?[0-9\s\-()]+$
 * - Solo dígitos: [0-9]+
 * - No dígitos: [^0-9]
 *
 * IMPORTANTE: En Kotlin strings, \ debe escaparse como \\
 * Ejemplo: \d se escribe como \\d
 * ============================================================================
 */

/**
 * Solicita texto con manejo seguro de nulos
 * Usa operador ?: para valor por defecto
 */
fun solicitarTexto(mensaje: String, valorPorDefecto: String = ""): String {
    while (true) {
        print(mensaje)
        val entrada = readLine()

        // Operador Elvis ?: para manejar null
        if (entrada.isNullOrBlank()) {
            if (valorPorDefecto.isNotEmpty()) {
                println("Valor por defecto aplicado: $valorPorDefecto")
                return valorPorDefecto
            } else {
                println("❌ El valor no puede estar vacío. Intente nuevamente.")
            }
        } else {
            return entrada.trim()
        }
    }
}

/**
 * Solicita texto opcional (puede ser null)
 * Usa operador Elvis ?: para asignar valor por defecto si es nulo
 */
fun solicitarTextoOpcional(mensaje: String, mensajePorDefecto: String = "Sin información"): String {
    print(mensaje)
    val entrada = readLine()

    // Operador Elvis ?: - Si entrada es null o blank, retorna mensajePorDefecto
    return entrada?.takeIf { it.isNotBlank() }?.trim() ?: mensajePorDefecto
}

/**
 * VALIDACIONES PARA ESPAÑOL LATINO
 * Soporta: acentos (áéíóú), ñ, Ñ, espacios
 */

/**
 * Valida email con formato correcto
 * Patrón: usuario@dominio.extensión
 */
fun validarEmail(email: String): ValidationResult {
    if (email.isBlank()) {
        return ValidationResult(false, "El email no puede estar vacío")
    }

    // Regex para email - permite caracteres especiales comunes
    val emailRegex = Regex("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,}$")

    return if (emailRegex.matches(email)) {
        ValidationResult(true, "Email válido")
    } else {
        ValidationResult(false, "Formato de email inválido. Ejemplo: usuario@dominio.com")
    }
}

/**
 * Valida teléfono chileno
 * Acepta: +56912345678, 912345678, (9) 1234-5678, etc.
 */
fun validarTelefono(telefono: String): ValidationResult {
    if (telefono.isBlank()) {
        return ValidationResult(false, "El teléfono no puede estar vacío")
    }

    // Eliminar espacios, guiones y paréntesis para validar
    val telefonoLimpio = telefono.replace(Regex("[\\s\\-()]"), "")

    // Validar formato chileno: +56XXXXXXXXX o XXXXXXXXX (9 dígitos)
    val telefonoRegex = Regex("^(\\+?56)?[9876][0-9]{8}$")

    return if (telefonoRegex.matches(telefonoLimpio)) {
        ValidationResult(true, "Teléfono válido")
    } else {
        ValidationResult(false, "Formato de teléfono inválido. Ejemplo: +56912345678 o 912345678")
    }
}

/**
 * Valida nombre con acentos y ñ
 * Acepta: María José, José Ñuñez, etc.
 */
fun validarNombre(nombre: String): ValidationResult {
    if (nombre.isBlank()) {
        return ValidationResult(false, "El nombre no puede estar vacío")
    }

    if (nombre.length < 2) {
        return ValidationResult(false, "El nombre debe tener al menos 2 caracteres")
    }

    // Regex que acepta letras (incluye acentos y ñ), espacios y apóstrofes
    val nombreRegex = Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ][a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s']+$")

    return if (nombreRegex.matches(nombre)) {
        ValidationResult(true, "Nombre válido")
    } else {
        ValidationResult(false, "El nombre solo puede contener letras, espacios y acentos")
    }
}

/**
 * Valida RUT chileno (formato: 12345678-9)
 */
fun validarRut(rut: String): ValidationResult {
    if (rut.isBlank()) {
        return ValidationResult(false, "El RUT no puede estar vacío")
    }

    // Eliminar puntos y espacios
    val rutLimpio = rut.replace(Regex("[.\\s]"), "")

    // Validar formato: 7-8 dígitos, guión, dígito o K
    val rutRegex = Regex("^[0-9]{7,8}-[0-9Kk]$")

    return if (rutRegex.matches(rutLimpio)) {
        // Aquí podrías agregar validación del dígito verificador si lo necesitas
        ValidationResult(true, "RUT válido")
    } else {
        ValidationResult(false, "Formato de RUT inválido. Ejemplo: 12345678-9")
    }
}

/**
 * Valida dirección
 * Acepta: Av. O'Higgins #123, Calle Ñuñoa 456 Depto 12, etc.
 */
fun validarDireccion(direccion: String): ValidationResult {
    if (direccion.isBlank()) {
        return ValidationResult(false, "La dirección no puede estar vacía")
    }

    if (direccion.length < 5) {
        return ValidationResult(false, "La dirección debe tener al menos 5 caracteres")
    }

    // Regex que acepta letras, números, espacios, acentos, ñ y símbolos comunes (#, ., ', -)
    val direccionRegex = Regex("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑüÜ\\s#.'\\-,]+$")

    return if (direccionRegex.matches(direccion)) {
        ValidationResult(true, "Dirección válida")
    } else {
        ValidationResult(false, "La dirección contiene caracteres no permitidos")
    }
}

/**
 * Valida edad
 */
fun validarEdad(edad: String): ValidationResult {
    if (edad.isBlank()) {
        return ValidationResult(false, "La edad no puede estar vacía")
    }

    val edadNum = edad.toIntOrNull()

    return when {
        edadNum == null -> ValidationResult(false, "La edad debe ser un número")
        edadNum < 0 -> ValidationResult(false, "La edad no puede ser negativa")
        edadNum > 30 -> ValidationResult(false, "La edad parece demasiado alta para una mascota")
        else -> ValidationResult(true, "Edad válida")
    }
}

/**
 * Valida peso
 */
fun validarPeso(peso: String): ValidationResult {
    if (peso.isBlank()) {
        return ValidationResult(false, "El peso no puede estar vacío")
    }

    val pesoNum = peso.toDoubleOrNull()

    return when {
        pesoNum == null -> ValidationResult(false, "El peso debe ser un número")
        pesoNum <= 0 -> ValidationResult(false, "El peso debe ser mayor a 0")
        pesoNum > 200 -> ValidationResult(false, "El peso parece demasiado alto")
        else -> ValidationResult(true, "Peso válido")
    }
}

/**
 * Valida cantidad (números enteros positivos)
 */
fun validarCantidad(cantidad: String): ValidationResult {
    if (cantidad.isBlank()) {
        return ValidationResult(false, "La cantidad no puede estar vacía")
    }

    val cantidadNum = cantidad.toIntOrNull()

    return when {
        cantidadNum == null -> ValidationResult(false, "La cantidad debe ser un número entero")
        cantidadNum <= 0 -> ValidationResult(false, "La cantidad debe ser mayor a 0")
        cantidadNum > 100 -> ValidationResult(false, "La cantidad máxima es 100")
        else -> ValidationResult(true, "Cantidad válida")
    }
}

/**
 * Formatea teléfono a formato estándar chileno
 * Ejemplo: 912345678 -> +56 (9) 1234-5678
 */
fun formatearTelefono(telefono: String): String {
    val limpio = telefono.replace(Regex("[^0-9]"), "")

    return when {
        limpio.length == 9 && limpio.startsWith("9") -> {
            "+56 (${limpio.substring(0, 1)}) ${limpio.substring(1, 5)}-${limpio.substring(5)}"
        }
        limpio.length == 11 && limpio.startsWith("56") -> {
            val numero = limpio.substring(2)
            "+56 (${numero.substring(0, 1)}) ${numero.substring(1, 5)}-${numero.substring(5)}"
        }
        else -> telefono
    }
}

// ==================== VALIDACIÓN CON REGEX (HELPERS COMPATIBLES) ====================
// NOTA: Para evitar conflictos de sobrecarga y mantener compatibilidad con llamadas antiguas,
// proveemos helpers que retornan Boolean con nombres distintos.

/**
 * Helper booleano: valida email (compatibilidad)
 */
fun esEmailValido(email: String?): Boolean {
    if (email.isNullOrBlank()) return false
    return validarEmail(email.trim()).isValid
}

/**
 * Solicita email con validación usando la API unificada
 */
fun solicitarEmailValido(mensaje: String = "Ingrese email: "): String {
    while (true) {
        print(mensaje)
        val email = readLine()?.trim()

        if (email.isNullOrBlank()) {
            println("❌ ERROR: El email no puede estar vacío")
            println("   Por favor, intente nuevamente.")
            continue
        }

        if (validarEmail(email).isValid) {
            return email
        } else {
            println("❌ ERROR: Formato de email inválido")
            println("   El email debe seguir el formato: nombre@dominio.com")
            println("   Ejemplos válidos:")
            println("   • juan.perez@gmail.com")
            println("   • maria_lopez@empresa.cl")
            println("   • contacto@veterinaria.com")
            println("   Por favor, intente nuevamente.")
        }
    }
}

/**
 * Valida y corrige email con operador Elvis ?:
 * Retorna valor predeterminado si es inválido
 */
fun validarYCorregirEmail(email: String?): String {
    val emailSeguro = email?.trim() ?: ""
    return if (validarEmail(emailSeguro).isValid) {
        emailSeguro
    } else {
        println("⚠️  Email inválido. Usando valor predeterminado: correo@invalido.com")
        "correo@invalido.com"
    }
}

/**
 * Valida email y usa let para ejecutar solo si es válido
 */
fun validarEmailConAccion(email: String?, accion: (String) -> Unit): Boolean {
    return email?.takeIf { esEmailValido(it) }?.let { emailValido ->
        accion(emailValido)
        true
    } ?: false
}

// ==================== VALIDACIÓN Y FORMATEO DE TELÉFONO (HELPERS COMPATIBLES) ====================

/**
 * Helper booleano: valida teléfono (compatibilidad)
 */
fun esTelefonoValido(telefono: String): Boolean {
    return validarTelefono(telefono).isValid
}

/**
 * Solicita teléfono con validación y formateo automático usando API unificada
 */
fun solicitarTelefonoValido(mensaje: String = "Ingrese teléfono: "): String {
    while (true) {
        print(mensaje)
        val telefono = readLine()?.trim()

        if (telefono.isNullOrBlank()) {
            println("❌ ERROR: El teléfono no puede estar vacío")
            println("   Por favor, intente nuevamente.")
            continue
        }

        val resultado = validarTelefono(telefono)
        if (resultado.isValid) {
            val telefonoFormateado = formatearTelefono(telefono)
            println("✅ Teléfono formateado: $telefonoFormateado")
            return telefonoFormateado
        } else {
            println("❌ ERROR: ${resultado.message}")
            println("   Ejemplos válidos:")
            println("   • 912345678")
            println("   • +56912345678")
            println("   • +56 9 1234 5678")
            println("   Por favor, intente nuevamente.")
        }
    }
}

// ==================== VALIDACIÓN CON RANGES ====================

/**
 * Valida que un número entero esté dentro de un rango
 * Usa el operador 'in' con IntRange
 */
fun validarEnteroEnRango(numero: Int, rango: IntRange): Boolean {
    return numero in rango
}

/**
 * Valida que un decimal esté dentro de un rango
 * Usa comparaciones con ClosedRange
 */
fun validarDecimalEnRango(numero: Double, minimo: Double, maximo: Double): Boolean {
    return numero in minimo..maximo
}

/**
 * Solicita entero dentro de un rango específico
 * Usa Ranges para validación
 */
fun solicitarEnteroEnRango(mensaje: String, rango: IntRange): Int {
    while (true) {
        print("$mensaje (${rango.first}-${rango.last}): ")
        val entrada = readLine()

        val numero = entrada?.toIntOrNull()

        if (numero == null) {
            println("❌ ERROR: Debe ingresar un número entero válido")
            continue
        }

        if (numero in rango) {
            return numero
        } else {
            println("❌ ERROR: El número debe estar entre ${rango.first} y ${rango.last}")
            println("   Valor ingresado: $numero")
        }
    }
}

/**
 * Solicita un número decimal dentro de un rango específico
 * Usa ClosedRange para validación
 */
fun solicitarDecimalEnRango(mensaje: String, minimo: Double, maximo: Double): Double {
    while (true) {
        print("$mensaje (${String.format(Locale.getDefault(), "%.2f", minimo)}-${String.format(Locale.getDefault(), "%.2f", maximo)}): ")
        val entrada = readLine()

        val numero = entrada?.toDoubleOrNull()

        if (numero == null) {
            println("❌ ERROR: Debe ingresar un número decimal válido (use punto como separador)")
            continue
        }

        if (numero in minimo..maximo) {
            return numero
        } else {
            println("❌ ERROR: El número debe estar entre $minimo y $maximo")
            println("   Valor ingresado: $numero")
        }
    }
}

/**
 * Clase de datos para el resultado de validación
 * @property isValid Indica si la validación fue exitosa
 * @property message Mensaje descriptivo de la validación
 */
data class ValidationResult(
    val isValid: Boolean,
    val message: String
)
