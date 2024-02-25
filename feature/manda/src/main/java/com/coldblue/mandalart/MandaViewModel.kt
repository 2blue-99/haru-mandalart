package com.coldblue.mandalart

import androidx.lifecycle.ViewModel
import com.coldblue.mandalart.state.MandaUIState
import com.coldblue.model.DetailManda
import com.coldblue.model.KeyManda
import kotlinx.coroutines.flow.StateFlow

class MandaViewModel: ViewModel() {

    val mandaUiState: StateFlow<MandaUIState> = TODO()
        // TODO 1. Data Store로 초기화 유무 확인
        // TODO 2. MandaUIState 만든 후 방출


    fun insertFinalManda(manda: KeyManda){

    }
    fun updateFinalManda(manda: KeyManda){

    }


    fun insertKeyManda(manda: KeyManda){

    }
    fun updateKeyManda(manda: KeyManda){

    }
    fun deleteKeyManda(manda: KeyManda){

    }


    fun insertDetailManda(manda: DetailManda){

    }
    fun updateDetailManda(manda: DetailManda){

    }
    fun deleteDetailManda(manda: DetailManda){

    }
}