package com.travelgo.app.data.repository

data class Paquete(
    val id: Int,
    var nombre: String,
    var precio: Double
)

class PaqueteRepository {

    private val paquetes = mutableListOf<Paquete>()

    fun insert(paquete: Paquete) {
        paquetes.add(paquete)
    }

    fun getAll(): List<Paquete> = paquetes.toList()

    fun getById(id: Int): Paquete? = paquetes.find { it.id == id }

    fun update(paquete: Paquete) {
        val index = paquetes.indexOfFirst { it.id == paquete.id }
        if (index != -1) {
            paquetes[index] = paquete
        } else {
            throw IllegalStateException("Paquete no encontrado")
        }
    }

    fun delete(id: Int) {
        val removed = paquetes.removeIf { it.id == id }
        if (!removed) throw IllegalStateException("Paquete no encontrado")
    }

    fun clear() {
        paquetes.clear()
    }
}
