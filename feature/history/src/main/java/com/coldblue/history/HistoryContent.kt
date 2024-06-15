package com.coldblue.history

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.MandaTodo
import java.time.LocalDate

@Composable
fun HistoryContent(
    historyUIState: HistoryUIState.Success,
    navigateToBackStack: () -> Unit,
    changeYear: (String) -> Unit,
    changeDay: (String) -> Unit,
    updateTodo: (MandaTodo) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HMColor.Background)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        HistoryGraph()

        HistoryTitleBar()



//        HistoryController(
//            today = ,
//            historyController = historyUIState.historyController,
//            todoYearList = ,
//            selectDate = ,
//            selectYear =
//        )

        HistoryTodo()
    }
}

@Composable
fun HistoryGraph(){



}

@Composable
fun GraphBar(
    allHeight: Dp,
    doneHeight: Dp,
    allCount: Int,
    doneCount: Int,
    name: String,
    lightColor: Color,
    darkColor: Color,
    selected: Boolean,
){
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(2.dp))
            .background(color = if (selected) darkColor else Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row(
            Modifier.padding(horizontal = 4.dp).padding(top = 4.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Box(
                modifier = Modifier
                    .zIndex(0f)
                    .height(allHeight)
                    .clip(RoundedCornerShape(2.dp))
                    .border(
                        width = 0.4.dp,
                        color = if (selected) HMColor.Background else lightColor,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .background(lightColor)
                    .padding(horizontal = 2.dp),
                contentAlignment = Alignment.TopCenter
            ){
                Text(text = allCount.toString(), style = HmStyle.text8, color = HMColor.Background)
            }
            Box(
                modifier = Modifier
                    .offset(x = (-0.6).dp)
                    .zIndex(1f)
                    .height(doneHeight)
                    .clip(RoundedCornerShape(2.dp))
                    .border(
                        width = 0.4.dp,
                        color = if (selected) HMColor.Background else darkColor,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .background(darkColor)
                    .padding(horizontal = 2.dp),
                contentAlignment = Alignment.TopCenter
            ){
                Text(text = doneCount.toString(), style = HmStyle.text8, color = HMColor.Background)
            }
        }
        Spacer(modifier = Modifier
            .height(0.4.dp)
            .fillMaxWidth()
            .background(HMColor.DarkGray))
        if(selected){
            Icon(imageVector = Icons.Default.Favorite, contentDescription = "Check")
        }else{

        }
    }
}

@Preview
@Composable
fun GraphBarPreview(){
    GraphBar(60.dp,40.dp,40,30,"푸푸",HMColor.LightPrimary, HMColor.Primary, true)
}

@Composable
fun HistoryTitleBar(){

}

@Composable
fun HistoryController(
    today: LocalDate,
    historyController: HistoryController,
    todoYearList: List<Int>,
    selectDate: (LocalDate) -> Unit,
    selectYear: (Int) -> Unit,
) {
//    var clickedDate by remember { mutableStateOf(today) }
//    var clickedYear by remember { mutableStateOf(today.year) }
//
//    val screenWidth = LocalConfiguration.current.screenWidthDp
//    val presentLocalDate = LocalDate.now()
//
//    fun dateController(year: Int) {
//        clickedYear = year
//        if (year == presentLocalDate.year) {
//            clickedDate = presentLocalDate
//            selectDate(presentLocalDate)
//        } else {
//            clickedDate = LocalDate.MIN
//            selectDate(LocalDate.MIN)
//        }
//    }
//
//    Column {
//        Row {
//            Column(
//                modifier = Modifier.width((screenWidth / 16).dp)
//            ) {
//                ControllerBox(
//                    containerColor = Color.Transparent,
//                    sideText = "",
//                    clickAble = false
//                ) {}
//                historyController.controller.forEach {
//                    if (it is ControllerDayState.Default)
//                        ControllerBox(
//                            containerColor = Color.Transparent,
//                            sideText = it.dayWeek,
//                            clickAble = false
//                        ) {}
//                }
//            }
//            LazyRow(
//                Modifier
//                    .fillMaxWidth()
//                    .background(HMColor.Background),
//                horizontalArrangement = Arrangement.Start
//            ) {
//                itemsIndexed(historyController.slice(1..<historyController.size)) { weekIndex, controllerWeek ->
//                    Column(
//                        modifier = Modifier.width((screenWidth / 16).dp),
//                        verticalArrangement = Arrangement.Top
//                    ) {
//                        ControllerBox(
//                            containerColor = Color.Transparent,
//                            sideText = if (controllerWeek.month != null) stringResource(id = R.string.history_month, controllerWeek.month) else "",
//                            clickAble = false
//                        ) {}
//
//                        controllerWeek.controllerDayList.forEachIndexed { dayIndex, dayState ->
//
//                            when (dayState) {
//
//                                is ControllerDayState.Default -> {
//                                    ControllerBox(
//                                        containerColor = Color.Transparent,
//                                        sideText = dayState.dayWeek,
//                                        clickAble = false
//                                    ) {}
//                                }
//
//                                is ControllerDayState.Empty -> {
//                                    when (val timeState = dayState.timeState) {
//
//                                        is ControllerTimeState.Past -> {
//                                            ControllerBox(
//                                                containerColor = HMColor.Gray,
//                                                tintColor = HMColor.Gray,
//                                                isClicked = clickedDate == timeState.date
//                                            ) {
//                                                selectDate(timeState.date)
//                                                clickedDate = timeState.date
//                                            }
//                                        }
//
//                                        is ControllerTimeState.Present -> {
//                                            ControllerBox(
//                                                containerColor = HMColor.Gray,
//                                                tintColor = HMColor.Gray,
//                                                isClicked = clickedDate == timeState.date
//                                            ) {
//                                                selectDate(timeState.date)
//                                                clickedDate = timeState.date
//                                            }
//                                        }
//
//                                        is ControllerTimeState.Future -> {
//                                            ControllerBox(
//                                                containerColor = HMColor.Box,
//                                                tintColor = HMColor.Box,
//                                                isClicked = clickedDate == timeState.date
//                                            ) {
//                                                selectDate(timeState.date)
//                                                clickedDate = timeState.date
//                                            }
//                                        }
//                                    }
//                                }
//
//                                is ControllerDayState.Exist -> {
//                                    when (val timeState = dayState.timeState) {
//
//                                        is ControllerTimeState.Past -> {
//                                            ControllerBox(
//                                                containerColor = HMColor.Box,
//                                                tintColor = HMColor.Primary,
//                                                isExistTodo = true,
//                                                isClicked = clickedDate == timeState.date
//                                            ) {
//                                                selectDate(timeState.date)
//                                                clickedDate = timeState.date
//                                            }
//                                        }
//
//                                        is ControllerTimeState.Present -> {
//                                            ControllerBox(
//                                                containerColor = HMColor.Box,
//                                                tintColor = HMColor.Primary,
//                                                isExistTodo = true,
//                                                isClicked = clickedDate == timeState.date
//                                            ) {
//                                                selectDate(timeState.date)
//                                                clickedDate = timeState.date
//                                            }
//                                        }
//
//                                        is ControllerTimeState.Future -> {
//                                            ControllerBox(
//                                                containerColor = HMColor.Background,
//                                                tintColor = HMColor.LightPrimary,
//                                                isExistTodo = true,
//                                                isClicked = clickedDate == timeState.date
//                                            ) {
//                                                selectDate(timeState.date)
//                                                clickedDate = timeState.date
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        LazyRow(
//            Modifier
//                .fillMaxWidth()
//                .background(HMColor.Background),
//            horizontalArrangement = Arrangement.End
//        ) {
//            itemsIndexed(todoYearList) { index, year ->
//                ControllerYearButton(
//                    year = year,
//                    isChecked = clickedYear == year,
//                ) {
//                    selectYear(year)
//                    dateController(year)
//                }
//            }
//        }
//    }
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
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
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
                Text(
                    text = sideText,
                    style = HmStyle.text8,
                    overflow = TextOverflow.Visible,
                    textAlign = TextAlign.Center
                )
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

@Composable
fun HistoryTodo(){

}

