package com.coldblue.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.todo.GetMandaTodoGraphUseCase
import com.coldblue.domain.todo.GetDoneDateByIndexYearUseCase
import com.coldblue.domain.todo.GetMandaTodoByIndexDateUseCase
import com.coldblue.domain.todo.GetUniqueTodoYearUseCase
import com.coldblue.domain.todo.UpsertMandaTodoUseCase
import com.coldblue.model.MandaTodo
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
    private val getDoneDateByIndexYearUseCase: GetDoneDateByIndexYearUseCase,
    private val getMandaTodoByIndexDateUseCase: GetMandaTodoByIndexDateUseCase,
    private val getMandaTodoGraphUseCase: GetMandaTodoGraphUseCase,
    private val getUniqueTodoYearUseCase: GetUniqueTodoYearUseCase,
    private val upsertMandaTodoUseCase: UpsertMandaTodoUseCase
) : ViewModel() {

    private val _currentYear = MutableStateFlow(LocalDate.now().year.toString())
    val currentYear: StateFlow<String> get() = _currentYear

    private val _currentDate = MutableStateFlow(LocalDate.now().toString())
    val currentDate: StateFlow<String> get() = _currentDate

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> get() = _currentIndex

    val historyUIState: StateFlow<HistoryUIState> =
        currentIndex.flatMapLatest { index ->
            currentYear.flatMapLatest { year ->
                currentDate.flatMapLatest { date ->
                    combine(
                        getMandaTodoGraphUseCase(),
                        getDoneDateByIndexYearUseCase(index, year),
                        getMandaTodoByIndexDateUseCase(index, date),
                        getUniqueTodoYearUseCase()
                    ) { graphList, doneDateList, todoList, yearList ->
                        val titleBar = TitleBar(
                            name = graphList[index].name,
                            startDate = if(doneDateList.isNotEmpty()) HistoryUtil.dateToString(doneDateList.first().toString()) else "",
                            rank = HistoryUtil.calculateRank(graphList, index),
                            colorIndex = graphList[index].colorIndex
                        )
                        val historyController = HistoryController(
                            colorIndex = graphList[index].colorIndex,
                            allCount = graphList[index].allCount,
                            doneCount = graphList[index].doneCount,
                            donePercentage = if(graphList[index].allCount != 0) (graphList[index].doneCount / graphList[index].allCount * 100) else 0,
                            continueDate = if(doneDateList.isNotEmpty()) HistoryUtil.calculateContinueDate(doneDateList) else 0,
                            controller = HistoryUtil.makeController(
                                year.toInt(),
                                doneDateList
                            ),
                            years = yearList
                        )
                        val todoController = TodoController(
                            date = date,
                            dayAllCount = todoList.size,
                            dayDoneCount = todoList.filter { it.isDone }.size,
                            todoList = todoList
                        )
                        HistoryUIState.Success(
                            todoGraph = graphList,
                            titleBar = titleBar,
                            historyController = historyController,
                            todoController = todoController
                        )
                    }.catch {
                        Logger.d(it)
                    }
                }
            }
        }.catch {
            HistoryUIState.Error(it.message ?: "Error")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HistoryUIState.Loading
        )

    fun changeYear(year: String) {
        _currentYear.value = year
    }

    fun changeDay(day: String) {
        _currentDate.value = day
    }

    fun changeCurrentIndex(index: Int) {
        _currentIndex.value = index
    }

    fun updateMandaTodo(todo: MandaTodo) {
        viewModelScope.launch {
            upsertMandaTodoUseCase(todo)
        }
    }

    fun deleteMandaTodo(todo: MandaTodo) {
        viewModelScope.launch {
            upsertMandaTodoUseCase(todo.copy(isDel = true))
        }
    }
}