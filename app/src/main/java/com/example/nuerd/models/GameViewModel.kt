package com.example.nuerd.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel(application: Application): ViewModel() {
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "app_database"
    ).build()
    private val difficultyDao = db.settingsDao()

    // Settings for the game:
    private val _difficulty = MutableStateFlow(1)
    val difficulty: StateFlow<Int> = _difficulty.asStateFlow()

    init {
        viewModelScope.launch {
            val savedDifficulty = difficultyDao.getDifficulty()
            if (savedDifficulty != null) {
                _difficulty.value = savedDifficulty.value
            }
        }
    }

    private val _difficultyTime = MutableStateFlow(
        if (_difficulty.value == 1) {
            10
        } else if (_difficulty.value == 2) {
            5
        } else {
            3
        }
    )
    val difficultyTime: StateFlow<Int> = _difficultyTime.asStateFlow()

    fun setDifficulty(newDifficulty: Int) {
        _difficulty.value = newDifficulty
        _difficultyTime.value = when (newDifficulty) {
            1 -> 10
            2 -> 5
            else -> 3
        }

        viewModelScope.launch {
            difficultyDao.insert(Difficulty(0, newDifficulty))
        }
        if (_difficulty.value == 1) {
            Log.d("GameViewModel", "Difficulty set to Easy")
        } else if (_difficulty.value == 2) {
            Log.d("GameViewModel", "Difficulty set to Medium")
        } else {
            Log.d("GameViewModel", "Difficulty set to Hard")
        }
    }
    // val authViewModel = AuthViewModel()

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


        _randomNumbers.value = numberList.shuffled()
    }

    // Random numbers for the buttons


    init {
        calculate()
    }

    private val _timeRemaining = MutableStateFlow(difficultyTime.value)
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
        _timeRemaining.value = difficultyTime.value
        calculate()  // Calculate new numbers first
        countdown()
    }

    fun looseLife() {
        if (_lives.value == 0) {
            return
        } else {
        _lives.value--
        _timeRemaining.value = difficultyTime.value }
    }

    fun resetGame() {
        _scoreNumber.value = 0
        _timeRemaining.value = difficultyTime.value
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
                _timeRemaining.value = difficultyTime.value  // Reset timer
                countdown()  // Start new countdown
            } else if (_lives.value == 0) {
                // authViewModel.highScore(_scoreNumber.value)
                _isPlaying.value = false
            }
        }
    }

}