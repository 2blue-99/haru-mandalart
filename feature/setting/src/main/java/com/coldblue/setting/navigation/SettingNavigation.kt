package com.coldblue.setting.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.designsystem.component.HMAnimation.slideToLeft
import com.coldblue.designsystem.component.HMAnimation.slideToRight
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
        enterTransition = { slideToLeft() },
        popEnterTransition = { slideToRight() }
    ) {
        SettingScreen(
            navigateToNotice,
            navigateToSurvey,
            navigateToBackStack
        )
    }
}


