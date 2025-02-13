package com.example.nuerd.Game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ButtonGrid(
    correctButton: () -> Unit,
    calculate: () -> Unit,
    randomize: () -> Unit,
    countdown: () -> Unit,
    result: Int,
    randomNumbers: List<Int>,
    isPlaying: Boolean
) {

    Column {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.height(200.dp).width(400.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(randomNumbers) { number ->

                Button(onClick = {
                    calculate()
                    randomize()
                    if (number == result) {
                        correctButton()
                    } else {
                        countdown()
                    }
                },
                    enabled = isPlaying
                ) {
                    Text("$number")
                }
            }

        }
    }
}