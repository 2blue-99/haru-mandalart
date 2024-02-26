package com.coldblue.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.designsystem.component.TitleText
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.CurrentGroup
import com.coldblue.model.Todo
import com.coldblue.model.TodoGroup

@Composable
fun TodoScreen(
    todoViewModel: TodoViewModel = hiltViewModel(),
) {
    val todoUiState by todoViewModel.todoUiState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = HMColor.Background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            TodoContentWithState(
                uiState = todoUiState,
                insertTodo = { todo -> todoViewModel.upsertTodo(todo) },
                insertTodoGroup = { todoGroup -> todoViewModel.upsertTodoGroup(todoGroup) },
                insertCurrentGroup = { currentGroup -> todoViewModel.upsertCurrentGroup(currentGroup) },
                onTodoToggle = { todo -> todoViewModel.toggleTodo(todo) }
            )
        }
    }
}

@Composable
private fun TodoContentWithState(
    uiState: TodoUiState,
    insertTodo: (Todo) -> Unit,
    insertTodoGroup: (TodoGroup) -> Unit,
    insertCurrentGroup: (CurrentGroup) -> Unit,
    onTodoToggle: (Todo) -> Unit

) {
    when (uiState) {
        is TodoUiState.Loading -> {
            Text(text = "로딩")
        }

        is TodoUiState.Error -> Text(text = uiState.msg)
        is TodoUiState.Success ->
            TodoContent(
                insertTodo,
                insertTodoGroup,
                insertCurrentGroup,
                uiState.todoList,
                onTodoToggle
            )
    }
}

@Composable
private fun TodoContent(
    insertTodo: (Todo) -> Unit,
    insertTodoGroup: (TodoGroup) -> Unit,
    insertCurrentGroup: (CurrentGroup) -> Unit,
    todoList: List<Todo>,
    onTodoToggle: (Todo) -> Unit

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
                    .height(120.dp)
                    .padding(vertical = 8.dp)
                    .background(HMColor.SubText)
            )
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(vertical = 16.dp)
                    .background(HMColor.SubText)
            )
        }
        item {
            TitleText("오늘 할 일")
        }
        items(todoList) { todo ->
            TodoItem(todo, onTodoToggle)
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    onTodoToggle: (Todo) -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
        .border(width = 1.5.dp, color = HMColor.Primary, shape = RoundedCornerShape(10.dp))
        .clickable {
        }
        .background(HMColor.Box)) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Start
        ) {
            Checkbox(checked = todo.isDone, onCheckedChange = { onTodoToggle(todo) })
            Column {
                Text(text = todo.title)
                Row {
                    Text(text = todo.groupName, style = HmStyle.content, color = HMColor.Primary)
                    Text(text = todo.time, style = HmStyle.content)
                }
            }
        }
    }


}

@Preview
@Composable
fun TodoContentPreView() {
    TodoContent(
        {},
        {},
        {},
        listOf(
            Todo("Sync 블로그 글쓰기", "", groupName = "안드로이드"),
            Todo("Sync 블로그 글쓰기", "", groupName = "안드로이드", time = "오전 11:35"),
            Todo("DB설계", ""),
            Todo("디자인 3페이지", "", groupName = "", time = "오전 08:00")
        ),
        {},
    )
}
