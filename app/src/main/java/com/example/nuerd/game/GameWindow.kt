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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.nuerd.ui.theme.NuerdTheme

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
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = colorScheme.surface,
                RoundedCornerShape(8.dp)
            )
            .background(colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        when {
            firstGame -> {
                Text(
                    "Press Play to Start",
                    style = typography.headlineMedium,
                    color = colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }
            lives == 0 -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Game Over",
                        style = typography.headlineMedium,
                        color = colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    CustomIconButton(
                        onPlayClicked = onPlayClicked,
                        imageVector = Icons.Filled.Replay,
                        tint = colorScheme.error,
                        contentDescription = "Replay Icon"
                    )
                }
            }
            isPlaying -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "$first",
                        style = typography.headlineLarge,
                        textAlign = TextAlign.Center,
                        color = colorScheme.surface
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = " * ",
                        style = typography.headlineLarge.copy(fontSize = 40.sp),
                        textAlign = TextAlign.Center,
                        color = colorScheme.surface
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "$second",
                        style = typography.headlineLarge,
                        textAlign = TextAlign.Center,
                        color = colorScheme.surface
                    )
                }
            }
        }
    }
}







@Preview(showBackground = true)
@Composable
fun GameWindowPreview() {
    NuerdTheme(theme = "Green") {
        GameWindow(onPlayClicked = {}, isPlaying = false, first = 0, second = 1, lives = 2, firstGame = true)
    }
}