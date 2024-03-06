package com.coldblue.mandalart.state

import com.coldblue.mandalart.model.MandaUI

sealed interface MandaBottomSheetUIState {
    data object Down : MandaBottomSheetUIState
    data class Up(val mandaBottomSheetContentState: MandaBottomSheetContentState) : MandaBottomSheetUIState
}

sealed interface MandaBottomSheetContentState {
    val mandaBottomSheetContentType: MandaBottomSheetContentType

    data class Insert(
        override val mandaBottomSheetContentType: MandaBottomSheetContentType
    ) : MandaBottomSheetContentState

    data class Update(
        override val mandaBottomSheetContentType: MandaBottomSheetContentType,
    ) : MandaBottomSheetContentState
}

sealed interface MandaBottomSheetContentType {
    val mandaUI: MandaUI
    val title: String

    data class MandaFinal(
        override val mandaUI: MandaUI
    ) : MandaBottomSheetContentType {
        override val title = "최종 목표"
    }

    data class MandaKey(
        override val mandaUI: MandaUI,
        val groupIdList: List<Int>?
    ) : MandaBottomSheetContentType {
        override val title = "핵심 목표"
    }

    data class MandaDetail(
        override val mandaUI: MandaUI
    ) : MandaBottomSheetContentType {
        override val title = "세부 목표"
    }
}

