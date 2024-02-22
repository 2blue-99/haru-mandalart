package com.coldblue.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.manda.DeleteAllMandaUseCase
import com.coldblue.domain.manda.GetMandaUseCase
import com.coldblue.domain.manda.InsertMandaUseCase
import com.coldblue.domain.todo.DeleteTodoUseCase
import com.coldblue.domain.todo.GetTodoUseCase
import com.coldblue.domain.todo.InsertTodoUseCase
import com.coldblue.domain.user.GetUserTokenUseCase
import com.coldblue.domain.user.UpdateUserTokenUseCase
import com.coldblue.model.Manda
import com.coldblue.model.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val deleteAllMandaUseCase: DeleteAllMandaUseCase,
    private val getMandaUseCase: GetMandaUseCase,
    private val insertMandaUseCase: InsertMandaUseCase,

    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val getTodoUseCase: GetTodoUseCase,
    private val insertTodoUseCase: InsertTodoUseCase,

    private val getTokenUseCase: GetUserTokenUseCase,
    private val updateUserTokenUseCase: UpdateUserTokenUseCase
): ViewModel() {

    val testManda: StateFlow<List<Manda>> =
        getMandaUseCase().map {
            Log.e("TAG", "getMandaData: $it", )
            it
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
    val testTodo: StateFlow<List<Todo>> =
        getTodoUseCase().map {
            Log.e("TAG", "getTodo: $it", )
            it
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val testToken: StateFlow<String> =
        getTokenUseCase().map {
            Log.e("TAG", "token: $it", )
            it
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = "Test"
        )

    fun insertManda(){
        viewModelScope.launch {
            insertMandaUseCase(listOf(Manda(id = 0, isSync = false, isDel = false, updateTime = "1999", name = "Manda", color = 11L)))
        }
    }

    fun insertTodo(){
        viewModelScope.launch {
            insertTodoUseCase(listOf(Todo(id = 0, originId = 0, harumandaId = 0, isSync = false, isDel = false, updateTime = "1999", title = "Todo", content = "Todo", time = "24.02.22")))
        }
    }

    fun updateToken(){
        viewModelScope.launch {
            updateUserTokenUseCase("Change Token")
        }
    }
}