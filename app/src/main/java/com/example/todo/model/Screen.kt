package com.example.todo.model

sealed class Screen(val route: String) {
    object ListAndSettings: Screen("list_settings")
    object ListOfToDos : Screen("list")
    object Settings : Screen("settings")
    object EditTask : Screen("edit_task")
}
