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
import com.coldblue.survey.SurveyWriteScreen

const val surveyRoute = "Survey"
const val surveyDetailRoute = "SurveyDetail"
const val surveyWriteRoute = "SurveyWrite"

fun NavController.navigateToSurvey(navOptions: NavOptions? = null) {
    this.navigate(surveyRoute, navOptions)
}

fun NavController.navigateToSurveyWrite(navOptions: NavOptions? = null) {
    this.navigate(surveyWriteRoute, navOptions)
}

fun NavController.navigateToSurveyDetail(id: Int, navOptions: NavOptions? = null) {
    this.navigate("$surveyDetailRoute/$id", navOptions)
}
fun NavGraphBuilder.surveyScreen(
    navigateToSurveyDetail: (id: Int) -> Unit,
    navigateToSurveyWrite: () -> Unit,
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
            navigateToSurveyWrite,
            navigateToBackStack,
        )
    }
}

fun NavGraphBuilder.surveyWriteScreen(
    navigateToBackStack: () -> Unit
) {
    composable(
        route = surveyWriteRoute,
        enterTransition = { slideToLeftEnter() },
        popExitTransition = { slideToRightExit() },
        exitTransition = { slideToLeftExit() },
        popEnterTransition = { slideToRightEnter() }
    ) {
        SurveyWriteScreen(
            navigateToBackStack,
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
