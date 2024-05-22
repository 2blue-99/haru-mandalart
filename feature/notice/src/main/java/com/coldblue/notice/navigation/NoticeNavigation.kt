package com.coldblue.notice.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.designsystem.component.HMNavigateAnimation.slideToLeftEnter
import com.coldblue.designsystem.component.HMNavigateAnimation.slideToRightExit
import com.coldblue.notice.NoticeScreen

const val noticeRoute = "Notice"

fun NavController.navigateToNotice(navOptions: NavOptions? = null){
    this.navigate(noticeRoute, navOptions)
}

fun NavGraphBuilder.noticeScreen(
    navigateToBackStack: () -> Unit
){
    composable(
        route = noticeRoute,
        enterTransition = { slideToLeftEnter() },
        exitTransition = { slideToRightExit() },
    ){
        NoticeScreen(
            navigateToBackStack = navigateToBackStack
        )
    }
}
