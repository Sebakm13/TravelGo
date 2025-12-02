package com.travelgo.app.ui

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModelProvider
import com.travelgo.app.data.Paquete
import com.travelgo.app.data.PaqueteRepository
import com.travelgo.app.data.datastore.UserPrefsDataStore

class PaqueteViewModel(private val prefs: UserPrefsDataStore) : ViewModel() {

    private val repo = PaqueteRepository()
    var paquetes = mutableStateListOf<Paquete>()
        private set

    init {
        paquetes.addAll(repo.getAll())
    }

    fun getById(id: Long) = paquetes.find { it.id == id }

    fun add(nombre: String, destino: String, precio: Double, descripcion: String) {
        repo.insert(Paquete(0, nombre, destino, precio, descripcion))
        paquetes.clear()
        paquetes.addAll(repo.getAll())
    }

    fun update(id: Long, nombre: String, destino: String, precio: Double, descripcion: String) {
        repo.update(Paquete(id, nombre, destino, precio, descripcion))
        paquetes.clear()
        paquetes.addAll(repo.getAll())
    }
}
