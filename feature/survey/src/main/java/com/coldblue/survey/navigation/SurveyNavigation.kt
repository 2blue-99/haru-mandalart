package com.coldblue.survey.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.coldblue.survey.SurveyDetailScreen
import com.coldblue.survey.SurveyScreen
import com.orhanobut.logger.Logger

const val surveyRoute = "Survey"
const val surveyDetailRoute = "SurveyDetail"

fun NavController.navigateToSurvey(navOptions: NavOptions? = null) {
    Logger.d("기능제안으로 감")
    this.navigate(surveyRoute, navOptions)
}

fun NavController.navigateToSurveyDetail(id:Int,navOptions: NavOptions? = null) {
    this.navigate("$surveyDetailRoute/$id", navOptions)
}

fun NavGraphBuilder.surveyScreen(navigateToSurveyDetail: (id: Int) -> Unit) {
    composable(route = surveyRoute) {
        SurveyScreen(navigateToSurveyDetail)
    }
}

fun NavGraphBuilder.surveyDetailScreen() {
    composable(route = "$surveyDetailRoute/{id}",
        arguments = listOf(
            navArgument("id"){type=NavType.IntType}
        )
    ) {
        SurveyDetailScreen()
    }
}

