package com.coldblue.history

import com.coldblue.model.Todo
import java.time.LocalDate

sealed interface HistoryUiState {
    data object Loading : HistoryUiState

    data class Error(val msg: String) : HistoryUiState

    data class Success(
        val historyWeekList: List<HistoryWeek>,
        val todoYearList: List<Int>,
        val today: LocalDate,
        val todoList: List<Todo>,
    ) : HistoryUiState

}

data class HistoryWeek(
    val month: String = "",
    val historyBoxList: List<HistoryBoxUIState>
)

sealed interface HistoryBoxUIState {
    data object Default: HistoryBoxUIState

    data class Empty(val date: LocalDate): HistoryBoxUIState

    data class Exist(val date: LocalDate): HistoryBoxUIState
}

sealed interface HistoryBoxTimeState {
    val date: LocalDate
    data class Past(override val date: LocalDate): HistoryBoxTimeState

    data class Future(override val date: LocalDate): HistoryBoxTimeState
}