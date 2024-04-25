package com.coldblue.notice

import com.coldblue.model.Notice

sealed interface NoticeUiState {
    data object Loading : NoticeUiState

    data class Error(val msg: String) : NoticeUiState

    data class Success(
        val noticeList: List<Notice>
    ) : NoticeUiState

}