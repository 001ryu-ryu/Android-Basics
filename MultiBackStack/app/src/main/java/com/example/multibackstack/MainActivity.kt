package com.example.multibackstack

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.multibackstack.ui.theme.MultiBackStackTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            MultiBackStackTheme {
                val rootNavController = rememberNavController()
                val navBackStackEntry by rootNavController.currentBackStackEntryAsState()

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            items.forEach { item ->
                                val isSelected = navBackStackEntry?.destination?.route?.equals(item.title.lowercase(), ignoreCase = true) == true
                                NavigationBarItem(
                                    selected = isSelected,
                                    label = { Text(item.title) },
                                    icon = {
                                        Icon(
                                            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                            contentDescription = null
                                        )
                                    },
                                    onClick = {
                                        rootNavController.navigate(item.title.lowercase()) {
                                            popUpTo(rootNavController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }) { innerPadding ->
                    NavHost(rootNavController, startDestination = "home") {
                        composable("home") {
                            HomeNavHost()
                        }

                        composable("chat") {
                            ChatNavHost()
                        }

                        composable("settings") {
                            SettingsNavHost()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeNavHost() {
    val homeNavController = rememberNavController()
    NavHost(homeNavController, startDestination = "home1") {
        repeat(10) { index ->
            val screenIndex = index + 1
            val route = "home$screenIndex"
            composable(route) {
                GenericScreen(
                    text = "Home $screenIndex"
                ) {
                    if (screenIndex < 10) {
                        homeNavController.navigate("home${screenIndex + 1}")
                    }
                }
            }
        }
    }
}

@Composable
fun ChatNavHost() {
    val chatNavController = rememberNavController()
    NavHost(chatNavController, startDestination = "chat1") {
        repeat(10) { index ->
            val screenIndex = index + 1
            val route = "chat$screenIndex"
            composable(route) {
                GenericScreen(
                    text = "Chat $screenIndex"
                ) {
                    if (screenIndex < 10) {
                        chatNavController.navigate("chat${screenIndex + 1}")
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsNavHost() {
    val settingsNavController = rememberNavController()
    NavHost(settingsNavController, startDestination = "settings1") {
        repeat(10) { index ->
            val screenIndex = index + 1
            val route = "settings$screenIndex"
            composable(route) {
                GenericScreen(
                    text = "Settings $screenIndex"
                ) {
                    if (screenIndex < 10) {
                        settingsNavController.navigate("settings${screenIndex + 1}")
                    }
                }
            }
        }
    }
}



@Composable
fun GenericScreen(text: String,
                  onNextClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = text)
        Spacer(Modifier.height(16.dp))
        Button(onClick = onNextClick) {
            Text("Next")
        }
    }
}



data class BottomNavigationItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)

val items = listOf(
    BottomNavigationItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    BottomNavigationItem(
        title = "Chat",
        selectedIcon = Icons.Filled.Email,
        unselectedIcon = Icons.Outlined.Email
    ),
    BottomNavigationItem(
        title = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )
)










































