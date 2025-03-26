package com.example.nuerd.models

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.nuerd.R
import com.example.nuerd.ui.theme.NuerdTheme
import kotlin.unaryMinus

@Composable
fun CustomTopBar(menuChoice: Int, goHome: () -> Unit) {
    // Use MaterialTheme colors
    val terminalGreen = colorScheme.surface
    val darkGreen = colorScheme.primary
    val mediumGreen = colorScheme.secondary

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top))
            .background(darkGreen)
    ) {
        // Main content with rectangular border
        Box(
            modifier = Modifier
                .fillMaxWidth()
                //.height(64.dp)
                .padding(bottom = 8.dp)
                .align(Alignment.Center)
                .shadow(elevation = 8.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(darkGreen, mediumGreen),
                        start = Offset(0f, 0f),
                        end = Offset(0f, 300f)
                    )
                )
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .align(Alignment.BottomCenter)
                     .padding(horizontal = 16.dp)
                    .background(terminalGreen)
            )
            PixelatedCorner(
                isLeft = true,
                darkColor = darkGreen,
                borderColor = terminalGreen
            )

            PixelatedCorner(
                isLeft = false,
                darkColor = darkGreen,
                borderColor = terminalGreen
            )





            // Content row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Logo with terminal-style border
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .shadow(8.dp, CircleShape)
                        .background(darkGreen, CircleShape)
                        .border(width = 2.dp, color = terminalGreen, shape = CircleShape)
                        .padding(4.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.nlogo),
                        contentDescription = "Logo",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Terminal-style text
                Text(
                    text = when (menuChoice) {
                        1 -> "Home"
                        2 -> "Practice"
                        3 -> "Account"
                        4 -> "Game"
                        5 -> "Settings"
                        else -> "Nuerd"
                    },
                    color = terminalGreen,
                    style = typography.headlineSmall.copy(
                        shadow = Shadow(
                            color = terminalGreen.copy(alpha = 0.7f),
                            offset = Offset(1f, 1f),
                            blurRadius = 2f
                        ),
                        letterSpacing = 1.5.sp
                    ),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 12.dp).weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Home button
                if (menuChoice != 1) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .shadow(4.dp, CircleShape)
                            .background(darkGreen, CircleShape)
                            .border(width = 2.dp, color = terminalGreen, shape = CircleShape)
                            .clickable(onClick = goHome),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Home",
                            tint = terminalGreen,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun BoxScope.PixelatedCorner(
    isLeft: Boolean,
    darkColor: Color,
    borderColor: Color
) {

    // Border pixels
    val borderPixels = listOf(
        PixelData(width = 4, height = 0, xOffset = 0, yOffset = -12, fillHeight = true),
        PixelData(width = 4, height = 4, xOffset = 12, yOffset = 0),
        PixelData(width = 4, height = 4, xOffset = 8, yOffset = 0),
        PixelData(width = 4, height = 4, xOffset = 4, yOffset = -4),
        PixelData(width = 4, height = 4, xOffset = 0, yOffset = -8)
    )

    // Draw border pixels
    for (pixel in borderPixels) {
        if (pixel.fillHeight) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(pixel.width.dp)
                    .align(if (isLeft) Alignment.BottomStart else Alignment.BottomEnd)
                    .offset(
                        x = if (isLeft) pixel.xOffset.dp else -4.dp,
                        y = (-12).dp
                    )
                    .background(borderColor)
            )
        } else {
            Box(
                modifier = Modifier
                    .width(pixel.width.dp)
                    .height(pixel.height.dp)
                    .align(if (isLeft) Alignment.BottomStart else Alignment.BottomEnd)
                    .offset(
                        x = if (isLeft) pixel.xOffset.dp else (-pixel.width - pixel.xOffset).dp,
                        y = pixel.yOffset.dp
                    )
                    .background(borderColor)
            )
        }
        Box(
            modifier = Modifier.width(4.dp).fillMaxHeight()
                .zIndex(-2f)
                .align(Alignment.BottomEnd)
                .offset(
                    x = if (isLeft) 0.dp else (-4).dp,
                    y = 0.dp

                )
                .background(darkColor)
        )
        Box(
            modifier = Modifier.height(8.dp).width(
                8.dp
            )
                .zIndex(-2f)
                .align(if (isLeft) Alignment.BottomStart else Alignment.BottomEnd)
                .offset(
                    x = if (isLeft) 0.dp else (-4).dp,
                    y = 0.dp

                )
                .background(darkColor)

        ) 
            
        
    }
    
}

data class PixelData(
    val width: Int,
    val height: Int,
    val xOffset: Int,
    val yOffset: Int,
    val fillHeight: Boolean = false
)

@Preview(showBackground = true)
@Composable
fun BarPreview() {
    NuerdTheme { 
        CustomTopBar(
            menuChoice = 1,
            goHome = {}
        ) 
    }
}