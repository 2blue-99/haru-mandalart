package com.coldblue.todo

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.component.HMSwitch
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.CurrentGroup
import com.coldblue.model.Todo
import com.coldblue.model.ToggleInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoBottomSheet(
    todo: Todo,
    upsertTodo: (Todo) -> Unit,
    onDismissRequest: () -> Unit,
    today: LocalDate,
    sheetState: SheetState,
    currentGroupList: List<CurrentGroup>,
) {
    var onSwitch by remember { mutableStateOf(false) }
    var time: LocalTime by remember { mutableStateOf(todo.time ?: LocalTime.now()) }
    var onDetail by remember { mutableStateOf(false) }
    var titleText by remember { mutableStateOf(todo.title) }
    var contentText by remember { mutableStateOf(todo.content) }

    var currentTodoGroupId by remember { mutableStateOf(currentGroupList.firstOrNull { it.todoGroupId == todo.todoGroupId }?.todoGroupId) }

    val dateButtons = remember {
        mutableStateListOf(
            ToggleInfo(true, "오늘", plus = 0),
            ToggleInfo(false, "내일", plus = 1),
            ToggleInfo(false, "다음주", plus = 7),
            ToggleInfo(false, "직접입력"),
        )
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var date by remember { mutableStateOf(today) }

    LaunchedEffect(onSwitch) {
        sheetState.expand()
    }

    LaunchedEffect(onDetail) {
        sheetState.expand()
    }
    LaunchedEffect(dateButtons.last().isChecked) {
        sheetState.expand()
    }


    Box() {
        LazyColumn(Modifier.padding(bottom = 60.dp)) {
            item {
                Text(text = "할 일", style = HmStyle.text16, fontWeight = FontWeight.Bold)
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = titleText,
                    maxLines = 1,
                    onValueChange = {
                        titleText = it
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
//                    focusManager.clearFocus(false)
                        }
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = HMColor.Primary,
                        containerColor = Color.Transparent
                    ),
                )
            }
            item {
                HMTimePicker(
                    onSwitch = onSwitch,
                    onCheckedChange = {
                        onSwitch = !onSwitch
                    },
                    time = time,
                    onHourChange = { hour -> time = time.withHour(hour) },
                    onMinuteChange = { minute -> time = time.withMinute(minute) }
                )
            }
            if (!onDetail) {
                item {
                    ClickableText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp),
                        text = AnnotatedString("세부설정"),
                        style = TextStyle(color = HMColor.Gray, textAlign = TextAlign.End),
                        onClick = { onDetail = true })
                }
            }
            if (onDetail) {
                item {
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
                }
                item {
                    Text(
                        modifier = Modifier.padding(top = 24.dp),
                        text = "날짜",
                        style = HmStyle.text16,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = date.toString())

                    Row {
                        dateButtons.forEach { button ->
                            SelectButton(button) {
                                date = today.plusDays(button.plus)
                                dateButtons.replaceAll {
                                    it.copy(isChecked = it.text == button.text)
                                }
                            }
                        }
                    }
                    if (dateButtons.last().isChecked) {

                        HMDatePicker(
                            date,
                            onYearChange = { year -> date = date.withYear(year) },
                            onMonthChange = { month -> date = date.withMonth(month) },
                            onDayChange = { day -> date = date.withDayOfMonth(day) })

                    }
                }
                item {
                    GroupPicker(currentGroupList, currentTodoGroupId) { todoGroupId ->
                        currentTodoGroupId = todoGroupId
                    }
                }
            }
        }
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            if (todo.id != 0) {
                Row(Modifier.fillMaxWidth()) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F)
                            .padding(end = 8.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonColors(
                            contentColor = HMColor.Primary,
                            containerColor = HMColor.Gray,
                            disabledContentColor = HMColor.Gray,
                            disabledContainerColor = HMColor.Primary,
                        ),
                        onClick = {
                            upsertTodo(
                                todo.copy(
                                    isDel = true
                                )
                            )
                            onDismissRequest()
                        }
                    ) {
                        Text(
                            text = "삭제",
                            style = HmStyle.text16,
                            modifier = Modifier.padding(vertical = 4.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F)
                            .padding(start = 8.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonColors(
                            contentColor = HMColor.Background,
                            containerColor = HMColor.Primary,
                            disabledContentColor = HMColor.Box,
                            disabledContainerColor = HMColor.Primary,
                        ),
                        onClick = {
                            upsertTodo(
                                todo.copy(
                                    title = titleText,
                                    content = contentText,
                                    time = time,
                                    todoGroupId = currentTodoGroupId,
                                    date = date
                                )
                            )
                            onDismissRequest()
                        }
                    ) {
                        Text(
                            text = "수정",
                            style = HmStyle.text16,
                            modifier = Modifier.padding(vertical = 4.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            } else {
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
                        Log.e("TAG", "TodoBottomSheet: ${date.toString()}")
                        upsertTodo(
                            todo.copy(
                                title = titleText,
                                content = contentText,
                                time = time,
                                todoGroupId = currentTodoGroupId,
                                date = date
                            )
                        )
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

    }
}

@Composable
fun HMDatePicker(
    date: LocalDate,
    onYearChange: (Int) -> Unit,
    onMonthChange: (Int) -> Unit,
    onDayChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularList(
            itemHeight = 40.dp,
            textStyle = HmStyle.text16,
            items = List(101) { "${2000 + it}년" },
            initialItem = "${date.year}년",
            textColor = HMColor.SubText,
            selectedTextColor = HMColor.Text,
            onItemSelected = { a, year -> onYearChange(year.dropLast(1).toInt()) },
            scrollState = rememberLazyListState(0)
        )
        InfiniteCircularList(
            itemHeight = 40.dp,
            textStyle = HmStyle.text16,
            items = List(12) { "${it + 1}월" },
            initialItem = date.month.getDisplayName(
                java.time.format.TextStyle.FULL,
                Locale.KOREA
            ),
            textColor = HMColor.SubText,
            selectedTextColor = HMColor.Text,
            onItemSelected = { a, month -> onMonthChange(month.dropLast(1).toInt()) },
            scrollState = rememberLazyListState(0)

        )
        InfiniteCircularList(
            itemHeight = 40.dp,
            textStyle = HmStyle.text16,
            items = List(date.lengthOfMonth()) { "${it + 1}일" },
            initialItem = "${date.dayOfMonth}일",
            textColor = HMColor.SubText,
            selectedTextColor = HMColor.Text,
            onItemSelected = { a, day -> onDayChange(day.dropLast(1).toInt()) },
            scrollState = rememberLazyListState(0)

        )
    }
}

@Composable
fun GroupPicker(
    currentGroupList: List<CurrentGroup>,
    currentTodoGroupId: Int?,
    onClick: (Int?) -> Unit
) {
    val groupButtons = remember {
        mutableStateListOf<ToggleInfo>().apply {
            add(
                ToggleInfo(
                    isChecked = currentTodoGroupId == null,
                    text = "그룸없음",
                )
            )
            addAll(currentGroupList.map { group ->
                ToggleInfo(
                    isChecked = currentTodoGroupId == group.todoGroupId,
                    text = group.name,
                    currentGroupId = group.id
                )
            })
        }
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(top = 48.dp),
            text = "그룹",
            style = HmStyle.text16,
            fontWeight = FontWeight.Bold
        )
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(groupButtons) { group ->
                SelectButton(group) {
                    onClick(group.currentGroupId)
                    groupButtons.replaceAll {
                        it.copy(isChecked = it.text == group.text)
                    }
                }
            }
        }
    }

}

