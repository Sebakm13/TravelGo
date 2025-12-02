package com.travelgo.app.data.db

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    private var instancia: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return instancia ?: Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "travelgo-db"
        ).fallbackToDestructiveMigration()
            .build()
            .also { instancia = it }
    }
}
