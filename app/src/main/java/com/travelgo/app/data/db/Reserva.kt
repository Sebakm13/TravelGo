package com.travelgo.app.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reserva")
data class Reserva(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val paqueteId: Long,
    val nombrePaquete: String,
    val descripcion: String,
    val precio: Double,
    val fecha: String,
    val personas: Int,
    val creadoAt: Long = System.currentTimeMillis()
)
