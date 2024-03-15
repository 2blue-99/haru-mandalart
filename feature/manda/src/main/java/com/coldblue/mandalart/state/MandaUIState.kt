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
        val mandaStateList: List<MandaState>,
    ) : MandaUIState
}

sealed interface MandaState {
    val id: Int

    data class Empty(override val id: Int) : MandaState

    // Detail 8개, Key 1개의 박스가 들어있음
    data class Exist(
        override val id: Int,
        val mandaUIList: List<MandaType>
    ) : MandaState
}

sealed interface MandaType {
    val mandaUI : MandaUI

    data class None(
        override val mandaUI: MandaUI
    ) : MandaType

    data class Key(
        override val mandaUI: MandaUI,
        val groupIdList: List<Int>
    ) : MandaType

    data class Detail(
        override val mandaUI: MandaUI
    ) : MandaType
}