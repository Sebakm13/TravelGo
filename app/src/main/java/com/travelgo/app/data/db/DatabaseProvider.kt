package com.travelgo.app.data.db

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var db: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        if (db == null) {
            db = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "travelgo.db"
            )
                .fallbackToDestructiveMigration() // Importante para que no explote
                .build()
        }
        return db!!
    }
}