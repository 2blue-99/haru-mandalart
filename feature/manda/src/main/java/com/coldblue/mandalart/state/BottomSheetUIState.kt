package com.coldblue.mandalart.state

import com.coldblue.mandalart.model.MandaUI

sealed interface BottomSheetUIState {
    data object Down : BottomSheetUIState
    data class Up(val bottomSheetContentState: BottomSheetContentState) : BottomSheetUIState
}

sealed interface BottomSheetContentState {
    val bottomSheetContentType: BottomSheetContentType

    data class Insert(
        override val bottomSheetContentType: BottomSheetContentType
    ) : BottomSheetContentState

    data class Update(
        override val bottomSheetContentType: BottomSheetContentType
    ) : BottomSheetContentState
}

sealed interface BottomSheetContentType {
    val mandaUI: MandaUI
    val title: String

    data class MandaFinal(
        override val mandaUI: MandaUI
    ) : BottomSheetContentType {
        override val title = "최종 목표"
    }

    data class MandaKey(
        override val mandaUI: MandaUI = MandaUI("", id = 0)
    ) : BottomSheetContentType {
        override val title = "핵심 목표"
    }

    data class MandaDetail(
        override val mandaUI: MandaUI = MandaUI("", id = 0)
    ) : BottomSheetContentType {
        override val title = "세부 목표"
    }
}

