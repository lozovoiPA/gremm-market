package com.implus.gremmarket.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.implus.gremmarket.ui.components.HomeTopBar
import kotlinx.serialization.Serializable

@Serializable
object Home
@Serializable
object Account


@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Home) {
        composable<Home> {
            HomeScreen(
                topBar = {
                    HomeTopBar(onAccountProfilePictureClick = {
                        navController.navigate(route = Account)
                    })
                }
            )
        }
        composable<Account> { AccountScreen() }
    }
}