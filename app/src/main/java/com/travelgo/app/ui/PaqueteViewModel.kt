package com.travelgo.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travelgo.app.data.Paquete
import com.travelgo.app.data.PaqueteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PaqueteViewModel(
    private val repository: PaqueteRepository
) : ViewModel() {

    val paquetes: StateFlow<List<Paquete>> =
        repository.paquetes.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun insertar(paquete: Paquete) {
        viewModelScope.launch {
            repository.insertar(paquete)
        }
    }

    fun actualizar(paquete: Paquete) {
        viewModelScope.launch {
            repository.actualizar(paquete)
        }
    }

    fun eliminar(paquete: Paquete) {
        viewModelScope.launch {
            repository.eliminar(paquete)
        }
    }

    suspend fun obtenerPorId(id: Int): Paquete? {
        return repository.obtenerPorId(id)
    }
}
