package com.example.nuerd

import android.app.Application
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuerd.models.AppDatabase
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.models.GameViewModel
import com.example.nuerd.models.GameViewModelFactory
import com.example.nuerd.models.ThemeViewModel
import com.example.nuerd.models.ThemeViewModelFactory
import com.example.nuerd.models.getCountriesViewModel
import com.example.nuerd.ui.theme.NuerdTheme
import kotlin.getValue


class MainActivity : ComponentActivity() {
    private val themeViewModel: ThemeViewModel by viewModels {
        val application = application as Application
        val settingsDao = AppDatabase.getDatabase(application).settingsDao()
        ThemeViewModelFactory(settingsDao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            !themeViewModel.isInitialized.value
        }
        actionBar?.hide()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel(
                factory = ThemeViewModelFactory(
                    settingsDao = AppDatabase
                        .getDatabase(LocalContext.current)
                        .settingsDao()))
            val gameViewModel: GameViewModel = viewModel(factory = GameViewModelFactory(application))
            val authViewModel: AuthViewModel = viewModel()
            val getCountries: getCountriesViewModel = viewModel()
            val currentTheme by themeViewModel.theme.collectAsState()

            NuerdTheme(theme = currentTheme) {
                MainScreen(
                    gameViewModel = gameViewModel,
                    themeViewModel = themeViewModel,
                    authViewModel = authViewModel,
                    getCountries = getCountries
                )
            }
        }
    }
}