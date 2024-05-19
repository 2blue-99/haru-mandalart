package com.coldblue.setting.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.setting.SettingScreen

const val settingRoute = "Setting"
fun NavController.navigateToSetting(navOptions: NavOptions? = null) {
    this.navigate(settingRoute, navOptions)
}


fun NavGraphBuilder.settingScreen(
    navigateToNotice: () -> Unit,
    navigateToSurvey: () -> Unit,
    navigateToBackStack: () -> Unit,
) {
    composable(
        route = settingRoute,
        enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(400)
            )
        },
        popEnterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(400)
            )
        }
    ) {
        SettingScreen(
            navigateToNotice,
            navigateToSurvey,
            navigateToBackStack
        )
    }
}


