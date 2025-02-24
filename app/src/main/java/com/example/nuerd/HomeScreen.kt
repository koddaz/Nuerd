package com.example.nuerd

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.nuerd.models.AuthState
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.ui.theme.highlightColor
import com.example.nuerd.ui.theme.mainBackgroundColor



@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
    onGameClick: () -> Unit,
    onPracticeClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onTablesClick: () -> Unit,
    authViewModel: AuthViewModel? = viewModel()

    ) {

    val authState = authViewModel?.authState?.observeAsState()

    LaunchedEffect(authState?.value) {
        when (authState?.value) {
            is AuthState.Unauthenticated -> onButtonClick()
            else -> Unit
        }
    }

    Column(modifier.fillMaxSize().background(mainBackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        Column(modifier
            .width(400.dp)
            .background(Color.Transparent)
            .border(width = 3.dp, color = highlightColor)
            .padding(16.dp)
        ) {
            Row(modifier
                .padding(16.dp)
            ) {
                Text(
                    text ="Nuerd",
                    fontSize = 50.sp,
                    color = highlightColor
                )

                MenuButton(onClick = { authViewModel?.signOut() }, imageVector = Icons.Filled.PersonRemove, contentDescription = "Sign out")
            }

            Column(modifier
                .background(Color.Transparent)
                .padding(20.dp)
            ) {


                // Settings
                MenuButton(onClick = onSettingsClick, imageVector = Icons.Filled.Settings, contentDescription = "Settings")
                // Practice
                MenuButton(onClick = onPracticeClick, imageVector = Icons.Filled.Calculate, contentDescription = "Practice")
                // Tables
                MenuButton(onClick = onTablesClick, imageVector = Icons.Filled.TableChart, contentDescription = "Tables")
                // Play
                MenuButton(onClick = onGameClick, imageVector = Icons.Filled.Settings, contentDescription = "Play")

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
        modifier = Modifier.fillMaxWidth(),
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
    HomeScreen(
        onGameClick = {},
        onPracticeClick = {},
        onSettingsClick = {},
        onTablesClick = {},
        onButtonClick = {},
        authViewModel = null // Pass null in Preview
    )
}