package com.coldblue.mandalart.state

import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import com.coldblue.model.MandaTag

sealed interface MandaUIState {
    data object Loading : MandaUIState
    data class Error(val msg: String) : MandaUIState
    data class UnInitializedSuccess(val tags: List<MandaTag>) : MandaUIState
    data class InitializedSuccess(
        val keyMandaCnt: Int,
        val detailMandaCnt: Int,
        val donePercentage: Int,
        val keys: List<MandaKey>,
        val details: List<MandaDetail>
    ) : MandaUIState
}