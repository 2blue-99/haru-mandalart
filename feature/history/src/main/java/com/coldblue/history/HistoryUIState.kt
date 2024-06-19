package com.coldblue.history

import androidx.compose.ui.graphics.Color
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
        val todo: List<MandaTodo>
    ) : HistoryUIState
}
data class TitleBar(
    val name: String,
    val startDate: String,
    val rank: Int?,
    val colorIndex: Int
)

data class HistoryController(
    val color: Color,
    val allCount: Int,
    val doneCount: Int,
    val donePercentage: Int,
    val continueDate: Int,
    val controller: List<Controller>,
    val years: List<String>
)

data class Controller(
    val month: Int? = null,
    val controllerDayList: List<ControllerDayState>,
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