package com.travelgo.app.data.db

import androidx.room.*
import com.travelgo.app.data.Paquete
import kotlinx.coroutines.flow.Flow

@Dao
interface PaqueteLocal {

    @Query("SELECT * FROM Paquete")
    fun getAllPaquetes(): Flow<List<Paquete>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(paquete: Paquete)

    @Update
    suspend fun update(paquete: Paquete)

    @Delete
    suspend fun delete(paquete: Paquete)

    @Query("SELECT * FROM Paquete WHERE id = :id")
    suspend fun getById(id: Int): Paquete?
}
