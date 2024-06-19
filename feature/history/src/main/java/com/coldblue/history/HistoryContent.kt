package com.coldblue.history

import android.health.connect.datatypes.units.Percentage
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.component.HMChip
import com.coldblue.designsystem.component.HMTopBar
import com.coldblue.designsystem.iconpack.Check
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.MandaTodo
import com.coldblue.model.TodoGraph
import com.orhanobut.logger.Logger
import java.time.LocalDate

@Composable
fun HistoryContent(
    historyUIState: HistoryUIState.Success,
    navigateToBackStack: () -> Unit,
    changeCurrentIndex: (Int) -> Unit,
    changeYear: (String) -> Unit,
    changeDay: (String) -> Unit,
    updateTodo: (MandaTodo) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HMColor.Background),
        verticalArrangement = Arrangement.Top,
    ) {

        HMTopBar(title = "기록") {
            navigateToBackStack()
        }

        Spacer(modifier = Modifier.height(10.dp))

        HistoryGraph(
            todoGraph = historyUIState.todoGraph,
            changeCurrentIndex = changeCurrentIndex
        )

        Spacer(modifier = Modifier.height(10.dp))

        HistoryTitleBar(
            titleBar = historyUIState.titleBar
        )

        Spacer(modifier = Modifier.height(20.dp))


        HistoryController(
            historyController = historyUIState.historyController,
        )

        HistoryTodo()
    }
}

@Composable
fun HistoryGraph(
    todoGraph: List<TodoGraph>,
    changeCurrentIndex: (Int) -> Unit = {}
){
    Logger.d(todoGraph)

    val width = LocalConfiguration.current.screenWidthDp
    val maxHeight = (width / 2.5).toInt()
    val maxCount = todoGraph.maxOfOrNull { it.allCount } ?: 0
    val weight = maxHeight / maxCount
    var selected by remember { mutableIntStateOf(0) }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        todoGraph.forEachIndexed{ index, it ->
            val darkColor = HistoryUtil.indexToDarkColor(it.colorIndex)
            val lightColor = HistoryUtil.indexToLightColor(it.colorIndex)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(color = if (index == selected) darkColor else Color.Transparent)
                    .padding(top = 4.dp)
                    .clickable {
                        if (it.name != "") {
                            changeCurrentIndex(index)
                            selected = index
                        }
                    }
            ) {
                Column(
                    modifier = Modifier
                        .height(height = maxHeight.dp)
                        .padding(horizontal = 2.dp),
                    verticalArrangement = Arrangement.Top
                ){
                    GraphBarGroup(
                        height = maxHeight.dp,
                        allHeight = (it.allCount*weight).dp,
                        doneHeight = (it.doneCount*weight).dp,
                        allCount = it.allCount,
                        doneCount = it.doneCount,
                        darkColor = darkColor,
                        lightColor = lightColor,
                        selected = index == selected
                    )
                }

                Spacer(modifier = Modifier
                    .height(0.4.dp)
                    .fillMaxWidth()
                    .background(HMColor.SubDarkText))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier
                        .width(0.4.dp)
                        .fillMaxHeight()
                        .padding(vertical = 8.dp)
                        .background(HMColor.Gray))
                    Box(
                        modifier = Modifier.padding(horizontal = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if(index == selected){
                            Icon(imageVector = IconPack.Check, tint = HMColor.Background, contentDescription = "Check")
                        }else{
                            Text(text = it.name, style = HmStyle.text10, color = darkColor, maxLines = 2, overflow = TextOverflow.Ellipsis)
                        }
                    }
                    Spacer(modifier = Modifier
                        .width(0.4.dp)
                        .fillMaxHeight()
                        .padding(vertical = 8.dp)
                        .background(HMColor.Gray))
                }
            }
        }
    }
}

@Preview
@Composable
fun HistoryGraphPreview(){
    HistoryGraph(listOf(
        TodoGraph("탈모", 150,60,0),
        TodoGraph("탈모", 50,10,1),
        TodoGraph("탈모", 10,10,2),
        TodoGraph("탈모", 30,20,3),
        TodoGraph("탈모", 50,40,4),
        TodoGraph("탈모", 10,5,5),
        TodoGraph("탈모", 0,0,6),
        TodoGraph("탈모", 15,5,7),
    ))
}


