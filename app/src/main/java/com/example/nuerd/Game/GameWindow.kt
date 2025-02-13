package com.example.nuerd.Game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameWindow(
    first: Int,
    second: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxWidth().height(150.dp)) {
        Row {
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
                if (first == 0 && second == 0) {
                    return
                } else {
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