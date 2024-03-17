package com.coldblue.mandalart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.manda.DeleteMandaAllUseCase
import com.coldblue.domain.manda.DeleteMandaDetailUseCase
import com.coldblue.domain.manda.DeleteMandaKeyUseCase
import com.coldblue.domain.manda.GetDetailMandaUseCase
import com.coldblue.domain.manda.GetKeyMandaUseCase
import com.coldblue.domain.manda.UpsertMandaDetailUseCase
import com.coldblue.domain.manda.UpsertMandaKeyUseCase
import com.coldblue.domain.user.GetMandaInitStateUseCase
import com.coldblue.domain.user.UpdateMandaInitStateUseCase
import com.coldblue.mandalart.state.MandaBottomSheetContentState
import com.coldblue.mandalart.state.MandaBottomSheetUIState
import com.coldblue.mandalart.state.MandaUIState
import com.coldblue.mandalart.util.MandaUtils
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MandaViewModel @Inject constructor(
    getMandaInitStateUseCase: GetMandaInitStateUseCase,
    private val updateMandaInitStateUseCase: UpdateMandaInitStateUseCase,
    getKeyMandaUseCase: GetKeyMandaUseCase,
    private val upsertMandaKeyUseCase: UpsertMandaKeyUseCase,
    private val deleteMandaKeyUseCase: DeleteMandaKeyUseCase,
    getDetailMandaUseCase: GetDetailMandaUseCase,
    private val upsertMandaDetailUseCase: UpsertMandaDetailUseCase,
    private val deleteMandaDetailUseCase: DeleteMandaDetailUseCase,
    private val deleteMandaAllUseCase: DeleteMandaAllUseCase
) : ViewModel() {

    val mandaUiState: StateFlow<MandaUIState> =
        getMandaInitStateUseCase().flatMapLatest { state ->
            if (state) {
                getKeyMandaUseCase().combine(getDetailMandaUseCase()) { mandaKeys, mandaDetails ->
                    Log.e("TAG", "mandaKeys : $mandaKeys")
                    Log.e("TAG", "mandaDetails : $mandaDetails")
                    MandaUIState.InitializedSuccess(
                        keyMandaCnt = mandaKeys.size - 1,
                        detailMandaCnt = mandaDetails.size,
                        donePercentage = (mandaDetails.count { it.isDone } / mandaDetails.size.toFloat()).takeIf { !it.isNaN() } ?: 0f,
                        finalName = mandaKeys.first { it.id == 5 }.name,
                        mandaStateList = MandaUtils.transformToMandaList(mandaKeys, mandaDetails),
                        mandaKeyList = mandaKeys.map { it.name }
                    )
                }.catch {
                    Log.e("TAG", "${it}: ")
                    MandaUIState.Error(it.message ?: "Error")
                }
            } else {
                flowOf(MandaUIState.UnInitializedSuccess)
            }
        }.catch {
            MandaUIState.Error(it.message ?: "Error")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MandaUIState.Loading
        )

    private val _mandaBottomSheetUIState =
        MutableStateFlow<MandaBottomSheetUIState>(MandaBottomSheetUIState.Down)
    val mandaBottomSheetUIState: StateFlow<MandaBottomSheetUIState> get() = _mandaBottomSheetUIState

    fun changeBottomSheet(isShow: Boolean, uiState: MandaBottomSheetContentState?) {
        Log.e("TAG", "changeBottomSheet ishow : $isShow // uiState : $uiState ")

        if (isShow && uiState != null) {
            _mandaBottomSheetUIState.value = MandaBottomSheetUIState.Up(uiState)
        } else {
            _mandaBottomSheetUIState.value = MandaBottomSheetUIState.Down
        }
    }

    fun upsertMandaFinal(text: String) {
        Logger.d(text)
        Log.e("TAG", "upsertMandaFinal: $text")
        viewModelScope.launch {
            upsertMandaKeyUseCase(MandaKey(id = 5, name = text))
        }
    }

    fun upsertMandaKey(mandaKey: MandaKey) {
        Log.e("TAG", "upsertMandaKey: $mandaKey")
        viewModelScope.launch {
            upsertMandaKeyUseCase(mandaKey)
        }
    }

    fun upsertMandaDetail(mandaDetail: MandaDetail) {
        Log.e("TAG", "upsertMandaDetail: $mandaDetail")
        viewModelScope.launch {
            upsertMandaDetailUseCase(mandaDetail)
        }
    }

    fun deleteMandaKey(id: Int, detailIdList: List<Int>) {
        Log.e("TAG", "deleteMandaKey: $id")
        viewModelScope.launch {
            deleteMandaKeyUseCase(id, detailIdList)
        }
    }

    fun deleteMandaDetail(id: Int) {
        Log.e("TAG", "deleteMandaDetail: $id")
        viewModelScope.launch {
            deleteMandaDetailUseCase(id)
        }
    }

    fun deleteMandaAll() {
        viewModelScope.launch {
            deleteMandaAllUseCase()
        }
    }

    fun updateMandaInitState(state: Boolean) {
        viewModelScope.launch {
            updateMandaInitStateUseCase(state)
        }
    }
}