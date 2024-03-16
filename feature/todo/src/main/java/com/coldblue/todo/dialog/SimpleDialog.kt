package com.coldblue.todo.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.component.DeleteDialog
import com.coldblue.designsystem.component.HMTextField
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.CurrentGroup
import com.coldblue.model.TodoGroup
import com.orhanobut.logger.Logger

@Composable
fun CurrentGroupDialog(
    text: String,
    groupName: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    currentGroup: CurrentGroup,
    upsertTodoGroupById: (Int, String) -> Unit,
    deleteCurrentGroup: (currentGroupId: Int, todoGroupId: Int) -> Unit,
) {
    var openDeleteDialog by remember { mutableStateOf(false) }

    if (openDeleteDialog) {
        DeleteDialog(
            text = "${groupName}의 오늘 할 일은 그룹없음 상태가 됩니다.",
            deleteConfirmText = "삭제",
            onDismissRequest = {
                openDeleteDialog = false
            },
            onConfirmation = {
                deleteCurrentGroup(currentGroup.id, currentGroup.todoGroupId)
                openDeleteDialog = false
                onDismissRequest()
            },
        )
    }

    var inputText by remember { mutableStateOf(text) }

    AlertDialog(
        shape = RoundedCornerShape(10.dp),
        containerColor = HMColor.Background,
        title = {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "오늘 그룹",
                    fontWeight = FontWeight.Bold,
                    style = HmStyle.text16
                )
                IconButton(onClick = { openDeleteDialog = true }, modifier = Modifier.size(32.dp)) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "삭제")
                }
            }
        },
        text = {
            HMTextField(
                inputText = text,
                maxLen = 12,
                onChangeText = {
                    inputText = it
                }
            )
        }, onDismissRequest = {
            onDismissRequest()
        }, confirmButton = {
            TextButton(
                enabled = inputText.isNotEmpty(),
                colors = ButtonColors(
                    containerColor = HMColor.Background,
                    contentColor = HMColor.Primary,
                    disabledContainerColor = HMColor.Background,
                    disabledContentColor = HMColor.Gray
                ),
                onClick = {
                    upsertTodoGroupById(
                        currentGroup.todoGroupId,
                        inputText
                    )
                    onConfirmation()
                }) {
                Text(
                    text = "수정",
                    fontWeight = FontWeight.Bold
                )
            }
        }, dismissButton = {
            TextButton(onClick = {
                onDismissRequest()
            }) {
                Text("취소", fontWeight = FontWeight.Bold, color = HMColor.Text)
            }
        })
}

@Composable
fun TodoGroupDialog(
    todoGroupList: List<TodoGroup>,
    onDismissRequest: () -> Unit,
    onConfirmation: (TodoGroup) -> Unit,
) {
    var inputText by remember { mutableStateOf("") }
    var isSame by remember { mutableStateOf(false) }
    AlertDialog(
        shape = RoundedCornerShape(10.dp),
        containerColor = HMColor.Background,
        title = {
            Text(
                text = "그룹",
                fontWeight = FontWeight.Bold,
                style = HmStyle.text16
            )
        },
        text = {
            Column {
                HMTextField(
                    inputText = inputText,
                    maxLen = 12,
                    onChangeText = {
                        if (isSame) isSame = false
                        inputText = it
                    }
                )
                if (isSame) {
                    Text(text = "${inputText}는 이미 존재하는 그룹입니다.", color = HMColor.Dark.Red)
                }
            }
        }, onDismissRequest = {
            onDismissRequest()
        }, confirmButton = {
            TextButton(
                enabled = inputText.isNotEmpty(),
                colors = ButtonColors(
                    containerColor = HMColor.Background,
                    contentColor = HMColor.Primary,
                    disabledContainerColor = HMColor.Background,
                    disabledContentColor = HMColor.Gray
                ),
                onClick = {
                    if (todoGroupList.map { it.name }.contains(inputText)) {
                        isSame = true
                    } else {
                        onConfirmation(TodoGroup(inputText))
                    }
                }) {
                Text(
                    text = "저장",
                    fontWeight = FontWeight.Bold
                )
            }
        }, dismissButton = {
            TextButton(onClick = {
                onDismissRequest()
            }) {
                Text("취소", fontWeight = FontWeight.Bold, color = HMColor.Text)
            }
        })
}

