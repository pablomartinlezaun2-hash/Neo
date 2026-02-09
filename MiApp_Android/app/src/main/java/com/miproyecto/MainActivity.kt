package com.miproyecto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miproyecto.ui.AppRoot
import com.miproyecto.ui.theme.MiAppTheme
import com.miproyecto.viewmodel.SessionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiAppTheme {
                val sessionViewModel: SessionViewModel = viewModel()
                AppRoot(sessionViewModel = sessionViewModel)
            }
        }
    }
}
