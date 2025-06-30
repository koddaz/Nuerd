package com.example.nuerd.game


import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.nuerd.models.GameButtonsViewModel
import com.example.nuerd.models.GameViewModel
import com.example.nuerd.ui.theme.NuerdTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.collections.get
import kotlin.collections.removeLast
import kotlin.compareTo

private const val TAG = "GameButtonsViewModel"

@Composable
fun BouncingButton(
    gameViewModel: GameViewModel?,
    modifier: Modifier = Modifier,
    buttonViewModel: GameButtonsViewModel,
    number: Int,
    allButtons: List<GameButtonsViewModel>,
    result: Int,
    flashDurationMillis: Int = 50
) {
    val lastClickTime = remember { mutableStateOf(0L) }
    val debounceTime = 200L

    val scope = rememberCoroutineScope()
    val x by buttonViewModel.xPosition
    val y by buttonViewModel.yPosition
    val animatedX by animateDpAsState(
        targetValue = x.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )
    val animatedY by animateDpAsState(
        targetValue = y.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )
    val ballSize by buttonViewModel.ballSize.collectAsState()

    val primaryColor = colorScheme.surface
    val errorColor = colorScheme.error
    var currentColor by remember { mutableStateOf(primaryColor) }

    val isPaused by gameViewModel?.isPaused?.collectAsState() ?: remember { mutableStateOf(false) }



    LaunchedEffect(allButtons) {
        buttonViewModel.startBouncing(allButtons)
    }

    Box(
        modifier = modifier
            .size(ballSize.dp)
            .offset { IntOffset(animatedX.roundToPx(), animatedY.roundToPx()) }
            .clickable {
                if (isPaused) return@clickable
                val currentTime = System.currentTimeMillis()

                if (currentTime - lastClickTime.value > debounceTime) {
                    lastClickTime.value = currentTime
                    Log.d(TAG, "Button click attempt - number: $number, result: $result, debounce: ${currentTime - lastClickTime.value}")

                    if (number == result) {
                        Log.d(TAG, "Correct button clicked - score will increase")
                        gameViewModel?.correctButton()
                    } else {
                        Log.d(TAG, "Wrong button clicked - will lose life")
                        scope.launch {
                            currentColor = errorColor
                            delay(flashDurationMillis.toLong())
                            currentColor = primaryColor
                        }
                        gameViewModel?.looseLife()
                        gameViewModel?.calculate()
                    }
                    gameViewModel?.countdown()
                }
            }
            .border(width = 2.dp, color = currentColor, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "$number",
            color = currentColor,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun GameButtons(
    gameViewModel: GameViewModel?,
    result: Int,
    randomNumbers: List<Int>,
    modifier: Modifier = Modifier,
    boxWidth: Float = 300f,  // Fixed width
    boxHeight: Float = 200f, // Fixed height
    ballSize: Float = 60f    // Fixed ball size
) {
    val buttonViewModels = remember { mutableStateListOf<GameButtonsViewModel>() }
    val maxSafeButtons = 6

    Log.d(TAG, "GameButtons composed with fixed dimensions: ${boxWidth}x${boxHeight}")

    Box(
        modifier
            .fillMaxWidth()
            .background(colorScheme.primary)
    ) {
        // Use LaunchedEffect with boxWidth/boxHeight as keys to update when they change
        LaunchedEffect(randomNumbers.size, boxWidth, boxHeight, ballSize) {
            try {
                Log.d(TAG, "Updating buttons for container: ${boxWidth}x${boxHeight}, ball size: $ballSize")

                val targetSize = randomNumbers.size.coerceAtMost(maxSafeButtons)

                // Update all existing buttons with the fixed dimensions
                buttonViewModels.forEach {
                    it.updateContainerSize(boxWidth, boxHeight)
                }

                // Add new buttons if needed
                while (buttonViewModels.size < targetSize) {
                    Log.d(TAG, "Adding new button - current count: ${buttonViewModels.size}")
                    val viewModel = GameButtonsViewModel()
                    viewModel.updateContainerSize(boxWidth, boxHeight)
                    viewModel.spawn(buttonViewModels.toList())
                    buttonViewModels.add(viewModel)
                }

                // Remove excess buttons
                while (buttonViewModels.size > targetSize) {
                    buttonViewModels.removeAt(buttonViewModels.lastIndex)
                }

                Log.d(TAG, "Button adjustment complete - final count: ${buttonViewModels.size}")
            } catch (e: Exception) {
                Log.e(TAG, "Error adjusting buttons: ${e.message}")
            }
        }

        randomNumbers.take(buttonViewModels.size).forEachIndexed { index, number ->
            if (index < buttonViewModels.size) {
                BouncingButton(
                    gameViewModel = gameViewModel,
                    number = number,
                    buttonViewModel = buttonViewModels[index],
                    allButtons = buttonViewModels.toList(),
                    result = result
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameButtonsPreview() {
    NuerdTheme(theme = "Green") {
        GameButtons(
            result = 0,
            randomNumbers = listOf(1, 2, 3),
            gameViewModel = null
        )
    }
}