package com.coldblue.history.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.history.HistoryScreen

const val historyRoute = "History"
fun NavController.navigateToHistory(navOptions: NavOptions? = null) {
    this.popBackStack()
    this.navigate(historyRoute, navOptions)
}

fun NavGraphBuilder.historyScreen(
    navigateToSetting: () -> Unit,
    navigateToTodoEdit: (Int, String, String) -> Unit
) {
    composable(route = historyRoute) {
        HistoryScreen(
            navigateToSetting = navigateToSetting,
            navigateToTodoEdit = navigateToTodoEdit
        )
    }
}