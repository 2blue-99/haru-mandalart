package com.coldblue.tutorial

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.component.HMButton
import com.coldblue.designsystem.component.HMNavigateAnimation.fadeInScreen
import com.coldblue.designsystem.component.HMNavigateAnimation.fadeOutScreen
import com.coldblue.designsystem.component.HMPagerIndicator
import com.coldblue.designsystem.component.calculateCurrentOffsetForPage
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TutorialScreen(
    offset: Offset = Offset.Zero,
    size: IntSize = IntSize.Zero,
    onFinished: () -> Unit
) {

    val explainList = HistoryUtil.getExplainList()
    val pagerState = rememberPagerState(pageCount = {4})
    val coroutineScope = rememberCoroutineScope()
    val backGroundAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit){
        backGroundAlpha.fadeInScreen()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(backGroundAlpha.value)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.6f)
                .background(Color.Black),
            beyondBoundsPageCount = 0
        ) {page ->
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = pagerState.calculateCurrentOffsetForPage(page)
                        translationX = pageOffset * size.width
                        alpha = 1 - pageOffset.absoluteValue
                    }
                    .fillMaxSize()
                    .clickable(false) {}
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset {
                            IntOffset(
                                x = offset.x.toInt(),
                                y = offset.y.toInt() + size.height
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = explainList[pagerState.currentPage],
                        style = HmStyle.text16,
                        color = HMColor.Background
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(top = 30.dp)
                .align(Alignment.TopCenter)
        ) {
            HMPagerIndicator(
                pageCount = pagerState.pageCount,
                currentPage = pagerState.currentPage,
                targetPage = pagerState.targetPage,
                currentPageOffsetFraction = pagerState.currentPageOffsetFraction,
                indicatorColor = HMColor.Background
            )
        }

        Box(
            modifier = Modifier
                .padding(top = 20.dp, end = 20.dp)
                .align(Alignment.TopEnd),
        ){
            Icon(
                imageVector = Icons.Default.Close,
                tint = HMColor.Background,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopCenter)
                    .clickable { onFinished() },
                contentDescription = "finish"
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp)
                .padding(horizontal = 30.dp),
        ) {
            HMButton(
                text = if (pagerState.currentPage == 3) stringResource(id = R.string.tutorial_finish) else stringResource(id = R.string.tutorial_next),
                clickableState = true
            ) {
                coroutineScope.launch {
                    if (pagerState.currentPage == 3) {
                        backGroundAlpha.fadeOutScreen()
                        onFinished()
                    } else {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TutorialPreview(){
    TutorialScreen {

    }
}