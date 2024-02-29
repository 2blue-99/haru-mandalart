package com.coldblue.mandalart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.manda.GetDetailMandaUseCase
import com.coldblue.domain.manda.GetKeyMandaUseCase
import com.coldblue.domain.manda.UpsertMandaDetailUseCase
import com.coldblue.domain.manda.UpsertMandaKeyUseCase
import com.coldblue.domain.user.GetMandaInitStateUseCase
import com.coldblue.domain.user.UpdateMandaInitStateUseCase
import com.coldblue.mandalart.state.MandaUIState
import com.coldblue.mandalart.util.MandaUtils
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val getDetailMandaUseCase: GetDetailMandaUseCase,
    private val upsertMandaDetailUseCase: UpsertMandaDetailUseCase,
) : ViewModel() {

    init {
        viewModelScope.launch {
            upsertMandaKeyUseCase(MandaKey(id = 1, name = "TEST"))
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
                        donePercentage = mandaDetails.count { it.isDone } / 64.0f,
                        finalName = mandaKeys.last().name,
                        mandaStateList = MandaUtils.transformToMandaList(mandaKeys, mandaDetails),
                    )
                }.catch {
                    Log.e("TAG", "${it}: ", )
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

    fun upsertMandaFinal(text: String) {
        viewModelScope.launch {
            upsertMandaKeyUseCase(MandaKey(id = 5, name = text))
        }
    }

    fun upsertMandaKey(text: String) {
        viewModelScope.launch {
            upsertMandaKeyUseCase(MandaKey(text))
        }
    }

    fun upsertMandaDetail(mandaDetail: MandaDetail) {
        viewModelScope.launch {
            upsertMandaDetailUseCase(mandaDetail)
        }
    }

    fun updateMandaInitState(state: Boolean) {
        viewModelScope.launch {
            updateMandaInitStateUseCase(state)
        }
    }
}