package com.coldblue.todo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.todo.TodoScreen

const val todoRoute = "Todo"
fun NavController.navigateToTodo(navOptions: NavOptions){
    this.navigate(todoRoute, navOptions)
}

fun NavGraphBuilder.todoScreen(onClick: (String) -> Unit){
    composable(route = todoRoute){ TodoScreen(onClick) }
}