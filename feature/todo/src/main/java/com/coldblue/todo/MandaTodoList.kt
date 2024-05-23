package com.coldblue.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coldblue.data.util.toMillis
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.iconpack.todo.AddSquare
import com.coldblue.designsystem.iconpack.todo.Alarm
import com.coldblue.designsystem.iconpack.todo.Calendar
import com.coldblue.designsystem.iconpack.todo.Circle
import com.coldblue.designsystem.iconpack.todo.CircleCheck
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.DateRange
import com.coldblue.model.MandaTodo
import com.coldblue.model.MyDate
import com.coldblue.model.MyTime
import com.coldblue.model.ToggleInfo
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun getDisplayTime(time: LocalTime?): String {

    return if (time != null) {
        val padM = time.minute.toString().padStart(2, '0')
        "${time.hour}:${padM}에 알림"
    } else {
        ""
    }
}

@Composable
fun MandaTodoList(
    colorList: List<Color?>,
    currentIndex: Int,
    todoRange: DateRange,
    todoList: List<MandaTodo>,
    todoCnt: Int,
    doneTodoCnt: Int,
    upsertMandaTodo: (MandaTodo) -> Unit,
    changeRange: (DateRange) -> Unit,
) {
    var datePickerState by remember { mutableStateOf(false) }
    var timePickerState by remember { mutableStateOf(false) }

    var dateState by remember { mutableStateOf<MyDate?>(null) }
    var myTimeState by remember { mutableStateOf<MyTime?>(null) }

    var showDoneTodo by remember { mutableStateOf(true) }

    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    if (datePickerState) {
        CustomDatePickerDialog(
            LocalDateTime.now().toMillis(),
            { datePickerState = false },
            {
                val inputDate = LocalDate.parse(it, DateTimeFormatter.BASIC_ISO_DATE)
                val displayText = inputDate.format(
                    DateTimeFormatter.ofPattern(
                        "M월 d일(E)",
                        Locale("ko")
                    )
                )
                dateState = MyDate(displayText = displayText, date = inputDate)
                datePickerState = false
            }
        )
    }


    if (timePickerState) {
        CustomTimePickerDialog(
            LocalTime.now().hour, LocalTime.now().minute,
            { timePickerState = false },
            { h, m ->
                myTimeState =
                    MyTime(h, m, toDisplayTime(h, m), LocalTime.of(h, m))
                timePickerState = false
            }
        )
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TodoRangeSelector(todoRange, changeRange, {
                scope.launch {
                    scrollState.scrollToItem(0)
                }
            })
            Text(text = "Todo:$todoCnt", style = HmStyle.text16, fontWeight = FontWeight.Bold)
        }
        Box(modifier = Modifier.fillMaxHeight()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                state = scrollState
            ) {
                if (todoList.isEmpty()) {
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 40.dp),
                            textAlign = TextAlign.Center,
                            text = if (currentIndex != -1 && colorList[currentIndex] != null) "Todo를 추가해 주세요!" else "Todo를 추가해 주세요!",
                            style = HmStyle.text20,
                            color = HMColor.SubLightText
                        )
                    }

                } else {
                    items(todoList.filter { !it.isDone }) { todo ->
                        MandaTodoItem(
                            todo,
                            currentIndex,
                            colorList[todo.mandaIndex] ?: HMColor.Gray,
                            upsertMandaTodo,
                        )
                    }
                    if (doneTodoCnt > 0) {
                        item {
                            Surface(
                                color = HMColor.LiteGray,
                                contentColor = HMColor.Text,
                                modifier = Modifier
                                    .padding(top = 6.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        showDoneTodo = !showDoneTodo
                                    }
                            ) {
                                Row(
                                    modifier = Modifier.padding(
                                        vertical = 4.dp,
                                        horizontal = 8.dp
                                    ),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    if (showDoneTodo) {
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowDown,
                                            contentDescription = "",
                                            tint = HMColor.Text,
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                            contentDescription = "",
                                            tint = HMColor.Text,
                                        )
                                    }
                                    Text(
                                        text = "완료 $doneTodoCnt",
                                        style = HmStyle.text14,
                                        modifier = Modifier.padding(bottom = 2.dp)
                                    )
                                }
                            }
                        }
                        if (showDoneTodo) {
                            items(todoList.filter { it.isDone }) { todo ->
                                MandaTodoItem(
                                    todo,
                                    currentIndex,
                                    colorList[todo.mandaIndex] ?: HMColor.Gray,
                                    upsertMandaTodo,
                                )
                            }
                        }
                    }

                }
                item { Spacer(modifier = Modifier.height(70.dp)) }
            }

            if (currentIndex != -1 && currentIndex != 4 && colorList[currentIndex] != null) {
                Column {
                    Spacer(modifier = Modifier.weight(1f))
                    TodoInput(
                        myTimeState,
                        dateState,
                        upsertMandaTodo,
                        currentIndex,
                        { datePickerState = true },
                        { timePickerState = true },
                        { myTimeState = null },
                        { dateState = null },
                        {
                            dateState = null
                            myTimeState = null
                        }
                    )
                }
            }

        }
    }
}

