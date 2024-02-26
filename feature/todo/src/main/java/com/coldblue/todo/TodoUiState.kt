package com.coldblue.todo

import com.coldblue.model.Todo
import java.time.LocalDate

sealed interface TodoUiState {
    data object Loading : TodoUiState

    data class Error(val msg: String) : TodoUiState

    data class Success(
        val today: String,
        val todoList: List<Todo>
    ) : TodoUiState

}