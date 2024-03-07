package com.coldblue.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.todo.GetTodoDateUseCase
import com.coldblue.domain.todo.GetTodoUseCase
import com.coldblue.domain.todo.GetTodoYearRangeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    getTodoUseCase: GetTodoUseCase,
    getTodoDateUseCase: GetTodoDateUseCase,
    getTodoYearRangeUseCase: GetTodoYearRangeUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            getTodoDateUseCase("2024").collect{
                Log.e("TAG", "date : $it", )
            }
        }

        viewModelScope.launch {
            getTodoYearRangeUseCase().collect{
                Log.e("TAG", "Year Range: $it", )
            }
        }
    }

    private val _dateSate = MutableStateFlow<LocalDate>(LocalDate.now())
    val dateSate: StateFlow<LocalDate> = _dateSate

    private val todoFlow = dateSate.flatMapLatest { getTodoUseCase(it) }




    //HistoryUiState 수정 & 필요한거 combine
    val historyUiState: StateFlow<HistoryUiState> = todoFlow.map {
        // Todo 테이블에 원하는 연도에 맞는 Todo ID List 를 위한 Combine
        HistoryUiState.Success(dateSate.value, it)
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

}