package com.coldblue.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.todo.GetYearlyExistTodoDateUseCase
import com.coldblue.domain.todo.GetTodoUseCase
import com.coldblue.domain.todo.GetTodoYearRangeUseCase
import com.coldblue.domain.todo.GetUniqueTodoCountByDateUseCase
import com.coldblue.domain.todo.ToggleTodoUseCase
import com.coldblue.history.util.HistoryUtil
import com.coldblue.model.Todo
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    getTodoUseCase: GetTodoUseCase,
    getYearlyExistTodoDateUseCase: GetYearlyExistTodoDateUseCase,
    getTodoYearRangeUseCase: GetTodoYearRangeUseCase,
    getUniqueTodoCountByDateUseCase: GetUniqueTodoCountByDateUseCase,
    private val toggleTodoUseCase: ToggleTodoUseCase,
) : ViewModel() {

    private val _yearSate = MutableStateFlow(LocalDate.now().year)
    val yearSate: StateFlow<Int> = _yearSate

    private val _dateSate = MutableStateFlow<LocalDate>(LocalDate.now())
    val dateSate: StateFlow<LocalDate> = _dateSate

    private val todoFlow = dateSate.flatMapLatest { getTodoUseCase(it) }

    private val yearlyExistDateFlow = yearSate.flatMapLatest { getYearlyExistTodoDateUseCase(it) }

    val historyUiState: StateFlow<HistoryUiState> = combine(
        todoFlow,
        getUniqueTodoCountByDateUseCase(),
        yearlyExistDateFlow,
        getTodoYearRangeUseCase()
    ) { todoList, uniqueTodoCnt, dateList, yearList ->
        HistoryUiState.Success(
            allTodoDayCnt = uniqueTodoCnt,
            controllerList = HistoryUtil.controllerListMaker(yearSate.value, dateList),
            todoYearList = yearList,
            today = dateSate.value,
            todoList = todoList
        )
    }.catch {
        HistoryUiState.Error(it.message ?: "Error")
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HistoryUiState.Loading
    )

    fun selectDate(date: LocalDate) {
        viewModelScope.launch {
            _dateSate.value = date
        }
    }

    fun toggleTodo(todo: Todo) {
        viewModelScope.launch {
            toggleTodoUseCase(todo)
        }
    }

    fun selectYear(year: Int) {
        viewModelScope.launch {
            _yearSate.value = year
        }
    }

}