package com.coldblue.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.todo.GetTodoUseCase
import com.coldblue.domain.todo.ToggleTodoUseCase
import com.coldblue.domain.todo.UpsertTodoUseCase
import com.coldblue.model.Todo
import com.coldblue.todo.uistate.BottomSheetUiState
import com.coldblue.todo.uistate.ContentState
import com.coldblue.todo.uistate.TodoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    getTodoUseCase: GetTodoUseCase,
    private val upsertTodoUseCase: UpsertTodoUseCase,
    private val toggleTodoUseCase: ToggleTodoUseCase,
) : ViewModel() {

    private val _bottomSheetUiSate = MutableStateFlow<BottomSheetUiState>(BottomSheetUiState.Down)
    val bottomSheetUiSate: StateFlow<BottomSheetUiState> = _bottomSheetUiSate

    private val _dateSate = MutableStateFlow<LocalDate>(LocalDate.now())
    val dateSate: StateFlow<LocalDate> = _dateSate

    private val todoFlow = dateSate.flatMapLatest { getTodoUseCase(it) }

    fun selectDate(date: LocalDate) {
        viewModelScope.launch {
            _dateSate.value = date
        }
    }

    fun showSheet(content: ContentState) {
        viewModelScope.launch {
            _bottomSheetUiSate.value = BottomSheetUiState.Up(content)
        }
    }

    fun hideSheet() {
        viewModelScope.launch {
            _bottomSheetUiSate.value = BottomSheetUiState.Down
        }
    }

    val todoUiState: StateFlow<TodoUiState> =
        todoFlow.map { todoList ->
            TodoUiState.Success(
                today = dateSate.value,
                todoList = todoList,
            )
        }.catch {
            TodoUiState.Error(it.message ?: "Error")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TodoUiState.Loading
        )

    fun upsertTodo(todo: Todo) {
        viewModelScope.launch {
            upsertTodoUseCase(todo)
        }
    }

    fun toggleTodo(todo: Todo) {
        viewModelScope.launch {
            toggleTodoUseCase(todo)
        }
    }
}