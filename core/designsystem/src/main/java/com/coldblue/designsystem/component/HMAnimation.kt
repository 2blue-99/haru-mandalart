package com.coldblue.designsystem.component

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

object HMAnimation {
    fun AnimatedContentTransitionScope<NavBackStackEntry>.slideToRight(): EnterTransition {
        return slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.End,
            tween(400)
        )
    }

    fun AnimatedContentTransitionScope<NavBackStackEntry>.slideToLeft(): EnterTransition {
        return slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Start,
            tween(400)
        )
    }
}