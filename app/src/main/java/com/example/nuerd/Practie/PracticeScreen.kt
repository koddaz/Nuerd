package com.example.nuerd.Practie

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PracticeScreen(onButtonClick: () -> Unit) {
    Column() {
        Text("Practice Screen")
        Button(onClick = onButtonClick) {
            Text("Home")
        }
    }

}
@Preview(showBackground = true)
@Composable
fun PracticeScreenPreview() {
    PracticeScreen(onButtonClick = {})
}
