package com.example.nuerd.account


import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuerd.models.AuthState
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.ui.theme.NuerdTheme
import com.example.nuerd.ui.theme.highlightColor
import com.example.nuerd.ui.theme.mainBackgroundColor
import com.example.nuerd.ui.theme.secondaryBackgroundColor






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




    Column(modifier.fillMaxSize().background(mainBackgroundColor),
        verticalArrangement = Arrangement.Center) {

        Column(modifier.border(width = 2.dp, color = highlightColor).padding(16.dp)) {
            Text(
                text = if (isSignUp) "Create Account" else "Sign In",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 32.dp),
                color = highlightColor
            )
            Column(modifier.padding(bottom = 32.dp)) {
                TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Username") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth().padding(16.dp)
                )
                TextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
                )
            }
            Button(
                border = BorderStroke(width = 4.dp, color = highlightColor),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
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
                    border = BorderStroke(width = 4.dp, color = highlightColor),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    onClick = { isSignUp = !isSignUp }
                ) {
                    Text(
                        if (isSignUp) "Already have an account? Sign In"
                        else "Don't have an account? Sign Up",

                        )
                }

        }
        Column(modifier.wrapContentSize().background(secondaryBackgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally) {




            Row {
                Column(modifier.weight(1f)) {

                }

            }
        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    NuerdTheme {
        // Provide a fake ViewModel instance to avoid preview issues
        LoginScreen(
            onSignIn = {},
            authViewModel = object : AuthViewModel() {
                override val authState = MutableLiveData<AuthState>().apply {
                    value = AuthState.Unauthenticated
                }
            }


        )


    }
}

