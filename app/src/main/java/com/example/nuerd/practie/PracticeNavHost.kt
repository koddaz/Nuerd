package com.example.nuerd.practie

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nuerd.models.Routes
import com.example.nuerd.tables.TablesScreen

@Composable
fun PracticeNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.PRACTICE) {
        composable(Routes.PRACTICE) { PracticeScreen(
            onButtonClick = { }
        )}
        composable(Routes.TABLES) { TablesScreen(
            onButtonClick = {  }
        ) }
    }
}