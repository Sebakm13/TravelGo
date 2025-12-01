package com.travelgo.app.data

data class Paquete(
    val id: Long = 0L,
    val nombre: String,
    val destino: String,
    val precio: Double,
    val descripcion: String,
    val creadoAt: Long? = null
)
