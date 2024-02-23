package com.coldblue.history.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.data.navi.Route
import com.coldblue.history.HistoryScreen

fun NavController.navigateToHistory(navOptions: NavOptions? = null){
    this.navigate(Route.history, navOptions)
}

fun NavGraphBuilder.historyScreen(navigateToSetting: () -> Unit){
    composable(route = Route.history){
        HistoryScreen{navigateToSetting()}
    }
}