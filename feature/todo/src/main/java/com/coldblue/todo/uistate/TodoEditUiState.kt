package com.coldblue.todo.uistate

import androidx.navigation.NavType
import com.coldblue.model.Todo
import java.time.LocalDate

sealed interface TodoEditUiState {
    data object Loading : TodoEditUiState

    data class Error(val msg: String) : TodoEditUiState

    data class Success(
        val today: LocalDate,
        val todo: Todo,
        val currentDay :LocalDate,
    ) : TodoEditUiState
}

const val DEFAULT_TODO = -1
const val TODO_ID = "todoId"
const val TITLE = "title"
const val MY_TIME = "myTime"
const val DATE = "date"
