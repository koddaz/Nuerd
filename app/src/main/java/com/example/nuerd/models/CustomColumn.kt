package com.example.nuerd.models

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.nuerd.ui.theme.NuerdTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun CustomColumn(
    title: String = "",
    modifier: Modifier = Modifier,
    bg: Color = colorScheme.background,
    border: Color = colorScheme.surface,
    textcolor: Color = colorScheme.onBackground,
    startPadding: Dp = 0.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(start = startPadding)
            .background(bg, RoundedCornerShape(8.dp))
            .border(2.dp, border, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {

        if (title.isNotEmpty()) {
            Text(
                text = title,
                color = textcolor,
                style = typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Column(modifier.padding(start = 16.dp)) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomColumnPreview() {
    NuerdTheme {
        CustomColumn(title = "Title", textcolor = colorScheme.surface) {
            Text("Content")
        }
    }
}