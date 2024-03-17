package com.coldblue.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.history.content.HistoryContent
import com.coldblue.model.Todo
import java.time.LocalDate

@Composable
fun HistoryScreen(
    historyViewModel: HistoryViewModel = hiltViewModel(),
    navigateToSetting: () -> Unit,
    navigateToTodoEdit: (Int, String, String) -> Unit

) {
    val historyUiState by historyViewModel.historyUiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        HistoryContentWithState(
            uiState = historyUiState,
            navigateToSetting = navigateToSetting,
            selectYear = historyViewModel::selectYear,
            selectDate = historyViewModel::selectDate,
            toggleTodo = historyViewModel::toggleTodo,
            navigateToTodoEdit = navigateToTodoEdit
        )
    }
}

@Composable
fun HistoryContentWithState(
    uiState: HistoryUiState,
    navigateToSetting: () -> Unit,
    selectYear: (Int) -> Unit,
    selectDate: (LocalDate) -> Unit,
    toggleTodo: (Todo) -> Unit,
    navigateToTodoEdit: (Int, String, String) -> Unit

) {
    when (uiState) {
        is HistoryUiState.Loading -> Text(text = "로딩")
        is HistoryUiState.Error -> Text(text = uiState.msg)
        is HistoryUiState.Success ->
            HistoryContent(
                historyUiState = uiState,
                navigateToSetting = navigateToSetting,
                selectYear = selectYear,
                selectDate = selectDate,
                toggleTodo = toggleTodo,
                navigateToTodoEdit = navigateToTodoEdit
            )
    }

}