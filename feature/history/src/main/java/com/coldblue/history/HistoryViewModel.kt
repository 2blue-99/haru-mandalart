package com.coldblue.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.todo.GetMandaTodoGraphUseCase
import com.coldblue.domain.todo.GetMandaTodoByIndexUseCase
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
    private val getMandaTodoByIndexYearUseCase: GetMandaTodoByIndexUseCase,
    private val getMandaTodoGraphUseCase: GetMandaTodoGraphUseCase,
    private val getUniqueTodoYearUseCase: GetUniqueTodoYearUseCase,
    private val upsertMandaTodoUseCase: UpsertMandaTodoUseCase
) : ViewModel() {

    private val _currentYear = MutableStateFlow(LocalDate.now().year.toString())
    val currentYear: StateFlow<String> get() = _currentYear

    private val _currentDay = MutableStateFlow(LocalDate.now().dayOfMonth.toString())
    val currentDay: StateFlow<String> get() = _currentDay

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> get() = _currentIndex

    val historyUIState: StateFlow<HistoryUIState> =
        currentIndex.flatMapLatest { index ->
            currentYear.flatMapLatest { year ->
                currentDay.flatMapLatest { day ->
                    combine(
                        getMandaTodoGraphUseCase(),
                        getMandaTodoByIndexYearUseCase(index, year),
                        getUniqueTodoYearUseCase()
                    ) { graphList, todoList, yearList ->
                        val titleBar = TitleBar(
                            name = graphList[index].name,
                            startDate = if(todoList.isNotEmpty()) HistoryUtil.dateToString(todoList.first().date.toString()) else "",
                            rank = HistoryUtil.calculateRank(graphList, index),
                            colorIndex = graphList[index].colorIndex
                        )
                        val historyController = HistoryController(
                            allCount = graphList[index].allCount,
                            doneCount = graphList[index].doneCount,
                            donePercentage = if(graphList[index].allCount != 0) (graphList[index].doneCount / graphList[index].allCount * 100) else 0,
                            continueDate = HistoryUtil.calculateContinueDate(todoList),
                            controller = HistoryUtil.makeController(
                                year.toInt(),
                                todoList.map { it.date }),
                            years = yearList
                        )
                        HistoryUIState.Success(
                            todoGraph = graphList,
                            titleBar = titleBar,
                            historyController = historyController,
                            todo = todoList
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
        _currentDay.value = day
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