package com.coldblue.explain

import android.util.Log
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
    private val updateExplainStateUseCase: UpdateExplainStateUseCase,
) : ViewModel() {

    fun updateExplainState(){
        viewModelScope.launch {
            updateExplainStateUseCase(true)
        }
    }
}