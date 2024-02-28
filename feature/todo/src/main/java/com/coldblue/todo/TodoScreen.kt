package com.coldblue.todo

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.component.CenterTitleText
import com.coldblue.designsystem.component.TitleText
import com.coldblue.designsystem.iconpack.Check
import com.coldblue.designsystem.iconpack.Plus
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
    val todoGroupList by todoViewModel.todoGroupList.collectAsStateWithLifecycle()
    val bottomSheetUiSate by todoViewModel.bottomSheetUiSate.collectAsStateWithLifecycle()


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
                bottomSheetUiSate = bottomSheetUiSate,
                showSheet = { content -> todoViewModel.showSheet(content) },
                hideSheet = { todoViewModel.hideSheet() },
                insertTodo = { todo -> todoViewModel.upsertTodo(todo) },
                upsertTodoGroup = { todoGroup -> todoViewModel.upsertTodoGroup(todoGroup) },
                insertCurrentGroup = { currentGroup -> todoViewModel.upsertCurrentGroup(currentGroup) },
                onTodoToggle = { todo -> todoViewModel.toggleTodo(todo) },
                todoGroupList = todoGroupList,
                upsertCurrentGroup = { group -> todoViewModel.upsertCurrentGroup(group) },

                )
        }
    }
}

@Composable
private fun TodoContentWithState(
    uiState: TodoUiState,
    bottomSheetUiSate: BottomSheetUiState,
    showSheet: (ContentState) -> Unit,
    hideSheet: () -> Unit,
    insertTodo: (Todo) -> Unit,
    upsertTodoGroup: (TodoGroup) -> Unit,
    insertCurrentGroup: (CurrentGroup) -> Unit,
    todoGroupList: List<TodoGroup>,
    onTodoToggle: (Todo) -> Unit,
    upsertCurrentGroup: (CurrentGroup) -> Unit

) {
    when (uiState) {
        is TodoUiState.Loading -> {
            Text(text = "로딩")
        }

        is TodoUiState.Error -> Text(text = uiState.msg)
        is TodoUiState.Success ->
            TodoContent(
                bottomSheetUiSate,
                showSheet,
                hideSheet,
                insertTodo,
                upsertTodoGroup,
                insertCurrentGroup,
                uiState.currentGroupList,
                uiState.todoList,
                todoGroupList,
                onTodoToggle,
                upsertCurrentGroup
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TodoContent(
    bottomSheetUiSate: BottomSheetUiState,
    showSheet: (ContentState) -> Unit,
    hideSheet: () -> Unit,
    insertTodo: (Todo) -> Unit,
    upsertTodoGroup: (TodoGroup) -> Unit,
    insertCurrentGroup: (CurrentGroup) -> Unit,
    currentGroupList: List<CurrentGroupState>,
    todoList: List<Todo>,
    todoGroupList: List<TodoGroup>,
    onTodoToggle: (Todo) -> Unit,
    upsertCurrentGroup: (CurrentGroup) -> Unit,


) {
    val sheetState = rememberModalBottomSheetState()
    if (bottomSheetUiSate is BottomSheetUiState.Up) {
        GroupBottomSheet(
            content = bottomSheetUiSate.content,
            sheetState = sheetState,
            onDismissRequest = { hideSheet() },
            todoGroupList = todoGroupList,
            currentGroupList = currentGroupList,
            upsertCurrentGroup = upsertCurrentGroup,
            upsertTodoGroup = upsertTodoGroup
        )
    }
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
            CenterTitleText("하루,만다라트")
        }
        item {
            HaruManda(currentGroupList, showSheet)
        }
        item {
            TitleText("오늘 할 일")
        }
        items(todoList.filter { !it.isDone }) { todo ->
            TodoItem(todo, onTodoToggle)
        }
        item {
            Text(text = "완료됨")
        }
        items(todoList.filter { it.isDone }) { todo ->
            TodoItem(todo, onTodoToggle)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupBottomSheet(
    content: ContentState,
    sheetState: SheetState,
    todoGroupList: List<TodoGroup>,
    currentGroupList: List<CurrentGroupState>,
    onDismissRequest: () -> Unit,
    upsertCurrentGroup: (CurrentGroup) -> Unit,
    upsertTodoGroup: (TodoGroup) -> Unit,

) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismissRequest() },
        containerColor = HMColor.Background
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = content.title, modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 28.dp),
                textAlign = TextAlign.Center, style = HmStyle.text16, fontWeight = FontWeight.Bold
            )
            when (content) {
                is ContentState.Group -> {
                    TodoGroupBottomSheetWithState(
                        content.currentGroup,
                        todoGroupList,
                        currentGroupList,
                        upsertCurrentGroup,
                        upsertTodoGroup
                    )
                }

                is ContentState.Todo -> {
//                    content.content(content)

                }
            }
        }
    }
}

