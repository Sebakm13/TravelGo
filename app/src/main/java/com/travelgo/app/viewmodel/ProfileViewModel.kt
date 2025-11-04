package com.travelgo.app.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.travelgo.app.data.model.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore

class ProfileViewModel : ViewModel() {

    private val firestore = Firebase.firestore

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState


    fun updateAvatar(uri: Uri?) {
        _uiState.update { it.copy(avatarUri = uri) }
    }


    fun loadUser(userId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            firestore.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->
                    val user = document.toObject(User::class.java)
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            user = user,
                            error = null
                        )
                    }
                }
                .addOnFailureListener { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = e.message
                        )
                    }
                }
        }
    }
}
