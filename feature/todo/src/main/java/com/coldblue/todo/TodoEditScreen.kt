package com.coldblue.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.data.util.asMyTime
import com.coldblue.data.util.getAmPmHour
import com.coldblue.data.util.getDisplayShort
import com.coldblue.data.util.isMatch
import com.coldblue.data.util.isNotMatch
import com.coldblue.designsystem.component.HMButton
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.Todo
import com.coldblue.model.ToggleInfo
import com.coldblue.todo.uistate.TodoEditUiState
import com.orhanobut.logger.Logger
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun TodoEditScreen(
    todoEditViewModel: TodoEditViewModel = hiltViewModel(),
    onDismissRequest: () -> Unit,
) {
    val todoEditUiState by todoEditViewModel.todoEditUiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (todoEditUiState) {
            is TodoEditUiState.Success -> {
                TodoEditContentWithState(
                    uiState = todoEditUiState as TodoEditUiState.Success,
                    upsertTodo = todoEditViewModel::upsertTodo,
                    onDismissRequest = onDismissRequest,
                    selectDate = { date -> todoEditViewModel.selectDate(date) },
                )
            }

            is TodoEditUiState.Error -> {}
            is TodoEditUiState.Loading -> {
                Text(text = "로딩 에디트임 ")
            }

        }

    }
}

@Composable
fun TodoEditContentWithState(
    uiState: TodoEditUiState.Success,
    upsertTodo: (Todo) -> Unit,
    onDismissRequest: () -> Unit,
    selectDate: (LocalDate) -> Unit,
    ) {
    TodoEditContent(
        todo = uiState.todo,
        upsertTodo = upsertTodo,
        todoDate = uiState.today,
        onDismissRequest = onDismissRequest,
        selectDate = selectDate
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEditContent(
    todo: Todo,
    upsertTodo: (Todo) -> Unit,
    todoDate: LocalDate,
    onDismissRequest: () -> Unit,
    selectDate: (LocalDate) -> Unit,

    ) {
    var onSwitch by remember { mutableStateOf(false) }
    var myTime by remember { mutableStateOf(todo.time?.asMyTime() ?: LocalTime.now().asMyTime()) }

    val today = LocalDate.now()
    LaunchedEffect(Unit) {
        onSwitch = todo.time != null
    }


    val keyboardController = LocalSoftwareKeyboardController.current
    var date by remember { mutableStateOf(todoDate) }

    var titleText by remember { mutableStateOf(todo.title) }
    var contentText by remember { mutableStateOf(todo.content ?: "") }




    val dateButtons = remember {
        mutableStateListOf(
            ToggleInfo(todoDate.isMatch(0), "오늘", plus = 0),
            ToggleInfo(todoDate.isMatch(1), "내일", plus = 1),
            ToggleInfo(todoDate.isMatch(7), "다음주", plus = 7),
            ToggleInfo(todoDate.isNotMatch(), "직접입력"),
        )
    }

    LaunchedEffect(date) {
        selectDate(date)
    }


    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier.padding(bottom = 60.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
               Box(modifier = Modifier
                   .fillMaxWidth()
                   .padding(vertical = 20.dp)){
                   Row(
                       Modifier
                           .fillMaxWidth()
                           .height(30.dp)
                           .background(HMColor.Background),
                       verticalAlignment = Alignment.CenterVertically,
                       horizontalArrangement = Arrangement.Absolute.Center
                   ) {
                       Text(
                           text = "세부 항목", style = HmStyle.text20, fontWeight = FontWeight.Bold
                       )
                   }
                   Row(
                       Modifier
                           .fillMaxWidth()
                           .height(30.dp),
                       verticalAlignment = Alignment.CenterVertically,
                       horizontalArrangement = Arrangement.End
                   ) {
                       IconButton(onClick = { onDismissRequest() }) {
                           Icon(
                               imageVector = Icons.Default.Close,
                               contentDescription = "작성 종료"
                           )
                       }
                   }
               }

            }
            item {
                Text(text = "이름", style = HmStyle.text16, fontWeight = FontWeight.Bold)
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = titleText,
                    singleLine = true,
                    onValueChange = {
                        titleText = it
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = HMColor.Primary,
                        containerColor = Color.Transparent
                    ),
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(top = 24.dp),
                    text = "설명",
                    style = HmStyle.text16,
                    fontWeight = FontWeight.Bold
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = contentText,
                    onValueChange = {
                        contentText = it
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = HMColor.Primary,
                        containerColor = Color.Transparent
                    ),
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(top = 24.dp),
                    text = "날짜",
                    style = HmStyle.text16,
                    fontWeight = FontWeight.Bold
                )
                Text(text = date.getDisplayShort())

                Row {
                    dateButtons.forEach { button ->

                    }
                }

            }
            item {

            }
        }


        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            if (todo.id != 0) {
                Row(Modifier.fillMaxWidth()) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F)
                            .padding(end = 8.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonColors(
                            contentColor = HMColor.Primary,
                            containerColor = HMColor.Gray,
                            disabledContentColor = HMColor.Gray,
                            disabledContainerColor = HMColor.Primary,
                        ),
                        onClick = {
                            upsertTodo(
                                todo.copy(
                                    isDel = true
                                )
                            )
                            onDismissRequest()
                        }
                    ) {
                        Text(
                            text = "삭제",
                            style = HmStyle.text16,
                            modifier = Modifier.padding(vertical = 4.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    HMButton(
                        text = "수정",
                        clickableState = titleText.isNotEmpty(),
                        modifier = Modifier.weight(1F)
                    ) {
                        Logger.d(myTime.getAmPmHour())
                        Logger.d(date)
                        upsertTodo(
                            todo.copy(
                                title = titleText,
                                content = contentText,
                                time = if (onSwitch) myTime.getAmPmHour() else null,
                                date = date
                            )
                        )
                        onDismissRequest()
                    }
                }
            } else {
                HMButton(text = "저장", clickableState = titleText.isNotEmpty()) {
                    upsertTodo(
                        todo.copy(
                            title = titleText,
                            content = contentText,
                            time = if (onSwitch) myTime.getAmPmHour() else null,
                            date = date
                        )
                    )
                    onDismissRequest()
                }
            }
        }

    }

}


