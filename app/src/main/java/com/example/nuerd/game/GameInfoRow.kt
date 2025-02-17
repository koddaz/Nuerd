package com.example.nuerd.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Scoreboard
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nuerd.ui.theme.secondaryBackgroundColor

@Composable
fun GameInfoRow(lives: Int, scoreNumber: Int, timeRemaining: Int) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(secondaryBackgroundColor)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            CountingLives(lives = lives)
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RemainingTime(timeRemaining = timeRemaining)
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            ScoreCount(scoreNumber = scoreNumber)
        }
    }
}

@Composable
fun RemainingTime(timeRemaining: Int) {
    Row() {
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
    Row() {
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