@Composable
fun HMTimePicker(
    onSwitch: Boolean,
    onCheckedChange: () -> Unit,
    time: LocalTime,
    onHourChange: (Int) -> Unit,
    onMinuteChange: (Int) -> Unit
) {
    var isAm by remember { mutableStateOf(time.hour < 12) }
    val timeString = if (isAm) "오전" else "오후"

    val amScrollState = rememberLazyListState(0)
    var lastHour by remember { mutableIntStateOf(time.hour) }

    val hourScrollState = rememberLazyListState(0)
    val minScrollState = rememberLazyListState(0)

    val coroutineState = rememberCoroutineScope()

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
        Text(
            text = if (onSwitch) "${timeString}${time.hour.padTwoSpace()}:${time.minute.padTwoZero()}" else "시간없음"
        )
        HMSwitch(onSwitch) {
            onCheckedChange()
        }

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
                items = listOf("오전", "오후"),
                initialItem = timeString,
                textColor = HMColor.SubText,
                selectedTextColor = HMColor.Text,
                onItemSelected = { _, _ -> },
                scrollState = amScrollState
            )

            InfiniteCircularList(
                itemHeight = 40.dp,
                textStyle = HmStyle.text16,
                items = List(12) { it + 1 },
                initialItem = time.hour % 12,
                textColor = HMColor.SubText,
                selectedTextColor = HMColor.Text,
                onItemSelected = { index, item ->
                    onHourChange(item)
                    if (amScrollState.firstVisibleItemIndex == 0) {
                        if (item == 12 && lastHour == 11 || item == 11 && lastHour == 12) {
                            coroutineState.launch {
                                amScrollState.animateScrollToItem(1, 0)
                            }
                        }
                    } else {
                        if (item == 12 && lastHour == 11 || item == 11 && lastHour == 12) {
                            coroutineState.launch {
                                amScrollState.animateScrollToItem(0, 0)
                            }
                        }
                    }
                    lastHour = item
                },
                scrollState = hourScrollState

            )
            Text(
                text = ":",
                style = HmStyle.text16,
                fontSize = HmStyle.text16.fontSize * 1.5F
            )
            InfiniteCircularList(
                itemHeight = 40.dp,
                textStyle = HmStyle.text16,
                items = List(60) { it.padTwoZero() },
                initialItem = time.minute.padTwoZero(),
                textColor = HMColor.SubText,
                selectedTextColor = HMColor.Text,
                onItemSelected = { index, item ->
                    onMinuteChange(item.toInt())
                },
                scrollState = minScrollState

            )
        }

    }
}

