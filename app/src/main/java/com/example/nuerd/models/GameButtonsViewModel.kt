package com.example.nuerd.models

import android.util.Log
import androidx.compose.runtime.mutableFloatStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

private const val TAG = "GameButtonsViewModel"


class GameButtonsViewModel : ViewModel() {
    // Position state
    var xPosition = mutableFloatStateOf(0f)
    var yPosition = mutableFloatStateOf(0f)

    // Physics parameters
    private val maxSpeed = 0.6f
    private val initialSpeed = 0.4f
    private var velocityX = 0f
    private var velocityY = 0f

    // Container dimensions (default values, will be updated)
    private val _boxWidth = MutableStateFlow(300f)
    private val _boxHeight = MutableStateFlow(300f)
    private val _ballSize = MutableStateFlow(75f)

    // Public accessors
    val boxWidth: StateFlow<Float> = _boxWidth.asStateFlow()
    val boxHeight: StateFlow<Float> = _boxHeight.asStateFlow()
    val ballSize: StateFlow<Float> = _ballSize.asStateFlow()

    // Pause state
    private val _isPaused = MutableStateFlow(false)
    val isPaused: StateFlow<Boolean> = _isPaused.asStateFlow()

    // Animation job
    private var bouncingJob: Job? = null

    init {
        startBouncing(emptyList())
    }

    // Update container dimensions when parent layout changes
    fun updateContainerSize(width: Float, height: Float) {
        _boxWidth.value = width
        _boxHeight.value = height
        Log.d(TAG, "Container size updated: ${_boxWidth.value}x${_boxHeight.value}")
    }

    fun setPaused(paused: Boolean) {
        _isPaused.value = paused
    }

    fun spawn(existingButtons: List<GameButtonsViewModel>) {
        val minSpawnDistance = _ballSize.value * 1.5f
        val maxAttempts = 50
        var attempts = 0

        // Try random positions first
        while (attempts < maxAttempts) {
            val randomX = Random.nextFloat() * (_boxWidth.value - _ballSize.value)
            val randomY = Random.nextFloat() * (_boxHeight.value - _ballSize.value)

            if (isValidPosition(randomX, randomY, existingButtons, minSpawnDistance)) {
                xPosition.floatValue = randomX
                yPosition.floatValue = randomY
                setRandomVelocity()
                return
            }
            attempts++
        }

        // Try grid-based placement if random fails
        val aspectRatio = _boxWidth.value / _boxHeight.value
        val gridRows = ceil(sqrt(existingButtons.size.toFloat() + 1) / aspectRatio).toInt() + 1
        val gridCols = ceil(sqrt(existingButtons.size.toFloat() + 1) * aspectRatio).toInt() + 1

        val cellWidth = (_boxWidth.value - _ballSize.value) / gridCols
        val cellHeight = (_boxHeight.value - _ballSize.value) / gridRows

        val positions = mutableListOf<Pair<Float, Float>>()
        for (i in 0 until gridCols) {
            for (j in 0 until gridRows) {
                val x = i * cellWidth + _ballSize.value / 2
                val y = j * cellHeight + _ballSize.value / 2
                positions.add(Pair(x, y))
            }
        }

        positions.shuffle()

        // Try each position in the grid
        for (pos in positions) {
            if (isValidPosition(pos.first, pos.second, existingButtons, minSpawnDistance)) {
                xPosition.floatValue = pos.first
                yPosition.floatValue = pos.second
                setRandomVelocity()
                return
            }
        }

        // Last resort - position at a corner
        xPosition.floatValue = _ballSize.value / 2
        yPosition.floatValue = _ballSize.value / 2
        setRandomVelocity()
    }

    private fun setRandomVelocity() {
        val angle = Random.nextDouble(0.0, 2 * PI).toFloat()
        velocityX = initialSpeed * cos(angle)
        velocityY = initialSpeed * sin(angle)
    }

    private fun isValidPosition(
        x: Float,
        y: Float,
        existingButtons: List<GameButtonsViewModel>,
        minDistance: Float
    ): Boolean {
        // Check bounds
        if (x < _ballSize.value / 2 || x > _boxWidth.value - _ballSize.value / 2 ||
            y < _ballSize.value / 2 || y > _boxHeight.value - _ballSize.value / 2) {
            return false
        }

        // Check collisions with other buttons
        return existingButtons.none { other ->
            val dx = x - other.xPosition.floatValue
            val dy = y - other.yPosition.floatValue
            val distance = sqrt(dx * dx + dy * dy)
            distance < minDistance
        }
    }

    fun startBouncing(allButtons: List<GameButtonsViewModel>) {
        bouncingJob?.cancel()

        bouncingJob = viewModelScope.launch {
            while (true) {
                if (!_isPaused.value) {
                    updatePosition(allButtons)
                }
                delay(16L) // ~60fps
            }
        }
    }

    private fun updatePosition(allButtons: List<GameButtonsViewModel>) {
        // Apply damping for stability
        velocityX *= 0.98f
        velocityY *= 0.98f

        // Calculate new position
        var newX = xPosition.floatValue + velocityX
        var newY = yPosition.floatValue + velocityY

        // Wall collisions
        if (newX <= 0 || newX >= _boxWidth.value - _ballSize.value) {
            velocityX = -velocityX * 0.9f
            newX = newX.coerceIn(0f, _boxWidth.value - _ballSize.value)
        }
        if (newY <= 0 || newY >= _boxHeight.value - _ballSize.value) {
            velocityY = -velocityY * 0.9f
            newY = newY.coerceIn(0f, _boxHeight.value - _ballSize.value)
        }

        // Optimized collision detection - only check closest buttons
        try {
            val maxCollisionsPerFrame = 2
            var collisionsProcessed = 0

            val nearbyButtons = allButtons
                .filter { it != this }
                .sortedBy {
                    val dx = newX - it.xPosition.floatValue
                    val dy = newY - it.yPosition.floatValue
                    dx * dx + dy * dy
                }
                .take(2)

            for (other in nearbyButtons) {
                if (collisionsProcessed >= maxCollisionsPerFrame) break

                val dx = newX - other.xPosition.floatValue
                val dy = newY - other.yPosition.floatValue
                val distance = sqrt(dx * dx + dy * dy)

                if (distance < _ballSize.value) {
                    collisionsProcessed++

                    // Simple collision response
                    val nx = dx / distance
                    val ny = dy / distance

                    velocityX = velocityX * 0.5f + nx * maxSpeed * 0.5f
                    velocityY = velocityY * 0.5f + ny * maxSpeed * 0.5f

                    // Ensure separation
                    newX = other.xPosition.floatValue + nx * _ballSize.value
                    newY = other.yPosition.floatValue + ny * _ballSize.value
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in collision detection: ${e.message}")
        }

        // Apply final position with bounds check
        xPosition.floatValue = newX.coerceIn(0f, _boxWidth.value - _ballSize.value)
        yPosition.floatValue = newY.coerceIn(0f, _boxHeight.value - _ballSize.value)
    }

    override fun onCleared() {
        super.onCleared()
        bouncingJob?.cancel()
    }
}