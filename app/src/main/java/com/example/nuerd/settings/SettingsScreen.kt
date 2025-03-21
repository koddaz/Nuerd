package com.example.nuerd.settings

import android.app.Application
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Rule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuerd.account.EditButton
import com.example.nuerd.models.AppDatabase
import com.example.nuerd.models.GameViewModel
import com.example.nuerd.models.SettingsDao
import com.example.nuerd.models.Theme
import com.example.nuerd.models.ThemeViewModel
import com.example.nuerd.models.ThemeViewModelFactory
import com.example.nuerd.ui.theme.NuerdTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.toString

@Composable
fun SettingsScreen(

    onButtonClick: () -> Unit,
    gameViewModel: GameViewModel?,
    themeViewModel: ThemeViewModel?)
{
    var soundVolume by remember { mutableStateOf(80) }
    var musicVolume by remember { mutableStateOf(80) }
    var soundEnabled by remember { mutableStateOf(true) }
    var musicEnabled by remember { mutableStateOf(true) }
    var difficulty by remember { mutableStateOf(listOf(1, 2, 3)) }
    var themeList by remember { mutableStateOf(listOf("Green", "Yellow", "Blue"))}
    val selectedDifficulty by gameViewModel?.difficulty?.collectAsState() ?: remember { mutableStateOf(2) }
    val currentDifficulty = selectedDifficulty
    val currentTheme by themeViewModel?.theme?.collectAsState() ?: remember { mutableStateOf("Green") }
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize().background(colorScheme.primary).padding(16.dp).verticalScroll(scrollState)) {

        Column(modifier = Modifier.fillMaxWidth()
            .background(colorScheme.background, RoundedCornerShape(8.dp))
            .border(2.dp, colorScheme.surface, RoundedCornerShape(8.dp)).padding(16.dp)) {
            Text(
                "Volume",
                color = colorScheme.onBackground,
                style = typography.bodySmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.padding(start = 16.dp)) {
                SliderModel(
                    title = "Music $musicVolume",
                    value = musicVolume,
                    enabled = musicEnabled,
                    onValueChange = { musicVolume = it },
                    onCheckedChange = { musicEnabled = it }
                )
                SliderModel(
                    title = "Effects $soundVolume",
                    value = soundVolume,
                    enabled = soundEnabled,
                    onValueChange = { soundVolume = it },
                    onCheckedChange = { soundEnabled = it }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Column(modifier = Modifier.fillMaxWidth()
            .background(colorScheme.background, RoundedCornerShape(8.dp))
            .border(2.dp, colorScheme.surface, RoundedCornerShape(8.dp)).padding(16.dp)) {
            Text(
                "Difficulty",
                color = colorScheme.onBackground,
                style = typography.bodySmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly) {

                    difficulty.forEach { value ->
                        Column(
                            modifier = Modifier.weight(1f).padding(start = 20.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            DifficultyModel(
                                onClick = {
                                    gameViewModel?.setDifficulty(newDifficulty = value)
                                },
                                selected = value == currentDifficulty,
                            )
                            Text(
                                if (value == 1) "Easy"
                                else if (value == 2) "Medium"
                                else "Hard",
                                color = colorScheme.onBackground,
                                style = typography.bodySmall
                            )
                        }
                    }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.fillMaxWidth().background(colorScheme.background, RoundedCornerShape(8.dp)).border(2.dp, colorScheme.surface, RoundedCornerShape(8.dp)).padding(16.dp)) {
            Text(
                "Theme",
                color = colorScheme.onBackground,
                style = typography.bodySmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly) {

                themeList.forEach { theme ->
                    Column(
                        modifier = Modifier.weight(1f).padding(start = 20.dp),
                        horizontalAlignment = Alignment.Start) {
                    DifficultyModel(
                        onClick = {
                            themeViewModel?.setTheme(newTheme = theme)
                        },
                        selected = theme == currentTheme,
                    )
                        Text("$theme", style = typography.bodySmall, color = colorScheme.onPrimary)

                }
                }
            }
        }
    }
}


@Preview
@Composable
fun SettingsScreenPreview() {
    NuerdTheme(theme = "Yellow") {
        SettingsScreen(themeViewModel = null, onButtonClick = {}, gameViewModel = null)
    }
}