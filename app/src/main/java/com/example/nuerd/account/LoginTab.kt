package com.example.nuerd.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.nuerd.account.models.CustomTextField
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.ui.theme.NuerdTheme

@Composable
fun LogIn(authViewModel: AuthViewModel?, navOnLogin: () -> Unit) {
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var error = ""

    Column(modifier = Modifier.fillMaxWidth().background(colorScheme.secondary).padding(8.dp)) {
        CustomTextField(
            onValueChange = { email = it },
            label = "E-mail",
            value = email,
            keyboardType = KeyboardType.Email
        )
        CustomTextField(
            onValueChange = { password = it },
            label = "Password",
            value = password,
            keyboardType = KeyboardType.Password
        )

        EditButton(
            onClick = {
                authViewModel?.signIn(email, password)
                navOnLogin()
            },
            title = "Sign In",
            icon = Icons.Filled.PersonAddAlt
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LogInPreview() {
    NuerdTheme {
        LogIn(authViewModel = null, navOnLogin = {})
    }
}