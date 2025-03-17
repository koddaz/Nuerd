package com.example.nuerd.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ThemeViewModel(private val settingsDao: SettingsDao) : ViewModel() {
    // Start with explicit Green default
    private val _theme = MutableStateFlow("Green")
    val theme: StateFlow<String> = _theme

    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized

    init {
        viewModelScope.launch {
            try {
                // Load from database but keep Green as fallback
                val savedTheme = settingsDao.getTheme()
                if (savedTheme != null) {
                    _theme.value = savedTheme.theme
                } else {
                    // Insert default theme if none exists
                    settingsDao.insertTheme(Theme(id = 0, theme = "Green"))
                }
            } catch (e: Exception) {
                // Already using Green as default, just log the error
                Log.e("ThemeViewModel", "Error loading theme", e)
            } finally {
                _isInitialized.value = true
            }
        }
    }

    fun setTheme(newTheme: String) {
        viewModelScope.launch {
            try {
                _theme.value = newTheme
                settingsDao.updateTheme(newTheme)
            } catch (error: Exception) {
                Log.e("ThemeViewModel", "Error updating theme", error)
            }
        }
    }
}