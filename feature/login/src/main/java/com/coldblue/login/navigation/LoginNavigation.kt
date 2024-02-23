package com.coldblue.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.data.navi.Route
import com.coldblue.login.LoginScreen

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    this.navigate(Route.login, navOptions)
}

fun NavGraphBuilder.loginScreen(navigateToTodo: () -> Unit) {
    composable(route = Route.login) {
        LoginScreen()
    }
}