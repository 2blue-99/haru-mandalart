package com.coldblue.mandalart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.manda.DeleteMandaAllUseCase
import com.coldblue.domain.manda.DeleteMandaDetailUseCase
import com.coldblue.domain.manda.DeleteMandaKeyUseCase
import com.coldblue.domain.manda.GetDetailMandaUseCase
import com.coldblue.domain.manda.GetKeyMandaUseCase
import com.coldblue.domain.manda.UpsertMandaDetailUseCase
import com.coldblue.domain.manda.UpsertMandaKeyUseCase
import com.coldblue.domain.todo.GetTodoUseCase
import com.coldblue.domain.todo.UpsertTodoUseCase
import com.coldblue.domain.user.GetMandaInitStateUseCase
import com.coldblue.domain.user.UpdateMandaInitStateUseCase
import com.coldblue.mandalart.state.MandaBottomSheetContentState
import com.coldblue.mandalart.state.MandaBottomSheetUIState
import com.coldblue.mandalart.state.MandaState
import com.coldblue.mandalart.state.MandaStatus
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
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
    private val deleteMandaAllUseCase: DeleteMandaAllUseCase,
//    val getTodoUseCase: GetTodoUseCase,
//    val upsertTodoUseCase: UpsertTodoUseCase,
) : ViewModel() {

    private val _currentIndex = MutableStateFlow(4)
    val currentIndex: StateFlow<Int> get() = _currentIndex

    private val _todoRange = MutableStateFlow(0)
    val todoRange: StateFlow<Int> get() = _todoRange

    private val _mandaBottomSheetUIState = MutableStateFlow<MandaBottomSheetUIState>(MandaBottomSheetUIState.Down)
    val mandaBottomSheetUIState: StateFlow<MandaBottomSheetUIState> get() = _mandaBottomSheetUIState

    init {
        val d = getKeyMandaUseCase()
        viewModelScope.launch {
            Logger.d(d.first())

        }
    }

    val mandaUiState: StateFlow<MandaUIState> =
        getMandaInitStateUseCase().flatMapLatest { state ->
            if (state) {
                combine(
                    getKeyMandaUseCase(),
                    getDetailMandaUseCase(),
//                    getTodoUseCase(LocalDate.now()),
                    currentIndex,
                    todoRange
                ) { mandaKeys, mandaDetails, curIndex, todoRange ->
                    val mandaStateList = MandaUtils.transformToMandaList(mandaKeys, mandaDetails)
                    val mandaStatus = MandaStatus(
                        titleManda = MandaUtils.matchingTitleManda(curIndex, mandaStateList),
                        percentageColor = MandaUtils.matchingPercentageColor(curIndex, mandaStateList),
                        donePercentage = MandaUtils.calculatePercentage(curIndex, mandaDetails)
                    )
                    MandaUIState.InitializedSuccess(
                        keyMandaCnt = mandaKeys.size - 1,
                        detailMandaCnt = mandaDetails.size,
                        mandaStatus = mandaStatus,
                        mandaStateList = mandaStateList,
                        mandaKeyList = mandaKeys.map { it.name },
                        currentIndex = curIndex,
                        todoRange = todoRange,
                        todoList = emptyList(),
                        todoCnt = 2,
                        doneTodoCnt = 3
                    )
                }.catch {
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

    fun changeBottomSheet(isShow: Boolean, uiState: MandaBottomSheetContentState?) {
        if (isShow && uiState != null) {
            _mandaBottomSheetUIState.value = MandaBottomSheetUIState.Up(uiState)
        } else {
            _mandaBottomSheetUIState.value = MandaBottomSheetUIState.Down
        }
    }

    fun upsertMandaFinal(mandaKey: MandaKey) {
        viewModelScope.launch {
            upsertMandaKeyUseCase(mandaKey.copy(id = 5))
        }
    }

    fun upsertMandaKey(mandaKey: MandaKey) {
        viewModelScope.launch {
            upsertMandaKeyUseCase(mandaKey)
        }
    }

    fun upsertMandaDetail(mandaDetail: MandaDetail) {
        viewModelScope.launch {
            upsertMandaDetailUseCase(mandaDetail)
        }
    }

    fun deleteMandaKey(id: Int, detailIdList: List<Int>) {
        viewModelScope.launch {
            deleteMandaKeyUseCase(id, detailIdList)
        }
    }

    fun deleteMandaDetail(id: Int) {
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

    fun changeCurrentIndex(index: Int) {
        Logger.d(index)
        _currentIndex.value = index
    }

    fun changeTodoRange(index: Int) {
        _todoRange.value = index
    }
}