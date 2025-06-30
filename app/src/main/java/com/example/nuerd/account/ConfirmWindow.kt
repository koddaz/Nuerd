package com.example.nuerd.account

import android.R
import android.R.attr.textAlignment
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.models.CustomColumn
import com.example.nuerd.ui.theme.NuerdTheme
import kotlin.reflect.typeOf

@Composable
fun ConfirmWindow(text: String, authViewModel: AuthViewModel?, onDismiss: () -> Unit) {
    CustomColumn(title = "Confirm", bg = colorScheme.errorContainer, border = colorScheme.error, textcolor = colorScheme.onErrorContainer) {
        Text(text, color = colorScheme.onErrorContainer, textAlign = TextAlign.Justify, style = typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            EditButton(
                bgColor = colorScheme.errorContainer,
                textcolor = colorScheme.surface,
                useCompactLayout = true,
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Check,
                onClick = {
                    authViewModel?.deleteAccount { success, error ->
                        if (success) {
                            authViewModel.signOut()
                            onDismiss()
                        } else {
                            Log.e("DeleteAccount", "Error deleting account: $error")
                        }

                    }


                }, title = "Yes"
            )
            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            EditButton(
                bgColor = colorScheme.errorContainer,
                textcolor = colorScheme.error,
                borderColor = colorScheme.error,
                useCompactLayout = true,
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Close,
                onClick = {
                    onDismiss()
                }, title = "No"
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ConfirmWindowPreview() {
    NuerdTheme(theme = "Green") {
        ConfirmWindow(
            text = "Are you sure you want to delete your account?",
            authViewModel = null,
            onDismiss = {})
    }
}
