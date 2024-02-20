package com.coldblue.tutorial.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.tutorial.TutorialScreen

const val tutorialRoute = "Tutorial"
fun NavController.navigateToTutorial(navOptions: NavOptions){
    this.navigate(tutorialRoute, navOptions)
}

fun NavGraphBuilder.tutorialScreen(){
    composable(route = tutorialRoute){
        TutorialScreen()
    }
}