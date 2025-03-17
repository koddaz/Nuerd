package com.example.nuerd.account

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.unit.dp
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.models.User

@Composable
fun UserSettings(
    user: User?,
    authViewModel: AuthViewModel?,
    onButtonClick: () -> Unit
) {
    var isCancelVisible by remember { mutableStateOf(false) }
    var isEditVisible by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize().background(colorScheme.primary).padding(16.dp)
    ) {
        if (!isCancelVisible && !isEditVisible) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .background(colorScheme.background, RoundedCornerShape(8.dp))
                .border(width = 2.dp, color = colorScheme.surface, RoundedCornerShape(8.dp))
                .padding(16.dp)) {
                Text(
                    "Account info:",
                    style = typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorScheme.background, RoundedCornerShape(8.dp))
                    .border(width = 2.dp, color = colorScheme.surface, RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {

                if (user != null) {
                    Text("Username: \n ${user.username ?: "N/A"}", color = colorScheme.onPrimary)
                    Text("Email: \n ${user.email ?: "N/A"}", color = colorScheme.onPrimary)
                    Text("Country: \n ${user.country ?: "N/A"}", color = colorScheme.onPrimary)
                } else {
                    Text("No user data found.")
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
                user = user,
                onDismiss = { isEditVisible = false },
                username = user?.username ?: "",
                email = user?.email ?: ""
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            EditButton(
                modifier = Modifier,
                icon = Icons.Default.Delete,
                onClick = { isCancelVisible = true }, title = "Delete account")

            EditButton(
                modifier = Modifier,
                icon = Icons.Default.Edit,
                onClick = { isEditVisible = true }, title = "Edit account")
            EditButton(
                modifier = Modifier,
                icon = Icons.AutoMirrored.Filled.Logout,
                onClick = {
                    onButtonClick()
                }, title = "Sign out")
        }
    }
}