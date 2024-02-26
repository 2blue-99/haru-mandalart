package com.coldblue.mandalart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.manda.GetDetailMandaUseCase
import com.coldblue.domain.manda.GetKeyMandaUseCase
import com.coldblue.domain.manda.UpsertMandaDetailUseCase
import com.coldblue.domain.manda.UpsertMandaKeyUseCase
import com.coldblue.domain.user.GetMandaInitStateUseCase
import com.coldblue.mandalart.state.MandaUIState
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import com.coldblue.model.MandaTag
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
import kotlin.math.roundToInt

@HiltViewModel
class MandaViewModel @Inject constructor(
    private val getMandaInitStateUseCase: GetMandaInitStateUseCase,
    private val getKeyMandaUseCase: GetKeyMandaUseCase,
    private val upsertMandaKeyUseCase: UpsertMandaKeyUseCase,
    private val getDetailMandaUseCase: GetDetailMandaUseCase,
    private val upsertMandaDetailUseCase: UpsertMandaDetailUseCase,
) : ViewModel() {

    val mandaUiState: StateFlow<MandaUIState> =
        getMandaInitStateUseCase().flatMapLatest { state ->
            if (state) {
                getKeyMandaUseCase().combine(getDetailMandaUseCase()) { mandaKeys, mandaDetails ->
                    MandaUIState.InitializedSuccess(
                            keyMandaCnt = mandaKeys.size,
                            detailMandaCnt = mandaDetails.size,
                            donePercentage = (mandaDetails.count { it.isDone } / 64.0 * 100).roundToInt(),
                            keys = mandaKeys,
                            details = mandaDetails
                    )
                }.catch {
                    MandaUIState.Error(it.message ?: "Error")
                }
            } else {
                flowOf(MandaUIState.UnInitializedSuccess(listOf (MandaTag("테그"))))
            }
        }.catch {
            MandaUIState.Error(it.message ?: "Error")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MandaUIState.Loading
        )

    fun upsertMandaFinal(data: MandaKey) {
        viewModelScope.launch {
            upsertMandaKeyUseCase(data)
        }
    }

    fun upsertMandaKey(data: MandaKey) {
        viewModelScope.launch {
            upsertMandaKeyUseCase(data)
        }
    }

    fun upsertMandaDetail(data: MandaDetail) {
        viewModelScope.launch {
            upsertMandaDetailUseCase(data)
        }
    }
}