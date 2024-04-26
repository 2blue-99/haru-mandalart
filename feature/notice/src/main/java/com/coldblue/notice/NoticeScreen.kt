package com.coldblue.notice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.designsystem.component.HMTopBar
import com.coldblue.notice.content.NoticeContent

@Composable
fun NoticeScreen(
    navigateToBackStack: () -> Unit,
    noticeViewModel: NoticeViewModel = hiltViewModel(),
) {
    val noticeUiState by noticeViewModel.noticeUIState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NoticeScreenWithState(
            uiState = noticeUiState,
            getNotice = noticeViewModel::getNotice,
            navigateToBackStack = navigateToBackStack
        )
    }


}

@Composable
fun NoticeScreenWithState(
    uiState: NoticeUiState,
    getNotice: (id: Int) -> Unit,
    navigateToBackStack: () -> Unit
) {
    when (uiState) {
        is NoticeUiState.Loading -> {}
        is NoticeUiState.Error -> {}
        is NoticeUiState.Success -> {
            NoticeContent(
                uiState.noticeList,
                getNotice,
                navigateToBackStack
            )
        }
    }


}