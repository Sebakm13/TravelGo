TravelGo

Acerca del proyecto lo primero que realizamos con sebastian aird 

Es que este proyecto lo implementamos con Android con Kotlin y Jetpack Compose. La idea, era armar algo funcional, pero más que nada para entender bien cómo se conectan las diferentes capas empleando MVVM, y como traer datos de una API con Retrofit. La app la pensamos mas como algo de turismo, aunque sobretodo me sirvió para probar cosas nuevas y mejorar la forma en que organizo mis proyectos.

Fuimos probando, rompiendo cosas, arreglandolas, y viendo qué funcionaba mejor.

En tema de Tecnologías y librerías, Está hecha íntegramente en Kotlin. Tambien Empleé Jetpack Compose para toda la interfaz, ya que asi me resulta más rápido y limpio que usar XML.
La arquitectura es MVVM, con ViewModel y StateFlow para manejar los estados de la UI.
Para consumir la API use Retrofit con Gson, y para las imágenes Coil, que anda bien con Compose.
También metí Material 3 para los botones y algunos componentes visuales nuevos.
El flujo de datos completo lo gestionamos usando corrutinas, evitando bloqueos y garantizando una app fluida.

Para la Organización del proyecto, Intentamos mantener el proyecto bien ordenado

Y lo organizamos asi:

Primero, tengo una carpeta "data" que contiene todo sobre la API y modelos.
Luego, está el repositorio, que interactúa con la API y manda datos al ViewModel.
El ViewModel contiene la lógica, manejo de estados y validaciones.
Y por ultimo , "ui" es la parte visual y las pantallas creadas en Compose.

Nuestra idea era separar bien la lógica de la interfaz y evitar que colapse la app. Por eso Experimentamos con estructuras hasta que de esta forma nos resultó mas cómoda.

Para ejecutarlo esto es lo que hicimos profesor y Para probarlo, simplemente abres el proyecto en Android Studio. 
Después de que Gradle sincronice, puedes correrlo en un emulador o en un dispositivo físico.
Lo probamos con el emulador Pixel y ningun problema.
Arranca, mostrando la pantalla principal y el login todo perfecto.

En verdad aprendimos un monton.
Cuando pusimos la manos en el proyecto comprendímos mejor el uso de Retrofit, y cómo lidiar con las respuestas de la API y sin atascar la interfaz de usuario .
También Realizamos y aprendimos el manejo de estados en Compose, usando ViewModel, y manteniendo una arquitectura más clara.
Ademas, entendímos lo crucial de separar responsabilidades, antes todo en un archivo, ahora es más claro dividir bien el codigo.


