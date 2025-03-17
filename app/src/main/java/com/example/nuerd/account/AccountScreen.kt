package com.example.nuerd.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.models.User
import com.example.nuerd.ui.theme.NuerdTheme


@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
    userState: User? = null,
    authViewModel: AuthViewModel?
) {
    Column(
        modifier.fillMaxSize().background(colorScheme.background),
        verticalArrangement = Arrangement.Center
    ) {

        UserSettings(
            user = userState,
            authViewModel = authViewModel,
            onButtonClick = onButtonClick
        )
    }

}

@Composable
fun AccountScreenContent(user: User?) {
    Column(modifier = Modifier.fillMaxSize()) {
        UserSettings(user = user, authViewModel = null, onButtonClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun AccountScreenPreview() {
    NuerdTheme(theme = "Yellow") {
    AccountScreenContent(
        user = User(
            username = "JaneDoe",
            email = "jane@example.com",
            password = "password123",
            country = "Canada",
            id = "preview-user-id"
        ))
}
}

