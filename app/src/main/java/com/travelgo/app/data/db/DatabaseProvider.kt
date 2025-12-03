package com.travelgo.app.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "paquete")  // La tabla se llama "paquete" en la base de datos
data class Paquete(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, // El id se genera automáticamente
    val nombre: String,
    val descripcion: String,
    val precio: Double
)
