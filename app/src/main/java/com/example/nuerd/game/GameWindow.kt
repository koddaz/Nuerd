package com.example.nuerd.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Scoreboard
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.nuerd.ui.theme.NuerdTheme
import com.example.nuerd.ui.theme.borderColor
import com.example.nuerd.ui.theme.highlightColor

@Composable
fun GameWindow(
    firstGame: Boolean,
    lives: Int,
    first: Int,
    second: Int,
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    onPlayClicked: () -> Unit
) {
    Column(modifier
        .fillMaxWidth()
        .height(150.dp)
        .border(
            width = 1.dp,
            color = borderColor
        )
        .background(Color.Transparent)) {

        Row(modifier.padding(end = 10.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Column(modifier
                .weight(1f)
                .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                if (firstGame) {
                    CustomIconButton(
                        onPlayClicked = { onPlayClicked() },
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Play Icon"
                    ) }
                else if (lives == 0) {
                    Text(
                        text = "Game Over",
                        style = MaterialTheme.typography.headlineMedium,
                        color = highlightColor

                    )
                    CustomIconButton (
                        onPlayClicked = { onPlayClicked() },
                        imageVector = Icons.Filled.Replay,
                        contentDescription = "Replay Icon",

                    )
                }
                    else if (isPlaying) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp), // Add some padding around the row
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // First Number
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "$first",
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center,
                            color = highlightColor
                        )

                        // Multiplication Sign
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 8.dp), // Adjust this value to move the * up/down
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = " * ",
                                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 40.sp),
                                textAlign = TextAlign.Center,
                                color = highlightColor
                            )
                        }

                        // Second Number
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "$second",
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center,
                            color = highlightColor
                        )
                    }


                    }

            }

        }

    }
}

@Composable
fun RemainingTime(timeRemaining: Int) {
    Row {
        Icon(
            imageVector = Icons.Filled.Timer,
            contentDescription = "Time Icon",
            tint = highlightColor,

        )
        Text(
            text = "$timeRemaining",
            fontSize = 24.sp,
            color = highlightColor,
            style = MaterialTheme.typography.headlineMedium

            )
    }
}

@Composable
fun ScoreCount(scoreNumber: Int) {
    Row {
    Icon(
        imageVector = Icons.Filled.Scoreboard,
        contentDescription = "Score Icon",
        tint = highlightColor
    )
    Text(
        text = "$scoreNumber",
        fontSize = 24.sp,
        color = highlightColor,
        style = MaterialTheme.typography.headlineMedium
    )
}
}


@Composable
fun CustomIconButton(
    onPlayClicked: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String
) {
    IconButton(onClick = onPlayClicked ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier.size(90.dp),
            tint = highlightColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameWindowPreview() {
    NuerdTheme{
    GameWindow(onPlayClicked = {}, isPlaying = true, first = 0, second = 1, lives = 2, firstGame = false)
}}