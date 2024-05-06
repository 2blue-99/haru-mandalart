package com.coldblue.todo.uistate

import androidx.compose.ui.graphics.Color
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.model.Todo
import java.time.LocalDate

sealed interface TodoUiState {
    data object Loading : TodoUiState

    data class Error(val msg: String) : TodoUiState

    data class Success(
        val today: LocalDate,
        val todoList: List<Todo>,
    ) : TodoUiState

}

