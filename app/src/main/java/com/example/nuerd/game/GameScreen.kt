package com.example.nuerd.game
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuerd.MenuButton
import com.example.nuerd.R
import com.example.nuerd.models.GameViewModel
import com.example.nuerd.ui.theme.NuerdTheme
import com.example.nuerd.ui.theme.highlightColor
import com.example.nuerd.ui.theme.mainBackgroundColor
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel(),
    onButtonClick: () -> Unit
    ) {
    // firstTimePlaying
    val firstGame by gameViewModel.firstGame.collectAsState()
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
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mainBackgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center

    ) {
        Column(modifier
            .fillMaxWidth()
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds // Prevents blurring
            )
            .padding(20.dp)) {

            GameInfoRow(lives = lives, timeRemaining = timeRemaining, scoreNumber = scoreNumber)
            Column(modifier
                .fillMaxWidth()
                .height(150.dp)
                // .border(width = 2.dp, color = highlightColor)
            ) {
                GameWindow(
                    firstGame = firstGame,
                    lives = lives,
                    first = firstNumber,
                    second = secondNumber,
                    isPlaying = isPlaying,
                    modifier = Modifier.weight(1f),
                    onPlayClicked = {
                        if (firstGame) {
                            gameViewModel.startGame() }
                        else {
                            gameViewModel.resetGame()
                            }

                            gameViewModel.calculate()
                            gameViewModel.countdown()

                    },

                    )
            }
            Column(modifier.fillMaxWidth(),
                //.border(width = 3.dp,color = highlightColor),

                horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(300.dp) // ✅ Explicitly constrain the bouncing area
                        .background(Color.Transparent)
                ) {

                    if (randomNumbers.isNotEmpty() && isPlaying) {
                        GameButtons(
                            gameViewModel = gameViewModel,
                            randomNumbers = randomNumbers,
                            result = result,
                        )
                    }
                }

            }

        }
        IconButton(
            onClick = onButtonClick,
        ) {
            Icon(imageVector = Icons.Filled.Home,
                contentDescription = "Home",
                tint = highlightColor)

        }





    }

}










@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    NuerdTheme {
        GameScreen(

            gameViewModel = viewModel(),
            onButtonClick = { }
        )
    }
}

@Composable
private fun GameScreenPreviewVersion(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mainBackgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Column(modifier
            .fillMaxWidth()
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
            .padding(20.dp)) {

            GameInfoRow(lives = 3, timeRemaining = 60, scoreNumber = 0)
            Column(modifier
                .fillMaxWidth()
                .height(150.dp)
            ) {
                GameWindow(
                    firstGame = true,
                    lives = 3,
                    first = 1,
                    second = 2,
                    isPlaying = true,
                    modifier = Modifier.weight(1f),
                    onPlayClicked = {}
                )
            }

            Column(modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(300.dp)
                        .background(Color.Transparent)
                )
            }

            Button(onClick = {}) {
                Text("Home")
            }
        }
    }
}