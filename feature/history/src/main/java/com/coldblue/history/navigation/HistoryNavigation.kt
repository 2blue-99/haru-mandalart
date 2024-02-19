package com.coldblue.history.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

const val historyRoute = "History"
fun NavController.navigateToHistory(navOptions: NavOptions){
    this.navigate(historyRoute, navOptions)
}