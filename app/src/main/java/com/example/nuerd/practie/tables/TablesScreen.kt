package com.example.nuerd.practie.tables
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nuerd.ui.theme.NuerdTheme


@Composable
fun TablesScreen(onButtonClick: () -> Unit, modifier: Modifier = Modifier, navigatePractice: () -> Unit = {}) {
        ScrollingColumn()
}





@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TablesScreenPreview() {
    NuerdTheme(theme = "Green") {
        TablesScreen(onButtonClick = {})
    }
}