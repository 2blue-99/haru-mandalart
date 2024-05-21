package com.coldblue.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.coldblue.designsystem.component.HMNavigateAnimation.slideToLeftEnter
import com.coldblue.designsystem.component.HMNavigateAnimation.slideToLeftExit
import com.coldblue.designsystem.component.HMNavigateAnimation.slideToRightEnter
import com.coldblue.designsystem.component.HMNavigateAnimation.slideToRightExit
import com.coldblue.setting.SettingScreen

const val settingRoute = "Setting"
fun NavController.navigateToSetting(navOptions: NavOptions? = null) {
    val gap = navOptions{ restoreState = true }
    this.navigate(settingRoute, gap)
}


fun NavGraphBuilder.settingScreen(
    navigateToNotice: () -> Unit,
    navigateToSurvey: () -> Unit,
    navigateToBackStack: () -> Unit,
) {
    composable(
        route = settingRoute,
        enterTransition = { slideToLeftEnter() },
        exitTransition = { slideToLeftExit() },
        popEnterTransition = { slideToRightEnter() },
        popExitTransition = {slideToRightExit()}
    ) {
        SettingScreen(
            navigateToNotice,
            navigateToSurvey,
            navigateToBackStack
        )
    }
}


