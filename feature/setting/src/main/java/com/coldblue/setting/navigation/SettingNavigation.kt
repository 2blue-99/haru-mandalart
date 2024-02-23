package com.coldblue.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.data.navi.Route
import com.coldblue.setting.SettingScreen

fun NavController.navigateToSetting(navOptions: NavOptions? = null){
    this.navigate(Route.setting, navOptions)
}

fun NavGraphBuilder.settingScreen(){
    composable(route = Route.setting){
        SettingScreen()
    }
}