package com.example.nuerd.models

import androidx.compose.runtime.mutableFloatStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

class GameButtonsViewModel : ViewModel() {
    var xPosition = mutableFloatStateOf(0f)
    var yPosition = mutableFloatStateOf(0f)

    private val maxSpeed = 0.6f
    private val initialSpeed = 0.4f
    private val _boxSize = MutableStateFlow(300f)
    private val _ballSize = MutableStateFlow(50f)

    private val boxSize: StateFlow<Float> = _boxSize.asStateFlow()
    val ballSize: StateFlow<Float> = _ballSize.asStateFlow()

    private var velocityX = initialSpeed * cos(Random.nextDouble(0.0, 2 * PI).toFloat())
    private var velocityY = initialSpeed * sin(Random.nextDouble(0.0, 2 * PI).toFloat())

    private val minSpawnDistance = _ballSize.value * 1.5f

    init {
        startBouncing(emptyList())
    }

    fun spawn(existingButtons: List<GameButtonsViewModel>) {
        val gridSize = 3
        val cellSize = (_boxSize.value - _ballSize.value) / gridSize

        val positions = mutableListOf<Pair<Float, Float>>()
        for (i in 0 until gridSize) {
            for (j in 0 until gridSize) {
                val x = i * cellSize + _ballSize.value / 2
                val y = j * cellSize + _ballSize.value / 2
                positions.add(Pair(x, y))
            }
        }

        positions.shuffle()

        for (pos in positions) {
            if (isValidPosition(pos.first, pos.second, existingButtons)) {
                xPosition.floatValue = pos.first
                yPosition.floatValue = pos.second
                val angle = Random.nextDouble(0.0, 2 * PI).toFloat()
                velocityX = initialSpeed * cos(angle)
                velocityY = initialSpeed * sin(angle)
                return
            }
        }

        for (pos in positions) {
            xPosition.floatValue = pos.first
            yPosition.floatValue = pos.second
            val angle = Random.nextDouble(0.0, 2 * PI).toFloat()
            velocityX = initialSpeed * cos(angle)
            velocityY = initialSpeed * sin(angle)
            break
        }
    }

    private fun isValidPosition(x: Float, y: Float, existingButtons: List<GameButtonsViewModel>): Boolean {
        if (x < _ballSize.value / 2 || x > _boxSize.value - _ballSize.value / 2 ||
            y < _ballSize.value / 2 || y > _boxSize.value - _ballSize.value / 2) {
            return false
        }

        return existingButtons.none { other ->
            val dx = x - other.xPosition.floatValue
            val dy = y - other.yPosition.floatValue
            val distance = sqrt(dx * dx + dy * dy)
            distance < minSpawnDistance
        }
    }


    fun startBouncing(allButtons: List<GameButtonsViewModel>) {
        viewModelScope.launch {
            while (true) {
                var newX = xPosition.floatValue + velocityX
                var newY = yPosition.floatValue + velocityY

                // Handle wall collisions
                if (newX <= 0 || newX >= boxSize.value - ballSize.value) {
                    velocityX = -velocityX
                    newX = newX.coerceIn(0f, boxSize.value - ballSize.value)
                }
                if (newY <= 0 || newY >= boxSize.value - ballSize.value) {
                    velocityY = -velocityY
                    newY = newY.coerceIn(0f, boxSize.value - ballSize.value)
                }

                // Handle collisions with other buttons
                allButtons.forEach { other ->
                    if (other != this@GameButtonsViewModel) {
                        val dx = newX - other.xPosition.floatValue
                        val dy = newY - other.yPosition.floatValue
                        val distance = sqrt(dx * dx + dy * dy)

                        if (distance < ballSize.value) {
                            // Calculate normal vector
                            val nx = dx / distance
                            val ny = dy / distance

                            // Calculate relative velocity
                            val relativeVx = velocityX - other.velocityX
                            val relativeVy = velocityY - other.velocityY

                            // Calculate velocity along the normal
                            val normalVelocity = relativeVx * nx + relativeVy * ny

                            // Only separate and resolve collision if objects are moving toward each other
                            if (normalVelocity < 0) {
                                // Calculate impulse
                                val impulse = -(2.0f * normalVelocity) / 2.0f  // Assuming equal masses

                                // Apply impulse to velocities
                                velocityX += impulse * nx
                                velocityY += impulse * ny
                                other.velocityX -= impulse * nx
                                other.velocityY -= impulse * ny

                                // Clamp speeds
                                val thisSpeed = sqrt(velocityX * velocityX + velocityY * velocityY)
                                val otherSpeed = sqrt(other.velocityX * other.velocityX + other.velocityY * other.velocityY)

                                if (thisSpeed > maxSpeed) {
                                    val scale = maxSpeed / thisSpeed
                                    velocityX *= scale
                                    velocityY *= scale
                                }

                                if (otherSpeed > maxSpeed) {
                                    val scale = maxSpeed / otherSpeed
                                    other.velocityX *= scale
                                    other.velocityY *= scale
                                }

                                // Separate the objects
                                val overlap = ballSize.value - distance
                                if (overlap > 0) {
                                    val separationX = (overlap * nx / 2)
                                    val separationY = (overlap * ny / 2)
                                    newX += separationX
                                    newY += separationY
                                    other.xPosition.value -= separationX
                                    other.yPosition.value -= separationY
                                }
                            }
                        }
                    }
                }

                xPosition.floatValue = newX.coerceIn(0f, boxSize.value - ballSize.value)
                yPosition.floatValue = newY.coerceIn(0f, boxSize.value - ballSize.value)

                delay(16L)
            }
        }
    }
}