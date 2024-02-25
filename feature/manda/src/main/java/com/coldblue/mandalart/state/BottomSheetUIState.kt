package com.coldblue.mandalart.state

import com.coldblue.model.BottomSheetManda
import com.coldblue.model.DetailManda
import com.coldblue.model.KeyManda
import com.coldblue.model.Tag

sealed interface BottomSheetUIState {
    data object Down : BottomSheetUIState
    data class Up(val bottomSheetMandaUiState: BottomSheetMandaUiState) : BottomSheetUIState
}

sealed interface BottomSheetMandaUiState {
    val bottomSheetManda: BottomSheetManda
    data class Default(override val bottomSheetManda: BottomSheetManda = BottomSheetManda("",0,false)) : BottomSheetMandaUiState
    data class Exist(override val bottomSheetManda: BottomSheetManda) : BottomSheetMandaUiState
}