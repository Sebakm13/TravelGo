package com.travelgo.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travelgo.app.data.Repository.UserRepository
import com.travelgo.app.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    fun fetchUser(id: Int) {
        viewModelScope.launch {
            val result = repository.fetchUser(id)
            if (result.isSuccess) {
                _user.value = result.getOrNull()
            }
        }
    }
}