@Composable
fun GraphBarGroup(
    height: Dp,
    allHeight: Dp,
    doneHeight: Dp,
    allCount: Int,
    doneCount: Int,
    darkColor: Color,
    lightColor: Color,
    selected: Boolean,
){
    val empty = allCount == 0 && doneCount == 0
    Row(
        modifier = Modifier
            .height(height)
            .offset(x = (1).dp),
        verticalAlignment = Alignment.Bottom
    ){
        Box(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .weight(3f)
                .zIndex(0f)
        ) {
            GraphBar(
                height = allHeight,
                count = allCount,
                selected = selected,
                color = if(empty) Color.Transparent else lightColor
            )
        }
        Box(
            modifier = Modifier
                .weight(3f)
                .offset(x = (-2).dp)
                .zIndex(1f)
        ){
            GraphBar(
                height = doneHeight,
                count = doneCount,
                selected = selected,
                color = if(empty) Color.Transparent else darkColor
            )
        }
        Box(modifier = Modifier.weight(1f))
    }


//    Column(
//        modifier = Modifier
//            .width(width),
//        verticalArrangement = Arrangement.Bottom,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Row(
//            Modifier
//                .padding(horizontal = 4.dp)
//                .padding(top = 4.dp),
//            verticalAlignment = Alignment.Bottom,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Column(
//                modifier = Modifier.weight(1f)
//            ) {
//                Text(text = allCount.toString(), style = HmStyle.text8, color = HMColor.Background)
//                Box(
//                    modifier = Modifier
//                        .zIndex(0f)
//                        .height(allHeight)
//                        .clip(RoundedCornerShape(2.dp))
//                        .border(
//                            width = 0.4.dp,
//                            color = if (selected) HMColor.Background else lightColor,
//                            shape = RoundedCornerShape(2.dp)
//                        )
//                        .background(lightColor)
//                        .padding(horizontal = 2.dp),
//                )
//            }
//            Column(
//                modifier = Modifier.weight(1f)
//            ) {
//                Text(text = doneCount.toString(), style = HmStyle.text8, color = HMColor.Background)
//                Box(
//                    modifier = Modifier
//                        .offset(x = (-0.6).dp)
//                        .zIndex(1f)
//                        .height(doneHeight)
//                        .clip(RoundedCornerShape(2.dp))
//                        .border(
//                            width = 0.4.dp,
//                            color = if (selected) HMColor.Background else darkColor,
//                            shape = RoundedCornerShape(2.dp)
//                        )
//                        .background(darkColor)
//                        .padding(horizontal = 2.dp),
//                )
//            }
//        }
//        Spacer(modifier = Modifier
//            .height(0.4.dp)
//            .fillMaxWidth()
//            .background(HMColor.DarkGray))
//    }
}

@Preview
@Composable
fun GraphBarGroupPreview(){
    GraphBarGroup(100.dp, 60.dp, 50.dp, 50,40,HMColor.Primary, HMColor.LightPrimary, true)
}

@Composable
fun GraphBar(
    height: Dp,
    count: Int,
    selected: Boolean,
    color: Color,
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(text = count.toString(), style = HmStyle.text8, color = if (selected) HMColor.Background else color)
        Box(
            modifier = Modifier
                .zIndex(0f)
                .height(height)
                .fillMaxWidth()
                .clip(RoundedCornerShape(2.dp))
                .border(
                    width = 0.4.dp,
                    color = if (selected) HMColor.Background else color,
                    shape = RoundedCornerShape(2.dp)
                )
                .background(color)
                .padding(horizontal = 2.dp),
        )
    }
}
@Preview(widthDp = 50)
@Composable
fun GraphBarPreview(){
    GraphBar(100.dp, 50, true, HMColor.Primary)
}

@Composable
fun HistoryTitleBar(
    titleBar: TitleBar
){
    Logger.d(titleBar)

    val color = HistoryUtil.indexToDarkColor(titleBar.colorIndex)
    val colors = arrayOf(0.7f to color, 1f to HMColor.Background)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = Brush.horizontalGradient(colorStops = colors,))
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(5f)
        ) {
            Text(text = titleBar.name, style = HmStyle.text40, color = HMColor.Background, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(
                text = if(titleBar.startDate.isNotBlank()) "${titleBar.startDate}에 시작했어요." else "할일을 추가해보세요!",
                style = HmStyle.text10,
                color = HMColor.Background
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if(titleBar.rank != null) {
                when (titleBar.rank) {
                    0 -> Image(
                        painter = painterResource(id = com.coldblue.designsystem.R.drawable.gold),
                        contentDescription = "gold"
                    )
                    1-> Image(
                         painter = painterResource(id = com.coldblue.designsystem.R.drawable.silver),
                        contentDescription = "silver"
                    )
                    2 -> Image(
                        painter = painterResource(id = com.coldblue.designsystem.R.drawable.bronze),
                        contentDescription = "bronze"
                    )
                    else -> {}
                }
            }
        }
    }
}

@Preview(widthDp = 400)
@Composable
fun HistoryTitleBarPreview(){
    HistoryTitleBar(TitleBar(name = "Hello", startDate = "2024-10-10", rank = 1, colorIndex = 1))
}

