package com.coldblue.todo

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TodoScreen(
    navigateToTutorial: () -> Unit,
    navigateToHistory: () -> Unit
) {
    Column {
        Text(text = "TodoScreen")
    }
}