package com.coldblue.mandalart.state

import com.coldblue.model.MandaBottomSheet

sealed interface BottomSheetUIState {
    data object Down : BottomSheetUIState
    data class Up(val bottomSheetMandaUiState: BottomSheetMandaUiState) : BottomSheetUIState
}

sealed interface BottomSheetMandaUiState {
    val mandaBottomSheet: MandaBottomSheet
    data class Default(override val mandaBottomSheet: MandaBottomSheet = MandaBottomSheet("",0,false)) : BottomSheetMandaUiState
    data class Exist(override val mandaBottomSheet: MandaBottomSheet) : BottomSheetMandaUiState
}