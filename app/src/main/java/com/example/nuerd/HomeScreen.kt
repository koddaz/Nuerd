package com.example.nuerd

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onGameClick: () -> Unit,
    onPracticeClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onTablesClick: () -> Unit,
//    onHelpClick: () -> Unit,
//    onAboutClick: () -> Unit

    ) {
    val buttonModifier = Modifier.width(150.dp)
    Column(modifier.fillMaxSize().padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier
            .width(300.dp)
            .background(Color.Green)
            ,
            horizontalArrangement = Arrangement.Center
            ) {
            Text(text ="Nuerd", fontSize = 50.sp, color = Color.White )
        }

        Column(modifier
            .width(300.dp)
            .padding(top = 20.dp)
            .background(Color.Green),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = onSettingsClick,
                buttonModifier
            ) {
                Text("Settings")
            }

            Button(
                onClick = onPracticeClick,
                buttonModifier
            ) {
                Text("Practice")
            }

            Button(
                onClick = onTablesClick,
                buttonModifier
            ) {
                Text("Tables")
            }

            Button(
                onClick = onGameClick,
                buttonModifier
            ) {
                Text("Play")

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(onGameClick = {}, onPracticeClick = {}, onSettingsClick = {}, onTablesClick = {})
}