package com.travelgo.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travelgo.app.data.Repository.PaqueteRepository
import com.travelgo.app.data.db.PaqueteLocal
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PaqueteViewModel(private val repository: PaqueteRepository) : ViewModel() {

    // Flow -> StateFlow para Compose
    val paquetes = repository.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun insert(paquete: PaqueteLocal) {
        viewModelScope.launch {
            repository.insert(paquete)
        }
    }

    fun update(paquete: PaqueteLocal) {
        viewModelScope.launch {
            repository.update(paquete)
        }
    }

    fun delete(paquete: PaqueteLocal) {
        viewModelScope.launch {
            repository.delete(paquete)
        }
    }

    fun getById(id: Long, onResult: (PaqueteLocal?) -> Unit) {
        viewModelScope.launch {
            val paquete = repository.getById(id)
            onResult(paquete)
        }
    }

}

