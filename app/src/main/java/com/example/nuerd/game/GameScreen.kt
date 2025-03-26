package com.example.nuerd.game
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistPlay
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuerd.R
import com.example.nuerd.account.EditButton
import com.example.nuerd.models.CustomColumn
import com.example.nuerd.models.GameButtonsViewModel
import com.example.nuerd.models.GameViewModel
import com.example.nuerd.ui.theme.NuerdTheme
import kotlin.compareTo


@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel? = viewModel(),
    buttonsViewModel: GameButtonsViewModel = viewModel(),
) {
    val firstGame by gameViewModel?.firstGame?.collectAsState()
        ?: remember { mutableStateOf(true) }
    val firstNumber by gameViewModel?.firstNumber?.collectAsState()
        ?: remember { mutableStateOf(1) }
    val secondNumber by gameViewModel?.secondNumber?.collectAsState()
        ?: remember { mutableStateOf(1) }
    val result by gameViewModel?.result?.collectAsState()
        ?: remember { mutableStateOf(1) }
    val randomNumbers by gameViewModel?.randomNumbers?.collectAsState()
        ?: remember { mutableStateOf(listOf(1, 2, 3, 4)) }
    val timeRemaining by gameViewModel?.timeRemaining?.collectAsState()
        ?: remember { mutableStateOf(10) }
    val lives by gameViewModel?.lives?.collectAsState()
        ?: remember { mutableStateOf(3) }
    val isPlaying by gameViewModel?.isPlaying?.collectAsState()
        ?: remember { mutableStateOf(false) }
    val scoreNumber by gameViewModel?.scoreNumber?.collectAsState()
        ?: remember { mutableStateOf(0) }
    val isPaused by buttonsViewModel.isPaused.collectAsState()
    val countDown by gameViewModel?.countDown?.collectAsState()
        ?: remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        gameViewModel?.calculate()
    }

    LaunchedEffect(gameViewModel?.isPaused?.collectAsState()?.value) {
        gameViewModel?.isPaused?.value?.let { isPaused ->
            buttonsViewModel.setPaused(isPaused)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.primary)
            .padding(16.dp),
    ) {
        Column(modifier = Modifier
            .background(colorScheme.background, RoundedCornerShape(8.dp))
            .border(width = 2.dp, color = colorScheme.surface, RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .padding(8.dp)) {
            GameWindow(
                firstGame = firstGame,
                lives = lives,
                first = firstNumber,
                second = secondNumber,
                isPlaying = isPlaying,
                modifier = Modifier.fillMaxWidth().height(150.dp),
                onPlayClicked = {
                    gameViewModel?.countdownStart()
                },
                isPaused = isPaused,
                countDown = countDown
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorScheme.background)
                ,
            ) {
                Column(
                    modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    CountingLives(lives = lives, size = 40)
                }
                Column(
                    modifier = modifier
                        .weight(1f)
                        .background(colorScheme.secondary, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    RemainingTime(timeRemaining = timeRemaining)
                }
            }

        }


        Spacer(modifier = Modifier.height(8.dp))


        Column(modifier = Modifier
            .background(colorScheme.background, RoundedCornerShape(8.dp))
            .border(width = 2.dp, color = colorScheme.surface, RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .padding(8.dp)) {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .background(colorScheme.primary)
            ) {
                if (randomNumbers.isNotEmpty() && isPlaying) {
                    GameButtons(
                        gameViewModel = gameViewModel,
                        randomNumbers = randomNumbers,
                        result = result,
                        boxWidth = 300f,      // Fixed width in pixels
                        boxHeight = 150f,     // Fixed height in pixels
                        ballSize = 60f        // Fixed ball size
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorScheme.background, RoundedCornerShape(8.dp))
                .border(2.dp, colorScheme.surface, RoundedCornerShape(8.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f).background(colorScheme.secondary).padding(8.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                ScoreCount(scoreNumber = scoreNumber)
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Row(modifier = Modifier.padding(end = 16.dp)) {
                    if (isPaused) {
                        // Game is paused - show play button
                        CustomIconButton(
                            onPlayClicked = {
                                buttonsViewModel.setPaused(false)
                                gameViewModel?.resumeGame()
                            },
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Resume",
                            tint = colorScheme.onSecondary,
                        )
                    } else if (isPlaying) {
                        // Game is playing - show pause button
                        CustomIconButton(
                            onPlayClicked = {
                                buttonsViewModel.setPaused(true)
                                gameViewModel?.pauseGame()
                            },
                            imageVector = Icons.Filled.Pause,
                            contentDescription = "Pause",
                            tint = colorScheme.onSecondary,
                        )
                    } else if (firstGame || lives == 0) {
                        // First game or game over - show play button
                        CustomIconButton(
                            onPlayClicked = {
                                buttonsViewModel.setPaused(false)
                                if (firstGame || lives == 0) {
                                    gameViewModel?.resetGame() // Reset game state if needed
                                    gameViewModel?.countdownStart() // Start countdown instead of directly starting
                                } else {
                                    gameViewModel?.countdownStart() // Always use countdown
                                }
                            },
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Play",
                            tint = colorScheme.onSecondary,
                        )
                    }
                }


            }

        }
        val lastButtonClickTime = remember { mutableStateOf(0L) }
        val buttonDebounceTime = 200L

        EditButton(
            onClick = {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastButtonClickTime.value > buttonDebounceTime) {
                    lastButtonClickTime.value = currentTime
                    gameViewModel?.correctButton()
                }
            },
            title = "Play",
            icon = Icons.Filled.PlayArrow
        )
    }
}














@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    NuerdTheme(theme = "Green") {
        GameScreen(

            gameViewModel = null,
        )
    }
}

