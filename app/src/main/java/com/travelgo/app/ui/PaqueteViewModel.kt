package com.travelgo.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travelgo.app.data.Repository.PaqueteRepository
import com.travelgo.app.data.db.Paquete
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PaqueteViewModel(
    private val repository: PaqueteRepository
) : ViewModel() {

    val paquetes = repository.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun insert(paquete: Paquete) {
        viewModelScope.launch {
            repository.insert(paquete)
        }
    }

    fun update(paquete: Paquete) {
        viewModelScope.launch {
            repository.update(paquete)
        }
    }

    fun delete(paquete: Paquete) {
        viewModelScope.launch {
            repository.delete(paquete)
        }
    }

    fun getById(id: Long, onResult: (Paquete?) -> Unit) {
        viewModelScope.launch {
            onResult(repository.getById(id))
        }
    }
}