@Composable
fun MandaTodoItem(
    mandaTodo: MandaTodo,
    currentIndex: Int,
    color: Color,
    upsertMandaTodo: (MandaTodo) -> Unit,
) {
    var todoDialogState by remember { mutableStateOf(false) }

    if (todoDialogState) {
        TodoBottomSheet(
            { todoDialogState = false },
            mandaTodo,
            upsertMandaTodo,
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(HMColor.LiteGray)
            .clickable {
                todoDialogState = true
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            CircleCheckbox(color, mandaTodo.isDone) {
                upsertMandaTodo(mandaTodo.copy(isDone = !mandaTodo.isDone))
            }
            Text(
                modifier = Modifier
                    .padding(4.dp).padding(bottom = 2.dp)
                    .fillMaxWidth(0.85f),
                text = mandaTodo.title,
                color = if (mandaTodo.isDone) HMColor.DarkGray else HMColor.Text,
                textDecoration = if (mandaTodo.isDone) TextDecoration.LineThrough else null,
                style = HmStyle.text14,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (mandaTodo.time!=null){
                Icon(
                    imageVector = IconPack.Alarm,
                    contentDescription = "",
                    tint = HMColor.Primary,
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(end = 0.dp)
                .width(12.dp)
                .height(48.dp)
                .background(
                    color, shape = RoundedCornerShape(
                        topStart = CornerSize(0.dp),
                        topEnd = CornerSize(8.dp),
                        bottomEnd = CornerSize(8.dp),
                        bottomStart = CornerSize(0.dp)
                    )
                )
        ) {

        }
    }


}

@Composable
fun CircleCheckbox(
    color: Color,
    selected: Boolean,
    enabled: Boolean = true,
    onChecked: () -> Unit
) {

    val imageVector = if (selected) IconPack.CircleCheck else IconPack.Circle
    val tint = if (selected) HMColor.Background else HMColor.Text
    val background = if (selected) color else HMColor.LiteGray

    IconButton(
        onClick = { onChecked() },
        enabled = enabled
    ) {
        Icon(
            imageVector = imageVector,
            modifier = Modifier.background(background, shape = CircleShape),
            tint = tint,
            contentDescription = "checkbox"
        )
    }
}

@Composable
fun TodoInput(
    myTime: MyTime?,
    date: MyDate?,
    upsertMandaTodo: (MandaTodo) -> Unit,
    currentIndex: Int,
    showDatePicker: () -> Unit,
    showTimePicker: () -> Unit,
    clearTimeState: () -> Unit,
    clearDateState: () -> Unit,
    clearDateAndTime: () -> Unit
) {
    var text by remember { mutableStateOf("") }

    val hintVisible by remember {
        derivedStateOf { text.isEmpty() }
    }
    val focusManager = LocalFocusManager.current
    Column() {
        if (!hintVisible) {
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
            ) {
                Surface(
                    color = HMColor.Box,
                    contentColor = HMColor.Text,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            showDatePicker()
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = IconPack.Calendar,
                            contentDescription = "",
                            tint = HMColor.Primary,
                        )
                        if (date != null) {
                            Text(
                                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                                text = date.displayText,
                                style = HmStyle.text12
                            )
                            Icon(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        clearDateState()
                                    },
                                imageVector = Icons.Rounded.Clear,
                                contentDescription = "",
                                tint = HMColor.Primary,
                            )
                        }
                    }
                }
                Surface(
                    color = HMColor.Box,
                    contentColor = HMColor.Text,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            showTimePicker()
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = IconPack.Alarm,
                            contentDescription = "",
                            tint = HMColor.Primary,
                        )
                        if (myTime != null) {
                            Text(
                                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                                text = myTime.displayText,
                                style = HmStyle.text12
                            )
                            Icon(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        clearTimeState()
                                    },
                                imageVector = Icons.Rounded.Clear,
                                contentDescription = "",
                                tint = HMColor.Primary,
                            )
                        }
                    }
                }
            }
        }

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text, onValueChange = { text = it },
            maxLines = 1,
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = HMColor.Box, shape = RoundedCornerShape(size = 8.dp))
                        .padding(all = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(modifier = Modifier.fillMaxWidth(0.9f)) {
                        if (hintVisible) {
                            Text(
                                text = "Todo 추가", color = HMColor.Gray
                            )
                        }
                        innerTextField()
                    }
                    Icon(
                        modifier = Modifier.clickable(enabled = !hintVisible) {
                            upsertMandaTodo(
                                MandaTodo(
                                    title = text, mandaIndex = currentIndex,
                                    date = date?.date ?: LocalDate.now(),
                                    time = myTime?.time
                                )
                            )
                            text = ""
                            clearDateAndTime()
                            focusManager.clearFocus()
                        },
                        imageVector = IconPack.AddSquare,
                        contentDescription = "",
                        tint = if (hintVisible) HMColor.Gray else HMColor.Primary,
                    )

                }
            },
        )

        Spacer(modifier = Modifier.height(20.dp))
    }

}

