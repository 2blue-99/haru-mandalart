package com.coldblue.explain.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.explain.ExplainScreen

const val explainRoute = "Explain"

fun NavController.navigateToExplain(navOptions: NavOptions? = null){
    this.navigate(explainRoute, navOptions)
}

fun NavGraphBuilder.explainScreen(navigateToExplain: () -> Unit){
    composable(route = explainRoute){
        ExplainScreen()
    }
}