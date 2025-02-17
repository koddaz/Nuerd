package com.example.nuerd.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nuerd.ui.theme.highlightColor

@Composable
fun CountingLives(modifier: Modifier = Modifier, lives: Int) {

    Column(
        modifier
            .padding(16.dp)
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = null,
            tint = highlightColor
        )
        Row {

            for (life in 1..lives) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = modifier.size(32.dp)

                )
            }

            for (lostLife in 1..(3 - lives)) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Empty Heart",
                    tint = Color.Red,
                    modifier = modifier.size(32.dp)
                )
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun CountingLivesPreview() {
    CountingLives(
        lives = 3,

    )
}