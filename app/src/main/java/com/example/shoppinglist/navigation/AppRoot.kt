package com.example.shoppinglist.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.launch
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.shoppinglist.screens.HomeScreen
import com.example.shoppinglist.screens.ProfileScreen
import com.example.shoppinglist.screens.SettingsScreen
import com.example.shoppinglist.viewmodel.ShoppingViewModel

object Routes {
    const val HOME = "home"
    const val PROFILE = "profile"
    const val SETTINGS = "settings"
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AppRoot() {
    val navController = rememberAnimatedNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val vm: ShoppingViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val topBarTitle = when {
        currentRoute == Routes.PROFILE -> "Profile"
        currentRoute == Routes.SETTINGS -> "Settings"
        else -> "Shopping List"
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menu", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(16.dp))
                NavigationDrawerItem(
                    label = { Text("Settings") },
                    selected = currentRoute == Routes.SETTINGS,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigateSingleTopTo(Routes.SETTINGS)
                    },
                    icon = { Icon(Icons.Filled.Settings, contentDescription = null) }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(topBarTitle) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    },
                )
            },
            bottomBar = {
                NavigationBar {
                    val items = listOf(
                        Triple(Routes.HOME, "Shopping List", Icons.Filled.Home),
                        Triple(Routes.PROFILE, "Profile", Icons.Filled.Person)
                    )
                    items.forEach { (route, label, icon) ->
                        NavigationBarItem(
                            selected = currentRoute == route,
                            onClick = {

                                navController.navigateSingleTopTo(route)
                            },
                            icon = { Icon(icon, contentDescription = label) },
                            label = { Text(label) }
                        )
                    }
                }
            }
        ) { inner ->
            Box(Modifier.padding(inner)) {
                AppNavHost(navController, vm)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AppNavHost(navController: NavHostController,  vm: ShoppingViewModel) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) { HomeScreen(vm) }

        composable(
            route = Routes.PROFILE) { ProfileScreen() }

        composable(Routes.SETTINGS) { SettingsScreen(onClearAll = { vm.clearAll() }) }
    }
}

private fun NavHostController.navigateSingleTopTo(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState   = true
    }
}
