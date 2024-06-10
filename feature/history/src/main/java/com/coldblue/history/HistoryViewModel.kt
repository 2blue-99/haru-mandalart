package com.coldblue.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.todo.GetAllMandaTodoCountUseCase
import com.coldblue.domain.todo.GetMandaTodoByIndexUseCase
import com.coldblue.domain.todo.UpsertMandaTodoUseCase
import com.coldblue.model.MandaTodo
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getMandaTodoByIndexUseCase: GetMandaTodoByIndexUseCase,
    private val getAllMandaTodoCountUseCase: GetAllMandaTodoCountUseCase,
    private val upsertMandaTodoUseCase: UpsertMandaTodoUseCase
) : ViewModel() {

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> get() = _currentIndex

    val mandaIndexTodo =
        currentIndex.flatMapLatest { getMandaTodoByIndexUseCase(_currentIndex.value) }

    // currentIndex 변경 -> TodoIndex 가져오기
    // todo 업데이트 -> TodoIndex, TodoCount 가져오기

    val historyUIState: StateFlow<HistoryUIState> =
        currentIndex.flatMapLatest { index ->
            getMandaTodoByIndexUseCase(index).combine(getAllMandaTodoCountUseCase()) { a, b ->

                HistoryUIState.Success(title = "")
            }
        }.catch {
            HistoryUIState.Error(it.message ?: "Error")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HistoryUIState.Loading
        )

//        combine(
//            mandaIndexTodo,
//            getAllMandaTodoCountUseCase(),
//        ) { mandaTodo, allCount ->
//
//
//            Logger.d(mandaTodo)
//
//            HistoryUIState.Success(mandaTodo)
//        }.catch {
//            HistoryUIState.Error(it.message ?: "Error")
//        }.stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = HistoryUIState.Loading
//        )

    fun changeMandaTodoIndex(index: Int) {
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