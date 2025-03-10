package com.example.nuerd.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nuerd.ui.theme.highlightColor
import com.example.nuerd.ui.theme.mainBackgroundColor

@Composable
fun SettingsScreen(onButtonClick: () -> Unit) {
    var soundVolume by remember { mutableStateOf(80) }
    var musicVolume by remember { mutableStateOf(80) }
    var soundEnabled by remember { mutableStateOf(true) }
    var musicEnabled by remember { mutableStateOf(true) }

    Column(modifier = Modifier.background(mainBackgroundColor)) {


            Text("Settings ", color = highlightColor)


            Text("Music volume: $musicVolume", color = highlightColor)
        Row(modifier = Modifier.fillMaxWidth()) {
            Slider(
                enabled = musicEnabled,
                value = musicVolume.toFloat(),
                onValueChange = { musicVolume = it.toInt() },
                valueRange = 0f..100f,
                modifier = Modifier.weight(1f)
            )
            Column() {
                Checkbox(
                    checked = musicEnabled,
                    onCheckedChange = { musicEnabled = it }
                )
            }
        }
            Text("Sound volume: $soundVolume", color = highlightColor)
            Slider(
                enabled = soundEnabled,
                value = soundVolume.toFloat(),
                onValueChange = { soundVolume = it.toInt() },
                valueRange = 0f..100f,
                modifier = Modifier.weight(1f)
            )

        Button(onClick = onButtonClick) {
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(onButtonClick = {})
}