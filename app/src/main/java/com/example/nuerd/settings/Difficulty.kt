package com.example.nuerd.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DifficultyModel(
    onClick: () -> Unit,
    selected: Boolean,
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(if (selected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .border(width = 2.dp, color = MaterialTheme.colorScheme.onBackground, RoundedCornerShape(8.dp)
            ).padding(4.dp)
    ) {}
}

@Preview(showBackground = true)
@Composable
fun DifficultyPReview() {
    DifficultyModel(onClick = {}, selected = true)

}