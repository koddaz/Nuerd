package com.example.nuerd

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.nuerd.ui.theme.NuerdTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NuerdTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(

                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.Top) {
        TopBar(modifier.fillMaxWidth())
        Content(modifier.padding(top = 10.dp))
    }
}


@Composable
fun Content(modifier: Modifier = Modifier) {
    // Countdown
    var timeRemaining by remember { mutableStateOf(5) }
    var isPlaying by remember { mutableStateOf(false) }
    var lives by remember { mutableStateOf(3) }
    var scope = rememberCoroutineScope()


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
            }
        } else {
            timeRemaining = 5
            lives = 3
        }
    }

    // Numbers for the multiplication
    var firstNumber by remember { mutableStateOf(0) }
    var secondNumber by remember { mutableStateOf(0) }
    var result by remember { mutableStateOf(0)}
    val textParts = listOf("$firstNumber", "*", "$secondNumber", "=", "$result")

    // Index for the buttongrid
    val buttonCount = 9
    val buttonIndex = (0 until buttonCount).toList()

    fun calculate() {
        firstNumber = Random.nextInt(1, 10)
        secondNumber = Random.nextInt(1, 10)
        result = firstNumber * secondNumber
    }

    Column(modifier.fillMaxSize().background(Color.Cyan).padding(horizontal = 10.dp)) {
        Row( modifier
            .height(100.dp)
            .fillMaxWidth()
        ) {
            Row(
                modifier
                    .weight(1f)
                    .background(Color.Blue)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                textParts.forEach { part ->
                    Text(
                        text = part,
                        fontSize = 45.sp,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                }

            }
            Column(modifier
                .wrapContentSize()
                .padding(start = 8.dp)) {
                Button(onClick = {
                    countdown()
                }) {
                    Icon(
                        Icons.Filled.PlayArrow,
                        contentDescription = null)
                }

            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier.height(200.dp).width(400.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(buttonIndex) { index ->
                Button(onClick = {
                    calculate()

                }) {
                    Text("$index")
                }
            }

        }
        Text(
            text = if (isPlaying) timeRemaining.toString() else "Start",
            fontSize = 50.sp,
            color = Color.White,
        )
        Text(lives.toString())
    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    Column(modifier.height(50.dp).fillMaxWidth().background(Color.Red)) {
        Text("Bar")
    }
}


@Preview(showBackground = true)
@Composable
fun MainPreview() {
    NuerdTheme {
        MainScreen()
    }
}