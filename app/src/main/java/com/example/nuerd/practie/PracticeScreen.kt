package com.example.nuerd.practie

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TableRows
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nuerd.account.EditButton
import com.example.nuerd.game.GameButtons
import com.example.nuerd.game.GameWindow
import com.example.nuerd.models.GameViewModel
import com.example.nuerd.ui.theme.NuerdTheme
import com.example.nuerd.practie.tables.TablesScreen

@Composable
fun PracticeScreen(onButtonClick: () -> Unit, gameViewModel: GameViewModel?, navigateTables: () -> Unit = {}) {
    var isPracticeMode by remember { mutableStateOf(true)}

    Column(modifier = Modifier
        .fillMaxSize()
        .background(colorScheme.primary)
        .padding(16.dp)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(colorScheme.background, RoundedCornerShape(8.dp))
            .border(2.dp, colorScheme.surface, RoundedCornerShape(8.dp))
            .padding(16.dp)
        ) {
            Text("Practice",
                style = typography.titleLarge,
                color = colorScheme.onPrimary)
        }
        Spacer(modifier = Modifier.height(16.dp))

        GameWindow(
            firstGame = true,
            lives = 3,
            first = 1,
            second = 2,
            isPlaying = false,
            modifier = Modifier,
            onPlayClicked = {  }
        )
        GameButtons(
            gameViewModel = gameViewModel,
            result = 20,
            randomNumbers = listOf(20, 10, 5)
        )
        TablesScreen(
            onButtonClick = {  },
            navigatePractice = { }
            )





        EditButton(
            modifier = Modifier,
            onClick = navigateTables,
            title = "Tables",
            icon = Icons.Filled.TableRows
        )
        Button(onClick = onButtonClick) {
            Text("Home")
        }
    }

}
@Preview(showBackground = true)
@Composable
fun PracticeScreenPreview() {
    NuerdTheme {
        PracticeScreen(onButtonClick = {}, gameViewModel = null)
    }
}
