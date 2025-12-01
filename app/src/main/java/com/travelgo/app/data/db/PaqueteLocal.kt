package com.travelgo.app.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "paquetes")
data class PaqueteLocal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val nombre: String,
    val destino: String = "",
    val descripcion: String,
    val precio: Double,
    val imagenUri: String? = null,
    val creadoAt: Long = System.currentTimeMillis()
)
