package com.travelgo.app.data.Repository

import com.travelgo.app.data.dao.PaqueteDao
import com.travelgo.app.data.db.PaqueteLocal
import kotlinx.coroutines.flow.Flow

class PaqueteRepository(private val paqueteDao: PaqueteDao) {

    fun getAll(): Flow<List<PaqueteLocal>> = paqueteDao.getAll()
    suspend fun getById(id: Long): PaqueteLocal? = paqueteDao.getById(id)
    suspend fun insert(paquete: PaqueteLocal): Long = paqueteDao.insert(paquete)
    suspend fun update(paquete: PaqueteLocal) = paqueteDao.update(paquete)
    suspend fun delete(paquete: PaqueteLocal) = paqueteDao.delete(paquete)
}