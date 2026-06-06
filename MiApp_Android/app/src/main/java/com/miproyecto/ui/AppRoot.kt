package com.miproyecto.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.miproyecto.viewmodel.SessionViewModel

@Composable
fun AppRoot(sessionViewModel: SessionViewModel) {
    val navController = rememberNavController()
    val isAuthenticated by sessionViewModel.isAuthenticated.collectAsState()

    LaunchedEffect(Unit) {
        sessionViewModel.loadPersistedSession()
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF4F7FA)) {
        NavHost(
            navController = navController,
            startDestination = if (isAuthenticated) "home" else "login"
        ) {
            composable("login") {
                LoginScreen(sessionViewModel, onSignup = { navController.navigate("signup") })
            }
            composable("signup") {
                SignupScreen(sessionViewModel, onBack = { navController.popBackStack() })
            }
            composable("home") {
                HomeScreen(sessionViewModel, onSettings = { navController.navigate("settings") })
            }
            composable("settings") {
                SettingsScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}
