package com.travelgo.app.data

import com.travelgo.app.data.db.PaqueteLocal
import com.travelgo.app.data.dao.PaqueteDao
import kotlinx.coroutines.flow.Flow

class PaqueteRepository(
    private val dao: PaqueteDao
) {

    fun getAll(): Flow<List<PaqueteLocal>> = dao.getAll()

    suspend fun insert(paquete: PaqueteLocal) {
        dao.insert(paquete)
    }

    suspend fun update(paquete: PaqueteLocal) {
        dao.update(paquete)
    }

    suspend fun delete(paquete: PaqueteLocal) {
        dao.delete(paquete)
    }
}
