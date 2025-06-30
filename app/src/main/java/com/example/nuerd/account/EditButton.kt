package com.example.nuerd.account

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun EditButton(
    textSize: TextStyle = typography.bodyMedium,
    bgColor: Color = colorScheme.background,
    borderColor: Color = colorScheme.surface,
    textcolor: Color = colorScheme.onBackground,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: String,
    icon: ImageVector,
    iconPlacement: Int = 1,
    spacerLength: Dp = 8.dp,
    padding: Dp = 8.dp,
    useCompactLayout: Boolean = false // Set to true to place text below icon
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 8.dp,
        tonalElevation = 4.dp,
        color = bgColor,
        border = BorderStroke(2.dp, borderColor),
        onClick = onClick,
    ) {
        if (useCompactLayout) {
            // Compact layout - text goes below icon
            Column(
                modifier = Modifier.padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = textcolor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title,
                    color = textcolor,
                    textAlign = TextAlign.Center,
                    style = typography.bodySmall
                )
            }
        } else {
            // Standard layout - icon and text side by side
            Row(modifier = Modifier.padding(padding), verticalAlignment = Alignment.CenterVertically) {
                if (iconPlacement == 1) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = textcolor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(title, color = textcolor, style = textSize)
                }



                if (iconPlacement == 2) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = title,
                            tint = textcolor,

                        )
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(spacerLength))
}