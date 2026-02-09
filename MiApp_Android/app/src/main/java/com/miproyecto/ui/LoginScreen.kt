package com.miproyecto.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.miproyecto.viewmodel.SessionViewModel

@Composable
fun LoginScreen(sessionViewModel: SessionViewModel, onSignup: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val error by sessionViewModel.error.collectAsState()
    val loading by sessionViewModel.loading.collectAsState()

    Column(
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("NorteNeo", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = { sessionViewModel.login(email, password) }, modifier = Modifier.fillMaxWidth()) {
            if (loading) {
                CircularProgressIndicator()
            } else {
                Text("Iniciar sesión")
            }
        }

        Button(onClick = onSignup, modifier = Modifier.fillMaxWidth()) {
            Text("Crear cuenta")
        }

        if (error != null) {
            Text(error ?: "", color = MaterialTheme.colorScheme.error)
        }
    }
}
