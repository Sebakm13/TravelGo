
package com.travelgo.app.presentation.destinations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travelgo.app.data.remote.dto.DestinationCreateRequest
import com.travelgo.app.data.remote.dto.DestinationDto
import com.travelgo.app.data.repository.DestinationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class DestinationsUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val destinations: List<DestinationDto> = emptyList(),
    val searchQuery: String = "",
    val categoryFilter: String = "ALL"
)

class DestinationsViewModel(
    private val repo: DestinationRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DestinationsUiState())
    val state: StateFlow<DestinationsUiState> = _state

    init { load() }

    fun load() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, isError = false)
            try {
                val filter = if (_state.value.categoryFilter == "ALL") null else _state.value.categoryFilter
                val result = repo.getDestinations(filter, _state.value.searchQuery)
                _state.value = _state.value.copy(isLoading = false, destinations = result)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, isError = true, errorMessage = e.message ?: "Error")
            }
        }
    }

    fun search(q: String) {
        _state.value = _state.value.copy(searchQuery = q)
        load()
    }

    fun changeCategory(c: String) {
        _state.value = _state.value.copy(categoryFilter = c)
        load()
    }
}
