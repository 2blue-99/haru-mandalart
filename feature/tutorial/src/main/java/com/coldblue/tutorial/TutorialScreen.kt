package com.coldblue.tutorial

import android.support.annotation.DrawableRes
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.component.HMButton
import com.coldblue.designsystem.component.HMNavigateAnimation.fadeInScreen
import com.coldblue.designsystem.component.HMNavigateAnimation.fadeOutScreen
import com.coldblue.designsystem.component.HMPagerIndicator
import com.coldblue.designsystem.component.calculateCurrentOffsetForPage
import com.coldblue.designsystem.iconpack.TutorialArrow
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TutorialScreen(
    titleOffset: Offset = Offset.Zero,
    mandaOffset: Offset = Offset.Zero,
    todoOffset: Offset = Offset.Zero,
    size: IntSize = IntSize.Zero,
    onFinished: () -> Unit,
) {

    val textList = TutorialUtil.getTextList()
    val imageList = TutorialUtil.getImageList()
    val pagerState = rememberPagerState(pageCount = {4})
    val coroutineScope = rememberCoroutineScope()
    val backGroundAlpha = remember { Animatable(0f) }
    var imageOffset by remember { mutableStateOf(Offset.Zero) }
    val list = listOf(titleOffset, mandaOffset, mandaOffset, todoOffset)

    LaunchedEffect(pagerState.currentPage){
        Log.e("TAG", "currentPage: ${pagerState.currentPage}")
        when(pagerState.currentPage){
            0 -> imageOffset = titleOffset
            1 -> imageOffset = mandaOffset
            2 -> imageOffset = mandaOffset
            3 -> imageOffset = todoOffset
        }
    }

    LaunchedEffect(Unit){
        backGroundAlpha.fadeInScreen()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(backGroundAlpha.value)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.6f)
                .background(Color.Black)
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            beyondBoundsPageCount =1
        ) {page ->
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = pagerState.calculateCurrentOffsetForPage(page)
//                        translationX = pageOffset * size.width // TODO 슬라이드되면서 Fade Out
                        alpha = 1 - pageOffset.absoluteValue
                    }
                    .fillMaxSize()
                    .clickable(false) {}
            ){
                TutorialContent(
                    imageOffset = list[page],
                    text = textList[page],
                    mainId = imageList[page],
                    subId = if(page==2) imageList[page+1] else null
                )

            }
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
                    .clickable(true) {
                        coroutineScope.launch {
                            backGroundAlpha.fadeOutScreen()
                            onFinished()
                        }
                    },
                contentDescription = "finish"
            )
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

//@Preview
//@Composable
//fun TutorialPreview(){
//    TutorialScreen(
//        setCurrentPosition = {},
//        onFinished = {}
//    )
//}
@Composable
fun TutorialContent(
    imageOffset: Offset,
    text: String,
    @DrawableRes mainId: Int,
    @DrawableRes subId: Int?
){
    val gap =  painterResource(id = mainId)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset {
                IntOffset(
                    x = 0,
                    y = imageOffset.y.toInt()
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .aspectRatio(gap.intrinsicSize.width / gap.intrinsicSize.height)
                    .fillMaxWidth(),
                painter = painterResource(id = mainId),
                contentDescription = "main image"
            )
            subId?.let {
                Image(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .aspectRatio(gap.intrinsicSize.width / gap.intrinsicSize.height)
                        .fillMaxWidth(),
                    painter = painterResource(id = subId),
                    contentDescription = "sub image"
                )
            }
            Icon(
                imageVector = IconPack.TutorialArrow,
                tint = HMColor.Background,
                contentDescription = "arrow"
            )
            Text(
                text = text,
                style = HmStyle.text16,
                color = HMColor.Background
            )
        }
    }
}

@Preview
@Composable
fun FirstTutorialPreview(){
    TutorialContent(Offset.Zero,"가나다라마바사", R.drawable.tutorial_first, R.drawable.tutorial_first)
}

