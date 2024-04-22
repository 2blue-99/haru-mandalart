package com.coldblue.mandalart.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coldblue.designsystem.component.HMButton
import com.coldblue.designsystem.component.HMText
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.colddelight.mandalart.R
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MandaExplainPage(
    updateExplainState: () -> Unit
) {
    val pageState = rememberPagerState(pageCount = { 4 })
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HorizontalPager(
            state = pageState,
            beyondBoundsPageCount = 0,
        ) { page ->
            ExplainPage(page)
        }

        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.padding(top = 30.dp)
        ) {
            PagerIndicator(
                pageCount = pageState.pageCount,
                currentPage = pageState.currentPage,
                targetPage = pageState.targetPage,
                currentPageOffsetFraction = pageState.currentPageOffsetFraction,
            )
        }

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .padding(bottom = 30.dp)
                .padding(horizontal = 30.dp)
        ) {
            HMButton(
                text = if (pageState.currentPage == 3)
                    stringResource(id = R.string.explain_finish) else stringResource(id = R.string.explain_next),
                clickableState = true
            ) {
                coroutineScope.launch {
                    val current = pageState.currentPage
                    if (current == 3)
                        updateExplainState()
                    else
                        pageState.animateScrollToPage(pageState.currentPage + 1)
                }
            }
        }
    }
}

@Composable
fun ExplainPage(
    pageCount: Int,
) {
//    val alpha = remember { Animatable(0f) }
//    LaunchedEffect(pageCount) {
//        alpha.animateTo(0f)
//        delay(100L)
//        alpha.animateTo(
//            targetValue = 1f,
//            animationSpec = tween(
//                durationMillis = 300,
//                easing = LinearEasing
//            )
//        )
//    }
    Surface(
        modifier = Modifier
            .background(HMColor.Background)
            .fillMaxSize()
            .padding(top = 90.dp)
            .padding(horizontal = 30.dp)
//            .alpha(alpha.value)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(HMColor.Background),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(40.dp),
                ) {
                    when (pageCount) {
                        0 -> FirstPage()
                        1 -> SecondPage()
                        2 -> ThirdPage()
                        else -> FourthPage()
                    }
                }
            }


        }
    }
}

@Composable
fun PagerIndicator(
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

@Composable
fun FirstPage() {
    Column {
        Text(
            text = stringResource(id = R.string.explain_title),
            style = HmStyle.text46,
            color = HMColor.Primary
        )
        Text(
            text = stringResource(id = R.string.explain_manda_mean),
            style = HmStyle.text12,
            color = HMColor.SubDarkText
        )
    }

    HMText(
        topText = stringResource(id = R.string.explain_content_top),
        targetText = stringResource(id = R.string.explain_content_tint),
        bottomText = stringResource(id = R.string.explain_content_bottom),
        tintColor = HMColor.Primary,
        fontSize = 18.sp
    )
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        painter = painterResource(id = R.drawable.explain_third_manda),
        contentDescription = ""
    )
}

@Composable
fun SecondPage() {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        painter = painterResource(id = R.drawable.explain_first_manda),
        contentDescription = ""
    )
    Text(
        modifier = Modifier.padding(start = 10.dp),
        text = stringResource(id = R.string.explain_usage_1),
        style = HmStyle.text16,
        color = HMColor.Primary
    )
}

@Composable
fun ThirdPage() {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        painter = painterResource(id = R.drawable.explain_second_manda),
        contentDescription = ""
    )
    Column(
        modifier = Modifier.padding(start = 10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.explain_usage_1),
            style = HmStyle.text16
        )
        Text(
            text = stringResource(id = R.string.explain_usage_2),
            style = HmStyle.text16,
            color = HMColor.Primary
        )
    }
}

@Composable
fun FourthPage() {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        painter = painterResource(id = R.drawable.explain_third_manda),
        contentDescription = ""
    )
    Column(
        modifier = Modifier.padding(start = 10.dp),
    ) {
        Text(
            text = stringResource(id = R.string.explain_usage_1),
            style = HmStyle.text16
        )
        Text(
            text = stringResource(id = R.string.explain_usage_2),
            style = HmStyle.text16
        )

        Text(
            text = stringResource(id = R.string.explain_usage_3),
            style = HmStyle.text16,
            color = HMColor.Primary
        )
    }

    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = stringResource(id = R.string.explain_manda_advantage),
        style = HmStyle.text20,
        color = HMColor.Primary,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
fun ExplainPagePreview() {
    FirstPage()
}
