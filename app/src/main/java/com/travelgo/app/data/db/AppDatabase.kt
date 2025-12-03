package com.travelgo.app.data.db

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.travelgo.app.data.dao.PaqueteDao
import com.travelgo.app.data.db.PaqueteLocal

object DatabaseProvider {
    fun getDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}

// AppDatabase (base de datos)
@Database(
    entities = [PaqueteLocal::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun paqueteLocal(): PaqueteDao
}


// PaqueteDao (DAO)
@Dao
interface PaqueteDao {
    @Insert
    suspend fun insert(paquete: Paquete)

    @Query("SELECT * FROM paquete")
    fun getAll(): List<Paquete>
}
