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
import com.coldblue.mandalart.navigation.mandaRoute
import com.coldblue.mandalart.navigation.navigateToManda
import com.coldblue.notice.navigation.noticeRoute
import com.coldblue.setting.navigation.settingRoute
import com.coldblue.survey.navigation.surveyDetailRoute
import com.coldblue.survey.navigation.surveyRoute

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
    fun checkTopBar(): Pair<Boolean, String> {
        currentLocation.let {
            return when (it?.route) {
                settingRoute -> true to "설정"
                noticeRoute -> true to "공지사항"
                surveyRoute -> true to "기능 제안하기"
                "$surveyDetailRoute/{id}" -> true to "기능 제안하기"
                else -> false to ""
            }
        }
    }

    fun navigateToTopLevelDestination(route: String) {
        if (navController.currentDestination?.route != route) {
            navController.popBackStack()
            val navOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            when (route) {
                mandaRoute -> navController.navigateToManda(navOptions)
            }
        }
    }
}