package com.coldblue.todo

import com.coldblue.model.CurrentGroupWithCnt
import com.coldblue.model.Todo
import com.coldblue.model.TodoGroup
import java.time.LocalDate

sealed interface TodoUiState {
    data object Loading : TodoUiState

    data class Error(val msg: String) : TodoUiState

    data class Success(
        val today: LocalDate,
        val todoList: List<Todo>,
        val todoCnt: Int,
        val doneTodoCnt: Int,
        val currentGroupList: List<CurrentGroupWithCnt>,
//        val todoGroupList: List<TodoGroup>
    ) : TodoUiState

}
