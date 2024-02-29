package com.coldblue.mandalart.state

import androidx.compose.ui.graphics.Color

sealed interface MandaUIState {
    data object Loading : MandaUIState
    data class Error(val msg: String) : MandaUIState
    data object UnInitializedSuccess : MandaUIState
    data class InitializedSuccess(
        val keyMandaCnt: Int,
        val detailMandaCnt: Int,
        val donePercentage: Float,
        val finalName: String,
        val mandaStateList: List<MandaState>,
    ) : MandaUIState
}

sealed interface MandaState {

    data class Empty(val id: Int) : MandaState

    data class Exist(
        val outlineColor: Color? = null,
        val fillColor: Color? = null,
        val mandaUIList: List<MandaType>
    ) : MandaState
}

sealed interface MandaType {
    val id: Int

    data class Empty(override val id: Int) : MandaType

    data class Fill(
        val name: String = "",
        val mandaId: Int? = null,
        override val id: Int
    ) : MandaType

    data class Done(
        val name: String = "",
        val mandaId: Int? = null,
        override val id: Int
    ) : MandaType
}