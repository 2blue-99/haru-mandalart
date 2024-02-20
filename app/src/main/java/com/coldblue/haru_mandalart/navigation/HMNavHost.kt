package com.coldblue.haru_mandalart.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.coldblue.haru_mandalart.ui.HMAppState
import com.coldblue.history.navigation.historyScreen
import com.coldblue.login.navigation.loginScreen
import com.coldblue.mandalart.navigation.mandaScreen
import com.coldblue.setting.navigation.settingScreen
import com.coldblue.todo.navigation.todoScreen
import com.coldblue.tutorial.navigation.tutorialScreen

@Composable
fun HMNavHost(
    modifier: Modifier,
    appState: HMAppState
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = HMDestination.TODO.titleTextId,
        modifier = modifier
    ){
        mandaScreen()
        loginScreen()
        settingScreen()
        todoScreen(onClick = appState::navigationToDestination)
        tutorialScreen()
        historyScreen()
    }
}