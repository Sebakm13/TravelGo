package com.travelgo.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.travelgo.app.data.model.PaqueteLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface PaqueteDao {
    @Query("SELECT * FROM paquetes ORDER BY creadoAt DESC")
    fun getAll(): Flow<List<PaqueteLocal>>

    @Query("SELECT * FROM paquetes WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): PaqueteLocal?

    @Insert
    suspend fun insert(paquete: PaqueteLocal): Long

    @Update
    suspend fun update(paquete: PaqueteLocal)

    @Delete
    suspend fun delete(paquete: PaqueteLocal)
}