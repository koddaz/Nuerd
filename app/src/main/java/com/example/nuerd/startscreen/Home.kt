package com.example.nuerd.startscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nuerd.R
import com.example.nuerd.ui.theme.NuerdTheme
import com.example.nuerd.ui.theme.highlightColor

@Composable
fun Welcome() {
    Column(modifier = Modifier.fillMaxSize().background(Color.Transparent).padding(16.dp)) {
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            Image(painter = painterResource(id = R.drawable.nlogo),
                contentDescription = "logo")
            Text("Welcome to the app",
                style = MaterialTheme.typography.headlineMedium,
                color = highlightColor,
                modifier = Modifier.padding(start = 8.dp))
        }
        Text("Start your journey to become the greatest Nuerd on the planet here",
            style = MaterialTheme.typography.bodyLarge,
            color = highlightColor)

    }
}

@Composable
fun Start(user: String) {

    Column(modifier = Modifier.fillMaxSize().background(Color.Transparent).padding(16.dp)) {
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            Image(painter = painterResource(id = R.drawable.nlogo),
                contentDescription = "logo")
            Text("Welcome!",
                style = MaterialTheme.typography.headlineLarge,
                color = highlightColor,
                modifier = Modifier.padding(start = 8.dp))
        }
        Text("It is nice to see you there $user. Are you ready to take on your Nuerd journey?",
            style = MaterialTheme.typography.bodyLarge,
            color = highlightColor)

    }
}

@Preview
@Composable
fun WelcomePreview() {
    NuerdTheme {
        val tempUser = "Robert"
        Column {

            Start(user = tempUser)
        }
    }
}