package com.coldblue.todo

import androidx.compose.ui.graphics.Color
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.model.CurrentGroup
import com.coldblue.model.TodoGroup

sealed interface BottomSheetUiState {
    data object Down : BottomSheetUiState
    data class Up(val content: ContentState) : BottomSheetUiState
}

sealed interface DiaLogState {
    val confirmText: String
    val confirmColor: Color

    data class InsertGroup(
        override val confirmText: String = "생성",
        override val confirmColor: Color = HMColor.Primary,
        val onInsertGroup: (TodoGroup) -> Unit
    ) : DiaLogState

    data class UpdateGroup(
        override val confirmText: String = "수정",
        override val confirmColor: Color = HMColor.Primary,
        val onUpsertGroup: (TodoGroup) -> Unit

    ) : DiaLogState

    data class DeleteGroup(
        override val confirmText: String = "삭제",
        override val confirmColor: Color = HMColor.Dark.Red,
        val currentGroup: CurrentGroup,
        val onDeleteGroup: (Int,Int) -> Unit
    ) : DiaLogState

}


sealed interface ContentState {
    val title: String

    data class Todo(
        override val title: String = "할 일",
        val todo: com.coldblue.model.Todo,
    ) : ContentState

    data class Group(
        override val title: String = "그룹",
        val currentGroup: CurrentGroupState,
    ) : ContentState
}