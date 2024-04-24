package com.coldblue.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.notice.GetNoticeListUseCase
import com.coldblue.domain.notice.GetNoticeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    getNoticeListUseCase: GetNoticeListUseCase,
    private val getNoticeUseCase: GetNoticeUseCase
) : ViewModel() {
    private val _noticeUIState = MutableStateFlow<NoticeUiState>(NoticeUiState.Loading)
    val noticeUIState: StateFlow<NoticeUiState> get() = _noticeUIState

    init {
        viewModelScope.launch {
            _noticeUIState.value = NoticeUiState.Success(getNoticeListUseCase())
        }
    }

    fun getNotice(id: Int) {
        when (noticeUIState.value) {
            is NoticeUiState.Success -> {
                viewModelScope.launch {
                    val noticeList = (noticeUIState.value as NoticeUiState.Success).noticeList
                    val notice = getNoticeUseCase(id)
                    _noticeUIState.value =
                        NoticeUiState.Success(noticeList.map { if (id == it.id) notice else it })
                }
            }
            else -> {}
        }
    }
}
