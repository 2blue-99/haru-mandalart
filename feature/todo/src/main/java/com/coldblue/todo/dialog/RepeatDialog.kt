package com.coldblue.todo.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.DATE_RANGE
import com.coldblue.model.RepeatRange
import com.coldblue.model.RepeatToggle
import com.coldblue.model.dateRangeToString


@Composable
fun RepeatDialog(
    repeatRange: RepeatRange,
    onDismissRequest: () -> Unit,
    onClick: (RepeatRange) -> Unit
) {


    var curRange by remember { mutableStateOf(if (repeatRange.dateRange != DATE_RANGE.NONE) DATE_RANGE.DAY else repeatRange.dateRange) }
    var rangeText by remember { mutableStateOf(dateRangeToString(repeatRange.dateRange)) }
    var rangeNum by remember { mutableStateOf(repeatRange.range.toString()) }

    val dateRangeButtons = remember {
        mutableStateListOf(
            RepeatToggle(
                isChecked = curRange == DATE_RANGE.DAY || curRange == DATE_RANGE.NONE,
                text = "매일",
                rangeText = "일마다",
                dateRange = DATE_RANGE.DAY
            ),
            RepeatToggle(
                isChecked = curRange == DATE_RANGE.WEEK,
                text = "매주",
                rangeText = "주마다",
                dateRange = DATE_RANGE.WEEK
            ),
            RepeatToggle(
                isChecked = curRange == DATE_RANGE.MONTH,
                text = "매월",
                rangeText = "개월마다",
                dateRange = DATE_RANGE.MONTH
            ),
        )
    }

    Dialog(onDismissRequest = onDismissRequest) {
        Card(shape = RoundedCornerShape(8.dp)) {
            Column(
                Modifier
                    .background(HMColor.Background)
                    .padding(16.dp)
            ) {
                LazyRow {
                    items(dateRangeButtons) { group ->
                        RepeatButton(group) { dateRange ->
                            curRange = dateRange
                            rangeText = group.rangeText
                            dateRangeButtons.replaceAll {
                                it.copy(isChecked = it.text == group.text)
                            }
                        }
                    }
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "반복 주기", fontWeight = FontWeight.Bold)
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .width(IntrinsicSize.Min)
                                .background(HMColor.Gray)
                                .padding(vertical = 2.dp, horizontal = 8.dp),
                            value = rangeNum,
                            maxLines = 1,
                            onValueChange = {
                                if (it.isNotEmpty()) {
                                    if (it.length < 4) {
                                        rangeNum = it
                                    }
                                } else {
                                    rangeNum = ""
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Text(text = rangeText)
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Button(
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = HMColor.Gray),
                        onClick = {
                            onDismissRequest()
                        }) {
                        Text(text = "취소", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(5.dp))

                    Button(
                        shape = RoundedCornerShape(8.dp),
                        onClick = {
                            onClick(
                                RepeatRange(
                                    if (curRange == DATE_RANGE.NONE) {
                                        DATE_RANGE.DAY
                                    } else{
                                        curRange
                                    },
                                    rangeNum.toInt()
                                )
                            )
                        }) {
                        Text(text = "확인", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }


    }
}

@Composable
fun RepeatButton(repeatToggle: RepeatToggle, onClick: (DATE_RANGE) -> Unit) {
    Surface(
        color = if (repeatToggle.isChecked) HMColor.Primary else HMColor.Background,
        contentColor = HMColor.Primary,
        modifier = Modifier
            .padding(end = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onClick(repeatToggle.dateRange)
            }
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 16.dp)
                .padding(bottom = 2.dp),
            text = repeatToggle.text,
            color = if (repeatToggle.isChecked) HMColor.Background else HMColor.DarkGray,
            style = HmStyle.text14
        )
    }
}

@Preview
@Composable
fun RepeatDialogPreview() {
    Box(modifier = Modifier.background(Color.White)) {
        RepeatDialog(
            repeatRange = RepeatRange(DATE_RANGE.DAY, 1),
            onDismissRequest = {},
            onClick = { s -> println(s) })
    }
}



