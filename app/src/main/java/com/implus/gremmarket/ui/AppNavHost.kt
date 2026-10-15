package com.implus.gremmarket.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.implus.gremmarket.AccountScreen
import com.implus.gremmarket.DeviceAppsScreen

/* // Можно делать таргеты навхоста в виде сериализуемых классов.
// Это удобно если например экземпляры классов могут изменять наполнение страницы.
// Например если это страницы пользователей. То Навхост может передавать объект.
@Serializable
object Home
@Serializable
object Account
@Serializable
object DeviceApps
*/

enum class Destination(
    val route: String,
    val label: String
) {
    HOME_GAMES("home-games", "Игры"),
    ACCOUNT("account", "Аккаунт"),
    DEVICEAPPS_GENERAL("device-apps-general", "Общие сведения"),
    DEVICEAPPS_APPS("device-apps-management", "Управление")
}

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: Destination = Destination.HOME_GAMES,
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val currentGraph = backStackEntry?.destination?.hierarchy?.lastOrNull()?.route

    NavHost(
        navController,
        route = "main",
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen()
        }
        composable(
            route = "account",
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) {
            AccountScreen()
        }
        navigation(route = "device-apps", startDestination = "device-apps-general") {
            composable("device-apps-general") {
                DeviceAppsScreen()
            }
            composable("device-apps-management") {
                DeviceAppsScreen()
            }
        }
    }
}