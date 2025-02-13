package com.example.nuerd.Game

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun CountingLives(lives: Int) {
    Row {
        if (lives == 0) {
            Text("Game over")
        } else {
            for (life in 1..lives) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        }

    }
}