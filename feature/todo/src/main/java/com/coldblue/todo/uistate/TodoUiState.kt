package com.coldblue.todo.uistate

import androidx.compose.ui.graphics.Color
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.model.CurrentGroup
import com.coldblue.model.Todo
import com.coldblue.model.TodoGroup
import java.time.LocalDate

sealed interface TodoUiState {
    data object Loading : TodoUiState

    data class Error(val msg: String) : TodoUiState

    data class Success(
        val today: LocalDate,
        val todoList: List<Todo>,
        val haruMandaList: List<CurrentGroupState>,
        val todoGroupList: List<TodoGroup>,
        val currentGroup: List<CurrentGroup>
    ) : TodoUiState

}

sealed interface CurrentGroupState {
    val border: Color
        get() = HMColor.Primary
    val backGround: Color
    val currentGroup: CurrentGroup

    data class Empty(
        override val backGround: Color = HMColor.Background,
        override val currentGroup: CurrentGroup,
    ) : CurrentGroupState

    data class Doing(
        val name: String,
        val leftTodo: String,
        override val currentGroup: CurrentGroup,

        override val backGround: Color = HMColor.Box
    ) : CurrentGroupState

    data class Center(
        val totTodo: String,
        val doneTodo: String,
        override val currentGroup: CurrentGroup,
        override val backGround: Color = HMColor.Primary
    ) : CurrentGroupState

    data class Done(
        val name: String,
        override val currentGroup: CurrentGroup,
        override val backGround: Color = HMColor.Primary
    ) : CurrentGroupState

}
