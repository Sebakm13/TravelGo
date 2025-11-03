package com.travelgo.app.data

class PaqueteRepository {

    // datos de prueba
    private val lista = mutableListOf(
        Paquete(1, "Paquete Playa", "Cancún", 1200.0, "Hotel + Tour + Traslados"),
        Paquete(2, "Paquete Montaña", "Bariloche", 900.0, "Cabaña + Excursiones")
    )

    fun getAll(): List<Paquete> = lista

    fun getById(id: Long) = lista.find { it.id == id }

    fun insert(paquete: Paquete) {
        val newId = (lista.maxOfOrNull { it.id } ?: 0) + 1
        lista.add(paquete.copy(id = newId))
    }

    fun update(paquete: Paquete) {
        val index = lista.indexOfFirst { it.id == paquete.id }
        if (index != -1) lista[index] = paquete
    }
}