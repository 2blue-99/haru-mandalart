package com.coldblue.todo.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.coldblue.data.util.padTwoSpace
import com.coldblue.data.util.padTwoZero
import com.coldblue.designsystem.component.HMSwitch
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.MyTime
import kotlinx.coroutines.launch

@Composable
fun HMTimePicker(
    onSwitch: Boolean,
    myTime: MyTime,
    onCheckedChange: () -> Unit,
    onAmPmChange: (String) -> Unit,
    onHourChange: (Int) -> Unit,
    onMinuteChange: (Int) -> Unit
) {
    val amScrollState = rememberLazyListState(if (myTime.ampm == "오전") 0 else 1)
    var lastHour by remember { mutableIntStateOf(myTime.hour) }

    val hourScrollState = rememberLazyListState(myTime.hour - 1)
    val minScrollState = rememberLazyListState(myTime.minute - 1)

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
            text = if (onSwitch) "${myTime.ampm}${myTime.hour.padTwoSpace()}:${myTime.minute.padTwoZero()}" else "시간없음"
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
                initialItem = myTime.ampm,
                textColor = HMColor.SubText,
                selectedTextColor = HMColor.Text,
                onItemSelected = { _, item ->
                    onAmPmChange(item)
                },
                scrollState = amScrollState
            )

            InfiniteCircularList(
                itemHeight = 40.dp,
                textStyle = HmStyle.text16,
                items = List(12) { it + 1 },
                initialItem = myTime.hour,
                textColor = HMColor.SubText,
                selectedTextColor = HMColor.Text,
                onItemSelected = { index, item ->
                    onHourChange(item)
                    if (amScrollState.firstVisibleItemIndex == 0) {
                        if (item == 12 && lastHour == 11 || item == 11 && lastHour == 12) {
                            coroutineState.launch {
                                amScrollState.animateScrollToItem(1, 0)
                            }
                            onAmPmChange("오후")
                        }
                    } else {
                        if (item == 12 && lastHour == 11 || item == 11 && lastHour == 12) {
                            coroutineState.launch {
                                amScrollState.animateScrollToItem(0, 0)
                            }
                            onAmPmChange("오전")
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
                initialItem = myTime.minute.padTwoZero(),
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