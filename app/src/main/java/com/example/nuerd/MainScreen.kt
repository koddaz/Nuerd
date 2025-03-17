package com.example.nuerd

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuerd.account.AccountScreen
import com.example.nuerd.account.LogIn
import com.example.nuerd.account.SignUp
import com.example.nuerd.game.GameScreen
import com.example.nuerd.menu.Menu
import com.example.nuerd.models.AuthState
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.models.GameViewModel
import com.example.nuerd.models.HighScoreEntry
import com.example.nuerd.models.HighScoreViewModel
import com.example.nuerd.models.ThemeViewModel
import com.example.nuerd.models.User
import com.example.nuerd.models.getCountriesViewModel
import com.example.nuerd.practie.PracticeScreen
import com.example.nuerd.settings.SettingsScreen
import com.example.nuerd.startscreen.HomeScreen
import com.example.nuerd.ui.theme.NuerdTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    highScoreViewModel: HighScoreViewModel = viewModel(),
    getCountries: getCountriesViewModel? = viewModel(),
    themeViewModel: ThemeViewModel? = null,
    authViewModel: AuthViewModel? = viewModel(),
    gameViewModel: GameViewModel? = viewModel(),
    padding: PaddingValues = PaddingValues(0.dp)
) {
    val authState by authViewModel?.authState?.collectAsState() ?: remember { mutableStateOf(null) }
    val userState = remember { mutableStateOf<User?>(null) }

    var menuChoice by remember { mutableStateOf(1) }
    var isMenuVisible by remember { mutableStateOf(false) }
    
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

    Column(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(colorScheme.primary)
        ) {
            // Main content
            Column(modifier = Modifier.fillMaxSize()) {
                // Screen content
                Box(modifier = Modifier.weight(1f)) {
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
                            onButtonClick = { authViewModel?.signOut()
                                menuChoice = 1 }
                        )

                        4 -> GameScreen(
                            gameViewModel = gameViewModel,
                            onButtonClick = {}
                        )

                        5 -> SettingsScreen(
                            onButtonClick = {  },
                            gameViewModel = gameViewModel,
                            themeViewModel = themeViewModel
                        )
                    }
                }

                // Bottom navigation
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            //.background(colorScheme.surface, shape = CircleShape)
                            .border(3.dp, colorScheme.surface, shape = CircleShape)
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

            // Menu overlay
            if (isMenuVisible) {
                // Background layer that closes menu when clicked
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(5f)
                        .clickable { isMenuVisible = false }
                )

                // Menu content layer (needs to stop click propagation)
                Box(
                    modifier = Modifier
                        .zIndex(10f)
                        .background(colorScheme.primary)
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Wrapper to prevent click propagation
                    Box(
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { /* Intercept clicks to prevent them from reaching background */ }
                    ) {
                        Menu(
                            countries = countriesList?.value,
                            authViewModel = authViewModel,
                            onPracticeClick = {
                                menuChoice = 2
                                isMenuVisible = false
                            },
                            onSettingsClick = {
                                menuChoice = 5  // Fixed to match SettingsScreen case
                                isMenuVisible = false
                            },
                            onAccountClick = {
                                menuChoice = 3  // Fixed to match AccountScreen case
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
    }
}


@Preview
@Composable
fun MainScreenPreview() {

    NuerdTheme {
        MainScreen(authViewModel = null, gameViewModel = null, getCountries = null)
    }
}

