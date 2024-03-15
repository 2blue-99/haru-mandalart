package com.coldblue.history

import com.coldblue.model.Todo
import java.time.LocalDate

sealed interface HistoryUiState {
    data object Loading : HistoryUiState

    data class Error(val msg: String) : HistoryUiState

    data class Success(
        val controllerList: List<ControllerWeek>,
        val todoYearList: List<Int>,
        val today: LocalDate,
        val todoList: List<Todo>,
    ) : HistoryUiState

}

data class ControllerWeek(
    val month: Int? = null,
    val todayIndex: Int? = null,
    val controllerDayList: List<ControllerDayState>
)

sealed interface ControllerDayState {
    data class Default(val dayWeek: String = ""): ControllerDayState

    data class Empty(val timeState: ControllerTimeState): ControllerDayState

    data class Exist(val timeState: ControllerTimeState): ControllerDayState
}

sealed interface ControllerTimeState {
    val date: LocalDate
    data class Past(override val date: LocalDate): ControllerTimeState
    data class Present(override val date: LocalDate): ControllerTimeState
    data class Future(override val date: LocalDate): ControllerTimeState
}