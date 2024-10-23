package com.coldblue.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.network.GetNetworkStateUseCase
import com.coldblue.domain.notice.GetNoticeListUseCase
import com.coldblue.domain.notice.GetNoticeUseCase
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val getNoticeListUseCase: GetNoticeListUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getNoticeUseCase: GetNoticeUseCase
) : ViewModel() {
    private val _noticeUIState = MutableStateFlow<NoticeUiState>(NoticeUiState.Loading)
    val noticeUIState: StateFlow<NoticeUiState> get() = _noticeUIState


    init {
        getNoticeList()
    }

    fun getNoticeList() {
        try {
            viewModelScope.launch {
                if (getNetworkStateUseCase().first()) {
                    if(getNoticeListUseCase().isNotEmpty()){
                        _noticeUIState.value = NoticeUiState.Success(getNoticeListUseCase())
                    }else{
                        _noticeUIState.value = NoticeUiState.Error("공지사항 게시판이 비어있습니다.")
                    }
                } else {
                    _noticeUIState.value = NoticeUiState.Error("네트워크 연결 상태가 좋지 않습니다.")
                }
            }
        }catch (e:Exception){

        }

    }

    fun getNotice(id: Int) {
        when (noticeUIState.value) {
            is NoticeUiState.Success -> {
                viewModelScope.launch {
                    if (getNetworkStateUseCase().first()) {
                        val noticeList = (noticeUIState.value as NoticeUiState.Success).noticeList
                        val notice = getNoticeUseCase(id)
                        _noticeUIState.value =
                            NoticeUiState.Success(noticeList.map { if (id == it.id) notice else it })
                    }
                }
            }

            else -> {}
        }
    }
}

