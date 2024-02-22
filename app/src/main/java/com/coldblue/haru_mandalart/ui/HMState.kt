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
import com.coldblue.haru_mandalart.navigation.TopLevelDestination
import com.coldblue.history.navigation.navigateToHistory
import com.coldblue.login.navigation.navigateToLogin
import com.coldblue.mandalart.navigation.navigateToManda
import com.coldblue.setting.navigation.navigateToSetting
import com.coldblue.todo.navigation.navigateToTodo
import com.coldblue.tutorial.navigation.navigateToTutorial

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
                TopLevelDestination.MANDA.titleTextId -> true
                TopLevelDestination.TODO.titleTextId -> true
                TopLevelDestination.HISTORY.titleTextId -> true
                else -> false
            }
        }
    }

    @Composable
    fun checkTopBar(): Boolean {
        currentLocation.let {
            return when(it?.route){
                TopLevelDestination.SETTING.titleTextId -> true
                else -> false
            }
        }
    }

    fun popBackStack(){ navController.popBackStack() }

    fun navigationToDestination(name: String) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when (name) {
            TopLevelDestination.HISTORY.titleTextId -> navController.navigateToHistory(navOptions)
            TopLevelDestination.LOGIN.titleTextId -> navController.navigateToLogin(navOptions)
            TopLevelDestination.MANDA.titleTextId -> navController.navigateToManda(navOptions)
            TopLevelDestination.SETTING.titleTextId -> navController.navigateToSetting(navOptions)
            TopLevelDestination.TODO.titleTextId -> navController.navigateToTodo(navOptions)
            TopLevelDestination.TUTORIAL.titleTextId -> navController.navigateToTutorial(navOptions)
        }
    }
}