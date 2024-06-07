package com.coldblue.history

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.model.MandaTodo

@Composable
fun HistoryScreen(
    historyViewModel: HistoryViewModel = hiltViewModel(),
    navigateToBackStack: () -> Unit,
){

    val historyUIState: State<HistoryUIState> = historyViewModel.historyUIState.collectAsStateWithLifecycle()

    Row {
        Button(onClick = { navigateToBackStack() }) {
            Text("Back")
        }

        Button(onClick = { historyViewModel.changeMandaTodoIndex(3) }) {
            Text("index")
        }


    }
}