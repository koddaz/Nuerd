package com.example.nuerd


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nuerd.Game.GameScreen
import com.example.nuerd.Practie.PracticeScreen
import com.example.nuerd.Settings.SettingsScreen
import com.example.nuerd.Tables.TablesScreen
import com.example.nuerd.ui.theme.NuerdTheme

object Routes {
    const val GAME = "game"
    const val HOME = "home"
    const val PRACTICE = "practice"
    const val SETTINGS = "settings"
    const val TABLES = "tables"
   //  const val ABOUT = "about"
}



@Composable
fun NavigationScreen() {
    val navController = rememberNavController()

    Column(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navController, startDestination = Routes.HOME) {
            composable(Routes.GAME) {
                GameScreen(onButtonClick = { navController.popBackStack() })
            }
            composable(Routes.HOME) {
                HomeScreen(
                    onTablesClick = { navController.navigate(Routes.TABLES) },
                    onPracticeClick = { navController.navigate(Routes.PRACTICE) },
                    onGameClick = { navController.navigate(Routes.GAME) },
                    onSettingsClick = { navController.navigate(Routes.SETTINGS) })
            }
            composable(Routes.PRACTICE) {
                PracticeScreen(onButtonClick = { navController.popBackStack() })
            }
            composable(Routes.SETTINGS) {
                SettingsScreen (onButtonClick = { navController.popBackStack() })
            }

            composable(Routes.TABLES) {

                TablesScreen(onButtonClick = { navController.popBackStack() })
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