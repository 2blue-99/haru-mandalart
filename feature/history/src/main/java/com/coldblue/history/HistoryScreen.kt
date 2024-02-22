package com.coldblue.history

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HistoryScreen(
    navigateToSetting: () -> Unit
){

    Column {
        Text(text = "HistoryScreen")
        Button(onClick = { navigateToSetting() }) {
            Text(text = "Navigate To Setting")
        }
    }
}