package com.coldblue.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.todo.GetAllMandaTodoCountUseCase
import com.coldblue.domain.todo.GetMandaTodoByIndexUseCase
import com.coldblue.domain.todo.UpsertMandaTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.logging.Logger
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getMandaTodoByIndexUseCase: GetMandaTodoByIndexUseCase,
    private val getAllMandaTodoCountUseCase: GetAllMandaTodoCountUseCase,
    private val upsertMandaTodoUseCase: UpsertMandaTodoUseCase
): ViewModel(){
    init {
        CoroutineScope(Dispatchers.IO).launch {
            com.orhanobut.logger.Logger.d(getAllMandaTodoCountUseCase())
        }
    }
}