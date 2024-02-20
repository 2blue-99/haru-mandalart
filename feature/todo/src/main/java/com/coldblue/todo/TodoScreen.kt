package com.coldblue.todo

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TodoScreen(
    onClick: (String) -> Unit
){
    Column {
        Text(text = "TodoScreen")
        Button(onClick = { onClick("Login") }) { Text("login") }
        Button(onClick = { onClick("Setting") }) { Text("setting") }
        Button(onClick = { onClick("Tutorial") }) { Text("tutorial") }
    }
}