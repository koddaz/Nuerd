package com.example.nuerd.tables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun Tables(modifier: Modifier = Modifier, table: Int) {
    val tablesCount = 9


    Column {
        LazyColumn {
            items(tablesCount) { index ->
                Row(modifier
                    .fillMaxWidth()
                    .background( Color.Blue
                        /*  when (table) {
                            0 -> lightPurple    // light gray
                             1 -> coralAccent    // coral red
                             2 -> complementaryTeal  // teal blue
                             3 -> buttonBackgroundColor // picton blue
                             4 -> highlightColor    // mikado yellow
                             5 -> orangeAccent    // burnt orange
                             6 -> skyBlue        // deep sky blue
                             7 -> goldenAccent   // goldenrod
                             8 -> brightBlue     // electric blue
                             9 -> secondaryBackgroundColor   // verdigris
                             else -> actionButtonColor // pumpkin
                        }*/
                    ).padding(10.dp)
                ) {
                    Column(modifier.weight(1f)) {
                        Text("${index + 1}",
                            fontSize = 40.sp,
                            color = Color.White)
                    }
                    Column(modifier.weight(1f)) {
                        Text("*",
                            fontSize = 40.sp,
                            color = Color.White)
                    }
                    Column(modifier.weight(1f)) {
                        Text(
                            text = "$table",
                            fontSize = 40.sp,
                            color = Color.White)
                    }
                    Column(modifier.weight(1f)) {
                        Text("=",
                            fontSize = 40.sp,
                            color = Color.White)
                    }
                    Column(modifier.weight(1f)) {
                        Text("${index + 1 * table}",
                            fontSize = 40.sp,
                            color = Color.White)
                    }
                }
            }
        }
    }
}