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
import com.coldblue.haru_mandalart.navigation.HMDestination
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

    val bottomNavDestination: List<HMDestination> =
        listOf(HMDestination.MANDA, HMDestination.TODO, HMDestination.HISTORY)

    val currentLocation: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    @Composable
    fun checkBottomNavBar(): Boolean {
        currentLocation.let {
            return when(it?.route){
                HMDestination.MANDA.titleTextId -> true
                HMDestination.TODO.titleTextId -> true
                HMDestination.HISTORY.titleTextId -> true
                else -> false
            }
        }
    }

    @Composable
    fun checkTopBar(): Boolean {
        currentLocation.let {
            return when(it?.route){
                HMDestination.SETTING.titleTextId -> true
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
            HMDestination.HISTORY.titleTextId -> navController.navigateToHistory(navOptions)
            HMDestination.LOGIN.titleTextId -> navController.navigateToLogin(navOptions)
            HMDestination.MANDA.titleTextId -> navController.navigateToManda(navOptions)
            HMDestination.SETTING.titleTextId -> navController.navigateToSetting(navOptions)
            HMDestination.TODO.titleTextId -> navController.navigateToTodo(navOptions)
            HMDestination.TUTORIAL.titleTextId -> navController.navigateToTutorial(navOptions)
        }
    }
}