package com.miproyecto.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.miproyecto.model.ApiClient
import com.miproyecto.model.AuthTokens
import com.miproyecto.model.SessionStore
import com.miproyecto.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SessionViewModel(application: Application) : AndroidViewModel(application) {
    private val store = SessionStore(application)

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val tokens = ApiClient.service.login(mapOf("email" to email, "password" to password))
                persist(tokens)
                _user.value = User(id = "local", name = email.substringBefore("@"), email = email)
                _isAuthenticated.value = true
            } catch (ex: Exception) {
                _error.value = "No se pudo iniciar sesión."
            }
            _loading.value = false
        }
    }

    fun signup(name: String, email: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val tokens = ApiClient.service.signup(
                    mapOf("name" to name, "email" to email, "password" to password)
                )
                persist(tokens)
                _user.value = User(id = "local", name = name, email = email)
                _isAuthenticated.value = true
            } catch (ex: Exception) {
                _error.value = "No se pudo registrar la cuenta."
            }
            _loading.value = false
        }
    }

    fun logout() {
        viewModelScope.launch {
            store.clear()
            _isAuthenticated.value = false
            _user.value = null
        }
    }

    fun loadPersistedSession() {
        viewModelScope.launch {
            val token = store.readAccessToken()
            _isAuthenticated.value = token != null
        }
    }

    suspend fun readAccessToken(): String? {
        return store.readAccessToken()
    }

    private suspend fun persist(tokens: AuthTokens) {
        store.save(tokens)
    }
}
