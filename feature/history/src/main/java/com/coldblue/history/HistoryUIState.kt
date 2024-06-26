package com.coldblue.history

import com.coldblue.model.MandaTodo
import com.coldblue.model.TodoGraph
import java.time.LocalDate

sealed interface HistoryUIState {
    data object Loading : HistoryUIState

    data class Error(val msg: String) : HistoryUIState

    data class Success(
        val todoGraph: List<TodoGraph>,
        val titleBar: TitleBar,
        val historyController: HistoryController,
        val todoController: TodoController
    ) : HistoryUIState
}
data class TitleBar(
    val name: String,
    val startDate: String,
    val rank: Int?,
    val colorIndex: Int
)

data class HistoryController(
    val colorIndex: Int,
    val allCount: Int,
    val doneCount: Int,
    val donePercentage: Int,
    val continueDate: Int,
    val controller: List<Controller>,
    val years: List<String>
)

data class TodoController(
    val date: String,
    val dayAllCount: Int,
    val dayDoneCount: Int,
    val todoList: List<MandaTodo>
)

data class Controller(
    val month: String = "",
    val controllerDayList: List<ControllerDayState>,
)

sealed interface ControllerDayState {
    // 공간 차지를 위한 빈 박스
    data class Default(val dayWeek: String = ""): ControllerDayState
    // TODO가 없는 박스
    data class Empty(val timeState: ControllerTimeState): ControllerDayState
    // TODO가 있는 박스
    data class Exist(val timeState: ControllerTimeState): ControllerDayState
}

sealed interface ControllerTimeState {
    val date: LocalDate
    data class Past(override val date: LocalDate): ControllerTimeState
    data class Present(override val date: LocalDate): ControllerTimeState
    data class Future(override val date: LocalDate): ControllerTimeState
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