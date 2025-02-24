package com.example.nuerd.game

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.example.nuerd.ui.theme.highlightColor

@Composable
fun CountingLives(lives: Int) {
    Row {

            for (life in 1..lives) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = highlightColor
                )
            }

        for (lostLife in 1..(3 - lives)) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = "Empty Heart",
                tint = highlightColor
            )
        }


    }
}