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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.iconpack.Circle
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.DateRange
import com.coldblue.model.MandaTodo
import com.coldblue.model.ToggleInfo
import com.orhanobut.logger.Logger
import java.time.LocalDate

@Composable
fun MandaTodoList(
    colorList: List<Color?>,
    currentIndex: Int,
    todoRange: DateRange,
    todoList: List<MandaTodo>,
    todoCnt: Int,
    doneTodoCnt: Int,
    upsertMandaTodo: (MandaTodo) -> Unit,
    changeRange: (DateRange) -> Unit
) {
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
        LazyColumn(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
        ) {
            if (todoList.isEmpty()) {
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp),
                        textAlign = TextAlign.Center,
                        text = "Todo를 추가해 주세요!",
                        style = HmStyle.text20,
                        color = HMColor.SubLightText
                    )
                }

            } else {
                items(todoList.filter { !it.isDone }) { todo ->
                    MandaTodoItem(
                        todo,
                        currentIndex,
                        colorList[todo.mandaIndex] ?: HMColor.Gray, upsertMandaTodo
                    )
                }
                item {
                    Text(text = "완료됨")
                }
                items(todoList.filter { it.isDone }) { todo ->
                    MandaTodoItem(
                        todo,
                        currentIndex,
                        colorList[todo.mandaIndex] ?: HMColor.Gray, upsertMandaTodo
                    )
                }
            }
        }
        TodoInput()
    }
}

@Composable
fun MandaTodoItem(
    mandaTodo: MandaTodo,
    currentIndex: Int,
    color: Color,
    upsertMandaTodo: (MandaTodo) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(HMColor.LiteGray)
            .clickable { },
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
            maxLines = 2
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

    val imageVector = if (selected) Icons.Filled.CheckCircle else IconPack.Circle
    val tint = if (selected) color.copy(alpha = 0.8f) else HMColor.LiteGray
    val background = if (selected) HMColor.LiteGray else Color.Black

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
fun TodoInput() {
    var text by remember { mutableStateOf("") }

    val hintVisible by remember {
        derivedStateOf { text.isEmpty() }
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
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    tint = HMColor.Gray,
                )
                Box(modifier = Modifier.fillMaxWidth(0.9f)) {
                    if (hintVisible) {
                        Text(
                            text = "Todo 추가", color = HMColor.Gray
                        )
                    }
                    innerTextField()
                }

                Icon(
                    modifier = Modifier.clickable { Logger.d("클릭이요") },
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    tint = HMColor.Gray,
                )

            }
        },
    )
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
        1, DateRange.DAY, listOf(
            MandaTodo("1번투구", true, false, null, LocalDate.now(), 1, false),
            MandaTodo("1번투구", false, false, null, LocalDate.now(), 1, false),
            MandaTodo("1번투구", false, false, null, LocalDate.now(), 1, false)
        ), 3, 3, {}, {}
    )

}