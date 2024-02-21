package com.coldblue.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginScreen(
    navigateToTodo: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
){

    val test by loginViewModel.test.collectAsStateWithLifecycle()

    Column {
        Text(text = "LoginScreen")
        Button(onClick = { navigateToTodo() }) {
            Text(text = "Navigate To Todo")
        }
        Button(onClick = { loginViewModel.insertManda() }) {
            Text(text = "Insert Todo")
        }
    }
}