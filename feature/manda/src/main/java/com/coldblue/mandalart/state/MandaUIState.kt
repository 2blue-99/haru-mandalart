package com.coldblue.mandalart.state

import com.coldblue.model.DetailManda
import com.coldblue.model.KeyManda
import com.coldblue.model.Tag

sealed interface MandaUIState {
    data object Loading : MandaUIState
    data class Error(val msg: String) : MandaUIState
    data class Success(val successData: SuccessType) : MandaUIState
}

sealed interface SuccessType {
    data class FinalMandaSuccess(val tags: Tag) : SuccessType
    data class MandaSuccess(
        val keyMandaCnt: Int,
        val detailMandaCnt: Int,
        val donePercentage: Int,
        val keys: List<KeyManda>,
        val details: List<DetailManda>
    ) : SuccessType
}