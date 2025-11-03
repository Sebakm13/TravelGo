package com.travelgo.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "paquetes")
data class PaqueteLocal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val descripcion: String,
    val precio: Int,
    val imagenUri: String? = null, // guardamos URI como String
    val creadoAt: Long = System.currentTimeMillis()
)