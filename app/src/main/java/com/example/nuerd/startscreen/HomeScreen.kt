package com.example.nuerd.startscreen

import androidx.compose.runtime.Composable
import com.example.nuerd.models.User
import com.example.nuerd.models.AuthState

@Composable
fun HomeScreen(authState: AuthState? = null, userState: User?) {

        if (authState is AuthState.Authenticated) {
            Start(user = userState?.username.toString() ?: "Guest")
        } else {
            Welcome()
        }

}
