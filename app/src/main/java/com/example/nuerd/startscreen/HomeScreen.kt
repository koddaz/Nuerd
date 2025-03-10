package com.example.nuerd.startscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.models.User
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.setValue
import com.example.nuerd.models.AuthState

@Composable
fun HomeScreen(authViewModel: AuthViewModel, authState: AuthState, userState: User?) {
    // val authState by authViewModel.authState.collectAsState()
    // val userState = remember { mutableStateOf<User?>(null) }


        if (authState is AuthState.Authenticated) {
            Start(user = userState?.username.toString())
        } else {
            Welcome()
        }

}
