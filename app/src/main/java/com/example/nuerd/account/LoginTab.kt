package com.example.nuerd.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.nuerd.menu.MenuButton
import com.example.nuerd.account.models.CustomTextField
import com.example.nuerd.models.AuthViewModel

@Composable
fun LogIn(authViewModel: AuthViewModel?, navOnLogin: () -> Unit) {
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var error = ""

    Column(modifier = Modifier.padding(bottom = 32.dp)) {
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

        MenuButton(
            onClick = {
                authViewModel?.signIn(email, password)
                navOnLogin()

            },
            imageVector = Icons.Filled.PersonAddAlt,
            contentDescription = "Sign In",
            enabled = true
        )
        Text(error)

    }
}