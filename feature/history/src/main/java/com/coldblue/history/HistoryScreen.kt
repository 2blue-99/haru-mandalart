package com.coldblue.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.MandaTodo

@Composable
fun HistoryScreen(
    historyViewModel: HistoryViewModel = hiltViewModel(),
    navigateToBackStack: () -> Unit,
){

    val historyUIState by historyViewModel.historyUIState.collectAsStateWithLifecycle()

    Column(
        modifier =Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HistoryContentWithState(
            uiState = historyUIState,
            navigateToBackStack = navigateToBackStack,
            changeYear = historyViewModel::changeYear,
            changeDay = historyViewModel::changeDay,
            updateTodo = historyViewModel::updateMandaTodo,
        )
    }
}

@Composable
fun HistoryContentWithState(
    uiState: HistoryUIState,
    navigateToBackStack: () -> Unit,
    changeYear: (String) -> Unit,
    changeDay: (String) -> Unit,
    updateTodo: (MandaTodo) -> Unit,
) {
    when (uiState) {
        is HistoryUIState.Loading -> Text(text = "Loading..", style = HmStyle.text18)
        is HistoryUIState.Error -> Text(text = uiState.msg)
        is HistoryUIState.Success ->
            HistoryContent(
                historyUiState = uiState,
                navigateToBackStack = navigateToBackStack,
                changeYear = changeYear,
                changeDay = changeDay,
                updateTodo = updateTodo,
            )
    }
}