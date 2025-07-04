package com.example.nuerd.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.models.CustomColumn
import com.example.nuerd.models.User
import com.example.nuerd.ui.theme.NuerdTheme


@Composable
fun UserSettings(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
    userState: User? = null,
    authViewModel: AuthViewModel?
) {
    Column(
        modifier.fillMaxSize(),
    ) {
        var scrollState = rememberScrollState()
        var isCancelVisible by remember { mutableStateOf(false) }
        var isEditVisible by remember { mutableStateOf(false) }

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .imePadding() // <-- Add this for keyboard avoidance
                .background(colorScheme.primary)
                .padding(8.dp)
        ) {
            if (!isCancelVisible && !isEditVisible) {
                Spacer(modifier = Modifier.height(16.dp))
                CustomColumn(title = "Profile") {
                    if (userState != null) {
                        val userProperties = listOf(
                            "Username" to userState.username,
                            "Email" to userState.email,
                            "ID" to userState.id
                        )
                        userProperties.forEach { (label, value) ->
                            Text(
                                text = "$label:",
                                color = colorScheme.onPrimary,
                                style = typography.bodySmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = value,
                                modifier = Modifier.padding(start = 8.dp, top = 4.dp, bottom = 8.dp),
                                color = colorScheme.onPrimary,
                                style = typography.bodySmall
                            )
                            if (label != userProperties.last().first) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    color = colorScheme.onPrimary.copy(alpha = 0.2f)
                                )
                            }
                        }
                    } else {
                        Text(
                            text = "No user data found.",
                            color = colorScheme.onPrimary,
                            style = typography.bodySmall
                        )
                    }
                }

            }
            if (isCancelVisible) {
                Spacer(modifier = Modifier.height(16.dp))
                ConfirmWindow(
                    authViewModel = authViewModel,
                    text = "Are you sure you want to delete your account?",
                    onDismiss = { isCancelVisible = false }
                )
            } else if (isEditVisible) {
                Spacer(modifier = Modifier.height(16.dp))
                EditAccount(
                    authViewModel = authViewModel,
                    user = userState,
                    onDismiss = { isEditVisible = false },
                    username = userState?.username ?: "",
                    email = userState?.email ?: ""
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            CustomColumn(title = "Edit profile") {
                Text("Here you can edit your profile information",
                    style = typography.bodySmall,
                    color = colorScheme.onPrimary)
                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                Column(modifier = Modifier.fillMaxWidth()) {

                    EditButton(
                        modifier = Modifier.padding(bottom = 2.dp),
                        icon = Icons.Default.Edit,
                        onClick = { isEditVisible = true },
                        title = "Edit account",
                        useCompactLayout = false)
                    EditButton(
                        modifier = Modifier.padding(bottom = 2.dp),
                        icon = Icons.AutoMirrored.Filled.Logout,
                        onClick = { onButtonClick() },
                        title = "Sign out",
                        useCompactLayout = false)
                    EditButton(
                        modifier = Modifier.padding(bottom = 2.dp),
                        icon = Icons.Default.Delete,
                        onClick = { isCancelVisible = true },
                        title = "Delete account",
                        useCompactLayout = false)
                }
            }
        }
    }

}

@Composable
fun AccountScreenContent(user: User?) {
    Column(modifier = Modifier.fillMaxSize()) {
        UserSettings(userState = user, authViewModel = null, onButtonClick = {})
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
                country = "Canada",
                id = "preview-user-id"
            ))
    }
}

