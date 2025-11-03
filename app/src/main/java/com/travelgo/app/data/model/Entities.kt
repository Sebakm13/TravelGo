package com.travelgo.app.data.model

// Paquete Turístico
data class PaqueteTuristico(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val precioPorPersona: Int,
    val destino: String,
    val imagenUrl: String,
    val enfoqueSustentable: String // ej: "turismo comunitario", "carbono neutral"
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
    val metodo: String // "Tarjeta", "Transferencia"
)

// Itinerario
data class Itinerario(
    val dia: Int,
    val actividad: String,
    val hora: String,
    val lugar: String
)

// Datos de ejemplo para mostrar en la app
val demoPaquetes = listOf(
    PaqueteTuristico(
        id = "p1",
        titulo = "Atacama Eco Experience",
        descripcion = "4 días en San Pedro de Atacama con guías locales, observación astronómica y visita a comunidades atacameñas.",
        precioPorPersona = 320000,
        destino = "San Pedro de Atacama, Chile",
        imagenUrl = "https://images.unsplash.com/photo-1508261305439-0e0e266c7438?auto=format&fit=crop&w=800&q=60",
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
