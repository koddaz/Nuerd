package com.example.nuerd.practie

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nuerd.models.GameViewModel
import com.example.nuerd.models.Routes
import com.example.nuerd.practie.tables.TablesScreen

@Composable
fun PracticeNavHost(onButtonClick: () -> Unit, gameViewModel: GameViewModel) {
    val navController = rememberNavController()
    val navigateTables = { navController.navigate(Routes.TABLES) }
    val navigatePractice = { navController.navigate(Routes.PRACTICE) }

    NavHost(navController = navController, startDestination = Routes.PRACTICE) {
        composable(Routes.PRACTICE) { PracticeScreen(
            onButtonClick = { onButtonClick },
            gameViewModel = gameViewModel,
            navigateTables = navigateTables
        )}
        composable(Routes.TABLES) { TablesScreen(
            onButtonClick = {  },
            navigatePractice = navigatePractice

        ) }
    }
}