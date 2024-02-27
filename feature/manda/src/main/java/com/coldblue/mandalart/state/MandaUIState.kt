package com.coldblue.mandalart.state

import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey

sealed interface MandaUIState {
    data object Loading : MandaUIState
    data class Error(val msg: String) : MandaUIState
    data object UnInitializedSuccess : MandaUIState
    data class InitializedSuccess(
        val keyMandaCnt: Int,
        val detailMandaCnt: Int,
        val donePercentage: Float,
        val keys: List<MandaKey>,
        val details: List<MandaDetail>
    ) : MandaUIState
}