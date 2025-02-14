package com.example.nuerd.Tables
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.nuerd.Tables.AppButtonSettings.buttonSettings
import com.example.nuerd.ui.theme.buttonBackgroundColor
import com.example.nuerd.ui.theme.mainBackgroundColor

object AppButtonSettings {
    @Composable
    fun buttonSettings(): ButtonColors {
        return ButtonDefaults.buttonColors(
            containerColor = buttonBackgroundColor,
            contentColor = Color.White
        )
    }
}

@Composable
fun TablesScreen(onButtonClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier
        .fillMaxSize()
        .background(mainBackgroundColor)) {
        Text("Tables Screen")

        ScrollingColumn()

        Row(modifier.wrapContentSize()) {
            Button(

                onClick = onButtonClick,
                colors = AppButtonSettings.buttonSettings()          // Set the text color
                ) {
                Text("Home")
            }
        }


    }
}





@Preview(showBackground = true)
@Composable
fun TablesScreenPreview() {
    TablesScreen(onButtonClick = {})
}