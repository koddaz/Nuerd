package com.example.nuerd.account

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.ui.theme.NuerdTheme
import com.example.nuerd.ui.theme.highlightColor

@Composable
fun ConfirmWindow(modifier: Modifier, text: String, authViewModel: AuthViewModel?, onDismiss: () -> Unit) {
    Column(
        modifier.fillMaxWidth().border(width = 2.dp, color = highlightColor).padding(16.dp)
    ) {
        Text(text)
        EditButton(modifier = Modifier, onClick = {
            authViewModel?.deleteAccount()
            authViewModel?.signOut()

        }, title = "Yes")
        EditButton(modifier = Modifier, onClick = {
            onDismiss()
        }, title = "No")
    }
}
@Preview(showBackground = true)
@Composable
fun ConfirmWindowPreview() {
    NuerdTheme {
        ConfirmWindow(
            modifier = Modifier,
            text = "Are you sure you want to delete your account?",
            authViewModel = null,
            onDismiss = {})
    }
}
