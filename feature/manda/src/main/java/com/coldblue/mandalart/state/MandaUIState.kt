package com.coldblue.mandalart.state

import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import com.coldblue.model.MandaTag

sealed interface MandaUIState {
    data object Loading : MandaUIState
    data class Error(val msg: String) : MandaUIState
    data class Success(val successData: SuccessType) : MandaUIState
}

sealed interface SuccessType {
    data class FinalMandaSuccess(val tags: MandaTag) : SuccessType
    data class MandaSuccess(
        val keyMandaCnt: Int,
        val detailMandaCnt: Int,
        val donePercentage: Int,
        val keys: List<MandaKey>,
        val details: List<MandaDetail>
    ) : SuccessType
}