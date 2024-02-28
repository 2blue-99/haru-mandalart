package com.coldblue.mandalart.state

import com.coldblue.mandalart.model.MandaUI

sealed interface MandaUIState {
    data object Loading : MandaUIState
    data class Error(val msg: String) : MandaUIState
    data object UnInitializedSuccess : MandaUIState
    data class InitializedSuccess(
        val keyMandaCnt: Int,
        val detailMandaCnt: Int,
        val donePercentage: Float,
        val finalName: String,
        val keys: List<MandaType>,
        val details: List<MandaType>
    ) : MandaUIState
}

sealed interface MandaType {
    val manda: MandaUI

    data class Empty(override val manda: MandaUI) : MandaType

    data class Fill(override val manda: MandaUI) : MandaType

    data class Done(override val manda: MandaUI) : MandaType
}