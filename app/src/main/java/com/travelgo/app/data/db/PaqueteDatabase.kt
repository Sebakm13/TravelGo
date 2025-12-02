package com.travelgo.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.travelgo.app.data.dao.PaqueteDao

@Database(
    entities = [PaqueteLocal::class],
    version = 1,
    exportSchema = false
)
abstract class PaqueteDatabase : RoomDatabase() {
    abstract fun paqueteDao(): PaqueteDao
}
