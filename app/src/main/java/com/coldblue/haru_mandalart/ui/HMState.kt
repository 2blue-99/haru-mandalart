package com.coldblue.haru_mandalart.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.coldblue.data.navi.Route
import com.coldblue.haru_mandalart.navigation.TopLevelDestination
import com.coldblue.history.navigation.navigateToHistory
import com.coldblue.mandalart.navigation.navigateToManda
import com.coldblue.todo.navigation.navigateToTodo

@Composable
fun rememberHMState(
    navController: NavHostController = rememberNavController()
): HMAppState =
    remember(navController) {
        HMAppState(navController)
    }

@Stable
class HMAppState(
    val navController: NavHostController
) {

    val bottomNavDestination: List<TopLevelDestination> =
        listOf(TopLevelDestination.MANDA, TopLevelDestination.TODO, TopLevelDestination.HISTORY)

    val currentLocation: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    @Composable
    fun checkBottomNavBar(): Boolean {
        currentLocation.let {
            return when(it?.route){
                Route.todo, Route.manda, Route.history-> true
                else -> false
            }
        }
    }

    @Composable
    fun checkTopBar(): Boolean {
        currentLocation.let {
            return when(it?.route){
                Route.setting -> true
                else -> false
            }
        }
    }

    fun popBackStack(){ navController.popBackStack() }

    fun navigateToTopLevelDestination(route: String) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when (route) {
            Route.history -> navController.navigateToHistory(navOptions)
            Route.manda -> navController.navigateToManda(navOptions)
            Route.todo -> navController.navigateToTodo(navOptions)
        }
    }
}