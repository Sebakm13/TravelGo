Sebastian Garrido y Sebastian Aird 
Desarrollo de Aplicaciones Móviles 

TravelGo

Acerca del proyecto, lo primero que realizamos junto a mi compañero fue implementarlo en Android app, utilizando Kotlin y Jetpack Compose.
La idea principal era desarrollar una aplicación funcional que nos permitiera comprender mejor cómo se conectan las diferentes capas bajo el modelo arquitectónico MVVM, además de aprender a consumir datos desde una API usando Retrofit.
Pensamos ponerle a la app un toque mas turístico como el contexto que nos entrego de travel go, aunque principalmente me sirvió como una oportunidad para experimentar, probar nuevas herramientas y mejorar la forma en como organizar el proyecto.
Fuimos probando, mejorando cosas y arreglandoles los errores para que nos funcione cada vez mejor la app.

En tema de Tecnologías y librerías, Está hecha íntegramente en Kotlin. Tambien Empleamos Jetpack Compose para toda la interfaz , ya que asi nos resulto más rápido y limpio que usar XML.
La arquitectura es MVVM, con ViewModel y StateFlow para manejar los estados de la UI.
Para consumir la API use Retrofit con Gson, y para las imágenes Coil, que anda bien con Compose.
También metí Material 3 para los botones y algunos componentes visuales nuevos.
El flujo de datos completo lo gestionamos usando corrutinas, evitando bloqueos y garantizando una app fluida.

Para la Organización del proyecto, Intentamos mantener el proyecto bien ordenado

Y lo organizamos asi:

Primero, tenemos una carpeta "data" que contiene todo sobre el API y modelos.
Luego, está el repositorio, que interactúa con la API y manda datos al ViewModel.
El ViewModel contiene la lógica, manejo de estados y validaciones.
Y por ultimo , "ui" es mas que nada para la parte visual y las pantallas creadas en el Compose.

Nuestra idea era separar bien la lógica de la interfaz y evitar que colapse la app. Por eso empezamos a Experimentar con estructuras y se nos hizo bastante mas facil.

Para ejecutarlo esto es lo que se hace , simplemente abres el proyecto en Android Studio. 
Después de que Gradle sincronice, puedes correrlo en un emulador o en un dispositivo físico.
Lo probamos con el emulador Pixel y ningun problema.
Arranca, mostrando la pantalla principal y el login.


En verdad como una conclusion mas personal del grupo sobre el proyecto y hablar un poco de lo que aprendimos fue que Cuando pusimos la manos en el proyecto comprendímos mejor el uso de Retrofit, cómo lidiar con las respuestas de la API y sin atascar la interfaz de usuario . También  lo que hicimos y aprendimos el manejo de estados en Compose, usando ViewModel, y manteniendo una arquitectura más clara. Ademas, entendímos lo crucial de separar responsabilidades, antes todo en un archivo, ahora es más claro dividir bien el codigo.




