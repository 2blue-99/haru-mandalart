package com.coldblue.tutorial.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.data.navi.Route
import com.coldblue.tutorial.TutorialScreen

fun NavController.navigateToTutorial(navOptions: NavOptions? = null){
    this.navigate(Route.tutorial, navOptions)
}

fun NavGraphBuilder.tutorialScreen(navigateToManda: () -> Unit){
    composable(route = Route.tutorial){
        TutorialScreen{navigateToManda()}
    }
}