package com.coldblue.todo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.data.util.toMillis
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.component.HMButton
import com.coldblue.designsystem.component.HMTextField
import com.coldblue.designsystem.iconpack.todo.Alarm
import com.coldblue.designsystem.iconpack.todo.Calendar
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.MandaTodo
import com.coldblue.model.MyDate
import com.coldblue.model.MyTime
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun toMyDate(date: LocalDate): MyDate {
    val displayText = date.format(
        DateTimeFormatter.ofPattern(
            "M월 d일(E)",
            Locale("ko")
        )
    )
    return MyDate(displayText = displayText, date = date)
}

fun toMyTime(time: LocalTime?): MyTime? {
    val h = time?.hour ?: LocalTime.now().hour
    val m = time?.minute ?: LocalTime.now().minute
    return if (time == null) null else
        MyTime(h, m, "${h}:${m}에 알림", LocalTime.of(h, m))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoBottomSheet(
    onClickCancel: () -> Unit,
    mandaTodo: MandaTodo,
    upsertMandaTodo: (MandaTodo) -> Unit,
) {
    var dateState by remember { mutableStateOf<MyDate>(toMyDate(mandaTodo.date)) }
    var myTimeState by remember { mutableStateOf<MyTime?>(toMyTime(mandaTodo.time)) }


    var timePickerState by remember { mutableStateOf(false) }
    var datePickerState by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var text by remember { mutableStateOf(mandaTodo.title) }


    if (datePickerState) {
        CustomDatePickerDialog(
            LocalDateTime.of(dateState.date, LocalTime.now()).toMillis(),
            { datePickerState = false },
            {
                datePickerState = false
                val inputDate = LocalDate.parse(it, DateTimeFormatter.BASIC_ISO_DATE)
                val displayText = inputDate.format(
                    DateTimeFormatter.ofPattern(
                        "M월 d일(E)",
                        Locale("ko")
                    )
                )
                dateState = MyDate(displayText = displayText, date = inputDate)
            }
        )
    }
    if (timePickerState) {
        CustomTimePickerDialog(
            myTimeState?.time?.hour,
            myTimeState?.time?.minute,
            { timePickerState = false },
            { h, m ->
                timePickerState = false
                myTimeState = MyTime(h, m, "${h}:${m}에 알림", LocalTime.of(h, m))
            }
        )
    }
    ModalBottomSheet(
        sheetState = sheetState,
        containerColor = HMColor.Background,
        onDismissRequest = { onClickCancel() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 0.dp, bottom = 50.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Text(
                text = "Todo",
                style = HmStyle.text20,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Column(
                verticalArrangement = Arrangement.spacedBy((-5).dp)
            ) {
                HMTextField(inputText = text, maxLen = -1) { text = it }

                val isDateSet = dateState == null
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
                        if (isDateSet) "날짜 추가" else dateState.displayText
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

                val isTimeSet = myTimeState != null
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
                    val timeText = myTimeState?.displayText ?: "알림 추가"
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
                                    myTimeState = null
//                                    upsertMandaTodo(mandaTodo.copy(time = null))
                                },
                            imageVector = Icons.Rounded.Clear,
                            contentDescription = "",
                            tint = HMColor.Primary,
                        )
                    }
                }


            }
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
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
                    text = stringResource(id = com.coldblue.designsystem.R.string.all_save),
                    clickableState = text.isNotBlank(),
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .weight(1f),
                ) {
                    onClickCancel()
                    upsertMandaTodo(
                        mandaTodo.copy(
                            title = text,
                            time = myTimeState?.time,
                            date = dateState.date
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TodoBottomSheetPreview() {
    TodoBottomSheet({}, MandaTodo("내용입니ㅏ 내용입니ㅏ 내용입니ㅏ 내용입니ㅏ내용입니ㅏ", mandaIndex = 2), {})
}
