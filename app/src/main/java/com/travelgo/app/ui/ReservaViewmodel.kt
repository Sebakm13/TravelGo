package com.travelgo.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travelgo.app.data.Repository.ReservaRepository
import com.travelgo.app.data.db.Reserva
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReservaViewModel(
    private val repo: ReservaRepository
) : ViewModel() {

    val reservas = repo.getAll().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun insert(reserva: Reserva) {
        viewModelScope.launch { repo.insert(reserva) }
    }

    fun update(reserva: Reserva) {            // ✅ NUEVO
        viewModelScope.launch { repo.update(reserva) }
    }

    fun delete(reserva: Reserva) {
        viewModelScope.launch { repo.delete(reserva) }
    }
}
