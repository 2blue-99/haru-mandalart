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

    val testManda by loginViewModel.testManda.collectAsStateWithLifecycle()
    val testTodo by loginViewModel.testTodo.collectAsStateWithLifecycle()
    val testToken by loginViewModel.testToken.collectAsStateWithLifecycle()
    Column {
        Text(text = "LoginScreen")
        Button(onClick = { navigateToTodo() }) {
            Text(text = "Navigate To Todo")
        }
        Button(onClick = { loginViewModel.insertManda() }) {
            Text(text = "Insert manda")
        }
        Button(onClick = { loginViewModel.insertTodo() }) {
            Text(text = "Insert Todo")
        }
        Button(onClick = { loginViewModel.updateToken() }) {
            Text(text = "Update Todo")
        }
    }
}