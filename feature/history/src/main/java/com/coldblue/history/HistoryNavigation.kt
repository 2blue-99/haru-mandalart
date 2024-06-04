package com.coldblue.history

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val historyRoute = "History"
fun NavController.navigateToHistory(navOptions: NavOptions? = null) {
    this.navigate(historyRoute, navOptions)
}

fun NavGraphBuilder.historyScreen(
    navigateToSetting: () -> Unit,
    navigateToTodoEdit: (Int, String, String, String) -> Unit
) {
    composable(route = historyRoute) {
        HistoryScreen(
//            navigateToSetting = navigateToSetting,
//            navigateToTodoEdit = navigateToTodoEdit
        )
    }
}