package com.coldblue.mandalart.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.mandalart.screen.MandaScreen

const val mandaRoute = "Manda"
fun NavController.navigateToManda(navOptions: NavOptions? = null){
    this.navigate(mandaRoute, navOptions)
}

fun NavGraphBuilder.mandaScreen(
    navigateToSetting: () -> Unit,
){
    composable(route =mandaRoute){
        MandaScreen(
            navigateToSetting = navigateToSetting,
        )
    }
}