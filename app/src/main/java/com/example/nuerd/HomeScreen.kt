package com.example.nuerd

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nuerd.ui.theme.buttonBackgroundColor
import com.example.nuerd.ui.theme.highlightColor
import com.example.nuerd.ui.theme.mainBackgroundColor
import com.example.nuerd.ui.theme.secondaryBackgroundColor


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onGameClick: () -> Unit,
    onPracticeClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onTablesClick: () -> Unit,

    ) {
    Column(modifier.fillMaxSize().background(mainBackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Column(modifier.width(300.dp).padding(bottom = 10.dp).background(secondaryBackgroundColor)) {
            Text(
                text ="Nuerd",
                fontSize = 50.sp,
                color = highlightColor
            )
        }
        Column(modifier
            .width(300.dp)
            .background(secondaryBackgroundColor)
            .padding(20.dp)
        ) {


            // Settings
            MenuButton(onClick = onSettingsClick, imageVector = Icons.Filled.Settings, contentDescription = "Settings")
            // Practice
            MenuButton(onClick = onPracticeClick, imageVector = Icons.Filled.Calculate, contentDescription = "Practice")
            // Tables
            MenuButton(onClick = onTablesClick, imageVector = Icons.Filled.TableChart, contentDescription = "Tables")
            // Play
            MenuButton(onClick = onGameClick, imageVector = Icons.Filled.Settings, contentDescription = "Play")

        }
    }
}


@Composable
fun MenuButton(onClick: () -> Unit, imageVector: ImageVector, contentDescription: String) {

    Button(
        colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundColor),
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            onClick()
        }) {

        Row(Modifier.weight(2f)) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                tint = highlightColor,
                modifier = Modifier.padding(end = 10.dp)
            )
            Text(text = contentDescription, color = highlightColor)
        }



    }


}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(onGameClick = {}, onPracticeClick = {}, onSettingsClick = {}, onTablesClick = {})
}