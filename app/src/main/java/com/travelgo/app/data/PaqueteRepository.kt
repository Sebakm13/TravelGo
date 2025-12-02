package com.travelgo.app.data

import com.travelgo.app.data.db.PaqueteLocal
import kotlinx.coroutines.flow.Flow

class PaqueteRepository(
    private val paqueteLocal: PaqueteLocal
) {

    val paquetes: Flow<List<Paquete>> = paqueteLocal.getAllPaquetes()

    suspend fun insertar(paquete: Paquete) {
        paqueteLocal.insert(paquete)
    }

    suspend fun actualizar(paquete: Paquete) {
        paqueteLocal.update(paquete)
    }

    suspend fun eliminar(paquete: Paquete) {
        paqueteLocal.delete(paquete)
    }

    suspend fun obtenerPorId(id: Int): Paquete? {
        return paqueteLocal.getById(id)
    }
}
