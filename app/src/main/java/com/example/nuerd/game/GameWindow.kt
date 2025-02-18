package com.example.nuerd.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.nuerd.ui.theme.borderColor
import com.example.nuerd.ui.theme.highlightColor
import com.example.nuerd.ui.theme.secondaryBackgroundColor

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
        .background(secondaryBackgroundColor)) {

        Row(modifier.padding(end = 10.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Column(modifier
                .weight(1f)
                .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                if (!isPlaying && firstGame) {
                    CustomIconButton(
                        onPlayClicked = { onPlayClicked() },
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Play Icon"
                    ) }
                else if (!firstGame && lives == 0) {
                    Text(
                        text = "Game Over",
                        fontSize = 60.sp,
                        fontWeight = FontWeight.Bold
                    )
                    CustomIconButton (
                        onPlayClicked = { onPlayClicked() },
                        imageVector = Icons.Filled.Replay,
                        contentDescription = "Replay Icon"
                    )
                    Text("Play Again")
                }
                    else {
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
fun RemainingTime(timeRemaining: Int) {
    Row {
        Icon(
            imageVector = Icons.Filled.Timer,
            contentDescription = "Time Icon"
        )
        Text(
            text = "$timeRemaining",
            fontSize = 24.sp,

            )
    }
}

@Composable
fun ScoreCount(scoreNumber: Int) {
    Row {
    Icon(
        imageVector = Icons.Filled.Scoreboard,
        contentDescription = "Score Icon"

    )
    Text(
        text = "$scoreNumber",
        fontSize = 24.sp
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
            modifier = Modifier.size(60.dp),
            tint = highlightColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameWindowPreview() {

    GameWindow(onPlayClicked = {}, isPlaying = false, first = 0, second = 0, lives = 2, firstGame = true)
}