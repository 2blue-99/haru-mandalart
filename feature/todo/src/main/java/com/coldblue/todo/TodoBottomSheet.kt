package com.coldblue.todo

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.Todo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoBottomSheet(todo: Todo, upsertTodo: (Todo) -> Unit, onDismissRequest: () -> Unit) {

    var onSwitch by remember { mutableStateOf(true) }
//    var onSwitch by remember { mutableStateOf(false) }
    var onDetail by remember { mutableStateOf(false) }
//    var onDetail by remember { mutableStateOf(true) }
    var titleText by remember { mutableStateOf(todo.title) }
    var contentText by remember { mutableStateOf(todo.content) }


    Column(
    ) {
        Text(text = "할 일", style = HmStyle.text16, fontWeight = FontWeight.Bold)
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = titleText,
            onValueChange = {
                titleText = it
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = HMColor.Primary,
                containerColor = Color.Transparent
            ),
        )
        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = "시간",
            style = HmStyle.text16,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "시간없음")
            Switch(checked = onSwitch, onCheckedChange = { onSwitch = !onSwitch })
        }
        if (onSwitch) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularList(
                    itemHeight = 40.dp,
                    textStyle = HmStyle.text16,
                    items = listOf("오전", "오후"), // 오전, 오후 항목 추가
                    initialItem = "오후", // 초기 선택 항목 설정
                    textColor = HMColor.SubText,
                    selectedTextColor = HMColor.Text,
                    onItemSelected = { _, _ -> }
                )

                InfiniteCircularList(
                    itemHeight = 40.dp,
                    textStyle = HmStyle.text16,
                    items = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
                    initialItem = 1,
                    textColor = HMColor.SubText,
                    selectedTextColor = HMColor.Text,
                    onItemSelected = { a, b -> }
                )
                Text(
                    text = ":",
                    style = HmStyle.text16,
                    fontSize = HmStyle.text16.fontSize * 1.5F
                )
                InfiniteCircularList(
                    itemHeight = 40.dp,
                    textStyle = HmStyle.text16,
                    items = List(60) { it + 1 },
                    initialItem = 1,
                    textColor = HMColor.SubText,
                    selectedTextColor = HMColor.Text,
                    onItemSelected = { a, b -> }
                )
            }

        }

        if (!onDetail) {
            ClickableText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                text = AnnotatedString("세부설정"),
                style = TextStyle(color = HMColor.Gray, textAlign = TextAlign.End),
                onClick = { onDetail = true })
        }


        if (onDetail) {
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = "설명",
                style = HmStyle.text16,
                fontWeight = FontWeight.Bold
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = contentText,
                onValueChange = {
                    contentText = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = HMColor.Primary,
                    containerColor = Color.Transparent
                ),
            )
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = "날짜",
                style = HmStyle.text16,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.padding(top = 48.dp),
                text = "그룹",
                style = HmStyle.text16,
                fontWeight = FontWeight.Bold
            )


        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonColors(
                contentColor = HMColor.Background,
                containerColor = HMColor.Primary,
                disabledContentColor = HMColor.Box,
                disabledContainerColor = HMColor.Primary,
            ),
            onClick = {
                upsertTodo(todo.copy(title = titleText, content = contentText))
                onDismissRequest()
            }
        ) {
            Text(
                text = "저장",
                style = HmStyle.text16,
                modifier = Modifier.padding(vertical = 4.dp),
                fontWeight = FontWeight.Bold
            )
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> InfiniteCircularList(
    itemHeight: Dp,
    numberOfDisplayedItems: Int = 3,
    items: List<T>,
    initialItem: T,
    itemScaleFact: Float = 1.5f,
    textStyle: TextStyle,
    textColor: Color,
    selectedTextColor: Color,
    onItemSelected: (index: Int, item: T) -> Unit = { _, _ -> }
) {
    val itemHalfHeight = LocalDensity.current.run { itemHeight.toPx() / 2f }
    val scrollState = rememberLazyListState(0)
    var lastSelectedIndex by remember { mutableIntStateOf(0) }
    val coroutineState = rememberCoroutineScope()

    LaunchedEffect(items) {
        var targetIndex = items.indexOf(initialItem) - 1
        targetIndex += ((Int.MAX_VALUE / 2) / items.size) * items.size
        lastSelectedIndex = targetIndex
        scrollState.scrollToItem(targetIndex)
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
                            if (isSelected && lastSelectedIndex != index) {
                                onItemSelected(index % items.size, item)
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
                            val index = i - 1
                            onItemSelected(index % items.size, item)
                            lastSelectedIndex = index
                            coroutineState.launch {
                                scrollState.animateScrollToItem(lastSelectedIndex, 0)
                            }
                        },
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> CircularList(
    itemHeight: Dp,
    numberOfDisplayedItems: Int = 3,
    items: List<T>,
    initialItem: T,
    itemScaleFact: Float = 1.5f,
    textStyle: TextStyle,
    textColor: Color,
    selectedTextColor: Color,
    onItemSelected: (index: Int, item: T) -> Unit = { _, _ -> }
) {
    val itemHalfHeight = LocalDensity.current.run { itemHeight.toPx() / 2f }
    val scrollState = rememberLazyListState(0)
    val coroutineState = rememberCoroutineScope()

    var lastSelectedIndex by remember {
        mutableIntStateOf(items.indexOf(initialItem))
    }

    LaunchedEffect(items) {
        val targetIndex = items.indexOf(initialItem)
        lastSelectedIndex = targetIndex
        scrollState.scrollToItem(lastSelectedIndex)
    }
    LazyColumn(
        modifier = Modifier
            .width(itemHeight * 2)
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
                            if (isSelected) {
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
                            onItemSelected(i, item)
                            lastSelectedIndex = i
                            coroutineState.launch {
                                scrollState.animateScrollToItem(lastSelectedIndex, 0)
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

@Preview
@Composable
fun TodoBottomSheetPreview() {
    Column(
        modifier = Modifier
            .background(HMColor.Background)
            .padding(16.dp)
    ) {

        TodoBottomSheet(todo = Todo(""), {}, {})

    }
}