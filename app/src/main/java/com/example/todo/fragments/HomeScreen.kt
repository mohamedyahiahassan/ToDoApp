package com.example.todo.fragments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todo.model.Screen
import com.example.todo.TaskViewModel
import com.example.todo.utils.ListOfToDos
import  androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.todo.ThemeViewModel

@Composable
fun Screens(themeViewModel: ThemeViewModel,paddingValues: PaddingValues, navController:NavHostController,selectedDayhere:MutableState<String>) {


    Column(
        modifier = Modifier
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {


        NavHost(
            navController = navController,
            startDestination = Screen.ListOfToDos.route
        ) {

            composable(Screen.ListOfToDos.route) {

                val viewModel = hiltViewModel<TaskViewModel>()
                ListOfToDos(selectedDayhere,viewModel,
                    navigateToEditTask = {uniqueId->
                    navController.navigate("${Screen.EditTask.route}/${uniqueId}")
                })
            }

            composable(Screen.Settings.route) {

                val viewModel = hiltViewModel<TaskViewModel>()
                SettingsTab(themeViewModel,viewModel)
            }

            composable("${Screen.EditTask.route}/{uniqueId}",
                arguments = listOf(navArgument("uniqueId"){

                    type = NavType.IntType
                })){

                val viewModel = hiltViewModel<TaskViewModel>()

                val uniqueId = it.arguments?.getInt("uniqueId")

                if (uniqueId != null) {
                    EditTask(uniqueId,viewModel = viewModel){

                        viewModel.updateTask()

                        navController.navigateUp()

                    }
                }
            }

        }
    }
}




