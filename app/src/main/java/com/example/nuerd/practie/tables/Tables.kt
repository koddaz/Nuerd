package com.example.nuerd.practie.tables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nuerd.ui.theme.NuerdTheme


@Composable
fun Tables(modifier: Modifier = Modifier, table: Int) {
    val tablesCount = 9

        LazyColumn(modifier.fillMaxWidth().background(colorScheme.background, RoundedCornerShape(8.dp)).border(width = 2.dp, color = colorScheme.surface, RoundedCornerShape(8.dp))) {
            items(tablesCount) { index ->
                val result = index + 1 * table
                Row(modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                ) {
                    Row(
                        modifier.weight(1f),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            "${index + 1}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = colorScheme.onBackground
                        )
                    }
                    Row(
                        modifier.weight(1f),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            "*",
                            style = MaterialTheme.typography.bodyLarge,
                            color = colorScheme.onBackground
                        )
                    }
                    Row(
                        modifier.weight(1f),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "$table",
                            style = MaterialTheme.typography.bodyLarge,
                            color = colorScheme.onBackground
                        )
                    }
                    Row(
                        modifier.weight(1f),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            "=",
                            style = MaterialTheme.typography.bodyLarge,
                            color = colorScheme.onBackground
                        )
                    }
                    Row(
                        modifier.weight(1f),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            "$result",
                            style = MaterialTheme.typography.bodyLarge,
                            color = colorScheme.onBackground
                        )
                    }
                }

            }
        }
    }

@Preview(showBackground = true)
@Composable
fun TablesPreview() {
    NuerdTheme(theme = "Green") {
        Tables(table = 1)
    }
}