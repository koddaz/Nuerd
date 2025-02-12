package com.example.nuerd

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

@Composable
fun GameScreen(onButtonClick: () -> Unit) {
    // Scoring
    var scoreNumber  by remember { mutableStateOf(0) }

    // Items for the countdown
    var timeRemaining by remember { mutableStateOf(5) }
    var isPlaying by remember { mutableStateOf(false) }
    var lives by remember { mutableStateOf(3) }
    val scope = rememberCoroutineScope()

    // Numbers for the multiplication
    var firstNumber by remember { mutableStateOf(0) }
    var secondNumber by remember { mutableStateOf(0) }
    var result by remember { mutableStateOf(0) }

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
        if (lives > 0) {
            if (timeRemaining <= 0) {
                timeRemaining = 5
            }
            if (!isPlaying) {
                isPlaying = true
                scope.launch {
                    while (timeRemaining > 0 && isPlaying) {
                        delay(1.seconds)
                        timeRemaining--
                    }
                    isPlaying = false
                    if (lives > 0) {
                        lives--
                        countdown()
                    }
                }
            } else { isPlaying = false }
        } else {
            timeRemaining = 5
            lives = 3
        }
    }

    fun calculate() {
        firstNumber = Random.nextInt(1, 10)
        secondNumber = Random.nextInt(1, 10)
        result = firstNumber * secondNumber
    }

    fun score() {
        scoreNumber++
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text("Game")
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.Black)
            .padding(start = 16.dp),
           verticalAlignment = Alignment.CenterVertically
        ) {
            CountingLives(lives = lives)
            Text(text ="$timeRemaining",
                fontSize = 30.sp,
                color = Color.White)
            Text(text = "$scoreNumber",
                fontSize = 30.sp,
                color = Color.White)
        }
        Row(Modifier.background(Color.Black)) {
            GameWindow(
                first = firstNumber,
                second = secondNumber,
                modifier = Modifier.weight(1f))
            PlayButton(
                calculate = { calculate() },
                countdown = { countdown() }
            )
        }
        Row(modifier = Modifier.padding(top = 16.dp)) {
            ButtonGrid(
                score = { score() },
                calculate = { calculate() },
                countdown = { countdown() },
                randomize = { randomize() },
                randomNumbers = randomNumbers,
                result = result,
                scoreNumber = scoreNumber)
        }

        Button(onClick = onButtonClick) {
            Text("Home")
        }
    }

}


@Composable
fun PlayButton(calculate: () -> Unit, countdown: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier.padding(8.dp)) {
        Button(onClick = {
            calculate()
            countdown()


        }) {
            Icon(Icons.Filled.PlayArrow, contentDescription = null, tint = Color.Blue)
        }
    }
}

@Composable
fun CountingLives(lives: Int) {
    Row() {
        if (lives == 0) {
            Text("Game over")
        } else {
            for (life in 1..lives) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        }

    }
}

@Composable
fun GameWindow(
    first: Int,
    second: Int,
    modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth().height(150.dp)) {
        Row() {
            Column(modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.Green)
                .border(
                    width = 1.dp,
                    color = Color.Black),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                if (first == 0 && second == 0) {} else {
                    Text(
                        text = "$first * $second",
                        fontSize = 60.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
        }

        }

    }
}


@Composable
fun ButtonGrid(
    score: () -> Unit,
    calculate: () -> Unit,
    randomize: () -> Unit,
    countdown: () -> Unit,
    result: Int,
    randomNumbers: List<Int>,
    scoreNumber: Int
) {

    Column ( ) {
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
                        score()
                    } else {
                        countdown()
                    }
                }) {
                    Text("$number")
                }
            }

        }
    }
}
@Preview(showBackground = true)
@Composable
fun GameScreenPreview(

) {
    GameScreen(onButtonClick = {})
}