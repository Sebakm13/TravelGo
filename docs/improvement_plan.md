# Plan de mejora para cumplir los requisitos

Este documento resume tareas prioritarias para cubrir los puntos pendientes en la app Android, los microservicios y la integración.

## Flujo de la app (lista, filtros, búsqueda, flujo post login)
1. **Conectar listas a servicios reales**
   - Reemplazar la lista en memoria en `app/src/main/java/.../PaqueteRepository.kt` por llamadas Retrofit al gateway.
   - Exponer estados `loading/error/empty` en los `ViewModel` de listados.
2. **Filtros y búsqueda server-side**
   - Ajustar endpoints del gateway para aceptar parámetros de filtro (categoría/destino, rango de precio) y query de texto.
   - Actualizar la UI para enviar los parámetros y mostrar resultados.
3. **Completar el flujo más allá de login/register**
   - Tras autenticación, consumir un endpoint de perfil/sesión para validar token.
   - Navegar a pantallas que consuman datos reales (paquetes, reservas, pagos) usando el token.

## Lógica y manejo de estados
1. **Estados en todas las pantallas**
   - Estandarizar un `UiState` con `Loading`, `Success`, `Empty`, `Error` y usarlo en listas, detalles, login/register y formularios.
   - Mostrar `ProgressIndicator`, `EmptyState` y `ErrorState` con retry.
2. **Validaciones visuales**
   - En formularios (login, registro, reservas) mostrar errores por campo, deshabilitar botones mientras hay errores y manejar teclado.

## Backend (microservicios) y gateway
1. **Persistencia real**
   - Añadir contenedores de base de datos (PostgreSQL/Mongo) en `docker-compose.yml` dentro de `BACKEND`.
   - Configurar ORM/migrations y verificar CRUD real en al menos un servicio (por ejemplo, `MICROSERVICIO/servicio-paquetes`).
2. **Conexión vía gateway**
   - Unificar rutas en `Gateway` para todos los microservicios y probar con `curl`/`postman` que respondan.
   - Implementar seguridad (JWT o API key) si es requerida por la app móvil.

## Integración App ↔ Backend
1. **Retrofit apuntando al gateway**
   - Centralizar la `baseUrl` en `BuildConfig` y usar interceptores para token y logging.
2. **CRUD desde la app**
   - Implementar creación/edición/eliminación de paquetes o reservas desde la UI y propagar al backend.
3. **Errores de red/serialización**
   - Manejar excepciones en repositorios y mapear a `UiState.Error` con mensajes amigables.

## API externa (obligatoria)
1. **Usar Retrofit real**
   - Mantener la integración OpenWeather (u otra API) con clave en `local.properties` y endpoint en `BuildConfig`.
2. **Mostrar datos en UI**
   - Incluir una pantalla/carta que muestre el clima del destino seleccionado y estados de carga/error.

## Pruebas unitarias
1. **Cobertura en ViewModel/Repository**
   - Crear al menos 6–7 tests con `MockWebServer` y `Turbine` para flujos de `ViewModel`.
   - Apuntar a >=80% de cobertura en ViewModel/Repository.
2. **Evidencia en video**
   - Grabar ejecución de pruebas (CLI o Android Studio) y agregar enlace en el repo.

## APK Release
1. **Firma**
   - Generar `keystore.jks`, configurar `signingConfigs` y `buildTypes.release` en `app/build.gradle`.
   - Asegurar `minifyEnabled`/`proguard` si aplica y subir el APK firmado al repositorio (`AP mas jks/` o `/build/outputs/apk/release`).

## Documentación
1. **Arquitectura Android**
   - Documentar capas (UI, domain, data), flujo de datos y uso de API externa en `docs/arquitectura.md`.
2. **Microservicios**
   - Documentar endpoints, puertos y funciones en `BACKEND/README.md` o `MICROSERVICIO/**/README.md`.
   - Agregar instrucciones claras para levantar `docker-compose` y variables de entorno.

## GitHub y colaboración
1. **Commits y ramas**
   - Usar ramas por feature/bugfix y mensajes de commit consistentes.
2. **Trello/Issues**
   - Mantener tablero con tareas/estados y vincular issues/PRs a las ramas correspondientes.

## Orden sugerido de ejecución
1. Levantar microservicios con DB y validar CRUD vía gateway.
2. Conectar la app Android a los endpoints reales con manejo de estados.
3. Añadir CRUD en la app y pruebas unitarias hasta alcanzar cobertura objetivo.
4. Documentar, generar APK release firmado y subir evidencia (video + APK).
