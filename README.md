TravelGo – Aplicación de Turismo Sustentable

Nombre de la aplicación: TravelGo

Aplicación móvil de turismo sustentable desarrollada como proyecto académico, que integra diseño de interfaz moderno, validaciones, navegación estructurada, persistencia local, backend propio con microservicios y consumo de una API externa.


Integrantes del equipo
- Sebastián Alejandro  
- Sebastián Aird  


Funcionalidades del proyecto

Autenticación de usuarios
- Registro de usuarios con validación de campos.
- Inicio de sesión persistente mediante DataStore.
- Manejo de errores y mensajes de validación.
- Cierre de sesión seguro.


Navegación principal
- Navegación entre pantallas usando **Jetpack Compose Navigation**.
- Pantalla principal con animaciones y microinteracciones.
- Acceso a una lista de paquetes turísticos.
- Navegación jerárquica y controlada mediante `NavController`.


Gestión de paquetes turísticos
- Listado completo de paquetes turísticos obtenidos desde el backend.
- Visualización del detalle de cada paquete.
- Creación, edición y eliminación de paquetes.
- Manejo de estados de la aplicación: **loading / success / error**.
- Arquitectura MVVM con separación de responsabilidades.

Localización (recurso nativo)
- Solicitud de permisos de ubicación del dispositivo.
- Obtención de la ubicación del usuario.
- Implementación de fallback en caso de que el usuario no otorgue permisos.
- Uso de recursos nativos del dispositivo Android.

Persistencia local
- Uso de DataStore para almacenar sesión del usuario y preferencias básicas.
- Persistencia local con Room para manejo de datos.
- Soporte para funcionamiento offline.

Integración con microservicios backend
- Comunicación con backend propio desarrollado en Spring Boot.
- Operaciones CRUD completas sobre paquetes/destinos turísticos.
- Consumo de backend a través de API Gateway.

Consumo de API externa
- Integración con OpenWeather API.
- Consulta de información climática según el destino.
- Visualización de datos de clima en la aplicación.

Endpoints utilizados

API externa – OpenWeather
- Base URL: 
- Endpoint principal:
GET /destinations
GET /destinations/{id}
POST /destinations
PUT /destinations/{id}
DELETE /destinations/{id}
- Ejemplo:

Microservicios Backend (Spring Boot + API Gateway)

- Base URL (Gateway):

Instrucciones para ejecutar el proyecto

Backend (Spring Boot)
1. Abrir el proyecto backend en IntelliJ o Eclipse.
2. Configurar JDK 17 o superior.
3. Ejecutar la clase principal.
4. Verificar en:


 Aplicación Android
1. Abrir el proyecto en Android Studio.
2. Sincronizar Gradle.
3. Ejecutar en emulador o dispositivo físico.

APK firmado y archivo .jks

Generación del APK firmado
1. Build > Generate Signed Bundle / APK
2. Seleccionar APK
3. Elegir archivo .jks
4. Ingresar alias y contraseña
5. Generar en:

Evidencia
- Captura del APK firmado
- Captura del archivo .jks

Código fuente incluido

Este repositorio público contiene:
- Código fuente de la aplicación móvil Android.
- Código fuente de los microservicios backend.
- Código del API Gateway.

Evidencia de trabajo colaborativo

El desarrollo del proyecto se realizó de forma colaborativa utilizando GitHub:

- Commits realizados por cada integrante.
- Historial visible en el repositorio público.

📸 Evidencia:
- Captura del historial de commits filtrado por autor.
