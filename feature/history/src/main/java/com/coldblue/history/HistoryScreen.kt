package com.coldblue.history

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HistoryScreen(
    historyViewModel: HistoryViewModel = hiltViewModel(),
    navigateToBackStack: () -> Unit,
){
    Button(onClick = { navigateToBackStack() }) {
        Text("History")
    }
}