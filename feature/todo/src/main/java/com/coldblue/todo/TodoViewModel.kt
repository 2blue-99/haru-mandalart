package com.coldblue.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.todo.GetTodoUseCase
import com.coldblue.domain.todo.ToggleTodoUseCase
import com.coldblue.domain.todo.UpsertTodoUseCase
import com.coldblue.domain.todogroup.DeleteCurrentGroupUseCase
import com.coldblue.domain.todogroup.DeleteTodoGroupUseCase
import com.coldblue.domain.todogroup.GetGroupWithCurrentUseCase
import com.coldblue.domain.todogroup.UpsertCurrentGroupUseCase
import com.coldblue.domain.todogroup.UpsertTodoGroupUseCase
import com.coldblue.model.CurrentGroup
import com.coldblue.model.Todo
import com.coldblue.model.TodoGroup
import com.coldblue.todo.uistate.BottomSheetUiState
import com.coldblue.todo.uistate.ContentState
import com.coldblue.todo.uistate.CurrentGroupState
import com.coldblue.todo.uistate.TodoUiState
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
class TodoViewModel @Inject constructor(
    getGroupWithCurrentUseCase: GetGroupWithCurrentUseCase,
    getTodoUseCase: GetTodoUseCase,
    private val upsertTodoGroupUseCase: UpsertTodoGroupUseCase,
    private val upsertCurrentGroupUseCase: UpsertCurrentGroupUseCase,
    private val upsertTodoUseCase: UpsertTodoUseCase,
    private val toggleTodoUseCase: ToggleTodoUseCase,
    private val deleteCurrentGroupUseCase: DeleteCurrentGroupUseCase,
    private val deleteTodoGroupUseCase: DeleteTodoGroupUseCase,
) : ViewModel() {

    private val _bottomSheetUiSate = MutableStateFlow<BottomSheetUiState>(BottomSheetUiState.Down)
    val bottomSheetUiSate: StateFlow<BottomSheetUiState> = _bottomSheetUiSate

    private val _dateSate = MutableStateFlow<LocalDate>(LocalDate.now())
    val dateSate: StateFlow<LocalDate> = _dateSate

    private val groupFlow = dateSate.flatMapLatest { getGroupWithCurrentUseCase(it) }
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
        groupFlow.combine(todoFlow) { group, todoList ->
            val todoGroupList = group.todoGroupList
            val currentGroupList = group.currentGroupList.groupBy { it.index }
            TodoUiState.Success(
                today = dateSate.value,
                todoList = todoList,
                todoGroupList = todoGroupList,
                currentGroup = group.currentGroupList,
                haruMandaList = List(9) { it ->
                    val index = it + 1
                    if (currentGroupList.keys.contains(index)) {
                        val currentGroup = currentGroupList[index]!!.first()
                        val name = currentGroup.name
                        val currentTodos =
                            todoList.filter { it.todoGroupId == currentGroup.todoGroupId }
                        val isDoing = currentTodos.all { it.isDone }
                        val hasTodo = currentTodos.isNotEmpty()
                        if (isDoing && hasTodo) {
                            CurrentGroupState.Done(
                                currentGroup = currentGroup,
                                name = name
                            )
                        } else {
                            CurrentGroupState.Doing(
                                name = name,
                                currentGroup = currentGroup,
                                leftTodo = currentTodos.filter { !it.isDone }.size.toString(),
                            )

                        }
                    } else if (index == 5) {
                        CurrentGroupState.Center(
                            totTodo = todoList.size.toString(),
                            doneTodo = todoList.filter { it.isDone }.size.toString(),
                            currentGroup = CurrentGroup(
                                -1,
                                index = 5,
                                date = dateSate.value,
                                originId = 0,
                                originGroupId = 0
                            )
                        )
                    } else {
                        CurrentGroupState.Empty(
                            currentGroup = CurrentGroup(
                                todoGroupId = -1,
                                index = index,
                                date = dateSate.value,
                                originId = 0,
                                originGroupId = 0
                            )
                        )
                    }
                }.sortedBy { it.currentGroup.index }
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

    fun upsertTodoGroup(originGroupId: Int, todoGroupId: Int, name: String) {
        viewModelScope.launch {
            upsertTodoGroupUseCase(originGroupId, todoGroupId, name)
        }
    }

    fun upsertCurrentGroup(currentGroup: CurrentGroup) {
        viewModelScope.launch {
            upsertCurrentGroupUseCase(currentGroup)
        }
    }

    fun deleteCurrentGroup(currentGroupId: Int, todoGroupId: Int, date: LocalDate) {
        viewModelScope.launch {
            deleteCurrentGroupUseCase(currentGroupId, todoGroupId, date)
        }
    }

    fun deleteTodoGroup(todoGroupId: Int) {
        viewModelScope.launch {
            deleteTodoGroupUseCase(todoGroupId)
        }
    }
}