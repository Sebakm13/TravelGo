package com.travelgo.app.data

import com.travelgo.app.data.dao.PaqueteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PaqueteRepository(
    private val paqueteDao: PaqueteDao
) {

    val paquetes: Flow<List<Paquete>> =
        paqueteDao.getAll().map { list -> list.map { it.toDomain() } }

    suspend fun insertar(paquete: Paquete) {
        paqueteDao.insert(paquete.toLocal())
    }

    suspend fun actualizar(paquete: Paquete) {
        paqueteDao.update(paquete.toLocal())
    }

    suspend fun eliminar(paquete: Paquete) {
        paqueteDao.delete(paquete.toLocal())
    }

    suspend fun obtenerPorId(id: Int): Paquete? {
        return paqueteDao.getById(id)?.toDomain()
    }
}
