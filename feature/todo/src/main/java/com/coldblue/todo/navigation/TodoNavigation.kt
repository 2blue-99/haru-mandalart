package com.coldblue.todo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.coldblue.todo.TodoEditScreen
import com.coldblue.todo.TodoScreen
import com.coldblue.todo.uistate.DATE
import com.coldblue.todo.uistate.MY_TIME
import com.coldblue.todo.uistate.TITLE
import com.coldblue.todo.uistate.TODO_ID

const val todoRoute = "Todo"
const val todoEditRoute = "TodoEdit"
fun NavController.navigateToTodo(navOptions: NavOptions? = null) {
    this.popBackStack()
    this.navigate(todoRoute, navOptions)
}

fun NavController.navigateToTodoEdit(
    todoId: Int,
    title: String,
    myTime: String,
    date: String,
    navOptions: NavOptions? = null
) {
    this.navigate("$todoEditRoute/$todoId/$title/$myTime/$date", navOptions)
}


fun NavGraphBuilder.todoScreen(navigateToTodoEdit: (Int, String, String,String) -> Unit) {
    composable(route = todoRoute) {
        TodoScreen(navigateToTodoEdit = navigateToTodoEdit)
    }
}

fun NavGraphBuilder.todoEditScreen(onDismissRequest: () -> Unit) {
    composable(
        route = "$todoEditRoute/{$TODO_ID}/{$TITLE}/{$MY_TIME}/{$DATE}",
        arguments = listOf(
            navArgument(TODO_ID) { type = NavType.IntType },
            navArgument(TITLE) { type = NavType.StringType },
            navArgument(MY_TIME) { type = NavType.StringType },
            navArgument(DATE) { type = NavType.StringType },
        )
    ) { backStackEntry ->
//        val todoId = backStackEntry.arguments?.getInt("todoId")
//        val title = backStackEntry.arguments?.getString("title")
//        val myTimeJson = backStackEntry.arguments?.getString("myTime")
//        val myTime = Gson().fromJson(myTimeJson, MyTime::class.java)
        TodoEditScreen(onDismissRequest = onDismissRequest)

    }
}

