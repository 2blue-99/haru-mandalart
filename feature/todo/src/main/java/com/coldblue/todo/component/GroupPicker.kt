package com.coldblue.todo.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.CurrentGroup
import com.coldblue.model.ToggleInfo

@Composable
fun GroupPicker(
    currentGroupList: List<CurrentGroup>,
    currentTodoGroupId: Int?,
    currentOriginGroupId: Int,
    onClick: (Int?, Int) -> Unit
) {


    val groupButtons = remember {
        mutableStateListOf<ToggleInfo>().apply {
            add(
                ToggleInfo(
                    isChecked = currentTodoGroupId == null,
                    text = "그룸없음",
                )
            )
            addAll(currentGroupList.map { group ->
                ToggleInfo(
                    isChecked = if (currentOriginGroupId == 0) currentTodoGroupId == group.todoGroupId else currentOriginGroupId == group.originGroupId,
                    text = group.name,
                    groupId = group.todoGroupId,
                    originId = group.originGroupId
                )
            })
        }
    }


    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(top = 48.dp),
            text = "그룹",
            style = HmStyle.text16,
            fontWeight = FontWeight.Bold
        )
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(groupButtons) { group ->
                SelectButton(group) {
                    onClick(group.groupId, group.originId)
                    groupButtons.replaceAll {
                        it.copy(isChecked = it.text == group.text)
                    }
                }
            }
        }
    }

}

@Composable
fun SelectButton(toggleInfo: ToggleInfo, onClick: () -> Unit) {
    Surface(
        color = if (toggleInfo.isChecked) HMColor.Primary else HMColor.Background,
        contentColor = HMColor.Primary,
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(1.dp, HMColor.Primary),
        modifier = Modifier
            .padding(end = 16.dp, top = 8.dp, bottom = 8.dp)
            .clickable {
                onClick()
            }
    ) {
        Text(
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp),
            text = toggleInfo.text,
            color = if (toggleInfo.isChecked) HMColor.Background else HMColor.Primary
        )
    }
}
