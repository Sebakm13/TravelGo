package com.travelgo.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.travelgo.app.data.PaqueteRepository

class PaqueteViewModelFactory(
    private val repository: PaqueteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaqueteViewModel::class.java)) {
            return PaqueteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
