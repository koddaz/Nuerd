package com.example.nuerd.practie.tables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nuerd.account.EditButton
import com.example.nuerd.ui.theme.NuerdTheme
import kotlinx.coroutines.launch

@Composable
fun ScrollingColumn(
    modifier: Modifier = Modifier,
    tableNumber: Int,
    onTableNumberChange: (Int) -> Unit,
    ) {
    val pagerState = rememberPagerState(
        initialPage = tableNumber - 1,
        pageCount = {
        10
    })
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(pagerState.currentPage) {
        onTableNumberChange(pagerState.currentPage + 1)
    }

    Column(modifier = Modifier.fillMaxSize().background(colorScheme.primary).padding(horizontal = 16.dp)) {


        Spacer(modifier = Modifier.padding(8.dp))

            HorizontalPager(
                state = pagerState) { page ->


                        Tables(table = page + 1)

            }

        

        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier.fillMaxWidth()) {
            EditButton(
                modifier = Modifier.weight(1f),
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = {
                    coroutineScope.launch {
                        if (pagerState.currentPage > 0) {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                        }
                }, title = "Back")
            Spacer(modifier = Modifier.padding(4.dp))
            EditButton(
                modifier = Modifier.weight(1f),
                icon = Icons.AutoMirrored.Filled.ArrowForward,
                onClick = {
                    coroutineScope.launch {
                        if (pagerState.currentPage < 9) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                iconPlacement = 2,
                title = "Next"
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColumnPreview() {
    NuerdTheme(theme = "Green") {
        ScrollingColumn(tableNumber = 1, onTableNumberChange = {})
    }
}
