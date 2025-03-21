package com.example.nuerd

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuerd.account.AccountScreen
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



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    highScoreViewModel: HighScoreViewModel = viewModel(),
    getCountries: getCountriesViewModel? = viewModel(),
    themeViewModel: ThemeViewModel? = null,
    authViewModel: AuthViewModel? = viewModel(),
    gameViewModel: GameViewModel? = viewModel(),
) {
    val authState by authViewModel?.authState?.collectAsState() ?: remember { mutableStateOf(null) }
    val userState = remember { mutableStateOf<User?>(null) }

    var menuChoice by remember { mutableStateOf(1) }
    var isMenuVisible by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }

    val countriesList = getCountries?.countries?.observeAsState()

    val allHighScores by highScoreViewModel.allHighScores.collectAsState()
    val userHighScore by highScoreViewModel.userHighScore.collectAsState()

    LaunchedEffect(Unit) {
        getCountries?.loadCountries()
    }

    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated) {
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid != null) {
                authViewModel?.databaseGet(uid) { user ->
                    userState.value = user
                }
            }
        }
    }
Box(modifier = Modifier.fillMaxSize()) {
    Scaffold (

        topBar = {
            CustomTopBar(menuChoice = menuChoice)
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

                        3 -> AccountScreen(
                            authViewModel = authViewModel,
                            userState = userState.value,
                            onButtonClick = {
                                authViewModel?.signOut()
                                menuChoice = 1
                            }
                        )

                        4 -> GameScreen(
                            gameViewModel = gameViewModel,
                            onButtonClick = {}
                        )

                        5 -> SettingsScreen(
                            onButtonClick = { },
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
                        .padding(end = 24.dp, bottom = 24.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)

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
                            countries = countriesList?.value,
                            authViewModel = authViewModel,
                            onPracticeClick = {
                                menuChoice = 2
                                isMenuVisible = false
                            },
                            onSettingsClick = {
                                menuChoice = 5
                                isMenuVisible = false
                            },
                            onAccountClick = {
                                menuChoice = 3
                                isMenuVisible = false
                            },
                            onSignClick = {
                                menuChoice = 1
                                isMenuVisible = false
                            },
                            onPlayClick = {
                                isMenuVisible = false
                                menuChoice = 4
                            },
                            authenticated = authState is AuthState.Authenticated,
                            getCountries = getCountries!!,
                            toggleMenu = { isMenuVisible = !isMenuVisible }
                        )
                    }
                }
            }
        }




