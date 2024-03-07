package com.coldblue.history.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.component.TitleText
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.model.Todo
import java.time.LocalDate

@Composable
fun HistoryContent(
    todoList: List<Todo>,
    selectedDate: LocalDate,
    selectDate: (LocalDate) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(HMColor.Background)
            .padding(16.dp),
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(HMColor.SubText)
            )
        }

        item {
            TitleText(text = "기록")
        }
        items(todoList) {
//            TodoItem(todo = it, onTodoToggle = {}, showSheet = {})
        }

    }

}

@Preview
@Composable
fun HistoryContentPreview() {
    HistoryContent(
        listOf(
            Todo("Sync 블로그 글쓰기", "", groupName = "안드로이드", todoGroupId = -1),
            Todo("Sync 블로그 글쓰기", "", groupName = "안드로이드", todoGroupId = -1),
            Todo("DB설계", "", todoGroupId = -1),
            Todo("디자인 3페이지", "", groupName = "", todoGroupId = -1)
        ),
        LocalDate.now(),
        {}
    )

}