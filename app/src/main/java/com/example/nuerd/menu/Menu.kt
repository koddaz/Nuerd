package com.example.nuerd.menu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nuerd.R

import com.example.nuerd.ui.theme.NuerdTheme
import com.example.nuerd.ui.theme.highlightColor
import com.example.nuerd.ui.theme.mainBackgroundColor


@Composable
fun Menu(
    modifier: Modifier = Modifier,
    onPracticeClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAccountClick: () -> Unit,
    onSignClick: () -> Unit,
    haveAcc: Boolean,
    changeHaveAcc: () -> Unit,
    authenticated: Boolean = false
) {



    Column(
        modifier.background(Color.Transparent).wrapContentSize()
    ) {
        Column(
            modifier
                .fillMaxWidth()

                .background(mainBackgroundColor)
                .paint(
                    painter = painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillBounds
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(modifier.padding(16.dp)) {
                Text(
                    text = "Menu",
                    style = MaterialTheme.typography.displayMedium,
                    color = highlightColor,
                )
                if(!authenticated) {
                    Text(
                        text = "To be able to use the full content of the game you will have to sign up or log in.",
                        color = highlightColor,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

            }

            Column(
                modifier
                    .background(Color.Transparent)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                MenuButton(
                    enabled = authenticated,
                    onClick = onPracticeClick,
                    imageVector = Icons.Filled.Calculate,
                    contentDescription = "Practice"
                )

                MenuButton(
                    enabled = authenticated,
                    onClick = onAccountClick,
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Profile"
                )
                MenuButton(
                    enabled = authenticated,
                    onClick = onSettingsClick,
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings"
                )

                if (!authenticated) {
                MenuButton(
                    enabled = true,
                    onClick = {
                        onSignClick()
                    },
                    imageVector = Icons.Filled.PersonAddAlt,
                    contentDescription = if (haveAcc) "Sign In" else "Sign Up"
                )

                    Column(modifier = Modifier.padding(top = 20.dp)) {
                        Text(
                            "Already have an account?", color = highlightColor,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            "Click here",
                            color = highlightColor,
                            modifier = Modifier.clickable(onClick = { changeHaveAcc() })
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun MenuButton(onClick: () -> Unit, enabled: Boolean, imageVector: ImageVector, contentDescription: String) {

    Button(
        border = BorderStroke(width = 4.dp, color = highlightColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        enabled = enabled,
        modifier = Modifier.width(250.dp),
        onClick = {
            onClick()
        }) {

        Row(Modifier.weight(1f)) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                tint = highlightColor,
                modifier = Modifier.padding(end = 10.dp)
            )
            Text(text = contentDescription, color = highlightColor)
        }



    }


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    NuerdTheme() {
        Menu(
            onPracticeClick = {},
            onSettingsClick = {},
            onAccountClick = {},
            onSignClick = {},
            authenticated = true,
            haveAcc = false,
            changeHaveAcc = {}
        )
    }
}