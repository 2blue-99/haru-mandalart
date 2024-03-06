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
import com.coldblue.mandalart.model.MandaUI
import com.coldblue.mandalart.state.MandaBottomSheetContentState
import com.coldblue.mandalart.state.MandaBottomSheetUIState
import com.coldblue.mandalart.state.MandaUIState
import com.coldblue.mandalart.util.MandaUtils
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
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
    private val getMandaInitStateUseCase: GetMandaInitStateUseCase,
    private val updateMandaInitStateUseCase: UpdateMandaInitStateUseCase,

    private val getKeyMandaUseCase: GetKeyMandaUseCase,
    private val upsertMandaKeyUseCase: UpsertMandaKeyUseCase,
    private val deleteMandaKeyUseCase: DeleteMandaKeyUseCase,

    private val getDetailMandaUseCase: GetDetailMandaUseCase,
    private val upsertMandaDetailUseCase: UpsertMandaDetailUseCase,
    private val deleteMandaDetailUseCase: DeleteMandaDetailUseCase,

    private val deleteMandaAllUseCase: DeleteMandaAllUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            upsertMandaKeyUseCase(MandaKey(id = 1, name = "TEST", colorIndex = 1))
            upsertMandaKeyUseCase(MandaKey(id = 5, name = "TEST", colorIndex = 5))
            upsertMandaKeyUseCase(MandaKey(id = 8, name = "TEST", colorIndex = 7))
            upsertMandaKeyUseCase(MandaKey(id = 9, name = "TEST", colorIndex = 8))
            upsertMandaDetailUseCase(
                MandaDetail(
                    id = 8,
                    name = "TEST",
                    colorIndex = 8,
                    isDone = false
                )
            )
            upsertMandaDetailUseCase(
                MandaDetail(
                    id = 2,
                    name = "TEST",
                    colorIndex = 5,
                    isDone = true
                )
            )
            upsertMandaDetailUseCase(
                MandaDetail(
                    id = 3,
                    name = "TEST",
                    colorIndex = 5,
                    isDone = true
                )
            )
            upsertMandaDetailUseCase(
                MandaDetail(
                    id = 4,
                    name = "TEST",
                    colorIndex = 5,
                    isDone = true
                )
            )
            upsertMandaDetailUseCase(
                MandaDetail(
                    id = 6,
                    name = "TEST",
                    colorIndex = 5,
                    isDone = true
                )
            )
        }
    }

    val mandaUiState: StateFlow<MandaUIState> =
        getMandaInitStateUseCase().flatMapLatest { state ->
            if (state) {
                getKeyMandaUseCase().combine(getDetailMandaUseCase()) { mandaKeys, mandaDetails ->
                    Log.e("TAG", "mandaKeys : $mandaKeys")
                    Log.e("TAG", "mandaDetails : $mandaDetails")
                    MandaUIState.InitializedSuccess(
                        keyMandaCnt = mandaKeys.size - 1,
                        detailMandaCnt = mandaDetails.size,
                        donePercentage = mandaDetails.count { it.isDone } / mandaDetails.size.toFloat(),
                        finalName = mandaKeys.last().name,
                        mandaStateList = MandaUtils.transformToMandaList(mandaKeys, mandaDetails),
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

    fun deleteMandaKey(id: Int) {
        Log.e("TAG", "deleteMandaKey: $id")
        viewModelScope.launch {
            deleteMandaKeyUseCase(id)
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