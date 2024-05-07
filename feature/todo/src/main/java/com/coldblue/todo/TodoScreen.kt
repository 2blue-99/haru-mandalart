package com.coldblue.todo

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.data.util.asMyTime
import com.coldblue.data.util.getDisplayName
import com.coldblue.designsystem.component.CenterTitleText
import com.coldblue.designsystem.component.TitleText
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.Todo
import com.coldblue.todo.uistate.BottomSheetUiState
import com.coldblue.todo.uistate.ContentState
import com.coldblue.todo.uistate.DEFAULT_TODO
import com.coldblue.todo.uistate.TodoUiState
import com.google.gson.Gson
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun TodoScreen(
    todoViewModel: TodoViewModel = hiltViewModel(),
    navigateToTodoEdit: (Int, String, String, String) -> Unit
) {
    val todoUiState by todoViewModel.todoUiState.collectAsStateWithLifecycle()
    val bottomSheetUiSate by todoViewModel.bottomSheetUiSate.collectAsStateWithLifecycle()
    val dateState by todoViewModel.dateSate.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = HMColor.Background,
        floatingActionButton = {
            FloatingActionButton(
                containerColor = HMColor.Primary,
                contentColor = HMColor.Background,
                shape = CircleShape,
                onClick = {
                    todoViewModel.showSheet(ContentState.Todo(todo = Todo("", originGroupId = 0)))
                },
                content = {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Todo",
                        tint = HMColor.Background
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            TodoContentWithState(
                uiState = todoUiState,
                bottomSheetUiSate = bottomSheetUiSate,
                showSheet = { content -> todoViewModel.showSheet(content) },
                onTodoToggle = { todo -> todoViewModel.toggleTodo(todo) },
                date = dateState,
                selectDate = { date -> todoViewModel.selectDate(date) },
            )
        }
    }
}

@Composable
private fun TodoContentWithState(
    uiState: TodoUiState,
    bottomSheetUiSate: BottomSheetUiState,
    showSheet: (ContentState) -> Unit,
    onTodoToggle: (Todo) -> Unit,
    date: LocalDate,
    selectDate: (LocalDate) -> Unit,

) {
    when (uiState) {
        is TodoUiState.Loading -> {}
        is TodoUiState.Error -> {}
        is TodoUiState.Success ->
            TodoContent(
                bottomSheetUiSate,
                showSheet,
                uiState.todoList,
                onTodoToggle,
                date,
                selectDate,
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TodoContent(
    bottomSheetUiSate: BottomSheetUiState,
    showSheet: (ContentState) -> Unit,
    todoList: List<Todo>,
    onTodoToggle: (Todo) -> Unit,
    date: LocalDate,
    selectDate: (LocalDate) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(bottomSheetUiSate) {
        when (bottomSheetUiSate) {
            is BottomSheetUiState.Up -> {
                sheetState.expand()
            }

            is BottomSheetUiState.Down -> {
                sheetState.hide()
            }
        }

    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(HMColor.Background)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
    ) {
        item {
            WeeklyDatePicker(date, selectDate)

        }
        item {
            CenterTitleText("하루,만다라트")
        }

        item {
            TitleText("오늘 할 일")
        }
        if (todoList.isEmpty()) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    textAlign = TextAlign.Center,
                    text = "할 일을 추가해 주세요!",
                    style = HmStyle.text20,
                    color = HMColor.SubLightText
                )
            }

        } else {
            items(todoList.filter { !it.isDone }) { todo ->
                TodoItem(todo, onTodoToggle, showSheet)
            }
            item {
                Text(text = "완료됨")
            }
            items(todoList.filter { it.isDone }) { todo ->
                TodoItem(todo, onTodoToggle, showSheet)
            }
        }
    }
}

@Composable
fun WeeklyDatePicker(
    today: LocalDate,
    selectDate: (LocalDate) -> Unit
) {
    val weekDates = generateWeekDates(today)
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "${today.year}년 ${today.month.getDisplayName(TextStyle.FULL, Locale.KOREA)}",
            style = HmStyle.text16
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(HMColor.Box)
        ) {
            weekDates.forEach { date ->
                val backGround = if (date == today) HMColor.Primary else HMColor.Box
                val textColor = if (date == today) HMColor.Background else HMColor.SubLightText

                Surface(
                    shape = RoundedCornerShape(5.dp),
                    color = backGround,
                    contentColor = HMColor.Primary,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            selectDate(date)
                        }
                        .weight(1f)

                ) {
                    Column(
                        modifier = Modifier
                            .background(backGround),
                        Arrangement.Center,
                        Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp),
                            text = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREA),
                            color = textColor
                        )
                        Text(
                            modifier = Modifier.padding(bottom = 4.dp),
                            text = "${date.dayOfMonth}", color = textColor
                        )
                    }
                }
            }
        }

    }

}

fun generateWeekDates(startDate: LocalDate): List<LocalDate> {
    val dates = mutableListOf<LocalDate>()
    var currentDate = startDate
    while (currentDate.dayOfWeek != DayOfWeek.SUNDAY) {
        currentDate = currentDate.minusDays(1)
    }
    repeat(7) {
        dates.add(currentDate)
        currentDate = currentDate.plusDays(1)
    }
    return dates
}


@Composable
fun TodoItem(
    todo: Todo,
    onTodoToggle: (Todo) -> Unit,
    showSheet: (ContentState) -> Unit,
    navigateToTodoEdit: (Int, String, String, String) -> Unit = { _, _, _, _ -> },
    date: LocalDate = LocalDate.now()
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
        .border(width = 1.5.dp, color = HMColor.Primary, shape = RoundedCornerShape(10.dp))
        .background(HMColor.Box)
        .clickable {
            val time = todo.time
            val myTime = if (time == null) {
                LocalTime
                    .now()
                    .asMyTime()
            } else {
                todo.time!!
                    .asMyTime()
                    .copy(isEdit = true)
            }
            showSheet(ContentState.Todo(todo = todo))
            navigateToTodoEdit(
                if (todo.id == 0) DEFAULT_TODO else todo.id,
                todo.title.ifEmpty { DEFAULT_TODO.toString() },
                Uri.encode(Gson().toJson(myTime)),
                date.toString()
            )
        }
    )
    {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Start,
        ) {
            Checkbox(checked = todo.isDone, onCheckedChange = { onTodoToggle(todo) })
            Column {
                if (todo.isDone) {
                    Text(text = todo.title, textDecoration = TextDecoration.LineThrough)
                } else {
                    Text(text = todo.title)
                }
                Row {
                    Text(
                        modifier = Modifier.padding(end = 4.dp),
                        text = todo.groupName,
                        style = HmStyle.text12,
                        color = HMColor.Primary
                    )
                    if (todo.time != null) {
                        Text(
                            text = todo.time!!.getDisplayName(),
                            style = HmStyle.text12,
                            color = if (!todo.isDone && todo.time!!.isBefore(LocalTime.now())) HMColor.Dark.Red else HMColor.Text
                        )
                    }
                }

            }
        }
    }

}


//@Preview
//@Composable
//fun TodoContentPreView() {
//    TodoContent(
//
//    )
//}
