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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuerd.models.GameViewModel
import com.example.nuerd.ui.theme.NuerdTheme
import com.example.nuerd.ui.theme.mainBackgroundColor
import com.example.nuerd.ui.theme.secondaryBackgroundColor



@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel(), onButtonClick: () -> Unit) {
    // firstTimePlaying
    var firstGame by remember { mutableStateOf(true) }

    val firstNumber by gameViewModel.firstNumber.collectAsState()
    val secondNumber by gameViewModel.secondNumber.collectAsState()
    val result by gameViewModel.result.collectAsState()

    val randomNumbers by gameViewModel.randomNumbers.collectAsState()

    val timeRemaining by gameViewModel.timeRemaining.collectAsState()
    val lives by gameViewModel.lives.collectAsState()
    val isPlaying by gameViewModel.isPlaying.collectAsState()

    val scoreNumber by gameViewModel.scoreNumber.collectAsState()

    LaunchedEffect(Unit) {
        gameViewModel.calculate()
        gameViewModel.randomize()
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
                                gameViewModel.resetGame()
                            }
                            gameViewModel.calculate()
                            gameViewModel.countdown()
                            gameViewModel.randomize()
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
                lives = { gameViewModel.looseLife() },
                isPlaying = isPlaying,
                correctButton = { gameViewModel.correctButton() },
                calculate = { gameViewModel.calculate() },
                countdown = { gameViewModel.countdown() },
                randomize = { gameViewModel.randomize() },
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
    NuerdTheme {
        GameScreen(onButtonClick = {})
    }
}