package com.travelgo.app.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewModelScope
import com.travelgo.app.data.Paquete
import com.travelgo.app.data.Repository.PaqueteRepository
import com.travelgo.app.data.db.DatabaseProvider
import com.travelgo.app.data.db.PaqueteLocal
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PaqueteViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = PaqueteRepository(DatabaseProvider.getDatabase(application).paqueteDao())

    var paquetes = mutableStateListOf<Paquete>()
        private set

    var isLoading by mutableStateOf(true)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private var loadJob: Job? = null

    init {
        observePaquetes()
    }

    fun refresh() {
        observePaquetes()
    }

    private fun observePaquetes() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            repo.getAll()
                .onStart {
                    isLoading = true
                    errorMessage = null
                }
                .catch { throwable ->
                    errorMessage = throwable.message ?: "Error al cargar paquetes"
                    isLoading = false
                }
                .collect { list ->
                    paquetes.clear()
                    paquetes.addAll(list.map { it.toPaquete() })
                    isLoading = false
                    errorMessage = null
                }
        }
    }

    fun getById(id: Long) = paquetes.find { it.id == id }

    fun add(nombre: String, destino: String, precio: Double, descripcion: String) {
        viewModelScope.launch {
            runCatching {
                repo.insert(
                    PaqueteLocal(
                        nombre = nombre,
                        destino = destino,
                        descripcion = descripcion,
                        precio = precio,
                        imagenUri = null
                    )
                )
            }.onFailure { throwable ->
                errorMessage = throwable.message ?: "Error al guardar el paquete"
            }
        }
    }

    fun update(id: Long, nombre: String, destino: String, precio: Double, descripcion: String) {
        viewModelScope.launch {
            runCatching {
                repo.update(
                    PaqueteLocal(
                        id = id,
                        nombre = nombre,
                        destino = destino,
                        descripcion = descripcion,
                        precio = precio,
                        imagenUri = null,
                        creadoAt = getById(id)?.let { paquete ->
                            paquete.creadoAt ?: System.currentTimeMillis()
                        } ?: System.currentTimeMillis()
                    )
                )
            }.onFailure { throwable ->
                errorMessage = throwable.message ?: "Error al actualizar el paquete"
            }
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch {
            getById(id)?.let { paquete ->
                runCatching {
                    repo.delete(
                        PaqueteLocal(
                            id = paquete.id,
                            nombre = paquete.nombre,
                            destino = paquete.destino,
                            descripcion = paquete.descripcion,
                            precio = paquete.precio,
                            imagenUri = null,
                            creadoAt = paquete.creadoAt ?: System.currentTimeMillis()
                        )
                    )
                }.onFailure { throwable ->
                    errorMessage = throwable.message ?: "Error al eliminar el paquete"
                }
            }
        }
    }

    private fun PaqueteLocal.toPaquete(): Paquete =
        Paquete(
            id = id,
            nombre = nombre,
            destino = destino,
            precio = precio,
            descripcion = descripcion,
            creadoAt = creadoAt
        )

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as Application
                PaqueteViewModel(application)
            }
        }
    }
}