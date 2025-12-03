Nombre del Proyecto: Travelgo

una aplicacion móvil de turismo desarrollada por nosotros, incluytendo un diseño de interfaz, validaciones, navegación, persistencia, backend propio y un consumo de API externa

Integrantes
Sebastián Alejandro
Sebastián Aird

Funcionalidad del proyecto

1.Autenticación
tiene registro de usuario con validaciones de campos
tiene un inicio de sesión persisntente gracias al DataStore
además de un manejo de errores y mensajes de validación

2. Navegacion principal
Navegacion con varias pantallas que usan Jetpack compose navigation
pantalla principal con microinteracciones y animaciones
acceso a una lista de paquetes turisticos

3. gestion de paquetes turisticos
listado completo de paquetes desde el backend
detalle de cada paquete.
tienen tambien una edicion,creacion y eliminacion de paquetes a travez de los microservicios
y un manejo de estados: loading/success/error

4. localizacion
permisos de ubicacion
un fallback implementado en caso de que el usuario no otorgue los permisos
y recursos nativos para el dispositivo

5. persistencia
Usamos datastore para guardar tanto la sesión de los usuarios y las preferencias basicas

6. integración con microservicio backend
comunicacion con el backend spring boot propio
incluyendo operaciones CRUD completas sobre los destinos o paquetes

7.Consumo de API externa 
Se consulta el clima usando OpenWeather API
se deberia de mostrar la informacion del clima relacionada con el destino

Endpoints utilizados

Api Externa:OpenWeather
Base URL: https://api.openweathermap.org/data/2.5

Endpont principal usado:
GET /weather?q={city}&appid={API_KEY}&units=metric&lang=es

Paramentros
https://api.openweathermap.org/data/2.5/weather?q=Santiago&appid=TU_API_KEY

Microservicio Backend (Spring Boot)
Base URL: http://localhost:8080/api

Pasos para ejecutar el proyecto backend (spring boot)
abrir el proyecto backend en intelliJ/eclipse
configurar JDK 17 o superior
ejecutar la clase principal (TravelGoApplication)
confirmar que el backend esta en: http://localhost:8080

Aplicacion android

Ejecución
Abir la carpeta del proyecto en Android Studio
Sincronizar Gradle
Ejecutar en dispositivo fisico o emulador Run

para generar APK firmado
Build > Generate Signed Bundle / APK
Seleccionar APK
Elegir tu archivo .jks
Ingresar contraseña y alias
Generar en: app/release/app-release.apk

Capturas (Agregar Aquí)
APK firmado
(Agregar aquí captura del APK firmado)

Archivo .jks
(Agregar aquí captura del archivo .jks)
