package com.coldblue.mandalart.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.designsystem.component.HMNavigateAnimation.slideToLeftExit
import com.coldblue.designsystem.component.HMNavigateAnimation.slideToRightEnter
import com.coldblue.mandalart.screen.MandaScreen

const val mandaRoute = "Manda"
fun NavController.navigateToManda(navOptions: NavOptions? = null){
    this.navigate(mandaRoute, navOptions)
}

fun NavGraphBuilder.mandaScreen(
    navigateToSetting: () -> Unit,
    navigateToHistory: () -> Unit
){
    composable(
        route = mandaRoute,
        exitTransition = { slideToLeftExit() },
        popEnterTransition = { slideToRightEnter() }
    ){
        MandaScreen(
            navigateToSetting = navigateToSetting,
            navigateToHistory = navigateToHistory
        )
    }
}

