package com.coldblue.todo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.data.util.getAmPmHour
import com.coldblue.data.util.toDate
import com.coldblue.domain.todo.GetTodoUseCase
import com.coldblue.domain.todo.UpsertTodoUseCase
import com.coldblue.model.MyTime2
import com.coldblue.model.Todo
import com.coldblue.todo.uistate.DATE
import com.coldblue.todo.uistate.DEFAULT_TODO
import com.coldblue.todo.uistate.MY_TIME
import com.coldblue.todo.uistate.TITLE
import com.coldblue.todo.uistate.TODO_ID
import com.coldblue.todo.uistate.TodoEditUiState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TodoEditViewModel @Inject constructor(
    getTodoUseCase: GetTodoUseCase,
    private val upsertTodoUseCase: UpsertTodoUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val todoId: Int? = savedStateHandle.get<Int>(TODO_ID)
    private val titleTmp: String? = savedStateHandle.get<String>(TITLE)
    private val myTime: String? = savedStateHandle.get<String>(MY_TIME)
    private val date: String? = savedStateHandle.get<String>(DATE)

    private val _dateSate = MutableStateFlow<LocalDate>(date?.toDate() ?: LocalDate.now())
    val dateSate: StateFlow<LocalDate> = _dateSate

    val todoFlow = getTodoUseCase(todoId, default = DEFAULT_TODO)


    val todoEditUiState: StateFlow<TodoEditUiState> =
        todoFlow.map { todo, ->
            val title = if (titleTmp == DEFAULT_TODO.toString()) "" else titleTmp ?: ""
            val time = Gson().fromJson(myTime, MyTime2::class.java)
            TodoEditUiState.Success(
                todo = todo.copy(
                    title = title,
                    time = if (time.isEdit) time.getAmPmHour() else null,
                    date = date?.toDate() ?: LocalDate.now(),
                ),
                today = todo.date,
                currentDay = dateSate.value
            )
        }.catch {
            TodoEditUiState.Error("${it.message}")
        }.stateIn(
            scope = viewModelScope,
            initialValue = TodoEditUiState.Loading,
            started = SharingStarted.WhileSubscribed(5_000),
        )

    fun upsertTodo(todo: Todo) {
        viewModelScope.launch {
            upsertTodoUseCase(todo)
        }
    }

    fun selectDate(date: LocalDate) {
        viewModelScope.launch {
            _dateSate.value = date
        }
    }


}

