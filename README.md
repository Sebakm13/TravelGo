Nombre del Proyecto: TravelGo

1. Caso elegido y su alcance
- el caso escogido fue TravelGo, en donde tenemos que realizar una aplicación móvil donde se gestiónen viajes.
- alcance del proyecto: el proyecto incluye un diseño/UI, validaciones, una navegación simple, gestioness de estado y persostencia local, ademas de recursos nativos, animaciones y un consumo de Api (incluyendo'/me')

2. Requisitos y ejecución del proyecto

-Stack contiene:
-el lengiaje Kotlin
-una UI con Jetpack Compose + material 3
-una arquitectura MVVM con ViewModel Y StateFlow
- Librerías: Retrofit, Gson, Coil y Coroutines

-Instalación
   -Primero clonar el repositorio
   git clone https://github.com/Sebakm13/TravelGo.git
   -segundo paso Abrir el proyecto en Android Studio
   -Tercer paso, esperar la sincronizacion de Gradle

- Ejecución:
  -Seleccionar un emulador o dispositivo físico (preferiblemente el Pixel 7 PRO).
  -Ejecutar con el botón Run ▶️.
  -La app inicia mostrando la pantalla principal y luego el login, donde habran más opciones      uha222  1para navegar.

3. Arquitectura y flujo del proyecto
Sigue la arquitectura MVVM, separando la lógica de negocio de la interfaz de usuario

├──app
|  ├──manifests
|  |  ├──AndroidManifest.xml
|  ├──kotlin+java
|  |  ├──data
|  |  |  ├──dao
|  |  |  |  ├── paquereDao
|  |  |  |  ├── UserDao.kt
|  |  |  ├── datastore
|  |  |  |  ├──UserPredsDataStore.kt
|  |  |  ├──db
|  |  |  |  ├──AppDatabase
|  |  |  |  ├──DatabaseProvider
|  |  |  |  ├──PaqueteLocal
|  |  |  ├──local
|  |  |  |  ├──SessionManager
|  |  |  ├──model
|  |  |  |  ├──Entities.kt
|  |  |  |  ├──User
|  |  |  ├──remote
|  |  |  |  ├──dto
|  |  |  |  |  ├──LoginRequest
|  |  |  |  |  ├──LoginResponse
|  |  |  |  |  ├──UserDto
|  |  |  |  |  ├──UsersResponse
|  |  |  |  ├──ApiService
|  |  |  |  ├──AuthInterceptor
|  |  |  |  ├──RetrofitClient
|  |  |  ├──Repository
|  |  |  |  ├──AvatarRepository.kt
|  |  |  |  ├──PaqueteRepository
|  |  |  |  ├──UserRepository
|  |  |  ├──Paquete
|  |  |  ├──PaqueteRepository
|  |  ├──ui
|  |  |  ├──components
|  |  |  |  ├──ImagePickerDialog.kt
|  |  |  |  ├──TopBarWithBack
|  |  |  ├──main
|  |  |  |  ├──NavGraph.kt
|  |  |  ├──screens
|  |  |  |  ├──HomeScreen.kt 
|  |  |  |  ├──LoginScreen.kt
|  |  |  |  ├──PaqueteDetailScreen.kt
|  |  |  |  ├──PaqueteEditScreen.kt
|  |  |  |  ├──PaqueteListScreen.kt
|  |  |  |  ├──PaquetesScreen.kt
|  |  |  |  ├──PerfilScreen.kt
|  |  |  |  ├──ReservaScreen.kt
|  |  |  ├──theme
|  |  |  |  ├──Color.kt
|  |  |  |  ├──Theme.kt
|  |  |  |  ├──Type.kt
|  |  |  ├──PaqueteViewModel
|  |  ├──utils
|  |  |  ├──ImafeUtils
|  |  ├──viewmodel
|  |  |  ├──ProfileViewModel.kt
|  |  ├──MainActivity
|  |  ├──TravelGoApp.kt
|  ├──res
├──Gradle Screipts

-Gestión de estado
  -StateFlow y MutableStateFlow son usados para emitir estados de carga, exito o error.
  -los ViewModel centralizan la lógica y exponen estados observables para compose

-Navegación
  -La implementamos con NavHost y NavController
  - el flujo principal siendo el login -> Home -> Perfil

4. Funcionalidades
  - Formulario validado de inicio de sesión y registro.  
  - Navegación entre pantallas con backstack funcional.  
  - Gestión de estado mediante ViewModel y StateFlow (carga, éxito, error).  
  - Persistencia local (almacenamiento de sesión y perfil con `SharedPreferences`).  
  - Almacenamiento de imagen de perfil utilizando Coil.  
  - Recursos nativos: acceso a cámara y galería (con permisos y fallback).  
  - Animaciones con propósito (transiciones entre pantallas).  
  - Consumo de API externa** con Retrofit, incluyendo el endpoint `/me`.

5. Endpoints
Base URL: http://10.0.2.2:8080/api/
| Método | Ruta | Body | Respuesta |
| ------ | ---- | ----- | ---------- |
| **POST** | `/auth/signup` | `{ email, password, name }` | 201 → `{ authToken, user: { id, email, name } }` |
| **POST** | `/auth/login` | `{ email, password }` | 200 → `{ authToken, user: { id, email } }` |
| **GET** | `/auth/me` | - (requiere `Authorization` header) | 200 → `{ id, email, name, avatarUrl }` |

6. User flows
Flujo principal de usuario
  -inicio de sesión: el usuario ingresa correo y contraseña, si llega a ser válido pasa al       home pero si falla, muestra un mensaje de error

  -Home: muestra las opciones paquetes sustentables, reservar experiencias y tu perfil
  -paquetes sustentables, muestra viajes que se pueden realizar en el momento
  -reservar experiencias, permite el realizar reservas de los viajes en los paquetes             sustentables para elejir una fecha ideal para tus necesidades
  -tu perfil, personaliza tu perfil para mostrar tu esencia (y algunos datos relevantes)
  -cerrar sesión, limpia los datos y te devuelve al login

7. autores

  -Sebastián garrido y sebastián Aird
  -desarrollo de aplicaciones móviles -EP3 2025

8. reflexion

-durante el tiempo que estubimos trabajando en este trabajo aprendimos no solo a optimizar nuestros tiempos de mejor manera, pero tambien el colaborar en caso de que alguien necesite apoyo en algunas areas, tambien el priorizar lo esencial antes que los pequeños detalles
