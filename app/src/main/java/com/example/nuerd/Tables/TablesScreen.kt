package com.example.nuerd.Tables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TablesScreen(onButtonClick: () -> Unit) {
    Column() {
        Text("Tables Screen")
        Button(onClick = onButtonClick) {
            Text("Home")
        }
    }
}