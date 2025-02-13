package com.example.nuerd.Game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PlayButton(randomize: () -> Unit, calculate: () -> Unit, countdown: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier.padding(8.dp)) {
        Button(onClick = {
            calculate()
            countdown()
            randomize()


        }) {
            Icon(Icons.Filled.PlayArrow, contentDescription = null, tint = Color.White)
        }
    }
}