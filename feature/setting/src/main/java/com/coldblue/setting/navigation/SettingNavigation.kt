package com.coldblue.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.setting.NoticeScreen
import com.coldblue.setting.SettingScreen

const val settingRoute = "Setting"
const val noticeRoute = "Notice"
fun NavController.navigateToSetting(navOptions: NavOptions? = null){
    this.navigate(settingRoute, navOptions)
}

fun NavController.navigateToNotice(navOptions: NavOptions? = null){
    this.navigate(noticeRoute, navOptions)
}

fun NavGraphBuilder.settingScreen(
    navigateToNotice: () -> Unit,
){
    composable(route = settingRoute){
        SettingScreen(navigateToNotice)
    }
}


fun NavGraphBuilder.noticeScreen(){
    composable(route = noticeRoute){
        NoticeScreen()
    }
}