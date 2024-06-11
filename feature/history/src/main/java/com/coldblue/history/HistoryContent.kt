package com.coldblue.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.model.MandaTodo

@Composable
fun HistoryContent(
    historyUiState: HistoryUIState.Success,
    navigateToBackStack: () -> Unit,
    changeYear: (String) -> Unit,
    changeDay: (String) -> Unit,
    updateTodo: (MandaTodo) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HMColor.Background)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        HistoryGraph()

        HistoryTitleBar()

        HistoryController()

        HistoryTodo()
    }
}

@Composable
fun HistoryGraph(){

}

@Composable
fun HistoryTitleBar(){

}

@Composable
fun HistoryController(){

}

@Composable
fun HistoryTodo(){

}

