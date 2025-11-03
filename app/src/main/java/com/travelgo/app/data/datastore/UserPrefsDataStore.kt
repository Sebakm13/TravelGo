package com.travelgo.app.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

// Declaramos el DataStore con nombre 'user_prefs'
private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPrefsDataStore(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val NAME_KEY = stringPreferencesKey("client_name")
    }

    // Guardar token sin bloquear la UI
    suspend fun saveToken(token: String) {
        withContext(Dispatchers.IO) {
            context.dataStore.edit { prefs ->
                prefs[TOKEN_KEY] = token
            }
        }
    }

    // Guardar nombre sin bloquear la UI
    suspend fun saveName(name: String) {
        withContext(Dispatchers.IO) {
            context.dataStore.edit { prefs ->
                prefs[NAME_KEY] = name
            }
        }
    }

    // Obtener token de forma segura
    suspend fun getToken(): String? {
        return withContext(Dispatchers.IO) {
            context.dataStore.data.map { prefs ->
                prefs[TOKEN_KEY]
            }.first()
        }
    }

    // Obtener nombre de forma segura
    suspend fun getName(): String? {
        return withContext(Dispatchers.IO) {
            context.dataStore.data.map { prefs ->
                prefs[NAME_KEY]
            }.first()
        }
    }

    // Limpiar todos los datos del usuario
    suspend fun clear() {
        withContext(Dispatchers.IO) {
            context.dataStore.edit { prefs ->
                prefs.clear()
            }
        }
    }
}
