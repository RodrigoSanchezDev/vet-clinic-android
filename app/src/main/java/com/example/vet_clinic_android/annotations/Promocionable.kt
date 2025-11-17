package com.example.vet_clinic_android.annotations

/**
 * Anotación personalizada para marcar medicamentos elegibles para promociones
 *
 * Esta anotación se utiliza en conjunto con el sistema de Reflection para
 * identificar automáticamente productos con descuentos especiales.
 *
 * @property descuento Porcentaje de descuento aplicable (0-100)
 * @property descripcion Descripción de la promoción
 *
 * @see com.example.vet_clinic_android.service.ReflectionService
 * @see com.example.vet_clinic_android.model.MedicamentoProducto
 *
 * @author Rodrigo Sánchez
 * @contact rodrigo@sanchezdev.com
 * @website sanchezdev.com
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Promocionable(
    val descuento: Int = 10,
    val descripcion: String = "Promoción especial"
)

