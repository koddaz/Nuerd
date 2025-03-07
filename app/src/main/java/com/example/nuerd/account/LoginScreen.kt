package com.example.nuerd.account


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.nuerd.MenuButton
import com.example.nuerd.models.AuthState
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.models.User
import com.example.nuerd.models.getCountriesViewModel
import com.example.nuerd.ui.theme.NuerdTheme
import com.example.nuerd.ui.theme.borderColor
import com.example.nuerd.ui.theme.cursorColor
import com.example.nuerd.ui.theme.errorColor
import com.example.nuerd.ui.theme.highlightColor
import com.example.nuerd.ui.theme.textColor
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
    navOnLogin: () -> Unit,
    authViewModel: AuthViewModel?,
    authState: AuthState?,
    haveAcc: Boolean
) {
    if (haveAcc) {
        LogIn(authViewModel = authViewModel, authState = authState, navOnLogin = navOnLogin)
    } else if (!haveAcc) {
        SignUp(authViewModel = authViewModel, authState = authState, navOnLogin = navOnLogin)
    } else {
        Text("Something went wrong!!")
    }
}

@Composable
fun LogIn(authViewModel: AuthViewModel?, authState: AuthState?, navOnLogin: () -> Unit) {
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("")}

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
                if (authState is AuthState.Authenticated) {
                    navOnLogin()
                }

            },
            imageVector = Icons.Filled.PersonAddAlt,
            contentDescription = "Sign In",
            enabled = true
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        var text by remember { mutableStateOf(value) }
        TextField(
            value = text,
            onValueChange = {
                text = it
                onValueChange(it)
            },
            label = { Text(label, color = highlightColor) },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                disabledTextColor = Color.Gray,
                cursorColor = cursorColor,
                errorCursorColor = errorColor,
                focusedIndicatorColor = highlightColor,
                unfocusedIndicatorColor = borderColor,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = errorColor
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 3.dp, color = highlightColor, shape = RoundedCornerShape(16.dp))
        )
    }
}


@Composable
fun SignUp(authViewModel: AuthViewModel?, authState: AuthState?, getCountries: getCountriesViewModel = viewModel(), navOnLogin: () -> Unit) {
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val countries by getCountries.countries.observeAsState(emptyList())

    Column(modifier = Modifier.padding(8.dp)) {

        CustomTextField(
            onValueChange = { username = it },
            label = "Username",
            value = username,
            keyboardType = KeyboardType.Text
        )
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


            Column() {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .border(
                            width = 3.dp,
                            color = highlightColor,
                            shape = RoundedCornerShape(16.dp)
                        ).padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(modifier = Modifier.weight(1f)) {
                    Text(
                        "${country.ifEmpty { "Country" }}", color = highlightColor,
                        modifier = Modifier.padding(8.dp)
                    ) }
                    Row(modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.End) {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More options")
                        }
                    }
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    countries.forEach { countryItem ->
                        DropdownMenuItem(

                            text = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                                ) {

                                    Image(
                                        painter = rememberImagePainter(data = countryItem.flags.png),
                                        contentDescription = "Flag of ${countryItem.name.common}",
                                        modifier = Modifier.size(24.dp))


                                        Text(text = countryItem.name.common ?: "")

                                }

                            },
                            onClick = {
                                country = countryItem.name.common ?: ""
                                expanded = false
                            }
                        )
                    }
                }
            }
        MenuButton(
            onClick = {
                authViewModel?.signUp(email, password, username, country)
               if (authState is AuthState.Authenticated) {
                   navOnLogin()
               }

            },
            imageVector = Icons.Filled.PersonAddAlt,
            contentDescription = "Sign Up",
            enabled = true
        )
        }



    }



/*
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


    }
}
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    NuerdTheme {
        LoginScreen(
            onButtonClick = {},
            authViewModel = null,
            authState = null,
            haveAcc = false,
            navOnLogin = {}
        )
    }
}