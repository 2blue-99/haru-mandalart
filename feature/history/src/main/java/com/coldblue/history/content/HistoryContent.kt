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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.coldblue.history.ControllerDayState
import com.coldblue.history.ControllerTimeState
import com.coldblue.history.ControllerWeek
import com.coldblue.history.HistoryUiState
import java.time.LocalDate

@Composable
fun HistoryContent(
    historyUiState: HistoryUiState.Success,
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
    selectDate: (LocalDate) -> Unit
) {

    val boxClickStateList = remember { MutableList((controllerList.size) * 7) { false } }
    var beforeBoxIndex by remember { mutableIntStateOf(0) }
    val screenWidth = LocalConfiguration.current.screenWidthDp

    Column {
        LazyRow(
            Modifier
                .fillMaxWidth()
                .background(HMColor.Background),
            horizontalArrangement = Arrangement.Start
        ) {
            itemsIndexed(controllerList) { weekIndex, controllerWeek ->
                Column(
                    modifier = Modifier.width((screenWidth/16).dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    ControllerBox(
                        Color = Color.Transparent,
                        sideText = if (controllerWeek.month != null) controllerWeek.month.toString() else "",
                        isClickAble = false
                    ) {}

                    controllerWeek.controllerDayList.forEachIndexed { dayIndex, dayState ->
                        val stateIndex = (weekIndex * 7) + dayIndex
                        Log.e("TAG", "HistoryController: $stateIndex", )
                        when (dayState) {

                            is ControllerDayState.Default -> {
                                ControllerBox(
                                    Color = Color.Transparent,
                                    sideText = dayState.dayWeek,
                                    isClickAble = false
                                ) {}
                            }

                            is ControllerDayState.Empty -> {
                                when (val timeState = dayState.timeState) {

                                    is ControllerTimeState.Past -> {
                                        ControllerBox(
                                            Color = HMColor.Gray,
                                            isToday = boxClickStateList[stateIndex]
                                        ) {
                                            boxClickStateList[beforeBoxIndex] = false
                                            boxClickStateList[stateIndex] = true
                                            beforeBoxIndex = stateIndex
                                            selectDate(timeState.date)
                                        }
                                    }

                                    is ControllerTimeState.Future -> {
                                        ControllerBox(
                                            Color = HMColor.Box,
                                            isToday = boxClickStateList[stateIndex]
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
                                            Color = HMColor.Primary,
                                            isToday = boxClickStateList[stateIndex]
                                        ) {
                                            boxClickStateList[beforeBoxIndex] = false
                                            boxClickStateList[stateIndex] = true
                                            beforeBoxIndex = stateIndex
                                            selectDate(timeState.date)
                                        }
                                    }

                                    is ControllerTimeState.Future -> {
                                        ControllerBox(
                                            Color = HMColor.LightPrimary,
                                            isToday = boxClickStateList[stateIndex]
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
        Row(
            Modifier
                .fillMaxWidth()
                .background(HMColor.Background),
            horizontalArrangement = Arrangement.End
        ) {
            repeat(todoYearList.size) {
                Button(
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "${todoYearList[it]}")
                }
            }
        }
    }
}

@Composable
fun ControllerBox(
    Color: Color,
    sideText: String = "",
    isClickAble: Boolean = true,
    isToday: Boolean = false,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
//            .size(40.dp)
            .padding(3.dp)
            .border(
                3.dp,
                if (isToday) HMColor.Primary else Color.Transparent,
                RoundedCornerShape(2.dp)
            )
            .background(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(2.dp))
                .background(Color)
                .clickable(enabled = isClickAble) { onClick() },
            contentAlignment = Alignment.Center
        ) {
            if (isToday) {
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
            if (!isClickAble)
                Text(text = sideText)
        }
    }
}

@Composable
@Preview
fun HistoryControllerPreview() {
    HistoryController(
        controllerList = listOf(
            ControllerWeek(
                month = 3,
                controllerDayList = listOf(
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                )
            ),
            ControllerWeek(
                month = 3,
                controllerDayList = listOf(
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                )
            ),
            ControllerWeek(
                month = 3,
                controllerDayList = listOf(
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                )
            ),
            ControllerWeek(
                month = 3,
                controllerDayList = listOf(
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                )
            ),
            ControllerWeek(
                month = 3,
                controllerDayList = listOf(
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                )
            ),
            ControllerWeek(
                month = 3,
                controllerDayList = listOf(
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                )
            ),
            ControllerWeek(
                month = 3,
                controllerDayList = listOf(
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                )
            ),
            ControllerWeek(
                month = 3,
                controllerDayList = listOf(
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                )
            ), ControllerWeek(
                month = 3,
                controllerDayList = listOf(
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                )
            ), ControllerWeek(
                month = 3,
                controllerDayList = listOf(
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                )
            ), ControllerWeek(
                month = 3,
                controllerDayList = listOf(
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                    ControllerDayState.Empty(ControllerTimeState.Past(LocalDate.now())),
                )
            )
        ),
        todoYearList = listOf(2024, 2025),
    ) {

    }
}

