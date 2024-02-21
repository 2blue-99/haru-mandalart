package com.coldblue.mandalart.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.mandalart.MandaScreen

const val mandaRoute = "Manda"
fun NavController.navigateToManda(navOptions: NavOptions){
    this.navigate(mandaRoute, navOptions)
}

fun NavGraphBuilder.mandaScreen(){
    composable(route = mandaRoute){
//        val gap = it.arguments?.getString("key")
        MandaScreen()
    }
}