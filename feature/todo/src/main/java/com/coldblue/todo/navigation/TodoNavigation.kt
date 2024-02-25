package com.coldblue.todo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.coldblue.data.navi.Route
import com.coldblue.todo.TodoScreen

fun NavController.navigateToTodo(navOptions: NavOptions? = null) {
    this.navigate(Route.todo, navOptions)
}

fun NavGraphBuilder.todoScreen() {
    composable(route = Route.todo) {
        TodoScreen( )}
}