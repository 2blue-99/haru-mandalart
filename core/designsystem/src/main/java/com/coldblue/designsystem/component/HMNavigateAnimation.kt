package com.coldblue.designsystem.component

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

object HMNavigateAnimation {

    fun AnimatedContentTransitionScope<NavBackStackEntry>.noneExit(): ExitTransition {
        return ExitTransition.None
    }
    fun AnimatedContentTransitionScope<NavBackStackEntry>.noneEnter(): EnterTransition {
        return EnterTransition.None
    }
    fun AnimatedContentTransitionScope<NavBackStackEntry>.slideToRightEnter(): EnterTransition {
        return slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.End,
            tween(400)
        )
    }
    fun AnimatedContentTransitionScope<NavBackStackEntry>.slideToLeftEnter(): EnterTransition {
        return slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Start,
            tween(400)
        )
    }
    fun AnimatedContentTransitionScope<NavBackStackEntry>.slideToRightExit(): ExitTransition {
        return slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.End,
            tween(400)
        )
    }
    fun AnimatedContentTransitionScope<NavBackStackEntry>.slideToLeftExit(): ExitTransition {
        return slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Start,
            tween(400)
        )
    }
    suspend fun Animatable<Float, AnimationVector1D>.fadeInScreen(){
        animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearEasing
            )
        )
    }

    suspend fun Animatable<Float, AnimationVector1D>.fadeOutScreen(){
        animateTo(
            targetValue = 0f,
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearEasing
            )
        )
    }
}