@Composable
fun HistoryController(
    historyController: HistoryController
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(imageVector = Icons.Default.CheckCircle, tint = historyController.color, contentDescription = "Check")
                Text(text = "완료 ~~", color = HMColor.SubDarkText)
                PercentageCircle(color = historyController.color, percentage = historyController.donePercentage)
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(imageVector = Icons.Default.ThumbUp, tint = historyController.color, contentDescription = "Check")
                Text(text = "${historyController.continueDate}일 연속 Clear!", color = HMColor.SubDarkText)
            }
        }
        Controller(
            historyController = historyController.controller,
            todoYearList = historyController.years,
            selectDate = {},
            selectYear = {}
        )
    }
}



@Preview
@Composable
fun HistoryControllerPreview(){
    HistoryController(
        HistoryController(HMColor.Primary, 100, 50, 50, 1, listOf(), years = listOf())
    )
}

@Composable
fun PercentageCircle(
    color: Color,
    percentage: Int
){
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(color)
    ) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = percentage.toString(),
            style = HmStyle.text10,
            color = HMColor.Background
        )
    }
}

@Preview
@Composable
fun PercentageCirclePreview(){
    PercentageCircle(
        HMColor.Primary, 30
    )
}

@Composable
fun Controller(
    historyController: List<Controller>,
    todoYearList: List<String>,
    selectDate: (LocalDate) -> Unit,
    selectYear: (String) -> Unit,
){
    val today = LocalDate.now()
    var clickedDate by remember { mutableStateOf(today) }
    var clickedYear by remember { mutableStateOf(today.year) }

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val presentLocalDate = LocalDate.now()

    val dayOfWeekList = historyController.first()
    val todoList = historyController.slice(1 until historyController.size)

    fun dateController(year: Int) {
        clickedYear = year
        if (year == presentLocalDate.year) {
            clickedDate = presentLocalDate
            selectDate(presentLocalDate)
        } else {
            clickedDate = LocalDate.MIN
            selectDate(LocalDate.MIN)
        }
    }

    Column {
        Row {

            // 요일 Column
            Column(
                // TODO  Weight(1f)
                modifier = Modifier.width((screenWidth / 16).dp)
            ) {
                ControllerBox(
                    containerColor = Color.Transparent,
                    sideText = "",
                    clickAble = false,
                    onClick = {}
                )
                dayOfWeekList.controllerDayList.forEach {
                    if (it is ControllerDayState.Default)
                        ControllerBox(
                            containerColor = Color.Transparent,
                            sideText = it.dayWeek,
                            clickAble = false,
                            onClick = {}
                        )
                }
            }
            LazyRow(
                Modifier
                    .fillMaxWidth()
                    .background(HMColor.Background),
                horizontalArrangement = Arrangement.Start
            ) {
                itemsIndexed(historyController.slice(1..<historyController.size)) { weekIndex, controllerWeek ->
                    Column(
                        modifier = Modifier.width((screenWidth / 16).dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        ControllerBox(
                            containerColor = Color.Transparent,
                            sideText = if (controllerWeek.month != null) "${controllerWeek.month}월" else "",
//                            sideText = if (controllerWeek.month != null) stringResource(id = R.string.history_month, controllerWeek.month) else "",
                            clickAble = false
                        ) {}

                        controllerWeek.controllerDayList.forEachIndexed { dayIndex, dayState ->

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
                                                tintColor = HMColor.Gray,
                                                isClicked = clickedDate == timeState.date
                                            ) {
                                                selectDate(timeState.date)
                                                clickedDate = timeState.date
                                            }
                                        }

                                        is ControllerTimeState.Present -> {
                                            ControllerBox(
                                                containerColor = HMColor.Gray,
                                                tintColor = HMColor.Gray,
                                                isClicked = clickedDate == timeState.date
                                            ) {
                                                selectDate(timeState.date)
                                                clickedDate = timeState.date
                                            }
                                        }

                                        is ControllerTimeState.Future -> {
                                            ControllerBox(
                                                containerColor = HMColor.Box,
                                                tintColor = HMColor.Box,
                                                isClicked = clickedDate == timeState.date
                                            ) {
                                                selectDate(timeState.date)
                                                clickedDate = timeState.date
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
                                                isClicked = clickedDate == timeState.date
                                            ) {
                                                selectDate(timeState.date)
                                                clickedDate = timeState.date
                                            }
                                        }

                                        is ControllerTimeState.Present -> {
                                            ControllerBox(
                                                containerColor = HMColor.Box,
                                                tintColor = HMColor.Primary,
                                                isExistTodo = true,
                                                isClicked = clickedDate == timeState.date
                                            ) {
                                                selectDate(timeState.date)
                                                clickedDate = timeState.date
                                            }
                                        }

                                        is ControllerTimeState.Future -> {
                                            ControllerBox(
                                                containerColor = HMColor.Background,
                                                tintColor = HMColor.LightPrimary,
                                                isExistTodo = true,
                                                isClicked = clickedDate == timeState.date
                                            ) {
                                                selectDate(timeState.date)
                                                clickedDate = timeState.date
                                            }
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
                    isChecked = clickedYear.toString() == year,
                ) {
                    selectYear(year)
                    dateController(year.toInt())
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
    year: String,
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

