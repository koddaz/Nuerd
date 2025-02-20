package com.example.nuerd.game

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.xr.compose.testing.toDp
import androidx.xr.runtime.math.toRadians
import com.example.nuerd.models.GameViewModel
import com.example.nuerd.ui.theme.highlightColor
import com.example.nuerd.ui.theme.mainBackgroundColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.random.Random
import kotlin.math.cos
import kotlin.math.sin


class GameButtonsViewModel : ViewModel() {
    var xPosition = mutableStateOf(Random.nextFloat() * 250f) // Start near the center
    var yPosition = mutableStateOf(Random.nextFloat() * 250f)

    private val _boxSize = MutableStateFlow(300f)  // Container size
    private val _ballSize = MutableStateFlow(50f)   // Ball size

    val boxSize: StateFlow<Float> = _boxSize.asStateFlow()
    val ballSize: StateFlow<Float> = _ballSize.asStateFlow()

    private var speed = 5f  // Speed of movement
    private var angle = Random.nextDouble(0.0, 360.0).toFloat()  // Random angle

    init {
        startBouncing()
    }

    private fun startBouncing() {
        viewModelScope.launch {
            while (true) {
                val radians = (angle * (PI / 180)).toFloat()
                val newX = xPosition.value + (speed * cos(radians))
                val newY = yPosition.value + (speed * sin(radians))

                // Check for collisions with walls
                if (newX <= 0 || newX >= boxSize.value - ballSize.value) {
                    angle = 180 - angle  // Reverse X direction
                }
                if (newY <= 0 || newY >= boxSize.value - ballSize.value) {
                    angle = -angle  // Reverse Y direction
                }


                // Update position with boundary constraints
                xPosition.value = newX.coerceIn(0f, boxSize.value - ballSize.value)
                yPosition.value = newY.coerceIn(0f, boxSize.value - ballSize.value)

                delay(16L) // 60 FPS movement
            }
        }
    }
}

@Composable
fun BouncingButton(
    modifier: Modifier = Modifier,
    buttonViewModel: GameButtonsViewModel = viewModel(),
    number: Int
) {

    val x by buttonViewModel.xPosition
    val y by buttonViewModel.yPosition

    val animatedX by animateDpAsState(targetValue = x.dp, animationSpec = tween(16))
    val animatedY by animateDpAsState(targetValue = y.dp, animationSpec = tween(16))

    val ballSize by buttonViewModel.ballSize.collectAsState()

    Box(modifier
        .size(ballSize.dp)
        .offset(animatedX, animatedY)
        .border(width = 2.dp, color = highlightColor, shape = CircleShape)
        ,
        contentAlignment = Alignment.Center
    ) {
        Text("$number", color = highlightColor)
    }
}

@Composable
fun GameButtons(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel()
) {
    val randomNumbers = gameViewModel.randomNumbers.collectAsState().value
    val buttonViewModel = remember(randomNumbers) {
        randomNumbers.map { GameButtonsViewModel() }
    }

            Box(modifier.size(300.dp).background(mainBackgroundColor)) {

                buttonViewModel.forEachIndexed() { index, buttonViewModel ->
                    BouncingButton(
                        number = randomNumbers[index],
                        buttonViewModel = buttonViewModel
                    )
                }


            }




}

@Preview(showBackground = true)
@Composable
fun GameButtonsPreview() {
    GameButtons()
}