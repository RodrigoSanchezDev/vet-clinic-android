# Vet Clinic Android

![Status](https://img.shields.io/badge/status-active-brightgreen.svg)
![Android](https://img.shields.io/badge/Android-24%2B-blue.svg)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9%2B-7f52ff.svg)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-orange.svg)
![License](https://img.shields.io/badge/license-MIT-black.svg)

> Plataforma Android nativa para gestionar una clínica veterinaria moderna: registro guiado de consultas, tablero con métricas, módulos avanzados (reflection, operadores sobrecargados, validación de duplicados) y una experiencia 100% Jetpack Compose.

Desarrollado por **Rodrigo Sánchez** · [rodrigo@sanchezdev.com](mailto:rodrigo@sanchezdev.com) · [sanchezdev.com](https://sanchezdev.com)

---

## 🧰 Stack & Integraciones
- **Kotlin + Coroutines/StateFlow** para un manejo reactivo de formularios y estados.
- **Jetpack Compose Material 3**: theming dinámico, componentes responsivos y banners reutilizables (`BannerCard`).
- **Navigation Compose** con `NavHost` y rutas tipadas para más de 15 pantallas (incluye `IntroScreen`).
- **ViewModel + Repository (services)** para desacoplar la lógica de negocio (consultas, dueños, mascotas, veterinarios).
- **Gradle Wrapper** con soporte `minSdk 24` vía *core library desugaring* (java.time en API bajas).
- **Lint personalizado + utilidades de validación** (Regex, Ranges, ClosedRange helpers).
- **Kotlin features avanzados**: operator overloading (`plus`, `equals`), destructuring, reflection y anotaciones personalizadas para promociones.

## 🗂️ Tabla de Contenidos
1. [Descripción General](#-descripción-general)
2. [Principales Funcionalidades](#-principales-funcionalidades)
3. [Arquitectura & Organización](#-arquitectura--organización)
4. [Requisitos y Configuración](#-requisitos-y-configuración)
5. [Uso y Flujo de Trabajo](#-uso-y-flujo-de-trabajo)
6. [Calidad, Pruebas y Automatización](#-calidad-pruebas-y-automatización)
7. [Capturas de Pantalla](#-capturas-de-pantalla)
8. [Roadmap](#-roadmap)
9. [Contribuciones](#-contribuciones)
10. [Autor](#-autor)
11. [Licencia](#-licencia)

## 📋 Descripción General
Vet Clinic Android es una app de demostración profesional que aglutina la mayoría de los flujos reales de una clínica veterinaria: alta de pacientes, dueños y consultas, métricas instantáneas y módulos "sandbox" para exhibir patrones avanzados de Kotlin. El objetivo es evidenciar buenas prácticas de Compose, navegación declarativa y separación de responsabilidades.

## ✨ Principales Funcionalidades
### Experiencia de usuario
- **IntroScreen** con CTA directo al menú, branding consistente y soporte para deep links.
- **HomeScreen** tipo dashboard con cuadrícula de accesos r��pidos y banners centrados.
- **Flujo multistep** para registrar consultas (mascota → dueño → consulta → resumen con banner de éxito).

### Gestión de consultas
- Listado completo, filtros de pendientes/programadas y chips de estado dinámicos.
- Resúmenes imprimibles, cálculo automático de costos y descuentos por múltiples mascotas.
- Servicios (`ConsultaService`) con generación de IDs, informes y estadísticas agregadas.

### Veterinarios & Medicamentos
- Agenda, estadísticas y búsqueda especializada de veterinarios.
- Creación de pedidos con validaciones numéricas, promociones detectadas vía annotations y cards informativas.
- Comparador de medicamentos (`==` overloading), combinación de pedidos (`+`) y detección de duplicados.

### Utilidades avanzadas
- `Validaciones.kt` reúne helpers de Regex, ranges, formateo y prompts seguros.
- Reflection Screen para inspeccionar metadatos de modelos.
- Desestructuración de data classes, operadores custom y reporte integrado final.

## 🧱 Arquitectura & Organización
```
app/
├── src/main/java/com/example/vet_clinic_android/
│   ├── model/               # Data classes, operators, annotations
│   ├── service/             # Lógica de negocio y repositorios en memoria
│   ├── ui/
│   │   ├── components/      # Compose reutilizables (BannerCard, etc.)
│   │   ├── screens/         # Secciones organizadas por dominio
│   │   ├── navigation/      # Screen sealed class + NavHost
│   │   └── viewmodels/      # VetClinicViewModel con StateFlow
│   └── util/                # Validaciones y formateadores
└── SOLUCION_API_24.md       # Documenta soporte a minSdk 24
```
- **Presentación (UI)**: 100% Compose, desacoplada mediante parámetros y ViewModel.
- **Dominio/Servicios**: clases Kotlin puras reutilizables (mascota, dueño, consulta, veterinario, pedidos).
- **Infraestructura**: Gradle con módulos únicos, lint configurado y scripts para verificación.

## 🛠️ Requisitos y Configuración
### Requisitos
- Android Studio Giraffe (o superior).
- Android SDK 24+ instalado.
- JDK 11 (el wrapper lo gestiona automáticamente).

### Pasos
```bash
# 1. Clonar el repositorio
 git clone https://github.com/<usuario>/vet-clinic-android.git
 cd vet-clinic-android

# 2. Sincronizar dependencias y verificar build
 ./gradlew clean assembleDebug
```
> Tip: el script `run-app.sh` incluye comandos abreviados para compilar y lanzar el emulador.

## 🧭 Uso y Flujo de Trabajo
1. **Bienvenida** → pulsa "Comenzar" para entrar al dashboard.
2. **Menú principal** → accede a registros de consultas, estadísticas, agenda veterinaria, promociones, reflection, etc.
3. **Registro de consulta** → completa los tres formularios; al terminar se genera un resumen con ID y costos formateados.
4. **Módulos avanzados** → prueba la comparación de medicamentos, la combinación de pedidos, validaciones de duplicados y el reporte integrado para ver cómo se aplican features de Kotlin.

## ✅ Calidad, Pruebas y Automatización
- Build probado con `./gradlew assembleDebug` (logs en `build_output.log`).
- `.gitignore` optimizado: excluye `.gradle`, `build/`, `.idea/*` salvo estilos y `local.properties`.
- Lint configurado para reconocer desugaring (`java.time`) y prevenir falsos positivos `NewApi`.
- Servicios con manejo robusto de excepciones, validaciones centralizadas y métricas numéricas.

## 🖼️ Capturas de Pantalla
Coloca tus assets en `docs/screenshots/` y referencia aquí:
```
![Intro](docs/screenshots/intro.png)
![Dashboard](docs/screenshots/dashboard.png)
![Registro Completo](docs/screenshots/register-success.png)
```
(Las capturas incluidas en este repositorio muestran el banner de éxito y el hero del dashboard.)

## 🛣️ Roadmap
- Persistencia local (Room o DataStore) para historiales entre sesiones.
- Integración REST para agenda de veterinarios y stock real de medicamentos.
- Tests instrumentados de flujos Compose y pruebas unitarias para servicios.
- Dark theme y adaptaciones para tablets/foldables.

## 🤝 Contribuciones
1. Haz un fork y crea una rama descriptiva (`feature/nueva-funcionalidad`).
2. Añade pruebas o demo en `docs/` cuando corresponda.
3. Ejecuta `./gradlew lint assembleDebug` antes de abrir el PR.
4. Describe claramente el problema que resuelves o la feature que agregas.

## 👤 Autor
**Rodrigo Sánchez**  
Email: [rodrigo@sanchezdev.com](mailto:rodrigo@sanchezdev.com)  
Portafolio: [sanchezdev.com](https://sanchezdev.com)

## 📄 Licencia
Este proyecto se distribuye bajo la [MIT License](./LICENSE).
