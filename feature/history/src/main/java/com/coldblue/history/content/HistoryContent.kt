package com.coldblue.history.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.component.TitleText
import com.coldblue.designsystem.theme.HMColor
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
        ){
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
){
    LazyRow(
        Modifier
            .fillMaxWidth()
            .background(HMColor.Background),
        horizontalArrangement = Arrangement.End
    ){

    }

    Row(
        Modifier
            .fillMaxWidth()
            .background(HMColor.Background),
        horizontalArrangement = Arrangement.End
    ) {
        repeat(todoYearList.size){

        }
    }
}

//@Composable
//@Preview
//fun HistoryControllerPreview(){
//    HistoryController()
//}

//@Preview
//@Composable
//fun HistoryContentPreview() {
//    HistoryContent(
//        listOf(
//            Todo("Sync 블로그 글쓰기", "", groupName = "안드로이드", todoGroupId = -1),
//            Todo("Sync 블로그 글쓰기", "", groupName = "안드로이드", todoGroupId = -1),
//            Todo("DB설계", "", todoGroupId = -1),
//            Todo("디자인 3페이지", "", groupName = "", todoGroupId = -1)
//        ),
//        LocalDate.now(),
//        {}
//    )
//
//}