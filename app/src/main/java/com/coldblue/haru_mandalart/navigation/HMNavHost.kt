package com.coldblue.haru_mandalart.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.coldblue.explain.navigation.navigateToExplain
import com.coldblue.haru_mandalart.ui.HMAppState
import com.coldblue.login.navigation.loginScreen
import com.coldblue.mandalart.navigation.mandaRoute
import com.coldblue.mandalart.navigation.mandaScreen
import com.coldblue.notice.navigation.navigateToNotice
import com.coldblue.notice.navigation.noticeScreen
import com.coldblue.setting.navigation.navigateToSetting
import com.coldblue.setting.navigation.settingScreen
import com.coldblue.survey.navigation.navigateToSurvey
import com.coldblue.survey.navigation.navigateToSurveyDetail
import com.coldblue.survey.navigation.surveyDetailScreen
import com.coldblue.survey.navigation.surveyScreen

@Composable
fun HMNavHost(
    modifier: Modifier,
    appState: HMAppState,
    startDestination: String = mandaRoute
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        loginScreen(
            navigateToExplain = navController::navigateToExplain
        )
        mandaScreen(
            navigateToSetting = navController::navigateToSetting
        )
//        historyScreen(
//            navigateToSetting = navController::navigateToSetting,
//            navigateToTodoEdit = navController::navigateToTodoEdit
//        )
        settingScreen(
            navigateToNotice = navController::navigateToNotice,
            navigateToSurvey = navController::navigateToSurvey,
            navigateToBackStack = navController::popBackStack
        )
        noticeScreen(
            navigateToBackStack = navController::popBackStack
        )
        surveyScreen(
            navigateToSurveyDetail = navController::navigateToSurveyDetail,
            navigateToBackStack = navController::popBackStack
        )
        surveyDetailScreen(
            navigateToBackStack = navController::popBackStack
        )

    }
}