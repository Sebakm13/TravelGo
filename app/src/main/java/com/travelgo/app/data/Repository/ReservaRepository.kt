package com.travelgo.app.data.Repository

import com.travelgo.app.data.dao.ReservaDao
import com.travelgo.app.data.db.Reserva
import kotlinx.coroutines.flow.Flow

class ReservaRepository(private val dao: ReservaDao) {
    fun getAll(): Flow<List<Reserva>> = dao.getAll()
    suspend fun insert(reserva: Reserva) = dao.insert(reserva)
    suspend fun delete(reserva: Reserva) = dao.delete(reserva)
}
