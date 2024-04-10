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
import com.coldblue.history.navigation.historyRoute
import com.coldblue.history.navigation.navigateToHistory
import com.coldblue.mandalart.navigation.mandaRoute
import com.coldblue.mandalart.navigation.navigateToManda
import com.coldblue.setting.navigation.settingRoute
import com.coldblue.todo.navigation.navigateToTodo
import com.coldblue.todo.navigation.todoRoute

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

//    val bottomNavDestination: List<TopLevelDestination> =
//        listOf(TopLevelDestination.MANDA, TopLevelDestination.TODO, TopLevelDestination.HISTORY)

    val currentLocation: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

//    @Composable
//    fun checkBottomNavBar(): Boolean {
//        currentLocation.let {
//            return when(it?.route){
//                todoRoute, mandaRoute, historyRoute-> true
//                else -> false
//            }
//        }
//    }

    @Composable
    fun checkTopBar(): Boolean {
        currentLocation.let {
            return when(it?.route){
                settingRoute -> true
                else -> false
            }
        }
    }

    fun popBackStack(){ navController.popBackStack() }

    fun navigateToTopLevelDestination(route: String) {
        if(navController.currentDestination?.route != route){
            navController.popBackStack()
            val navOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            when (route) {
                historyRoute -> navController.navigateToHistory(navOptions)
                mandaRoute -> navController.navigateToManda(navOptions)
                todoRoute -> navController.navigateToTodo(navOptions)
            }
        }
    }
}