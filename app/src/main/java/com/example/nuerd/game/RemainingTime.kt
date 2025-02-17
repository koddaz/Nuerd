package com.example.nuerd.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nuerd.ui.theme.highlightColor

@Composable
fun RemainingTime(modifier: Modifier = Modifier, timeRemaining: Int) {
    Column(modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Filled.Timer,
            contentDescription = "Time Icon",
            tint = highlightColor,

        )
        Text(
            text = "$timeRemaining",
            fontSize = 24.sp,
            color = highlightColor
            )
    }
}