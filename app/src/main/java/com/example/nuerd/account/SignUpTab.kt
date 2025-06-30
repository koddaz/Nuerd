package com.example.nuerd.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.example.nuerd.account.models.CustomTextField
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.models.Country
import com.example.nuerd.models.Flags
import com.example.nuerd.models.Name
import com.example.nuerd.models.getCountriesViewModel
import com.example.nuerd.ui.theme.NuerdTheme
import androidx.compose.foundation.layout.imePadding
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.PasswordVisualTransformation
import coil.compose.rememberAsyncImagePainter
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

    CustomColumn(title = "Sign Up") {
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