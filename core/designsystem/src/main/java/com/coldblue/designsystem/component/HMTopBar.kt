package com.coldblue.designsystem.component

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HMTopBar(
    popBackStack: () -> Unit
){
    TopAppBar(
        title = { Text(text = "설정") },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.LightGray),
        navigationIcon = {
            Button(
                onClick = { popBackStack() }
            ) {
                Text(text = "Back")
            }
        },
    )
}