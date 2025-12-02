package com.travelgo.app.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "paquete")
data class PaqueteLocal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val descripcion: String,
    val precio: Int,
    val creadoAt: Long = System.currentTimeMillis()
)
