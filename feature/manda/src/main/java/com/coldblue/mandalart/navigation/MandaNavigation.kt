package com.coldblue.mandalart.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.coldblue.mandalart.screen.MandaScreen

const val mandaRoute = "Manda"
fun NavController.navigateToManda(navOptions: NavOptions? = null){
    this.navigate(mandaRoute, navOptions)
}

fun NavGraphBuilder.mandaScreen(
    navigateToSetting: () -> Unit
){
    composable(
        route = mandaRoute,
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(400)
            )
        }
    ){
        MandaScreen(
            navigateToSetting = navigateToSetting,
        )
    }
}

