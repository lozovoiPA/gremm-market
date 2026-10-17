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
    ROOT("root", ""),
    HOME_GAMES("home-games", "Игры"),
    ACCOUNT("account", "Аккаунт"),
    DEVICEAPPS("device-apps", ""),
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
        route = Destination.ROOT.route,
        startDestination = startDestination.route
    ) {
        composable(Destination.HOME_GAMES.route) {
            HomeScreen()
        }
        composable(
            route = Destination.ACCOUNT.route,
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
        navigation(route = Destination.DEVICEAPPS.route, startDestination = Destination.DEVICEAPPS_GENERAL.route) {
            composable(Destination.DEVICEAPPS_GENERAL.route) {
                DeviceAppsScreen()
            }
            composable(Destination.DEVICEAPPS_APPS.route) {
                DeviceAppsScreen()
            }
        }
//        composable(
//            route = Destination.DEVICEAPPS.route
//        ) {
//            DeviceAppsScreen()
//        }
    }
}