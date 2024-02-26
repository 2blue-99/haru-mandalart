package com.coldblue.todo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.todo.GetTodoUseCase
import com.coldblue.domain.todo.ToggleTodoUseCase
import com.coldblue.domain.todo.UpsertTodoUseCase
import com.coldblue.domain.todogroup.GetCurrentGroupUseCase
import com.coldblue.domain.todogroup.UpsertCurrentGroupUseCase
import com.coldblue.domain.todogroup.UpsertTodoGroupUseCase
import com.coldblue.model.CurrentGroup
import com.coldblue.model.Todo
import com.coldblue.model.TodoGroup
import com.coldblue.model.withCnt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    getCurrentGroupUseCase: GetCurrentGroupUseCase,
    getTodoUseCase: GetTodoUseCase,
    private val upsertTodoGroupUseCase: UpsertTodoGroupUseCase,
    private val upsertCurrentGroupUseCase: UpsertCurrentGroupUseCase,
    private val upsertTodoUseCase: UpsertTodoUseCase,
    private val toggleTodoUseCase: ToggleTodoUseCase
) : ViewModel() {
    init {
        viewModelScope.launch {
//            upsertTodoUseCase(Todo("1번이요","내용입니다"))
//            upsertTodoUseCase(Todo("2번이요","내용입니다"))
//            upsertTodoUseCase(Todo("4번이요","내용입니다", todoGroupId = 1))
//            upsertTodoGroup(TodoGroup("안드로이드"))
//            upsertTodoGroup(TodoGroup("블로그"))
        }
    }

    val todoUiState: StateFlow<TodoUiState> =
        getCurrentGroupUseCase().combine(getTodoUseCase(LocalDate.now())) { currentGroupList, todoList ->
            TodoUiState.Success(
                today = LocalDate.now(),
                todoList = todoList,
                todoCnt = todoList.size,
                doneTodoCnt = todoList.filter { it.isDone }.size,
                currentGroupList = currentGroupList.map { group ->
                    val doneTodoCnt = todoList.filter { it.todoGroupId == group.id }.size
                    group.withCnt(doneTodoCnt)
                }
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

    fun upsertTodoGroup(todoGroup: TodoGroup) {
        viewModelScope.launch {
            upsertTodoGroupUseCase(todoGroup)
        }
    }

    fun upsertCurrentGroup(currentGroup: CurrentGroup) {
        viewModelScope.launch {
            upsertCurrentGroupUseCase(currentGroup)
        }
    }
}