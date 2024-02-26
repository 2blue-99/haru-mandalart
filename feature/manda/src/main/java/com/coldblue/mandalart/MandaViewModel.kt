package com.coldblue.mandalart

import android.nfc.Tag
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.manda.GetAllMandaUseCase
import com.coldblue.domain.manda.InsertMandaDetailUseCase
import com.coldblue.domain.manda.InsertMandaKeyUseCase
import com.coldblue.domain.manda.UpdateMandaDetailUseCase
import com.coldblue.domain.manda.UpdateMandaKeyUseCase
import com.coldblue.domain.user.GetMandaInitStateUseCase
import com.coldblue.mandalart.state.MandaUIState
import com.coldblue.mandalart.state.SuccessType
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import com.coldblue.model.MandaTag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.PrintFormat
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class MandaViewModel @Inject constructor(
    private val getMandaInitStateUseCase: GetMandaInitStateUseCase,
    private val getAllMandaUseCase: GetAllMandaUseCase,
    private val insertMandaKeyUseCase: InsertMandaKeyUseCase,
    private val insertMandaDetailUseCase: InsertMandaDetailUseCase,
    private val updateMandaKeyUseCase: UpdateMandaKeyUseCase,
    private val updateMandaDetailUseCase: UpdateMandaDetailUseCase
) : ViewModel() {

    val mandaUiState: StateFlow<MandaUIState> =
        getMandaInitStateUseCase().flatMapLatest { state ->
            if (state) {
                getAllMandaUseCase().map { data ->
                    //TODO 핵심 달성 여부 제외한 Manda / 64 * 100 의 반올림
                    val donePercentage = (data.mandaDetails.count { it.isDone } / 64.0 * 100).roundToInt()
                    MandaUIState.Success(
                        SuccessType.MandaSuccess(
                            keyMandaCnt = data.mandaKeys.size,
                            detailMandaCnt = data.mandaDetails.size,
                            donePercentage = donePercentage,
                            keys = data.mandaKeys,
                            details = data.mandaDetails
                        )
                    )
                }.catch {
                    MandaUIState.Error(it.message ?: "Error")
                }
            } else {
                flowOf(MandaUIState.Success(SuccessType.FinalMandaSuccess(MandaTag(""))))
            }
        }.catch {
            MandaUIState.Error(it.message ?: "Error")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MandaUIState.Loading
        )
    fun insertFinalManda(data: MandaKey) {
        viewModelScope.launch {
            insertMandaKeyUseCase(data)
        }
    }

    fun updateFinalManda(data: MandaKey) {
        viewModelScope.launch {
            updateMandaKeyUseCase(data)
        }
    }


    fun insertKeyManda(data: MandaKey) {
        viewModelScope.launch {
            updateMandaKeyUseCase(data)
        }
    }

    fun updateKeyManda(data: MandaKey) {
        viewModelScope.launch {
            updateMandaKeyUseCase(data)
        }
    }

    fun deleteKeyManda(data: MandaKey) {
        viewModelScope.launch {
            updateMandaKeyUseCase(data)
        }
    }


    fun insertDetailManda(data: MandaDetail) {
        viewModelScope.launch {
            updateMandaDetailUseCase(data)
        }
    }

    fun updateDetailManda(data: MandaDetail) {
        viewModelScope.launch {
            updateMandaDetailUseCase(data)
        }
    }

    fun deleteDetailManda(data: MandaDetail) {
        viewModelScope.launch {
            updateMandaDetailUseCase(data)
        }
    }
}