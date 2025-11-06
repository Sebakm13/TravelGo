package com.travelgo.app.data.model

data class PaqueteTuristico(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val precioPorPersona: Int,
    val destino: String,
    val imagenUrl: String,
    val enfoqueSustentable: String
)

// Cliente
data class Cliente(
    val nombre: String,
    val email: String,
    val telefono: String,
    val pais: String
)

// Reserva
data class Reserva(
    val id: String,
    val paqueteId: String,
    val fecha: String,
    val personas: Int
)

// Pago
data class Pago(
    val id: String,
    val reservaId: String,
    val montoTotal: Int,
    val metodo: String
)

// Itinerario
data class Itinerario(
    val dia: Int,
    val actividad: String,
    val hora: String,
    val lugar: String
)


val demoPaquetes = listOf(
    PaqueteTuristico(
        id = "p1",
        titulo = "Atacama Eco Experience",
        descripcion = "4 días en San Pedro de Atacama con guías locales, observación astronómica y visita a comunidades atacameñas.",
        precioPorPersona = 320000,
        destino = "San Pedro de Atacama, Chile",
        imagenUrl = "https://th.bing.com/th/id/OIP.O79hbdujledOPlOwL6jfJQHaDs?w=306&h=174&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3",
        enfoqueSustentable = "Turismo comunitario / astro turismo responsable"
    ),
    PaqueteTuristico(
        id = "p2",
        titulo = "Ruta Lagos & Bosques",
        descripcion = "Trekking guiado, alojamiento en eco-lodges familiares y navegación en lagos del sur.",
        precioPorPersona = 410000,
        destino = "Región de Los Lagos, Chile",
        imagenUrl = "https://images.unsplash.com/photo-1500534623283-312aade485b7?auto=format&fit=crop&w=800&q=60",
        enfoqueSustentable = "Alojamiento sustentable / apoyo a economías locales"
    ),
    PaqueteTuristico(
        id = "p3",
        titulo = "Costa & Ballenas",
        descripcion = "Avistamiento responsable de fauna marina y estadía en hostales de comunidad pesquera.",
        precioPorPersona = 280000,
        destino = "Chiloé, Chile",
        imagenUrl = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=800&q=60",
        enfoqueSustentable = "Protección de fauna / impacto controlado"
    )
)