@Composable
fun SelectButton(toggleInfo: ToggleInfo, onClick: () -> Unit) {
    Surface(
        color = if (toggleInfo.isChecked) HMColor.Primary else HMColor.Background,
        contentColor = HMColor.Primary,
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(1.dp, HMColor.Primary),
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onClick()

            }

    ) {
        Text(
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp),
            text = toggleInfo.text,
            color = if (toggleInfo.isChecked) HMColor.Background else HMColor.Primary
        )
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
    scrollState: LazyListState,
    textColor: Color,
    selectedTextColor: Color,
    onItemSelected: (index: Int, item: T) -> Unit = { _, _ -> }
) {
    val itemHalfHeight = LocalDensity.current.run { itemHeight.toPx() / 2f }
    var lastSelectedIndex by remember { mutableIntStateOf(0) }
    val coroutineState = rememberCoroutineScope()

    LaunchedEffect(items) {
        var targetIndex = items.indexOf(initialItem) - 1
        targetIndex += ((Int.MAX_VALUE / 2) / items.size) * items.size
        lastSelectedIndex = targetIndex
        scrollState.scrollToItem(lastSelectedIndex, 0)

//        scrollState.animateScrollToItem(lastSelectedIndex, 0)
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
                                onItemSelected(index % items.size, item)
                                lastSelectedIndex = index
                                coroutineState.launch {
                                    scrollState.animateScrollToItem(lastSelectedIndex, 0)
                                }
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
    scrollState: LazyListState,
    textStyle: TextStyle,
    textColor: Color,
    selectedTextColor: Color,
    onItemSelected: (index: Int, item: T) -> Unit = { _, _ -> }
) {
    val itemHalfHeight = LocalDensity.current.run { itemHeight.toPx() / 2f }
    val coroutineState = rememberCoroutineScope()

    var lastSelectedIndex by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(items) {
        val targetIndex = items.indexOf(initialItem)
        lastSelectedIndex = targetIndex
        scrollState.scrollToItem(lastSelectedIndex, 0)
//        scrollState.animateScrollToItem(lastSelectedIndex, 0)

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