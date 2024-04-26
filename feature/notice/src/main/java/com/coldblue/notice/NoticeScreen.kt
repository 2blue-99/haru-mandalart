package com.coldblue.notice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.designsystem.component.HMTopBar
import com.coldblue.designsystem.theme.HMColor
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
            getNoticeList = noticeViewModel::getNoticeList,
            navigateToBackStack = navigateToBackStack
        )
    }
}

@Composable
fun NoticeScreenWithState(
    uiState: NoticeUiState,
    getNotice: (id: Int) -> Unit,
    getNoticeList: () -> Unit,
    navigateToBackStack: () -> Unit
) {
    when (uiState) {
        is NoticeUiState.Loading -> {
            Text(text = "")
        }

        is NoticeUiState.Error -> {
            Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
                IconButton(onClick = { getNoticeList() }) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "다시 시도")
                }
                Text(text = uiState.msg, color = HMColor.SubDarkText)
            }
        }

        is NoticeUiState.Success -> {
            NoticeContent(
                uiState.noticeList,
                getNotice,
                navigateToBackStack
            )
        }
    }

}