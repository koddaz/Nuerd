package com.example.nuerd.practie.tables
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nuerd.ui.theme.NuerdTheme


@Composable
fun TablesScreen(tableNumber: Int, onTableNumberChange: (Int) -> Unit) {
        ScrollingColumn(tableNumber = tableNumber, onTableNumberChange = onTableNumberChange)
}





@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TablesScreenPreview() {
    NuerdTheme(theme = "Green") {
        TablesScreen(tableNumber = 1, onTableNumberChange = {})
    }
}