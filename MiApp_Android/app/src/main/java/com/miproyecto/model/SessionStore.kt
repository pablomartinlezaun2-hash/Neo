package com.miproyecto.model

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "auth")

class SessionStore(private val context: Context) {
    private val accessKey = stringPreferencesKey("access")
    private val refreshKey = stringPreferencesKey("refresh")

    suspend fun save(tokens: AuthTokens) {
        context.dataStore.edit { prefs ->
            prefs[accessKey] = tokens.accessToken
            prefs[refreshKey] = tokens.refreshToken
        }
    }

    suspend fun readAccessToken(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[accessKey]
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}
