package com.coldblue.explain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.auth.LoginSucceededUseCase
import com.coldblue.domain.user.GetExplainStateUseCase
import com.coldblue.domain.user.UpdateExplainStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExplainViewModel @Inject constructor(
    getExplainStateUseCase: GetExplainStateUseCase,
    private val updateExplainStateUseCase: UpdateExplainStateUseCase,
    private val loginSucceededUseCase: LoginSucceededUseCase,
) : ViewModel() {

    val mandaExplainUIState: StateFlow<Boolean> =
        getExplainStateUseCase().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun finishedExplain(){
        viewModelScope.launch {
            updateExplainStateUseCase(true)
            loginSucceededUseCase()
        }
    }
}