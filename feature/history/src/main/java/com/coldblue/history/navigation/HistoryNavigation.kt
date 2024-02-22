package com.coldblue.history.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.data.navires.historyRoute
import com.coldblue.history.HistoryScreen

fun NavController.navigateToHistory(navOptions: NavOptions){
    this.navigate(historyRoute, navOptions)
}

fun NavGraphBuilder.historyScreen(navigateToSetting: () -> Unit){
    composable(route = historyRoute){
        HistoryScreen{navigateToSetting()}
    }
}