package com.example.nuerd.account

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.nuerd.account.models.CustomTextField
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.models.User

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
        modifier.fillMaxWidth().border(width = 2.dp, color = colorScheme.surface, RoundedCornerShape(8.dp)).background(colorScheme.background, RoundedCornerShape(8.dp)).padding(16.dp)
    ) {
        Text("Edit account:", color = colorScheme.onPrimary)

        CustomTextField(
            value = username,
            label = "Username",
            onValueChange = { tempUser = it },
            keyboardType = KeyboardType.Text
        )
        CustomTextField(
            value = email,
            label = "Email",
            onValueChange = { tempMail = it },
            keyboardType = KeyboardType.Email

        )

        CustomTextField(
            value = oldPassword,
            onValueChange = { oldPassword = it },
            label = "Current password",
            keyboardType = KeyboardType.Password,
            visual = PasswordVisualTransformation()
        )
        CustomTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = "New password",
            keyboardType = KeyboardType.Password,
            visual = PasswordVisualTransformation()
        )
        CustomTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirm password",
            keyboardType = KeyboardType.Password,
            visual = PasswordVisualTransformation()
        )



        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = Color.Red)
        }

        Row(modifier.fillMaxWidth()) {
            EditButton(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Save,
                onClick = {

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
            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            EditButton(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Close,
                onClick = { onDismiss() }, title = "Cancel")

        }
    }
}