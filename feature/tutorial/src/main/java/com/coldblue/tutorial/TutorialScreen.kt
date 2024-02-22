package com.coldblue.tutorial

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TutorialScreen(navigateToManda: () -> Unit){
    Column {
        Text(text = "TutorialScreen")
        Button(onClick = { navigateToManda() }) {
            Text(text = "Navigate To Manda")
        }
    }
}