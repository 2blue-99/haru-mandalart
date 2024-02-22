package com.coldblue.tutorial.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.data.navires.tutorialRoute
import com.coldblue.tutorial.TutorialScreen

fun NavController.navigateToTutorial(navOptions: NavOptions){
    this.navigate(tutorialRoute, navOptions)
}

fun NavGraphBuilder.tutorialScreen(navigateToManda: () -> Unit){
    composable(route = tutorialRoute){
        TutorialScreen{navigateToManda()}
    }
}