package com.coldblue.todo.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

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
            textColor = HMColor.SubLightText,
            selectedTextColor = HMColor.Text,
            onItemSelected = { a, year -> onYearChange(year.dropLast(1).toInt()) },
            scrollState = rememberLazyListState(date.year - 2000)
        )
        CircularList(
            itemHeight = 40.dp,
            textStyle = HmStyle.text16,
            items = List(12) { "${it + 1}월" },
            initialItem = date.month.getDisplayName(
                TextStyle.FULL,
                Locale.KOREA
            ),
            textColor = HMColor.SubLightText,
            selectedTextColor = HMColor.Text,
            onItemSelected = { a, month -> onMonthChange(month.dropLast(1).toInt()) },
            scrollState = rememberLazyListState(date.month.value - 1)
        )
        CircularList(
            itemHeight = 40.dp,
            textStyle = HmStyle.text16,
            items = List(date.lengthOfMonth()) { "${it + 1}일" },
            initialItem = "${date.dayOfMonth}일",
            textColor = HMColor.SubLightText,
            selectedTextColor = HMColor.Text,
            onItemSelected = { a, day -> onDayChange(day.dropLast(1).toInt()) },
            scrollState = rememberLazyListState(date.dayOfMonth - 1)
        )
    }
}