package com.example.nuerd.game

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CustomIconButton(
    onPlayClicked: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String,
    tint: Color = colorScheme.surface,
    size: Int = 90
) {
    IconButton(onClick = onPlayClicked) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier.size(size.dp),
            tint = tint
        )
    }
}