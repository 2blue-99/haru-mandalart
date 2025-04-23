package com.coldblue.mandalart.state

import com.coldblue.mandalart.model.MandaUI
import com.coldblue.model.DateRange
import com.coldblue.model.MandaTodo

sealed interface MandaUIState {
    data object Loading : MandaUIState
    data class Error(val msg: String) : MandaUIState
    data object UnInitializedSuccess : MandaUIState
    data class InitializedSuccess(
//        val keyMandaCnt: Int,
//        val detailMandaCnt: Int,
        val mandaStatus: MandaStatus,
        val mandaList: List<MandaState>,
        val mandaKeyList: List<String>,
        val usedColorIndexList: List<Int>,
        val currentIndex: Int,
        val todoRange: DateRange,
        val todoList: List<MandaTodo>,
        val todoCnt: Int,
        val doneTodoCnt: Int,
        val mandaChangeInfo: List<MandaInfo>
    ) : MandaUIState
}

sealed interface MandaState {
    val id: Int

    data class Empty(override val id: Int) : MandaState
    data class Exist(
        override val id: Int,
        val mandaUIList: List<MandaType>
    ) : MandaState
}

sealed interface MandaType {
    val mandaUI: MandaUI

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

data class MandaInfo(
    val name:String,
    val index:Int
)

const val MAX_MANDA_CNT = 3