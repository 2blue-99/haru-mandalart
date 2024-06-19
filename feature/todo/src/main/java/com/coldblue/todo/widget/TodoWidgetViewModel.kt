package com.coldblue.todo.widget

import com.coldblue.domain.manda.GetKeyMandaUseCase
import com.coldblue.domain.setting.StartAppUseCase
import com.coldblue.domain.todo.GetMandaTodoUseCase
import com.coldblue.domain.todo.UpsertMandaTodoUseCase
import com.coldblue.model.MandaTodo
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoWidgetViewModel @Inject constructor(
    private val getMandaTodoUseCase: GetMandaTodoUseCase,
    private val upsertMandaTodoUseCase: UpsertMandaTodoUseCase,
    private val startAppUseCase: StartAppUseCase,
    private val getKeyMandaUseCase: GetKeyMandaUseCase,
) {
    val todos = getMandaTodoUseCase().map { it.filter { !it.isDone } }
    val mandaKeys = getKeyMandaUseCase()
    fun startApp() {
        startAppUseCase()
    }

    suspend fun upsertMandaTodo(mandaTodo: MandaTodo) {
        upsertMandaTodoUseCase(mandaTodo)
    }

}