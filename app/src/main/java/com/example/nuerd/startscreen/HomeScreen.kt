package com.example.nuerd.startscreen

import androidx.compose.runtime.Composable
import com.example.nuerd.models.User
import com.example.nuerd.models.AuthState
import com.example.nuerd.models.HighScoreEntry

@Composable
fun HomeScreen(authState: AuthState? = null, userState: User?, allHighScores: List<HighScoreEntry>, userHighScore: Int ) {

        if (authState is AuthState.Authenticated) {
            Start(user = userState?.username.toString() ?: "Guest", allHighScores = allHighScores, userHighScore = userHighScore)
        } else {
            Welcome(allHighScores = allHighScores)
        }

}
