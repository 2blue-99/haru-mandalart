package com.coldblue.haru_mandalart.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.coldblue.haru_mandalart.navigation.HMDestination
import com.coldblue.history.navigation.navigateToHistory

@Composable
fun rememberHMState(
    navController: NavHostController = rememberNavController()
): HMAppState =
    remember(navController){
        HMAppState(navController)
    }

@Stable
class HMAppState(
    val navController: NavHostController
){
    val destination: List<HMDestination> = HMDestination.entries

    val currentLocation: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    fun navigationToDestination(name: String){
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when(name){
            HMDestination.HISTORY.titleTextId -> navController.navigateToHistory(navOptions)
            HMDestination.LOGIN.titleTextId -> navController.navigateToHistory(navOptions)
            HMDestination.MANDALART.titleTextId -> navController.navigateToHistory(navOptions)
            HMDestination.SETTING.titleTextId -> navController.navigateToHistory(navOptions)
            HMDestination.TODO.titleTextId -> navController.navigateToHistory(navOptions)
            HMDestination.TUTORIAL.titleTextId -> navController.navigateToHistory(navOptions)
        }
    }
}