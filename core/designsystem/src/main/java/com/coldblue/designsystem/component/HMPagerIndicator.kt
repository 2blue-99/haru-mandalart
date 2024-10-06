package com.coldblue.designsystem.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue

@Composable
fun HMPagerIndicator(
    pageCount: Int,
    currentPage: Int,
    targetPage: Int,
    currentPageOffsetFraction: Float,
    indicatorColor: Color = Color.DarkGray,
    unselectedIndicatorSize: Dp = 8.dp,
    selectedIndicatorSize: Dp = 10.dp,
    indicatorPadding: Dp = 8.dp
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .wrapContentSize()
            .height(selectedIndicatorSize + indicatorPadding * 2)
    ) {
        repeat(pageCount) { page ->
            val (color, size) =
                if (currentPage == page || targetPage == page) {
                    val pageOffset =
                        ((currentPage - page) + currentPageOffsetFraction).absoluteValue
                    val offsetPercentage = 1f - pageOffset.coerceIn(0f, 1f)
                    val size =
                        unselectedIndicatorSize + ((selectedIndicatorSize - unselectedIndicatorSize) * offsetPercentage)
                    indicatorColor.copy(
                        alpha = offsetPercentage
                    ) to size
                } else {
                    indicatorColor.copy(alpha = 0.1f) to unselectedIndicatorSize
                }

            Box(
                modifier = Modifier
                    .padding(
                        horizontal = ((selectedIndicatorSize + indicatorPadding * 2) - size) / 2,
                        vertical = size / 4
                    )
                    .clip(CircleShape)
                    .background(color)
                    .size(size)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}