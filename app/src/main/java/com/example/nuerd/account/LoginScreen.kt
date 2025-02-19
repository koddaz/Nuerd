package com.example.nuerd.account


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuerd.Routes
import com.example.nuerd.models.GameViewModel
import com.example.nuerd.ui.theme.NuerdTheme
import com.example.nuerd.ui.theme.mainBackgroundColor
import com.example.nuerd.ui.theme.secondaryBackgroundColor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthViewModel: ViewModel() {
private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState : LiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        if (auth.currentUser != null) {
            _authState.value = AuthState.Authenticated
        } else {
            _authState.value = AuthState.Unauthenticated
        }
    }

    fun signIn(email: String, password: String) {
        _authState.value = AuthState.Loading

        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password can't be empty empty")
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Sign in failed")
                }

            }
    }

    fun signUp(email: String, password: String) {
        _authState.value = AuthState.Loading

        if (email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Email or password can't be empty empty")
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    val errorMessage = task.exception?.localizedMessage ?: "Unknown error"
                    Log.e("AuthError", "Signup failed: $errorMessage", task.exception)
                    _authState.value = AuthState.Error(errorMessage)
                }
            }
    }

    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }
}

sealed class AuthState {
    data object Authenticated : AuthState()
    data object Unauthenticated : AuthState()
    data object Loading : AuthState()
    data class Error (val message : String) : AuthState()
}




@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onSignIn: () -> Unit,
    authViewModel: AuthViewModel = viewModel(),

) {
    var isSignUp by remember { mutableStateOf(true) }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> onSignIn()
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }




    Column(modifier.fillMaxSize().background(mainBackgroundColor)) {


        Column(modifier.wrapContentSize().background(secondaryBackgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = if (isSignUp) "Create Account" else "Sign In",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Username") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth().padding(16.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth().padding(16.dp))

            Row {
                Column(modifier.weight(1f)) {
                    Button(
                        onClick = {
                            if (isSignUp) {
                                authViewModel.signUp(email, password)
                                if (authState.value == AuthState.Authenticated) {
                                    onSignIn()
                                }

                            } else {
                                authViewModel.signIn(email, password)
                                if (authState.value == AuthState.Authenticated) {
                                    onSignIn()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),

                        ) {
                        Text(if (isSignUp) "Sign Up" else "Sign In")
                    }

                    Button(
                        onClick = { isSignUp = !isSignUp }
                    ) {
                        Text(
                            if (isSignUp) "Already have an account? Sign In"
                            else "Don't have an account? Sign Up",

                            )
                    }
                }

            }
        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    NuerdTheme {
        LoginScreen(
            onSignIn = {},


        )
    }
}

