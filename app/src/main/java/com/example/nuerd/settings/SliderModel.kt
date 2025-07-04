package com.example.nuerd.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SliderModel(
    title: String,
    value: Int,
    enabled: Boolean,
    onValueChange: (Int) -> Unit,
    onCheckedChange: (Boolean) -> Unit) {

    Text(title, color = colorScheme.onBackground, style = typography.bodySmall)
    Row(modifier = Modifier.fillMaxWidth()) {
        Slider(
            colors = SliderDefaults.colors(
                thumbColor = colorScheme.onBackground,
                activeTrackColor = colorScheme.onBackground,
                inactiveTrackColor = colorScheme.onBackground.copy(alpha = 0.5f)
            ),
            enabled = enabled,
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = 0f..100f,
            modifier = Modifier.weight(1f)
        )
        Checkbox(
            colors = CheckboxDefaults.colors(
                checkedColor = colorScheme.onBackground,
                uncheckedColor = colorScheme.onBackground.copy(alpha = 0.5f)
            ),
            checked = enabled,
            onCheckedChange = { onCheckedChange(it) }
        )
    }
}