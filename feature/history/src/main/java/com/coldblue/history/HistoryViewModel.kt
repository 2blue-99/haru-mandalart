package com.coldblue.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.todo.GetMandaTodoGraphUseCase
import com.coldblue.domain.todo.GetTodoExistDateByIndexYearUseCase
import com.coldblue.domain.todo.GetMandaTodoByIndexDateUseCase
import com.coldblue.domain.todo.GetUniqueTodoYearUseCase
import com.coldblue.domain.todo.UpsertMandaTodoUseCase
import com.coldblue.model.MandaTodo
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getTodoExistDateByIndexYearUseCase: GetTodoExistDateByIndexYearUseCase,
    private val getMandaTodoByIndexDateUseCase: GetMandaTodoByIndexDateUseCase,
    private val getMandaTodoGraphUseCase: GetMandaTodoGraphUseCase,
    private val getUniqueTodoYearUseCase: GetUniqueTodoYearUseCase,
    private val upsertMandaTodoUseCase: UpsertMandaTodoUseCase
) : ViewModel() {

    /**
     * 1. 존재하는 Graph 중 첫번째 Index 탐색
     * 2. 존재하는 Year 중 해당 년도 -> 가장 첫번째 년도 탐색
     * 3. Init Year에 맞춰 Date 초기화
     * 4. initState 갱신
     */
    init {
        viewModelScope.launch {
            val initIndex = HistoryUtil.initCurrentIndex(getMandaTodoGraphUseCase().first())
            val initYear = HistoryUtil.initCurrentYear(getUniqueTodoYearUseCase(initIndex).first(), currentYear.value)
            val initDate = HistoryUtil.initCurrentDate(initYear, LocalDate.parse(currentDate.value))
            _currentIndex.value = initIndex
            _currentYear.value = initYear
            _currentDate.value = initDate.toString()
            _initState.emit(Unit)
        }
    }

    private val _initState = MutableSharedFlow<Unit>()
    val initState: SharedFlow<Unit> = _initState

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> get() = _currentIndex

    private val _currentYear = MutableStateFlow(LocalDate.now().year.toString())
    val currentYear: StateFlow<String> get() = _currentYear

    private val _currentDate = MutableStateFlow(LocalDate.now().toString())
    val currentDate: StateFlow<String> get() = _currentDate




    // graph 데이터를 가져오기
    // 존재하는 데이터 중 가장 왼쪽 데이터 선택
    val historyUIState: StateFlow<HistoryUIState> =
        initState.flatMapLatest { _ ->
            currentIndex.flatMapLatest { index ->
                currentYear.flatMapLatest { year ->
                    currentDate.flatMapLatest { date ->
                        combine(
                            getMandaTodoGraphUseCase(),
                            getMandaTodoByIndexDateUseCase(index, date),
                            getTodoExistDateByIndexYearUseCase(index, year),
                            getUniqueTodoYearUseCase(index)
                        ) { graphList, todoList, doneDateList, yearList ->
                            val titleBar = if (graphList.isNotEmpty()) {
                                TitleBar(
                                    name = graphList[index].name,
                                    startDate = if (doneDateList.isNotEmpty()) HistoryUtil.dateToString(
                                        doneDateList.first().toString()
                                    ) else "",
                                    rank = HistoryUtil.calculateRank(graphList, index),
                                    colorIndex = graphList[index].colorIndex
                                )
                            } else {
                                TitleBar()
                            }

                            val historyController = if (doneDateList.isNotEmpty()) {
                                HistoryController(
                                    colorIndex = graphList[index].colorIndex,
                                    allCount = graphList[index].allCount,
                                    doneCount = graphList[index].doneCount,
                                    donePercentage = if (graphList[index].allCount != 0) (graphList[index].doneCount / graphList[index].allCount * 100) else 0,
                                    continueDate = if (doneDateList.isNotEmpty()) HistoryUtil.calculateContinueDate(
                                        doneDateList
                                    ) else 0,
                                    controller = HistoryUtil.makeController(
                                        year.toInt(),
                                        doneDateList
                                    ),
                                    currentYear = year,
                                    years = yearList
                                )
                            } else {
                                HistoryController(years = yearList)
                            }

                            val todoController = if (todoList.isNotEmpty()) {
                                TodoController(
                                    date = date,
                                    dayAllCount = todoList.size,
                                    dayDoneCount = todoList.filter { it.isDone }.size,
                                    todoList = todoList
                                )
                            } else {
                                TodoController(date = date)
                            }

                            HistoryUIState.Success(
                                todoGraph = graphList.ifEmpty { emptyList() },
                                titleBar = titleBar,
                                historyController = historyController,
                                todoController = todoController
                            )
                        }.catch {
                            Logger.d(it)
                        }
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
            _initState.emit(Unit)
        }
    }

    fun deleteMandaTodo(todo: MandaTodo) {
        viewModelScope.launch {
            upsertMandaTodoUseCase(todo.copy(isDel = true))
        }
    }
}