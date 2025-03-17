package com.example.nuerd.menu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuerd.account.EditButton
import com.example.nuerd.account.LogIn
import com.example.nuerd.account.SignUp
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.models.Country
import com.example.nuerd.models.getCountriesViewModel

import com.example.nuerd.ui.theme.NuerdTheme


@Composable
fun Menu(
    getCountries: getCountriesViewModel,
    countries: List<Country>?,
    authViewModel: AuthViewModel?,
    modifier: Modifier = Modifier,
    onPracticeClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAccountClick: () -> Unit,
    onSignClick: () -> Unit,
    onPlayClick: () -> Unit,
    authenticated: Boolean,
    toggleMenu: () -> Unit
) {

    var showSignIn by remember { mutableStateOf(true) }

    Column(
        modifier.background(colorScheme.primary).wrapContentSize()
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Column(
                modifier
                    .fillMaxWidth()
                    .background(colorScheme.secondary, RoundedCornerShape(8.dp))
                    .border(2.dp, colorScheme.surface, RoundedCornerShape(8.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier.fillMaxWidth()
                        .background(colorScheme.background, RoundedCornerShape(8.dp))
                        .border(2.dp, colorScheme.surface, RoundedCornerShape(8.dp)).padding(16.dp)
                ) {
                    Text(
                        text = "Menu",
                        style = MaterialTheme.typography.displaySmall,
                        color = colorScheme.onPrimary,
                    )
                }
                if (!authenticated) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        modifier
                            .fillMaxWidth()
                            .background(colorScheme.background, RoundedCornerShape(8.dp))
                            .border(2.dp, colorScheme.surface, RoundedCornerShape(8.dp))
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "To be able to use the full content of the game you will have to sign up or log in.",
                            color = colorScheme.onPrimary,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier
                            .fillMaxWidth()
                            .background(colorScheme.background, RoundedCornerShape(8.dp))
                            .border(2.dp, colorScheme.surface, RoundedCornerShape(8.dp))
                            .padding(16.dp)
                    ) {
                        Text(
                            if (showSignIn) "Don't have an account?" else "Already have an account?",
                            color = colorScheme.onPrimary,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            if (showSignIn) "Sign up here" else "Sign in here",
                            color = colorScheme.onPrimary,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.clickable(onClick = {
                                showSignIn = !showSignIn
                            })
                        )
                    }
                    Column() {
                        if (showSignIn) {
                            LogIn(
                                authViewModel = authViewModel,
                                navOnLogin = {
                                    onSignClick
                                    toggleMenu()
                                }
                            )
                        } else {
                            SignUp(
                                countriesList = countries,
                                authViewModel = authViewModel,
                                navOnLogin = {
                                    onSignClick
                                    toggleMenu()
                                },
                                getCountries = getCountries
                            )
                        }




                    }


                }
                Spacer(modifier = Modifier.height(16.dp))
                EditButton(
                    onClick = onPlayClick,
                    title = "Play",
                    icon = Icons.Filled.PlayArrow
                )
                if (authenticated) {
                    EditButton(
                        modifier = Modifier,
                        onClick = onPracticeClick,
                        title = "Practice",
                        icon = Icons.Filled.Calculate
                    )
                    EditButton(
                        modifier = Modifier,
                        onClick = onAccountClick,
                        title = "Profile",
                        icon = Icons.Filled.Person
                    )
                    EditButton(
                        modifier = Modifier,
                        onClick = onSettingsClick,
                        title = "Settings",
                        icon = Icons.Filled.Settings
                    )
                }
            }



        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    NuerdTheme(theme = "Green") {
        Menu(
            onPracticeClick = {},
            onSettingsClick = {},
            onAccountClick = {},
            onSignClick = {},
            authenticated = true,
            onPlayClick = {},
            authViewModel = null,
            countries = null,
            getCountries = viewModel(),
            toggleMenu = {}

        )
    }
}