package com.coldblue.history

import com.coldblue.model.MandaTodo

sealed interface HistoryUIState {
    data object Loading : HistoryUIState

    data class Error(val msg: String) : HistoryUIState

    data class Success(
        //그래프 데이터
        val title: String,

//        val controllerList: List<ControllerWeek>,
//        val todoYearList: List<Int>,
//        val today: LocalDate,
//        val todoList: List<Todo>,
    ) : HistoryUIState
}

data class Graph(
    val allTodoGraph: List<Pair<Int,Int>>,

)

data class TitleBar(
    val name: String,
    val startDate: String,
    val rank: Int,
)

//data class Controller(
//
//)
//
//data class Todo(
//
//)


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