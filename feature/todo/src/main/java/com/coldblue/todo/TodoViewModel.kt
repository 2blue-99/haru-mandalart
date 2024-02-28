package com.coldblue.todo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.todo.GetTodoUseCase
import com.coldblue.domain.todo.ToggleTodoUseCase
import com.coldblue.domain.todo.UpsertTodoUseCase
import com.coldblue.domain.todogroup.GetCurrentGroupUseCase
import com.coldblue.domain.todogroup.GetTodoGroupUseCase
import com.coldblue.domain.todogroup.UpsertCurrentGroupUseCase
import com.coldblue.domain.todogroup.UpsertTodoGroupUseCase
import com.coldblue.model.CurrentGroup
import com.coldblue.model.Todo
import com.coldblue.model.TodoGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    getCurrentGroupUseCase: GetCurrentGroupUseCase,
    getTodoUseCase: GetTodoUseCase,
    getTodoGroupUseCase: GetTodoGroupUseCase,
    private val upsertTodoGroupUseCase: UpsertTodoGroupUseCase,
    private val upsertCurrentGroupUseCase: UpsertCurrentGroupUseCase,
    private val upsertTodoUseCase: UpsertTodoUseCase,
    private val toggleTodoUseCase: ToggleTodoUseCase
) : ViewModel() {
    private val _bottomSheetUiSate = MutableStateFlow<BottomSheetUiState>(BottomSheetUiState.Down)
    val bottomSheetUiSate: StateFlow<BottomSheetUiState> = _bottomSheetUiSate

    init {
        viewModelScope.launch {
//            upsertTodoGroup(TodoGroup("안드로이드"))
//            upsertTodoGroup(TodoGroup("블로그"))
//            upsertTodoGroup(TodoGroup("취업"))
//            upsertTodoGroup(TodoGroup("운동"))
//            upsertTodoUseCase(Todo("1번이요","내용입니다"))
//            upsertTodoUseCase(Todo("2번이요","내용입니다"))
//            upsertTodoUseCase(Todo("3번이요","내용입니다", todoGroupId = 1))
//            upsertTodoUseCase(Todo("4번이요","내용입니다", todoGroupId = 2))

//            upsertCurrentGroup(CurrentGroup(1))
//            upsertCurrentGroup(CurrentGroup(2))

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
        getCurrentGroupUseCase().combine(getTodoUseCase(LocalDate.now())) { currentGroupList, todoList ->
            val todoByGroup = todoList.groupBy { it.todoGroupId }
            TodoUiState.Success(
                today = LocalDate.now(),
                todoList = todoList,
                currentGroupList = List(9) {
                    val index = it + 1
                    when (index) {
                        5 -> CurrentGroupState.Center(
                            totTodo = todoList.size.toString(),
                            doneTodo = todoList.filter { it.isDone }.size.toString(),
                            index = index
                        )

                        else -> {
                            if (todoByGroup[index] == null) {
                                CurrentGroupState.Empty(
                                    index = index
                                )
                            } else if (todoByGroup[index]?.all { it.isDone } == true) {
                                CurrentGroupState.Done(
                                    currentGroup = currentGroupList[index]!!,
                                    name = currentGroupList[index]!!.name,
                                    index = index
                                )
                            } else {
                                CurrentGroupState.Doing(
                                    name = currentGroupList[index]!!.name,
                                    currentGroup = currentGroupList[index]!!,
                                    leftTodo = todoByGroup[index]!!.filter { !it.isDone }.size.toString(),
                                    index = index
                                )
                            }
                        }
                    }
                }
            )
        }.catch {
            TodoUiState.Error(it.message ?: "Error")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TodoUiState.Loading
        )


    val todoGroupList: StateFlow<List<TodoGroup>> = getTodoGroupUseCase().map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
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