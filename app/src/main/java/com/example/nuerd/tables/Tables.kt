package com.example.nuerd.tables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nuerd.ui.theme.highlightColor


@Composable
fun Tables(modifier: Modifier = Modifier, table: Int) {
    val tablesCount = 9


    Column(modifier.border(width = 2.dp, color = highlightColor)) {
        LazyColumn {
            items(tablesCount) { index ->
                val result = index + 1 * table
                Row(modifier
                    .fillMaxWidth()
                    .background( Color.Transparent

                    ).padding(10.dp)
                ) {
                    Column(modifier.weight(1f)) {
                        Text("${index + 1}",
                            style = MaterialTheme.typography.bodyLarge,
                            )
                    }
                    Column(modifier.weight(1f)) {
                        Text("*",
                            style = MaterialTheme.typography.bodyLarge)
                    }
                    Column(modifier.weight(1f)) {
                        Text(
                            text = "$table",
                            style = MaterialTheme.typography.bodyLarge)
                    }
                    Column(modifier.weight(1f)) {
                        Text("=",
                            style =  MaterialTheme.typography.bodyLarge)
                    }
                    Column(modifier.weight(1f)) {
                        Text("$result",
                            style =  MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TablesPreview() {
    Tables( table = 1)
}