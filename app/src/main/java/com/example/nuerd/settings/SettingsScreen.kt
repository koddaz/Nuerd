package com.example.nuerd.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SettingsScreen(onButtonClick: () -> Unit) {
    Column() {
        Text("Settings Screen")
        Button(onClick = onButtonClick) {
            Text("Home")
        }
    }
}