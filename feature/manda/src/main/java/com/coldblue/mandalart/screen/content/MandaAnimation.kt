package com.coldblue.mandalart.screen.content

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.resetToBeginning
import com.coldblue.mandalart.screen.content.madalart.MandalartGestureController
import com.colddelight.mandalart.R

@Composable
fun MandaAnimation(
    showDone: Boolean,
    showCreate: Boolean,
    onDoneFinished: () -> Unit,
    onCreateFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    val doneComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.manda_done)
    )
    val createComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.manda_create)
    )

    val doneAni = rememberLottieAnimatable()
    val createAni = rememberLottieAnimatable()


    LaunchedEffect(showDone) {
        if (showDone && doneComposition != null) {
            doneAni.animate(
                composition = doneComposition,
                speed = 0.7f,
                clipSpec = LottieClipSpec.Frame(0, 1200),
                initialProgress = 0f
            )
            onDoneFinished()
        }
    }

    LaunchedEffect(showCreate) {
        if (showCreate && createComposition != null) {
            createAni.animate(
                composition = createComposition,
                speed = 0.7f,
                clipSpec = LottieClipSpec.Frame(0, 1200),
                initialProgress = 0f
            )
            createAni.resetToBeginning()
            onCreateFinished()
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (showDone) {
            LottieAnimation(
                composition = doneComposition,
                progress = { doneAni.progress },
                contentScale = ContentScale.FillHeight
            )
        }

        if (showCreate) {
            LottieAnimation(
                composition = createComposition,
                modifier = Modifier.scale(3f),
                progress = { createAni.progress },
                contentScale = ContentScale.FillHeight
            )
        }
    }
}

data class MandalartTransform(
    val scaleX: Float,
    val scaleY: Float,
    val translateX: Float,
    val translateY: Float
)

@Composable
fun rememberMandalartTransform(controller: MandalartGestureController): MandalartTransform {

    val dampingRatio = 0.8f // 클수록 스프링 효과 감소
    val stiffness = 1600f // 클수록 빨리 확대, 축소
    val animatedScaleX by animateFloatAsState(
        targetValue = controller.scaleX,
        animationSpec = spring(dampingRatio = dampingRatio, stiffness = stiffness),
        label = ""
    )

    val animatedScaleY by animateFloatAsState(
        targetValue = controller.scaleY,
        animationSpec = spring(dampingRatio = dampingRatio, stiffness = stiffness),
        label = ""
    )

    val animatedTranslateX by animateFloatAsState(
        targetValue = controller.translateX,
        label = ""
    )

    val animatedTranslateY by animateFloatAsState(
        targetValue = controller.translateY,
        label = ""
    )

    return MandalartTransform(
        scaleX = animatedScaleX,
        scaleY = animatedScaleY,
        translateX = animatedTranslateX,
        translateY = animatedTranslateY
    )
}