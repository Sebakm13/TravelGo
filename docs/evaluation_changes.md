# Sugerencias y cambios de código para pasar la evaluación

Lista condensada de ajustes concretos en código/configuración. Apunta a los archivos exactos que debes modificar.

## App Android
- **Base URL real**: En `app/build.gradle`, define `BuildConfig.BASE_URL` para debug/release tomando la URL del gateway desde `local.properties` (`gateway.url`).
- **Repositorios con Retrofit**: En `app/src/main/java/**/repository/PaqueteRepository.kt` y repositorios similares, reemplaza listas locales por llamadas Retrofit al gateway. Soporta CRUD completo (`get/list`, `create`, `update`, `delete`).
- **Filtros y búsqueda**: En los data sources de paquetes/reservas, agrega parámetros para categoría/destino, rango de precio y texto. Actualiza los `ViewModel` y pantallas de lista para enviar esos parámetros y refrescar resultados.
- **Manejo uniforme de estados**: Crea un `UiState` compartido (`Loading`, `Success`, `Empty`, `Error`) en `app/src/main/java/**/ui/state/UiState.kt` y úsalo en todos los `ViewModel` (login/register, listas, detalle, formularios). Mapea errores de red/serialización a mensajes de UI y agrega acción de reintentar.
- **Flujo post login**: Después del login/register, llama a un endpoint de perfil/sesión desde el `AuthRepository` para validar el token y navegar a pantallas que consumen datos reales.
- **API externa en UI**: Mantén el cliente Retrofit de la API externa (ej. OpenWeather) en `app/src/main/java/**/network/` y muestra sus datos (clima del destino) en una tarjeta/pantalla con estados `loading/error/empty`.
- **CRUD visible**: Habilita crear/editar/eliminar desde la app y refresca la lista tras cada operación (refetch o optimistic update).

## Backend y Gateway
- **Base de datos**: En `BACKEND/docker-compose.yml`, agrega un contenedor de DB (PostgreSQL/Mongo) con variables `DB_HOST`, `DB_USER`, `DB_PASS`, `DB_NAME` y volumen persistente.
- **Microservicios con persistencia**: En cada servicio del dominio (ej. `MICROSERVICIO/servicio-paquetes`), configura el ORM/driver para la DB, crea migraciones/tablas y expone endpoints CRUD reales (`GET/POST/PUT/DELETE`).
- **Gateway unificado**: En `Gateway` centraliza rutas hacia los microservicios, usando las URLs internas del compose. Si usas JWT/API key, agrega middleware para validar tokens.
- **Filtros/búsqueda en backend**: Ajusta endpoints para aceptar query params de categoría/destino, rango de precio y texto, devolviendo resultados paginados.

## Integración App ↔ Backend
- **Interceptors**: En la configuración Retrofit (`app/src/main/java/**/network/RetrofitModule.kt`), agrega interceptores de logging y de autenticación para el token.
- **Errores de red**: Envuelve respuestas en Result/Resource y traduce timeouts o HTTP 4xx/5xx a mensajes claros en `UiState.Error`.
- **Pruebas de integración manual**: Verifica cada endpoint vía gateway con `curl`/Postman antes de conectar la app.

## Pruebas y release
- **Unit tests clave**: Cubre `ViewModel` y `Repository` principales con `MockWebServer`/`Turbine` buscando >=80% de cobertura en esas capas.
- **APK firmado**: Genera `keystore.jks`, configura `signingConfigs` en `app/build.gradle`, arma `assembleRelease` y sube el APK a `AP mas jks/` o `app/build/outputs/apk/release/`.

## Documentación mínima
- **Microservicios**: Añade en `BACKEND/README.md` o `MICROSERVICIO/**/README.md` los endpoints, puertos y pasos para `docker-compose` (incluye ejemplos de requests).
- **Arquitectura Android**: Documenta en `docs/arquitectura.md` el flujo de datos (UI → ViewModel → Repository → Gateway/API externa).
