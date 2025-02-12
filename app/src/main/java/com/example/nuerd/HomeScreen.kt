package com.example.nuerd

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onGameClick: () -> Unit,
//    onPracticeClick: () -> Unit,
//    onSettingsClick: () -> Unit,
//    onHelpClick: () -> Unit,
//    onAboutClick: () -> Unit

    ) {
    Text("Home")
    Button(onClick = onGameClick) {
        Text("Game")

    }
}