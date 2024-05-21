package com.coldblue.todo

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.coldblue.data.util.toMillis
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.component.HMButton
import com.coldblue.designsystem.iconpack.todo.Alarm
import com.coldblue.designsystem.iconpack.todo.Calendar
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.MandaTodo
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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