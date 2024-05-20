package com.coldblue.notice.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.designsystem.component.HMAnimation.slideToLeft
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
        enterTransition = { slideToLeft() },
    ){
        NoticeScreen(
            navigateToBackStack = navigateToBackStack
        )
    }
}
