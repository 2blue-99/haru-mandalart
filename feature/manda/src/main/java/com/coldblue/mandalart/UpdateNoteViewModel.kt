package com.coldblue.mandalart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.data.util.SettingHelper
import com.coldblue.domain.manda.GetUpdateNoteUseCase
import com.coldblue.mandalart.state.MandaUpdateDialogState
import com.coldblue.model.UpdateNote
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateNoteViewModel @Inject constructor(
    private val getUpdateNoteUseCase: GetUpdateNoteUseCase,
    private val settingHelper: SettingHelper
) : ViewModel() {

    private val _mandaUpdateDialogUIState =
        MutableStateFlow<MandaUpdateDialogState>(MandaUpdateDialogState.Hide)
    val mandaUpdateDialogUIState: StateFlow<MandaUpdateDialogState> get() = _mandaUpdateDialogUIState


    fun changeUpdateNoteDialog(isShow: Boolean, updateNote: UpdateNote? = null) {
        if (isShow && updateNote != null) {
            _mandaUpdateDialogUIState.value = MandaUpdateDialogState.Show(updateNote)
        }
        else
            _mandaUpdateDialogUIState.value = MandaUpdateDialogState.Hide
    }

    fun showPlayStore() {
        settingHelper.showPlayStore()
    }

    fun getUpdateNote(){
        viewModelScope.launch {
            changeUpdateNoteDialog(true, getUpdateNoteUseCase())
        }.runCatching {}
    }
}