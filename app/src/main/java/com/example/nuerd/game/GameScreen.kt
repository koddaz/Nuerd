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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.nuerd.ui.theme.mainBackgroundColor
import com.example.nuerd.ui.theme.secondaryBackgroundColor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

class GameViewModel: ViewModel() {

    // Calculate
    private val _firstNumber = MutableStateFlow(0)
    private val _secondNumber = MutableStateFlow(0)
    private val _result = MutableStateFlow(0)

    val firstNumber: StateFlow<Int> = _firstNumber.asStateFlow()
    val secondNumber: StateFlow<Int> = _secondNumber.asStateFlow()
    val result: StateFlow<Int> = _result.asStateFlow()

    // ButtonGrid
    private val _buttonCount = MutableStateFlow(9)
    private val _randomNumbers = MutableStateFlow(listOf<Int>())

    private val buttonCount: StateFlow<Int> = _buttonCount.asStateFlow()
    val randomNumbers: StateFlow<List<Int>> = _randomNumbers.asStateFlow()

    // Countdown
    private val _timeRemaining = MutableStateFlow(10)
    private val _isPlaying = MutableStateFlow(false)
    private val _lives = MutableStateFlow(3)
    private val _firstGame = MutableStateFlow(true)

    var timeRemaining: StateFlow<Int> = _timeRemaining.asStateFlow()
    var isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()
    var lives: StateFlow<Int> = _lives.asStateFlow()
    var firstGame: StateFlow<Boolean> = _firstGame.asStateFlow()

    // Score
    private var _score = MutableStateFlow(0)
    var score: StateFlow<Int> = _score.asStateFlow()

    fun calculate() {
        _firstNumber.value = Random.nextInt(1, 10)
        _secondNumber.value = Random.nextInt(1, 10)
        _result.value = _firstNumber.value * _secondNumber.value
    }

    fun randomize() {
        val numbers = mutableListOf<Int>()

        repeat(buttonCount.value - 1) {
            numbers.add(Random.nextInt(1, 100)) // Generate random numbers
        }
        numbers.add(result.value) // Ensure the correct answer is included
        numbers.shuffle() // Shuffle so the correct answer isn't always last

        _randomNumbers.value = numbers // Update the StateFlow
    }


    fun startCountdown() {
        if (_isPlaying.value) { return }
        _isPlaying.value = true
        _lives.value = 3
        _firstGame.value = false
        _timeRemaining.value = 10

        if (_isPlaying.value) {

            if (_timeRemaining.value == 0 && _lives.value > 0) {
                looseLife()
            } else
                viewModelScope.launch {
                    while (_timeRemaining.value > 0 && _isPlaying.value && _lives.value > 0) {
                        delay(1.seconds)
                        _timeRemaining.value--
                    }


                }

        } else { return }
    }

    fun looseLife() {
        _lives.value--
        _timeRemaining.value = 10
    }

    fun gainLife() {
       _lives.value++
        _timeRemaining.value = 10
    }
   // fun countdown() {

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
   // }


}

@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel(), onButtonClick: () -> Unit) {
    // firstTimePlaying
    val firstGame by gameViewModel.firstGame.collectAsState()

    // Scoring
    var scoreNumber  by remember { mutableIntStateOf(0) }

    // Items for the countdown
    val timeRemaining by gameViewModel.timeRemaining.collectAsState()
    val isPlaying by gameViewModel.isPlaying.collectAsState()


    val lives by gameViewModel.lives.collectAsState()

    // val scope: CoroutineScope = rememberCoroutineScope()

    // Numbers for the multiplication
    val firstNumber by gameViewModel.firstNumber.collectAsState()
    val secondNumber by gameViewModel.secondNumber.collectAsState()
    val result by gameViewModel.result.collectAsState()

    val randomNumbers by gameViewModel.randomNumbers.collectAsState()

    // Buttons for the grid
    // val buttonCount = 9


   /*  fun randomize() {
        randomNumbers.clear()
        repeat(buttonCount - 1) {
            randomNumbers.add(Random.nextInt(1, 100))
        }
        randomNumbers.add(result)
        randomNumbers.shuffle()

    } */

   LaunchedEffect(Unit) {
       gameViewModel.calculate()
       gameViewModel.randomize()

   }

    /*
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
 */

    fun correctButton() {
        scoreNumber++
        // timeRemaining = 10
        gameViewModel.startCountdown()
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
                GameInfoRow(
                    lives = lives,
                    timeRemaining = timeRemaining,
                    scoreNumber = scoreNumber
                )


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
                            gameViewModel.startCountdown()
                        }
                    }
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
                randomNumbers = randomNumbers,
                lives = { lives == 2 },
                isPlaying = isPlaying,
                correctButton = { correctButton() },
                calculate = { gameViewModel.calculate() },
                countdown = { gameViewModel.startCountdown() },
                randomize = { gameViewModel.randomize() },
                result = result,
                )
        }

        Button(onClick = onButtonClick) {
            Text("Home")
        }

        Column {
            Text(text = "$timeRemaining", color = Color.Red)
            Button(onClick = { gameViewModel.startCountdown() }) {
                Text("Start Countdown")
            }
        }
    }

}










@Preview(showBackground = true)
@Composable
fun GameScreenPreview(

) {
    GameScreen(gameViewModel = GameViewModel(), onButtonClick = {})
}