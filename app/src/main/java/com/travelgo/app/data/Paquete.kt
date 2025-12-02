package com.travelgo.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Paquete")
data class Paquete(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String = "",
    val descripcion: String = "",
    val precio: Int = 0,
    val imagenUrl: String = ""
)
