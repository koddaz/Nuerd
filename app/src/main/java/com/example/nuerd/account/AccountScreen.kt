package com.example.nuerd.account

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.models.User
import com.example.nuerd.ui.theme.NuerdTheme
import com.example.nuerd.ui.theme.highlightColor
import com.example.nuerd.ui.theme.mainBackgroundColor
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.max


@Composable
fun AccountScreen(modifier: Modifier = Modifier,
                  authViewModel: AuthViewModel? = viewModel(),
                  onButtonClick: () -> Unit
                  ) {

    val userState = remember { mutableStateOf<User?>(null) }


        LaunchedEffect(Unit) {
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid != null) {
                authViewModel?.databaseGet(uid) { user ->
                    userState.value = user
                }
            }
        }

    Column(modifier.fillMaxSize().background(mainBackgroundColor),
        verticalArrangement = Arrangement.Center) {
        IconButton(
            onClick = onButtonClick,
        ) {
            Icon(imageVector = Icons.Filled.Home,
                contentDescription = "Home",
                tint = highlightColor)

        }
        UserSettings(user = userState.value, authViewModel = AuthViewModel(), onButtonClick = onButtonClick)

    }
}

@Composable
fun UserSettings(modifier: Modifier = Modifier, user: User?, authViewModel: AuthViewModel?, onButtonClick: () -> Unit) {
    var isCancelVisible by remember { mutableStateOf(false) }
    var isEditVisible by remember { mutableStateOf(false) }
    Column(modifier.fillMaxSize().padding(16.dp).background(Color.Transparent)) {

        if (!isCancelVisible && !isEditVisible) {
            Column(
                modifier.fillMaxWidth().border(width = 2.dp, color = highlightColor).padding(16.dp)
            ) {
                Text(
                    "Account info:",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = highlightColor
                )

                if (user != null) {
                    Text("Username: \n ${user.username ?: "N/A"}", color = highlightColor)
                    Text("Email: \n ${user.email ?: "N/A"}", color = highlightColor)
                    Text("Country: \n ${user.country ?: "N/A"}", color = highlightColor)
                } else {
                    Text("No user data found.")
                }

            }
        }
        if (isCancelVisible) {
            ConfirmWindow(
                authViewModel = authViewModel,
                modifier = Modifier,
                text = "Are you sure you want to delete your account?",
                onDismiss = { isCancelVisible = false })
        } else if (isEditVisible) {
        EditAccount(
            authViewModel = authViewModel, user = user, onDismiss = { isEditVisible = false },
            username = user?.username ?: "",
            email = user?.email ?: "",

        )
            }
        EditButton(modifier = Modifier, onClick = {
            isCancelVisible = true }, title = "Delete account")
        EditButton(modifier = Modifier, onClick = {
            isEditVisible = true }, title = "Edit account")
        EditButton(modifier = Modifier, onClick = { authViewModel?.signOut() }, title = "Sign out")

    }
}

@Composable
fun EditAccount(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel?,
    user: User?,
    onDismiss: () -> Unit,
    username: String,
    email: String,
) {
    var tempUser by remember { mutableStateOf(user?.username ?: "") }
    var tempMail by remember { mutableStateOf(user?.email ?: "") }
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier.fillMaxWidth().border(width = 2.dp, color = highlightColor).background(Color.Transparent).padding(16.dp)
    ) {
        Text("Edit account:", color = highlightColor)

        Text("Username:", color = highlightColor)
        TextField(
            value = username,
            onValueChange = { tempUser = it },
            label = { Text("Username") },
            maxLines = 1,
            modifier = modifier.fillMaxWidth()
        )

        Text("Email:", color = highlightColor)
        TextField(
            value = email,
            onValueChange = { tempMail = it },
            label = { Text("Email") },
            maxLines = 1,
            modifier = modifier.fillMaxWidth()
        )

        Text("Current password:", color = highlightColor)
        TextField(
            value = oldPassword,
            onValueChange = { oldPassword = it },
            label = { Text("Current Password") },
            maxLines = 1,
            visualTransformation = PasswordVisualTransformation(),
            modifier = modifier.fillMaxWidth()
        )

        Text("New password:", color = highlightColor)
        TextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("New Password") },
            maxLines = 1,
            visualTransformation = PasswordVisualTransformation(),
            modifier = modifier.fillMaxWidth()
        )

        Text("Confirm new password:", color = highlightColor)
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm New Password") },
            maxLines = 1,
            visualTransformation = PasswordVisualTransformation(),
            modifier = modifier.fillMaxWidth()
        )

        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = Color.Red)
        }

        Row {
            EditButton(modifier = Modifier, onClick = {

                    if (newPassword == confirmPassword) {
                        authViewModel?.updatePassword(oldPassword, newPassword) { success, error ->
                            if (success) {
                                onDismiss()
                            } else {
                                errorMessage = error ?: "Password update failed"
                            }
                        }
                    } else {
                        errorMessage = "Passwords do not match"
                    }

            }, title = "Save")
            EditButton(modifier = Modifier, onClick = { onDismiss() }, title = "Cancel")

        }
    }
}

@Composable
fun EditButton(modifier: Modifier, onClick: () -> Unit, title: String) {
    Button(onClick = onClick) {
        Text(title)
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
    NuerdTheme {
    AccountScreenContent(
        user = User(
            username = "JaneDoe",
            email = "jane@example.com",
            password = "password123",
            country = "Canada"))
}
}

