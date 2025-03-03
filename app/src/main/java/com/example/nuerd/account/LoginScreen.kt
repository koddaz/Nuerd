package com.example.nuerd.account


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.icons.filled.RotateLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuerd.MenuButton
import com.example.nuerd.models.AuthState
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.ui.theme.NuerdTheme
import com.example.nuerd.ui.theme.highlightColor
import com.example.nuerd.ui.theme.mainBackgroundColor


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
    authViewModel: AuthViewModel? = viewModel(),
    authState: AuthState?
) {
    var isSignUp by remember { mutableStateOf(true) }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }

    // val authState by authViewModel?.authState?.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> onButtonClick()
            // is AuthState.Error -> Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    Column(
        modifier.fillMaxSize().background(mainBackgroundColor),
        verticalArrangement = Arrangement.Center
    ) {
        Column(modifier.border(width = 2.dp, color = highlightColor).padding(16.dp)) {
            Text(
                text = if (isSignUp) "Create Account" else "Sign In",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 32.dp),
                color = highlightColor
            )
            Column(modifier.padding(bottom = 32.dp)) {
                if (isSignUp) {
                    TextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    )
                }
                TextField(
                    value = email,
                    onValueChange = { newMail -> email = newMail.replace(" ", "") },
                    label = { Text("E-mail") },
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
                if (isSignUp) {
                    TextField(
                        value = country,
                        onValueChange = { country = it },
                        label = { Text("Country") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    )
                }
            }
            MenuButton(
                onClick = {
                    if (isSignUp) {
                        authViewModel?.signUp(email, password, username, country)
                    } else {
                        authViewModel?.signIn(email, password)
                    }
                },
                imageVector = Icons.Filled.PersonAddAlt,
                contentDescription = if (isSignUp) "Sign Up" else "Sign In"
            )
            MenuButton(
                onClick = { isSignUp = !isSignUp },
                imageVector = Icons.Filled.RotateLeft,
                contentDescription = "Switch to ${if (isSignUp) "Sign In" else "Sign Up"}"
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    NuerdTheme {
        LoginScreen(
            onButtonClick = {},
            authViewModel = null,
            authState = null
        )
    }
}