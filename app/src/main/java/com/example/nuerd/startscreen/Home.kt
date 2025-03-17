package com.example.nuerd.startscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nuerd.R
import com.example.nuerd.models.HighScoreEntry
import com.example.nuerd.ui.theme.NuerdTheme

@Composable
fun Welcome(allHighScores: List<HighScoreEntry>) {
    Column(modifier = Modifier.fillMaxSize().background(colorScheme.primary)) {
        Column(modifier = Modifier.fillMaxWidth().padding(8.dp).background(colorScheme.background).border(2.dp, colorScheme.surface)) {

            /*
            Row(modifier = Modifier.padding(bottom = 8.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.nlogo),
                    contentDescription = "logo"
                )
                Text(
                    "Nuerd!",
                    style = typography.titleLarge,
                    color = colorScheme.onBackground,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

             */
            Text(
                "Start your journey to become the greatest Nuerd on the planet here",
                style = typography.bodyLarge,
                color = colorScheme.onBackground
            )
            Column(modifier = Modifier.fillMaxWidth().padding(8.dp).background(colorScheme.background).border(2.dp, colorScheme.surface)) {
                Text(
                    "Global Top 5",
                    style = typography.titleMedium,
                    color = colorScheme.onBackground,
                    modifier = Modifier.padding(8.dp)
                )

                if (allHighScores.isEmpty()) {
                    Text(
                        "No high scores yet",
                        style = typography.bodyMedium,
                        color = colorScheme.onBackground,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                LazyColumn(modifier = Modifier.height(200.dp)) {
                    itemsIndexed(allHighScores.take(5)) { index, entry ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${index + 1}.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = colorScheme.onBackground,
                                modifier = Modifier.width(32.dp)
                            )
                            Text(
                                text = entry.username,
                                style = MaterialTheme.typography.bodyMedium,
                                color = colorScheme.onBackground,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = entry.score.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = colorScheme.onBackground
                            )
                        }
                        if (index < allHighScores.size - 1) {
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                        }
                    }
                }
            }
            }
        }
        }


    }


@Composable
fun Start(user: String, allHighScores: List<HighScoreEntry>, userHighScore: Int) {


    Column(modifier = Modifier.fillMaxSize().background(colorScheme.primary).padding(8.dp)) {
        /*
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.nlogo),
                contentDescription = "logo"
            )
            Text(
                "Nuerd!",
                style = MaterialTheme.typography.headlineLarge,
                color = colorScheme.onBackground,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

         */
        Column(modifier = Modifier.fillMaxWidth().background(colorScheme.background, RoundedCornerShape(8.dp)).border(2.dp, colorScheme.surface, RoundedCornerShape(8.dp)).padding(8.dp)) {

                Text(
                    "It is nice to see you there $user. Are you ready to take on your Nuerd journey?",
                    style = typography.bodyMedium,
                    color = colorScheme.onBackground
                )

        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier
            .fillMaxWidth()
            .background(colorScheme.background, RoundedCornerShape(8.dp))
            .border(2.dp, colorScheme.surface, RoundedCornerShape(8.dp))
            .padding(8.dp)) {
            Text("Global Top 5", style = typography.titleMedium, color = colorScheme.onBackground)

        }
        Spacer(modifier = Modifier.height(4.dp))
        Column(modifier = Modifier.background(colorScheme.background, RoundedCornerShape(8.dp)).border(2.dp, colorScheme.surface, RoundedCornerShape(8.dp)))
        {
            if (allHighScores.isEmpty()) {
                Text(
                    "No high scores yet",
                    style = typography.bodyMedium,
                    color = colorScheme.onBackground,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.height(200.dp).fillMaxWidth()
                        .background(colorScheme.background)
                ) {
                    itemsIndexed(allHighScores.take(5)) { index, entry ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${index + 1}.",
                                style = typography.bodyMedium,
                                color = colorScheme.onBackground,
                                modifier = Modifier.width(32.dp)
                            )
                            Text(
                                text = entry.username,
                                style = typography.bodyMedium,
                                color = colorScheme.onBackground,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = entry.score.toString(),
                                style = typography.bodyMedium,
                                color = colorScheme.onBackground
                            )
                        }
                        if (index < allHighScores.size - 1) {
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.fillMaxWidth().background(colorScheme.background, RoundedCornerShape(8.dp)).border(2.dp, colorScheme.surface, RoundedCornerShape(8.dp)).padding(8.dp)) {
            Text("$user's high score: ", style = typography.titleMedium, color = colorScheme.onBackground)

        }
        Spacer(modifier = Modifier.height(4.dp))

        Column(modifier = Modifier.fillMaxWidth().background(colorScheme.background, RoundedCornerShape(8.dp)).border(2.dp, colorScheme.surface, RoundedCornerShape(8.dp)).padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text("$userHighScore", style = typography.bodyMedium, color = colorScheme.onBackground)
        }


    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    NuerdTheme(theme = "Yellow") {
        val tempUser = "Robert"
        val mockHighScores = listOf(
            HighScoreEntry(userId = "1", username = "Robert", score = 95),
            HighScoreEntry(userId = "2", username = "John", score = 88),
            HighScoreEntry(userId = "3", username = "Doe", score = 82),
            HighScoreEntry(userId = "4", username = "Jane", score = 75),
            HighScoreEntry(userId = "5", username = "Alice", score = 70),
            HighScoreEntry(userId = "6", username = "Bob", score = 65)
        )
        Column {

            Start(user = tempUser, userHighScore = 5, allHighScores = mockHighScores)
        }
    }
}