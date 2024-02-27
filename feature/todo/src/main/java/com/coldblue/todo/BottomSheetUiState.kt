package com.coldblue.todo

sealed interface BottomSheetUiState {
    data object Down : BottomSheetUiState
    data class Up(val content: ContentState) : BottomSheetUiState
}

sealed interface ContentState {
    data object Todo : ContentState
    data object Group : ContentState
}