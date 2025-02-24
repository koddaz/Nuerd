package com.example.nuerd.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel: ViewModel() {

    // Random numbers for the game and the result
    private val _firstNumber = MutableStateFlow(0)
    private val _secondNumber = MutableStateFlow(0)
    private val _result = MutableStateFlow(0)

    val firstNumber: StateFlow<Int> = _firstNumber.asStateFlow()
    val secondNumber: StateFlow<Int> = _secondNumber.asStateFlow()
    val result: StateFlow<Int> = _result.asStateFlow()


    private val _randomNumbers = MutableStateFlow<List<Int>>(emptyList())
    private val _listSize = MutableStateFlow(3)
    val randomNumbers: StateFlow<List<Int>> = _randomNumbers.asStateFlow()

    fun removeFromList() {
        val currentList = _randomNumbers.value.toMutableList()
        currentList.remove(_result.value)
        _randomNumbers.value = currentList
    }

    fun calculate() {
        _firstNumber.value = Random.nextInt(1, 10)
        _secondNumber.value = Random.nextInt(1, 10)
        _result.value = _firstNumber.value * _secondNumber.value

        val numberList = mutableSetOf<Int>()
        numberList.add(_result.value)

        while (numberList.size < _listSize.value) {
            val randomNumber = Random.nextInt(1, 99)
            if (randomNumber != _result.value) {
                numberList.add(randomNumber)
            }

            _randomNumbers.value = numberList.toList().shuffled()

        }

        /*
        val numbersList = if (_result.value <= 4) {
            listOf(
                _result.value + 1,
                _result.value + 2,
                _result.value + 3,
                _result.value + 4,
                _result.value,
                _result.value + 5,
                _result.value + 6,
                _result.value + 7,
                _result.value + 8
            )
        } else {
            listOf(
                _result.value - 4,
                _result.value - 3,
                _result.value - 2,
                _result.value - 1,
                _result.value,
                _result.value + 1,
                _result.value + 2,
                _result.value + 3,
                _result.value + 4
            )
        }

         */
        _randomNumbers.value = numberList.shuffled()
    }

    // Random numbers for the buttons


    init {
        calculate()
    }

    private val _timeRemaining = MutableStateFlow(10)
    private val _isPlaying = MutableStateFlow(false)
    private val _lives = MutableStateFlow(3)
    private val _scoreNumber = MutableStateFlow(0)
    private val _firstGame = MutableStateFlow(true)

    val timeRemaining: StateFlow<Int> = _timeRemaining.asStateFlow()
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()
    val lives: StateFlow<Int> = _lives.asStateFlow()
    val scoreNumber: StateFlow<Int> = _scoreNumber.asStateFlow()
    val firstGame: StateFlow<Boolean> = _firstGame.asStateFlow()

    private var countdownJob: Job? = null
    private var scoreIncreased = 0

    fun correctButton() {
        _scoreNumber.value++
        scoreIncreased++
        if (scoreIncreased >= 5) {
            _listSize.value++
            scoreIncreased = 0
        }
        _timeRemaining.value = 10
        calculate()  // Calculate new numbers first
        countdown()
    }

    fun looseLife() {
        if (_lives.value == 0) {
            return
        } else {
        _lives.value--
        _timeRemaining.value = 10 }
    }

    fun resetGame() {
        _scoreNumber.value = 0
        _timeRemaining.value = 10
        _lives.value = 3
        _isPlaying.value = false
        scoreIncreased = 0
    }

    fun startGame() {
        _firstGame.value = false
        countdown()
    }

    fun countdown() {
        // Cancel any existing countdown
        countdownJob?.cancel()

        if (!_isPlaying.value) {
            _isPlaying.value = true
        }

        countdownJob = viewModelScope.launch {
            while (timeRemaining.value > 0 && lives.value > 0) {
                delay(1000L)
                if (_timeRemaining.value > 0) {
                    _timeRemaining.value-- // Decrement time
                }
            }
            // When countdown reaches zero
            if (_timeRemaining.value == 0 && _lives.value > 0) {
                looseLife()
                _timeRemaining.value = 10  // Reset timer
                countdown()  // Start new countdown
            } else if (_lives.value == 0) {
                _isPlaying.value = false
            }
        }
    }

}