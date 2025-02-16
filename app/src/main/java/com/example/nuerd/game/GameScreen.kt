package com.example.nuerd.game
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.nuerd.ui.theme.mainBackgroundColor
import com.example.nuerd.ui.theme.secondaryBackgroundColor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

@Composable
fun GameScreen(onButtonClick: () -> Unit) {
    // firstTimePlaying
    var firstGame by remember { mutableStateOf(true) }

    // Scoring
    var scoreNumber  by remember { mutableIntStateOf(0) }

    // Items for the countdown
    var timeRemaining by remember { mutableIntStateOf(10) }
    var isPlaying by remember { mutableStateOf(false) }
    var lives by remember { mutableIntStateOf(3) }
    val scope: CoroutineScope = rememberCoroutineScope()

    // Numbers for the multiplication
    var firstNumber by remember { mutableIntStateOf(0) }
    var secondNumber by remember { mutableIntStateOf(0) }
    var result by remember { mutableIntStateOf(0) }

    // Buttons for the grid
    val buttonCount = 9
    val randomNumbers = remember { mutableStateListOf<Int>()}

    fun randomize() {
        randomNumbers.clear()
        repeat(buttonCount - 1) {
            randomNumbers.add(Random.nextInt(1, 100))
        }
        randomNumbers.add(result)
        randomNumbers.shuffle()

    }

    LaunchedEffect(Unit) { randomize() }

    fun countdown() {
        if (!isPlaying) {
            isPlaying = true
            scope.launch {

                while (timeRemaining > 0 && isPlaying && lives > 0) {
                    delay(1.seconds)
                    timeRemaining--
                }
                isPlaying = false
                if (timeRemaining == 0 && lives > 0) {
                    lives--
                    timeRemaining = 10
                    countdown()
                } else if (lives == 0) {
                    scoreNumber = 0
                    isPlaying = false
                    timeRemaining = 0
                    lives = 0
                }
            }
        }
    }

    fun calculate() {
        firstNumber = Random.nextInt(1, 10)
        secondNumber = Random.nextInt(1, 10)
        result = firstNumber * secondNumber
    }

    fun correctButton() {
        scoreNumber++
        timeRemaining = 10
        countdown()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mainBackgroundColor)
            .padding(16.dp)

    ) {
        Column(Modifier.background(secondaryBackgroundColor)) {
            Row(Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color.Black)
                .background(secondaryBackgroundColor)
                .padding(16.dp)) {
                GameInfoRow(lives = lives, timeRemaining = timeRemaining, scoreNumber = scoreNumber)

            }
            Row(Modifier.height(10.dp)) {
                // FILLER
            }
            Column(Modifier.height(150.dp)) {

                GameWindow(
                    firstGame = firstGame,
                    lives = lives,
                    first = firstNumber,
                    second = secondNumber,
                    isPlaying = isPlaying,
                    modifier = Modifier.weight(1f),
                    onPlayClicked = {
                        if (!isPlaying) {
                            if (firstGame) {
                            firstGame = false } else {
                                scoreNumber = 0
                                timeRemaining = 10
                                lives = 3
                            }
                            calculate()
                            countdown()
                            randomize()
                        }
                    },

                    )
            }
        }
        Row(Modifier.height(10.dp)) {
            // FILLER
        }


        Row(modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Black)
            .background(secondaryBackgroundColor)
            .padding(16.dp)) {
            ButtonGrid(
                lives = { lives-- },
                isPlaying = isPlaying,
                correctButton = { correctButton() },
                calculate = { calculate() },
                countdown = { countdown() },
                randomize = { randomize() },
                randomNumbers = randomNumbers,
                result = result,
                )
        }

        Button(onClick = onButtonClick) {
            Text("Home")
        }
    }

}










@Preview(showBackground = true)
@Composable
fun GameScreenPreview(

) {
    GameScreen(onButtonClick = {})
}