package com.travelgo.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.travelgo.app.data.dao.PaqueteDao
import com.travelgo.app.data.dao.ReservaDao

@Database(
    entities = [Paquete::class, Reserva::class],
    version = 2,
    exportSchema = false
)
abstract class PaqueteDatabase : RoomDatabase() {
    abstract fun paqueteDao(): PaqueteDao
    abstract fun reservaDao(): ReservaDao
}
