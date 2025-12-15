package com.travelgo.app.data.dao

import androidx.room.*
import com.travelgo.app.data.db.Paquete
import kotlinx.coroutines.flow.Flow

@Dao
interface PaqueteDao {

    @Query("SELECT * FROM paquetes ORDER BY creadoAt DESC")
    fun getAll(): Flow<List<Paquete>>

    @Query("SELECT * FROM paquetes WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): Paquete?

    @Insert
    suspend fun insert(paquete: Paquete)

    @Update
    suspend fun update(paquete: Paquete)

    @Delete
    suspend fun delete(paquete: Paquete)
}
