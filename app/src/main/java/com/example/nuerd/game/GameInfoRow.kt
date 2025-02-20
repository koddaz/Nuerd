package com.example.nuerd.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nuerd.ui.theme.secondaryBackgroundColor

@Composable
fun GameInfoRow(lives: Int, timeRemaining: Int, scoreNumber: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(20.dp),
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

@Preview(showBackground = true)
@Composable
fun GameInfoRowPreview() {
    GameInfoRow(lives = 3, timeRemaining = 60, scoreNumber = 0)
}