package com.example.nuerd

import android.app.Application
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.TopAppBarDefaults.windowInsets
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
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
import com.example.nuerd.splash.CustomSplashScreen
import com.example.nuerd.ui.theme.NuerdTheme
import kotlin.getValue


class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { false }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

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
                var showSplash by remember { mutableStateOf(true) }

                if (showSplash

                    ) {
                    CustomSplashScreen(
                        onSplashScreenFinished = {
                            showSplash = false
                        }
                    )
                } else {
                    MainScreen(
                        gameViewModel = gameViewModel,
                        themeViewModel = themeViewModel,
                        authViewModel = authViewModel,
                    )
                }
            }
        }
    }
}