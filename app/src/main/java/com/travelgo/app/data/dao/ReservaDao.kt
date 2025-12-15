package com.travelgo.app.data.dao

import androidx.room.*
import com.travelgo.app.data.db.Reserva
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservaDao {

    @Query("SELECT * FROM reserva ORDER BY creadoAt DESC")
    fun getAll(): Flow<List<Reserva>>

    @Insert
    suspend fun insert(reserva: Reserva)

    @Delete
    suspend fun delete(reserva: Reserva)
}
