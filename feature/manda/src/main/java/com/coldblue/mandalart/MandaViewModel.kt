package com.coldblue.mandalart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.manda.DeleteMandaUseCase
import com.coldblue.domain.manda.DeleteMandaDetailUseCase
import com.coldblue.domain.manda.DeleteMandaKeyUseCase
import com.coldblue.domain.manda.GetDetailMandaUseCase
import com.coldblue.domain.manda.GetKeyMandaUseCase
import com.coldblue.domain.manda.UpsertMandaDetailUseCase
import com.coldblue.domain.manda.UpsertMandaKeyUseCase
import com.coldblue.domain.todo.GetMandaTodoUseCase
import com.coldblue.domain.todo.UpsertMandaTodoUseCase
import com.coldblue.domain.user.GetCurrentMandaIndexUseCase
import com.coldblue.domain.user.GetExplainStateUseCase
import com.coldblue.domain.user.GetMandaInitStateUseCase
import com.coldblue.domain.user.UpdateCurrentMandaIndexUseCase
import com.coldblue.domain.user.UpdateMandaInitStateUseCase
import com.coldblue.mandalart.state.CurrentManda
import com.coldblue.mandalart.state.MandaBottomSheetContentState
import com.coldblue.mandalart.state.MandaBottomSheetUIState
import com.coldblue.mandalart.state.MandaStatus
import com.coldblue.mandalart.state.MandaUIState
import com.coldblue.mandalart.util.MandaUtils
import com.coldblue.mandalart.util.MandaUtils.mandaChangeInfo
import com.coldblue.model.DateRange
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import com.coldblue.model.MandaTodo
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
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
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
    private val deleteMandaUseCase: DeleteMandaUseCase,
    val getMandaTodoUseCase: GetMandaTodoUseCase,
    val upsertMandaTodoUseCase: UpsertMandaTodoUseCase,
    val getExplainStateUseCase: GetExplainStateUseCase,
    val getCurrentMandaIndexUseCase: GetCurrentMandaIndexUseCase,
    val updateCurrentMandaIndexUseCase: UpdateCurrentMandaIndexUseCase
) : ViewModel() {

    private val _currentManda = MutableStateFlow(CurrentManda(0, 4))
    val currentManda: StateFlow<CurrentManda> get() = _currentManda

    init {
        viewModelScope.launch {
            getCurrentMandaIndexUseCase().collect { mandaIndex ->
                _currentManda.value = _currentManda.value.copy(currentManda = mandaIndex)
            }
        }
    }

    /**
     * 다른 앱 위 표시 권한 요청을 해야 하는지 여부
     */
    private var isRequestPermission = true

    private val _explainUIState = MutableStateFlow(true)
    val explainUIState: StateFlow<Boolean> get() = _explainUIState

//    private val _currentIndex = MutableStateFlow(4)
//    val currentIndex: StateFlow<Int> get() = _currentIndex


    private val _todoRange = MutableStateFlow(DateRange.ALL)
    val todoRange: StateFlow<DateRange> get() = _todoRange

    private val _mandaBottomSheetUIState =
        MutableStateFlow<MandaBottomSheetUIState>(MandaBottomSheetUIState.Down)
    val mandaBottomSheetUIState: StateFlow<MandaBottomSheetUIState> get() = _mandaBottomSheetUIState

    val mandaUiState: StateFlow<MandaUIState> =
        getMandaInitStateUseCase().flatMapLatest { state ->
            if (state) {
                combine(
                    getKeyMandaUseCase(),
                    getDetailMandaUseCase(),
                    getMandaTodoUseCase(),
                    currentManda,
                    todoRange,
                ) { mandaKeys, mandaDetails, todoList, currentManda, todoRange ->
                    val mandaKey1to9 = MandaUtils.mandaKey1to9(mandaKeys, currentManda.currentManda)
                    val mandaDetail1to81 =
                        MandaUtils.mandaDetail1to81(mandaDetails, currentManda.currentManda)

                    val mandaList = MandaUtils.transformToMandaList(
                        mandaKey1to9,
                        mandaDetail1to81
                    )
                    val mandaInfo = mandaChangeInfo(mandaKeys)

                    val currentTodoList =
                        MandaUtils.mandaTodo1to9(todoList, currentManda.currentManda)

                    val usedColorIndexList =
                        mandaKey1to9.filter { it.id != 5 }.map { it.colorIndex }.toSet().toList()
                    val mandaStatus = MandaStatus(
                        titleManda = MandaUtils.matchingTitleManda(
                            currentManda.currentIndex,
                            mandaList
                        ),
                        statusColor = MandaUtils.matchingPercentageColor(
                            currentManda.currentIndex,
                            mandaList
                        ),
                        donePercentage = MandaUtils.calculatePercentage(
                            currentManda.currentIndex,
                            mandaDetail1to81
                        )
                    )

                    val curIndexTodoList =
                        if (currentManda.currentIndex != 4 && currentManda.currentIndex != -1) currentTodoList.filter { it.mandaIndex == currentManda.currentIndex } else currentTodoList


                    val curDateRangeTodoList = when (todoRange) {
                        DateRange.DAY -> curIndexTodoList.filter { it.date == LocalDate.now() }
                        DateRange.WEEK -> {
                            val startOfWeek = LocalDate.now()
                                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                            val endOfWeek =
                                LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                            curIndexTodoList.filter {
                                it.date.isAfter(startOfWeek) && it.date.isBefore(
                                    endOfWeek
                                )
                            }
                        }

                        else -> curIndexTodoList
                    }

                    MandaUIState.InitializedSuccess(
//                        keyMandaCnt = mandaKeys.size - 1,
//                        detailMandaCnt = mandaDetails.size,
                        mandaStatus = mandaStatus,
                        mandaList = mandaList,
                        mandaKeyList = mandaKey1to9.map { it.name },
                        usedColorIndexList = usedColorIndexList,
                        currentIndex = currentManda.currentIndex,
                        todoRange = todoRange,
                        todoList = curDateRangeTodoList,
                        todoCnt = curDateRangeTodoList.count { !it.isDone },
                        doneTodoCnt = curDateRangeTodoList.count { it.isDone },
                        mandaChangeInfo = mandaInfo
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

    fun changeManda(index: Int) {
        viewModelScope.launch {
            updateCurrentMandaIndexUseCase(index)
        }
//        _currentManda.value = CurrentManda(index, 4)
    }

    fun changeBottomSheet(isShow: Boolean, uiState: MandaBottomSheetContentState?) {
        if (isShow && uiState != null) {
            _mandaBottomSheetUIState.value = MandaBottomSheetUIState.Up(uiState)
        } else {
            _mandaBottomSheetUIState.value = MandaBottomSheetUIState.Down
        }
    }

    fun upsertMandaFinal(mandaKey: MandaKey) {
        viewModelScope.launch {
            upsertMandaKeyUseCase(mandaKey.copy(id = 5 + (currentManda.value.currentManda * 9)))
        }
    }

    fun upsertMandaKey(mandaKey: MandaKey) {
        viewModelScope.launch {
            upsertMandaKeyUseCase(mandaKey.copy(id = mandaKey.id + (currentManda.value.currentManda * 9)))
        }
    }

    fun upsertMandaDetail(mandaDetail: MandaDetail) {
        viewModelScope.launch {
            upsertMandaDetailUseCase(mandaDetail.copy(id = mandaDetail.id + (currentManda.value.currentManda * 81)))
        }
    }

    fun deleteMandaKey(id: Int, detailIdList: List<Int>) {
        viewModelScope.launch {
            deleteMandaKeyUseCase(
                id + (currentManda.value.currentManda * 9),
                detailIdList.map { it + (currentManda.value.currentManda * 81) })
        }
    }

    fun deleteMandaDetail(id: Int) {
        viewModelScope.launch {
            deleteMandaDetailUseCase(id + (currentManda.value.currentManda * 81))
        }
    }

    fun deleteManda(mandaIndex: Int) {
        viewModelScope.launch {
            deleteMandaUseCase(mandaIndex)
        }
    }

    fun updateMandaInitState(state: Boolean) {
        viewModelScope.launch {
            updateMandaInitStateUseCase(state)
        }
    }

    fun changeCurrentIndex(index: Int) {
        _currentManda.value = CurrentManda(currentManda.value.currentManda, index)
//        _currentIndex.value = index
    }

    fun changeTodoRange(dateRange: DateRange) {
        _todoRange.value = dateRange
    }

    fun upsertMandaTodo(mandaTodo: MandaTodo) {
        viewModelScope.launch {
            upsertMandaTodoUseCase(mandaTodo.copy(mandaIndex = mandaTodo.mandaIndex + (currentManda.value.currentManda * 9)))
        }
    }

    fun updateExplainState() {
        viewModelScope.launch {
            _explainUIState.emit(getExplainStateUseCase().first())
        }
    }

    /**
     * 다른 앱 위 표시 권한 요청 여부 체크
     */
    fun getRequestPermission(): Boolean = isRequestPermission

    /**
     * 다른 앱 위 표시 권한 요청 상태 저장
     */
    fun setRequestPermission() {
        isRequestPermission = false
    }


}