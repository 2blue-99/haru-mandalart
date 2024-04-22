package com.coldblue.mandalart.state

import com.coldblue.model.UpdateNote

sealed interface MandaUpdateDialogState {
    data class Error(val msg: String) : MandaUpdateDialogState
    data object Hide : MandaUpdateDialogState
    data class Show(val updateNote: UpdateNote) : MandaUpdateDialogState
}