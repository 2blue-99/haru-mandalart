package com.coldblue.history

sealed interface HistoryUIState {
    data object Loading : HistoryUIState

    data class Error(val msg: String) : HistoryUIState

    data class Success(
        val allTodoDayCnt: Int,
//        val controllerList: List<ControllerWeek>,
//        val todoYearList: List<Int>,
//        val today: LocalDate,
//        val todoList: List<Todo>,
    ) : HistoryUIState

}
//
//data class ControllerWeek(
//    val month: Int? = null,
//    val controllerDayList: List<ControllerDayState>
//)
//
//sealed interface ControllerDayState {
//    data class Default(val dayWeek: String = ""): ControllerDayState
//
//    data class Empty(val timeState: ControllerTimeState): ControllerDayState
//
//    data class Exist(val timeState: ControllerTimeState): ControllerDayState
//}
//
//sealed interface ControllerTimeState {
//    val date: LocalDate
//    data class Past(override val date: LocalDate): ControllerTimeState
//    data class Present(override val date: LocalDate): ControllerTimeState
//    data class Future(override val date: LocalDate): ControllerTimeState
//}