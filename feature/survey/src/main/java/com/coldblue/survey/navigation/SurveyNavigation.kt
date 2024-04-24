package com.coldblue.survey.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.survey.SurveyScreen

const val surveyRoute = "Survey"

fun NavController.navigateToSurvey(navOptions: NavOptions? = null){
    this.navigate(surveyRoute, navOptions)
}

fun NavGraphBuilder.surveyScreen(){
    composable(route = surveyRoute){
        SurveyScreen()
    }
}