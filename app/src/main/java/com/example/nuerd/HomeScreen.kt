package com.example.nuerd

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.nuerd.models.AuthState
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.ui.theme.NuerdTheme
import com.example.nuerd.ui.theme.highlightColor
import com.example.nuerd.ui.theme.mainBackgroundColor


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSignClick: () -> Unit,
    onButtonClick: () -> Unit,
    onGameClick: () -> Unit,
    onPracticeClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onTablesClick: () -> Unit,
    onAccountClick: () -> Unit,
    authViewModel: AuthViewModel? = viewModel(),
    authState: AuthState?
) {
    LaunchedEffect(authState) {
        authViewModel?.checkAuthStatus()
    }

    Column(
        modifier.fillMaxSize().background(mainBackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .height(500.dp)
                .background(Color.Transparent)
                .paint(
                    painter = painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillBounds
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(modifier.padding(16.dp)) {
                Text(
                    text = "Nuerd",
                    fontSize = 50.sp,
                    color = highlightColor,
                )
            }

            Column(
                modifier
                    .background(Color.Transparent)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MenuButton(onClick = onGameClick, imageVector = Icons.Filled.PlayArrow, contentDescription = "Play")
                Row(modifier.padding(vertical = 8.dp)) {
                    Text("...", style = MaterialTheme.typography.titleLarge, color = highlightColor)
                }

                when (authState) {
                    is AuthState.Authenticated -> {
                        MenuButton(
                            onClick = onPracticeClick,
                            imageVector = Icons.Filled.Calculate,
                            contentDescription = "Practice"
                        )
                        MenuButton(
                            onClick = onTablesClick,
                            imageVector = Icons.Filled.TableChart,
                            contentDescription = "Tables"
                        )
                        Row(modifier.padding(vertical = 8.dp)) {
                            Text(
                                "...",
                                style = MaterialTheme.typography.titleLarge,
                                color = highlightColor
                            )
                        }
                        MenuButton(
                            onClick = onAccountClick,
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile"
                        )
                        MenuButton(
                            onClick = onSettingsClick,
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings"
                        )
                    }
                    else -> {
                        MenuButton(
                            onClick = onSignClick,
                            imageVector = Icons.Filled.PersonAddAlt,
                            contentDescription = "Sign in/up"
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun MenuButton(onClick: () -> Unit, imageVector: ImageVector, contentDescription: String) {

    Button(
        border = BorderStroke(width = 4.dp, color = highlightColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.width(250.dp),
        onClick = {
            onClick()
        }) {

        Row(Modifier.weight(1f)) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                tint = highlightColor,
                modifier = Modifier.padding(end = 10.dp)
            )
            Text(text = contentDescription, color = highlightColor)
        }



    }


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    NuerdTheme() {
        HomeScreen(
            onGameClick = {},
            onPracticeClick = {},
            onSettingsClick = {},
            onTablesClick = {},
            onButtonClick = {},
            onAccountClick = {},
            onSignClick = {},
            authViewModel = null,
            authState = null
            // Pass null in Preview
        )
    }
}