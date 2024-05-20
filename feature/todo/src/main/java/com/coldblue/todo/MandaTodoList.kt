package com.coldblue.todo

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.window.Dialog
import com.coldblue.data.util.toMillis
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.component.HMButton
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
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun getDisplayTime(time: LocalTime?): String {
    return if (time != null) {
        "${time.hour}:${time.minute}에 알림"
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
                    MyTime(h, m, "${h}:${m}에 알림", LocalTime.of(h, m))
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
            TodoRangeSelector(todoRange, changeRange)
            Text(text = "Todo:$todoCnt", style = HmStyle.text16, fontWeight = FontWeight.Bold)
        }
        Box(modifier = Modifier.fillMaxHeight()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
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
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        showDoneTodo = !showDoneTodo
                                    }
                            ) {
                                Row(
                                    modifier = Modifier.padding(
                                        vertical = 6.dp,
                                        horizontal = 8.dp
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
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
                                    Text(text = "완료됨 $doneTodoCnt")
                                }
                            }
                        }
                        if (showDoneTodo){
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
        TodoDialog(
            { todoDialogState = false },
            mandaTodo,
            upsertMandaTodo,
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(HMColor.LiteGray)
            .clickable {
                todoDialogState = true
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        CircleCheckbox(color, mandaTodo.isDone) {
            upsertMandaTodo(mandaTodo.copy(isDone = !mandaTodo.isDone))
        }

        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.95f),
            text = mandaTodo.title,
            color = if (mandaTodo.isDone) HMColor.DarkGray else HMColor.Text,
            textDecoration = if (mandaTodo.isDone) TextDecoration.LineThrough else null,
            style = HmStyle.text16,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Box(
            modifier = Modifier
                .padding(end = 0.dp)
                .width(12.dp)
                .height(60.dp)
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
    val tint = if (selected) color.copy(alpha = 0.8f) else HMColor.Text
    val background = if (selected) HMColor.LiteGray else HMColor.LiteGray

    IconButton(
        onClick = { onChecked() },
        modifier = Modifier.offset(x = 4.dp, y = 4.dp),
        enabled = enabled
    ) {

        Icon(
            imageVector = imageVector, tint = tint,
            modifier = Modifier.background(background, shape = CircleShape),
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
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clip(CircleShape)
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
                            tint = HMColor.Text,
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
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clip(CircleShape)
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
                            tint = HMColor.Text,
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
fun TodoDialog(
    onClickCancel: () -> Unit,
    mandaTodo: MandaTodo,
    upsertMandaTodo: (MandaTodo) -> Unit,
) {
    var timePickerState by remember { mutableStateOf(false) }
    var datePickerState by remember { mutableStateOf(false) }

    var text by remember { mutableStateOf(mandaTodo.title) }


    if (datePickerState) {
        CustomDatePickerDialog(
            LocalDateTime.of(mandaTodo.date, LocalTime.now()).toMillis(),
            { datePickerState = false },
            {
                datePickerState = false
                upsertMandaTodo(
                    mandaTodo.copy(
                        date = LocalDate.parse(
                            it,
                            DateTimeFormatter.BASIC_ISO_DATE
                        )
                    )
                )
            }
        )
    }
    if (timePickerState) {
        CustomTimePickerDialog(
            if (mandaTodo.time != null) mandaTodo.time?.hour else LocalTime.now().hour,
            if (mandaTodo.time != null) mandaTodo.time?.minute else LocalTime.now().minute,
            { timePickerState = false },
            { h, m ->
                timePickerState = false
                upsertMandaTodo(mandaTodo.copy(time = LocalTime.of(h, m)))
            }
        )
    }
    Dialog(
        onDismissRequest = { onClickCancel() },
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .background(HMColor.Background)
                    .padding(16.dp)
            ) {
                LazyColumn {
                    item {
                        BasicTextField(
                            textStyle = HmStyle.text16.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            value = text, onValueChange = { text = it },
                        )
                    }
                }
                val isDateSet = mandaTodo.date == null
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { datePickerState = true }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val color =
                        if (isDateSet) HMColor.DarkGray else HMColor.Primary
                    val dateText =
                        if (isDateSet) "날짜 추가" else mandaTodo.date.toString()
                    val textColor =
                        if (isDateSet) HMColor.DarkGray else HMColor.Text
                    Icon(
                        imageVector = IconPack.Calendar,
                        contentDescription = "",
                        tint = color,
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(dateText, color = textColor)
                }

                val isTimeSet = mandaTodo.time != null
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { timePickerState = true }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val color =
                        if (isTimeSet) HMColor.Primary else HMColor.DarkGray
                    val timeText =
                        if (isTimeSet) getDisplayTime(mandaTodo.time) else "알림 추가"
                    val textColor =
                        if (isTimeSet) HMColor.Text else HMColor.DarkGray
                    Row {
                        Icon(
                            imageVector = IconPack.Alarm,
                            contentDescription = "",
                            tint = color,
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(timeText, color = textColor)
                    }

                    if (isTimeSet) {
                        Icon(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .clickable {
                                    upsertMandaTodo(mandaTodo.copy(time = null))
                                },
                            imageVector = Icons.Rounded.Clear,
                            contentDescription = "",
                            tint = HMColor.Primary,
                        )
                    }
                }

                Row(modifier = Modifier.padding(top = 12.dp)) {
                    Button(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .height(50.dp)
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = HMColor.Gray),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            onClickCancel()
                            upsertMandaTodo(mandaTodo.copy(isDel = true))
                        }
                    ) {
                        Text(
                            text = "삭제",
                            style = HmStyle.text16,
                            color = HMColor.Primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    HMButton(
                        "저장",
                        text.isNotBlank(),
                        modifier = Modifier.fillMaxWidth(0.5f),
                        onClick = {
                            onClickCancel()
                            upsertMandaTodo(mandaTodo.copy(title = text))
                        })
                }

            }
        }
    }

}

@Preview
@Composable
fun TodoDialogPreview() {
    TodoDialog({}, MandaTodo("내용입니ㅏ 내용입니ㅏ 내용입니ㅏ 내용입니ㅏ내용입니ㅏ", mandaIndex = 2), {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    selectedDate: Long,
    onClickCancel: () -> Unit,
    onClickConfirm: (yyyyMMdd: String) -> Unit
) {
    DatePickerDialog(
        onDismissRequest = { onClickCancel() },
        confirmButton = {},
        colors = DatePickerDefaults.colors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(6.dp)
    ) {
        val datePickerState = rememberDatePickerState(
            yearRange = LocalDate.now().year..LocalDate.now().year + 1,
            initialDisplayMode = DisplayMode.Picker,
            initialSelectedDateMillis = selectedDate,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return true
                }
            })

        DatePicker(
            state = datePickerState,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Button(onClick = {
                onClickCancel()
            }) {
                Text(text = "취소")
            }
            Spacer(modifier = Modifier.width(5.dp))

            Button(onClick = {
                datePickerState.selectedDateMillis?.let { selectedDateMillis ->
                    val yyyyMMdd = SimpleDateFormat(
                        "yyyyMMdd",
                        Locale.getDefault()
                    ).format(Date(selectedDateMillis))

                    onClickConfirm(yyyyMMdd)
                }
            }) {
                Text(text = "확인")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePickerDialog(
    selectedHour: Int?,
    selectedMinute: Int?,
    onClickCancel: () -> Unit,
    onClickConfirm: (hour: Int, minute: Int) -> Unit
) {
    val timePickerState = rememberTimePickerState(
        initialHour = selectedHour ?: 0,
        initialMinute = selectedMinute ?: 0,
        is24Hour = false
    )
    Dialog(
        onDismissRequest = { onClickCancel() },
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .wrapContentHeight()
                    .background(
                        color = Color.White,
                    )
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(text = "시간을 선택해주세요.")

                Spacer(modifier = Modifier.width(15.dp))

                TimePicker(
                    state = timePickerState,
                    modifier = Modifier.padding(top = 10.dp),
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(onClick = {
                        onClickCancel()
                    }) {
                        Text(text = "취소")
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    Button(onClick = {
                        val hour = timePickerState.hour
                        val minute = timePickerState.minute
                        onClickConfirm(hour, minute)
                    }) {
                        Text(text = "확인")
                    }
                }
            }
        }
    }
}

@Composable
fun TodoRangeSelector(todoRange: DateRange, changeRange: (DateRange) -> Unit) {
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
            }
        }
    }
}

@Composable
fun SelectButton(toggleInfo: ToggleInfo, onClick: (DateRange) -> Unit) {
    Surface(
        color = if (toggleInfo.isChecked) HMColor.Primary else HMColor.Background,
        contentColor = HMColor.Primary,
        shape = CircleShape,
        modifier = Modifier
            .padding(end = 8.dp)
            .clip(CircleShape)
            .clickable {
                onClick(toggleInfo.dateRange)
            }
    ) {
        Text(
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 16.dp),
            text = toggleInfo.text,
            color = if (toggleInfo.isChecked) HMColor.Background else HMColor.DarkGray
        )
    }
}

@Preview
@Composable
fun MandaTodoItemPreview() {
//            upsertMandaTodoUseCase(MandaTodo("1번투구", false, false, null, LocalDate.now(), 1, false))

    MandaTodoList(
        listOf(HMColor.Manda.Red, HMColor.Manda.Orange),
        1, DateRange.DAY,
        listOf(
            MandaTodo("1번투구", true, false, null, LocalDate.now(), 1, false),
            MandaTodo("1번투구", false, false, null, LocalDate.now(), 1, false),
            MandaTodo("1번투구", false, false, null, LocalDate.now(), 1, false)
        ),
        3, 3, {}, {},
    )

}