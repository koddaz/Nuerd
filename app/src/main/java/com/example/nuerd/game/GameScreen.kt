package com.example.nuerd.game
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuerd.models.GameViewModel
import com.example.nuerd.ui.theme.NuerdTheme
import com.example.nuerd.ui.theme.highlightColor
import com.example.nuerd.ui.theme.mainBackgroundColor



@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel(),
    onButtonClick: () -> Unit
    ) {
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
        Column(modifier.fillMaxWidth().border(width = 4.dp, color = highlightColor).background(Color.Transparent).padding(20.dp)) {

            GameInfoRow(lives = lives, timeRemaining = timeRemaining, scoreNumber = scoreNumber)
            Column(modifier
                .fillMaxWidth()
                .height(150.dp)
                .border(width = 2.dp, color = highlightColor)
            ) {
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
            Column(modifier.fillMaxWidth().border(width = 3.dp, color = highlightColor),

                horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(300.dp) // ✅ Explicitly constrain the bouncing area
                        .background(Color.Transparent)
                ) {
                    GameButtons()
                    if (randomNumbers.isNotEmpty() && isPlaying) {

                        ButtonGrid(
                            lives = { gameViewModel.looseLife() },
                            correctButton = { gameViewModel.correctButton() },
                            calculate = { gameViewModel.calculate() },
                            countdown = { gameViewModel.countdown() },
                            randomize = { gameViewModel.randomize() },
                            randomNumbers = randomNumbers,
                            result = result,
                            isPlaying = isPlaying
                        )
                    }
                }

            }
            Button(onClick = onButtonClick) {
                Text("Home")
            }
        }





    }

}










@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GameScreenPreview(

) {
    NuerdTheme {
        GameScreen(onButtonClick = {})
    }
}