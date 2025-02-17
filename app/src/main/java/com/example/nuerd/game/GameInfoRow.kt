package com.example.nuerd.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuerd.ui.theme.borderColor
import com.example.nuerd.ui.theme.secondaryBackgroundColor

@Composable
fun GameInfoRow(modifier: Modifier = Modifier, gameViewModel: GameViewModel = viewModel()) {
    val timeRemaining by gameViewModel.timeRemaining.collectAsState()
    val scoreNumber by gameViewModel.score.collectAsState()
    val lives by gameViewModel.lives.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color.Transparent)
            .padding(vertical = 8.dp, horizontal = 10.dp),
        horizontalArrangement = Arrangement.Start

    ) {

            InfoBox(
                modifier.border(width = 4.dp, color = borderColor),
                boxContent = {
                CountingLives(lives = lives)
            })
            Row(modifier.weight(1f)) { /* FILLER */ }
            Row(modifier.border(width = 4.dp, color = borderColor)) {
                InfoBox(
                    boxContent = {
                        RemainingTime(timeRemaining = timeRemaining)
                    })

                InfoBox(
                    boxContent = {
                        ScoreCount(scoreNumber = scoreNumber)
                    })
            }


    }
}

@Composable
fun InfoBox(modifier: Modifier = Modifier, boxContent: @Composable () -> Unit) {
    Column(modifier
        .wrapContentSize()
        .background(secondaryBackgroundColor)
        ,
        ) {
        boxContent()
    }
}

@Preview(showBackground = true)
@Composable
fun InfoRowPreview() {
    GameInfoRow(gameViewModel = GameViewModel())

}