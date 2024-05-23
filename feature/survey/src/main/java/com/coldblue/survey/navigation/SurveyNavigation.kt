package com.coldblue.survey.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.coldblue.designsystem.component.HMNavigateAnimation.slideToLeftEnter
import com.coldblue.designsystem.component.HMNavigateAnimation.slideToLeftExit
import com.coldblue.designsystem.component.HMNavigateAnimation.slideToRightEnter
import com.coldblue.designsystem.component.HMNavigateAnimation.slideToRightExit
import com.coldblue.survey.SurveyDetailScreen
import com.coldblue.survey.SurveyScreen

const val surveyRoute = "Survey"
const val surveyDetailRoute = "SurveyDetail"

fun NavController.navigateToSurvey(navOptions: NavOptions? = null) {
    this.navigate(surveyRoute, navOptions)
}

fun NavController.navigateToSurveyDetail(id: Int, navOptions: NavOptions? = null) {
    this.navigate("$surveyDetailRoute/$id", navOptions)
}

fun NavGraphBuilder.surveyScreen(
    navigateToSurveyDetail: (id: Int) -> Unit,
    navigateToBackStack: () -> Unit
) {
    composable(
        route = surveyRoute,
        enterTransition = { slideToLeftEnter() },
        popExitTransition = { slideToRightExit() },
        exitTransition = { slideToLeftExit() },
        popEnterTransition = { slideToRightEnter() }
    ) {
        SurveyScreen(
            navigateToSurveyDetail,
            navigateToBackStack
        )
    }
}

fun NavGraphBuilder.surveyDetailScreen(
    navigateToBackStack: () -> Unit
) {
    composable(route = "$surveyDetailRoute/{id}",
        arguments = listOf(
            navArgument("id") { type = NavType.IntType }
        ),
        enterTransition = { slideToLeftEnter() },
        exitTransition = { slideToRightExit() }
    ) {
        SurveyDetailScreen(
            navigateToBackStack = navigateToBackStack
        )
    }
}
