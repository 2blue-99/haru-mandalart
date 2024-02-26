package com.coldblue.mandalart.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.data.navi.Route
import com.coldblue.mandalart.MandaScreen

fun NavController.navigateToManda(navOptions: NavOptions? = null){
    this.navigate(Route.manda, navOptions)
}

fun NavGraphBuilder.mandaScreen(){
    composable(route = Route.manda){
        MandaScreen()
    }
}