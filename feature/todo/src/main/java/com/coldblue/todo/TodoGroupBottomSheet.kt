package com.coldblue.todo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.component.DeleteDialog
import com.coldblue.designsystem.iconpack.Plus
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.CurrentGroup
import com.coldblue.model.TodoGroup
import com.coldblue.todo.dialog.TodoGroupDialog

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoGroupBottomSheet(
    currentGroup: CurrentGroup,
    todoGroupList:List<TodoGroup>,
    unSelectedTodoGroupList: List<TodoGroup>,
    upsertCurrentGroup: (CurrentGroup) -> Unit,
    upsertTodoGroup: (TodoGroup) -> Unit,
    onDismissRequest: () -> Unit,
    deleteTodoGroup: (Int) -> Unit,
) {
    var openTodoGroupDialog by remember { mutableStateOf(false) }
    var openDeleteDialog by remember { mutableStateOf(false) }
    var selectTodoGroup by remember { mutableStateOf("") }
    var selectTodoGroupId by remember { mutableIntStateOf(-1) }

    if (openTodoGroupDialog) {
        TodoGroupDialog(
            todoGroupList = todoGroupList,
            onConfirmation = { todoGroup ->
                upsertTodoGroup(todoGroup)
                openTodoGroupDialog = false
            },
            onDismissRequest = { openTodoGroupDialog = false },
        )
    }

    if (openDeleteDialog) {
        DeleteDialog(
            targetText = selectTodoGroup,
            text = " 이(가) 포함한 모든 할 일은 그룹없음 상태가 돼요.",
            deleteConfirmText = "삭제",
            onDismissRequest = {
                openDeleteDialog = false
            },
            onConfirmation = {
                deleteTodoGroup(selectTodoGroupId)
                openDeleteDialog = false
            },
        )
    }

    Column(
    ) {
        Text(
            text = "그룹", modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start, style = HmStyle.text16, fontWeight = FontWeight.Bold
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f),
        ) {
            items(unSelectedTodoGroupList) { todoGroup ->
                Surface(
                    color = HMColor.Background,
                    contentColor = HMColor.Primary,
                    shape = RoundedCornerShape(5.dp),
                    border = BorderStroke(1.dp, HMColor.Primary),
                    modifier = Modifier
                        .fillMaxHeight(0.7f)
                        .padding(end = 16.dp, top = 8.dp, bottom = 8.dp)
                        .combinedClickable(
                            enabled = true,
                            onClick = {
                                upsertCurrentGroup(
                                    CurrentGroup(
                                        id = currentGroup.id,
                                        index = currentGroup.index,
                                        name = todoGroup.name,
                                        date = currentGroup.date,
                                        todoGroupId = todoGroup.id,
                                        originId = currentGroup.originId,
                                        originGroupId = currentGroup.originGroupId
                                    )
                                )
                                onDismissRequest()
                            },
                            onLongClick = {
                                selectTodoGroupId = todoGroup.id
                                selectTodoGroup = todoGroup.name
                                openDeleteDialog = true
                            }
                        )
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp),
                        text = todoGroup.name,
                        color = HMColor.Primary
                    )
                }
            }
            item {
                OutlinedButton(
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .fillMaxHeight(0.7f)
                        .aspectRatio(1.1f)
                        .padding(end = 16.dp, top = 8.dp, bottom = 8.dp)
                        .border(
                            width = 1.dp,
                            color = HMColor.Primary,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clip(RoundedCornerShape(5.dp))
                        .background(HMColor.Background),
                    onClick = { openTodoGroupDialog = true },
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = IconPack.Plus,
                        contentDescription = null,
                        tint = HMColor.Primary
                    )
                }
            }

        }
    }
}