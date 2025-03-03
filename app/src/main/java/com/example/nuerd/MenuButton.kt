package com.example.nuerd

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nuerd.ui.theme.gameFont
import com.example.nuerd.ui.theme.highlightColor

@Composable
fun PixelButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    widthMultiplier: Int = 2 // Control how much wider the button should be
) {
    val pixelSize = 4.dp // Defines the size of each "pixel"

    val buttonPixels = generateWidePixelButton(widthMultiplier)

    val colors = listOf(
        Color.Transparent, // 0 = No pixel
        Color.Black,       // 1 = Border
        Color(0xFFAA5522), // 2 = Darker Orange (Shadow)
        Color(0xFFFFAA44)  // 3 = Lighter Orange (Main button color)
    )

    Box(
        modifier = modifier
            .size((buttonPixels[0].size * pixelSize.value).dp, (buttonPixels.size * pixelSize.value).dp)
            .clickable { onClick() }
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val blockSize = size.width / buttonPixels[0].size // Dynamic pixel size based on width

            for (y in buttonPixels.indices) {
                for (x in buttonPixels[y].indices) {
                    val colorIndex = buttonPixels[y][x]
                    if (colorIndex != 0) {
                        drawRect(
                            color = colors[colorIndex],
                            topLeft = Offset(x * blockSize, y * blockSize),
                            size = Size(blockSize, blockSize)
                        )
                    }
                }
            }
        }

        // Pixelated Text
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = TextStyle(fontFamily = FontFamily.Monospace),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
fun generateWidePixelButton(widthMultiplier: Int = 2): Array<IntArray> {
    val basePattern = arrayOf(
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 0),
        intArrayOf(1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1),
        intArrayOf(1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 1),
        intArrayOf(1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 1),
        intArrayOf(1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 1, 1),
        intArrayOf(0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0)
    )

    return basePattern.map { row ->
        val leftPart = row.take(3) // First pixels (left border)
        val middlePart = row.drop(3).dropLast(3).flatMap { List(widthMultiplier) { it } } // Stretch middle
        val rightPart = row.takeLast(3) // Last pixels (right border)

        (leftPart + middlePart + rightPart).toIntArray()
    }.toTypedArray()
}



@Preview(showBackground = true)
@Composable
fun MenuButtonPreview() {
    PixelButton(text = "Play", onClick = {} )
}