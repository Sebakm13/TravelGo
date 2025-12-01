# Checklist de acciones de código para cumplir los requisitos

Guía concreta de cambios de código y configuraciones necesarias. Sigue la prioridad sugerida y marca cada punto al completarlo.

## 1) Backend y gateway (CRUD real)
- [ ] En `BACKEND/docker-compose.yml`, añade un contenedor de base de datos (PostgreSQL o MongoDB) y variables de entorno (`DB_HOST`, `DB_USER`, `DB_PASS`, `DB_NAME`). Expone puertos y volúmenes para persistencia.
- [ ] En cada microservicio del dominio (por ejemplo `MICROSERVICIO/servicio-paquetes`), configura el ORM/driver con la base de datos real y crea migraciones/tablas básicas.
- [ ] Implementa CRUD real en al menos un servicio (paquetes/reservas), con endpoints `GET /items`, `GET /items/{id}`, `POST /items`, `PUT /items/{id}`, `DELETE /items/{id}`.
- [ ] En `Gateway` unifica rutas y asegura que reenvíen hacia los microservicios con la baseUrl interna del docker-compose. Agrega validación de token si corresponde.
- [ ] Verifica con `curl` o `Postman` contra el gateway que cada endpoint responda (incluye ejemplos en la documentación).

## 2) App Android: consumo real y manejo de estados
- [ ] Define `BuildConfig.BASE_URL` apuntando al gateway (debug/release) en `app/build.gradle` y usa `local.properties` para claves/apikeys.
- [ ] En los clientes Retrofit (`app/src/main/java/.../network/*.kt`), agrega interceptores para log y token, y maneja timeouts.
- [ ] Reemplaza los repositorios que usan listas locales (por ejemplo `PaqueteRepository` en `app/src/main/java/...`) por llamadas Retrofit a los endpoints del gateway, incluyendo CRUD.
- [ ] Estandariza un `UiState` (`Loading`, `Success`, `Empty`, `Error`) y úsalo en todos los `ViewModel` (listas, detalle, login/register, formularios). Mapea errores de red/serialización a mensajes de UI y agrega botón de reintentar.
- [ ] Implementa filtros y búsqueda server-side: la UI debe enviar categoría/destino, rango de precio y query de texto; el backend debe soportar esos parámetros.
- [ ] Después de login/register, valida el token con un endpoint de perfil/sesión y navega a pantallas que consuman datos reales.
- [ ] CRUD desde la app: permite crear/editar/eliminar paquetes o reservas y refleja el resultado en la lista (optimistic update o refetch).

## 3) API externa con Retrofit y UI
- [ ] Mantén/crea un cliente Retrofit para la API externa (ej. OpenWeather) con la key en `local.properties` y `BuildConfig`.
- [ ] Muestra datos de la API externa en la UI (por ejemplo, clima del destino seleccionado) con estados `loading/error/empty` y opción de reintentar.

## 4) Pruebas unitarias y cobertura
- [ ] Crea al menos 6–7 pruebas unitarias enfocadas en `ViewModel` y `Repository` usando `MockWebServer` y `Turbine` para flujos de `Flow`/`StateFlow`.
- [ ] Apunta a ≥80% de cobertura en ViewModel/Repository y genera un reporte (`./gradlew testDebugUnitTest` con jacoco si aplica).
- [ ] Graba un video corto mostrando la ejecución de las pruebas y agrega el enlace en la documentación.

## 5) APK release firmado
- [ ] Genera `keystore.jks` y agrega configuración de firma en `app/build.gradle` (`signingConfigs` y `buildTypes.release`), leyendo credenciales desde `local.properties` (no subir claves).
- [ ] Genera el APK de release (`./gradlew assembleRelease`), verifica que inicia y súbelo al repo (por ejemplo en `AP mas jks/` o `app/build/outputs/apk/release/`).

## 6) Documentación y evidencias
- [ ] Documenta arquitectura Android y flujo de datos (UI, domain, data, API externa) en `docs/arquitectura.md`.
- [ ] Documenta microservicios: endpoints, puertos, variables y pasos para levantar `docker-compose` en `BACKEND/README.md` o `MICROSERVICIO/**/README.md`.
- [ ] Incluye ejemplos de requests/responses y credenciales dummy de prueba.
- [ ] Añade referencias a tablero Trello/issues y a ramas/PRs de colaboración.

## 7) Orden recomendado
1. Levantar DB y validar CRUD vía gateway.
2. Conectar la app a los endpoints reales con manejo de estados y filtros/búsqueda.
3. Implementar CRUD desde la app y pruebas unitarias hasta lograr la cobertura objetivo.
4. Documentar, generar APK release firmado y subir APK + video de pruebas.
