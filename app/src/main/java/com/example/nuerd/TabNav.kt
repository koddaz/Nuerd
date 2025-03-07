import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.example.nuerd.HomeScreen
import com.example.nuerd.account.AccountScreen
import com.example.nuerd.account.LoginScreen
import com.example.nuerd.game.GameScreen
import com.example.nuerd.models.AuthState
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.models.GameViewModel
import com.example.nuerd.models.Routes
import com.example.nuerd.practie.PracticeScreen
import com.example.nuerd.settings.SettingsScreen
import com.example.nuerd.ui.theme.highlightColor
import com.example.nuerd.ui.theme.mainBackgroundColor
import kotlinx.coroutines.launch

@Composable
fun TabNav(gameViewModel: GameViewModel = viewModel(), authViewModel: AuthViewModel? = viewModel()) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val authState = authViewModel?.authState?.value
    var authenticated by remember { mutableStateOf(false) }
    var haveAcc by remember { mutableStateOf(false) }
    val changeHaveAcc = { haveAcc = !haveAcc }

    LaunchedEffect(authState) {
        authViewModel?.checkAuthStatus()
        if (authState is AuthState.Authenticated) {
            authenticated = true
        } else {
            authenticated = false
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
                        .padding(bottom = 100.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End
                ) {
                    HomeScreen(
                        onPracticeClick = { navController.navigate(Routes.PRACTICE)
                            scope.launch { drawerState.close ()}
                                          },
                        onSettingsClick = { navController.navigate(Routes.SETTINGS)
                            scope.launch { drawerState.close ()}
                                          },
                        onAccountClick = { navController.navigate(Routes.ACCOUNT)
                            scope.launch { drawerState.close ()}
                                         },
                        onSignClick = { navController.navigate(Routes.SIGN)
                            scope.launch { drawerState.close ()}
                                      },
                        authenticated = authenticated,
                        haveAcc = haveAcc,
                        changeHaveAcc = changeHaveAcc
                    )
                }
            }

        ) {
            Scaffold(
                bottomBar = {
                    BottomAppBar(
                        containerColor = mainBackgroundColor,
                        actions = {

                            IconButton(
                                modifier = Modifier.weight(1f),
                                onClick = { navController.navigate(Routes.GAME) }) {
                                Icon(
                                    Icons.Filled.PlayArrow,
                                    contentDescription = "Game",
                                    tint = highlightColor,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                            IconButton(
                                modifier = Modifier.weight(1f),
                                onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    Icons.Filled.Menu,
                                    contentDescription = "Menu",
                                    tint = highlightColor,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    )
                },
            ) { innerPadding ->
                NavHost(
                    navController,
                    startDestination = Routes.GAME,
                    Modifier.padding(innerPadding)
                ) {
                    composable(Routes.GAME) {
                        GameScreen(
                            modifier = Modifier,
                            gameViewModel = gameViewModel,
                            onButtonClick = {}
                        )
                    }
                    composable(Routes.PRACTICE) {
                        PracticeScreen(
                            onButtonClick = { navController.popBackStack() }
                        )
                    }
                    composable(Routes.SETTINGS) {
                        SettingsScreen(
                            onButtonClick = { navController.popBackStack() }
                        )
                    }
                    composable(Routes.ACCOUNT) {
                        AccountScreen(
                            authViewModel = authViewModel,
                            onButtonClick = { navController.popBackStack() }
                        )
                    }
                    composable(Routes.SIGN) {
                        LoginScreen(
                            onButtonClick = { navController.popBackStack() },
                            authViewModel = authViewModel,
                            authState = authState,
                            haveAcc = haveAcc,
                            navOnLogin = { navController.navigate(Routes.ACCOUNT) },
                        )

                    }
                }
            }
        }

}

@Preview(showBackground = true)
@Composable
fun BottomAppBarExamplePreview() {
    TabNav(authViewModel = null)
}