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
import com.coldblue.domain.todo.UpsertMandaTodoUseCase
import com.coldblue.domain.user.GetExplainStateUseCase
import com.coldblue.domain.user.GetMandaInitStateUseCase
import com.coldblue.domain.user.UpdateMandaInitStateUseCase
import com.coldblue.mandalart.state.MandaBottomSheetContentState
import com.coldblue.mandalart.state.MandaBottomSheetUIState
import com.coldblue.mandalart.state.MandaStatus
import com.coldblue.mandalart.state.MandaUIState
import com.coldblue.mandalart.util.MandaUtils
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
    private val deleteMandaAllUseCase: DeleteMandaAllUseCase,
    val getMandaTodoUseCase: GetMandaTodoUseCase,
    val upsertMandaTodoUseCase: UpsertMandaTodoUseCase,
    val getExplainStateUseCase: GetExplainStateUseCase
) : ViewModel() {

    /**
     * 다른 앱 위 표시 권한 요청을 해야 하는지 여부
     */
    private var isRequestPermission = true

    private val _explainUIState = MutableStateFlow(true)
    val explainUIState: StateFlow<Boolean> get() = _explainUIState

    private val _currentIndex = MutableStateFlow(4)
    val currentIndex: StateFlow<Int> get() = _currentIndex

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
                    currentIndex,
                    todoRange
                ) { mandaKeys, mandaDetails, todoList, curIndex, todoRange ->
                    val mandaList = MandaUtils.transformToMandaList(mandaKeys, mandaDetails)
                    val usedColorIndexList = mandaKeys.filter { it.id != 5 }.map { it.colorIndex }.toSet().toList()
                    val mandaStatus = MandaStatus(
                        titleManda = MandaUtils.matchingTitleManda(curIndex, mandaList),
                        statusColor = MandaUtils.matchingPercentageColor(curIndex, mandaList),
                        donePercentage = MandaUtils.calculatePercentage(curIndex, mandaDetails)
                    )

                    val curIndexTodoList =
                        if (curIndex != 4 && curIndex != -1) todoList.filter { it.mandaIndex == curIndex } else todoList

                    val curDateRangeTodoList = when (todoRange) {
                        DateRange.DAY -> curIndexTodoList.filter { it.date == LocalDate.now() }
                        DateRange.WEEK -> {
                            val startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                            val endOfWeek = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                            curIndexTodoList.filter { it.date.isAfter(startOfWeek) && it.date.isBefore(endOfWeek) }
                        }
                        else -> curIndexTodoList
                    }

                    MandaUIState.InitializedSuccess(
                        keyMandaCnt = mandaKeys.size - 1,
                        detailMandaCnt = mandaDetails.size,
                        mandaStatus = mandaStatus,
                        mandaList = mandaList,
                        mandaKeyList = mandaKeys.map { it.name },
                        usedColorIndexList = usedColorIndexList,
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

    fun changeTodoRange(dateRange: DateRange) {
        _todoRange.value = dateRange
    }

    fun upsertMandaTodo(mandaTodo: MandaTodo) {
        viewModelScope.launch {
            upsertMandaTodoUseCase(mandaTodo)
        }
    }

    fun updateExplainState(){
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
    fun setRequestPermission(){
        isRequestPermission = false
    }
}