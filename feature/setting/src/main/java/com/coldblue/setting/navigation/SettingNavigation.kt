package com.coldblue.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.data.navires.settingRoute
import com.coldblue.setting.SettingScreen

fun NavController.navigateToSetting(navOptions: NavOptions){
    this.navigate(settingRoute, navOptions)
}

fun NavGraphBuilder.settingScreen(){
    composable(route = settingRoute){
        SettingScreen()
    }
}