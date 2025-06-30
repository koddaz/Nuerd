package com.example.nuerd

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuerd.account.UserSettings
import com.example.nuerd.game.GameScreen
import com.example.nuerd.menu.Menu
import com.example.nuerd.models.AuthState
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.models.GameViewModel
import com.example.nuerd.models.HighScoreViewModel
import com.example.nuerd.models.ThemeViewModel
import com.example.nuerd.models.User
import com.example.nuerd.models.getCountriesViewModel
import com.example.nuerd.practie.PracticeScreen
import com.example.nuerd.settings.SettingsScreen
import com.example.nuerd.startscreen.HomeScreen
import com.google.firebase.auth.FirebaseAuth
import com.example.nuerd.models.CustomTopBar
import com.example.nuerd.ui.theme.NuerdTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    highScoreViewModel: HighScoreViewModel? = viewModel(),
    themeViewModel: ThemeViewModel? = null,
    authViewModel: AuthViewModel? = viewModel(),
    gameViewModel: GameViewModel? = viewModel(),
) {
    val authState by authViewModel?.authState?.collectAsState() ?: remember { mutableStateOf(null) }
    val userState = remember { mutableStateOf<User?>(null) }

    var menuChoice by remember { mutableIntStateOf(1) }
    var isMenuVisible by remember { mutableStateOf(false) }

    val allHighScores by highScoreViewModel?.allHighScores?.collectAsState() ?: remember { mutableStateOf(emptyList()) }
    val userHighScore by highScoreViewModel?.userHighScore?.collectAsState()  ?: remember { mutableIntStateOf(0) }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Unauthenticated -> {
                menuChoice = 1
            }
            is AuthState.Authenticated -> {
                val uid = FirebaseAuth.getInstance().currentUser?.uid
                if (uid != null) {
                    authViewModel?.databaseGet(uid) { user ->
                        userState.value = user
                    }
                }
            }
            else -> { /* no-op for Loading or Error */ }
        }
    }

Box(modifier = Modifier.fillMaxSize()) {
    Scaffold (

        topBar = {
            CustomTopBar(menuChoice = menuChoice, goHome = { menuChoice = 1 })
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(colorScheme.primary)
        ) {
            // Main content

                    when (menuChoice) {
                        1 -> HomeScreen(
                            authState = authState,
                            userState = userState.value,
                            allHighScores = allHighScores,
                            userHighScore = userHighScore
                        )

                        2 -> PracticeScreen(
                            gameViewModel = gameViewModel,
                        )

                        3 -> UserSettings(
                            authViewModel = authViewModel,
                            userState = userState.value,
                            onButtonClick = {
                                authViewModel?.signOut()
                                menuChoice = 1
                            },
                        )

                        4 -> GameScreen(
                            gameViewModel = gameViewModel,
                        )

                        5 -> SettingsScreen(
                            gameViewModel = gameViewModel,
                            themeViewModel = themeViewModel
                        )
                    }
                }

                // Bottom navigation
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .padding(end = 24.dp, bottom = 12.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)

                            .background(colorScheme.primary, CircleShape)
                            .border(3.dp, colorScheme.surface, CircleShape)
                            .clickable { isMenuVisible = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = colorScheme.surface
                        )
                    }
                }
                }

        }

            // Menu overlay
            if (isMenuVisible) {
                // Background layer that closes menu when clicked
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorScheme.primary.copy(alpha = 0.9f))
                        .zIndex(5f)
                        .clickable { isMenuVisible = false },
                )

                // Menu content layer (needs to stop click propagation)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(10f)
                        .padding(20.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    // Wrapper to prevent click propagation
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { /* Intercept clicks */ }
                    ) {
                        Menu(
                            authViewModel = authViewModel,
                            onClick = { choice ->
                                menuChoice = choice
                                isMenuVisible = false
                            },
                            authenticated = authState is AuthState.Authenticated,
                        )
                    }
                }
            }
        }



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NuerdTheme {
        MainScreen(
            gameViewModel = null,
            authViewModel = null,
            themeViewModel = null,
            highScoreViewModel = null,
        )
    }
    }
