package com.example.nuerd.game

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuerd.ui.theme.buttonBackgroundColor
import com.example.nuerd.ui.theme.highlightColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

@Composable
fun ButtonGrid(
    lives: () -> Unit,
    calculate: () -> Unit,
    randomize: () -> Unit,
    countdown: () -> Unit,
    isPlaying: Boolean,
    correctButton: () -> Unit,
    result: Int,
    randomNumbers: List<Int>,

    ) {

    Column {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.width(400.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(randomNumbers.size) { number ->

                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundColor),
                    onClick = {
                        if (isPlaying) {
                            if (number == result) {
                                correctButton()
                            }
                        }
                    },
                    enabled = isPlaying
                ) {
                    Text(text = "$number", color = highlightColor)
                }
            }

        }
    }
}
@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    ButtonGrid(
        lives = {},
        correctButton = {},
        calculate = {},
        randomize = {},
        countdown = {},
        result = 0,
        randomNumbers = listOf(1, 2, 3, 4, 5),
        isPlaying = true // Provide some sample numbers for preview
    )
}