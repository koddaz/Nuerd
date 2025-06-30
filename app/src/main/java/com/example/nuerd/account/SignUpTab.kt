package com.example.nuerd.account


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.nuerd.account.models.CustomTextField
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.ui.theme.NuerdTheme
import androidx.compose.foundation.layout.imePadding
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.nuerd.models.CustomColumn

@Composable
fun SignUp(authViewModel: AuthViewModel?, navOnLogin: () -> Unit) {
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }

    val usernameFocus = remember { FocusRequester() }
    val emailFocus = remember { FocusRequester() }
    val passwordFocus = remember { FocusRequester() }

    val scrollState = rememberScrollState()

    CustomColumn(
        title = "Sign Up"
    ) {
        CustomTextField(
            modifier = Modifier.focusRequester(usernameFocus),
            onValueChange = { username = it },
            label = "Username",
            value = username,
            keyboardType = KeyboardType.Text,
            onImeAction = { emailFocus.requestFocus() },
            imeAction = ImeAction.Next
        )

        CustomTextField(
            onValueChange = { email = it },
            label = "E-mail",
            value = email,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            onImeAction = { passwordFocus.requestFocus() },
            modifier = Modifier.focusRequester(emailFocus)
        )

        CustomTextField(
            onValueChange = { password = it },
            label = "Password",
            value = password,
            keyboardType = KeyboardType.Password,
            visual = PasswordVisualTransformation(),
            imeAction = ImeAction.Done,
            onImeAction = {
                authViewModel?.signUp(email, password, username)
                navOnLogin()
            },
            modifier = Modifier.focusRequester(passwordFocus)
        )


        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(2f))
            EditButton(
                useCompactLayout = true,
                modifier = Modifier.weight(1f),
                onClick = {
                    authViewModel?.signUp(email, password, username)
                    navOnLogin()
                },
                title = "Sign Up",
                icon = Icons.Filled.PersonAddAlt
            )
        }
    }
}

@Preview
@Composable
fun SignUpPreview() {


    NuerdTheme {
        SignUp(
            authViewModel = null,
            navOnLogin = { },


        )
    }
}