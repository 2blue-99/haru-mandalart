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
    val name: String = "작은 목표를 추가해 주세요!",
    val startDate: String = "",
    val rank: Int? = null,
    val colorIndex: Int = 1
)

data class HistoryController(
    val colorIndex: Int = 0,
    val allCount: Int = 0,
    val doneCount: Int = 0,
    val donePercentage: Int = 0,
    val continueDate: Int = 0,
    val controller: List<Controller> = emptyList(),
    val years: List<String> = emptyList()
)

data class TodoController(
    val date: String = LocalDate.now().toString(),
    val dayAllCount: Int = 0,
    val dayDoneCount: Int = 0,
    val todoList: List<MandaTodo> = emptyList()
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