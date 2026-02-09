package com.miproyecto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miproyecto.model.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    private val _cards = MutableStateFlow<List<String>>(emptyList())
    val cards: StateFlow<List<String>> = _cards

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun load(accessToken: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                _cards.value = ApiClient.service.dashboard("Bearer $accessToken")
            } catch (ex: Exception) {
                _error.value = "No se pudieron cargar los datos."
            }
            _loading.value = false
        }
    }
}
