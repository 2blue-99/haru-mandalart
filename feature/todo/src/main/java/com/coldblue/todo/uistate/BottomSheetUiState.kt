package com.coldblue.todo.uistate


sealed interface BottomSheetUiState {
    data object Down : BottomSheetUiState
    data class Up(val content: ContentState) : BottomSheetUiState
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