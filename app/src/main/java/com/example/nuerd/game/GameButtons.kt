package com.example.nuerd.game

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuerd.models.GameButtonsViewModel
import com.example.nuerd.models.GameViewModel
import com.example.nuerd.ui.theme.NuerdTheme
import com.example.nuerd.ui.theme.highlightColor
import com.example.nuerd.ui.theme.mainBackgroundColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.format.TextStyle


@Composable
fun BouncingButton(
    gameViewModel: GameViewModel,
    modifier: Modifier = Modifier,
    buttonViewModel: GameButtonsViewModel,
    number: Int,
    allButtons: List<GameButtonsViewModel>,
    result: Int,
    flashDurationMillis: Int = 50
) {

    val scope = rememberCoroutineScope()
    // Animations: Movement
    val x by buttonViewModel.xPosition
    val y by buttonViewModel.yPosition
    val animatedX by animateDpAsState(
        targetValue = x.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ), label = "Animated X"
    )

    val animatedY by animateDpAsState(
        targetValue = y.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ), label = "Animated Y"
    )
    val ballSize by buttonViewModel.ballSize.collectAsState()

    // Flashing:
    var currentColor by remember { mutableStateOf(highlightColor) }

    LaunchedEffect(allButtons) {
        buttonViewModel.startBouncing(allButtons)
    }

    Box(
        modifier = modifier
            .size(ballSize.dp)
            .offset { IntOffset(animatedX.roundToPx(), animatedY.roundToPx()) }
            .clickable {

                    if (number == result) {
                        gameViewModel.correctButton()
                    } else {
                        scope.launch {
                            currentColor = Color.Red
                            delay(flashDurationMillis.toLong())

                        currentColor = highlightColor}
                        
                        gameViewModel.looseLife()
                        gameViewModel.calculate()
                        // randomize()
                    }

                gameViewModel.countdown()
            }
            .border(width = 2.dp, color = currentColor, shape = CircleShape),

        contentAlignment = Alignment.Center
    ) {
        Text("$number", color = currentColor,
            style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun GameButtons(
    gameViewModel: GameViewModel,
    result: Int,
    randomNumbers: List<Int>
) {
    val buttonViewModels = remember { mutableStateListOf<GameButtonsViewModel>() }

    LaunchedEffect(randomNumbers) {
        buttonViewModels.clear()
        randomNumbers.forEach { _ ->
            val viewModel = GameButtonsViewModel()
            viewModel.spawn(buttonViewModels.toList())
            buttonViewModels.add(viewModel)
        }
    }

    Box(
        Modifier
            .size(300.dp)
            .background(mainBackgroundColor)
    ) {
        buttonViewModels.forEachIndexed { index, buttonViewModel ->
            BouncingButton(
                gameViewModel = gameViewModel,
                number = randomNumbers[index],
                buttonViewModel = buttonViewModel,
                allButtons = buttonViewModels.toList(),
                result = result,

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameButtonsPreview() {
    NuerdTheme  {
        GameButtons(result = 0, randomNumbers = listOf(1, 2, 3), gameViewModel = viewModel())
    }
}