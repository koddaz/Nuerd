package com.example.nuerd.Game
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

@Composable
fun GameScreen(onButtonClick: () -> Unit) {
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
                while (timeRemaining > 0 && isPlaying) {
                    delay(1.seconds)
                    timeRemaining--
                }
                isPlaying = false
                if (timeRemaining == 0 && lives > 0) {
                    lives--
                    timeRemaining = 10
                    countdown()
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
            .padding(16.dp)

    ) {
        Text("Game")
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.Black)
            .padding(start = 16.dp),
           verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.
            weight(1f)) {
                CountingLives(lives = lives)
            }
            Column(modifier = Modifier.
            weight(1f)) {
                Text(
                    text = "Time: $timeRemaining",
                    fontSize = 30.sp,
                    color = Color.White
                )
            }
            Column(modifier = Modifier.
            weight(1f)) {
                Text(
                    text = "Score: $scoreNumber",
                    fontSize = 30.sp,
                    color = Color.White
                )
            }
        }
        Row(Modifier.background(Color.Black)) {
            Column(modifier = Modifier.width(50.dp)) {

            }
            GameWindow(
                first = firstNumber,
                second = secondNumber,
                modifier = Modifier.weight(1f))
            PlayButton(
                calculate = { calculate() },
                countdown = { countdown() },
                randomize = { randomize() }
            )
        }
        Row(modifier = Modifier.padding(16.dp)) {
            ButtonGrid(
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