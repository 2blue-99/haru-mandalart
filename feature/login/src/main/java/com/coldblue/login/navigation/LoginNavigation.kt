package com.coldblue.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.login.LoginScreen

const val loginRoute = "Login"
fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    this.navigate(loginRoute, navOptions)
}

fun NavGraphBuilder.loginScreen(navigateToExplain: () -> Unit) {
    composable(route = loginRoute) {
        LoginScreen()
    }
}