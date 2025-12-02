package com.travelgo.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.travelgo.app.data.Paquete

@Database(
    entities = [Paquete::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun paqueteLocal(): PaqueteLocal
}
