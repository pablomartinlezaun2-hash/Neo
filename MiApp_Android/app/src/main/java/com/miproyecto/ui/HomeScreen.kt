package com.miproyecto.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.miproyecto.viewmodel.DashboardViewModel
import com.miproyecto.viewmodel.SessionViewModel

@Composable
fun HomeScreen(sessionViewModel: SessionViewModel, onSettings: () -> Unit) {
    val dashboardViewModel = androidx.lifecycle.viewmodel.compose.viewModel<DashboardViewModel>()
    val cards by dashboardViewModel.cards.collectAsState()
    val loading by dashboardViewModel.loading.collectAsState()
    val error by dashboardViewModel.error.collectAsState()
    val user by sessionViewModel.user.collectAsState()

    LaunchedEffect(Unit) {
        if (sessionViewModel.isAuthenticated.value) {
            val access = sessionViewModel.readAccessToken()
            if (access != null) {
                dashboardViewModel.load(access)
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Hola, ${user?.name ?: "Usuario"}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("Resumen diario", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Button(onClick = { sessionViewModel.logout() }) {
                Text("Salir")
            }
        }

        when {
            loading -> CircularProgressIndicator()
            error != null -> Text(error ?: "", color = MaterialTheme.colorScheme.error)
            else -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(cards) { card ->
                        Surface(shape = MaterialTheme.shapes.medium, tonalElevation = 2.dp) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(card, style = MaterialTheme.typography.titleMedium)
                                Text("Detalle del indicador, acciones rápidas y CTA.")
                            }
                        }
                    }
                }
            }
        }

        Button(onClick = onSettings, modifier = Modifier.fillMaxWidth()) {
            Text("Ajustes")
        }
    }
}
