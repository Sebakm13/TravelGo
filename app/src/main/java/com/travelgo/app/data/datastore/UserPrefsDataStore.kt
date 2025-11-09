package com.travelgo.app.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// --- Configuración del DataStore ---
val Context.dataStore by preferencesDataStore("user_prefs")

class UserPrefsDataStore(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val NAME_KEY = stringPreferencesKey("user_name")
        private val EMAIL_KEY = stringPreferencesKey("user_email")
        private val PASSWORD_KEY = stringPreferencesKey("user_password")
        private val PHOTO_KEY = stringPreferencesKey("user_photo")
    }

    // --- GUARDAR DATOS ---
    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    suspend fun saveName(name: String) {
        context.dataStore.edit { prefs ->
            prefs[NAME_KEY] = name
        }
    }

    suspend fun saveEmail(email: String) {
        context.dataStore.edit { prefs ->
            prefs[EMAIL_KEY] = email
        }
    }

    suspend fun savePassword(password: String) {
        context.dataStore.edit { prefs ->
            prefs[PASSWORD_KEY] = password
        }
    }

    suspend fun savePhoto(uri: String) {
        context.dataStore.edit { prefs ->
            prefs[PHOTO_KEY] = uri
        }
    }

    // --- OBTENER DATOS ---
    suspend fun getToken(): String? {
        return context.dataStore.data.map { it[TOKEN_KEY] }.first()
    }

    suspend fun getName(): String? {
        return context.dataStore.data.map { it[NAME_KEY] }.first()
    }

    suspend fun getEmail(): String? {
        return context.dataStore.data.map { it[EMAIL_KEY] }.first()
    }

    suspend fun getPassword(): String? {
        return context.dataStore.data.map { it[PASSWORD_KEY] }.first()
    }
    suspend fun saveUser(name: String, email: String, password: String) {
        context.dataStore.edit { prefs ->
            prefs[NAME_KEY] = name
            prefs[EMAIL_KEY] = email
            prefs[PASSWORD_KEY] = password
        }
    }
    suspend fun getPhoto(): String? {
        return context.dataStore.data.map { it[PHOTO_KEY] }.first()
    }

    // --- LIMPIAR DATOS ---
    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}