@Composable
fun TodoRangeSelector(
    todoRange: DateRange,
    changeRange: (DateRange) -> Unit,
    scrollInit: () -> Unit
) {
    val dateRangeButtons = remember {
        mutableStateListOf(
            ToggleInfo(
                isChecked = todoRange == DateRange.DAY,
                text = "오늘",
                dateRange = DateRange.DAY
            ), ToggleInfo(
                isChecked = todoRange == DateRange.WEEK,
                text = "이번주",
                dateRange = DateRange.WEEK
            ),
            ToggleInfo(
                isChecked = todoRange == DateRange.ALL,
                text = "전체",
                dateRange = DateRange.ALL
            )
        )
    }
    LazyRow {
        items(dateRangeButtons) { group ->
            SelectButton(group) { dateRange ->
                changeRange(dateRange)
                dateRangeButtons.replaceAll {
                    it.copy(isChecked = it.text == group.text)
                }
                scrollInit()

            }
        }
    }
}

@Composable
fun SelectButton(toggleInfo: ToggleInfo, onClick: (DateRange) -> Unit) {
    Surface(
        color = if (toggleInfo.isChecked) HMColor.Primary else HMColor.Background,
        contentColor = HMColor.Primary,
        modifier = Modifier
            .padding(end = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onClick(toggleInfo.dateRange)
            }
    ) {
        Text(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp).padding(bottom = 2.dp),
            text = toggleInfo.text,
            color = if (toggleInfo.isChecked) HMColor.Background else HMColor.DarkGray,
            style = HmStyle.text14
        )
    }
}

@Preview
@Composable
fun MandaTodoItemPreview() {
    MandaTodoList(
        listOf(HMColor.Manda.Red, HMColor.Manda.Orange),
        1, DateRange.DAY,
        listOf(
            MandaTodo("1번투구ffffffffffffffffffffffffffffffsdddddddddddd", true, false, LocalTime.now(), LocalDate.now(), 1, false),
            MandaTodo("1번투구", false, false, null, LocalDate.now(), 1, false),
            MandaTodo("1번투구", false, false, null, LocalDate.now(), 1, false)
        ),
        3, 3, {}, {},
    )
}