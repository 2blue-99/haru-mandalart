package com.coldblue.todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.designsystem.theme.HMColor
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
) {
    when (uiState) {
        is TodoUiState.Loading -> {}
        is TodoUiState.Error -> Text(text = uiState.msg)
        is TodoUiState.Success ->
            TodoContent(insertTodo, insertTodoGroup, insertCurrentGroup)
    }
}

@Composable
private fun TodoContent(
    insertTodo: (Todo) -> Unit,
    insertTodoGroup: (TodoGroup) -> Unit,
    insertCurrentGroup: (CurrentGroup) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Button(onClick = { insertTodo(Todo("d", "dd")) }) {
            Text(text = "todo 추가")
        }
        Button(onClick = { insertTodoGroup(TodoGroup("안드로이드")) }) {
            Text(text = "todo그룹 추가")
        }
        Button(onClick = { insertCurrentGroup(CurrentGroup(1)) }) {
            Text(text = "todo그룹 추가")
        }

    }

}