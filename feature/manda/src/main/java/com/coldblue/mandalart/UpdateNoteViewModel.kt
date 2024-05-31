package com.coldblue.mandalart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.data.util.SettingHelper
import com.coldblue.domain.manda.GetUpdateNoteUseCase
import com.coldblue.domain.user.GetNoteRequestDateUseCase
import com.coldblue.domain.user.UpdateNoteRequestDateUseCase
import com.coldblue.mandalart.state.MandaUpdateDialogState
import com.coldblue.model.UpdateNote
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class UpdateNoteViewModel @Inject constructor(
    private val getUpdateNoteUseCase: GetUpdateNoteUseCase,
    private val getNoteRequestDateUseCase: GetNoteRequestDateUseCase,
    private val updateNoteRequestDateUseCase: UpdateNoteRequestDateUseCase,
    private val settingHelper: SettingHelper

) : ViewModel() {

    private val _mandaUpdateDialogUIState =
        MutableStateFlow<MandaUpdateDialogState>(MandaUpdateDialogState.Hide)
    val mandaUpdateDialogUIState: StateFlow<MandaUpdateDialogState> get() = _mandaUpdateDialogUIState

    private var updateNoteState: Boolean = true

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
            if(updateNoteState){
                val beforeRequestDay = LocalDate.parse(getNoteRequestDateUseCase().first())
                val presentRequestDay = LocalDate.now()
                if(presentRequestDay.compareTo(beforeRequestDay) >= 7) {
                    changeUpdateNoteDialog(true, getUpdateNoteUseCase())
                    updateNoteRequestDateUseCase(presentRequestDay.toString())
                }
                updateNoteState = false
            }
        }
    }
}