package com.travelgo.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.travelgo.app.data.datastore.UserPrefsDataStore

class PaqueteViewModelFactory(private val prefs: UserPrefsDataStore) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return if (modelClass.isAssignableFrom(PaqueteViewModel::class.java)) {
            PaqueteViewModel(prefs) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
