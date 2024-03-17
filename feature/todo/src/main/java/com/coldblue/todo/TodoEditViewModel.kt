package com.coldblue.todo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.data.util.getAmPmHour
import com.coldblue.domain.todo.GetTodoUseCase
import com.coldblue.domain.todo.UpsertTodoUseCase
import com.coldblue.domain.todogroup.GetCurrentGroupWithName
import com.coldblue.model.MyTime
import com.coldblue.model.Todo
import com.coldblue.todo.uistate.DEFAULT_TODO
import com.coldblue.todo.uistate.MY_TIME
import com.coldblue.todo.uistate.TITLE
import com.coldblue.todo.uistate.TODO_ID
import com.coldblue.todo.uistate.TodoEditUiState
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoEditViewModel @Inject constructor(
    getTodoUseCase: GetTodoUseCase,
    getCurrentGroupWithName: GetCurrentGroupWithName,
    private val upsertTodoUseCase: UpsertTodoUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val todoId: Int? = savedStateHandle.get<Int>(TODO_ID)
    private val titleTmp: String? = savedStateHandle.get<String>(TITLE)
    private val myTime: String? = savedStateHandle.get<String>(MY_TIME)

    val todoFlow = getTodoUseCase(todoId, default = DEFAULT_TODO)
    val currentGroupFlow = todoFlow.flatMapLatest { getCurrentGroupWithName(it.date) }


    val todoEditUiState: StateFlow<TodoEditUiState> =
        todoFlow.combine(currentGroupFlow) { todo, groupList ->
            val title = if (titleTmp == DEFAULT_TODO.toString()) "" else titleTmp ?: ""
            val time = Gson().fromJson(myTime, MyTime::class.java)
            TodoEditUiState.Success(
                todo = todo.copy(
                    title = title,
                    time = if (time.isEdit) time.getAmPmHour() else null
                ),
                today = todo.date,
                currentGroup = groupList
            )
        }.catch {
            TodoEditUiState.Error("${it.message}")
        }.stateIn(
            scope = viewModelScope,
            initialValue = TodoEditUiState.Loading,
            started = SharingStarted.WhileSubscribed(5_000),
        )

    fun upsertTodo(todo: Todo) {

        Logger.d(todo)
        viewModelScope.launch {
            upsertTodoUseCase(todo)
        }
    }

}

