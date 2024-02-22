package com.coldblue.mandalart.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.data.navires.mandaRoute
import com.coldblue.mandalart.MandaScreen

fun NavController.navigateToManda(navOptions: NavOptions){
    this.navigate(mandaRoute, navOptions)
}

fun NavGraphBuilder.mandaScreen(){
    composable(route = mandaRoute){
        MandaScreen()
    }
}