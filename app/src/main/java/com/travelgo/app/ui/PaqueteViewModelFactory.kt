package com.travelgo.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.travelgo.app.data.Repository.PaqueteRepository


class PaqueteViewModelFactory(private val repository: PaqueteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return if (modelClass.isAssignableFrom(PaqueteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            PaqueteViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
