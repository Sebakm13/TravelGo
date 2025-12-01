package com.travelgo.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.travelgo.app.data.dao.PaqueteDao
import com.travelgo.app.data.db.PaqueteLocal

@Database(
    entities = [PaqueteLocal::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun paqueteDao(): PaqueteDao
}