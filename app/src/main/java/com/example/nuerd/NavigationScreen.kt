package com.example.nuerd


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nuerd.ui.theme.NuerdTheme

object Routes {
    const val GAME = "game"
    const val HOME = "home"
    // const val PRACTICE = "practice"
    // const val SETTINGS = "settings"
    // const val HELP = "help"
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
                HomeScreen(onGameClick = { navController.navigate(Routes.GAME) })
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