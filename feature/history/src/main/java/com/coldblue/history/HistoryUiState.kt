package com.coldblue.history

import com.coldblue.model.Todo
import java.time.LocalDate

sealed interface HistoryUiState {
    data object Loading : HistoryUiState

    data class Error(val msg: String) : HistoryUiState

    data class Success(
        val today: LocalDate,
        val todoList: List<Todo>,
    ) : HistoryUiState

}