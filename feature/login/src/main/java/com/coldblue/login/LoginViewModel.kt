package com.coldblue.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.data.repo.MandaRepo
import com.coldblue.data.repo.TodoRepo
import com.coldblue.data.repo.UserPreferencesRepo
import com.coldblue.data.util.LoginHelper
import com.coldblue.domain.database.manda.DeleteAllMandaUseCase
import com.coldblue.domain.database.manda.GetMandaUseCase
import com.coldblue.domain.database.manda.InsertMandaUseCase
import com.coldblue.domain.database.todo.DeleteTodoUseCase
import com.coldblue.domain.database.todo.GetTodoUseCase
import com.coldblue.domain.database.todo.InsertTodoUseCase
import com.coldblue.model.Manda
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
    private val insertTodoUseCase: InsertTodoUseCase
): ViewModel() {

    val test: StateFlow<List<Manda>> =
        getMandaUseCase().map {
            Log.e("TAG", "getMandaData: $it", )
            it
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList<Manda>()
        )

    fun insertManda(){
        viewModelScope.launch {
            insertMandaUseCase(listOf(Manda(id = 0, isSync = false, isDel = false, updateTime = "1999", name = "pureum", color = 11L)))
        }
    }
}