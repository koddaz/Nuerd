package com.example.nuerd.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CountingLives(lives: Int, size: Int = 20, modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly) {

            for (life in 1..lives) {
                    Column() {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = null,
                            tint = colorScheme.error,
                            modifier = Modifier.size(size.dp)
                        )
                    }

            }

        for (lostLife in 1..(3 - lives)) {
            Column() {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Empty Heart",
                    tint = colorScheme.error,
                    modifier = Modifier.size(size.dp)
                )
            }
        }


    }
}