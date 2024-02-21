package com.coldblue.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LoginScreen(
    navigateToTodo: () -> Unit
){
    Column {
        Text(text = "LoginScreen")
        Button(onClick = { navigateToTodo() }) {
            Text(text = "Navigate To Todo")
        }
    }
}