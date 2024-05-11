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
import com.coldblue.domain.todo.GetMandaTodoUseCase
import com.coldblue.domain.todo.GetTodoUseCase
import com.coldblue.domain.todo.UpsertMandaTodoUseCase
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
import com.coldblue.model.MandaTodo
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
    val getMandaTodoUseCase: GetMandaTodoUseCase,
    val upsertMandaTodoUseCase: UpsertMandaTodoUseCase
) : ViewModel() {

    private val _currentIndex = MutableStateFlow(4)
    val currentIndex: StateFlow<Int> get() = _currentIndex

    private val _todoRange = MutableStateFlow(0)
    val todoRange: StateFlow<Int> get() = _todoRange

    private val _mandaBottomSheetUIState =
        MutableStateFlow<MandaBottomSheetUIState>(MandaBottomSheetUIState.Down)
    val mandaBottomSheetUIState: StateFlow<MandaBottomSheetUIState> get() = _mandaBottomSheetUIState

    init {
        viewModelScope.launch {
//            upsertMandaTodoUseCase(MandaTodo("1번투구", false, false, null, LocalDate.now(), 1, false))
//            upsertMandaTodoUseCase(MandaTodo("3번투두 굉장히 긴것이다 이것을 how? 보여주는가 그것은 네이버에도 나와있지 않다 캡틴잭 화이팅 나는 간다 어디든 하하하하하하 이것은 최고의 투두이다 ", false, false, null, LocalDate.now(), 3, false))

        }
    }

    val mandaUiState: StateFlow<MandaUIState> =
        getMandaInitStateUseCase().flatMapLatest { state ->
            if (state) {
                combine(
                    getKeyMandaUseCase(),
                    getDetailMandaUseCase(),
                    getMandaTodoUseCase(),
                    currentIndex,
                    todoRange
                ) { mandaKeys, mandaDetails, todoList, curIndex, todoRange ->
                    val mandaStateList = MandaUtils.transformToMandaList(mandaKeys, mandaDetails)
                    val mandaStatus = MandaStatus(
                        titleManda = MandaUtils.matchingTitleManda(curIndex, mandaStateList),
                        percentageColor = MandaUtils.matchingPercentageColor(
                            curIndex,
                            mandaStateList
                        ),
                        donePercentage = MandaUtils.calculatePercentage(curIndex, mandaDetails)
                    )

                    val curIndexTodoList =
                        if (curIndex != 4 && curIndex != -1) todoList.filter { it.mandaIndex == curIndex } else todoList

                    val curDateRangeTodoList = when (todoRange) {
                        1 -> curIndexTodoList
                        2 -> curIndexTodoList
                        else -> curIndexTodoList
                    }

                    MandaUIState.InitializedSuccess(
                        keyMandaCnt = mandaKeys.size - 1,
                        detailMandaCnt = mandaDetails.size,
                        mandaStatus = mandaStatus,
                        mandaStateList = mandaStateList,
                        mandaKeyList = mandaKeys.map { it.name },
                        currentIndex = curIndex,
                        todoRange = todoRange,
                        todoList = curDateRangeTodoList,
                        todoCnt = curDateRangeTodoList.count { !it.isDone },
                        doneTodoCnt = curDateRangeTodoList.count { it.isDone }
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
        _currentIndex.value = index
    }

    fun changeTodoRange(index: Int) {
        _todoRange.value = index
    }

    fun upsertMandaTodo(mandaTodo: MandaTodo) {
        viewModelScope.launch {
            upsertMandaTodoUseCase(mandaTodo)
        }
    }
}