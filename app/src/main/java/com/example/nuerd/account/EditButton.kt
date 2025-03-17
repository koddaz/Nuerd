package com.example.nuerd.account

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun EditButton(modifier: Modifier = Modifier, onClick: () -> Unit, title: String, icon: ImageVector) {


    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 8.dp,
        tonalElevation = 4.dp,
        color = colorScheme.background,
        border = BorderStroke(2.dp, colorScheme.surface),
        onClick = onClick
    ) {
        Row(modifier.padding(8.dp)) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(title, color = colorScheme.onPrimary)
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}