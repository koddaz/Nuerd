package com.example.nuerd.Tables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nuerd.R
import com.example.nuerd.ui.theme.mainBackgroundColor
import com.example.nuerd.ui.theme.secondaryBackgroundColor
import kotlinx.coroutines.launch

@Composable
fun ScrollingColumn(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = {
        10
    })
    val coroutineScope = rememberCoroutineScope()
    val tablePage = pagerState.currentPage + 1

    Column(modifier.fillMaxSize().background(mainBackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Column(modifier.border(1.dp, Color.Black).width(300.dp)) {
            Row(modifier.background(secondaryBackgroundColor).fillMaxWidth().padding(10.dp)) {

                Text(text = "Table $tablePage",
                    fontSize = 40.sp,
                    color = Color.White)
            }
            HorizontalPager(state = pagerState) { page ->

                Column() {

                    Tables(table = page + 1)

                }
            }
        }
        Row(modifier.width(300.dp)) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                enabled = pagerState.currentPage > 0,
                colors = AppButtonSettings.buttonSettings()
            ) {
                Text("Back")
            }
            Button(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                enabled = pagerState.currentPage < pagerState.pageCount - 1,
                colors = AppButtonSettings.buttonSettings()
            ) {
                Text("Next")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColumnPreview()  {
        ScrollingColumn()
    }
