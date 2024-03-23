package com.coldblue.todo

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.coldblue.data.util.asMyTime
import com.coldblue.data.util.getAmPmHour
import com.coldblue.designsystem.component.HMButton
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.Todo
import com.coldblue.todo.component.HMTimePicker
import com.coldblue.todo.uistate.DEFAULT_TODO
import com.google.gson.Gson
import java.time.LocalDate
import java.time.LocalTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoBottomSheet(
    todo: Todo,
    upsertTodo: (Todo) -> Unit,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    today: LocalDate,
    navigateToTodoEdit: (Int, String, String, String) -> Unit


) {
    var onSwitch by remember { mutableStateOf(false) }
    var myTime by remember { mutableStateOf(todo.time?.asMyTime() ?: LocalTime.now().asMyTime()) }

    var toEdit by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onSwitch = todo.time != null
    }

    var titleText by remember { mutableStateOf(todo.title) }

    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(onSwitch) {
        sheetState.expand()
    }


    DisposableEffect(toEdit) {

        onDispose {
            if (toEdit) {
                navigateToTodoEdit(
                    if (todo.id == 0) DEFAULT_TODO else todo.id,
                    titleText.ifEmpty { DEFAULT_TODO.toString() },
                    Uri.encode(Gson().toJson(myTime.copy(isEdit = onSwitch))),
                    today.toString()
                )
            }
        }

    }

    Box() {
        LazyColumn(Modifier.padding(bottom = 60.dp)) {
            item {
                // 할 일
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
                HMTimePicker(
                    myTime = myTime,
                    onSwitch = onSwitch,
                    onCheckedChange = {
                        onSwitch = !onSwitch
                    },
                    onHourChange = { hour ->
                        myTime = myTime.copy(hour = hour)
                    },
                    onMinuteChange = { minute -> myTime = myTime.copy(minute = minute) },
                    onAmPmChange = { ampm -> myTime = myTime.copy(ampm = ampm) },
                )
            }
            item {
                ClickableText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    text = AnnotatedString("세부 항목 >"),
                    style = HmStyle.text16.copy(
                        color = HMColor.SubText,
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Bold
                    ),
                    onClick = {
                        onDismissRequest()
                        toEdit= true

                    })
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
                        upsertTodo(
                            todo.copy(
                                title = titleText,
                                time = if (onSwitch) myTime.getAmPmHour() else null,
                                date = today
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
                            time = if (onSwitch) myTime.getAmPmHour() else null,
                            date = today
                        )
                    )
                    onDismissRequest()
                }
            }
        }

    }
}
