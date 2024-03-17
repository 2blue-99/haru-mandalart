package com.coldblue.todo.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
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
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> CircularList(
    itemHeight: Dp,
    numberOfDisplayedItems: Int = 3,
    items: List<T>,
    initialItem: T,
    itemScaleFact: Float = 1.5f,
    scrollState: LazyListState,
    textStyle: TextStyle,
    textColor: Color,
    selectedTextColor: Color,
    onItemSelected: (index: Int, item: T) -> Unit = { _, _ -> }
) {
    val itemHalfHeight = LocalDensity.current.run { itemHeight.toPx() / 2f }
    val coroutineState = rememberCoroutineScope()

    var check by remember { mutableStateOf(true) }
    var lastSelectedIndex by remember { mutableIntStateOf(items.indexOf(initialItem)) }



    LazyColumn(
        modifier = Modifier
            .width(itemHeight * 2.5f)
            .height(itemHeight * numberOfDisplayedItems),
        state = scrollState,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        flingBehavior = rememberSnapFlingBehavior(
            lazyListState = scrollState
        )
    ) {
        item {
            Box(modifier = Modifier.size(itemHeight))
        }
        items(
            count = items.size,
            itemContent = { i ->
                val item = items[i]
                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .onGloballyPositioned { coordinates ->
                            val y = coordinates.positionInParent().y - itemHalfHeight
                            val isSelected = y == itemHalfHeight
                            val index = i
                            if (isSelected && check && lastSelectedIndex != index) {
                                onItemSelected(i, item)
                                lastSelectedIndex = i
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
                            Logger.i(item.toString())
                            onItemSelected(i, item)
                            lastSelectedIndex = i
                            coroutineState.launch {
                                check = false
                                scrollState.animateScrollToItem(lastSelectedIndex, 0)
                                check = true
                            }
                        },
                    )
                }
            }
        )
        item {
            Box(modifier = Modifier.size(itemHeight))
        }
    }
}