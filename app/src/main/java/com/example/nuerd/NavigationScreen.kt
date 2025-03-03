package com.example.nuerd


import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nuerd.account.AccountScreen
import com.example.nuerd.account.LoginScreen
import com.example.nuerd.game.GameScreen
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.practie.PracticeScreen
import com.example.nuerd.settings.SettingsScreen
import com.example.nuerd.tables.TablesScreen
import com.example.nuerd.ui.theme.NuerdTheme

object Routes {
    const val GAME = "game"
    const val HOME = "home"
    const val PRACTICE = "practice"
    const val SETTINGS = "settings"
    const val TABLES = "tables"
    const val LOGIN = "login"
    const val ACCOUNT = "account"
   //  const val ABOUT = "about"
}



@Composable
fun NavigationScreen(modifier: Modifier = Modifier, authViewModel: AuthViewModel = viewModel()) {
    val navController = rememberNavController()
    val authState by authViewModel.authState.observeAsState()

    val currentAuthState by rememberUpdatedState(authState)

    LaunchedEffect(currentAuthState) {
        Log.d("HomeScreen", "AuthState: $currentAuthState")
        if (currentAuthState == null) {
            authViewModel.setUnauthenticated()
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        NavHost(navController = navController, startDestination = Routes.HOME) {
            composable(Routes.GAME) {
                GameScreen(onButtonClick = { navController.popBackStack() })
            }
            composable(Routes.HOME) {
                HomeScreen(
                    onSignClick = { navController.navigate(Routes.LOGIN) },
                    onButtonClick = { navController.popBackStack() },
                    onTablesClick = { navController.navigate(Routes.TABLES) },
                    onPracticeClick = { navController.navigate(Routes.PRACTICE) },
                    onGameClick = { navController.navigate(Routes.GAME) },
                    onSettingsClick = { navController.navigate(Routes.SETTINGS) },
                    onAccountClick = { navController.navigate(Routes.ACCOUNT) },
                    authState = authState
                )
            }
            composable(Routes.PRACTICE) {
                PracticeScreen(onButtonClick = { navController.popBackStack() })
            }
            composable(Routes.SETTINGS) {
                SettingsScreen(onButtonClick = { navController.popBackStack() })
            }
            composable(Routes.TABLES) {
                TablesScreen(onButtonClick = { navController.popBackStack() })
            }
            composable(Routes.LOGIN) {
                LoginScreen(authState = authState, onButtonClick = { navController.navigate(Routes.HOME) })
            }
            composable(Routes.ACCOUNT) {
                AccountScreen(onButtonClick = { navController.popBackStack() })
            }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NuerdTheme {
        NavigationScreen()
    }
}