package com.travelgo.app.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("user_prefs")

class UserPrefsDataStore(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val NAME_KEY = stringPreferencesKey("user_name")
        private val EMAIL_KEY = stringPreferencesKey("user_email")
    }

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

    suspend fun getToken(): String? {
        return context.dataStore.data
            .map { prefs -> prefs[TOKEN_KEY] }
            .first()
    }

    suspend fun getName(): String? {
        return context.dataStore.data
            .map { prefs -> prefs[NAME_KEY] }
            .first()
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}
