package com.example.nuerd.game

import androidx.activity.result.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds




class GameViewModel : ViewModel() {

    private val _score = MutableStateFlow(0)
    private val _lives = MutableStateFlow(3)
    private val _timeRemaining = MutableStateFlow(10)
    private val _firstNumber = MutableStateFlow(0)
    private val _secondNumber = MutableStateFlow(0)
    private val _result = MutableStateFlow(0)
    private val _isPlaying = MutableStateFlow(false)
    private val _firstGame = MutableStateFlow(true)

    val score: StateFlow<Int> = _score.asStateFlow()
    val lives: StateFlow<Int> = _lives.asStateFlow()
    val timeRemaining: StateFlow<Int> = _timeRemaining.asStateFlow()
    val firstNumber: StateFlow<Int> = _firstNumber.asStateFlow()
    val secondNumber: StateFlow<Int> = _secondNumber.asStateFlow()
    val result: StateFlow<Int> = _result.asStateFlow()
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()
    val firstGame: StateFlow<Boolean> = _firstGame.asStateFlow()

    private var countdownJob: Job? = null


    fun calculate() {
        _firstNumber.value = (1..10).random()
        _secondNumber.value = (1..10).random()
        _result.value = _firstNumber.value * _secondNumber.value
    }


    private fun countdown() {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            while (_timeRemaining.value > 0 && _isPlaying.value) {
                delay(1.seconds)
                _timeRemaining.value--
            }
            if (_timeRemaining.value == 0 && _lives.value > 0) {
                _lives.value--
                _timeRemaining.value = 10
                countdown()
            } else {
                _isPlaying.value = false
            }
        }
    }

    fun correctButton() {
        _score.value++
        _timeRemaining.value = 10
        countdown()
    }
    fun startGame() {
        _firstGame.value = false
        _isPlaying.value = true
        _score.value = 0
        _timeRemaining.value = 10
        _lives.value = 3
        calculate()
        countdown()
    }

    fun resetGame() {
        _firstGame.value = true
        _score.value = 0
        _timeRemaining.value = 10
        _lives.value = 3
        _isPlaying.value = false
        calculate()
        countdownJob?.cancel()
    }

    fun playPause() {
        if (_isPlaying.value) {
            _isPlaying.value = false
            countdownJob?.cancel()
        } else {
            _isPlaying.value = true
            countdown()
        }
    }

}


/*



fun countdown() {
    if (!isPlaying) {
        isPlaying = true
        scope.launch {

            while (timeRemaining > 0 && isPlaying && lives > 0) {
                delay(1.seconds)
                timeRemaining--
            }
            isPlaying = false
            if (timeRemaining == 0 && lives > 0) {
                lives--
                timeRemaining = 10
                countdown()
            } else if (lives == 0) {
                scoreNumber = 0
                isPlaying = false
                timeRemaining = 0
                lives = 0
            }
        }
    }
}

fun calculate() {
    firstNumber = Random.nextInt(1, 10)
    secondNumber = Random.nextInt(1, 10)
    result = firstNumber * secondNumber
}

fun correctButton() {
    scoreNumber++
    timeRemaining = 10
    countdown()
}
*/
