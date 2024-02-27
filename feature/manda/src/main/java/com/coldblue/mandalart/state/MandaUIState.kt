package com.coldblue.mandalart.state

import com.coldblue.model.Manda

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
    data object Empty : MandaType

    data class Fill(
        val name: String,
        val mandaId: Int,
        val isDone: Boolean,
        val colorIndex: Int,
        val id: Int
    ) : MandaType

    data class Done(override val data: Manda) : MandaType
}