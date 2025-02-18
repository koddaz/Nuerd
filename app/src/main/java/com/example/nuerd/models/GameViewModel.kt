package com.example.nuerd.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun calculate() {
        _firstNumber.value = Random.nextInt(1, 10)
        _secondNumber.value = Random.nextInt(1, 10)
        _result.value = _firstNumber.value * _secondNumber.value
    }

    // Random numbers for the buttons
    private val _buttonCount = MutableStateFlow(9)
    private val _randomNumbers = MutableStateFlow<List<Int>>(emptyList())

    val randomNumbers: StateFlow<List<Int>> = _randomNumbers.asStateFlow()

    fun randomize() {
        val newRandomNumbers = mutableListOf<Int>()

        repeat(_buttonCount.value - 1) {
            newRandomNumbers.add(Random.nextInt(1, 100))
        }
        newRandomNumbers.add(_result.value)
        newRandomNumbers.shuffle()

        _randomNumbers.value = newRandomNumbers

    }

    private val _timeRemaining = MutableStateFlow(10)
    private val _isPlaying = MutableStateFlow(false)
    private val _lives = MutableStateFlow(3)
    private val _scoreNumber = MutableStateFlow(0)

    val timeRemaining: StateFlow<Int> = _timeRemaining.asStateFlow()
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()
    val lives: StateFlow<Int> = _lives.asStateFlow()
    val scoreNumber: StateFlow<Int> = _scoreNumber.asStateFlow()

    fun correctButton() {
        _scoreNumber.value++
        _timeRemaining.value = 10
        countdown()
    }

    fun looseLife() {
        _lives.value--
        _timeRemaining.value = 10
        countdown()
    }

    fun resetGame() {
        _scoreNumber.value = 0
        _timeRemaining.value = 10
        _lives.value = 3
        _isPlaying.value = false
    }

    fun countdown() {
        if (!_isPlaying.value) {
            _isPlaying.value = true
            viewModelScope.launch {
                while (timeRemaining.value > 0 && isPlaying.value && lives.value > 0) {
                    delay(1000L)
                    _timeRemaining.value--
                }
                _isPlaying.value = false
                if (timeRemaining.value == 0 && lives.value > 0) {
                    _lives.value--
                    _timeRemaining.value = 10
                    countdown()
                } else if (lives.value == 0) {
                    _scoreNumber.value = 0
                    _isPlaying.value = false
                    _timeRemaining.value = 0
                    _lives.value = 0
                }
            }
        } else {
            _isPlaying.value = false
        }

    }

}