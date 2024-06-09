package com.coldblue.todo.widget

import com.coldblue.domain.setting.StartAppUseCase
import com.coldblue.domain.todo.GetMandaTodoUseCase
import com.coldblue.domain.todo.UpsertMandaTodoUseCase
import com.coldblue.model.MandaTodo
import javax.inject.Inject

class TodoWidgetViewModel @Inject constructor(
    private val getMandaTodoUseCase: GetMandaTodoUseCase,
    private val upsertMandaTodoUseCase: UpsertMandaTodoUseCase,
    private val startAppUseCase: StartAppUseCase
) {
//    private val coroutineScope = MainScope()
//    val todos = getMandaTodoUseCase().stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5_000),
//        initialValue = emptyList()
//    )
    val todos = getMandaTodoUseCase()

    val todo = getMandaTodoUseCase()

    fun startApp(){
        startAppUseCase()
    }

    suspend fun upsertMandaTodo(mandaTodo: MandaTodo) {
        upsertMandaTodoUseCase(mandaTodo)
    }

}