package com.coldblue.history.content

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.component.TitleText
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.history.ControllerDayState
import com.coldblue.history.ControllerTimeState
import com.coldblue.history.ControllerWeek
import com.coldblue.history.HistoryUiState
import java.time.LocalDate

@Composable
fun HistoryContent(
    historyUiState: HistoryUiState.Success,
    selectYear: (Int) -> Unit,
    selectDate: (LocalDate) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HMColor.Background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        TitleText(text = "65개의 \n 하루, 만다라트")

        HistoryController(
            controllerList = historyUiState.controllerList,
            todoYearList = historyUiState.todoYearList,
            selectYear = selectYear,
            selectDate = selectDate
        )

        Column {
            TitleText(text = historyUiState.today.toString())
            TitleText(text = "기록")
        }

        LazyColumn(
            Modifier
                .fillMaxSize()
                .background(HMColor.Background),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(historyUiState.todoList) {
//            TodoItem(todo = it, onTodoToggle = {}, showSheet = {})
            }
        }
    }
}

@Composable
fun HistoryController(
    controllerList: List<ControllerWeek>,
    todoYearList: List<Int>,
    selectDate: (LocalDate) -> Unit,
    selectYear: (Int) -> Unit
) {

    // 이 코드 내부의 변화는 Recompose를 발생시키지 않음
    val boxClickStateList = remember { MutableList((controllerList.size) * 7) { false } }
    var yearClickStateList by remember { mutableStateOf(BooleanArray(todoYearList.size)) }
    var beforeBoxIndex by remember { mutableIntStateOf(0) }
    val screenWidth = LocalConfiguration.current.screenWidthDp

    Column {
        Row {
            Column(
                modifier = Modifier.width((screenWidth / 16).dp)
            ){
                ControllerBox(
                    containerColor = Color.Transparent,
                    sideText = "",
                    clickAble = false
                ) {}
                controllerList[0].controllerDayList.forEach {
                    if(it is ControllerDayState.Default)
                        ControllerBox(
                            containerColor = Color.Transparent,
                            sideText = it.dayWeek,
                            clickAble = false
                        ) {}
                }
            }
            LazyRow(
                Modifier
                    .fillMaxWidth()
                    .background(HMColor.Background),
                horizontalArrangement = Arrangement.Start
            ) {
                itemsIndexed(controllerList.slice(1..< controllerList.size)) { weekIndex, controllerWeek ->
                    Column(
                        modifier = Modifier.width((screenWidth / 16).dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        ControllerBox(
                            containerColor = Color.Transparent,
                            sideText = if (controllerWeek.month != null) controllerWeek.month.toString() else "",
                            clickAble = false
                        ) {}

                        controllerWeek.controllerDayList.forEachIndexed { dayIndex, dayState ->
                            val stateIndex = (weekIndex * 7) + dayIndex
                            when (dayState) {

                                is ControllerDayState.Default -> {
                                    ControllerBox(
                                        containerColor = Color.Transparent,
                                        sideText = dayState.dayWeek,
                                        clickAble = false
                                    ) {}
                                }

                                is ControllerDayState.Empty -> {
                                    when (val timeState = dayState.timeState) {

                                        is ControllerTimeState.Past -> {
                                            ControllerBox(
                                                containerColor = HMColor.Gray,
                                                isClicked = boxClickStateList[stateIndex]
                                            ) {
                                                boxClickStateList[beforeBoxIndex] = false
                                                boxClickStateList[stateIndex] = true
                                                beforeBoxIndex = stateIndex
                                                selectDate(timeState.date)
                                            }
                                        }

                                        is ControllerTimeState.Future -> {
                                            ControllerBox(
                                                containerColor = HMColor.Box,
                                                isClicked = boxClickStateList[stateIndex]
                                            ) {
                                                boxClickStateList[beforeBoxIndex] = false
                                                boxClickStateList[stateIndex] = true
                                                beforeBoxIndex = stateIndex
                                                selectDate(timeState.date)
                                            }
                                        }
                                    }
                                }

                                is ControllerDayState.Exist -> {
                                    when (val timeState = dayState.timeState) {

                                        is ControllerTimeState.Past -> {
                                            ControllerBox(
                                                containerColor = HMColor.Box,
                                                tintColor = HMColor.Primary,
                                                isExistTodo = true,
                                                isClicked = boxClickStateList[stateIndex]
                                            ) {
                                                boxClickStateList[beforeBoxIndex] = false
                                                boxClickStateList[stateIndex] = true
                                                beforeBoxIndex = stateIndex
                                                selectDate(timeState.date)
                                            }
                                        }

                                        is ControllerTimeState.Future -> {
                                            ControllerBox(
                                                containerColor = HMColor.Background,
                                                tintColor = HMColor.LightPrimary,
                                                isExistTodo = true,
                                                isClicked = boxClickStateList[stateIndex]
                                            ) {
                                                boxClickStateList[beforeBoxIndex] = false
                                                boxClickStateList[stateIndex] = true
                                                beforeBoxIndex = stateIndex
                                                selectDate(timeState.date)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            LazyRow(
                Modifier
                    .fillMaxWidth()
                    .background(HMColor.Background),
                horizontalArrangement = Arrangement.End
            ) {
                itemsIndexed(todoYearList) { index, year ->
                    ControllerYearButton(
                        year = year,
                        isChecked = yearClickStateList[index],
                    ) {
                        selectYear(year)
                        val arr = BooleanArray(todoYearList.size)
                        arr[index] = true
                        yearClickStateList = arr
                    }
                }
            }
        }
    }
}

@Composable
fun ControllerBox(
    containerColor: Color,
    tintColor: Color = HMColor.Primary,
    sideText: String = "",
    isExistTodo: Boolean = false,
    clickAble: Boolean = true,
    isClicked: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(3.dp)
            .border(
                3.dp,
                if (isClicked) HMColor.Primary else Color.Transparent,
                RoundedCornerShape(2.dp)
            )
            .background(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(2.dp))
                .background(if (isExistTodo && !isClicked) tintColor else containerColor)
                .clickable(enabled = clickAble) { onClick() },
            contentAlignment = Alignment.Center
        ) {
            if (isExistTodo && isClicked) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height

                    // 도형 중심 좌표
                    val centerX = width / 2
                    val centerY = height / 2
                    // 도형 너비,높이
                    val diamondWidth = width * 0.6f
                    val diamondHeight = height * 0.6f

                    val diamondPoints = arrayOf(
                        Offset(centerX, centerY - diamondHeight / 2), // Top point
                        Offset(centerX + diamondWidth / 2, centerY), // Right point
                        Offset(centerX, centerY + diamondHeight / 2), // Bottom point
                        Offset(centerX - diamondWidth / 2, centerY)  // Left point
                    )

                    drawPath(
                        path = Path().apply {
                            moveTo(diamondPoints[0].x, diamondPoints[0].y)
                            lineTo(diamondPoints[1].x, diamondPoints[1].y)
                            lineTo(diamondPoints[2].x, diamondPoints[2].y)
                            lineTo(diamondPoints[3].x, diamondPoints[3].y)
                            close()
                        },
                        color = HMColor.Primary
                    )
                }
            }
            if (!clickAble)
                Text(text = sideText)
        }
    }
}

@Composable
fun ControllerYearButton(
    year: Int,
    isChecked: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(3.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(if (isChecked) HMColor.Primary else HMColor.Gray)
            .clickable { onClick() }
    ) {
        Text(
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp),
            text = year.toString(),
            color = if (isChecked) HMColor.Background else HMColor.Text,
            style = HmStyle.text12
        )
    }
}

@Preview
@Composable
fun ControllerYearButtonPreview() {
    ControllerYearButton(
        year = 2023,
        isChecked = true,
    ) {

    }
}

//@Composable
//@Preview
//fun HistoryControllerPreview() {
//    HistoryController(
//        controllerList = listOf(
//            ControllerWeek(
//                month = 3,
//                controllerDayList = listOf(
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                )
//            ),
//            ControllerWeek(
//                month = 3,
//                controllerDayList = listOf(
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                )
//            ),
//            ControllerWeek(
//                month = 3,
//                controllerDayList = listOf(
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                )
//            ),
//            ControllerWeek(
//                month = 3,
//                controllerDayList = listOf(
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                )
//            ),
//            ControllerWeek(
//                month = 3,
//                controllerDayList = listOf(
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                )
//            ),
//            ControllerWeek(
//                month = 3,
//                controllerDayList = listOf(
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                )
//            ),
//            ControllerWeek(
//                month = 3,
//                controllerDayList = listOf(
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                )
//            ),
//            ControllerWeek(
//                month = 3,
//                controllerDayList = listOf(
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                )
//            ), ControllerWeek(
//                month = 3,
//                controllerDayList = listOf(
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                )
//            ), ControllerWeek(
//                month = 3,
//                controllerDayList = listOf(
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                )
//            ), ControllerWeek(
//                month = 3,
//                controllerDayList = listOf(
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
//                )
//            )
//        ),
//        todoYearList = listOf(2024, 2025),
//    ) {
//
//    }
//}
