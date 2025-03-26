package com.example.nuerd

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nuerd.startscreen.HomeScreen
import com.example.nuerd.account.LogIn
import com.example.nuerd.account.SignUp
import com.example.nuerd.game.GameScreen
import com.example.nuerd.menu.Menu
import com.example.nuerd.models.AuthState
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.models.GameViewModel
import com.example.nuerd.models.Routes
import com.example.nuerd.models.ThemeViewModel
import com.example.nuerd.models.User
import com.example.nuerd.models.getCountriesViewModel
import com.example.nuerd.settings.SettingsScreen
import com.example.nuerd.ui.theme.NuerdTheme

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch




/*
// Skapa en class för alla routes och en för navbaritem.

@SuppressLint("UnrememberedMutableState")
@Composable
fun TabNav(
    themeViewModel: ThemeViewModel? = viewModel(),
    authViewModel: AuthViewModel? = viewModel(),
    gameViewModel: GameViewModel? = viewModel(),
    getCountries: getCountriesViewModel = viewModel()
) {

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val countriesList = getCountries.countries.observeAsState()

    val authState by authViewModel?.authState?.collectAsState() ?: mutableStateOf(null)
    val userState = remember { mutableStateOf<User?>(null) }

    var haveAcc by remember { mutableStateOf(false) }
    val changeHaveAcc = { haveAcc = !haveAcc }

    LaunchedEffect(Unit) {
        getCountries.loadCountries()
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

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color.Transparent)
                    .width(300.dp)
                    .padding(bottom = 150.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Menu(
                    onPracticeClick = {
                        navController.navigate(Routes.PRACTICE)
                        scope.launch { drawerState.close() }
                    },
                    onSettingsClick = {
                        navController.navigate(Routes.SETTINGS)
                        scope.launch { drawerState.close() }
                    },
                    onAccountClick = {
                        navController.navigate(Routes.ACCOUNT)
                        scope.launch { drawerState.close() }
                    },
                    onSignClick = {
                        navController.navigate(Routes.SIGN)
                        scope.launch { drawerState.close() }
                    },
                    onPlayClick = {},
                    authenticated = authState is AuthState.Authenticated,
                )
            }
        }
    ) {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    containerColor = colorScheme.background,
                    actions = {
                        IconButton(
                            modifier = Modifier.weight(1f),
                            onClick = { navController.navigate(Routes.GAME) }) {
                            Icon(
                                Icons.Filled.PlayArrow,
                                contentDescription = "Game",
                                tint = colorScheme.onBackground,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        IconButton(
                            modifier = Modifier.weight(1f),
                            onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                Icons.Filled.Menu,
                                contentDescription = "Menu",
                                tint = colorScheme.onBackground,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                )
            },
        ) { innerPadding ->
            NavHost(
                navController,
                startDestination = Routes.HOME,
                Modifier.padding(innerPadding)
            ) {
                composable(Routes.HOME) {
                    if (authViewModel != null) {
                        HomeScreen(
                            authState = authState ?: AuthState.Unauthenticated,
                            userState = userState.value
                        )
                    } else {
                        // Handle the case where authViewModel is null
                        Text("Error: AuthViewModel is not available")
                    }
                }
                composable(Routes.GAME) {
                    gameViewModel?.let { it1 ->
                        GameScreen(
                            modifier = Modifier,
                            gameViewModel = it1,
                            onButtonClick = {}
                        )
                    }
                }
                composable(Routes.PRACTICE) {
                    PracticeNavHost(
                        gameViewModel = gameViewModel!!,
                        onButtonClick = { navController.popBackStack() }
                    )
                }
                composable(Routes.SETTINGS) {
                    SettingsScreen(
                        onButtonClick = { navController.popBackStack() },
                        gameViewModel = gameViewModel,
                        themeViewModel = themeViewModel
                    )
                }
                composable(Routes.ACCOUNT) {
                    AccountScreen(
                        authViewModel = authViewModel,
                        userState = userState.value,
                        onButtonClick = { navController.popBackStack() }
                    )
                }
                composable(Routes.SIGN) {
                    if (haveAcc) {
                        LogIn(
                            authViewModel = authViewModel,
                            navOnLogin = {
                                navController.popBackStack()
                            })
                    } else {
                        SignUp(
                            authViewModel = authViewModel,
                            navOnLogin = {
                                navController.popBackStack()
                            },
                            countriesList = countriesList.value)
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun BottomAppBarExamplePreview() {
    val mockAuthViewModel = AuthViewModel().apply {
        // Set up mock data for the preview
    }
    val mockApplication = Application() // Mock application instance
    val mockGameViewModel = GameViewModel(
        application = mockApplication
    ).apply {
        // Set up mock data for the preview
    }
    TabNav(
        themeViewModel = viewModel(),
        authViewModel = mockAuthViewModel,
        gameViewModel = mockGameViewModel,
        getCountries = viewModel()
    )
}
 */