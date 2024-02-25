package com.coldblue.mandalart

import androidx.lifecycle.ViewModel
import com.coldblue.mandalart.state.MandaUIState
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import kotlinx.coroutines.flow.StateFlow

class MandaViewModel: ViewModel() {

    val mandaUiState: StateFlow<MandaUIState> = TODO()
        // TODO 1. Data Store로 초기화 유무 확인
        // TODO 2. MandaUIState 만든 후 방출


    fun insertFinalManda(manda: MandaKey){

    }
    fun updateFinalManda(manda: MandaKey){

    }


    fun insertKeyManda(manda: MandaKey){

    }
    fun updateKeyManda(manda: MandaKey){

    }
    fun deleteKeyManda(manda: MandaKey){

    }


    fun insertDetailManda(manda: MandaDetail){

    }
    fun updateDetailManda(manda: MandaDetail){

    }
    fun deleteDetailManda(manda: MandaDetail){

    }
}