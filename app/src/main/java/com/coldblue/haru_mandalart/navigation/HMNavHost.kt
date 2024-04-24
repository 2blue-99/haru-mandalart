package com.coldblue.haru_mandalart.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.coldblue.haru_mandalart.ui.HMAppState
import com.coldblue.history.navigation.historyScreen
import com.coldblue.login.navigation.loginScreen
import com.coldblue.mandalart.navigation.mandaRoute
import com.coldblue.mandalart.navigation.mandaScreen
import com.coldblue.mandalart.navigation.navigateToManda
import com.coldblue.notice.navigation.navigateToNotice
import com.coldblue.notice.navigation.noticeScreen
import com.coldblue.setting.navigation.navigateToSetting
import com.coldblue.setting.navigation.settingScreen
import com.coldblue.todo.navigation.navigateToTodo
import com.coldblue.todo.navigation.navigateToTodoEdit
import com.coldblue.todo.navigation.todoEditScreen
import com.coldblue.todo.navigation.todoRoute
import com.coldblue.todo.navigation.todoScreen
import com.coldblue.tutorial.navigation.tutorialScreen

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
        loginScreen(navigateToTodo = navController::navigateToTodo)
//        todoScreen(navigateToTodoEdit = navController::navigateToTodoEdit)
        todoEditScreen(onDismissRequest = navController::popBackStack)
//        tutorialScreen(navigateToManda = navController::navigateToManda)
        mandaScreen(
            navigateToSetting = navController::navigateToSetting
        )
//        historyScreen(
//            navigateToSetting = navController::navigateToSetting,
//            navigateToTodoEdit = navController::navigateToTodoEdit
//        )
        settingScreen(
            navigateToNotice = navController::navigateToNotice
        )
        noticeScreen()

    }
}