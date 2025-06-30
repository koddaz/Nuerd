package com.example.nuerd.practie

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.TableRows
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuerd.account.EditButton
import com.example.nuerd.game.GameButtons
import com.example.nuerd.game.GameWindow
import com.example.nuerd.models.GameViewModel
import com.example.nuerd.practie.tables.ScrollingColumn
import com.example.nuerd.ui.theme.NuerdTheme
import com.example.nuerd.practie.tables.TablesScreen

@Composable
fun PracticeScreen(gameViewModel: GameViewModel?) {
    val scrollState = rememberScrollState()
    var isTableVisible by remember {mutableStateOf(false)}
    val firstNumber by gameViewModel?.firstNumber?.collectAsState() ?: remember { mutableStateOf(1) }
    val secondNumber by gameViewModel?.secondNumber?.collectAsState() ?: remember { mutableStateOf(2) }
    val numberList by gameViewModel?.randomNumbers?.collectAsState() ?: remember { mutableStateOf(listOf(1,2,3)) }
    val result by gameViewModel?.result?.collectAsState() ?: remember { mutableStateOf(0) }
    val correct by gameViewModel?.correctAnswers?.collectAsState() ?: remember { mutableStateOf(0) }
    val wrong by gameViewModel?.wrongAnswers?.collectAsState() ?: remember { mutableStateOf(0) }
    var tableNumber by remember { mutableStateOf(1) }

    LaunchedEffect(tableNumber) {
        gameViewModel?.calculateWithTable(tableNumber)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(colorScheme.primary)
        .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.Center)
    {


        Spacer(modifier = Modifier.height(16.dp))
        PracticeCalculate(
            firstNumber = tableNumber,
            secondNumber = secondNumber,
            correct = correct,
            wrong = wrong,
        )

        Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()
                .background(colorScheme.background, RoundedCornerShape(8.dp)).padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                numberList.forEach { index ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                        .background(colorScheme.primary, CircleShape)
                        .border(2.dp, colorScheme.surface, CircleShape)
                        .size(60.dp)
                        .clickable(onClick = {
                            if (result == index) {
                                gameViewModel?.practiceCorrect()
                                gameViewModel?.calculateWithTable(tableNumber)
                            } else {
                                gameViewModel?.practiceWrong()
                                gameViewModel?.calculateWithTable(tableNumber)
                            }
                        })) {
                        Text("$index", color = colorScheme.onPrimary)
                    }


                }
            }
        Spacer(modifier = Modifier.height(4.dp))
        EditButton(
            title = if (!isTableVisible) "Show tables" else "Close tables",
            onClick = { isTableVisible = !isTableVisible },
            icon = if(!isTableVisible) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
            iconPlacement = 2,
            spacerLength = 0.dp
        )
        if (isTableVisible) {
            ScrollingColumn(
                tableNumber = tableNumber,
                onTableNumberChange = { newTableNumber ->
                   tableNumber = newTableNumber
                },
            )
        }

    }






}

@Composable
fun PracticeCalculate(
    firstNumber: Int,
    secondNumber: Int,
    correct: Int = 0,
    wrong: Int = 0) {
    Column(modifier = Modifier
        .background(colorScheme.background, RoundedCornerShape(8.dp))
        .border(2.dp, colorScheme.surface, RoundedCornerShape(8.dp))
        .padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(colorScheme.secondary, RoundedCornerShape(8.dp))
                        .border(2.dp, colorScheme.surface, RoundedCornerShape(8.dp))
                        .padding(8.dp),
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Correct",
                            color = colorScheme.onBackground,
                            style = typography.titleSmall
                        )
                        Text(
                            "$correct",
                            color = colorScheme.onBackground,
                            style = typography.headlineLarge,

                            )

                    }
                }

            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(colorScheme.error, RoundedCornerShape(8.dp))
                        .border(2.dp, colorScheme.errorContainer, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Wrong",
                            color = colorScheme.onError,
                            style = typography.titleSmall
                        )
                        Text(
                            "$wrong",
                            color = colorScheme.onError,
                            style = typography.headlineLarge
                        )
                    }
                }
            }


        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorScheme.primary, RoundedCornerShape(8.dp))
                .border(2.dp, colorScheme.surface, RoundedCornerShape(8.dp))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                "$firstNumber",
                style = typography.titleLarge,
                color = colorScheme.onPrimary
            )
            Text(
                "*",
                style = typography.titleLarge,
                color = colorScheme.onPrimary
            )
            Text(
                "$secondNumber",
                style = typography.titleLarge,
                color = colorScheme.onPrimary
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PracticeScreenPreview() {
    NuerdTheme {
        PracticeScreen(gameViewModel = null)
    }
}
