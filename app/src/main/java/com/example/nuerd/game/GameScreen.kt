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
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.nuerd.ui.theme.highlightColor

import com.example.nuerd.ui.theme.mainBackgroundColor
import com.example.nuerd.ui.theme.secondaryBackgroundColor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

@Composable
fun GameScreen(
    gameViewModel: GameViewModel,
    onButtonClick: () -> Unit
) {



    // firstTimePlaying
    val firstGame by gameViewModel.firstGame.collectAsState()

    // Scoring
    // val scoreNumber by gameViewModel.score.collectAsState()

    // Items for the countdown
    // val timeRemaining by gameViewModel.timeRemaining.collectAsState()
    val isPlaying by gameViewModel.isPlaying.collectAsState()
    val lives by gameViewModel.lives.collectAsState()

    // val scope: CoroutineScope = rememberCoroutineScope()

    // Numbers for the multiplication
    val firstNumber by gameViewModel.firstNumber.collectAsState()
    // by remember { mutableIntStateOf(0) }
    val secondNumber by gameViewModel.secondNumber.collectAsState()
    val result by gameViewModel.result.collectAsState()

    val buttonCount = 9
    val randomNumbers = remember(result) {
        mutableStateListOf<Int>().apply {
            repeat(buttonCount - 1) {
                add(Random.nextInt(1, 100))
            }
            add(result)
            shuffle()
        }
    }


    /*
    fun randomize() {
        randomNumbers.clear()
        repeat(buttonCount - 1) {
            randomNumbers.add(Random.nextInt(1, 100))
        }
        randomNumbers.add(gameViewModel.result.value)
        randomNumbers.shuffle()

    }



    fun countdown() {

       /* if (!isPlaying) {
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
        } */
    }

    fun calculate() {
        gameViewModel.calculate()
    }
 */

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mainBackgroundColor)
            .padding(16.dp)

    ) {
        Column() {
            Row(Modifier
                .fillMaxWidth()
                .height(100.dp)
                .border(width = 1.dp, color = Color.Black)
                .background(secondaryBackgroundColor)
                ) {
               Text(text ="GAME", fontSize = 40.sp, color = highlightColor)

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
                        if (firstGame) {
                            gameViewModel.startGame()
                        } else if (lives == 0) {
                            gameViewModel.resetGame()
                        } else {
                            gameViewModel.playPause()
                        }
                    },

                    )
            }
        }
        Row(Modifier.height(10.dp)) {
            // FILLER
        }

        Row(Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Black)
            .background(Color.Transparent)
        ) {
            GameInfoRow()

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
                isPlaying = isPlaying,
                correctButton = { gameViewModel.correctButton() },
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
    GameScreen(onButtonClick = {}, gameViewModel = GameViewModel())
}