@Composable
fun HaruManda(
    currentGroupList: List<CurrentGroupState>,
    showSheet: (ContentState) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .aspectRatio(1F),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ) {
        items(currentGroupList) { group ->
            OutlinedButton(
                contentPadding = PaddingValues(
                    12.dp
                ),
                enabled = group !is CurrentGroupState.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
                    .padding(8.dp)
                    .border(
                        width = 1.5.dp,
                        color = group.border,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .background(group.backGround),
                onClick = {
                    showSheet(ContentState.Group(currentGroup = group))
                },
                shape = RoundedCornerShape(10.dp)
            ) {
                when (group) {
                    is CurrentGroupState.Empty -> {
                        Icon(
                            imageVector = IconPack.Plus,
                            contentDescription = null,
                            tint = HMColor.Primary
                        )
                    }

                    is CurrentGroupState.Center -> {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column(modifier = Modifier.fillMaxSize(), Arrangement.SpaceBetween) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = group.doneTodo,
                                    style = HmStyle.text20,
                                    color = HMColor.Background
                                )
                                Text(
                                    modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End,
                                    text = group.totTodo,
                                    style = HmStyle.text20,
                                    color = HMColor.Background
                                )
                            }
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val startX = size.width * 0.1f
                                val startY = size.height * 0.95f
                                val endX = size.width * 0.9f
                                val endY = size.height * 0.05f
                                drawLine(
                                    color = HMColor.Background,
                                    start = Offset(startX, startY),
                                    end = Offset(endX, endY),
                                    strokeWidth = 3f,
                                )
                            }

                        }
                    }

                    is CurrentGroupState.Doing -> {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column(modifier = Modifier.fillMaxSize(), Arrangement.SpaceBetween) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = group.name,
                                    style = HmStyle.text12,
                                    color = HMColor.Primary
                                )
                                Text(
                                    modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End,
                                    text = group.leftTodo,
                                    style = HmStyle.text12,
                                    color = HMColor.Primary
                                )
                            }
                        }

                    }

                    is CurrentGroupState.Done -> {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column(modifier = Modifier.fillMaxSize(), Arrangement.SpaceBetween) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = group.name,
                                    style = HmStyle.text12,
                                    color = HMColor.Background
                                )
                                Icon(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(align = Alignment.End),
                                    imageVector = IconPack.Check,
                                    contentDescription = null,
                                    tint = HMColor.Background
                                )
                            }
                        }
                    }
                }
            }

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
                if (todo.isDone) {
                    Text(text = todo.title, textDecoration = TextDecoration.LineThrough)
                } else {
                    Text(text = todo.title)
                }
                Row {
                    Text(text = todo.groupName, style = HmStyle.text12, color = HMColor.Primary)
                    Text(text = todo.time, style = HmStyle.text12)
                }
            }
        }
    }
}

@Preview
@Composable
fun TodoContentPreView() {
    TodoContent(
        BottomSheetUiState.Down,
        {},
        {},
        {},
        {},
        {},
        List<CurrentGroupState>(9) { CurrentGroupState.Done("안드로이드", 1, CurrentGroup(1)) },
//        List<CurrentGroupState>(9) { CurrentGroupState.Doing("안드로이드이", "4", CurrentGroup(1)) },
//        List<CurrentGroupState>(9) { CurrentGroupState.Center("4", "11", ) },
        listOf(
            Todo("Sync 블로그 글쓰기", "", groupName = "안드로이드"),
            Todo("Sync 블로그 글쓰기", "", groupName = "안드로이드", time = "오전 11:35"),
            Todo("DB설계", ""),
            Todo("디자인 3페이지", "", groupName = "", time = "오전 08:00")
        ),
        emptyList(),
        {},
        {}
    )
}
