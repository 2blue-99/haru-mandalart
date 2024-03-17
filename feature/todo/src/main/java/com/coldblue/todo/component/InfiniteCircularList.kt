package com.coldblue.todo.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> InfiniteCircularList(
    itemHeight: Dp,
    numberOfDisplayedItems: Int = 3,
    items: List<T>,
    initialItem: T,
    itemScaleFact: Float = 1.5f,
    textStyle: TextStyle,
    scrollState: LazyListState,
    textColor: Color,
    selectedTextColor: Color,
    onItemSelected: (index: Int, item: T) -> Unit = { _, _ -> }
) {
    val itemHalfHeight = LocalDensity.current.run { itemHeight.toPx() / 2f }
    var lastSelectedIndex by remember { mutableIntStateOf(0) }
    val coroutineState = rememberCoroutineScope()
    var check by remember { mutableStateOf(true) }

    LaunchedEffect(items) {
        var targetIndex = items.indexOf(initialItem) - 1
        targetIndex += ((Int.MAX_VALUE / 2) / items.size) * items.size
        lastSelectedIndex = targetIndex
        scrollState.scrollToItem(lastSelectedIndex, 0)
    }
    LazyColumn(
        modifier = Modifier
            .width(itemHeight * 2)
            .height(itemHeight * numberOfDisplayedItems),
        state = scrollState,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        flingBehavior = rememberSnapFlingBehavior(
            lazyListState = scrollState
        )
    ) {
        items(
            count = Int.MAX_VALUE,
            itemContent = { i ->
                val item = items[i % items.size]
                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .onGloballyPositioned { coordinates ->
                            val y = coordinates.positionInParent().y - itemHalfHeight
                            val parentHalfHeight = (itemHalfHeight * numberOfDisplayedItems)
                            val isSelected =
                                (y > parentHalfHeight - itemHalfHeight && y < parentHalfHeight + itemHalfHeight)
                            val index = i - 1
                            if (isSelected && check && lastSelectedIndex != index) {
                                onItemSelected(index % items.size, items[index % items.size])
                                lastSelectedIndex = index
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {

                    ClickableText(
                        AnnotatedString(item.toString()),
                        style = textStyle.copy(
                            fontSize = if (lastSelectedIndex == i) {
                                textStyle.fontSize * itemScaleFact
                            } else {
                                textStyle.fontSize
                            },
                            color = if (lastSelectedIndex == i) {
                                selectedTextColor
                            } else {
                                textColor
                            },
                        ),
                        onClick = {
                            if (lastSelectedIndex != i) {
                                val index = i - 1
                                lastSelectedIndex = index
                                coroutineState.launch {
                                    check = false
                                    scrollState.animateScrollToItem(lastSelectedIndex, 0)
                                    onItemSelected(index % items.size, item)
                                    check = true
                                }
                            }
                        },
                    )
                }
            }
        )
    }
}

