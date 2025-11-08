package com.travelgo.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.travelgo.app.data.Repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ProfileUiState(
    val isLoading: Boolean = false,
    val userName: String = "",
    val userEmail: String = "",
    val userImage: String? = null,
    val error: String? = null
)

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepository(application)

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun loadUser(id: Int = 1) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            val result = repository.fetchUser(id)

            _uiState.value = result.fold(
                onSuccess = { user ->
                    _uiState.value.copy(
                        isLoading = false,
                        userName = "${user.firstName} ${user.lastName}", // ✅ existe
                        userEmail = user.email.ifEmpty { "Sin email" },
                        userImage = user.image,
                        error = null
                    )
                },
                onFailure = { e ->
                    _uiState.value.copy(
                        isLoading = false,
                        error = e.localizedMessage ?: "Error desconocido"
                    )
                }
            )
        }
    }
}
