package com.coldblue.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.data.navires.loginRoute
import com.coldblue.login.LoginScreen

fun NavController.navigateToLogin(navOptions: NavOptions) {
    this.navigate(loginRoute, navOptions)
}

fun NavGraphBuilder.loginScreen(navigateToTodo: () -> Unit) {
    composable(route = loginRoute) {
        LoginScreen(navigateToTodo = { navigateToTodo() } )
    }
}