package com.travelgo.app.data.Repository

import com.travelgo.app.data.dao.PaqueteDao
import com.travelgo.app.data.db.Paquete
import kotlinx.coroutines.flow.Flow

class PaqueteRepository(
    private val paqueteDao: PaqueteDao
) {
    fun getAll(): Flow<List<Paquete>> = paqueteDao.getAll()
    suspend fun getById(id: Long): Paquete? = paqueteDao.getById(id)
    suspend fun insert(paquete: Paquete) = paqueteDao.insert(paquete)
    suspend fun update(paquete: Paquete) = paqueteDao.update(paquete)
    suspend fun delete(paquete: Paquete) = paqueteDao.delete(paquete)
}
