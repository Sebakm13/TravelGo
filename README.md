# TravelGo SPA

Agencia de viajes digital enfocada en experiencias sustentables y turismo comunitario.
La app busca:
- Modernizar la gestión de reservas y pagos.
- Priorizar paquetes turísticos que apoyen economías locales.
- Promover movilidad y alojamiento responsable.
- Mostrar itinerarios y experiencias con impacto positivo.

## Entidades del dominio
- Paquete Turístico
- Cliente
- Reserva
- Pago
- Itinerario

## Funcionalidad de la app
- Login simulado (cualquier usuario entra).
- Pantalla Home con navegación.
- Listado de Paquetes Turísticos sustentables con imágenes e info.
- Formulario de Reserva con validaciones y feedback visual.
- Perfil del Cliente con:
    - Cambio de foto desde galería (recurso nativo).
    - Guardar nombre en DataStore (persistente).
    - Obtener ubicación GPS actual (recurso nativo).
- Animaciones con AnimatedVisibility.
- Estados de carga/éxito/error simulados.

## Tecnología
- 100% Jetpack Compose + Material 3.
- Navegación Compose.
- DataStore Preferences para persistencia local.
- Coroutines + estados Compose.
- Kotlin 1.9.21 compatible con Compose Compiler 1.5.6.
- compileSdk/targetSdk 35, minSdk 24.
- Tema oscuro moderno con acentos verdes/turquesa.
- Cumple con los puntos de la evaluación:
    - Diseño visual coherente
    - Formularios validados
    - Navegación entre vistas
    - Gestión de estado loading/éxito/error
    - Persistencia local
    - Recursos nativos (galería + GPS)
    - Animaciones con propósito
