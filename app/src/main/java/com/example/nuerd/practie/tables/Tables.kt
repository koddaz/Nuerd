package com.example.nuerd.practie.tables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nuerd.ui.theme.NuerdTheme


@Composable
fun Tables(modifier: Modifier = Modifier, table: Int) {
    val tablesCount = 9



    LazyColumn(modifier
        .fillMaxWidth()
        .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp))
        .border(width = 2.dp, color = colorScheme.surface, RoundedCornerShape(8.dp))) {

        item{
            val bg = if (table % 2 == 0) colorScheme.secondary else colorScheme.background

            Column(modifier
                .background(color = bg)
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            ) {
                Text(
                    text = "Table $table",
                    style = MaterialTheme.typography.titleLarge,
                    color = colorScheme.onPrimary
                )
            }
        }
        items(tablesCount) { index ->
            val result = (index + 1) * table
            val bgTemp = if (table % 2 == 0) colorScheme.secondary else colorScheme.background
            val bgBox = if (table % 2 == 0) colorScheme.background else colorScheme.secondary

            Row(modifier
                .background(color = bgTemp)
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 2.dp),
            ) {
                Row(
                    modifier
                        .fillMaxWidth()
                        .background(bgBox, RoundedCornerShape(8.dp))
                        .padding(horizontal = 40.dp, vertical = 10.dp)
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween

                )
                {


                    Text(
                        "${index + 1}",
                        style = MaterialTheme.typography.bodySmall,
                        color = colorScheme.onBackground
                    )


                    Text(
                        "*",
                        style = MaterialTheme.typography.bodySmall,
                        color = colorScheme.onBackground
                    )


                    Text(
                        text = "$table",
                        style = MaterialTheme.typography.bodySmall,
                        color = colorScheme.onBackground
                    )

                    Text(
                        "=",
                        style = MaterialTheme.typography.bodySmall,
                        color = colorScheme.onBackground
                    )

                    Text(
                        "$result",
                        style = MaterialTheme.typography.bodySmall,
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